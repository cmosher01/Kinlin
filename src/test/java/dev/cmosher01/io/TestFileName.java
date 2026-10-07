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

package dev.cmosher01.io;

import lombok.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"SequencedCollectionMethodCanBeUsed", "StringOperationCanBeSimplified", "SimplifiableAssertion", "ConstantValue", "DataFlowIssue", "EqualsWithItself"})
public class TestFileName {
    @Test
    void nominal() {
        val uut = FileName.of("document.txt");
        assertFalse(uut.isHidden());
        assertFalse(uut.isAllDots());
        assertEquals("document", uut.name());
        assertEquals("document", uut.nameUnhidden());
        assertTrue(uut.extension().isPresent());
        assertEquals("txt", uut.extension().get());
        assertEquals("txt", uut.extensionOrEmpty());
        assertEquals(1, uut.extensions().size());
        assertEquals("txt", uut.extensions().get(0));
    }

    @Test
    void noExtension() {
        val uut = FileName.of("My Documents");
        assertFalse(uut.isHidden());
        assertFalse(uut.isAllDots());
        assertEquals("My Documents", uut.name());
        assertEquals("My Documents", uut.nameUnhidden());
        assertTrue(uut.extension().isEmpty());
        assertEquals("", uut.extensionOrEmpty());
        assertEquals(0, uut.extensions().size());
    }

    @Test
    void multipleExtensions() {
        val uut = FileName.of("document.csv.txt");
        assertFalse(uut.isHidden());
        assertFalse(uut.isAllDots());
        assertEquals("document", uut.name());
        assertEquals("document", uut.nameUnhidden());
        assertTrue(uut.extension().isPresent());
        assertEquals("txt", uut.extension().get());
        assertEquals("txt", uut.extensionOrEmpty());
        assertEquals(2, uut.extensions().size());
        assertEquals("csv", uut.extensions().get(0));
        assertEquals("txt", uut.extensions().get(1));
    }

    @Test
    void multipleExtensionsWithEmpty() {
        val uut = FileName.of("document.csv..txt");
        assertFalse(uut.isHidden());
        assertFalse(uut.isAllDots());
        assertEquals("document", uut.name());
        assertEquals("document", uut.nameUnhidden());
        assertTrue(uut.extension().isPresent());
        assertEquals("txt", uut.extension().get());
        assertEquals("txt", uut.extensionOrEmpty());
        assertEquals(3, uut.extensions().size());
        assertEquals("csv", uut.extensions().get(0));
        assertEquals("", uut.extensions().get(1));
        assertEquals("txt", uut.extensions().get(2));
    }

    @Test
    void hiddenNominal() {
        val uut = FileName.of(".config.dir");
        assertTrue(uut.isHidden());
        assertFalse(uut.isAllDots());
        assertEquals(".config", uut.name());
        assertEquals("config", uut.nameUnhidden());
        assertTrue(uut.extension().isPresent());
        assertEquals("dir", uut.extension().get());
        assertEquals("dir", uut.extensionOrEmpty());
        assertEquals(1, uut.extensions().size());
        assertEquals("dir", uut.extensions().get(0));
    }

    @Test
    void hiddenNoExtension() {
        val uut = FileName.of(".gitignore");
        assertTrue(uut.isHidden());
        assertFalse(uut.isAllDots());
        assertEquals(".gitignore", uut.name());
        assertEquals("gitignore", uut.nameUnhidden());
        assertTrue(uut.extension().isEmpty());
        assertEquals("", uut.extensionOrEmpty());
        assertEquals(0, uut.extensions().size());
    }

    @Test
    void hiddenMultipleExtensions() {
        val uut = FileName.of(".document.csv.txt");
        assertTrue(uut.isHidden());
        assertFalse(uut.isAllDots());
        assertEquals(".document", uut.name());
        assertEquals("document", uut.nameUnhidden());
        assertTrue(uut.extension().isPresent());
        assertEquals("txt", uut.extension().get());
        assertEquals("txt", uut.extensionOrEmpty());
        assertEquals(2, uut.extensions().size());
        assertEquals("csv", uut.extensions().get(0));
        assertEquals("txt", uut.extensions().get(1));
    }

