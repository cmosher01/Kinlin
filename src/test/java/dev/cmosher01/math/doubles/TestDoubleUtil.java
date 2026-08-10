package dev.cmosher01.math.doubles;

import lombok.val;
import manifold.ext.rt.api.auto;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static dev.cmosher01.math.doubles.DoubleUtil.*;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"SimplifiableAssertion", "DataFlowIssue", "ConstantValue", "WrapperTypeMayBePrimitive", "UnnecessaryBoxing"})
public class TestDoubleUtil {
    @Test
    void nominal() {
        auto x = fp(-3.0D);
        assertEquals(-3D, x);
    }

    @Test
    void fpSimpleConstructor() {
        double d = 2;
        assertEquals(d, fp(d));
        float f = 2;
        assertEquals(d, fp(f));
        long l = 2;
        assertEquals(d, fp(l));
        int i = 2;
        assertEquals(d, fp(i));
        char c = '9';
        assertEquals(9D, fp(c));
        short h = 2;
        assertEquals(d, fp(h));
        byte n = 2;
        assertEquals(d, fp(n));

        boolean b = true;
        assertEquals(1D, fp(b));

        Object o = new Object() {
            @Override
            public String toString() {
                return "99";
            }
        };
        assertEquals(99D, fp(o));

//        assertEquals(Double.NaN, fp(null));

        String s = "74";
        assertEquals(74D, fp(s));
        assertEquals(Double.NaN, fp("NaN"));
        assertEquals(Double.NaN, fp("-NaN"));
        assertEquals(Double.POSITIVE_INFINITY, fp("Infinity"));
        assertEquals(Double.NEGATIVE_INFINITY, fp("-Infinity"));
        assertEquals(-1234.5678D, fp("-1234.5678D"));
        assertEquals(12e3D, fp("12e3D"));

        String s2 = "74qqq";
        assertEquals(Double.NaN, fp(s2));
        Object o2 = new Object() {
            @Override
            public String toString() {
                return "99qqq";
            }
        };
        assertEquals(Double.NaN, fp(o2));

        assertTrue(1.2F == fp(Float.valueOf(1.2F)));

        assertEquals(1, fp(Boolean.TRUE));
        assertEquals(2, fp(Character.valueOf('2')));
    }

    @Test
    void asIntegral() {
        val d = 1000D;
        assertEquals(1000, asInt(d));
        assertEquals(1000L, asLong(d));
        val x = Double.valueOf(Integer.MAX_VALUE)+1000D;
        assertTrue(Integer.MAX_VALUE < x);
        assertEquals(Integer.MAX_VALUE,asInt(x));
        assertEquals(Integer.MAX_VALUE+1000L, asLong(x));
    }

    @Test
    void invertDouble() {
        final Double a = 2D;
        assertTrue(0.5 == ~a);
    }

    @Test
    void clampInf() {
        assertEquals(1.0D, Math.clamp(Double.POSITIVE_INFINITY, 0.0D, 1.0D));
    }

    @Test
    void infinitesimal() {
        double d = -1; p(d);
        d = Math.nextUp(d); p(d);
        d = Math.nextUp(d); p(d);
        System.out.println("...");

        double d1 = NEGATIVE_ZERO;
        double d2 = Math.nextDown(d1);
        double d3 = Math.nextDown(d2);
        p(d3); p(d2); p(d1);
        System.out.println("-------------");

        d = POSITIVE_ZERO; p(d);
        d = Math.nextUp(d); p(d);
        d = Math.nextUp(d); p(d);
        System.out.println("...");

        d1 = +1;
        d2 = Math.nextDown(d1);
        d3 = Math.nextDown(d2);
        p(d3); p(d2); p(d1);
    }

    private static void p(final double d) {
        System.out.println(new BigDecimal(d));
//        System.out.printf("%+.400f\n", d);
    }

