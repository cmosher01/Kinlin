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

import lombok.NonNull;
import manifold.ext.rt.api.*;

import java.util.Objects;

/**
 * Line segment, defined by its two endpoints. Non-directional.
 * Line(a,b) is identical to Line(b,a), for any two Point objects a and b
 */
public record Line(@NonNull Point a, @NonNull Point b) implements ComparableUsing<Line> {
    @Override
    public boolean equals(final @Self Object o) {
        return
            o instanceof Line(Point oa, Point ob) &&
            ((a == oa && b == ob) || (a == ob && b == oa));
    }
    @Override
    public int hashCode() {
        return Objects.hash(a, b) ^ Objects.hash(b, a);
    }
    @Override
    public int compareTo(final @NonNull Line o) {
        throw new UnsupportedOperationException();
    }

    public @NonNull Line times(final @NonNull ScaleFactor k) {
        return new Line(k * a, k * b);
    }
    public @NonNull Line plus(final @NonNull Xlation d) {
        return new Line(a + d, b + d);
    }

    public @NonNull Point section(final Proportion p) {
        return new Point(
            new Coord(~p * a.x.u + p * b.x.u),
            new Coord(~p * a.y.u + p * b.y.u));
    }
}
