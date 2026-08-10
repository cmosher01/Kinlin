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

package dev.cmosher01.math.doubles;

import lombok.NonNull;

/**
 * Represents a tolerance of error (typically represented by epsilon)
 * in floating point equality comparisons.
 * This is a total tolerance, not a plus/minus.
 *
 * @param e tolerance, can't be infinite or negative or NaN or zero
 */
public record Tolerance(double e) {
    public Tolerance {
        if (!domain(e)) {
            throw new IllegalArgumentException("Invalid Tolerance=="+e+"; must be a finite number greater than zero.");
        }
    }

    public static @NonNull Tolerance magnitude(int exp) {
        return new Tolerance(Math.pow(1, exp));
    }

    public boolean within(final double dx) {
        return Math.abs(dx) <= e;
    }

    private static boolean domain(final double e) {
        return 0 < e && e < Double.POSITIVE_INFINITY;
    }
}
