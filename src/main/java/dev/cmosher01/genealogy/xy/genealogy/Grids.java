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
import dev.cmosher01.math.doubles.DoubleUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.Stream;

@Slf4j
public final class Grids {
    @Deprecated
    private Grids() {
        throw new UnsupportedOperationException();
    }



    private static final @NonNull MoveTerm NO_OFFSET = MoveTerm.IDENTITY;
    private static final @NonNull ScaleFactor NO_GRID = ScaleFactor.IDENTITY;
    private static final @NonNull ScaleFactor MIN_GRID = new ScaleFactor(1e1D);
    private static final @NonNull ScaleFactor MAX_GRID = new ScaleFactor(1e3D);



    public static void createFromIndis(final @NonNull List<@NonNull Indi> individuals, final @NonNull Grid ret) {
        val indis = Collections.unmodifiableList(individuals);

        // TODO have separate X and Y grid values
        val coords = getAllCoordsFrom(indis);

        var k = calcGrid(coords);
        var d = MoveTerm.IDENTITY;

        ret.setActive(true);
        ret.setX(new Quantizer(k, d));
        ret.setY(ret.getX());


        if (ret.getX().isQuantizing()) {
            d = calcOffset(coords, k);
        } else {
            k = MIN_GRID;
        }

        ret.setX(new Quantizer(k, d));
        ret.setY(ret.getX());

        reportGridAlignmentOf(indis, ret);
    }


    //    public void setFromUserEnteredString(final String usersEntry, final Preferences prefs) {
//        if (Objects.isNull(usersEntry) || usersEntry.isBlank()) {
//            return;
//        }
//
//        final int g = Math.clamp(parseIntSafe(usersEntry), NO_GRID, MAX_GRID);
//        this.grid = g;
//        this.offset = DEFAULT_OFFSET; // TODO allow user to enter grid offset?
//        setPref(g, prefs);
//    }



    private static List<Coord> getAllCoordsFrom(final List<Indi> indis) {
        return indis
            .stream()
            .map(Indi::point)
            // TODO works only with integers; does that make sense:
            .flatMap(p -> Stream.of(p.x().rint(), p.y().rint()))
            .toList();
    }

    private static List<Coord> getAllXs(final List<Indi> indis) {
        return indis
            .stream()
            .map(Indi::point)
            .map(Point::x)
            // TODO works only with integers; does that make sense?:
            .map(Coord::rint)
            .toList();
    }

    private static List<Coord> getAllYs(final List<Indi> indis) {
        return indis
            .stream()
            .map(Indi::point)
            .map(Point::y)
            // TODO works only with integers; does that make sense?:
            .map(Coord::rint)
            .toList();
    }

    // notes on variable names:
    // "dx" means "delta X", which is difference between two coordinates
    // "x" means any coordinate (regardless of whether it's x, y, or z, etc.)
    // "s" on end means plural, for example "xs" means plural of "x" (used for lists, arrays, etc.)

    private static ScaleFactor calcGrid(final List<Coord> xs) {
        log.info("Total count of coordinates: {}", xs.size());

        // count unique differences between each pair of coordinates
        // map unique distances to their counts
        val dxs = new HashMap<Long, Integer>();
        for (int i = 0; i < xs.size() - 1; ++i) {
            for (int j = i + 1; j < xs.size(); ++j) {
                val dx = Math.round(Math.abs(xs.get(i).u() - xs.get(j).u()));
                if (0 < dx) {
                    dxs.merge(dx, 1, Integer::sum);
                }
            }
        }
        log.info("Total count of different distances between coordinates: {}", dxs.size());


        // sort dx values by popularity
        // use BigInteger just so we can use its "gcd()" method
        final var dxsSorted = new ArrayList<BigInteger>(dxs.size());
        {
            final double COVERAGE_RATE = 0.6D;
            log.info("Distances by popularity (to {}% coverage rate)", Math.round(Math.rint(100D * COVERAGE_RATE)));
            log.info("   # fraction   distance-->count coverage");
            final var T = (double) (dxs.values().stream().reduce(0, Integer::sum));
            final var Tnum = (double) dxs.size();
            final var n = new AtomicInteger();
            final var inum = new AtomicInteger();
            final var cut = new AtomicBoolean();
            dxs
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) // sort by count desc
                .forEach(e -> {
                    final var dx = e.getKey();
                    final var c = e.getValue();
                    final var p = n.addAndGet(c);
                    final var num = inum.incrementAndGet();
                    if (!cut.get()) {
                        log.info(String.format("%4d (%3.5f): %8d-->%5d (%3.5f)", num, num / Tnum, dx, c, p / T));
                        dxsSorted.add(BigInteger.valueOf(dx));
                        if (Double.compare(COVERAGE_RATE, p / T) <= 0) {
                            cut.set(true); // basically this terminates the loop (or makes it do nothing for the remaining iterations)
                        }
                    }
                });
        }