    @Test
    void oneForFun() {
        assertTrue(1.0D == Double.longBitsToDouble(((1L<<10)-1)<<((1<<6)-12)));
    }

//    @Test
//    void minmaxNear() {
//        final double pa = .1D+.2D;
//        final Double wa = pa;
//
//        final double pb = .3D;
//        final Double wb = pb;
//
//        assertFalse(pa == pb); // ********
//        assertTrue (pa >  pb); // ********
//        assertTrue (pa >  wb); // ********
//
//
//
//        assertTrue (pa == Math.max(pa, pb));
//        assertTrue (pa == dUtilMax(pa, pb));
//        assertTrue (pa == Math.max(wa, wb));
//        assertTrue (pa == dUtilMax(wa, wb));
//
//        assertFalse(pb == Math.max(pa, pb)); // ********
//        assertTrue (pb == dUtilMax(pa, pb));
//        assertFalse(pb == Math.max(wa, wb)); // ********
//        assertTrue (pb == dUtilMax(wa, wb));
//
//        assertTrue (pa == Math.max(pb, pa));
//        assertTrue (pa == dUtilMax(pb, pa));
//        assertTrue (pa == Math.max(wb, wa));
//        assertTrue (pa == dUtilMax(wb, wa));
//
//        assertFalse(pb == Math.max(pb, pa)); // ********
//        assertTrue (pb == dUtilMax(pb, pa));
//        assertFalse(pb == Math.max(wb, wa)); // ********
//        assertTrue (pb == dUtilMax(wb, wa));
//
//        assertFalse(pa == Math.min(pa, pb)); // ********
//        assertTrue (pa == dUtilMin(pa, pb));
//        assertFalse(pa == Math.min(wa, wb)); // ********
//        assertTrue (pa == dUtilMin(wa, wb));
//
//        assertTrue (pb == Math.min(pa, pb));
//        assertTrue (pb == dUtilMin(pa, pb));
//        assertTrue (pb == Math.min(wa, wb));
//        assertTrue (pb == dUtilMin(wa, wb));
//
//        assertFalse(pa == Math.min(pb, pa)); // ********
//        assertTrue (pb == dUtilMin(pb, pa));
//        assertFalse(pa == Math.min(wb, wa)); // ********
//        assertTrue (pb == dUtilMin(wb, wa));
//
//        assertTrue (pb == Math.min(pb, pa));
//        assertTrue (pb == dUtilMin(pb, pa));
//        assertTrue (pb == Math.min(wb, wa));
//        assertTrue (pb == dUtilMin(wb, wa));
//    }
//
//    @Test
//    void minmaxNominal() {
//        final Double les = 11D;
//        final Double mor = 19D;
//        assertTrue(les == les);
//        assertTrue(mor == mor);
//        assertTrue(les < mor);
//        assertTrue(mor > les);
//
//        assertFalse(les == Math.max(les, mor));
//        assertFalse(les == dUtilMax(les, mor));
//        assertTrue (mor == Math.max(les, mor));
//        assertTrue (mor == dUtilMax(les, mor));
//
//        assertFalse(les == Math.max(mor, les));
//        assertFalse(les == dUtilMax(mor, les));
//        assertTrue (mor == Math.max(mor, les));
//        assertTrue (mor == dUtilMax(mor, les));
//
//        assertTrue (les == Math.min(les, mor));
//        assertTrue (les == dUtilMin(les, mor));
//        assertFalse(mor == Math.min(les, mor));
//        assertFalse(mor == dUtilMin(les, mor));
//
//        assertTrue (les == Math.min(mor, les));
//        assertTrue (les == dUtilMin(mor, les));
//        assertFalse(mor == Math.min(mor, les));
//        assertFalse(mor == dUtilMin(mor, les));
//    }
//
//    @Test
//    void minmaxCorner() {
//        assertTrue(Double.isNaN(Math.min(Double.NaN, Double.NaN)));
//        assertTrue(Double.isNaN(dUtilMin(Double.NaN, Double.NaN)));
//        assertTrue(Double.isNaN(Math.min(Double.NaN, ONE)));
//        assertTrue(Double.isNaN(dUtilMin(Double.NaN, ONE)));
//        assertTrue(Double.isNaN(Math.min(ONE, Double.NaN)));
//        assertTrue(Double.isNaN(dUtilMin(ONE, Double.NaN)));
//
//        assertTrue(Double.isNaN(Math.max(Double.NaN, Double.NaN)));
//        assertTrue(Double.isNaN(dUtilMax(Double.NaN, Double.NaN)));
//        assertTrue(Double.isNaN(Math.max(Double.NaN, ONE)));
//        assertTrue(Double.isNaN(dUtilMax(Double.NaN, ONE)));
//        assertTrue(Double.isNaN(Math.max(ONE, Double.NaN)));
//        assertTrue(Double.isNaN(dUtilMax(ONE, Double.NaN)));
//
//        assertEquals(POSITIVE_ZERO, POSITIVE_ZERO);
//        assertNotEquals(POSITIVE_ZERO, NEGATIVE_ZERO);
//        assertNotEquals(NEGATIVE_ZERO, POSITIVE_ZERO);
//        assertEquals(NEGATIVE_ZERO, NEGATIVE_ZERO);
//
//        assertEquals(POSITIVE_ZERO, Math.min( ONE, POSITIVE_ZERO));
//        assertEquals(POSITIVE_ZERO, dUtilMin( ONE, POSITIVE_ZERO));
//        assertEquals(NEGATIVE_ZERO, Math.min( ONE, NEGATIVE_ZERO));
//        assertEquals(NEGATIVE_ZERO, dUtilMin( ONE, NEGATIVE_ZERO));
//        assertEquals(POSITIVE_ZERO, Math.max(-ONE, POSITIVE_ZERO));
//        assertEquals(POSITIVE_ZERO, dUtilMax(-ONE, POSITIVE_ZERO));
//        assertEquals(NEGATIVE_ZERO, Math.max(-ONE, NEGATIVE_ZERO));
//        assertEquals(NEGATIVE_ZERO, dUtilMax(-ONE, NEGATIVE_ZERO));
//
//        assertEquals(NEGATIVE_ZERO, Math.min(NEGATIVE_ZERO, POSITIVE_ZERO));
//        assertEquals(NEGATIVE_ZERO, dUtilMin(NEGATIVE_ZERO, POSITIVE_ZERO));
//        assertEquals(NEGATIVE_ZERO, Math.min(POSITIVE_ZERO, NEGATIVE_ZERO));
//        assertEquals(NEGATIVE_ZERO, dUtilMin(POSITIVE_ZERO, NEGATIVE_ZERO));
//        assertEquals(POSITIVE_ZERO, Math.max(NEGATIVE_ZERO, POSITIVE_ZERO));
//        assertEquals(POSITIVE_ZERO, dUtilMax(NEGATIVE_ZERO, POSITIVE_ZERO));
//        assertEquals(POSITIVE_ZERO, Math.max(POSITIVE_ZERO, NEGATIVE_ZERO));
//        assertEquals(POSITIVE_ZERO, dUtilMax(POSITIVE_ZERO, NEGATIVE_ZERO));
//    }
//
//
//
//    // method name length is the same as "Math." so calls line up vertically
//    // return value is primitive to prevent calling DoubleExtension
//    private static double dUtilMin(double a, double b) {
//        return DoubleUtil.min(a, b);
//    }
//    private static double dUtilMax(double a, double b) {
//        return DoubleUtil.max(a, b);
//    }

    @Test
    void clear() {
        assertNotEquals(77.0D, 77.000000001D);
        assertTrue((Double)77.0D == 77.000000001D);
        assertEquals(77.0D, clearAt(77.000000001D, 77));
        assertNotEquals(77.0D, 76.999999999D);
        assertTrue((Double)77.0D == 76.999999999D);
        assertEquals(77.0D, clearAt(76.999999999D, 77));
    }
}
