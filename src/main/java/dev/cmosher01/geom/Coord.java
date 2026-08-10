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

import lombok.NonNull;
import manifold.ext.rt.api.*;

import java.util.Objects;

public record Coord(@NonNull Double u = 0) implements ComparableUsing<Coord> {
    public static final @NonNull Coord NULL = new Coord(Double.NaN);
    public static final @NonNull Coord ORIGIN = new Coord();

    @Override
    public boolean equals(final @Self Object o) {
        return o instanceof Coord(Double ou) && u == ou;
    }
    @Override
    public int hashCode() {
        return Objects.hash(u);
    }
    @Override
    public int compareTo(final @NonNull Coord that) {
        return Double.compare(this.u, that.u);
    }


    // Coord <- -Coord
    public @NonNull Coord unaryMinus() {
        return new Coord(-u);
    }

    // Coord <- Coord + MoveTerm
    public @NonNull Coord plus(final @NonNull MoveTerm du) {
        return new Coord(du + u);
    }
    // Coord <- Coord - MoveTerm
    public @NonNull Coord minus(final @NonNull MoveTerm du) {
        return new Coord(-du + u);
    }
    // MoveTerm <- Coord - Coord
    public @NonNull MoveTerm minus(final @NonNull Coord du) {
        return new MoveTerm(u - du.u);
    }

    // Coord <- Coord * ScaleFactor
    public @NonNull Coord times(final @NonNull ScaleFactor k) {
        return new Coord(k * u);
    }
    // Coord <- Coord / ScaleFactor
    public @NonNull Coord div(final @NonNull ScaleFactor k) {
        return new Coord(k / u);
    }
    // ScaleFactor <- Coord / Coord
    public @NonNull ScaleFactor div(final @NonNull Coord k) {
        return new ScaleFactor(k.u / u);
    }

    public boolean origin() {
        return this == ORIGIN;
    }
}
