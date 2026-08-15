package dev.cmosher01.geom;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMoveTerm {
    @Test
    void nominal() {
        val x = 13_049D;
        val dx = new MoveTerm(40_897D);
        assertEquals(53_946D, x + dx);

        val x2 = 983_389D;
        assertEquals(942_492D, x2 + -dx);
    }
}
