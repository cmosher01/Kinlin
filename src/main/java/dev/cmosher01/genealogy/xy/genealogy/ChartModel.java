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
import dev.cmosher01.geom.*;
import lombok.*;

import java.awt.geom.*;
import java.util.*;

public class ChartModel {
    private static final Outset BOUNDS_OUTSET = new Outset(new GrowTerm(100D)); // TODO



    private final @NonNull List<Indi> indis;
    private final @NonNull List<Fami> famis;

    private Rect bounds;

    @Getter
    private final Selectable<Indi> selection;



    public ChartModel(final @NonNull List<Indi> indis, final @NonNull List<Fami> famis) {
        this.indis = indis;
        this.famis = famis;
        this.selection = new IndiSelectionModel(indis);
        this.bounds = bounds(indis);
    }

    public void updateBounds() {
        this.bounds = bounds(this.indis);
    }

    private static Rect bounds(final @NonNull List<Indi> indis) {
        return Rect.bounds(indis.stream().map(Indi::bounds).toList());
    }

    public @NonNull Rect bounds() {
        return this.bounds;
    }

    public @NonNull Rect boundsOutset() {
        return this.bounds + BOUNDS_OUTSET;
    }



    public boolean isEmpty() {
        return this.indis.isEmpty();
    }

    public boolean isPresent() {
        return !isEmpty();
    }

    public boolean isModified() {
        for (val i : this.indis) {
            if (i.isModified()) {
                return true;
            }
        }
        return false;
    }

    public Optional<Indi> getAt(final Point at) {
        for (val i : this.indis.reversed()) {
            if (i.bounds().contains(at)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    List<Indi> indis() {
        return this.indis;
    }


    public void flip() {
        this.indis.forEach(Indi::flip);
    }
}
