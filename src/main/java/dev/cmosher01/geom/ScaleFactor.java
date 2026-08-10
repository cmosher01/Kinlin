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

import static dev.cmosher01.geom.Domain.ABS0_FIN;

/**
 * Represents a scaling factor.
 *
 * @param k scaling factor; must be: zero, a finite positive number, or NaN;
 *          cannot be negative, or infinite
 *          default 1
 */
public record ScaleFactor(@NonNull Double k = identity) {
    private static final @NonNull Double identity = DoubleUtil.ONE;
    public static final @NonNull ScaleFactor IDENTITY = new ScaleFactor();
    public static final @NonNull ScaleFactor HALF = new ScaleFactor(0.5D);
    public static final @NonNull ScaleFactor TWICE = new ScaleFactor(2.0D);

    public ScaleFactor {
        k = ABS0_FIN.filter(k);
    }

    public boolean identity() {
        return k == identity;
    }

    public @NonNull ScaleFactor inv() {
        return new ScaleFactor(~k);
    }
    public @NonNull Double times(final @NonNull Double n) {
        return k * n;
    }
    public @NonNull Double div(final @NonNull Double n) {
        return times(~n);
    }
}