    @Test
    void blank() {
        val uut = FileName.of("");
        assertFalse(uut.isHidden());
        assertFalse(uut.isAllDots());
        assertEquals("", uut.name());
        assertEquals("", uut.nameUnhidden());
        assertTrue(uut.extension().isEmpty());
        assertEquals("", uut.extensionOrEmpty());
        assertEquals(0, uut.extensions().size());
    }

    @Test
    void nullString() {
        val uut = FileName.of(null);
        assertFalse(uut.isHidden());
        assertFalse(uut.isAllDots());
        assertEquals("", uut.name());
        assertEquals("", uut.nameUnhidden());
        assertTrue(uut.extension().isEmpty());
        assertEquals("", uut.extensionOrEmpty());
        assertEquals(0, uut.extensions().size());
    }

    @Test
    void space() {
        val uut = FileName.of(" ");
        assertFalse(uut.isHidden());
        assertFalse(uut.isAllDots());
        assertEquals(" ", uut.name());
        assertEquals(" ", uut.nameUnhidden());
        assertTrue(uut.extension().isEmpty());
        assertEquals("", uut.extensionOrEmpty());
        assertEquals(0, uut.extensions().size());
    }

    @Test
    void spaceDotSpace() {
        val uut = FileName.of(" . ");
        assertFalse(uut.isHidden());
        assertFalse(uut.isAllDots());
        assertEquals(" ", uut.name());
        assertEquals(" ", uut.nameUnhidden());
        assertTrue(uut.extension().isPresent());
        assertEquals(" ", uut.extension().get());
        assertEquals(" ", uut.extensionOrEmpty());
        assertEquals(1, uut.extensions().size());
        assertEquals(" ", uut.extensions().get(0));
    }

    @Test
    void nameDot() {
        val uut = FileName.of("something.");
        assertFalse(uut.isHidden());
        assertFalse(uut.isAllDots());
        assertEquals("something", uut.name());
        assertEquals("something", uut.nameUnhidden());
        assertTrue(uut.extension().isPresent());
        assertEquals("", uut.extension().get());
        assertEquals("", uut.extensionOrEmpty()); // !!!
        assertEquals(1, uut.extensions().size());
        assertEquals("", uut.extensions().get(0));
    }

    @Test
    void dot1() {
        val uut = FileName.of(".");
        assertTrue(uut.isHidden());
        assertTrue(uut.isAllDots());
        assertEquals(".", uut.name());
        assertEquals(".", uut.nameUnhidden());
        assertTrue(uut.extension().isEmpty());
        assertEquals("", uut.extensionOrEmpty());
        assertEquals(0, uut.extensions().size());
    }

    @Test
    void dot2() {
        val uut = FileName.of("..");
        assertTrue(uut.isHidden());
        assertTrue(uut.isAllDots());
        assertEquals("..", uut.name());
        assertEquals("..", uut.nameUnhidden());
        assertTrue(uut.extension().isEmpty());
        assertEquals("", uut.extensionOrEmpty());
        assertEquals(0, uut.extensions().size());
    }

    @Test
    void dot3() {
        val uut = FileName.of("...");
        assertTrue(uut.isHidden());
        assertTrue(uut.isAllDots());
        assertEquals("...", uut.name());
        assertEquals("...", uut.nameUnhidden());
        assertTrue(uut.extension().isEmpty());
        assertEquals("", uut.extensionOrEmpty());
        assertEquals(0, uut.extensions().size());
    }



