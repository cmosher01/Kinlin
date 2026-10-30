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

package dev.cmosher01.genealogy.xy.genealogy;

import dev.cmosher01.genealogy.xy.*;
import dev.cmosher01.geom.*;
import lombok.*;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "OptionalIsPresent"})
public class FamiView {
    private final @NonNull Fami model;

    public FamiView(final @NonNull Fami model) {
        this.model = model;
    }

    @NonNull Fami model() {
        return this.model;
    }

    public void paint(final @NonNull Graphics2D g, final @NonNull Rectangle2D clip) {
        if (this.model.selected()) {
            g.setColor(Solarized.MAGENTA);
        } else {
            g.setColor(Solarized.BASE_00_GRAY__DIM);
        }

        drawBars(g, this.model.getBarParents(), clip);

        draw(g, this.model.getDescentBarParent(), clip);
        draw(g, this.model.getDescentBarChilds(), clip);
        draw(g, this.model.getDescentBarMiddle(), clip);

        draw(g, this.model.getBarChildren(), clip);
        this.model.getRBarChild().forEach(olin -> draw(g, olin, clip));
    }

    private static void drawBars(final @NonNull Graphics2D g, final @NonNull Optional<Bars> bar, @NonNull Rectangle2D clip) {
        if (bar.isPresent()) {
            drawBar(g, bar.get(), true, clip);
            drawBar(g, bar.get(), false, clip);
        }
    }

    private static void drawBar(final @NonNull Graphics2D g, final @NonNull Bars bars, final boolean topBar, final @NonNull Rectangle2D clip) {
        Line2D.Double top = Awts.line(bars.one(topBar));
        Optional<Line2D.Double> topClipped = clipLine(top, clip);
        if (topClipped.isPresent()) {
            g.draw(topClipped.get());
        }
    }

    private static void draw(final @NonNull Graphics2D g, final @NonNull Optional<OrthogonalLine> olin, @NonNull Rectangle2D clip) {
        if (olin.isPresent()) {
            draw(g, olin.get(), clip);
        }
    }

    private static void draw(final @NonNull Graphics2D g, final @NonNull OrthogonalLine olin, @NonNull Rectangle2D clip) {
        val lineClipped = clipLine(Awts.line(olin), clip);
        if (lineClipped.isPresent()) {
            g.draw(lineClipped.get());
        }
    }







    private static Optional<Line2D.Double> clipLine(final Line2D.Double ln, final Rectangle2D c) {
        double x1 = ln.x1;
        double y1 = ln.y1;
        double x2 = ln.x2;
        double y2 = ln.y2;

        final double xMin = c.getMinX();
        final double xMax = c.getMaxX();
        final double yMin = c.getMinY();
        final double yMax = c.getMaxY();

        if (x1 < xMin && x2 < xMin) {
            return Optional.empty();
        }
        if (x1 < xMin) {
            y1 = interpolate(y1, x1, y2, x2, xMin);
            x1 = xMin;
        }
        if (x2 < xMin) {
            y2 = interpolate(y1, x1, y2, x2, xMin);
            x2 = xMin;
        }

        if (x1 > xMax && x2 > xMax) {
            return Optional.empty();
        }
        if (x1 > xMax) {
            y1 = interpolate(y1, x1, y2, x2, xMax);
            x1 = xMax;
        }
        if (x2 > xMax) {
            y2 = interpolate(y1, x1, y2, x2, xMax);
            x2 = xMax;
        }

        if (y1 < yMin && y2 < yMin) {
            return Optional.empty();
        }
        if (y1 < yMin) {
            x1 = interpolate(x1, y1, x2, y2, yMin);
            y1 = yMin;
        }
        if (y2 < yMin) {
            x2 = interpolate(x1, y1, x2, y2, yMin);
            y2 = yMin;
        }

        if (y1 > yMax && y2 > yMax) {
            return Optional.empty();
        }
        if (y1 > yMax) {
            x1 = interpolate(x1, y1, x2, y2, yMax);
            y1 = yMax;
        }
        if (y2 > yMax) {
            x2 = interpolate(x1, y1, x2, y2, yMax);
            y2 = yMax;
        }

        if (!(x1 >= xMin) || !(x1 <= xMax) || !(y1 >= yMin) || !(y1 <= yMax) ||
            !(x2 >= xMin) || !(x2 <= xMax) || !(y2 >= yMin) || !(y2 <= yMax)) {
            return Optional.empty();
        }

        return Optional.of(new Line2D.Double(x1, y1, x2, y2));
    }

    private static double interpolate(double a1, double b1, double a2, double b2, double t) {
        return a1 + (a2 - a1) * (t - b1) / (b2 - b1);
    }
}
