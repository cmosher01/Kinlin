/*
 *     Copyright © 2026, Christopher Alan Mosher, New York, New York, USA, <cmosher01@gmail.com>.
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
import manifold.ext.rt.api.*;

import java.util.Objects;


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


    public static @NonNull Rect square(final @NonNull Dim n, final @NonNull Point center) {
        return new Rect(new RectDim(n), center);
    }

    public @NonNull Rect grow(final @NonNull Outset d) {
        return new Rect(new RectDim(dim.w + d.w, dim.h + d.h), center);
    }
    public @NonNull Rect scale(final @NonNull ScaleFactor k) {
        return new Rect(k * dim, center);
    }
    // do we need this?
    public @NonNull Rect scale(final @NonNull ScaleFactor kw, final ScaleFactor kh) {
        return new Rect(dim.scale(kw, kh), center);
    }
    public @NonNull Rect translate(final @NonNull Xlation d) {
        return new Rect(dim, center + d);
    }

    public static boolean intersect(final @NonNull Rect a, final @NonNull Rect b) {
        val dif = (a.center - b.center).abs();
        val dim = (a.dim + b.dim) * ScaleFactor.HALF;
        return dif.x.d < dim.w.n && dif.y.d < dim.h.n;
    }

    public boolean contains(final @NonNull Point p) {
        val half = ScaleFactor.HALF * dim;
        return
            center.x.u - half.w.n <= p.x.u && p.x.u <= center.x.u + half.w.n &&
            center.y.u - half.h.n <= p.y.u && p.y.u <= center.y.u + half.h.n;
    }
}
