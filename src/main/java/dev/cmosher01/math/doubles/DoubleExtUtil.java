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

import lombok.NonNull;
import manifold.ext.rt.api.ComparableUsing.Operator;

import static dev.cmosher01.math.doubles.DoubleUtil.*;
import static manifold.ext.rt.api.ComparableUsing.Operator.*;

/*
BE CAREFUL not to cause recursive calls due to operations on Double objects
being "caught" by DoubleExtension (and then calling back into here)
 */
public final class DoubleExtUtil {
    @Deprecated
    private DoubleExtUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Compares using "near" semantics.
     * This breaks the normal contract of equals, so beware.
     * @param a any double
     * @param x any object
     * @return true if "nearly" the same number
     */
    public static boolean equals(final @NonNull Double a, final Object x) {
        return x instanceof Number b && Nearness.near(a, b.doubleValue());
    }

    public static boolean compareToUsing(final @NonNull Double a, final @NonNull Double b, final @NonNull Operator op) {
        return switch (op) {
            case LT -> !equals(a,b)  &&  unbox(a) <  unbox(b);
            case EQ ->  equals(a,b);
            case GT -> !equals(a,b)  &&  unbox(a) >  unbox(b);
            case GE ->  equals(a,b)  ||  unbox(a) >= unbox(b);
            case NE -> !equals(a,b);
            case LE ->  equals(a,b)  ||  unbox(a) <= unbox(b);
        };
    }

    public static int compareTo(final @NonNull Double a, final @NonNull Double b) {
        return
            compareToUsing(a, b, LT) ?
                -1 :
            (
            compareToUsing(a, b, GT) ?
                +1 :
            (
                +0
            ));
    }

    public static boolean isNegative(final Double d) {
        return DoubleUtil.isNegative(d);
    }
    public static boolean isPositive(final Double d) {
        return DoubleUtil.isPositive(d);
    }
    public static boolean isFinite(final Double d) {
        return Double.isFinite(d);
    }
    public static @NonNull Double inv(final Double d) {
        return ONE/d;
    }
    public static boolean isExactlyZero(final Double d) {
        return 0L == Double.doubleToRawLongBits(Math.abs(d));
    }
}
