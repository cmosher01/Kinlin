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

//        val actual = w * 3D; //COMPILER ERROR, GOOD!
//        val actual = w * -3D; //COMPILER ERROR, GOOD!

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
