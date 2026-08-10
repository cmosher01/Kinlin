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

import static java.lang.Math.*;

public class Nearness {
    /**
     *  Since this package is geared towards use in graphics,
     *  we choose the default based on pixels:
     *  assuming a dimension of 1.0 represents one visible pixels
     *  then even on a NTSC TV with huge pixels, a movement of a pixel
     *  by this distance wouldn't be visible to the naked eye.
     *  So the value is small enough that an error within tolerance will not be visible.
     *  We also want a value large enough to mask the expected worst case
     *  of accumulated roundoff error. Even adding one million numbers shouldn't
     *  cause more than 1e-10 accumulated error.
     */
//    public static final @NonNull Tolerance E = Tolerance.magnitude(-5);

//    public static boolean near(final double a, final double b) {
//        return near(a, b, E);
//    }

    // nothing is near a NaN, not even another NaN
    // +INF  == +INF
    // -INF  == -INF
    // -0.0D == +0.0D
    // nothing is near +INF (except +INF itself)
    // nothing is near -INF (except -INF itself)
//    public static boolean near(final double a, final double b, final @NonNull Tolerance tol) {
//        return a == b || tol.within(b-a);
//    }


    /**
     * Comparison of IEEE 754 floating point double precision numbers.
     * Our environment and constraints:
     * Primitive/wrapped doubles are used to represent units of pixels.
     * The canvas size is assumed to be a maximum of 1x10^8 pixels square.
     * We expect to do about 1x10^6 calculations at the absolute maximum.
     * Visible differences of up to one full pixel are acceptable (at the borders, for example)
     * We expect to compare any value against exactly zero a considerable number of times.
     */

    private static final double E_REL = 1e-8;
    private static final double E_ABS = 1e-3;

    public static boolean near(double a, double b) {
        final boolean r;

        if (a == b) {
            // exact bit-wise match
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
