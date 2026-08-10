package dev.cmosher01.math.doubles;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.*;

import static dev.cmosher01.math.doubles.DoubleUtil.*;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"SimplifiableAssertion", "WrapperTypeMayBePrimitive", "DataFlowIssue", "ConstantValue", "BoxingBoxedValue", "SameParameterValue", "MismatchedQueryAndUpdateOfCollection"})
public class TestManifold {


    @Test
    public void t() {
        val a = Double.valueOf(.3D);
        val b = Double.valueOf(.1D+.2D);
        System.err.println("testing with my Manifold extension");
        assertTrue(a.equals(b));
        assertTrue(a == b);
        assertFalse(a != b);
        assertTrue(a <= b);
        assertTrue(a >= b);
        assertFalse(a < b);
        assertFalse(a > b);
        assertTrue(a.isPositive());




        //default Java behavior:
//        assertFalse(a.equals(b));
//        assertFalse(a == b);
//        assertTrue(a != b);
//        assertTrue(a <= b);
//        assertFalse(a >= b);
//        assertTrue(a < b);
//        assertFalse(a > b);


        assertFalse((Double)a == (Double)null);
        final Double n = (Double)null;
        assertFalse((Double)a == (Double)n);
        assertFalse(fp((Object)null) == fp((Object)null));
        assertTrue(fp((Object)null).isNaN());
        assertEquals(Double.NaN, fp((Object)null));

        final Float fa = .3F;
        assertFalse((Double)a == (Number)(Object)fa);
        assertFalse(a.equals((Object)fa));

        assertFalse(Double.valueOf(36D) == (Object)Float.valueOf(36F));
        assertTrue(Double.valueOf(36D).equals((Object)Float.valueOf(36F)));
        assertTrue(Double.valueOf(36D) == Double.valueOf(36D));
        assertFalse(Double.valueOf(36D) == (Number)Double.valueOf(36D)); // WHY?
        assertFalse(Double.valueOf(36D) == (Object)Double.valueOf(36D)); // WHY?
        assertTrue(Double.valueOf(36D).equals((Object)Double.valueOf(36D)));
        assertTrue(Double.valueOf(36D) == (Double)(Object)Double.valueOf(36D));


        //?????????????????????????????????????????? WHY
        assertFalse(1e-8D == 0);
        assertFalse(fp(1e-8) == 0);
        assertFalse(1e-8D == (double)0);
        assertFalse((Double)fp(1e-8) == 0);
        assertFalse(1e-8D == 0D);
        assertFalse(1e-8D == -0D);
        assertFalse(1e-8D == 0.0D);
        assertFalse(1e-8D == -0.0D);
        var dVarPrimitive = 0D; assertFalse(1e-8D == dVarPrimitive);
        assertFalse(dVarPrimitive == 1e-8D);

        // to fix it: (This is a Manifold thing, not a regular Java compiler thing)
        assertTrue(1e-8D == (Double)dVarPrimitive);
        assertTrue((Double)1e-8D == dVarPrimitive);
        assertTrue((Double)1e-8D == (Double)dVarPrimitive);
        Double dDoubleWrapped = 0D;
        assertTrue(1e-8D == dDoubleWrapped);
        assertTrue(dDoubleWrapped == 1e-8D);

        Double dDoubleWrappedN = 1e-8D;
        assertFalse(dDoubleWrappedN == 0);
        assertTrue(dDoubleWrappedN == 0D);
        assertTrue(dDoubleWrappedN == 0.0D);
        eqOfParam((double)0D);
        eqOfParam((double)0);
        neOfParam((Double)Double.valueOf(POSITIVE_ZERO));
        neOfParam((Double)Double.valueOf(NEGATIVE_ZERO));
        assertTrue(1e-8D == (Double)(double)0);
        assertTrue(1e-8D == (Double)0D);
        assertTrue(1e-8D == (Double)(-0D));
        assertTrue(1e-8D == (Double)0.0D);
        assertTrue(1e-8D == (Double)(-0.0D));
        assertTrue(fp(1e-8) == (double)0);
        assertTrue(fp(1e-8) == (Double)(double)0);
        assertTrue((Double)1e-8D == (double)0);
        assertTrue((Double)1e-8D == 0D);
        assertTrue((Double)1e-8D == -0D);
        assertTrue((Double)1e-8D == 0.0D);
        assertTrue((Double)1e-8D == -0.0D);



        assertTrue(fp(1e-8) == 0D);
        assertTrue(fp(1e-8) == -0D);
        assertTrue(fp(1e-8) == 0.0D);
        assertTrue(fp(1e-8) == -0.0D);

        assertTrue(1e-8 == fp(0));
        assertTrue(1e-8 == fp(0D));
        assertTrue(1e-8 == fp(-0D));
        assertTrue(1e-8 == fp(0.0D));
        assertTrue(1e-8 == fp(-0.0D));
        assertTrue(1e-8 == DoubleUtil.ZERO);
        assertTrue(1e-8 == DoubleUtil.POSITIVE_ZERO);
        assertTrue(1e-8 == DoubleUtil.NEGATIVE_ZERO);
        assertTrue(-1e-8 == fp(0));
        assertTrue(-1e-8 == fp(0D));
        assertTrue(-1e-8 == fp(-0D));
        assertTrue(-1e-8 == fp(0.0D));
        assertTrue(-1e-8 == fp(-0.0D));
        assertTrue(1e-8 == DoubleUtil.ZERO);
        assertTrue(-1e-8 == DoubleUtil.POSITIVE_ZERO);
        assertTrue(-1e-8 == DoubleUtil.NEGATIVE_ZERO);

        assertEquals(0, (int)a.compareTo(b));
        assertEquals(0, (int)b.compareTo(a));




        assertFalse(77D == 77.000000001D);
        assertFalse(77.000000001D == 77D);
        assertTrue(Double.valueOf(77D) == 77.000000001D);
        assertTrue(Double.valueOf(77.000000001D) == 77D);
        assertTrue(77D == (Double)77.000000001D);
        assertTrue((Double)77.000000001D == 77D);
        assertTrue((Double)77D == 77.000000001D);
        assertTrue(77.000000001D == (Double)77D);
        assertTrue((Double)77D == (Double)77.000000001D);
        assertTrue((Double)77.000000001D == (Double)77D);

        assertFalse(-77 == -77.000000001D);
        assertFalse(-77.000000001D == -77);
        assertFalse(77 == 77.000000001D);
        assertFalse(77.000000001D == 77);
        assertFalse(77 == (Double)77.000000001D);
        assertFalse((Double)77.000000001D == 77);
        assertFalse((double)77 == 77.000000001D);
        assertFalse( 77.000000001D == (double)77);
        assertTrue(Double.valueOf(77) == 77.000000001D);
        assertFalse(Double.valueOf(77.000000001D) == 77);
        assertTrue(Double.valueOf(77.000000001D) == fp(77));
        assertTrue((Double)(double)77 == 77.000000001D);
        assertTrue(77.000000001D == (Double)(double)77);
        assertTrue((double)77 == (Double)77.000000001D);
        assertTrue((Double)77.000000001D == (double)77);
        assertTrue((Double)(double)77 == (Double)77.000000001D);
        assertTrue((Double)77.000000001D == (Double)(double)77);



        assertFalse(77.0 == 77.000000001D);
        assertFalse(77.000000001D == 77.0);
        assertFalse(-77.0 == -77.000000001D);
        assertFalse(-77.000000001D == -77.0);
        assertFalse((double)77.0 == 77.000000001D);
        assertFalse( 77.000000001D == (double)77.0);
        assertTrue(77.0 == (Double)77.000000001D);
        assertTrue((Double)77.000000001D == 77.0);
        assertTrue((Double)77.0 == 77.000000001D);
        assertTrue(77.000000001D == (Double)77.0);
        assertTrue((Double)77.0 == 77.000000001D);
        assertTrue( 77.000000001D == (Double)77.0);
        assertTrue((Double)77.0 == (Double)77.000000001D);
        assertTrue((Double)77.000000001D == (Double)77.0);


        // this doesn't work
        // it will not call DoubleExtension.compareTo
        final Set<Double> x = new TreeSet<>();
        x.add(a);
        x.add(b);
//        assertEquals(1, w.size());
    }

    private void eqOfParam(Double i) {
        assertTrue(i == 1e-8D);
    }
    private void neOfParam(double i) {
        assertFalse(i == 1e-8D);
    }

    @Test
    void primitive() {
        assertFalse(.1D+.2D == .3D);
        assertTrue(Double.valueOf(.1D+.2D) == Double.valueOf(.3D));
        assertTrue((Double)(.1D+.2D) == (Double)(.3D));
        assertTrue((Double)(.1D+.2D) == (.3D));
        assertTrue((.1D+.2D) == (Double)(.3D));
    }

    @Test
    public void d() {
        final double a = 1;
        assertTrue(3 == 2 + + + a);
        assertTrue(3 == 2 + - - a);
        assertTrue(3 == 2 + + - + - a);
        assertTrue(3 == 2 + - + - + a);
        assertTrue(3 == 2 - + - + a);
        assertTrue(3 == 2 - - + + a);

        final Double d = 7D;
        assertEquals(7D, + + + + + + + + d, 1e-20);

//        assertEquals(7D, ~n, E);
//        Double.valueOf(2).inv();
    }
}
