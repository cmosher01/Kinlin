# Kinlin
### Kinship Lineage Chart

---

Copyright © 1990-2026, Christopher Alan Mosher, New York, NY, USA, <cmosher01@gmail.com>.

I started developing my drop-line chart style genealogy algorithms starting in 1990
for my first genealogy software product, Genealogy Research Organizer.

That progressed over the years. I also make other versions, with Dojo, and
pure Javascript, and other GEDCOM processing utilities.

I later converted the algorithms to Java, and developed Gedcom-XY-Editor, which
concentrates only on the drop line chart.

Various historical versions of my design and programming of the algorithms:
- https://app.assembla.com/spaces/cmosher/subversion/source/HEAD/genealogy/groapplet
- https://github.com/cmosher01/cmosher-software-archive/tree/master/genealogy/groapplet

- https://app.assembla.com/spaces/cmosher/subversion/source/HEAD/genealogy/grojs
- https://github.com/cmosher01/cmosher-software-archive/tree/master/genealogy/grojs

- https://app.assembla.com/spaces/cmosher/subversion/source/HEAD/genealogy/grojo
- https://github.com/cmosher01/cmosher-software-archive/tree/master/genealogy/grojo

- https://app.assembla.com/spaces/cmosher/subversion/source/HEAD/genealogy/gro
- https://github.com/cmosher01/OpenGRO
- https://github.com/cmosher01/gro-redux

- https://github.com/cmosher01/Gedcom-XY-Editor/blob/master/src/main/java/nu/mine/mosher/gedcom/xy/Layout.java
- https://github.com/cmosher01/Gedcom-XY-Editor/blob/master/src/main/java/nu/mine/mosher/gedcom/xy/Indi.java
- https://github.com/cmosher01/Gedcom-XY-Editor/blob/master/src/main/java/nu/mine/mosher/gedcom/xy/Fami.java
- https://github.com/cmosher01/Gedcom-XY-Editor/blob/master/src/main/java/nu/mine/mosher/gedcom/xy/Metrics.java
- https://github.com/cmosher01/Gedcom-XY-Editor/blob/master/src/main/java/nu/mine/mosher/gedcom/xy/util/Grid.java

- https://github.com/cmosher01/Ftm-Web-View/blob/latest/src/main/java/nu/mine/mosher/genealogy/xy/metrics/ChartMetrics.java
- https://github.com/cmosher01/Ftm-Web-View/blob/latest/src/main/java/nu/mine/mosher/genealogy/xy/metrics/FontBasedMetrics.java
- https://github.com/cmosher01/Ftm-Web-View/blob/latest/src/main/java/nu/mine/mosher/genealogy/xy/metrics/HeadlessWordWrap.java
- https://github.com/cmosher01/Ftm-Web-View/blob/latest/src/main/java/nu/mine/mosher/genealogy/xy/Indi.java
- https://github.com/cmosher01/Ftm-Web-View/blob/latest/src/main/java/nu/mine/mosher/genealogy/xy/Fami.java

Here, I want to take the graphical handling algorithms (currently in Gedcom-XY-Editor, and
Ftm-Web-View) and make a new high-speed version of Gedcom-XY-Editor.

Gedcom-XY-Editor uses JavaFX for its graphics, which slows to a crawl for large
family trees because it recalculates everything during panning, zooming, and
dragging people around.

The architecture will more rigorously follow the Model-View-Controller
pattern. The program will use Swing, and only recalculate what's needed. I want to separate
the genealogy-related algorithms from the graphical algorithms, and also from
the underlying graphical framework (Swing, in this case). This will allow easier
porting of the algorithms to other graphical frameworks in the future, if something
better comes along.

See https://www.tldraw.com/p/dFnLn33eeS05HJAdDu_W8?d=v-762.-5338.2461.1625.page
for MVC architecture diagrams.


```
FEATURE TYPE         1D                   2D

measurement     m    n Dim                w,h RectDim
resizing (abs)  g    d GrowTerm           w,h Outset
resizing (rel)  s    k ScaleFactor        [locked to proportional]

location/post'n p    u Coord              x,y Point
movement/delta  d    d MoveTerm           x,y Xlation

othogonal shapes       OrthLine               Rect

oblique   shapes                              Line
                                              Bars
portion/ratio        k Proportion

quantization           Quantizer              Grid

text                   AttributedString       HeadlessWordWrap



operations:

m = m +- g
m = m +- m
m = m */ s
s = m  / m [for 1D only]

p = p +- d
p = p */ s
s = p  / p [for 1D only]
d = p  - p
```

Use Manifold library http://manifold.systems/
- extend java.lang.Double to compare using Nearness
- operator overloading on graphical features

Tolerances for "nearness" comparisons are based on the domain
of graphical computations we expect to handle efficiently for
a drop-line chart of "small" to "huge" size family trees.



# Drawing algorithms and ideas

given:
- people with (x,y), name, dates, place
- families (parents/children)

read all data and calculate entire tree
- each person has
  - one and only one plaque, with
    - rectangle border
    - (word-wrapped lines of) attributed text
- each family (parents/children as a unit) has
  - up to one marriage bar
  - up to one descent line (1 segment or 3 segments)
  - one and only one child line for each child

