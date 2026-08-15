package dev.cmosher01.math.doubles;

import lombok.*;

public final class DoubleUtil {
    @Deprecated
    private DoubleUtil() {
        throw new UnsupportedOperationException();
    }

    public static final Double ONE = fp(1D);
    public static final Double POSITIVE_ZERO = ONE / Double.POSITIVE_INFINITY;
    public static final Double NEGATIVE_ZERO = ONE / Double.NEGATIVE_INFINITY;
    public static final Double ZERO = POSITIVE_ZERO; // a more convenient name
    public static final Double DOUBLE_EPSILON = Math.ulp(ONE);

    // these methods work with inputs of any double,
    // including NaN, +/-INF, +/-0, or any regular double
    public static boolean isNegative(final double d) {
        return signbit(d) != 0L;
    }
    public static boolean isPositive(final double d) {
        return signbit(d) == 0L;
    }
    private static long signbit(final double d) {
        return Double.doubleToLongBits(d) & (1L<<63);
    }




    // these fp methods allow for wrapping anything into a Double
    // using syntax like this:
    //     fp(73)
    // note: a literal null argument needs to be cast to an Object
    public static @NonNull Double fp(final double d) {
        return d;
    }
    public static @NonNull Double fp(final boolean b) {
        return fp(b ? 1D : 0D);
    }
    public static @NonNull Double fp(final char c) {
        val v = fp(Character.getNumericValue(c));
        return v < 0D ? Double.NaN : v;
    }
    public static @NonNull Double fp(final /*@Nullable*/ Object o) {
        try {
            return switch (o) {
                case Boolean b -> fp(b.booleanValue());
                case Number n -> n.doubleValue();
                default -> fp(Double.valueOf(o.toString()));
            };
        } catch (final Throwable e) {
            return Double.NaN;
        }
    }

    public static double unbox(final @NonNull Double n) {
        return n;
    }


    // asInt and asLong are typically used as the final step after all
    // geometric manipulations are done in the double-floating-point domain,
    // when the underlying graphics subsystem uses integral types.
    // These two methods round to the nearest long or integer.
    // See StrictMath.clamp(long,int,int), StrictMath.round, and StrictMath.rint
    // rounds to nearest int (ties round towards +INF)
    // NaN-->0, +INF-->MAX_LONG, -INF-->MIN_LONG
    public static int asInt(final double d) {
        return Math.clamp(asLong(d), Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    public static long asLong(final double d) {
        return StrictMath.round(StrictMath.rint(d));
    }



    // returns: if n is near 0, then +0.0D, else n
    public static Double clearToPosZero(final Double n) {
        return n == ZERO ? POSITIVE_ZERO : n;
    }
    // returns: if n is near 0, then -0.0D, else n
    public static Double clearToNegZero(final Double n) {
        return n == ZERO ? NEGATIVE_ZERO : n;
    }
    // returns: if n is near 0, then -0.0 or +0.0 (preserving the sign of n),
    // else n
    // note: NaN is _always_ positive and is never near zero
    public static Double clearAtZero(final Double n) {
        return n != ZERO ? n : (isPositive(n) ? POSITIVE_ZERO : NEGATIVE_ZERO);
    }
    // returns: if n is near i, then i, else n
    // returns NaN if n is NaN
    // if i == 0 and n is near zero, the result will be +0.0D (even if n is negative)
    // to avoid this behavior call one of the clearXxxZero methods
    public static Double clearAt(final Double n, final int i) {
        return n == fp(i) ? i : n;
    }


// I don't think we actually need these at all. Math.min and max should work fine in all cases (except testing for equality)
//    public static Double Xmin(final @NonNull Double a, final @NonNull Double b) {
//        if (unbox(a) == 0 && unbox(b) == 0 && isNegative(a) && isPositive(b)) {
//            return NEGATIVE_ZERO;
//        }
//        if (a.isNaN() || b.isNaN()) {
//            return Double.NaN;
//        }
//        return a < b ? a : b;
//    }
//    public static Double Xmax(final @NonNull Double a, final @NonNull Double b) {
//        if (unbox(a) == 0 && unbox(b) == 0 && isPositive(a) && isNegative(b)) {
//            return POSITIVE_ZERO;
//        }
//        if (a.isNaN() || b.isNaN()) {
//            return Double.NaN;
//        }
//        return b < a ? a : b;
//    }
//
//    public static Double min(final @NonNull Double a, final @NonNull Double b) {
//        return Math.min(a, b);
//    }
//    public static Double max(final @NonNull Double a, final @NonNull Double b) {
//        return Math.max(a, b);
//    }

    // filters the given value to be positive:
    // 1. abs (makes sign bit positive)
    // 2. clear at zero (makes numbers within the tolerance range of zero exactly positive zero)
    // -INF --> +INF
    // -anything -> +anything
    // near -0 -> +0
    // -0 -> +0
    // +0 -> +0
    // near +0 -> +0
    // +anything -> +anything
    // +INF --> +INF
    // Nan --> NaN (NaN is always positive)
    // Use Domain.ABS0 instead:
//    public static Double min0(Double n) {
//        return clearToPosZero(Math.abs(n));
//    }
    public static boolean between(final Double dMin, final Double d, final Double dMax) {
        return dMin <= d && d <= dMax;
    }

    public static int compareTo(final @NonNull Double a, final @NonNull Double b) {
        return DoubleExtUtil.compareTo(a, b);
    }
}
