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
import lombok.NonNull;

import java.awt.*;

public class IndiView {
    private static final double MIN_ZOOM_DETAIL = 0.1D;
    private static final Color SELECTED_FILL_COLOR = Swings.mix(Solarized.MAGENTA, Solarized.BASE__2_BEIGE_DRK, .2);

    private final @NonNull Indi model;
    private final @NonNull ZoomPanModel modelZoomPan;

    public IndiView(final @NonNull Indi indi, final @NonNull ZoomPanModel modelZoomPan) {
        this.model = indi;
        this.modelZoomPan = modelZoomPan;
    }

    Indi model() {
        return this.model;
    }

    public void paint(final Graphics2D g) {
        if (this.model.selected()) {
            g.setColor(SELECTED_FILL_COLOR);
        } else {
            if (showDetails()) {
                g.setColor(Solarized.BASE__3_BEIGE_BRT);
            } else {
                g.setColor(Solarized.BASE__1_GRAY__BRT);
            }
        }

        g.fill(Awts.rect(this.model.bounds()));

        if (showDetails()) {
//            g.setStroke(Swings.simpleStroke());
//            g.setColor(Solarized.BASE__0_GRAY__LGT);
//            g.draw(this.model.rect());

//            g.setFont(FONT);
            if (this.model.selected()) {
                g.setColor(Solarized.MAGENTA);
            } else {
                g.setColor(Solarized.BASE_02_BLACK_BRT);
            }
            this.model().plaque().paint(g, this.model().point());
//            g.drawString(this.model.tag(), (float)this.model.rect().x+2F, (float)this.model.rect().y+18F);
        }
    }

    private boolean showDetails() {
        return MIN_ZOOM_DETAIL <= this.modelZoomPan.zoomFactor();
    }
}
