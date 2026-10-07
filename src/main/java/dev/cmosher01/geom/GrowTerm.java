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


import dev.cmosher01.math.doubles.*;
import lombok.NonNull;

/**
 * Represents a change in size: grow or shrink.
 * Must be finite.
 * Different from a MoveTerm, for example...
 * A rectangle can "move" without restriction,
 * but it can only "shrink" down to a minimum of zero.
 *
 * @param d default 0
 */
public record GrowTerm(@NonNull Double d = identity) {
    private static final @NonNull Double identity = DoubleUtil.ZERO;
    public static final @NonNull GrowTerm IDENTITY = new GrowTerm();
    public static final @NonNull GrowTerm NULL = new GrowTerm(Double.NaN);

    public GrowTerm {
        d = Domain.FIN.filter(d);
    }

    public boolean identity() {
        return d == identity;
    }
    public @NonNull GrowTerm abs() {
        return new GrowTerm(Math.abs(d));
    }
    // GrowTerm = -GrowTerm
    public @NonNull GrowTerm unaryMinus() {
        return new GrowTerm(-d);
    }
    // Double = Double + GrowTerm
    public @NonNull Double plus(final @NonNull Double n) {
        // guard against shrinking to below zero (dimensions can't be negative)
        return Math.max(0, d + n);
    }

    // don't need operator minus
    // we can do "Double = Double + -GrowTerm"
    // we don't want to allow "Double = GrowTerm - Double"
    // and we can't do "Double = Double - GrowTerm" because that would be Double.minus(GrowTerm)
}