    @Test
    void dotSpaceDotDot() {
        val uut = FileName.of(". ..");
        assertTrue(uut.isHidden());
        assertFalse(uut.isAllDots());
        assertEquals(". ", uut.name());
        assertEquals(" ", uut.nameUnhidden());
        assertTrue(uut.extension().isPresent());
        assertEquals("", uut.extension().get());
        assertEquals("", uut.extensionOrEmpty());
        assertEquals(2, uut.extensions().size());
        assertEquals("", uut.extensions().get(0));
        assertEquals("", uut.extensions().get(1));
    }

    @Test
    void immutableList() {
        val uut = FileName.of("this.is.a.test");
        assertEquals(3, uut.extensions().size());
        assertEquals("is", uut.extensions().get(0));
        assertEquals("a", uut.extensions().get(1));
        assertEquals("test", uut.extensions().get(2));

        final List<String> exts = uut.extensions();

        assertThrows(UnsupportedOperationException.class,
            () -> exts.remove(0));
        assertThrows(UnsupportedOperationException.class,
            () -> exts.add("foo"));
        assertThrows(UnsupportedOperationException.class,
            exts::clear);
    }

    @Test
    void equals() {
        val n1 = FileName.of(new String("testfile.name.txt"));
        assertTrue(n1.equals(n1));

        val n2 = FileName.of(new String("testfile.name.txt"));
        assertNotSame(n2, n1);

        assertTrue(n1.equals(n2));
        assertEquals(n1, n2);
        assertTrue(n2.equals(n1));
        assertEquals(n2, n1);



        val n3 = FileName.of(new String("testfile.name.txtx"));
        assertNotSame(n3, n1);
        assertFalse(n1.equals(n3));
        assertNotEquals(n1, n3);
        assertFalse(n3.equals(n1));
        assertNotEquals(n3, n1);

        val n4 = FileName.of(new String("textfile.name.txt"));
        assertNotSame(n4, n1);
        assertFalse(n1.equals(n4));
        assertNotEquals(n1, n4);
        assertFalse(n4.equals(n1));
        assertNotEquals(n4, n1);
    }

    @Test
    void equalsCornerCases() {
        eq(FileName.of(new String(".")), FileName.of(new String(".")));
        eq(FileName.of(new String("..")), FileName.of(new String("..")));
        eq(FileName.of(new String("...")), FileName.of(new String("...")));
        eq(FileName.of(new String(".config")), FileName.of(new String(".config")));
        eq(FileName.of(new String(".config.dir")), FileName.of(new String(".config.dir")));
        eq(FileName.of(new String("")), FileName.of(new String("")));
        eq(FileName.of(new String("")), FileName.of(null));
        eq(FileName.of(null), FileName.of(null));

        neq(FileName.of("junk"), FileName.of(".junk"));
        neq(FileName.of("junk"), FileName.of("junk."));
        neq(FileName.of("."), FileName.of(""));
        neq(FileName.of("."), FileName.of(".."));
    }


    @Test
    void isAllDots() {
        assertTrue(FileName.isAllDots("."));
        assertTrue(FileName.isAllDots(".."));
        assertTrue(FileName.isAllDots("..."));
        assertFalse(FileName.isAllDots(""));
        assertFalse(FileName.isAllDots("a"));
        assertFalse(FileName.isAllDots("a."));
        assertFalse(FileName.isAllDots(".a"));
        assertFalse(FileName.isAllDots("a.b"));
        assertFalse(FileName.isAllDots("...........................x"));
    }




    private void eq(final @NonNull FileName a, final @NonNull FileName b) {
        assertNotSame(a, b);
        assertEquals(a, b);
        assertTrue(a.equals(b));
        assertEquals(b, a);
        assertTrue(b.equals(a));
    }

    private void neq(final @NonNull FileName a, final @NonNull FileName b) {
        assertNotSame(a, b);
        assertNotEquals(a, b);
        assertFalse(a.equals(b));
        assertNotEquals(b, a);
        assertFalse(b.equals(a));
    }
}
