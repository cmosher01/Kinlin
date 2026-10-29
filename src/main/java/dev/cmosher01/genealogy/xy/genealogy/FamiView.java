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
import java.util.*;

public class FamiView {
    private final @NonNull Fami model;

    public FamiView(final @NonNull Fami model) {
        this.model = model;
    }

    @NonNull Fami model() {
        return this.model;
    }

    public void paint(final @NonNull Graphics2D g) {
        if (this.model.selected()) {
            g.setColor(Solarized.MAGENTA);
        } else {
            g.setColor(Solarized.BASE_00_GRAY__DIM);
        }

        drawBars(g, this.model.getBarParents());

        draw(g, this.model.getDescentBarParent());
        draw(g, this.model.getDescentBarChilds());
        draw(g, this.model.getDescentBarMiddle());

        draw(g, this.model.getBarChildren());
        this.model.getRBarChild().forEach(olin -> draw(g, olin));
    }

    private static void drawBars(final @NonNull Graphics2D g, final @NonNull Optional<Bars> bar) {
        if (bar.isPresent()) {
            g.draw(Awts.line(bar.get().one(true)));
            g.draw(Awts.line(bar.get().one(false)));
        }
    }

    private static void draw(final @NonNull Graphics2D g, final @NonNull Optional<OrthogonalLine> olin) {
        if (olin.isPresent()) {
            draw(g, olin.get());
        }
    }

    private static void draw(final @NonNull Graphics2D g, final @NonNull OrthogonalLine olin) {
        g.draw(Awts.line(olin));
    }
}
