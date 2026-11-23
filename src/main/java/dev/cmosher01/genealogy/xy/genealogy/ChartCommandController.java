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

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.*;

@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "OptionalIsPresent"})
public class ChartCommandController {
    private final @NonNull DocumentModel model;
    private final @NonNull ZoomPanModel zp;
    private final @NonNull MainView viewMain;
    private final @NonNull MenuView viewMenu;
    private final @NonNull FrameView viewFrame;
    private final @NonNull StatusBarView viewStatusBar;

    private @NonNull String lastFind = "";
    private @NonNull Optional<Finder> finder = Optional.empty();
    private @NonNull final Jumper jumper = new Jumper();


    public ChartCommandController(final @NonNull ZoomPanModel zp, final @NonNull DocumentModel model, final @NonNull MainView viewMain, final @NonNull MenuView viewMenu, final @NonNull FrameView viewFrame, final @NonNull StatusBarView viewStatusBar) {
        this.zp = zp;
        this.model = model;
        this.viewMain = viewMain;
        this.viewMenu = viewMenu;
        this.viewFrame = viewFrame;
        this.viewStatusBar = viewStatusBar;
    }



    public void reset() {
        val c = Awts.rect(this.viewMain.clip()).center();
        this.zp.resetZoom();
        panTo(c);

        this.viewMain.repaint();
        this.viewStatusBar.refresh();
    }

    public void center() {
        panTo(Awts.rect(this.model.bounds()).center());
    }

    public void fit() {
        val v = this.viewMain.viewportClip();
        val c = this.model.boundsOutset();

        val w = v.getWidth()/c.getWidth();
        val h = v.getHeight()/c.getHeight();
        val f = Math.min(w,h);
        this.zp.setZoom(f);

        center();
    }



    public void find() {
        val result = Optional.ofNullable(JOptionPane.showInputDialog(this.viewMain,
            "Enter name to search for:", "Find", JOptionPane.QUESTION_MESSAGE, null, null,
            this.lastFind));
        if (result.isPresent()) {
            val s = result.get().toString().strip();
            if (!s.isBlank()) {
                this.lastFind = s;
                if (this.finder.isEmpty()) {
                    this.finder = Optional.of(new Finder(this.model.model().indis()));
                }
                val optFoundAt = this.finder.get().find(s);
                if (optFoundAt.isPresent()) {
                    panTo(optFoundAt.get());
                }
            }
        }
    }

    public void findNext() {
        if (this.finder.isPresent()) {
            val optFoundAt = this.finder.get().next();
            if (optFoundAt.isPresent()) {
                panTo(optFoundAt.get());
            }
        }
    }

    public void findPrevious() {
        if (this.finder.isPresent()) {
            val optFoundAt = this.finder.get().prev();
            if (optFoundAt.isPresent()) {
                panTo(optFoundAt.get());
            }
        }
    }



    public void jump() {
        // checks if user changed the selection since last time
        this.jumper.selectionDelta(this.model.model().getSelection().center());

        val jumpTo = this.jumper.jump(Awts.rect(this.viewMain.clip()).center());
        if (jumpTo.isPresent()) {
            panTo(jumpTo.get());
        }
    }



    public void undo() {
        this.model.undo();
        this.viewFrame.refresh();
        this.viewMain.repaint();
        this.viewMenu.refresh();
    }

    public void redo() {
        this.model.redo();
        this.viewFrame.refresh();
        this.viewMain.repaint();
        this.viewMenu.refresh();
    }

    public void nudge() {
        if (this.model.getModel().isEmpty()) {
            return;
        }

        val chart = this.model.getModel().get();
        val optSelectionBounds = chart.selectionBounds();
        if (chart.hasSelected() && optSelectionBounds.isPresent()) {
            val selectionBounds = optSelectionBounds.get();


            //  Nudge:
            //  Move selection near (graphically on the chart) to their
            //  "closest" (relationship-wise) unselected relative.

            //  maxR = empty
            //  maxd = -INF
            //  for each indi S in the selection
            //      for each related indi (parent, sibling, spouse, child) R of S
            //          if R is not in the selection
            //              find distance d from S to R
            //              if (maxd < d)
            //                  maxR = R
            //                  maxd = d
            //  if maxR is present
            //      move selection to below maxR
            //      scroll display to center upon maxR

            var maxR = Optional.<Indi>empty();
            var maxd = Double.NEGATIVE_INFINITY;
            val selectedIndis = chart.getSelectedIndis();
            for (val S : selectedIndis) {
                for (val R : S.getRelatives()) {
                    if (!R.selected()) {
                        val d = R.distanceFrom(S);
                        if (maxd < d.n()) {
                            maxR = Optional.of(R);
                            maxd = d.n();
                        }
                    }
                }
            }
            if (maxR.isPresent()) {
                val R = maxR.get();
                val S = selectionBounds.center();

                val dx = R.point().x().u() - S.x().u();
                val dy = (R.point().y().u() + /*TODO familyChart.metrics().getGenDistance()*/80D) - S.y().u();
                val D = new Point2D.Double(dx, dy);

                selectedIndis.forEach(i -> i.move(D, this.model.getModel().get().grid()));

                // scroll display to relative we just moved to
                // (not the group we just moved)
                panTo(R.point());
            }
        }
    }



    public void exportPdf() {
        // TODO
    }

    public void exportMod() {
        // TODO
    }

    public void exportAll() {
        // TODO
    }

    public void gridSettings() {
        this.model.getModel().get().gridSettings(this.viewFrame);
    }






    private void panTo(final @NonNull Point at) {
        val d = Awts.rect(this.viewMain.clip()).center() - at;
        val p  = this.zp.viewportToCanvas(this.zp.getPan());
        this.zp.panTo(this.zp.canvasToViewport(p + d));
        this.viewMain.repaint();
        this.viewStatusBar.refresh();
    }

    public void selectAll() {
        this.model.selectAll();
        this.viewFrame.refresh();
        this.viewMain.repaint();
    }
}
