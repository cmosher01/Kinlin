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
import dev.cmosher01.util.ModificationTracker;
import lombok.*;
import nu.mine.mosher.gedcom.GedcomTree;

import java.awt.geom.Point2D;
import java.util.*;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class ChartModel {
    private static final @NonNull Outset BOUNDS_OUTSET = new Outset(new GrowTerm(100D)); // TODO



    private final @NonNull List<Indi> indis;
    private final @NonNull List<Fami> famis;

    @Getter private final @NonNull Optional<GedcomTree> tree;

    private @NonNull Rect bounds = Rect.CENTERED_UNIT_SQUARE;

    @Getter private final @NonNull IndiSelectionModel selection;

    private final @NonNull ModificationTracker modtrack = new ModificationTracker();
    private final @NonNull Grid grid = new Grid();


    public ChartModel(final @NonNull List<@NonNull Indi> indis, final @NonNull List<@NonNull Fami> famis, final @NonNull Optional<GedcomTree> tree) {
        this.indis = indis;
        this.famis = famis;
        this.tree = tree;
        this.selection = new IndiSelectionModel(indis);
    }



    public void refresh(final @NonNull FontModel fonts) {
        refreshIndis(fonts);
        refreshFamis(fonts);
        updateBounds();
    }

    private void refreshIndis(final @NonNull FontModel fonts) {
        this.indis.forEach(i -> i.refresh(fonts));
    }

    private void refreshFamis(final @NonNull FontModel fonts) {
        this.famis.forEach(f -> f.refresh(fonts));
    }



    public void updateBounds() {
        this.bounds = bounds(this.indis);
    }

    private static @NonNull Rect bounds(final @NonNull List<@NonNull Indi> indis) {
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

    public @NonNull Optional<Indi> getAt(final @NonNull Point at) {
        for (val i : this.indis.reversed()) {
            if (i.bounds().contains(at)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    @NonNull List<@NonNull Indi> indis() {
        return this.indis;
    }

    @NonNull List<@NonNull Fami> famis() {
        return this.famis;
    }

    public void flip() {
        this.indis.forEach(Indi::flip);
    }

    public void saveXyToTree() {
        this.indis.forEach(Indi::saveXyToTree);
    }





    public void selectIndi(final @NonNull Indi indi, final boolean select) {
        this.selection.select(indi, select);
    }

    public void anchorMovedIndis() {
        this.selection.anchor();
        this.modtrack.make(this.selection.takeModifications());
    }

    public void undo() {
        this.modtrack.undo();
        updateBounds();
    }

    public void redo() {
        this.modtrack.redo();
        updateBounds();
    }

    public boolean canUndo() {
        return this.modtrack.canUndo();
    }

    public boolean canRedo() {
        return this.modtrack.canRedo();
    }

    public void calcGrid() {
        Grids.createFromIndis(this.indis, this.grid);
    }

    public boolean hasSelected() {
        return this.selection.hasSelected();
    }

    public void selectAll() {
        this.selection.selectAll();
    }

    public void moveSelection(final @NonNull Point2D.Double d) {
        this.selection.move(d, this.grid);
    }

    public void gridSettings(final @NonNull FrameView viewFrame) {
        new GridSettingsDialog(viewFrame, this.grid).dialog();
    }

    public @NonNull Optional<Rect> selectionBounds() {
        return this.selection.bounds();
    }

    public @NonNull List<@NonNull Indi> getSelectedIndis() {
        return this.selection.getSelection();
    }

    public @NonNull Grid grid() {
        return this.grid;
    }
}
