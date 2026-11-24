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

import dev.cmosher01.genealogy.xy.MovablePoint;
import lombok.*;
import org.sqlite.SQLiteConfig;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;



// TODO verify handling of each column with respect to NULL values

public final class IoFtm {
    @Deprecated
    private IoFtm() {
        throw new UnsupportedOperationException();
    }



    public static @NonNull ChartModel read(final @NonNull Path path) throws SQLException, IOException {
        final List<Indi> indis;
        final List<Fami> famis;
        val config = new SQLiteConfig();
        config.setReadOnly(true);
        try (final Connection conn = config.createConnection("jdbc:sqlite:"+path.toString())) {
            val mapIdToIndi = new HashMap<String, Indi>();
            indis = buildIndis(conn, mapIdToIndi);
            famis = buildFamis(conn, Collections.unmodifiableMap(mapIdToIndi));
        }

        new Layout(indis, famis).clean();

        return new ChartModel(indis, famis, Optional.empty());
    }

    private static @NonNull List<Indi> buildIndis(final @NonNull Connection conn, final @NonNull Map<@NonNull String, @NonNull Indi> mapIdToIndi) throws SQLException, IOException {
        val indis = new ArrayList<Indi>();
        try (val select = conn.prepareStatement(getRes("Indi.sql"))) {
            try (val rs = select.executeQuery()) {
                while (rs.next()) {
                    val indi = buildIndi(rs);
                    mapIdToIndi.put(indi.key(), indi);
                    indis.add(indi);
                }
            }
        }
        return indis;
    }

    private static @NonNull Indi buildIndi(final @NonNull ResultSet rs) throws SQLException {
        // TODO do we need to save this somewhere?
//        final String pkidFact = rs.getString("pkidFact");

        val id = new IndividualId(rs.getString("pkidPerson"));

        // sex and birthdate are only used as hints by the automatic layout algorithms
        val sex = rs.getInt("sex");
        val birth = calcBirthForSort(rs.getString("birth"));

        val plaque = buildPlaque(rs);

        val pt = new MovablePoint(XyValue.create(rs.getString("xy")).get());

        return new Indi(id, sex, birth, plaque, pt);
    }

    private static @NonNull Plaque buildPlaque(final @NonNull ResultSet rs) throws SQLException {
        val name = GedcomIndiName.create(rs.getString("name"));
        val lifespan = getLifespan(rs.getString("birth"), rs.getString("death"));
        val place = getPlace(rs);
        return new Plaque(name, lifespan, place);
    }

    private static long calcBirthForSort(final String sDateBirth) {
        return FtmDate.fromFtmFactDate(sDateBirth).ym();
    }

    private static @NonNull String getPlace(final @NonNull ResultSet rs) throws SQLException {
        val birthplace = FtmPlace.fromFtmPlace(rs.getString("birthplace"));
        val anyplace = FtmPlace.fromFtmPlace(rs.getString("anyplace"));
        return (birthplace.toString().isBlank() ? anyplace : birthplace).toString();
    }

    private static @NonNull String getLifespan(final String sDateBirth, final String sDateDeath) {
        val db = FtmDate.fromFtmFactDate(sDateBirth);
        val dd = FtmDate.fromFtmFactDate(sDateDeath);
        if (db.unknown() && dd.unknown()) {
            return "";
        }
        return "("+db+"\u2013"+dd+")";
    }













    private static @NonNull List<Fami> buildFamis(final @NonNull Connection conn, final @NonNull Map<String, Indi> mapIdToIndi) throws IOException, SQLException {
        val famis = new ArrayList<Fami>();
        try (val select = conn.prepareStatement(getRes("Fami.sql"))) {
            try (val rs = select.executeQuery()) {
                int prev = -1;
                Fami fami = null;
                while (rs.next()) {
                    final int curr = rs.getInt("ID");
                    Optional<Indi> indi;
                    if (curr != prev) {
                        if (Objects.nonNull(fami)) {
                            famis.add(fami);
                        }
                        fami = new Fami();
                        indi = Optional.ofNullable(mapIdToIndi.get(rs.getString("Person1ID")));
                        if (indi.isPresent()) {
                            fami.addParent(indi.get());
                            indi.get().addAsParentIn(fami);
                        }
                        indi = Optional.ofNullable(mapIdToIndi.get(rs.getString("Person2ID")));
                        if (indi.isPresent()) {
                            fami.addParent(indi.get());
                            indi.get().addAsParentIn(fami);
                        }
                        prev = curr;
                    }
                    indi = Optional.ofNullable(mapIdToIndi.get(rs.getString("PersonID")));
                    if (indi.isPresent()) {
                        fami.addChild(indi.get());
                        indi.get().addAsChildIn(fami);
                    }
                }
                if (Objects.nonNull(fami)) {
                    famis.add(fami);
                }
            }
        }
        return famis;
    }








    public static void save(final @NonNull Path path) {
        // TODO
    }









    private static @NonNull String getRes(final @NonNull String fileName) throws IOException {
        try (val is = IoFtm.class.getResourceAsStream(fileName)) {
            if (Objects.isNull(is)) {
                throw new IOException("Cannot open resource "+fileName);
            }
            return new String(is.readAllBytes(), StandardCharsets.US_ASCII);
        }
    }
}
