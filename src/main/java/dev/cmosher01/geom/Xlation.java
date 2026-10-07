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

public record Xlation(@NonNull MoveTerm dx = MoveTerm.IDENTITY, @NonNull MoveTerm dy = dx) {
    public static final Xlation IDENTITY = new Xlation();

    public @NonNull Xlation unaryMinus() {
        return new Xlation(-dx, -dy);
    }
    public boolean identity() {
        return dx.identity() && dy.identity();
    }
    public @NonNull Xlation abs() {
        return new Xlation(dx.abs(), dy.abs());
    }
}
