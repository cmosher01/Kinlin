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

public class TestCoord {
    @Test
    public void nominal() {
        val x1 = new Coord(.1D+.2D);
        val x2 = new Coord(.3D);
        assertTrue(x1 == x2);
        assertTrue(x2 == x1);
    }

    @Test
    public void origin() {
        val x = Coord.ORIGIN;
        assertTrue(Coord.ORIGIN.origin());
        val xe = new Coord(Math.abs((.1D+.2D)-.3D));
        assertTrue(xe.origin());
    }

    @Test
    void compare() {
        val a = new Coord(433D);

        assertFalse(a < a);
        assertTrue(a <= a);
        assertFalse(a > a);
        assertTrue(a >= a);


        val b = new Coord(197D);

        assertTrue(b < a);
        assertTrue(b <= a);
        assertFalse(b > a);
        assertFalse(b >= a);

        assertFalse(a < b);
        assertFalse(a <= b);
        assertTrue(a > b);
        assertTrue(a >= b);
    }

    @Test
    public void move() {
        val x = Coord.ORIGIN;
//        val actual = w + 3D; //COMPILER ERROR, GOOD!
//        val actual * (dx + new MoveTerm(.3D)); //COMPILER ERROR, GOOD!
        val actual = x + new MoveTerm(.3D);
        val expected = new Coord(.3D);
        assertTrue(expected == actual);
        assertTrue(actual == expected);
        assertEquals(expected, actual);
        assertEquals(actual, expected);

        assertEquals(expected, new Coord(.1D+.2D));
        assertTrue(expected == new Coord(.1D+.2D));
        assertEquals(new Coord(.1D+.2D), expected);
        assertTrue(new Coord(.1D+.2D) == expected);
    }

    @Test
    public void scale() {
        val x = new Coord(.1D);

//        val actual = x * 3D; //COMPILER ERROR, GOOD!
//        val actual = x * -3D; //COMPILER ERROR, GOOD!
//        val actual = x * new GrowTerm(3D); //COMPILER ERROR, GOOD!
//        val acutal = x + Outset.IDENTITY; //COMPILER ERROR, GOOD!
//        val actual = x + ScaleFactor.IDENTITY; //COMPILER ERROR, GOOD!

        val actual = x * new ScaleFactor(3.0); // creates new ScaleFactor and applies it to w
        val expected = new Coord(.3);
        assertTrue(expected == actual);
        assertTrue(actual == expected);
        assertEquals(expected, actual);
        assertEquals(actual, expected);
    }

    @Test
    void minus() {
        val a = new Coord(10.0);
        val b = new Coord(33.0);
        val d = b - a;
        assertEquals(23.0, d.d());
    }
}
