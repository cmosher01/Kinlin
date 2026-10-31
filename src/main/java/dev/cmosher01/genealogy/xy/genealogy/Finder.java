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

package dev.cmosher01.genealogy.xy.genealogy;


import dev.cmosher01.genealogy.xy.*;
import dev.cmosher01.geom.Point;
import lombok.*;

import java.util.*;

// TODO hardcoded to Indi; how should we make it generic?
// TODO alternative algorithm: upon initial find(), create circular Deque of all matches
public class Finder {
    private final @NonNull List<Indi> indis;

    private @NonNull String lastQuery = "";
    private int lastIndex = -1;



    public Finder(final @NonNull List<Indi> indis) {
        this.indis = indis;

        assert !isQuery(this.lastQuery);
    }



    public Optional<Point> find(final String query) {
        Optional<Point> ret = Optional.empty();
        if (!isQuery(query)) {
            return ret;
        }
        setQuery(query);

        boolean found = false;
        int i = this.lastIndex;
        i = incToEnd(i);
        while (!found && 0 <= i && i < this.indis.size()) {
            if (matches(i)) {
                found = true;
            } else {
                i = incToEnd(i);
            }
        }
        if (found) {
            ret = foundAt(i);
        } else {
            clear();
        }
        return ret;
    }

    public Optional<Point> next() {
        Optional<Point> ret = Optional.empty();
        if (!isQuery(this.lastQuery)) {
            return ret;
        }

        boolean found = false;
        int i = this.lastIndex;
        i = incModulo(i);
        int sane = 1_000_000;
        while (!found && 0 <= --sane) {
            if (matches(i)) {
                found = true;
            } else {
                i = incModulo(i);
            }
        }
        if (found) {
            ret = foundAt(i);
        } else {
            // should never happen
            clear();
        }
        return ret;
    }

    public Optional<Point> prev() {
        Optional<Point> ret = Optional.empty();
        if (!isQuery(this.lastQuery)) {
            return ret;
        }

        boolean found = false;
        int i = this.lastIndex;
        i = decModulo(i);
        int sane = 1_000_000;
        while (!found && 0 <= --sane) {
            if (matches(i)) {
                found = true;
            } else {
                i = decModulo(i);
            }
        }
        if (found) {
            ret = foundAt(i);
        } else {
            // should never happen
            clear();
        }
        return ret;
    }



    private Optional<Point> foundAt(final int i) {
        this.lastIndex = i;
        return Optional.of(this.indis.get(i).point());
    }

    private void clear() {
        setQuery("");
        assert !isQuery(this.lastQuery);
    }

    private void setQuery(String query) {
        if (Objects.isNull(query)) {
            query = "";
        }
        this.lastQuery = query.strip().toLowerCase();
        this.lastIndex = -1;
    }

    private boolean matches(final int i) {
        final var indi = this.indis.get(i).simpleName().toLowerCase();
        return indi.contains(this.lastQuery);
    }

    // returns -1 to indicate gone past end
    private int incToEnd(int i) {
        ++i;
        if (i <= 0) {
            return 0;
        }
        if (this.indis.size() <= i) {
            return -1;
        }
        return i;
    }

    private int incModulo(int i) {
        ++i;
        if (this.indis.size() <= i) {
            i = 0;
        }
        return i;
    }

    private int decModulo(int i) {
        if (i <= 0) {
            i = this.indis.size();
        }
        --i;
        return i;
    }



    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean isQuery(final String query) {
        return Objects.nonNull(query) && !query.isBlank();
    }
}
