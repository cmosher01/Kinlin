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
import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertTrue(RectDim.ZERO.zero());
    }

    @Test
    void empty() {
        assertTrue(RectDim.ZERO.empty());
        assertTrue((RectDim.ZERO + new Outset(GrowTerm.IDENTITY, new GrowTerm(1D))).empty());
        assertTrue((RectDim.ZERO + new Outset(new GrowTerm(1D), GrowTerm.IDENTITY)).empty());
    }

    @Test
    void unit() {
        assertFalse(RectDim.UNIT.empty());
        assertFalse(RectDim.UNIT.zero());
        assertEquals(1, RectDim.UNIT.w.n);
        assertEquals(Dim.ONE, RectDim.UNIT.w);
        assertEquals(1, RectDim.UNIT.h.n);
        assertEquals(Dim.ONE, RectDim.UNIT.h);
        assertEquals(new Dim(Math.sqrt(2)), RectDim.UNIT.diag());
    }

    @Test
    void minus() {
        assertEquals(RectDim.UNIT, RectDim.UNIT - RectDim.ZERO);
        assertEquals(RectDim.UNIT, RectDim.ZERO - RectDim.UNIT);
    }

    @Test
    void scale() {
        val one = RectDim.UNIT;
        val k = new ScaleFactor(3D);
        assertEquals(new RectDim(new Dim(3D)), k * one);
    }
}
