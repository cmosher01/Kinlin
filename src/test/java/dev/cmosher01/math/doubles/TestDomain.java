package dev.cmosher01.math.doubles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestDomain {
    private static final Double N = Double.NaN;

    private static void t(Double expected, Double input, Domain uut) {
        assertEquals(expected, uut.filter(input));
    }

    @Test
    void test() {
        assertNotEquals(DoubleUtil.POSITIVE_ZERO, DoubleUtil.NEGATIVE_ZERO);
        assertNotEquals(DoubleUtil.NEGATIVE_ZERO, DoubleUtil.POSITIVE_ZERO);
        assertEquals(N, N);
        assertNotEquals(N, DoubleUtil.POSITIVE_ZERO);
    }

    /*
    neginf
    neg
    negnear0
    neg0
    pos0
    posnear0
    pos
    posinf
    nan
     */
    @Test
    void pos() {
        final Double n = 37D;

        // everything passes unchanged
        t(n, n, Domain.ABS0    );
        t(n, n, Domain.ABS0_FIN);
        t(n, n, Domain.ABS     );
        t(n, n, Domain.ABS_FIN );
        t(n, n, Domain.ANY     );
        t(n, n, Domain.FIN     );
        t(n, n, Domain.NON0    );
        t(n, n, Domain.NON0_FIN);
    }

    @Test
    void neg() {
        final Double n = -37D;
        final Double p = 37D;
        assertEquals(n, -p);
        assertEquals(p, -n);
        assertEquals(p, Math.abs(n));
        assertEquals(p, Math.abs(p));

        // absolute domains change neg input to pos
        t(p, n, Domain.ABS0    );
        t(p, n, Domain.ABS0_FIN);
        t(p, n, Domain.ABS     );
        t(p, n, Domain.ABS_FIN );
        t(n, n, Domain.ANY     );
        t(n, n, Domain.FIN     );
        t(n, n, Domain.NON0    );
        t(n, n, Domain.NON0_FIN);
    }

    @Test
    void pos0() {
        final Double p = DoubleUtil.POSITIVE_ZERO;

        // non-zero domains fail on pos zero input
        t(p, p, Domain.ABS0    );
        t(p, p, Domain.ABS0_FIN);
        t(N, p, Domain.ABS     );
        t(N, p, Domain.ABS_FIN );
        t(p, p, Domain.ANY     );
        t(p, p, Domain.FIN     );
        t(N, p, Domain.NON0    );
        t(N, p, Domain.NON0_FIN);
    }

    @Test
    void posnear0() {
        final Double p = 1e-8;
        final Double pz = DoubleUtil.POSITIVE_ZERO;

        assertTrue(p == DoubleUtil.ZERO);
        assertTrue(DoubleUtil.ZERO == p);

        // non-zero domains fail on pos near zero input
        // absolute domains change pos near zero input to exactly pos zero
        t(pz, p, Domain.ABS0    );
        t(pz, p, Domain.ABS0_FIN);
        t(N , p, Domain.ABS     );
        t(N , p, Domain.ABS_FIN );
        t(pz, p, Domain.ANY     );
        t(pz, p, Domain.FIN     );
        t(N , p, Domain.NON0    );
        t(N , p, Domain.NON0_FIN);
    }

    @Test
    void neg0() {
        final Double n = DoubleUtil.NEGATIVE_ZERO;
        final Double p = DoubleUtil.POSITIVE_ZERO;
        assertEquals(n, -p);
        assertEquals(p, -n);
        assertEquals(p, Math.abs(n));
        assertEquals(p, Math.abs(p));

        // non-zero domains fail on pos zero input
        // absolute domains change neg zero to pos zero
        t(p, n, Domain.ABS0    );
        t(p, n, Domain.ABS0_FIN);
        t(N, n, Domain.ABS     );
        t(N, n, Domain.ABS_FIN );
        t(n, n, Domain.ANY     );
        t(n, n, Domain.FIN     );
        t(N, n, Domain.NON0    );
        t(N, n, Domain.NON0_FIN);
    }

    @Test
    void negnear0() {
        final Double n = -1e-8;
        final Double pz = DoubleUtil.POSITIVE_ZERO;
        final Double nz = DoubleUtil.NEGATIVE_ZERO;

        // near zero inputs are filtered to exact zero
        // non-zero domains fail on neg near zero input
        // absolute domains change neg input to pos
        t(pz, n, Domain.ABS0    );
        t(pz, n, Domain.ABS0_FIN);
        t(N , n, Domain.ABS     );
        t(N , n, Domain.ABS_FIN );
        t(nz, n, Domain.ANY     );
        t(nz, n, Domain.FIN     );
        t(N , n, Domain.NON0    );
        t(N , n, Domain.NON0_FIN);
    }

    @Test
    void posinf() {
        final Double n = Double.POSITIVE_INFINITY;

        t(n, n, Domain.ABS0    );
        t(N, n, Domain.ABS0_FIN);
        t(n, n, Domain.ABS     );
        t(N, n, Domain.ABS_FIN );
        t(n, n, Domain.ANY     );
        t(N, n, Domain.FIN     );
        t(n, n, Domain.NON0    );
        t(N, n, Domain.NON0_FIN);
    }

    @Test
    void neginf() {
        final Double n = Double.NEGATIVE_INFINITY;
        final Double p = Double.POSITIVE_INFINITY;
        assertEquals(n, -p);
        assertEquals(p, -n);
        assertEquals(p, Math.abs(n));
        assertEquals(p, Math.abs(p));

        t(p, n, Domain.ABS0    );
        t(N, n, Domain.ABS0_FIN);
        t(p, n, Domain.ABS     );
        t(N, n, Domain.ABS_FIN );
        t(n, n, Domain.ANY     );
        t(N, n, Domain.FIN     );
        t(n, n, Domain.NON0    );
        t(N, n, Domain.NON0_FIN);
    }

    @Test
    void nan() {
        final Double n = N;

        t(n, n, Domain.ABS0    );
        t(n, n, Domain.ABS0_FIN);
        t(n, n, Domain.ABS     );
        t(n, n, Domain.ABS_FIN );
        t(n, n, Domain.ANY     );
        t(n, n, Domain.FIN     );
        t(n, n, Domain.NON0    );
        t(n, n, Domain.NON0_FIN);
    }
}
