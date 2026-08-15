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

Here, I want to take the graphical handling algorithms (currently in Gedcom-XY-Editor, and
and Ftm-Web-View) and make a new high-speed version of Gedcom-XY-Editor.

Gedcom-XY-Editor uses JavaFX for its graphics, which slows to a crawl for large
family trees because it recalculates everything during panning, zooming, and
dragging people around.

This version will use Swing, and only recalculate what's needed. I want to separate
the genealogy-related algorithms from the graphical algorithms, and also from
the underlying graphical framework (Swing, in this case). This will allow easier
porting of the algorithms to other graphical frameworks in the future, if something
better comes along.




```
FEATURE TYPE      1D                 2D

measurement     m n Dim                w,h RectDim
resizing (abs)  g d GrowTerm           w,h Outset
resizing (rel)  s k ScaleFactor        [locked to proportional]

location/post'n p u Coord              x,y Point
movement/delta  d d MoveTerm           x,y Xlation

finite shapes     OrthLine           Rect

                  Line
                  Bars

portion/ratio     k Proportion

text              AttributedString   HeadlessWordWrap


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

Limits (for practical purposes)
- `B` <= 1 x 10<sup>8</sup> square pixels
- count of people <= 1 x 10<sup>6</sup>

### Display

`V` is the viewport, the (one and only) currently visible rectangle subset of `F`
`V` can be panned and zoomed throughout `B`
each plaque/person can be selected; `S` is the set of currently selected plaques/people

Neither panning nor zooming require recalculating any graphical items

panning:
- When panning, repaint just the objects coming into view.
- `MV` is `V` with a given margin `M` around it: when calculating graphics
  within `V`, extend it to `MV` to allow for quick panning.

zooming:
- when zoomed out 
  - hide text 
  - minimize plaque size (so user can still easily click to select it)
  - anything else to reduce drawing time? (cache bitmap image(s) at different zoom levels?)

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


### Application termination flows

- click frame close button
- File/Quit menu item
- (App)/Quit (mac native) menu item
- command-Q

### Algorithms
from Ftm-Web-View:
- ChartMetrics.java
- FontBasedMetrics.java
- HeadlessWordWrap.java
- Fami.java (graphical calculations)
- Indi.java (graphical calculations)

from Gedcom-XY-Editor:
- Layout.java
