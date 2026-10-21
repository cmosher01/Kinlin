/*
 *     Copyright 2026, Christopher Alan Mosher, New York, New York, USA, <cmosher01@gmail.com>.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.cmosher01.font;

import dev.cmosher01.geom.*;
import dev.cmosher01.geom.Point;
import lombok.*;

import java.awt.*;
import java.awt.font.*;
import java.text.AttributedString;
import java.util.*;
import java.util.List;

public class TextBlock {
    private record Line(@NonNull TextLayout layout, @NonNull Xlation d) {
        public void paint(final @NonNull Graphics2D g, final @NonNull Point c) {
            val p = c + this.d;
            val x = p.x().u().floatValue();
            val y = p.y().u().floatValue();
            this.layout.draw(g, x, y);
        }
    }

    private final @NonNull RectDim dim;
    private final @NonNull List<Line> lines;

    private TextBlock(final @NonNull RectDim dim, final @NonNull List<Line> lines) {
        this.dim = dim;
        this.lines = lines;
    }



    public static @NonNull TextBlock create(final @NonNull List<AttributedString> s) {
        return create(s, Float.MAX_VALUE);
    }

    public static @NonNull TextBlock create(final @NonNull List<AttributedString> s, final @NonNull Dim wMax) {
        return create(s, wMax.n().floatValue());
    }



    public @NonNull RectDim dim() {
        return this.dim;
    }

    public void paint(final @NonNull Graphics2D g, final @NonNull Point c) {
        lines.forEach(line -> line.paint(g, c));
    }




    private static @NonNull TextBlock create(final @NonNull List<AttributedString> s, final float wMax) {
        val layouts = layouts(s, wMax);
        val dims = dimensions(layouts);
        val lines = lines(layouts, dims);
        return new TextBlock(dims, lines);
    }


    private static @NonNull List<TextLayout> layouts(final @NonNull List<AttributedString> rs, final float wMax) {
        val ret = new ArrayList<TextLayout>();

        for (val s : rs) {
            val m = new LineBreakMeasurer(s.getIterator(), FontContext.ctx());
            for (var l = m.nextLayout(wMax); Objects.nonNull(l); l = m.nextLayout(wMax)) {
                ret.add(l);
            }
        }

        return List.copyOf(ret);
    }

    private static @NonNull RectDim dimensions(final @NonNull List<TextLayout> layouts) {
        double w = 0;
        double h = 0;
        for (val layout : layouts) {
            w = Math.max(w, layout.getVisibleAdvance());
            h += layout.getAscent()+layout.getDescent()+layout.getLeading();
        }
        return new RectDim(new Dim(w), new Dim(h));
    }

    private static @NonNull List<Line> lines(final @NonNull List<TextLayout> layouts, final @NonNull RectDim siz) {
        val ret = new ArrayList<Line>(1);

        final double w2 = siz.w().half().n();
        final double h2 = siz.h().half().n();

        double y = 0;
        for (val layout : layouts) {
            y += layout.getAscent();
            val x = (siz.w().n() - layout.getVisibleAdvance()) / 2F;

            // dx,dy offset from center of block
            val d = new Xlation(new MoveTerm(x-w2), new MoveTerm(y-h2));

            ret.add(new Line(layout, d));

            y += layout.getDescent() + layout.getLeading();
        }

        return List.copyOf(ret);
    }
}
