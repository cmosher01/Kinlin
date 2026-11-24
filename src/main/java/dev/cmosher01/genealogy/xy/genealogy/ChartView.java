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

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

import static dev.cmosher01.genealogy.xy.Solarized.*;

@RequiredArgsConstructor
public class ChartView {
    private static final boolean ALWAYS_SHOW_ALL_FAMILY_LINES = false;

    private static final boolean BOUNDS_FILL = true;
    private static final @NonNull Color BOUNDS_FILL_COLOR = BASE__2_BEIGE_DRK;

    private static final boolean BOUNDS_DRAW = true;
    private static final @NonNull Color BOUNDS_DRAW_COLOR = BASE_01_GRAY__DRK;
    private static final @NonNull Stroke BOUNDS_DRAW_STROKE = new BasicStroke(5F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);


    private final @NonNull DocumentModel doc;
    private final ZoomPanModel modelZoomPan;
    private final @NonNull List<@NonNull IndiView> viewIndis = new ArrayList<>();
    private final @NonNull List<@NonNull FamiView> viewFamis = new ArrayList<>();



    public void paintBackground(final @NonNull Graphics2D g, final @NonNull Rectangle2D clip) {
        if (this.doc.isEmpty()) {
            return;
        }

        if (BOUNDS_FILL) {
            g.setColor(BOUNDS_FILL_COLOR);
            val canvClip = Awts.rect(this.modelZoomPan.viewportToCanvas(clip));
            val intersection = Rect.intersection(canvClip, this.doc.model().boundsOutset());
            if (intersection.isPresent()) {
                g.fill(Awts.rect(intersection.get()));
            }
        }
        if (BOUNDS_DRAW) {
            g.setStroke(BOUNDS_DRAW_STROKE);
            g.setColor(BOUNDS_DRAW_COLOR);
            g.draw(Awts.rect(this.doc.model().boundsOutset()));
        }
    }

    public void paint(final @NonNull Graphics2D g, final @NonNull Rectangle2D clip) {
        g.setStroke(Swings.simpleStroke());
        g.setColor(Solarized.BASE_02_BLACK_BRT);

        if (ALWAYS_SHOW_ALL_FAMILY_LINES) {
            this.viewFamis.forEach(i -> {
                if (Awts.rect(i.model().getBounds()).intersects(clip)) {
                    i.paint(g, clip);
                }
            });
        } else {
            val setFami = Collections.<FamiView>newSetFromMap(new IdentityHashMap<>());
            this.viewIndis.forEach(i -> {
                if (Awts.rect(i.model().bounds()).intersects(clip)) {
                    setFami.addAll(i.getFamis());
                }
            });
            setFami.forEach(fv -> fv.paint(g, clip));
        }

        this.viewIndis.forEach(i -> {
            if (Awts.rect(i.model().bounds()).intersects(clip)) {
                i.paint(g);
            }
        });
    }

    public void refresh() {
        this.viewIndis.clear();
        this.viewFamis.clear();

        val mapFamiToView = new IdentityHashMap<Fami, FamiView>();

        if (this.doc.isPresent()) {
            this.doc.model().famis().forEach(f -> {
                val fv = new FamiView(f);
                this.viewFamis.add(fv);
                mapFamiToView.put(f, fv);
            });

            this.doc.model().indis().forEach(i -> {
                val iv = new IndiView(i, this.modelZoomPan);
                this.viewIndis.add(iv);
                iv.addFamiViewsFrom(mapFamiToView);
            });
        }
    }
}
