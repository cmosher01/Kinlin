/*
 *     Copyright © 2018-2026, Christopher Alan Mosher, New York, New York, USA, <cmosher01@gmail.com>.
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


import dev.cmosher01.util.ModificationTracker;
import lombok.*;

import java.util.*;


public class MoveIndis implements ModificationTracker.Modification {
    private final @NonNull Set<@NonNull IndiMovement> movements;

    public MoveIndis(final @NonNull Collection<@NonNull IndiMovement> movements) {
        this.movements = Set.copyOf(movements);
    }

    @Override
    public void make() {
    }

    @Override
    public void undo() {
        flip();
    }

    @Override
    public void redo() {
        flip();
    }



    private void flip() {
        this.movements.forEach(IndiMovement::flip);
    }
}
