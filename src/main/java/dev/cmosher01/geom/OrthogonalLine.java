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

package dev.cmosher01.geom;

import lombok.*;

import java.awt.*;
import java.util.Collection;
import java.util.stream.DoubleStream;

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

    public @NonNull Point midpoint() {
        val m = new Coord((a.u + b.u) / 2D);
        final Point ret;
        if (or == Orient.H) {
            ret = new Point(m, pos);
        } else {
            ret = new Point(pos, m);
        }
        return ret;
    }

    public Dim length() {
        return new Dim((b-a).d());
    }



    public static @NonNull Rect bounds(final @NonNull Collection<OrthogonalLine> lines) {
        val sx = lines.stream()
            .flatMapToDouble(l -> l.or == Orient.H ? DoubleStream.of(l.a.u, l.b.u) : DoubleStream.of(l.pos.u))
            .summaryStatistics();

        // Collect all Y coordinates
        val sy = lines.stream()
            .flatMapToDouble(l -> l.or == Orient.H ? DoubleStream.of(l.pos.u) : DoubleStream.of(l.a.u, l.b.u))
            .summaryStatistics();

        val x = new Coord((sx.getMin() + sx.getMax())/2);
        val y = new Coord((sy.getMin() + sy.getMax())/2);
        val center = new Point(x, y);
        val w = new Dim(sx.getMax() - sx.getMin());
        val h = new Dim(sy.getMax() - sy.getMin());
        val dim = new RectDim(w, h);
        return new Rect(dim, center);
    }
}
