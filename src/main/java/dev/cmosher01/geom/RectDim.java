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

import lombok.NonNull;
import manifold.ext.rt.api.*;

import java.util.Objects;

// dimensions (size/measurement/distance) (width, height) of a rectangular area
// this differs from a rectangle by not having a position (location)
public record RectDim(@NonNull Dim w = Dim.ZERO, @NonNull Dim h = w) implements ComparableUsing<RectDim> {
    public static RectDim ZERO = new RectDim();
    public static RectDim UNIT = new RectDim(Dim.ONE);

    @Override
    public boolean equals(final @Self Object o) {
        return o instanceof RectDim(Dim ow, Dim oh) && w == ow && h == oh;
    }
    @Override
    public int hashCode() {
        return Objects.hash(w, h);
    }
    @Override
    public int compareTo(final @NonNull RectDim o) {
        throw new UnsupportedOperationException();
    }

// will we need these? should there be static methods in Geom for these formulas?
//    public Dim perim() {
//        return ScaleFactor.TWICE * (w+h);
//    }
// should this return DimSq:
//    public FP area() {
//        return fp(w.d().n() * h.d().n());
//    }

//    // this is not needed, but is it nice to have?
//    public static RectDim square(final @NonNull Dim n) {
//        return new RectDim(n);
//    }

    public Dim diag() {
        return new Dim(Math.hypot(w.n, h.n));
    }

    public boolean zero() {
        return this == ZERO;
    }
    public boolean empty() {
        return w.zero() || h.zero();
    }

    public boolean congruent(final @NonNull RectDim that) {
        return this == that;
    }

    // RectDim <- RectDim + Outset (grow/shrink)
    public @NonNull RectDim plus(final @NonNull Outset d) {
        return new RectDim(w + d.w, h + d.h);
    }
    // RectDim <- RectDim - Outset (grow/shrink)
    public @NonNull RectDim minus(final @NonNull Outset d) {
        return plus(-d);
    }

    // RectDim <- RectDim + RectDim (add widths, add heights)
    public @NonNull RectDim plus(final @NonNull RectDim that) {
        return new RectDim(this.w + that.w, this.h + that.h);
    }
    // RectDim <- RectDim - RectDim (note: this operation is commutative)
    public @NonNull RectDim minus(final @NonNull RectDim that) {
        return new RectDim(this.w - that.w, this.h - that.h);
    }

    // RectDim <- RectDim * ScaleFactor
    public @NonNull RectDim times(final @NonNull ScaleFactor k) {
        return new RectDim(k * w, k * h);
    }
    // RectDim <- RectDim / ScaleFactor
    public @NonNull RectDim div(final @NonNull ScaleFactor k) {
        return times(~k);
    }
}
