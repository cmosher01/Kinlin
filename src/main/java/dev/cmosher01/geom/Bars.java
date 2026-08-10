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
