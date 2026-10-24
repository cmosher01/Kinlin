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
import dev.cmosher01.geom.Awts;
import lombok.*;

import java.awt.geom.Point2D;
import java.util.Optional;

@RequiredArgsConstructor
public class ChartController {
    private final ZoomPanModel zp;
    private final DocumentModel model;

    private Optional<Indi> optItem = Optional.empty();
    private Optional<Point2D.Double> optptDragPivot = Optional.empty();
    private boolean wasSelected;



    public boolean want(final Point2D.Double at) {
        val optModel = this.model.getModel();
        this.optItem = optModel.isPresent() ? optModel.get().getAt(Awts.point(at)) : Optional.empty();
        val want = this.optItem.isPresent();
        if (want) {
            this.optptDragPivot = Optional.of(at);
        }
        return want;
    }

    public boolean has() {
        return this.optItem.isPresent();
    }

    public void press() {
        if (has()) {
            val item = this.optItem.get();
            this.wasSelected = item.selected();
            if (!this.wasSelected) {
                val chart = this.model.getModel().get();
                chart.getSelection().select(item, true);
            }
        }
    }

    public void drag(final Point2D.Double at) {
        if (has()) {
            val chart = this.model.getModel().get();
            val d = Swings.delta(this.optptDragPivot.get(), at);
//            System.out.printf("drag by (%.1f,%.1f)\n", d.getX(), d.getY());
            if (d.x != 0 || d.y != 0) {
                chart.getSelection().move(d);
                if (chart.getSelection().isModified()) {
                    this.model.setModified();
                } else {
                    this.model.refreshModified();
                }
                chart.updateBounds();
                this.zp.setZoomOutMinFromBounds(this.model.bounds());
                this.optptDragPivot = Optional.of(at);
            }
        }
    }

    public void release(final boolean dragged) {
        val optModel = this.model.getModel();
        if (optModel.isPresent()) {
            val chart = optModel.get();
            if (has()) {
                if (dragged) {
                    chart.getSelection().anchor();
                    chart.updateBounds();
                    this.zp.setZoomOutMinFromBounds(this.model.bounds());
                } else if (this.wasSelected) {
                    val item = this.optItem.get();
                    chart.getSelection().select(item, false);
                }
                this.optItem = Optional.empty();
                this.optptDragPivot = Optional.empty();
            } else if (!dragged) {
                chart.getSelection().clear();
            }
        }
    }
}
