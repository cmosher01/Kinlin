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

package dev.cmosher01.genealogy.xy;

import dev.cmosher01.geom.*;
import lombok.val;

import java.awt.geom.*;

public class InteractiveRectModel {
    public static final double ITEM_WIDTH = 60.0D;
    public static final double ITEM_HEIGHT = 30.0D;

    private final double w;
    private final double h;
    private final String tag;

    private final MovablePoint pt;

    private boolean selected;



    public InteractiveRectModel(final double x, final double y, final String tag, final Grid grid) {
        this.w = ITEM_WIDTH;
        this.h = ITEM_HEIGHT;
        this.tag = tag;

        this.pt = new MovablePoint(grid, new Point(new Coord(x), new Coord(y)));
    }

    public String tag() {
        return this.tag;
    }

    public boolean selected() {
        return this.selected;
    }

    public void select(final boolean selected) {
        this.selected = selected;
    }

    public void move(final Point2D.Double d) {
        this.pt.move(new Xlation(new MoveTerm(d.getX()), new MoveTerm(d.getY())));
    }

    public boolean isModified() {
        return this.pt.dirty();
    }

    public void anchor() {
        this.pt.anchor();
    }

    public void flip() {
        this.pt.flip();
    }

    public Rectangle2D.Double rect() {
        val p = this.pt.get();
        return new Rectangle2D.Double(p.x().u(), p.y().u(), this.w, this.h);
    }
}
