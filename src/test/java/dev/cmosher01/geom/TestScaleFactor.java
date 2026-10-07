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


import lombok.val;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestScaleFactor {
    @Test
    public void nominal() {
        // let k be a scale factor of 27 times:
        val k = new ScaleFactor(27.0);

        final double input = 7;
        final double expected = 27D * 7D;
        assertEquals(expected, k.k * input);
        assertEquals(expected, input * k.k);
    }

    @Test
    void ident() {
        assertTrue(ScaleFactor.IDENTITY.identity());

        val passthru = 39D;
        assertEquals(passthru, ScaleFactor.IDENTITY.k * passthru);
    }

    @Test
    void absOfNeg() {
        val expected = new ScaleFactor(3.0);
        val actual = new ScaleFactor(-3.0);
        assertEquals(expected, actual);
    }

    @Test
    void illegalInf() {
        assertEquals(Double.NaN, new ScaleFactor(Double.POSITIVE_INFINITY).k());
        assertEquals(Double.NaN, new ScaleFactor(Double.NEGATIVE_INFINITY).k());
    }

    @Test
    void inverse() {
        val three = new ScaleFactor(3D);
        val oneThird = ~three;
        assertEquals(3D, oneThird * 9D);
    }
}
