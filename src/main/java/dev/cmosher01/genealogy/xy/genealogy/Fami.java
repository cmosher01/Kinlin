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

import dev.cmosher01.geom.*;
import lombok.*;

import java.util.*;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Fami {
    private double duDistanceForDescentOnParentBars = 40D; // TODO 80%? of plaque max width?
    private double dyDescentMiddleAboveChild = 8D; // TODO this.metricsFont.getChildBarHeight() / 2.0D;
    private double dxChildrenBarEndZone = 50D; // TODO this.metricsChart.medianMinimumDistanceToNeighborScaled() / 4.0D;
    private double dyBarChild = 25D; // TODO



    private final @NonNull ArrayList<@NonNull Indi> rIndiSpouses = new ArrayList<>(2);
    private final @NonNull ArrayList<@NonNull Indi> rIndiChildren = new ArrayList<>(2);

    /*  ==+==              */ @Getter private @NonNull Optional<Bars> barSpouses = Optional.empty();
    /*    |                */ @Getter private @NonNull Optional<OrthogonalLine> descentBarParent = Optional.empty();
    /*    +-----+          */ @Getter private @NonNull Optional<OrthogonalLine> descentBarMiddle = Optional.empty();
    /*          |          */ @Getter private @NonNull Optional<OrthogonalLine> descentBarChilds = Optional.empty();
    /*  +----+--+--+----+  */ @Getter private @NonNull Optional<OrthogonalLine> barChildren = Optional.empty();
    /*  |    |     |    |  */ @Getter private @NonNull ArrayList<OrthogonalLine> rBarChild = new ArrayList<>();

    @Getter private Rect bounds;

    private boolean selected;



    public void addSpouse(final @NonNull Indi indi) {
        this.rIndiSpouses.add(indi);
        indi.addAsSpouseTo(this);
    }

    public void addChild(final @NonNull Indi indi) {
        this.rIndiChildren.add(indi);
        indi.addAsChildTo(this);
    }




    public boolean isEmpty() {
        return this.rIndiSpouses.isEmpty() && this.rIndiChildren.isEmpty();
    }

    public void refresh() {
        this.barSpouses = barsFromParents(this.rIndiSpouses);

        if (!this.rIndiChildren.isEmpty()) {
            this.barChildren = Optional.of(buildChildsBar(this.rIndiChildren));
            this.rBarChild = buildChildBars(this.rIndiChildren, this.barChildren.get());
        }

        if (this.barSpouses.isPresent() && this.barChildren.isPresent()) {
            final Point descentBarParentStart = calcDescentBarParentStart(this.barSpouses.get(), this.barChildren.get().midpoint());
            final Point descentBarChildsStart = calcDescentBarChildsStart(descentBarParentStart.x(), this.barChildren.get());
            final Coord descentBarMiddleY = calcDescentBarMiddleY(descentBarChildsStart.y(), this.rIndiChildren.size());

            buildDescentBars(descentBarParentStart, descentBarMiddleY, descentBarChildsStart);
        }

        calcBounds();
    }




    public boolean selected() {
        return this.selected;
    }
    public void select(final boolean selected) {
        this.selected = selected;
    }



    private static @NonNull Optional<Bars> barsFromParents(final @NonNull List<Indi> parents) {
        final Optional<Bars> ret;
        if (parents.isEmpty()) {
            // no parents, no bar
            ret = Optional.empty();
        } else if (parents.size() == 1) {
            // only one parent, degenerate case zero-length bar
            val pt = parents.getFirst().point(); // TODO TwoUnknownSpouses.ged (can we move point for subsequent spouses?)
            ret = Optional.of(buildSpousesBar(pt, pt));
        } else {
            // two parents, nominal case
            val pt0 = parents.get(0).point();
            val pt1 = parents.get(1).point();
            ret = Optional.of(buildSpousesBar(pt0, pt1));
        }
        return ret;
    }

    private static @NonNull Bars buildSpousesBar(final @NonNull Point pt0, final @NonNull Point pt1) {
        val center = new Line(pt0, pt1);
        return new Bars(center, new Dim(4D));
    }






    // throws if given empty list
    private @NonNull OrthogonalLine buildChildsBar(final @NonNull List<Indi> rChild) {
        if (rChild.isEmpty()) {
            throw new IllegalStateException();
        }

        // get min/max x (left/right), and min y (top) values of children
        double xLft = Double.MAX_VALUE;
        double xRgt = -Double.MAX_VALUE;
        double yTop = Double.MAX_VALUE;
        for (val child : rChild) {
            val x = xChild(child);
            if (x < xLft) {
                xLft = x;
            }
            if (xRgt < x) {
                xRgt = x;
            }

            val y = yChild(child);
            if (y < yTop) {
                yTop = y;
            }
        }
        yTop -= dyBarChild;

        return new OrthogonalLine(OrthogonalLine.Orient.H, new Coord(xLft), new Coord(xRgt), new Coord(yTop));
    }

    private @NonNull ArrayList<OrthogonalLine> buildChildBars(final @NonNull List<Indi> rChild, final @NonNull OrthogonalLine barChildren) {
        val rBarChild = new ArrayList<OrthogonalLine>(rChild.size());
        for (val child : rChild) {
            val x = new Coord(xChild(child));
            val y = new Coord(yChild(child));
            rBarChild.add(new OrthogonalLine(OrthogonalLine.Orient.V, barChildren.pos(), y, x));
        }
        return rBarChild;
    }












    /**
     * Calculate the point on the (bottom) marriage bar where the descentBarParent starts.
     * For short bars, use the midpoint.
     * For longer bars (where the two parents are for from each other), use a point close to
     * one of them (the one that is nearest to the children).
     *
     * @param barParents the parents' marriage bar
     * @param midpointChild the midpoint of the horizontal childsBar
     * @return descentBarParent starting point
     */
    private @NonNull Point calcDescentBarParentStart(final Bars barParents, final Point midpointChild) {
        // Figure out which parent is closest to the child bar midpoint
        // (and calculate the parent bar start point, near that parent)
        final Line bar;
        if (midpointChild.distance(barParents.center().a()) < midpointChild.distance(barParents.center().b())) {
            bar = new Line(barParents.center().a(), barParents.center().b());
        } else {
            bar = new Line(barParents.center().b(), barParents.center().a());
        }

        // Calculate ratio r of distance d along the length D of the marriage bar,
        // to the full length, from the near
        // parent towards the far parent, at which the descent line will start.
        // The ratio should never be more than halfway from near to far.

        // total distance (use min to prevent division by zero later)
        final double D = Math.max(0.1, bar.length().n());

        // absolute distance along line from starting point
        final double d = this.duDistanceForDescentOnParentBars;

        return bar.section(new Proportion(Math.min(d/D, 0.5D)));
    }

    /*
        Ideally we'd like to simply start at the childsBar midpoint, but this doesn't
        always look visually appealing. We move the starting point towards the parent's
        bar starting point, but don't get too close to the ends of the childsBar.
     */
    private Point calcDescentBarChildsStart(final Coord descentBarParentX, final OrthogonalLine childsBar) {
        // Minimum horiz distance of descentBarChildsStart from ends of childsBar allowed.
        // A visual nicety, it looks bad if the descent line is really close to an end of the childs bar.
        /*
            Not this:
              ===
               |
               |
               |
              +*--------------+
              |               |
            +-|-+           +-|-+
            | * |           | * |
            +---+           +---+

            but this instead:
              ===
               |
               |
               +---+
                   |
              +----*----------+
              |               |
            +-|-+           +-|-+
            | * |           | * |
            +---+           +---+




   minXend--->|   |<-             ->|   |<---minXend

              +---[-----------------]---+   <---[allowable range for start point]
              |                         |
            +-|-+                     +-|-+
            | * |                     | * |
            +---+                     +---+

            (but if bar is shorter than 2 * minXLen, just use the midpoint)

         */


        final Coord x;
        if (childsBar.length() < new Dim(this.dxChildrenBarEndZone).twice()) {
            // corner case: the bar is too short to avoid the case of "too close to the end"
            x = childsBar.midpoint().x();
        } else {
            val xe = new MoveTerm(dxChildrenBarEndZone);
            x = new Coord(Math.clamp(descentBarParentX.u(), (childsBar.a() + xe).u(), (childsBar.b() - xe).u()));
        }
        return new Point(x, childsBar.pos());
    }

    private Coord calcDescentBarMiddleY(final Coord descentBarChildsY, final int nChild) {
        // calculate height of middle descent bar (which is hidden if there's only one child)
        /*

                  1 == c                           1 < c
                 ________                   _____________________

        Not this:
                 ===                          ===
                  |                            |
                  |                            |
                  +---+                        +---+
                      |                            |
                      *                       +----*----------+
                      |                       |               |
                    +-|-+                   +-|-+           +-|-+
                    | * |                   | * |           | * |
                    +---+                   +---+           +---+

        but this instead:
                 ===                          ===
                  |                            |
                  |                            |
                  |                            +---+
                  |                                |
                  +---+                       +----*----------+
                      |                       |               |
                    +-|-+                   +-|-+           +-|-+
                    | * |                   | * |           | * |
                    +---+                   +---+           +---+
         */
        Coord descentBarMiddleY = descentBarChildsY;
        if (1 < nChild) {
            descentBarMiddleY -= new MoveTerm(dyDescentMiddleAboveChild);
        }
        return descentBarMiddleY;
    }

    private void buildDescentBars(final Point descentBarParentTop, final Coord descentBarMiddleY, final Point descentBarChildsBtm) {
        this.descentBarParent = Optional.of(new OrthogonalLine(OrthogonalLine.Orient.V, descentBarParentTop.y(), descentBarMiddleY, descentBarParentTop.x()));
        this.descentBarChilds = Optional.of(new OrthogonalLine(OrthogonalLine.Orient.V, descentBarChildsBtm.y(), descentBarMiddleY, descentBarChildsBtm.x()));
        this.descentBarMiddle = Optional.of(new OrthogonalLine(OrthogonalLine.Orient.H, this.descentBarParent.get().pos(), this.descentBarChilds.get().pos(), descentBarMiddleY));
    }



    private double xChild(final @NonNull Indi child) {
        return child.xOffsetForParent(this);
    }

    private double yChild(final @NonNull Indi child) {
        return child.yOffsetForParent(this);
    }








    // These three methods are used only by the Layout algorithms
    List<@NonNull Indi> getChildren() {
        return new ArrayList<>(this.rIndiChildren);
    }
    Optional<Indi> getHusb() {
        for (val parent : this.rIndiSpouses) {
            if (parent.getSex() == 1) {
                return Optional.of(parent);
            }
        }
        return Optional.empty();
    }
    Optional<Indi> getWife() {
        for (val parent : this.rIndiSpouses) {
            if (parent.getSex() == 2) {
                return Optional.of(parent);
            }
        }
        return Optional.empty();
    }












    private void calcBounds() {
        val orthos = new ArrayList<>(rBarChild);
        this.barChildren.ifPresent(orthos::add);
        this.descentBarChilds.ifPresent(orthos::add);
        this.descentBarMiddle.ifPresent(orthos::add);
        this.descentBarParent.ifPresent(orthos::add);
        val bndOrtho = OrthogonalLine.bounds(orthos);

        val rects = new ArrayList<Rect>(2);
        rects.add(bndOrtho);
        if (this.barSpouses.isPresent()) {
            rects.add(bnd(this.barSpouses.get().center()));
        }

        this.bounds = Rect.bounds(rects);
    }

    private static Rect bnd(final @NonNull Line line) {
        val ax = new Dim(line.a().x().u());
        val bx = new Dim(line.b().x().u());
        val x = (ax + bx).half();

        val ay = new Dim(line.a().y().u());
        val by = new Dim(line.b().y().u());
        val y = (ay + by).half();

        val w = ax - bx;
        val h = ay - by;

        return new Rect(new RectDim(w, h), new Point(new Coord(x.n()), new Coord(y.n())));
    }
}
