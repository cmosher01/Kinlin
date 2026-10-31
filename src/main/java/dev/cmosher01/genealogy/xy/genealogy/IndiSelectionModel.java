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

@RequiredArgsConstructor
public class IndiSelectionModel implements Selectable<Indi> {
    private final List<Indi> universe;

    /**
     * Set of currently selected items. Redundant with Indi::selected property.
     */
    private final Set<Indi> selection = Collections.newSetFromMap(new IdentityHashMap<>());


    @Override
    public void clear() {
        this.selection.forEach(indi -> indi.select(false));
        this.selection.clear();
    }

    @Override
    public void select(final Indi indi, final boolean select) {
        indi.select(select);
        if (select) {
            this.selection.add(indi);
        } else {
            this.selection.remove(indi);
        }
    }

    @Override
    public void setFromRectangle(final Rectangle2D.Double r) {
        for (val indi : this.universe.reversed()) {
            val hit = indi.intersects(r);
            select(indi, hit);
        }
    }

    @Override
    public void move(final Point2D.Double delta) {
        this.selection.forEach(indi -> indi.move(delta));
    }

    @Override
    public void anchor() {
        this.selection.forEach(Indi::anchor);
    }

    @Override
    public boolean isModified() {
        for (val indi : this.selection) {
            if (indi.isModified()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<Point> center() {
        if (this.selection.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(Rect.bounds(this.selection.stream().map(Indi::bounds).toList()).center());
    }
}
