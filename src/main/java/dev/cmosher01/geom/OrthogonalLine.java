package dev.cmosher01.geom;

import lombok.NonNull;

public record OrthogonalLine(@NonNull Orient or, @NonNull Coord a, @NonNull Coord b, @NonNull Coord pos = Coord.ORIGIN) {
    public enum Orient {H, V}

    public @NonNull OrthogonalLine scale(final @NonNull ScaleFactor k) {
        return new OrthogonalLine(or, k * a, k * b, k * pos);
    }
    public @NonNull OrthogonalLine translate(final @NonNull Xlation d) {
        return switch (or) {
            case H -> new OrthogonalLine(or, a + d.dx, b + d.dx, pos + d.dy);
            case V -> new OrthogonalLine(or, a + d.dy, b + d.dy, pos + d.dx);
        };
    }
}
