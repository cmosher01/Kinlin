package dev.cmosher01.geom;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// mostly tested by other classes that can be translated
public class TestXlation {
    @Test
    void nominal() {
        val xlat3by3 = new Xlation(new MoveTerm(3.0));
        val xlatNeg3by3 = new Xlation(new MoveTerm(-3.0));
        assertEquals(xlatNeg3by3, -xlat3by3);

        assertEquals(xlat3by3, xlatNeg3by3.abs());
        assertEquals(xlat3by3, xlat3by3.abs());

        assertTrue(Xlation.IDENTITY.identity());
    }
}
