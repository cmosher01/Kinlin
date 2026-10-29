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
import lombok.NonNull;

import java.awt.*;

import static dev.cmosher01.geom.Awts.rect;

public class IndiView {
    private static final double MIN_ZOOM_DETAIL = 0.08D;
    private static final Color SELECTED_FILL_COLOR = Swings.mix(Solarized.MAGENTA, Solarized.BASE__2_BEIGE_DRK, .2);
    private static final Color NONSELECTED_FILL_COLOR = Swings.mix(Solarized.BASE__3_BEIGE_BRT, Solarized.BASE__1_GRAY__BRT, .6);

    private final @NonNull Indi model;
    private final @NonNull ZoomPanModel modelZoomPan;

    public IndiView(final @NonNull Indi indi, final @NonNull ZoomPanModel modelZoomPan) {
        this.model = indi;
        this.modelZoomPan = modelZoomPan;
    }

    @NonNull Indi model() {
        return this.model;
    }

    private static final boolean SHOW_PLAQUE_BG = false;

    public void paint(final Graphics2D g) {
        if (this.model.selected()) {
            g.setColor(SELECTED_FILL_COLOR);
        } else {
            if (showDetails()) {
                if (SHOW_PLAQUE_BG) {
                    g.setColor(Solarized.BASE__3_BEIGE_BRT);
                } else {
                    g.setColor(Solarized.BASE__2_BEIGE_DRK);
                }
            } else {
                g.setColor(NONSELECTED_FILL_COLOR);
            }
        }

        g.fill(rect(this.model.bounds()));

        if (showDetails()) {
            if (SHOW_PLAQUE_BG) {
                if (this.model.selected()) {
                    g.setColor(Solarized.MAGENTA);
                } else {
                    g.setColor(Solarized.BASE_00_GRAY__DIM);
                }

                g.setStroke(Swings.simpleStroke());
                g.draw(rect(new Rect(this.model().plaque().dim(), this.model().point())));
            }

            if (this.model.selected()) {
                g.setColor(Solarized.MAGENTA);
            } else {
                g.setColor(Solarized.BASE_02_BLACK_BRT);
            }

            this.model().plaque().paint(g, this.model().point());
        }
    }

    private boolean showDetails() {
        return MIN_ZOOM_DETAIL <= this.modelZoomPan.zoomFactor();
    }
}
