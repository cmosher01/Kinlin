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
import lombok.*;

import javax.swing.*;
import java.awt.geom.*;

public class ChartCommandController {
    private final @NonNull DocumentModel model;
    private final @NonNull ZoomPanModel zp;
    private final @NonNull MainView viewMain;
    private final @NonNull StatusBarView viewStatusBar;

    public ChartCommandController(final @NonNull ZoomPanModel zp, final @NonNull DocumentModel model, final @NonNull MainView viewMain, final @NonNull StatusBarView viewStatusBar) {
        this.zp = zp;
        this.model = model;
        this.viewMain = viewMain;
        this.viewStatusBar = viewStatusBar;
    }

    public void reset() {
        val c_clip = this.viewMain.clip();
        val v_clip = this.viewMain.viewportClip();
        this.zp.resetZoom(c_clip.getCenterX(), c_clip.getCenterY(), v_clip.getWidth(), v_clip.getHeight());
        this.viewMain.repaint();
        this.viewStatusBar.refresh();
    }

    public void center() {
        val c_bounds = this.model.bounds();
        final var c_p  = new Point2D.Double(c_bounds.getCenterX(), c_bounds.getCenterY());
        val c_clip = this.viewMain.clip();
        final var c_c  = new Point2D.Double(c_clip.getCenterX(), c_clip.getCenterY());
        final var v_l  = new Point2D.Double(this.zp.panX(), this.zp.panY());
        final var c_l  = this.zp.viewportToCanvas(v_l);
        final var c_d  = new Point2D.Double(c_c.getX()-c_p.getX(), c_c.getY()-c_p.getY());
        final var c_lp = new Point2D.Double(c_l.getX()+c_d.getX(), c_l.getY()+c_d.getY());
        final var v_lp = this.zp.canvasToViewport(c_lp);
        this.zp.panTo(v_lp.getX(), v_lp.getY());
        this.viewMain.repaint();
    }

    public void fit() {
        val v = this.viewMain.viewportClip();
        val c = this.model.boundsOutset();

        final var w = v.getWidth ()/c.getWidth();
        final var h = v.getHeight()/c.getHeight();
        final var f = Math.min(w,h);
        this.zp.setZoom(f);

        center();
    }
}
