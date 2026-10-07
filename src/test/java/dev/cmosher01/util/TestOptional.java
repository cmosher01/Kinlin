/*
 *     Copyright 2026, Christopher Alan Mosher, New York, New York, USA, <cmosher01@gmail.com>.
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

package dev.cmosher01.util;

import lombok.val;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"EqualsWithItself", "SimplifiableAssertion"})
public class TestOptional {
    @Test
    void eq() {
        val aPresent = Optional.of(73);
        val bPresent = Optional.of(73);
        val cPresent = Optional.of(37);

        val aEmpty = Optional.<Integer>empty();
        val bEmpty = Optional.<Integer>empty();

        assertNotSame(aPresent, bPresent);
        assertEquals(aPresent, bPresent);
        assertTrue(aPresent.equals(bPresent));
        assertEquals(bPresent, aPresent);
        assertTrue(bPresent.equals(aPresent));
        assertNotEquals(aPresent, cPresent);
        assertFalse(aPresent.equals(cPresent));
        assertNotEquals(cPresent, aPresent);
        assertFalse(cPresent.equals(aPresent));

        assertEquals(aEmpty, aEmpty);
        assertTrue(aEmpty.equals(aEmpty));
        assertEquals(aEmpty, bEmpty);
        assertTrue(aEmpty.equals(bEmpty));
        assertEquals(bEmpty, aEmpty);
        assertTrue(bEmpty.equals(aEmpty));

        assertNotEquals(aPresent, aEmpty);
        assertFalse(aPresent.equals(aEmpty));
        assertNotEquals(aEmpty, aPresent);
        assertFalse(aEmpty.equals(aPresent));
    }

    @Disabled("incidentally true, but not guaranteed by Optional's contract")
    @Test
    void incidental() {
        val aEmpty = Optional.<Integer>empty();
        val bEmpty = Optional.<Integer>empty();

        assertSame(aEmpty, bEmpty);
    }

    @Test
    void chain() {
        val a = Optional.<Integer>empty();
        val b = Optional.<Integer>empty();
        val c = Optional.of(3);

        val first = a.or(() -> b).or(() -> c);

        assertEquals(3, first.get());
    }
}
