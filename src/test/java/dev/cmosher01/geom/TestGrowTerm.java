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
    }
}
