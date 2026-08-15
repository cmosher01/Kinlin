package dev.cmosher01.geom;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestOutset {
    @Test
    void nominal() {
        val grow3by3 = new Outset(new GrowTerm(3.0));
        val growNeg3by3 = new Outset(new GrowTerm(-3.0));
        assertEquals(growNeg3by3, -grow3by3);

        assertEquals(grow3by3, growNeg3by3.abs());
        assertEquals(grow3by3, grow3by3.abs());

        assertTrue(Outset.IDENTITY.identity());
    }
}
