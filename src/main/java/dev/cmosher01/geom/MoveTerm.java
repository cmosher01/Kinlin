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


import dev.cmosher01.math.doubles.DoubleUtil;
import lombok.NonNull;

/**
 * Represents a movement (change in location). A one-dimensional translation.
 * Must be finite.
 *
 * @param d default 0
 */
public record MoveTerm(@NonNull Double d = identity) {
    private static final @NonNull Double identity = DoubleUtil.ZERO;
    public static final @NonNull MoveTerm IDENTITY = new MoveTerm();

    public MoveTerm {
        d = Domain.FIN.filter(d);
    }

    public boolean identity() {
        return d == identity;
    }
    public @NonNull MoveTerm abs() {
        return new MoveTerm(Math.abs(d));
    }

    public @NonNull MoveTerm unaryMinus() {
        return new MoveTerm(-d);
    }
    public @NonNull Double plus(final @NonNull Double n) {
        return d + n;
    }
    public @NonNull Double minus(final @NonNull Double n) {
        return d - n;
    }
}
