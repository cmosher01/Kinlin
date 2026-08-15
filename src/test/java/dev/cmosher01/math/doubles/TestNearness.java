package dev.cmosher01.math.doubles;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestNearness {
    public static boolean uut(final double a, final double b) {
        return Nearness.near(a, b);
    }

    @Test
    void smoke() {
        assertTrue(uut(.1+.2, .3));
    }

    @Test
    void test() {
        Double a0 = Double.NEGATIVE_INFINITY;
        Double b0 = -Double.MAX_VALUE;
        Double c0 = -1e154D;
        Double d0 = -1e10D;
        Double e0 = -1.0D;
        Double f0 = -Double.MIN_NORMAL;
        Double g0 = -Double.MIN_VALUE;
        Double h0 = DoubleUtil.NEGATIVE_ZERO;
        Double h1 = DoubleUtil.POSITIVE_ZERO;
        Double g1 = Double.MIN_VALUE;
        Double f1 = Double.MIN_NORMAL;
        Double e1 = +1.0D;
        Double d1 = +1e10D;
        Double c1 = +1e154D;
        Double b1 = Double.MAX_VALUE;
        Double a1 = Double.POSITIVE_INFINITY;
        Double x1 = Double.NaN;

        assertTrue(uut(a0, a0));
        assertFalse(uut(a0, b0));
        assertFalse(uut(a0, c0));
        assertFalse(uut(a0, d0));
        assertFalse(uut(a0, e0));
        assertFalse(uut(a0, f0));
        assertFalse(uut(a0, g0));
        assertFalse(uut(a0, h0));
        assertFalse(uut(a0, h1));
        assertFalse(uut(a0, g1));
        assertFalse(uut(a0, f1));
        assertFalse(uut(a0, e1));
        assertFalse(uut(a0, d1));
        assertFalse(uut(a0, c1));
        assertFalse(uut(a0, b1));
        assertFalse(uut(a0, a1));
        assertFalse(uut(a0, x1));

        assertFalse(uut(b0, a0));
        assertTrue(uut(b0, b0));
        assertFalse(uut(b0, c0));
        assertFalse(uut(b0, d0));
        assertFalse(uut(b0, e0));
        assertFalse(uut(b0, f0));
        assertFalse(uut(b0, g0));
        assertFalse(uut(b0, h0));
        assertFalse(uut(b0, h1));
        assertFalse(uut(b0, g1));
        assertFalse(uut(b0, f1));
        assertFalse(uut(b0, e1));
        assertFalse(uut(b0, d1));
        assertFalse(uut(b0, c1));
        assertFalse(uut(b0, b1));
        assertFalse(uut(b0, a1));
        assertFalse(uut(b0, x1));

        assertFalse(uut(c0, a0));
        assertFalse(uut(c0, b0));
        assertTrue(uut(c0, c0));
        assertFalse(uut(c0, d0));
        assertFalse(uut(c0, e0));
        assertFalse(uut(c0, f0));
        assertFalse(uut(c0, g0));
        assertFalse(uut(c0, h0));
        assertFalse(uut(c0, h1));
        assertFalse(uut(c0, g1));
        assertFalse(uut(c0, f1));
        assertFalse(uut(c0, e1));
        assertFalse(uut(c0, d1));
        assertFalse(uut(c0, c1));
        assertFalse(uut(c0, b1));
        assertFalse(uut(c0, a1));
        assertFalse(uut(c0, x1));

        assertFalse(uut(d0, a0));
        assertFalse(uut(d0, b0));
        assertFalse(uut(d0, c0));
        assertTrue(uut(d0, d0));
        assertFalse(uut(d0, e0));
        assertFalse(uut(d0, f0));
        assertFalse(uut(d0, g0));
        assertFalse(uut(d0, h0));
        assertFalse(uut(d0, h1));
        assertFalse(uut(d0, g1));
        assertFalse(uut(d0, f1));
        assertFalse(uut(d0, e1));
        assertFalse(uut(d0, d1));
        assertFalse(uut(d0, c1));
        assertFalse(uut(d0, b1));
        assertFalse(uut(d0, a1));
        assertFalse(uut(d0, x1));

        assertFalse(uut(e0, a0));
        assertFalse(uut(e0, b0));
        assertFalse(uut(e0, c0));
        assertFalse(uut(e0, d0));
        assertTrue(uut(e0, e0));
        assertFalse(uut(e0, f0));
        assertFalse(uut(e0, g0));
        assertFalse(uut(e0, h0));
        assertFalse(uut(e0, h1));
        assertFalse(uut(e0, g1));
        assertFalse(uut(e0, f1));
        assertFalse(uut(e0, e1));
        assertFalse(uut(e0, d1));
        assertFalse(uut(e0, c1));
        assertFalse(uut(e0, b1));
        assertFalse(uut(e0, a1));
        assertFalse(uut(e0, x1));

        assertFalse(uut(f0, a0));
        assertFalse(uut(f0, b0));
        assertFalse(uut(f0, c0));
        assertFalse(uut(f0, d0));
        assertFalse(uut(f0, e0));
        assertTrue(uut(f0, f0));
        assertTrue(uut(f0, g0));
        assertTrue(uut(f0, h0));
        assertTrue(uut(f0, h1));
        assertTrue(uut(f0, g1));
        assertTrue(uut(f0, f1));
        assertFalse(uut(f0, e1));
        assertFalse(uut(f0, d1));
        assertFalse(uut(f0, c1));
        assertFalse(uut(f0, b1));
        assertFalse(uut(f0, a1));
        assertFalse(uut(f0, x1));

        assertFalse(uut(g0, a0));
        assertFalse(uut(g0, b0));
        assertFalse(uut(g0, c0));
        assertFalse(uut(g0, d0));
        assertFalse(uut(g0, e0));
        assertTrue(uut(g0, f0));
        assertTrue(uut(g0, g0));
        assertTrue(uut(g0, h0));
        assertTrue(uut(g0, h1));
        assertTrue(uut(g0, g1));
        assertTrue(uut(g0, f1));
        assertFalse(uut(g0, e1));
        assertFalse(uut(g0, d1));
        assertFalse(uut(g0, c1));
        assertFalse(uut(g0, b1));
        assertFalse(uut(g0, a1));
        assertFalse(uut(g0, x1));

        assertFalse(uut(h0, a0));
        assertFalse(uut(h0, b0));
        assertFalse(uut(h0, c0));
        assertFalse(uut(h0, d0));
        assertFalse(uut(h0, e0));
        assertTrue(uut(h0, f0));
        assertTrue(uut(h0, g0));
        assertTrue(uut(h0, h0));
        assertTrue(uut(h0, h1));
        assertTrue(uut(h0, g1));
        assertTrue(uut(h0, f1));
        assertFalse(uut(h0, e1));
        assertFalse(uut(h0, d1));
        assertFalse(uut(h0, c1));
        assertFalse(uut(h0, b1));
        assertFalse(uut(h0, a1));
        assertFalse(uut(h0, x1));

        assertFalse(uut(h1, a0));
        assertFalse(uut(h1, b0));
        assertFalse(uut(h1, c0));
        assertFalse(uut(h1, d0));
        assertFalse(uut(h1, e0));
        assertTrue(uut(h1, f0));
        assertTrue(uut(h1, g0));
        assertTrue(uut(h1, h0));
        assertTrue(uut(h1, h1));
        assertTrue(uut(h1, g1));
        assertTrue(uut(h1, f1));
        assertFalse(uut(h1, e1));
        assertFalse(uut(h1, d1));
        assertFalse(uut(h1, c1));
        assertFalse(uut(h1, b1));
        assertFalse(uut(h1, a1));
        assertFalse(uut(h1, x1));

        assertFalse(uut(g1, a0));
        assertFalse(uut(g1, b0));
        assertFalse(uut(g1, c0));
        assertFalse(uut(g1, d0));
        assertFalse(uut(g1, e0));
        assertTrue(uut(g1, f0));
        assertTrue(uut(g1, g0));
        assertTrue(uut(g1, h0));
        assertTrue(uut(g1, h1));
        assertTrue(uut(g1, g1));
        assertTrue(uut(g1, f1));
        assertFalse(uut(g1, e1));
        assertFalse(uut(g1, d1));
        assertFalse(uut(g1, c1));
        assertFalse(uut(g1, b1));
        assertFalse(uut(g1, a1));
        assertFalse(uut(g1, x1));

        assertFalse(uut(f1, a0));
        assertFalse(uut(f1, b0));
        assertFalse(uut(f1, c0));
        assertFalse(uut(f1, d0));
        assertFalse(uut(f1, e0));
        assertTrue(uut(f1, f0));
        assertTrue(uut(f1, g0));
        assertTrue(uut(f1, h0));
        assertTrue(uut(f1, h1));
        assertTrue(uut(f1, g1));
        assertTrue(uut(f1, f1));
        assertFalse(uut(f1, e1));
        assertFalse(uut(f1, d1));
        assertFalse(uut(f1, c1));
        assertFalse(uut(f1, b1));
        assertFalse(uut(f1, a1));
        assertFalse(uut(f1, x1));

        assertFalse(uut(e1, a0));
        assertFalse(uut(e1, b0));
        assertFalse(uut(e1, c0));
        assertFalse(uut(e1, d0));
        assertFalse(uut(e1, e0));
        assertFalse(uut(e1, f0));
        assertFalse(uut(e1, g0));
        assertFalse(uut(e1, h0));
        assertFalse(uut(e1, h1));
        assertFalse(uut(e1, g1));
        assertFalse(uut(e1, f1));
        assertTrue(uut(e1, e1));
        assertFalse(uut(e1, d1));
        assertFalse(uut(e1, c1));
        assertFalse(uut(e1, b1));
        assertFalse(uut(e1, a1));
        assertFalse(uut(e1, x1));

        assertFalse(uut(d1, a0));
        assertFalse(uut(d1, b0));
        assertFalse(uut(d1, c0));
        assertFalse(uut(d1, d0));
        assertFalse(uut(d1, e0));
        assertFalse(uut(d1, f0));
        assertFalse(uut(d1, g0));
        assertFalse(uut(d1, h0));
        assertFalse(uut(d1, h1));
        assertFalse(uut(d1, g1));
        assertFalse(uut(d1, f1));
        assertFalse(uut(d1, e1));
        assertTrue(uut(d1, d1));
        assertFalse(uut(d1, c1));
        assertFalse(uut(d1, b1));
        assertFalse(uut(d1, a1));
        assertFalse(uut(d1, x1));

        assertFalse(uut(c1, a0));
        assertFalse(uut(c1, b0));
        assertFalse(uut(c1, c0));
        assertFalse(uut(c1, d0));
        assertFalse(uut(c1, e0));
        assertFalse(uut(c1, f0));
        assertFalse(uut(c1, g0));
        assertFalse(uut(c1, h0));
        assertFalse(uut(c1, h1));
        assertFalse(uut(c1, g1));
        assertFalse(uut(c1, f1));
        assertFalse(uut(c1, e1));
        assertFalse(uut(c1, d1));
        assertTrue(uut(c1, c1));
        assertFalse(uut(c1, b1));
        assertFalse(uut(c1, a1));
        assertFalse(uut(c1, x1));

        assertFalse(uut(b1, a0));
        assertFalse(uut(b1, b0));
        assertFalse(uut(b1, c0));
        assertFalse(uut(b1, d0));
        assertFalse(uut(b1, e0));
        assertFalse(uut(b1, f0));
        assertFalse(uut(b1, g0));
        assertFalse(uut(b1, h0));
        assertFalse(uut(b1, h1));
        assertFalse(uut(b1, g1));
        assertFalse(uut(b1, f1));
        assertFalse(uut(b1, e1));
        assertFalse(uut(b1, d1));
        assertFalse(uut(b1, c1));
        assertTrue(uut(b1, b1));
        assertFalse(uut(b1, a1));
        assertFalse(uut(b1, x1));

        assertFalse(uut(a1, a0));
        assertFalse(uut(a1, b0));
        assertFalse(uut(a1, c0));
        assertFalse(uut(a1, d0));
        assertFalse(uut(a1, e0));
        assertFalse(uut(a1, f0));
        assertFalse(uut(a1, g0));
        assertFalse(uut(a1, h0));
        assertFalse(uut(a1, h1));
        assertFalse(uut(a1, g1));
        assertFalse(uut(a1, f1));
        assertFalse(uut(a1, e1));
        assertFalse(uut(a1, d1));
        assertFalse(uut(a1, c1));
        assertFalse(uut(a1, b1));
        assertTrue(uut(a1, a1));
        assertFalse(uut(a1, x1));

        assertFalse(uut(x1, a0));
        assertFalse(uut(x1, b0));
        assertFalse(uut(x1, c0));
        assertFalse(uut(x1, d0));
        assertFalse(uut(x1, e0));
        assertFalse(uut(x1, f0));
        assertFalse(uut(x1, g0));
        assertFalse(uut(x1, h0));
        assertFalse(uut(x1, h1));
        assertFalse(uut(x1, g1));
        assertFalse(uut(x1, f1));
        assertFalse(uut(x1, e1));
        assertFalse(uut(x1, d1));
        assertFalse(uut(x1, c1));
        assertFalse(uut(x1, b1));
        assertFalse(uut(x1, a1));
        assertFalse(uut(x1, x1));
    }

    @Test
    @DisplayName("exact matches (identical bits)")
    void testExactMatches() {
        assertTrue(uut(DoubleUtil.POSITIVE_ZERO, DoubleUtil.POSITIVE_ZERO));
        assertTrue(uut(DoubleUtil.POSITIVE_ZERO, DoubleUtil.NEGATIVE_ZERO));
        assertTrue(uut(DoubleUtil.NEGATIVE_ZERO, DoubleUtil.POSITIVE_ZERO));
        assertTrue(uut(DoubleUtil.NEGATIVE_ZERO, DoubleUtil.NEGATIVE_ZERO));
        assertTrue(uut(1.0, 1.0));
        assertTrue(uut(-1.0, -1.0));
        assertTrue(uut(42.42, 42.42));
        assertTrue(uut(-99.99, -99.99));
        assertTrue(uut(123.456, 123.456));
        assertTrue(uut(1000.0, 1000.0));
        assertTrue(uut(-5000.5, -5000.5));
        assertTrue(uut(12345.67, 12345.67));
        assertTrue(uut(99999.0, 99999.0));
        assertTrue(uut(-123456.78, -123456.78));
        assertTrue(uut(500000.0, 500000.0));
        assertTrue(uut(-750000.5, -750000.5));
        assertTrue(uut(1000000.0, 1000000.0));
        assertTrue(uut(-2345678.9, -2345678.9));
        assertTrue(uut(5555555.55, 5555555.55));
        assertTrue(uut(-8888888.88, -8888888.88));
        assertTrue(uut(10000000.0, 10000000.0));
        assertTrue(uut(-15000000.0, -15000000.0));
        assertTrue(uut(25345678.91, 25345678.91));
        assertTrue(uut(-38475621.0, -38475621.0));
        assertTrue(uut(45000000.12, 45000000.12));
        assertTrue(uut(-52109876.54, -52109876.54));
        assertTrue(uut(60000000.0, 60000000.0));
        assertTrue(uut(-67890123.45, -67890123.45));
        assertTrue(uut(72345678.9, 72345678.9));
        assertTrue(uut(-79999999.99, -79999999.99));
        assertTrue(uut(85000000.0, 85000000.0));
        assertTrue(uut(-89123456.7, -89123456.7));
        assertTrue(uut(92500000.0, 92500000.0));
        assertTrue(uut(-95000000.11, -95000000.11));
        assertTrue(uut(98123456.78, 98123456.78));
        assertTrue(uut(-99999999.99, -99999999.99));
        assertTrue(uut(100000000.0, 100000000.0));
        assertTrue(uut(-100000000.0, -100000000.0));
        assertTrue(uut(5123456.78, 5123456.78));
        assertTrue(uut(-6123456.78, -6123456.78));
        assertTrue(uut(7123456.78, 7123456.78));
        assertTrue(uut(-8123456.78, -8123456.78));
        assertTrue(uut(9123456.78, 9123456.78));
        assertTrue(uut(-1123456.78, -1123456.78));
        assertTrue(uut(2123456.78, 2123456.78));
        assertTrue(uut(-3123456.78, -3123456.78));
        assertTrue(uut(4123456.78, 4123456.78));
        assertTrue(uut(15.12345, 15.12345));
        assertTrue(uut(-15.12345, -15.12345));
        assertTrue(uut(0.00005, 0.00005));
        assertTrue(uut(-0.00005, -0.00005));
        assertTrue(uut(77777777.77, 77777777.77));
    }

    @Test
    @DisplayName("large boundaries passing inside tolerance")
    void testLargeCoordinatesWithinTolerance() {
        assertTrue(uut(100000000.0, 100000000.8));
        assertTrue(uut(-100000000.0, -100000000.8));
        assertTrue(uut(98500000.0, 98500000.5));
        assertTrue(uut(-95200000.0, -95200000.4));
        assertTrue(uut(91000000.0, 91000000.7));
        assertTrue(uut(-88000000.0, -88000000.6));
        assertTrue(uut(84300000.0, 84300000.3));
        assertTrue(uut(-81200000.0, -81200000.5));
        assertTrue(uut(79000000.0, 79000000.4));
        assertTrue(uut(-75000000.0, -75000000.3));
        assertTrue(uut(71400000.0, 71400000.5));
        assertTrue(uut(-68200000.0, -68200000.2));
        assertTrue(uut(65000000.0, 65000000.4));
        assertTrue(uut(-61000000.0, -61000000.3));
        assertTrue(uut(58900000.0, 58900000.2));
        assertTrue(uut(-55000000.0, -55000000.1));
        assertTrue(uut(52300000.0, 52300000.3));
        assertTrue(uut(-49000000.0, -49000000.2));
        assertTrue(uut(46200000.0, 46200000.1));
        assertTrue(uut(-42000000.0, -42000000.3));
        assertTrue(uut(39500000.0, 39500000.2));
        assertTrue(uut(-35000000.0, -35000000.1));
        assertTrue(uut(32100000.0, 32100000.15));
        assertTrue(uut(-29000000.0, -29000000.08));
        assertTrue(uut(25000000.0, 25000000.12));
        assertTrue(uut(-22400000.0, -22400000.05));
        assertTrue(uut(19500000.0, 19500000.11));
        assertTrue(uut(-16000000.0, -16000000.09));
        assertTrue(uut(14200000.0, 14200000.05));
        assertTrue(uut(-11000000.0, -11000000.04));
        assertTrue(uut(9500000.0, 9500000.03));
        assertTrue(uut(-8200000.0, -8200000.04));
        assertTrue(uut(7100000.0, 7100000.02));
        assertTrue(uut(-6300000.0, -6300000.01));
        assertTrue(uut(5000000.0, 5000000.03));
        assertTrue(uut(-4200000.0, -4200000.02));
        assertTrue(uut(3100000.0, 3100000.01));
        assertTrue(uut(-2500000.0, -2500000.01));
        assertTrue(uut(1900000.0, 1900000.005));
        assertTrue(uut(-1200000.0, -1200000.003));
        assertTrue(uut(950000.0, 950000.004));
        assertTrue(uut(-75000.0, -75000.0003));
        assertTrue(uut(50000.0, 50000.0002));
        assertTrue(uut(-25000.0, -25000.0001));
        assertTrue(uut(12000.0, 12000.00005));
        assertTrue(uut(-8000.0, -8000.00004));
        assertTrue(uut(4500.0, 4500.00002));
        assertTrue(uut(-2100.0, -2100.00001));
        assertTrue(uut(1050.0, 1050.000005));
        assertTrue(uut(-500.0, -500.000001));
    }

    @Test
    @DisplayName("large boundaries failing outside tolerance")
    void testLargeCoordinatesOutsideTolerance() {
        assertFalse(uut(100000000.0, 100000001.5));
        assertFalse(uut(-100000000.0, -100000001.5));
        assertFalse(uut(95000000.0, 95000001.2));
        assertFalse(uut(-90000000.0, -90000001.1));
        assertFalse(uut(85000000.0, 85000001.3));
        assertFalse(uut(-80000000.0, -80000001.0));
        assertFalse(uut(75000000.0, 75000000.9));
        assertFalse(uut(-70000000.0, -70000000.8));
        assertFalse(uut(65000000.0, 65000000.7));
        assertFalse(uut(-60000000.0, -60000000.7));
        assertFalse(uut(55000000.0, 55000000.6));
        assertFalse(uut(-50000000.0, -50000000.6));
        assertFalse(uut(45000000.0, 45000000.5));
        assertFalse(uut(-40000000.0, -40000000.5));
        assertFalse(uut(35000000.0, 35000000.4));
        assertFalse(uut(-30000000.0, -30000000.4));
        assertFalse(uut(25000000.0, 25000000.3));
        assertFalse(uut(-20000000.0, -20000000.3));
        assertFalse(uut(15000000.0, 15000000.2));
        assertFalse(uut(-10000000.0, -10000000.2));
        assertFalse(uut(8000000.0, 8000000.15));
        assertFalse(uut(-6000000.0, -6000000.12));
        assertFalse(uut(4000000.0, 4000000.09));
        assertFalse(uut(-2000000.0, -2000000.05));
        assertFalse(uut(1000000.0, 1000000.02));
        assertFalse(uut(-900000.0, -900000.015));
        assertFalse(uut(700000.0, 700000.012));
        assertFalse(uut(-500000.0, -500000.009));
        assertFalse(uut(300000.0, 300000.006));
        assertFalse(uut(-100000.0, -100000.003));
        assertFalse(uut(80000.0, 80000.002));
        assertFalse(uut(-60000.0, -60000.0015));
        assertFalse(uut(40000.0, 40000.0009));
        assertFalse(uut(-20000.0, -20000.0005));
        assertFalse(uut(10000.0, 10000.0003));
        assertFalse(uut(-8000.0, -8000.0002));
        assertFalse(uut(6000.0, 6000.00015));
        assertFalse(uut(-4000.0, -4000.0001));
        assertFalse(uut(2000.0, 2000.00005));
        assertFalse(uut(-1000.0, -1000.00003));
        assertFalse(uut(800.0, 800.00002));
        assertFalse(uut(-600.0, -600.000015));
        assertFalse(uut(400.0, 400.00001));
        assertFalse(uut(-200.0, -200.000005));
        assertFalse(uut(100.0, 100.000003));
        assertFalse(uut(-50.0, -50.000002));
        assertFalse(uut(25.0, 25.000001));
        assertFalse(uut(-12.0, -12.0000005));
        assertFalse(uut(5.0, 5.0000002));
        assertFalse(uut(-2.0, -2.0000001));
    }

    @Test
    @DisplayName("origin boundaries passing inside absolute dead zone")
    void testNearZeroAbsoluteTolerance() {
        assertTrue(uut(0.0001, 0.0002));
        assertTrue(uut(-0.0001, 0.0002));
        assertTrue(uut(0.0003, -0.0004));
        assertTrue(uut(-0.0005, -0.0001));
        assertTrue(uut(0.00001, 0.00009));
        assertTrue(uut(-0.00002, 0.00007));
        assertTrue(uut(0.00015, -0.00025));
        assertTrue(uut(-0.00035, -0.00005));
        assertTrue(uut(0.000001, 0.000009));
        assertTrue(uut(-0.000002, 0.000007));
        assertTrue(uut(0.0004, 0.00045));
        assertTrue(uut(-0.0004, 0.00045));
        assertTrue(uut(0.00012, -0.00018));
        assertTrue(uut(-0.00022, -0.00002));
        assertTrue(uut(0.00005, 0.00015));
        assertTrue(uut(-0.00005, 0.00015));
        assertTrue(uut(0.00025, -0.00015));
        assertTrue(uut(-0.00011, -0.00019));
        assertTrue(uut(0.000005, 0.000015));
        assertTrue(uut(-0.000025, 0.000035));
        assertTrue(uut(0.00031, 0.00039));
        assertTrue(uut(-0.00031, 0.00039));
        assertTrue(uut(0.00017, -0.00013));
        assertTrue(uut(-0.00027, -0.00007));
        assertTrue(uut(0.00003, 0.00004));
        assertTrue(uut(-0.00006, 0.00002));
        assertTrue(uut(0.00021, -0.00029));
        assertTrue(uut(-0.00014, -0.00016));
        assertTrue(uut(0.00008, 0.00002));
        assertTrue(uut(-0.00007, 0.00009));
        assertTrue(uut(0.00041, 0.00049));
        assertTrue(uut(-0.00042, 0.00041));
        assertTrue(uut(0.00011, -0.00012));
        assertTrue(uut(-0.00023, -0.00004));
        assertTrue(uut(0.00007, 0.00011));
        assertTrue(uut(-0.00008, 0.00012));
        assertTrue(uut(0.00022, -0.00024));
        assertTrue(uut(-0.00013, -0.00015));
        assertTrue(uut(0.00004, 0.00006));
        assertTrue(uut(-0.00009, 0.00003));
        assertTrue(uut(0.00033, 0.00036));
        assertTrue(uut(-0.00034, 0.00032));
        assertTrue(uut(0.00019, -0.00011));
        assertTrue(uut(-0.00026, -0.00008));
        assertTrue(uut(0.00002, 0.00005));
        assertTrue(uut(-0.00004, 0.00001));
        assertTrue(uut(0.00028, -0.00022));
        assertTrue(uut(-0.00017, -0.00012));
        assertTrue(uut(0.00006, 0.00003));
        assertTrue(uut(-0.00003, 0.00008));
    }

    @Test
    @DisplayName("passing cases with infinities")
    void testUniqueInfinityPassingCases() {
        // --- 1-10: Primitive literals and basic constants ---
        assertTrue(uut(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        assertTrue(uut(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
        assertTrue(uut(Double.POSITIVE_INFINITY, 1.0 / 0.0));
        assertTrue(uut(Double.NEGATIVE_INFINITY, -1.0 / 0.0));
        assertTrue(uut(1.0 / 0.0, 1.0 / 0.0));
        assertTrue(uut(-1.0 / 0.0, -1.0 / 0.0));
        assertTrue(uut(Double.POSITIVE_INFINITY, Double.valueOf(Double.POSITIVE_INFINITY)));
        assertTrue(uut(Double.NEGATIVE_INFINITY, Double.valueOf(Double.NEGATIVE_INFINITY)));
        assertTrue(uut(Double.valueOf(Double.POSITIVE_INFINITY), Double.POSITIVE_INFINITY));
        assertTrue(uut(Double.valueOf(Double.NEGATIVE_INFINITY), Double.NEGATIVE_INFINITY));

        // --- 11-20: Unique floating-point division variations (Positive) ---
        assertTrue(uut(Double.POSITIVE_INFINITY, 2.0 / 0.0));
        assertTrue(uut(Double.POSITIVE_INFINITY, 100.0 / 0.0));
        assertTrue(uut(Double.POSITIVE_INFINITY, 99999.9 / 0.0));
        assertTrue(uut(Double.POSITIVE_INFINITY, 100000000.0 / 0.0));
        assertTrue(uut(5.0 / 0.0, Double.POSITIVE_INFINITY));
        assertTrue(uut(500.0 / 0.0, Double.POSITIVE_INFINITY));
        assertTrue(uut(12345.0 / 0.0, Double.POSITIVE_INFINITY));
        assertTrue(uut(88888888.0 / 0.0, Double.POSITIVE_INFINITY));
        assertTrue(uut(1.5 / 0.0, 2.5 / 0.0));
        assertTrue(uut(1000.0 / 0.0, 2000.0 / 0.0));

        // --- 21-30: Unique floating-point division variations (Negative) ---
        assertTrue(uut(Double.NEGATIVE_INFINITY, -2.0 / 0.0));
        assertTrue(uut(Double.NEGATIVE_INFINITY, -100.0 / 0.0));
        assertTrue(uut(Double.NEGATIVE_INFINITY, -99999.9 / 0.0));
        assertTrue(uut(Double.NEGATIVE_INFINITY, -100000000.0 / 0.0));
        assertTrue(uut(-5.0 / 0.0, Double.NEGATIVE_INFINITY));
        assertTrue(uut(-500.0 / 0.0, Double.NEGATIVE_INFINITY));
        assertTrue(uut(-12345.0 / 0.0, Double.NEGATIVE_INFINITY));
        assertTrue(uut(-88888888.0 / 0.0, Double.NEGATIVE_INFINITY));
        assertTrue(uut(-1.5 / 0.0, -2.5 / 0.0));
        assertTrue(uut(-1000.0 / 0.0, -2000.0 / 0.0));

        // --- 31-40: Local cached variable allocations ---
        double p1 = Double.POSITIVE_INFINITY;
        double n1 = Double.NEGATIVE_INFINITY;
        double p2 = DoubleUtil.ONE / DoubleUtil.POSITIVE_ZERO;
        double n2 = -DoubleUtil.ONE / DoubleUtil.POSITIVE_ZERO;

        assertTrue(uut(p1, p1));
        assertTrue(uut(n1, n1));
        assertTrue(uut(p1, p2));
        assertTrue(uut(n1, n2));
        assertTrue(uut(p2, p1));
        assertTrue(uut(n2, n1));
        assertTrue(uut(p1, Double.POSITIVE_INFINITY));
        assertTrue(uut(n1, Double.NEGATIVE_INFINITY));
        assertTrue(uut(Double.POSITIVE_INFINITY, p1));
        assertTrue(uut(Double.NEGATIVE_INFINITY, n1));

        // --- 41-50: Math multiplication and parsing aliases ---
        assertTrue(uut(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY * 2.0));
        assertTrue(uut(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY * 500.0));
        assertTrue(uut(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY * 2.0));
        assertTrue(uut(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY * 500.0));
        assertTrue(uut(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY * -1.0));
        assertTrue(uut(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY * -1.0));
        assertTrue(uut(Double.POSITIVE_INFINITY, Double.parseDouble("Infinity")));
        assertTrue(uut(Double.NEGATIVE_INFINITY, Double.parseDouble("-Infinity")));
        assertTrue(uut(Double.parseDouble("Infinity"), Double.POSITIVE_INFINITY));
        assertTrue(uut(Double.parseDouble("-Infinity"), Double.NEGATIVE_INFINITY));
    }

    @Test
    @DisplayName("hardware extremes & fault inputs")
    void testHardwareExtremes() {
        assertFalse(uut(Double.NaN, 0.0));
        assertFalse(uut(0.0, Double.NaN));
        assertFalse(uut(Double.NaN, Double.NaN));
        assertFalse(uut(Double.POSITIVE_INFINITY, 100000000.0));
        assertFalse(uut(-100000000.0, Double.NEGATIVE_INFINITY));
        assertFalse(uut(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));
        assertTrue(uut(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        assertTrue(uut(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
        assertFalse(uut(Double.NaN, 1.1));
        assertFalse(uut(Double.NaN, -2.2));
        assertFalse(uut(Double.NaN, 33.3));
        assertFalse(uut(Double.NaN, -44.4));
        assertFalse(uut(Double.NaN, 555.5));
        assertFalse(uut(Double.NaN, -666.6));
        assertFalse(uut(Double.NaN, 7777.7));
        assertFalse(uut(Double.NaN, -8888.8));
        assertFalse(uut(Double.NaN, 99999.9));
        assertFalse(uut(Double.NaN, -12345.6));
        assertFalse(uut(1.1, Double.NaN));
        assertFalse(uut(-2.2, Double.NaN));
        assertFalse(uut(33.3, Double.NaN));
        assertFalse(uut(-44.4, Double.NaN));
        assertFalse(uut(555.5, Double.NaN));
        assertFalse(uut(-666.6, Double.NaN));
        assertFalse(uut(7777.7, Double.NaN));
        assertFalse(uut(-8888.8, Double.NaN));
        assertFalse(uut(99999.9, Double.NaN));
        assertFalse(uut(-12345.6, Double.NaN));
        assertFalse(uut(Double.POSITIVE_INFINITY, 12.34));
        assertFalse(uut(Double.POSITIVE_INFINITY, -56.78));
        assertFalse(uut(Double.POSITIVE_INFINITY, 910.11));
        assertFalse(uut(Double.POSITIVE_INFINITY, -1213.14));
        assertFalse(uut(Double.POSITIVE_INFINITY, 151617.0));
        assertFalse(uut(12.34, Double.POSITIVE_INFINITY));
        assertFalse(uut(-56.78, Double.POSITIVE_INFINITY));
        assertFalse(uut(910.11, Double.POSITIVE_INFINITY));
        assertFalse(uut(-1213.14, Double.POSITIVE_INFINITY));
        assertFalse(uut(151617.0, Double.POSITIVE_INFINITY));
        assertFalse(uut(Double.NEGATIVE_INFINITY, 12.34));
        assertFalse(uut(Double.NEGATIVE_INFINITY, -56.78));
        assertFalse(uut(Double.NEGATIVE_INFINITY, 910.11));
        assertFalse(uut(Double.NEGATIVE_INFINITY, -1213.14));
        assertFalse(uut(Double.NEGATIVE_INFINITY, 151617.0));
        assertFalse(uut(12.34, Double.NEGATIVE_INFINITY));
        assertFalse(uut(-56.78, Double.NEGATIVE_INFINITY));
        assertFalse(uut(910.11, Double.NEGATIVE_INFINITY));
        assertFalse(uut(-1213.14, Double.NEGATIVE_INFINITY));
        assertFalse(uut(151617.0, Double.NEGATIVE_INFINITY));
        assertFalse(uut(Double.NaN, Double.POSITIVE_INFINITY));
        assertFalse(uut(Double.NEGATIVE_INFINITY, Double.NaN));
    }

    @Test
    @DisplayName("Category 7: 50 Unique Hardcoded Tests Comparing Large Positive vs Large Negative Coordinates")
    void testLargePositiveVsLargeNegative() {
        // --- 1-10: Maximum Canvas Boundary Comparisons ---
        assertFalse(uut(100_000_000.0, -100_000_000.0));
        assertFalse(uut(100_000_000.0, -99_999_999.0));
        assertFalse(uut(99_999_999.0, -100_000_000.0));
        assertFalse(uut(98_765_432.1, -98_765_432.1));
        assertFalse(uut(95_000_000.0, -95_000_000.0));
        assertFalse(uut(92_100_500.2, -92_100_500.2));
        assertFalse(uut(90_000_000.0, -90_000_000.0));
        assertFalse(uut(88_888_888.8, -88_888_888.8));
        assertFalse(uut(85_000_000.0, -85_000_000.0));
        assertFalse(uut(81_234_567.8, -81_234_567.8));

        // --- 11-20: High-Range Asymmetric Comparisons ---
        assertFalse(uut(80_000_000.0, -79_999_999.0));
        assertFalse(uut(77_777_777.7, -77_777_777.7));
        assertFalse(uut(75_000_000.0, -75_000_000.0));
        assertFalse(uut(72_500_000.0, -72_400_000.0));
        assertFalse(uut(70_000_000.0, -70_000_000.0));
        assertFalse(uut(67_123_456.0, -67_123_456.0));
        assertFalse(uut(65_000_000.0, -65_000_000.0));
        assertFalse(uut(62_000_000.5, -62_000_000.5));
        assertFalse(uut(60_000_000.0, -60_000_000.0));
        assertFalse(uut(58_585_585.5, -58_585_585.5));

        // --- 21-30: Mid-to-High Range Canvas Sweeps ---
        assertFalse(uut(55_000_000.0, -55_000_000.0));
        assertFalse(uut(52_111_222.3, -52_111_222.3));
        assertFalse(uut(50_000_000.0, -50_000_000.0));
        assertFalse(uut(47_500_000.0, -47_500_000.0));
        assertFalse(uut(45_000_000.0, -45_000_000.0));
        assertFalse(uut(42_424_424.2, -42_424_424.2));
        assertFalse(uut(40_000_000.0, -40_000_000.0));
        assertFalse(uut(37_300_000.9, -37_300_000.9));
        assertFalse(uut(35_000_000.0, -35_000_000.0));
        assertFalse(uut(32_987_654.0, -32_987_654.0));

        // --- 31-40: Lower-to-Mid Range Polar Opposites ---
        assertFalse(uut(30_000_000.0, -30_000_000.0));
        assertFalse(uut(27_000_500.0, -27_000_500.0));
        assertFalse(uut(25_000_000.0, -25_000_000.0));
        assertFalse(uut(22_123_123.1, -22_123_123.1));
        assertFalse(uut(20_000_000.0, -20_000_000.0));
        assertFalse(uut(18_000_000.0, -17_999_999.0));
        assertFalse(uut(15_000_000.0, -15_000_000.0));
        assertFalse(uut(12_345_678.9, -12_345_678.9));
        assertFalse(uut(10_000_000.0, -10_000_000.0));
        assertFalse(uut(9_876_543.21, -9_876_543.21));

        // --- 41-50: Minimum Macro Range Stepdowns (Escaping Absolute Dead zone) ---
        assertFalse(uut(7_500_000.0, -7_500_000.0));
        assertFalse(uut(5_000_000.0, -5_000_000.0));
        assertFalse(uut(2_500_000.0, -2_500_000.0));
        assertFalse(uut(1_000_000.0, -1_000_000.0));
        assertFalse(uut(750_000.0, -750_000.0));
        assertFalse(uut(500_000.0, -500_000.0));
        assertFalse(uut(250_000.0, -250_000.0));
        assertFalse(uut(100_000.0, -100_000.0));
        assertFalse(uut(50_000.0, -50_000.0));
        assertFalse(uut(10_000.0, -10_000.0));
    }
}
