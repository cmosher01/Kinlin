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

package dev.cmosher01.genealogy.xy.genealogy;

import dev.cmosher01.genealogy.xy.*;
import dev.cmosher01.geom.Grid;
import lombok.*;
import nu.mine.mosher.collection.TreeNode;
import nu.mine.mosher.gedcom.*;
import nu.mine.mosher.gedcom.exception.InvalidLevel;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public final class IoGedcom {
    @Deprecated
    private IoGedcom() {
        throw new UnsupportedOperationException();
    }





    public static ChartModel read(final @NonNull Path path, final @NonNull Grid grid, final @NonNull FontModel fonts) throws IOException, InvalidLevel {
        val tree = readTree(path);

        val mapIdToIndi = new HashMap<String, Indi>();

        val indis = buildIndis(tree, mapIdToIndi, grid, fonts);
        val famis = buildFamis(tree, Collections.unmodifiableMap(mapIdToIndi));

        return new ChartModel(indis, famis);
    }





    private static GedcomTree readTree(final @NonNull Path path) throws IOException, InvalidLevel {
        try (val input = new BufferedInputStream(Files.newInputStream(path))) {
            val tree = Gedcom.readFile(input);
            new GedcomConcatenator(tree).concatenate();
            return tree;
        }
    }











    private static @NonNull ArrayList<Indi> buildIndis(final @NonNull GedcomTree tree, final @NonNull Map<String, Indi> mapIdToIndi, final @NonNull Grid grid, final @NonNull FontModel fonts) {
        val indis = new ArrayList<Indi>();
        tree.getRoot().forEach(nodeIndi -> {
            if (nodeIndi.getObject().getTag().equals(GedcomTag.INDI)) {
                val indi = buildIndi(nodeIndi, grid, fonts);
                mapIdToIndi.put(indi.key(), indi);
                indis.add(indi);
            }
        });
        return indis;
    }

    private static @NonNull Indi buildIndi(final @NonNull TreeNode<GedcomLine> node, final @NonNull Grid grid, final @NonNull FontModel fonts) {
        val id = new IndividualId(node);

        // sex and birthdate are only used as hints by the automatic layout algorithms
        val sex = toSex(getValue(node, "SEX"));
        val birth = GedcomDateUtil.calcBirthForSort(getEventDate(node, "BIRT"));

        val plaque = buildPlaque(node, fonts);
        val pt = new MovablePoint(grid, XyValue.create(getValue(node, "_XY")).get());

        return new Indi(id, sex, birth, plaque, pt);
    }

    private static Plaque buildPlaque(TreeNode<GedcomLine> node, FontModel fonts) {
        final Plaque plaque;
        val name = GedcomIndiName.create(getValue(node, "NAME"));
        val lifespan = GedcomDateUtil.getLifespan(getEventDate(node, "BIRT"), getEventDate(node, "DEAT"));
        val place = getPlace(node);
        plaque = Plaque.create(fonts, name, lifespan, place);
        return plaque;
    }



    private static @NonNull String getPlace(final @NonNull TreeNode<GedcomLine> nodeIndi) {
        @NonNull val birthplace = getBirthPlace(nodeIndi).strip();
        if (!birthplace.isBlank()) {
            return birthplace;
        }
        return getAnyPlace(nodeIndi);
    }

    private static @NonNull String getBirthPlace(final @NonNull TreeNode<GedcomLine> node) {
        for (@NonNull val c : node) {
            if (c.getObject().getTagString().equals("BIRT")) {
                return getValue(c, "PLAC");
            }
        }
        return "";
    }

    private static @NonNull String getAnyPlace(final @NonNull TreeNode<GedcomLine> node) {
        for (@NonNull val c : node) {
            @NonNull val p = getValue(c, "PLAC");
            if (!p.isBlank()) {
                return p;
            }
        }
        return "";
    }



    private static @NonNull String getEventDate(final @NonNull TreeNode<GedcomLine> node, final @NonNull String tag) {
        for (@NonNull val c : node) {
            if (c.getObject().getTagString().equals(tag)) {
                return getValue(c, "DATE");
            }
        }
        return "";
    }



    private static @NonNull String getValue(final @NonNull TreeNode<GedcomLine> node, final @NonNull String tag) {
        for (@NonNull val c : node) {
            if (c.getObject().getTagString().equals(tag)) {
                val p = c.getObject().getValue().strip();
                if (!p.isBlank()) {
                    return p;
                }
            }
        }
        return "";
    }



    private static int toSex(final @NonNull String sex) {
        if (!sex.isEmpty()) {
            val c = sex.toUpperCase().charAt(0);
            if (c == 'M') {
                return 1;
            }
            if (c == 'F') {
                return 2;
            }
        }
        return 0;
    }











    private static @NonNull ArrayList<Fami> buildFamis(final @NonNull GedcomTree tree, final @NonNull Map<String, Indi> mapIdToIndi) {
        val famis = new ArrayList<Fami>();
        tree.getRoot().forEach(nodeFami -> {
            if (nodeFami.getObject().getTag().equals(GedcomTag.FAM)) {
                val fami = buildFami(nodeFami, mapIdToIndi);
                fami.ifPresent(famis::add);
            }
        });
        return famis;
    }

    private static @NonNull Optional<Fami> buildFami(final @NonNull TreeNode<GedcomLine> nodeFami, final @NonNull Map<String, Indi> mapIdToIndi) {
        val fami = new Fami();
        for (val c : nodeFami) {
            val child = c.getObject();
            val oindi = Optional.ofNullable(mapIdToIndi.get(child.getPointer()));
            if (oindi.isPresent()) {
                val indi = oindi.get();
                switch (child.getTag()) {
                    case HUSB, WIFE -> fami.addSpouse(indi);
                    case CHIL -> fami.addChild(indi);
                }
            }
        }
        if (fami.isEmpty()) {
            System.err.printf("empty family: %s\n", nodeFami.getObject().toString());
            return Optional.empty();
        }
        return Optional.of(fami);
    }
}