        // count unique GCDs (>= MIN_GRID_TO_DETECT) between each pair of distances
        final var gcds = new HashMap<BigInteger, Integer>();
        {
            final var gcdMinToDetect = BigInteger.valueOf(MIN_GRID.k().longValue());
            for (int i = 0; i < dxsSorted.size() - 1; ++i) {
                for (int j = i + 1; j < dxsSorted.size(); ++j) {
                    final var gcd = dxsSorted.get(i).gcd(dxsSorted.get(j));
                    if (1L < gcd.longValue()) {
                        if (gcdMinToDetect.compareTo(gcd) < 0) {
                            gcds.merge(gcd, 1, Integer::sum);
                        }
                    }
                }
            }
            if (dxsSorted.size() == 1) {
                gcds.put(dxsSorted.getFirst(), 1);
            }
            log.info("Total count of different GCDs >= {} between each pair of distances: {}", MIN_GRID, gcds.size());
        }
        if (gcds.isEmpty()) {
            return NO_GRID;
        }


        // sort GCD values by popularity, and
        // convert from BigInteger to final result type for grid (Integer)
        final var gcdsSorted = new ArrayList<Integer>(gcds.size());
        {
            final double COVERAGE_RATE = 0.6D;
            log.info("Top GCDs (min: {}), by popularity (to {}% coverage rate)", MIN_GRID, Math.round(Math.rint(100D * COVERAGE_RATE)));
            final var T = (double) (gcds.values().stream().reduce(0, Integer::sum));
            final var n = new AtomicInteger();
            final var cut = new AtomicBoolean();
            gcds
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) // sort by count desc
                .forEach(e -> {
                    final var gcd = e.getKey().intValue();
                    final int c = e.getValue();
                    final var p = n.addAndGet(c);
                    if (!cut.get()) {
                        log.info(String.format("%8d-->%5d (%3.5f)", gcd, c, p / T));
                        gcdsSorted.add(gcd);
                        if (Double.compare(COVERAGE_RATE, p / T) <= 0) {
                            cut.set(true); // basically this terminates the loop (or makes it do nothing for the remaining iterations)
                        }
                    }
                });
        }
        if (gcdsSorted.isEmpty()) {
            return NO_GRID;
        }

        // and take min GCD between each of those GCDs
        BigInteger bigGcdFinal = null;
        for (final var i : gcdsSorted) {
            final var gcd = BigInteger.valueOf(i);
            if (Objects.isNull(bigGcdFinal)) {
                bigGcdFinal = gcd;
            } else {
                bigGcdFinal = bigGcdFinal.gcd(gcd);
            }
        }
        // This is what we're trying to calculate,
        // the value used for snap-to-grid.
        final int gcdRaw;
        if (1 < bigGcdFinal.intValue()) {
            gcdRaw = bigGcdFinal.intValue();
            // log report on top GCDs to show if they are multiples of the chosen GCD (which is good)
            log.info("Top 10 GCDs:");
            log.info("          M = is a multiple of final GCD");
            log.info("          XXXXX = is NOT a multiple of final GCD");
            for (int i = 0; i < Math.min(20, gcdsSorted.size()); ++i) {
                final var gcd = gcdsSorted.get(i);
                log.info(String.format("    %5d %s", gcd, (gcd % gcdRaw == 0) ? "M" : "XXXXX"));
            }
        } else {
            gcdRaw = gcdsSorted.getFirst();
            log.warn("Initial coordinates are not well aligned, choosing majority alignment: {}", gcdRaw);
        }

        if (MAX_GRID.k() < gcdRaw) {
            log.warn("Calculated gcd {} is greater than maximum allowed grid {}", gcdRaw, MAX_GRID.k());
        }
        val gcd = new ScaleFactor(DoubleUtil.fp(Math.clamp(gcdRaw, 1, MAX_GRID.k())));

        log.info("(final) GCD of all those GCDs: {} <-----------FINAL GRID VALUE", gcd.k());

        return gcd;
    }

    private static MoveTerm calcOffset(final List<Coord> xs, final ScaleFactor scale) {
        val grid = scale.k().intValue();
        final var offs = new HashMap<Integer, Integer>(grid);
        for (final var x : xs) {
            for (int off = 0; off < grid; ++off) {
                if ((x.u() - off) % grid == 0) { // x-off, not x+off
                    offs.merge(off, 1, Integer::sum);
                }
            }
        }
        if (offs.isEmpty()) {
            return NO_OFFSET;
        }


        final var offsSorted = new ArrayList<Integer>(offs.size());
        final var offsSortedCounts = new ArrayList<Integer>(offs.size());
        offs
            .entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(e -> {
                final var off = e.getKey();
                offsSorted.add(off);
                final var offCount = e.getValue();
                offsSortedCounts.add(offCount);
            });

        log.info("Top offsets:");
        log.info("   offset  count");
        for (int i = 0; i < Math.min(100, offsSorted.size()); ++i) {
            final var off = offsSorted.get(i);
            final var offCount = offsSortedCounts.get(i);
            log.info(String.format("    %5d   %5d", off, offCount));
        }
        final var offFinal = offsSorted.getFirst();
        log.info("Chose top offset value: {} <-----------FINAL GRID OFFSET VALUE", offFinal);

        return new MoveTerm(DoubleUtil.fp(offFinal));
    }









    private static void reportGridAlignmentOf(final List<Indi> indis, final @NonNull Grid grid) {
        reportPointGridAlignmentOf(indis, grid);
        reportCoordinateGridAlignmentOf(indis, grid);
    }

    private static void reportCoordinateGridAlignmentOf(final List<Indi> indis, final @NonNull Grid grid) {
        final var xs = getAllXs(indis);
        final var ys = getAllYs(indis);
        final var xys = Stream.concat(xs.stream(), ys.stream()).toList();

        final var N = xys.size();
        int cAligned = 0;
        for (final var xy : xys) {
            final var xySnapped = grid.snap(new Point(xy));
            final var eq = Point.distance(xySnapped, new Point(xy)).n() < 0.1D;
            if (eq) {
                cAligned++;
            }
        }
        final var percentAligned = Math.round(Math.rint(
            (100D * cAligned) / N
        ));

        log.info("Count of non-zero single coordinates, X or Y: {}", N);
        log.info("Count of those coordinates initially aligned to calculated grid/offset: {} ({}%)", cAligned, percentAligned);
    }

    private static void reportPointGridAlignmentOf(final List<Indi> indis, final Grid grid) {
        final var xys = indis
            .stream()
            .map(Indi::point)
            .filter(i -> {
                final var x = i.x().u();
                final var y = i.y().u();
                return
                    (x < -0.1D || 0.1D < x) ||
                        (y < -0.1D || 0.1D < y);
            })
            .toList();

        final var N = xys.size();
        int cAligned = 0;
        for (final var xy : xys) {
            final var xySnapped = grid.snap(xy);
            if (xySnapped.equals(xy)) {
                cAligned++;
            }
        }
        final var percentAligned = Math.round(Math.rint(
            (100D * cAligned) / N
        ));

        log.info("Count of people with points not (0,0): {}", N);
        log.info("Count of those people initially aligned to calculated grid/offset: {} ({}%)", cAligned, percentAligned);
    }
}
