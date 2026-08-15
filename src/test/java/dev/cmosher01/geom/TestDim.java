/*
 *     Copyright © 2026, Christopher Alan Mosher, New York, New York, USA, <cmosher01@gmail.com>.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.cmosher01.geom;

import dev.cmosher01.math.doubles.DoubleUtil;
import lombok.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.*;

import static dev.cmosher01.math.doubles.DoubleUtil.*;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"SimplifiableAssertion", "ConstantValue", "MismatchedQueryAndUpdateOfCollection", "SequencedCollectionMethodCanBeUsed", "UnnecessaryLocalVariable", "ExpressionComparedToItself"})
public class TestDim {
    @Test
    void nominal() {
        final Double input = fp(99.9D);
        final Dim uut = new Dim(input);
        final Double expected = input;
        final Double actual = uut.n();
        assertTrue(expected == actual);
    }

    @Test
    void zero() {
        assertTrue(Dim.ZERO == Dim.ZERO);
        assertTrue(Dim.ZERO == Dim.ZERO);
        assertFalse(Dim.ZERO < Dim.ZERO);
        assertFalse(Dim.ZERO > Dim.ZERO);
        assertTrue(Dim.ZERO.zero());
        assertTrue(Dim.ZERO.n() == DoubleUtil.ZERO);

        final Dim nz = new Dim(fp(NEGATIVE_ZERO));
        assertTrue(nz.zero());
        final Dim pz = new Dim(fp(POSITIVE_ZERO));
        assertTrue(pz.zero());
        assertTrue(Dim.ZERO == nz);
        assertTrue(Dim.ZERO == pz);
    }

    @Test
    void one() {
        assertEquals(1, DoubleUtil.asInt(Dim.ONE.n()));
        assertFalse(Dim.ONE.zero());
        assertTrue(Dim.ONE == Dim.ONE);
        assertTrue(Dim.ONE == new Dim(DoubleUtil.ONE));
        assertFalse(Dim.ONE == Dim.ZERO);
        assertFalse(Dim.ZERO == Dim.ONE);

        var other = new Dim(fp(1));
        assertNotSame(Dim.ONE, other);
        assertTrue(other == Dim.ONE);
        assertTrue(Dim.ONE == other);

        assertTrue(Dim.ZERO < Dim.ONE);
        assertFalse(Dim.ONE < Dim.ZERO);
        assertFalse(Dim.ZERO > Dim.ONE);
        assertTrue(Dim.ONE > Dim.ZERO);
    }

    @Test
    void near() {
        final Double a = fp(0.1D+0.2D);
        final Double b = fp(0.3D);

        // two different (but near) floating point numbers
        assertFalse(unbox(a) == unbox(b));
        assertFalse(unbox(b) == unbox(a));
        assertFalse(unbox(a)-unbox(b) == 0.0D);
        assertFalse(unbox(b)-unbox(a) == 0.0D);
        assertTrue(unbox(a) > unbox(b));

        // therefore, two different (but near) Dim objects:
        final Dim dimA = new Dim(a);
        final Dim dimB = new Dim(b);

        assertNotSame(dimA, dimB);
        // "near" dimensions are considered equal
        assertTrue(dimA == dimB);
        assertFalse(dimA < dimB);
        assertFalse(dimA > dimB);
        assertTrue(dimB == dimA);
        assertFalse(dimB < dimA);
        assertFalse(dimB > dimA);
    }

    @Test
    void grow() {
        assertTrue(Dim.ONE == Dim.ZERO + new GrowTerm(+1.0D));
        assertTrue(Dim.ZERO == Dim.ONE + new GrowTerm(-1.0D));
        assertTrue(Dim.ZERO == Dim.ONE + new GrowTerm(-2.0D));
        assertTrue(Dim.ONE == Dim.ONE + new GrowTerm(POSITIVE_ZERO));
        assertTrue(Dim.ONE == Dim.ONE + new GrowTerm(NEGATIVE_ZERO));
        assertEquals(Dim.ZERO, Dim.ZERO - new GrowTerm(+1.0D));
        assertEquals(Dim.ONE, Dim.ZERO - new GrowTerm(-1.0D));
    }

    @Test
    void scale() {
        assertTrue(new Dim(fp(2D)) == Dim.ONE * ScaleFactor.TWICE);
        assertTrue(Dim.ONE == new Dim(fp(2D)) * ScaleFactor.HALF);
    }

    @Test
    void diff() {
        val a = new Dim(7D);
        val b = new Dim(13D);
        assertEquals(new Dim(6D), a - b);
        assertEquals(new Dim(6D), b - a);
    }

    @Test
    void list() {
        var r = new ArrayList<Dim>();
        assertEquals(0, r.size());
        r.add(Dim.ONE);
        assertEquals(1, r.size());
        r.add(Dim.ZERO);
        assertEquals(2, r.size());

        assertTrue(r.get(0) == Dim.ONE);
        assertTrue(r.get(1) == Dim.ZERO);
        r.sort(Dim::compareTo);
        assertEquals(2, r.size());
        assertTrue(r.get(0) == Dim.ZERO);
        assertTrue(r.get(1) == Dim.ONE);
        r.sort(((Comparator<Dim>)Dim::compareTo).reversed());
        assertTrue(r.get(0) == Dim.ONE);
        assertTrue(r.get(1) == Dim.ZERO);

        r.sort(Dim::compareTo);
        r.removeFirst();
        assertEquals(1, r.size());
        assertTrue(r.get(0) == Dim.ONE);
    }

    public static void illegal(@NonNull final Executable executable) {
        assertThrows(RuntimeException.class, executable);
    }
}
