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

/**
 * A set of two bars, above and below a line segment. Looks like = for segment -
 * center line: *----------------------------*
 *        bars: *============================*
 * vertical is the total height of the set of bars
 *
 * @param center
 * @param vertical
 */
public record Bars(@NonNull Line center, @NonNull Dim vertical) {
    public @NonNull Bars scale(final @NonNull ScaleFactor k) {
        return new Bars(k * center, k * vertical);
    }
    public @NonNull Bars translate(final @NonNull Xlation d) {
        return new Bars(center + d, vertical);
    }

    public @NonNull Point section(final Proportion p, final boolean top = false) {
        return one(top).section(p);
    }

    public @NonNull Line one(final boolean top) {
        return center + (top ? -half() : half());
    }

    private Xlation half() {
        return new Xlation(MoveTerm.IDENTITY, new MoveTerm(vertical.half().n()));
    }
}
