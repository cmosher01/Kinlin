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

public record Point(@NonNull Coord x = Coord.ORIGIN, @NonNull Coord y = Coord.ORIGIN) implements ComparableUsing<Point> {
    public static Point ORIGIN = new Point();
    public static Point NULL = new Point(Coord.NULL, Coord.NULL);

    @Override
    public boolean equals(final @Self Object o) {
        return o instanceof Point(Coord ox, Coord oy) && x == ox && y == oy;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
    @Override
    public int compareTo(final @NonNull Point o) {
        throw new UnsupportedOperationException();
    }

    public boolean origin() {
        return this == ORIGIN;
    }

    public @NonNull Dim distance(final @NonNull Point that) {
        val d = that - this;
        return new Dim(Math.hypot(d.x.d, d.y.d));
    }

    // Point <- Point + Xlation
    public @NonNull Point plus(final @NonNull Xlation d) {
        return new Point(x + d.x, y + d.y);
    }
    // Point <- Point - Xlation
    public @NonNull Point minus(final @NonNull Xlation d) {
        return plus(-d);
    }
    // Xlation <- Point - Point
    public @NonNull Xlation minus(final @NonNull Point o) {
        return new Xlation(x - o.x, y - o.y);
    }
    // Point <- Point * ScaleFactor
    public @NonNull Point times(final @NonNull ScaleFactor k) {
        return new Point(k * x, k * y);
    }
    // Point <- Point / ScaleFactor
    public @NonNull Point div(final @NonNull ScaleFactor k) {
        return new Point(~k * x, ~k * y);
    }
}
