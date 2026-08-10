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

public record Outset(@NonNull GrowTerm w = GrowTerm.IDENTITY, @NonNull GrowTerm h = w) {
    public static final Outset IDENTITY = new Outset();

    public @NonNull Outset unaryMinus() {
        return new Outset(-w, -h);
    }

    public boolean identity() {
        return w.identity() && h.identity();
    }

    public @NonNull Outset abs() {
        return new Outset(w.abs(), h.abs());
    }
}
