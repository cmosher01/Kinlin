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

package dev.cmosher01.geom;

import dev.cmosher01.math.doubles.*;
import lombok.NonNull;
import manifold.ext.rt.api.*;

import java.util.Objects;


// a single dimension/size/measurement/distance
// always non-negative
// no location/orientation/direction
// immutable
// not meant to be used in a set or as a key in a map
// (beware the distinction between positive and negative zero for doubles: here we always store zero as positive)
// (beware can be infinite (positive) or NaN)
// DOMAIN OF ALLOWABLE VALUES: NaN, positive zero, any positive normal double number
// NON-ALLOWABLE VALUES: negative zero, any negative normal double number, negative infinity, positive infinity
public record Dim(@NonNull Double n = DoubleUtil.ZERO) implements ComparableUsing<Dim> {
    public static final Dim NULL = new Dim(Double.NaN);
    public static final Dim ZERO = new Dim();
    public static final Dim ONE = new Dim(DoubleUtil.ONE);

    public Dim {
//        n = Math.clamp(n, 0D, Double.POSITIVE_INFINITY);
        n = Domain.ABS0_FIN.filter(n);
    }

    @Override
    public boolean equals(final @Self Object o) {
        return o instanceof Dim(Double on) && n == on;
    }
    @Override
    public int hashCode() {
        return Objects.hash(n);
    }
    @Override
    public int compareTo(final @NonNull Dim that) {
        return DoubleUtil.compareTo(this.n, that.n);
    }

    // Dim <- Dim + GrowTerm
    public @NonNull Dim plus(final @NonNull GrowTerm dn) {
        return new Dim(dn + n);
    }
    // Dim <- Dim - GrowTerm
    public @NonNull Dim minus(final @NonNull GrowTerm dn) {
        return plus(-dn);
    }

    // Dim <- Dim + Dim (add two dimensions)
    public @NonNull Dim plus(final @NonNull Dim that) {
        return new Dim(this.n + that.n);
    }
    // Dim <- Dim - Dim (note: this operation is commutative)
    public @NonNull Dim minus(final @NonNull Dim that) {
        return new Dim(this.n - that.n);
    }

    // Dim <- Dim * ScaleFactor
    public @NonNull Dim times(final @NonNull ScaleFactor k) {
        return new Dim(k * n);
    }
    // Dim <- Dim / ScaleFactor
    public @NonNull Dim div(final @NonNull ScaleFactor k) {
        return times(~k);
    }
    // ScaleFactor <- Dim / Dim
    public @NonNull ScaleFactor div(final @NonNull Dim that) {
        return new ScaleFactor(this.n / that.n);
    }

    public @NonNull Dim half() {
        return ScaleFactor.HALF * this;
    }

    public boolean zero() {
        return this == ZERO;
    }
}
