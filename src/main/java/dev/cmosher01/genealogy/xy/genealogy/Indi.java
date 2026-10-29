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
import java.util.ArrayList;

@RequiredArgsConstructor
public class Indi {
    private final @NonNull IndividualId id;

    @Getter private final int sex;
    @Getter private final long birth;

    private final @NonNull ArrayList<@NonNull Fami> rFamiChildIn = new ArrayList<>(1);
    private final @NonNull ArrayList<@NonNull Fami> rFamiParentIn = new ArrayList<>(1);

    private final @NonNull Plaque plaque;

    private final @NonNull MovablePoint pt;
    private boolean selected;
    private FontModel cachedFonts;


    public void addAsChildIn(final @NonNull Fami fami) {
        this.rFamiChildIn.add(fami);
    }

    public void addAsParentIn(final @NonNull Fami fami) {
        this.rFamiParentIn.add(fami);
    }





    public @NonNull String key() {
        return this.id.key();
    }

    public void refresh(final @NonNull FontModel fonts) {
        this.plaque.refresh(fonts);
        this.cachedFonts = fonts;
    }


    public @NonNull Rect bounds() {
        return new Rect(this.plaque.dim(), this.pt.get());
    }

    @NonNull Plaque plaque() {
        return this.plaque;
    }

    @NonNull MovablePoint getMovablePoint() {
        return this.pt;
    }

    public @NonNull Point point() {
        return this.pt.get();
    }

    public double xOffsetForParent(final @NonNull Fami parent) {
        return this.pt.get().x().u()+offset(this.rFamiChildIn.indexOf(parent));
    }

    public double yOffsetForParent(final @NonNull Fami parent) {
        val h = new MoveTerm(this.plaque().dim().h().half().n());
        return (this.pt.get().y()-h).u()-offset(this.rFamiChildIn.indexOf(parent));
    }

    private static final double PARENT_OFFSET = 3D;

    private static double offset(final int i) {
        return i * PARENT_OFFSET;
    }


    public void layOut(final @NonNull Point at) {
        this.pt.layoutMoveTo(at);
    }

    public void move(final @NonNull Point2D.Double d) {
        this.pt.move(new Xlation(new MoveTerm(d.getX()), new MoveTerm(d.getY())));
        refreshFamis(this.cachedFonts);
    }

    private void refreshFamis(final @NonNull FontModel fonts) {
        this.rFamiParentIn.forEach(i -> i.refresh(fonts));
        this.rFamiChildIn.forEach(i -> i.refresh(fonts));
    }

    public boolean isModified() {
        return this.pt.dirty();
    }
    public void anchor() {
        this.pt.anchor();
    }
    public void flip() {
        this.pt.flip();
    }

    public boolean selected() {
        return this.selected;
    }
    public void select(final boolean selected) {
        this.selected = selected;

        this.rFamiChildIn.forEach(f -> f.select(selected));
        this.rFamiParentIn.forEach(f -> f.select(selected));
    }

    public boolean intersects(final Rectangle2D.Double r) {
        return Rect.intersect(bounds(), Awts.rect(r));
    }

    public String simpleName() {
        return this.plaque.simpleName();
    }

    public boolean hadOriginalXY() {
        return this.pt.hasOriginal();
    }
}
