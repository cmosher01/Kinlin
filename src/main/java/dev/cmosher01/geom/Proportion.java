package dev.cmosher01.geom;

import dev.cmosher01.math.doubles.DoubleUtil;
import lombok.NonNull;
import manifold.ext.rt.api.*;

import java.util.Objects;

public record Proportion(@NonNull Double k = identity) implements ComparableUsing<Proportion> {
    private static final Double identity = DoubleUtil.ONE;
    public static final Proportion IDENTITY = new Proportion();
    public static final Proportion NULL = new Proportion(Double.NaN);
    public static final Proportion NONE = new Proportion(0D);
    public static final Proportion HALF = new Proportion(0.5D);

    // input value is abs, clear at zero, and clamped to [0,1], Nan is allowed
    public Proportion {
        k = Domain.ABS0.filter(k);
        k = Math.clamp(k, DoubleUtil.ZERO, DoubleUtil.ONE);
    }

    public static @NonNull Proportion ratio(final @NonNull Double proportion, final @NonNull Double whole) {
        return new Proportion(proportion / whole);
    }

    @Override
    public boolean equals(final @Self Object o) {
        return o instanceof Proportion(Double ok) && k == ok;
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(k);
    }

    @Override
    public int compareTo(final @NonNull Proportion o) {
        return DoubleUtil.compareTo(k, o.k);
    }

    public @NonNull Proportion inv() {
        return new Proportion(identity - k);
    }
    public Double times(Double o) {
        return k * o;
    }

    public boolean identity() {
        return k == identity;
    }
}
