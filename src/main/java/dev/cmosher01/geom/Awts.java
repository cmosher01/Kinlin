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

package dev.cmosher01.geom;

import lombok.*;

import java.awt.geom.*;

public class Awts {
    public static @NonNull Rectangle2D.Double rect(final @NonNull Rect rect) {
        return new Rectangle2D.Double(
            rect.xMin(), rect.yMin(),
            rect.dim().w().n(), rect.dim().h().n());
    }

    public static @NonNull Rect rect(final @NonNull Rectangle2D rect) {
        return new Rect(
            new RectDim(new Dim(rect.getWidth()), new Dim(rect.getHeight())),
            new Point(new Coord(rect.getCenterX()), new Coord(rect.getCenterY())));
    }



    public static @NonNull Point2D.Double point(final @NonNull Point pt) {
        return new Point2D.Double(pt.x().u(), pt.y().u());
    }

    public static @NonNull Point point(final @NonNull Point2D pt) {
        return new Point(new Coord(pt.getX()), new Coord(pt.getY()));
    }



    public static @NonNull Line2D.Double line(final @NonNull Line line) {
        return new Line2D.Double(point(line.a()), point(line.b()));
    }

    public static @NonNull Line line(final @NonNull Line2D line) {
        return new Line(point(line.p1), point(line.p2));
    }



    public static @NonNull Line2D.Double line(final @NonNull OrthogonalLine line) {
        final Line2D.Double ret;
        if (line.or == OrthogonalLine.Orient.H) {
            final double y = line.pos().u();
            ret = new Line2D.Double(line.a().u(), y, line.b().u(), y);
        } else {
            final double x = line.pos().u();
            ret = new Line2D.Double(x, line.a().u(), x, line.b().u());
        }
        return ret;
    }
}
