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
    public static boolean isZero(final @This Double self) {
        return DoubleExtUtil.isZero(self);
    }

    @Extension
    public static @NonNull Double inv(final @This Double self) {
        return DoubleExtUtil.inv(self);
    }
}
