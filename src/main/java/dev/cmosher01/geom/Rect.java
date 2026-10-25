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

import dev.cmosher01.math.doubles.DoubleUtil;
import lombok.*;
import manifold.ext.rt.api.*;

import java.util.*;

/**
 * A rectangle is defined in terms of its center point and its width and height dimensions.
 *
 * @param dim dimensions of this rectangle
 * @param center center point of this rectangle
 */
public record Rect(@NonNull RectDim dim, @NonNull Point center) implements ComparableUsing<Rect> {
    private static final @NonNull Coord HALF = new Coord(0.5D);
    public static final @NonNull Rect UNIT_SQUARE = new Rect(RectDim.UNIT, new Point(HALF, HALF));
    public static final @NonNull Rect CENTERED_UNIT_SQUARE = new Rect(RectDim.UNIT, Point.ORIGIN);

    @Override
    public boolean equals(final @Self Object o) {
        return o instanceof Rect(RectDim odim, Point ocenter) && (dim == odim && center == ocenter);
    }
    @Override
    public int hashCode() {
        return Objects.hash(dim, center);
    }
    @Override
    public int compareTo(final @NonNull Rect o) {
        throw new UnsupportedOperationException();
    }

    public @NonNull Rect plus(final @NonNull Outset d) {
        return new Rect(dim + d, center);
    }
    public @NonNull Rect times(final @NonNull ScaleFactor k) {
        return new Rect(k * dim, center);
    }
    public @NonNull Rect plus(final @NonNull Xlation d) {
        return new Rect(dim, center + d);
    }

    public @NonNull Rect moveTo(final @NonNull Point center) {
        return new Rect(dim, center);
    }
    public @NonNull Rect resize(final @NonNull RectDim dim) {
        return new Rect(dim, center);
    }

    public boolean contains(final @NonNull Point p) {
        val half = ScaleFactor.HALF * dim;
        return
            DoubleUtil.within(p.x.u, center.x.u, half.w.n) &&
            DoubleUtil.within(p.y.u, center.y.u, half.h.n);
    }



    public @NonNull Double xMin() { return center.x().u() - dim.w().half().n(); }
    public @NonNull Double xMax() { return center.x().u() + dim.w().half().n(); }
    public @NonNull Double yMin() { return center.y().u() - dim.h().half().n(); }
    public @NonNull Double yMax() { return center.y().u() + dim.h().half().n(); }




    public static boolean intersect(final @NonNull Rect a, final @NonNull Rect b) {
        val dif = (a.center - b.center).abs();
        val dim = ScaleFactor.HALF * (a.dim + b.dim);
        return dif.dx.d < dim.w.n && dif.dy.d < dim.h.n;
    }

    public static Optional<Rect> intersection(final @NonNull Rect a, final @NonNull Rect b) {
        final double minXa = a.center().x().u() - a.dim().w().half().n();
        final double maxXa = a.center().x().u() + a.dim().w().half().n();
        final double minYa = a.center().y().u() - a.dim().h().half().n();
        final double maxYa = a.center().y().u() + a.dim().h().half().n();

        final double minXb = b.center().x().u() - b.dim().w().half().n();
        final double maxXb = b.center().x().u() + b.dim().w().half().n();
        final double minYb = b.center().y().u() - b.dim().h().half().n();
        final double maxYb = b.center().y().u() + b.dim().h().half().n();

        final double isctMinX = Math.max(minXa, minXb);
        final double isctMaxX = Math.min(maxXa, maxXb);
        final double isctMinY = Math.max(minYa, minYb);
        final double isctMaxY = Math.min(maxYa, maxYb);

        final Optional<Rect> ret;
        if (isctMinX < isctMaxX && isctMinY < isctMaxY) {
            final double isctWidth = isctMaxX - isctMinX;
            final double isctHeight = isctMaxY - isctMinY;
            final RectDim dim = new RectDim(new Dim(isctWidth), new Dim(isctHeight));

            final Coord isctCenterX = new Coord(isctMinX + isctWidth/2D);
            final Coord isctCenterY = new Coord(isctMinY + isctHeight/2D);
            final Point center = new Point(isctCenterX, isctCenterY);

            ret = Optional.of(new Rect(dim, center));
        } else {
            ret = Optional.empty();
        }
        return ret;
    }



    public static @NonNull Rect bounds(final @NonNull Collection<Rect> rs) {
        if (rs.isEmpty) {
            return new Rect(new RectDim(), Point.ORIGIN);
        }
        double xMin = Double.MAX_VALUE;
        double xMax = -Double.MAX_VALUE;
        double yMin = Double.MAX_VALUE;
        double yMax = -Double.MAX_VALUE;
        for (val r : rs) {
            if (r.xMin() < xMin) {
                xMin = r.xMin();
            }
            if (xMax < r.xMax()) {
                xMax = r.xMax();
            }
            if (r.yMin() < yMin) {
                yMin = r.yMin();
            }
            if (yMax < r.yMax()) {
                yMax = r.yMax();
            }
        }
        val w = new Dim(xMax - xMin);
        val h = new Dim(yMax - yMin);
        val x = new Coord(xMin + w.half().n());
        val y = new Coord(yMin + h.half().n());
        return new Rect(new RectDim(w, h), new Point(x, y));
    }
}