- plaque: rect, styled text lines (with starting points for each line)
- marriage bars: two line segments
- descent lines: three orthogonal line segments (two vert, one horz)
- children lines: one horz ortho plus a vert ortho for each child

Each graphical element could have various attributes attached that
affect the style of the drawn element (e.g., currently selected).

### Bounds and limits

Let `F` be the set of graphical elements
calculate the bounding rectangle `B(F)`
define `M(B(F))` as `B(F)` with a margin `m` on each side
so `M.width` is `B.width + 2*m`, and likewise for height

Limits (for practical purposes)
- `M` <= 1 x 10<sup>8</sup> square pixels
- count of people <= 1 x 10<sup>6</sup>

### Display

`V` is the viewport, the (one and only) currently visible rectangle subset of `F`.
In more detail: find all plaques that are partially or fully within
`V`, and all connected family graphics (child-to, and spouse-to);
paint only those. We also need to paint family graphics that cut
across `V`. Consider options: 1. treat family as any other
graphic and just keep track of its bounding rectangle and draw
if it intersects the clipping region; 2. don't draw it (it's not
entirely relevant to the current display); 3. if too slow to
draw in realtime (i.e., it interferes with the app's responsiveness),
do a delayed draw in a worker thread.

`V` can be panned and zoomed throughout `M`.

Each plaque/person can be selected; `S` is the set of currently selected plaques/people.

Neither panning nor zooming require recalculating any graphical items.

panning:
- When panning, repaint just the objects coming into view.
- when calculating graphics within `V`, extend it (by some amount) on all
  sides to allow for quick panning.

zooming:
- when zoomed out
  - hide text
  - minimize plaque size (so user can still easily click to select it)
  - at ridiculously small sizes of display, see if it can completely avoid displaying
    the plaques, and just display the connecting lines (without preventing selection???)
  - anything else to reduce drawing time? (cache bitmap image(s) at different zoom levels?)
- limits
  - zooming *in* to show a capital letter at 1024 pixels tall should be enough;
    not much reason to make it bigger than that
  - zooming *out* should at least allow the entire tree to fit on the screen,
    with some margin around it in case the user want to drag something far
    outside the current boundaries. But don't zoom out so far as to make the
    whole image invisible. Maybe limit it so `M` scales down to around 10 pixels
    at the very smallest.

selecting:
- Selecting/deselecting people requires a repaint of the affected people and families
- Dragging to reposition `S` requires the recalculating of each person `s` in `S`,
along with their related families (child-to-families and spouse-to-families)
Let `R` be the set of people and families that need to be recalculated (due to
change in selection, or dragging to a different position)

dragging (editing) `S`:
- need to recalculate `R`:
  - each person `s` plaque (location)
  - their associated families (graphical lines)
- It needs to be efficient regardless of zoom level

Painting Z-order (back to front):
- Pan and zoom transforms
- Canvas background fill (opaque)
- Chart background fill (opaque)
- Axes draw (COULD be semi-transparent?)
- Family connection lines draw (opaque)
- Plaque rectangle draw and fill (MUST be opaque to hide family lines)
- Plaque text (opaque)
- Selection rectangle (opaque draw, with semi-transparent fill?)

### Drawing drop lines

- parent point = null
- if 1 parent
  - parent point = parent indi xy
- else if 2 parents
  - draw bars between indis
  - parent point = midpoint of bars (bottom line)
---
- child bar = null
- if 1 child (degenerate case, can optionally handle specially)
  - child bar = (point) child indi xy (for given parent)
- else if 2 or more children
  - draw child vertical descent lines (for given parent)
  - draw children horizontal connection bar (for given parent)
  - child bar = connection bar
---
- if parent point != null and child bar != null
  - draw three-segment descent line from parent point
    to child bar (with x from x1 to x2 at y)
    (using special prettydraw algorithm)
---

For an orthogonal line, consider filling a (one pixel thin)
rectangle, instead of actually drawing a line.

### Drawing an individual's plaque

Note that the plaque data is immutable throughout
the entire life of the editing session. The only
editing is moving plaques to different locations,
which only changes their (x,y) coordinates. So, we
should be able to increase performance by creating
a bitmap of each plaque once, and just blitting it
onto the canvas. We will need four bitmaps for the
four different states:
- not modified and not selected
- not modified and selected
- modified and not selected
- modified and selected

Also note: once the bitmaps are created, we don't
need to hold the actual data (name, dates, place)
in memory anymore. Just the IDs and relationships,
along with the (x,y) coordinates.

### Application termination flows

- program-defined "File/Quit" menu item (with cmd-Q accel)
- program-defined "File/Quit" menu item (with other accel)
- (App)/Quit (mac native) menu item
- command-Q (be careful on Mac: will trigger both menu items in sequence)
- click frame close button
- OS shutdown requests app quit
- taskbar/close
- command line "kill"

### My Prior-Existing Algorithms
from Ftm-Web-View:
- ChartMetrics.java
- FontBasedMetrics.java
- HeadlessWordWrap.java
- Fami.java (graphical calculations)
- Indi.java (graphical calculations)

from Gedcom-XY-Editor:
- Layout.java



