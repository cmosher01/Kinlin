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

package dev.cmosher01.math.doubles;

import static dev.cmosher01.math.doubles.DoubleUtil.*;
import static java.lang.Double.NaN;
import static java.lang.Math.abs;

import lombok.*;

/*
    ranges based on three qualities:
    note: NaN is always allowed
    0 = includes zero(es)
    + = absolute (only non-negative)
    ∞ = includes infinite(s)

    +	0	∞

    t	t	t	[+0,+∞]	abs0
    t	t	f	[+0,+∞)	abs0_fin

    t	f	t	(+0,+∞]	abs
    t	f	f	(+0,+∞)	abs_fin

    f	t	t	[-∞,+∞]	any
    f	t	f	(-∞,+∞)	fin

    f	f	t	[-∞,-0)+(+0,+∞]	non0
    f	f	f	(-∞,-0)+(+0,+∞)	non0_fin
 */
public class Domain {
    public static final @NonNull Domain ABS0     = new Domain(true ,true ,true );
    public static final @NonNull Domain ABS0_FIN = new Domain(true ,true ,false);
    public static final @NonNull Domain ABS      = new Domain(true ,false,true );
    public static final @NonNull Domain ABS_FIN  = new Domain(true ,false,false);
    public static final @NonNull Domain ANY      = new Domain(false,true ,true );
    public static final @NonNull Domain FIN      = new Domain(false,true ,false);
    public static final @NonNull Domain NON0     = new Domain(false,false,true );
    public static final @NonNull Domain NON0_FIN = new Domain(false,false,false);

    private final boolean absolute;
    private final boolean zero;
    private final boolean infinite;

    public Domain(boolean absolute, boolean zero, boolean infinite) {
        this.absolute = absolute;
        this.zero = zero;
        this.infinite = infinite;
    }

    public Double filter(@NonNull Double n) {
        if (n == ZERO) {
            n = this.zero ? clearAtZero(n) : NaN;
        }
        if (this.absolute) {
            n = abs(n);
        }
        if (n.isInfinite()) {
            n = this.infinite ? n : NaN;
        }
        return n;
    }

//    public boolean wouldFail(@NonNull Double n) {
//    }
//
//    public boolean wouldPassThru(@NonNull Double n) {
//    }
//
//    public boolean wouldPassChanged(@NonNull Double n) {
//    }
}
