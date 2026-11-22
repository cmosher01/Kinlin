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
import java.util.Optional;

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

        final var w = v.getWidth()/c.getWidth();
        final var h = v.getHeight()/c.getHeight();
        final var f = Math.min(w,h);
        this.zp.setZoom(f);

        center();
    }



    public void find() {
        final var result = Optional.ofNullable(JOptionPane.showInputDialog(this.viewMain,
            "Enter name to search for:", "Find", JOptionPane.QUESTION_MESSAGE, null, null,
            this.lastFind));
        if (result.isPresent()) {
            final var s = result.get().toString().strip();
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
        // TODO implement nudge
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
