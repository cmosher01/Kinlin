package dev.cmosher01.geom;

import lombok.val;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestPoint {
    @Test
    void nominal() {
        val a = new Point(new Coord(883.0), new Coord(-3049.0));
        assertTrue(883.0 == a.x().u());
        assertTrue(-3049.0 == a.y().u());
    }

    @Test
    void near() {
        val a = new Point(Coord.ORIGIN, new Coord(.1+.2));
        val b = new Point(Coord.ORIGIN, new Coord(.3));
        assertFalse(.1D+.2D == .3D);
        assertTrue(a == b);
    }

    @Test
    void equality() {
        val a = new Point(new Coord(883.0), new Coord(-3049.0));
        val b = new Point(new Coord(883.0), new Coord(-3049.0));
        assertTrue(Point.ORIGIN == Point.ORIGIN);
        assertTrue(a == a);
        assertTrue(a == b);
        assertTrue(b == a);
        assertTrue(b == b);
        assertFalse(a != a);
        assertFalse(a != b);
        assertFalse(b != a);
        assertFalse(b != b);

        val c = new Point(new Coord(877.0), new Coord(-3049.0));
        assertFalse(a == c);
        assertFalse(c == a);
        assertTrue(c == c);
        assertTrue(a != c);
        assertTrue(c != a);
        assertFalse(c != c);

        val d = new Point(new Coord(883.0), new Coord(-3037.0));
        assertFalse(a == d);
        assertFalse(d == a);
        assertTrue(d == d);
        assertTrue(a != d);
        assertTrue(d != a);
        assertFalse(d != d);
    }

    @Test
    void cantCompare() {
        assertThrows(RuntimeException.class, () -> {
            val illegal = Point.ORIGIN < Point.ORIGIN;
        });
    }

    @Test
    void scale() {
        val a = new Point(new Coord(100.0), new Coord(100.0));
        val k = new ScaleFactor(20.0);
        assertEquals(new Point(new Coord(2000.0), new Coord(2000.0)), k * a);
        assertEquals(new Point(new Coord(5.0), new Coord(5.0)), a / k);
    }

    @Test
    void translate() {
        val a = new Point(new Coord(100.0), new Coord(100.0));
        val da = new Xlation(new MoveTerm(33.0), new MoveTerm(66.0));
        assertEquals(new Point(new Coord(133.0), new Coord(166.0)), a + da);
        assertEquals(new Point(new Coord(67.0), new Coord(34.0)), a - da);

        val b = new Point(new Coord(103.0), new Coord(103.0));
        assertEquals(new Xlation(new MoveTerm(3.0), new MoveTerm(3.0)), b - a);
        assertEquals(new Xlation(new MoveTerm(-3.0), new MoveTerm(-3.0)), a - b);
    }
}
