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

import dev.cmosher01.genealogy.xy.genealogy.ChartView;
import lombok.*;

import java.awt.*;
import java.awt.geom.*;

import static dev.cmosher01.genealogy.xy.Solarized.*;

public final class MainView extends Canvas {
    private static final @NonNull Color BACKGROUND_COLOR = BASE__1_GRAY__BRT;



    private final @NonNull ZoomPanModel modelZoomPan;
    private final @NonNull ZoomPanView viewZoomPan;
    private final @NonNull DocumentModel modelDocument;
    private final @NonNull ChartView viewChart;
    private final @NonNull DragSelectionView viewDragSelection;
    private final @NonNull AxesView viewAxes;


    public MainView(final @NonNull ZoomPanModel modelZoomPan, final @NonNull ZoomPanView viewZoomPan, final @NonNull DocumentModel modelDocument, final @NonNull ChartView viewChart, final @NonNull DragSelectionView viewDragSelection, final @NonNull AxesView viewAxes) {
        super(BACKGROUND_COLOR);
        this.modelZoomPan = modelZoomPan;
        this.viewZoomPan = viewZoomPan;
        this.modelDocument = modelDocument;
        this.viewChart = viewChart;
        this.viewDragSelection = viewDragSelection;
        this.viewAxes = viewAxes;
    }



    @Override
    public void paint(final @NonNull Graphics2D g) {
        if (this.modelDocument.isPresent()) {
            this.viewZoomPan.paint(g);
            this.viewChart.paintBackground(g, viewportClip());
//            this.viewAxes.paint(g, super.getWidth(), super.getHeight());
            this.viewChart.paint(g, clip());
            this.viewDragSelection.paint(g, this.modelZoomPan);
        }
    }



    /**
     * Calculates the clipping rectangle in canvas coordinates
     * @return clipping rect
     */
    public @NonNull Rectangle2D.Double clip() {
        return this.modelZoomPan.viewportToCanvas(viewportClip());
    }
}
