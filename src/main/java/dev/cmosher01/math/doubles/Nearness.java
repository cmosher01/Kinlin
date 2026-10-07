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

package dev.cmosher01.math.doubles;

import static java.lang.Math.*;

/**
 * Comparison of IEEE 754 floating point double precision numbers.
 * Our environment and constraints:
 * Primitive/wrapped doubles are used to represent units of pixels.
 * The canvas size is assumed to be a maximum of 1x10^8 pixels square.
 * We expect to do about 1x10^6 calculations at the absolute maximum.
 * Visible differences of up to one full pixel are acceptable (at the borders, for example)
 * We expect to compare any value against exactly zero a considerable number of times.
 * The relative and absolute allowable tolerances (epsilon) are chosen
 * based on these criteria.
 */
public class Nearness {
    private static final double E_REL = 1e-8;
    private static final double E_ABS = 1e-3;

    /**
     * Check if two double values are "near" (approximately equal within
     * the appropriate allowable tolerances).
     *
     * @param a double value
     * @param b double value
     * @return true if a and b compare as "near" based on the tolerances
     */
    public static boolean near(double a, double b) {
        final boolean r;

        if (a == b) {
            r = true;
        } else {
            final double d = abs(a - b);

            a = abs(a);
            b = abs(b);
            if (a < E_ABS && b < E_ABS) {
                // near zero
                r = d < E_ABS;
            } else {
                r = d/max(a,b) < E_REL;
            }
        }

        return r;
    }
}
