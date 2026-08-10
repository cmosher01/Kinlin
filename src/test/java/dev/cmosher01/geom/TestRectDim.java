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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static dev.cmosher01.math.doubles.DoubleUtil.fp;

public class TestRectDim {
    @Test
    void increment() {
        assertEquals(RectDim.UNIT, RectDim.ZERO + new Outset(new GrowTerm(1D)));
    }

    @Test
    void min0() {
        assertEquals(new RectDim(Dim.ZERO, Dim.ZERO), RectDim.ZERO + new Outset(new GrowTerm(-1D)));
        assertEquals(new RectDim(Dim.ZERO, Dim.ZERO), RectDim.ZERO + new Outset(new GrowTerm(-1D), GrowTerm.IDENTITY));
        assertEquals(new RectDim(Dim.ZERO, Dim.ZERO), RectDim.ZERO + new Outset(GrowTerm.IDENTITY, new GrowTerm(-1D)));
    }

    @Test
    void zero() {
        assertTrue(RectDim.ZERO == new RectDim(Dim.ZERO, Dim.ZERO));
        assertEquals(Dim.ZERO, RectDim.ZERO.w());
        assertEquals(Dim.ZERO, RectDim.ZERO.h());
//        assertEquals(DoubleUtil.ZERO, RectDim.ZERO.perim());
//        assertEquals(DoubleUtil.ZERO, RectDim.ZERO.area());
        assertEquals(Dim.ZERO, RectDim.ZERO.diag());
    }

    @Test
    void empty() {
        assertTrue(RectDim.ZERO.empty());
        assertTrue((RectDim.ZERO + new Outset(GrowTerm.IDENTITY, new GrowTerm(1D))).empty());
        assertTrue((RectDim.ZERO + new Outset(new GrowTerm(1D), GrowTerm.IDENTITY)).empty());
        assertFalse(RectDim.UNIT.empty());
    }
}
