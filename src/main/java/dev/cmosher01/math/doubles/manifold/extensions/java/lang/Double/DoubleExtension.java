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

package dev.cmosher01.math.doubles.manifold.extensions.java.lang.Double;

import dev.cmosher01.math.doubles.DoubleExtUtil;
import lombok.NonNull;
import manifold.ext.rt.api.*;

/**
 * Augment java.long.Double.
 * Each method delegates to DoubleExtUtil.
 */
@Extension
public abstract class DoubleExtension implements ComparableUsing<Double> {
    @Extension
    @Intercept
    public static boolean equals(final @This Double self, final Object that) {
        return DoubleExtUtil.equals(self, that);
    }

    @Extension
    @Intercept
    public static int compareTo(final @This Double self, final @NonNull Double that) {
        return DoubleExtUtil.compareTo(self, that);
    }

    @Extension
    public static boolean compareToUsing(final @This Double self, final @NonNull Double that, final @NonNull Operator op) {
        return DoubleExtUtil.compareToUsing(self, that, op);
    }

    @Extension
    public static boolean isPositive(final @This Double self) {
        return DoubleExtUtil.isPositive(self);
    }

    @Extension
    public static boolean isNegative(final @This Double self) {
        return DoubleExtUtil.isNegative(self);
    }

    @Extension
    public static boolean isFinite(final @This Double self) {
        return DoubleExtUtil.isFinite(self);
    }

    @Extension
    public static boolean isExactlyZero(final @This Double self) {
        return DoubleExtUtil.isExactlyZero(self);
    }

    @Extension
    public static @NonNull Double inv(final @This Double self) {
        return DoubleExtUtil.inv(self);
    }
}
