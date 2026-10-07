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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestGrowTerm {
    static class ClientOfUut {
        double i;
        ClientOfUut(double j) {
            i = j;
        }
        void add(GrowTerm d) {
            i += d.d;
        }
    }

    @Test
    void nominal() {
        val a = 59D;
        val b = 924D;
        assertEquals(b, a + new GrowTerm(865D));
        assertEquals(b, new GrowTerm(865D) + a);

        val d = 865D;
        assertEquals(b, a + new GrowTerm(d));
        assertEquals(b, new GrowTerm(d) + a);

        assertEquals(new GrowTerm(-5D), - new GrowTerm(5D));
//        assertEquals(d, b - new GrowTerm(a)); // doesn't compile
        assertEquals(d, b + - new GrowTerm(a));
        assertEquals(d, b + new GrowTerm(-a));

        assertEquals(new GrowTerm(37D), new GrowTerm(-37D).abs());

        val client = new ClientOfUut(35000);
        client.add(new GrowTerm(892D));
        assertEquals(35892D, client.i);
    }

    @Test
    void bad() {
        val nan = new GrowTerm(Double.NaN);
        assertTrue(nan.d.isNaN());
        assertTrue((1D + nan).isNaN());
        assertTrue((1D + - nan).isNaN());
        assertTrue((-nan).d().isNaN());
        assertTrue((nan + 1D).isNaN());

        assertEquals(nan, new GrowTerm(Double.POSITIVE_INFINITY));
        assertEquals(nan, new GrowTerm(Double.NEGATIVE_INFINITY));
    }
}
