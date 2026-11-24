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

package dev.cmosher01.genealogy.xy;

import lombok.*;

import java.awt.geom.Point2D;
import java.util.Objects;

@RequiredArgsConstructor
public class ZoomPanMouseController {
    private final @NonNull ZoomPanModel zp;

    private Point2D.Double ptDragPivot;



    public void press(final @NonNull Point2D.Double at) {
        this.ptDragPivot = at;
    }

    public void drag(final @NonNull Point2D.Double at) {
        if (Objects.nonNull(this.ptDragPivot)) {
            val d = Swings.delta(this.ptDragPivot, at);
            this.zp.pan(d.getX(), d.getY());
            this.ptDragPivot = at;
        }
    }

    public void release() {
        this.ptDragPivot = null;
    }



    public void rotate(final @NonNull Point2D.Double at, final int clicks) {
        this.zp.zoom(clicks, at.getX(), at.getY());
    }
}
