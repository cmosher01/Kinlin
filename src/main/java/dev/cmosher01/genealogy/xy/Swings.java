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

import lombok.NonNull;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.util.Map;

import static java.awt.RenderingHints.*;

public final class Swings {
    @Deprecated
    private Swings() {
        throw new UnsupportedOperationException();
    }

    public static @NonNull Point2D.Double pointOf(final @NonNull MouseEvent e) {
        return pt2d(e.getPoint());
    }

    public static @NonNull Point2D.Double pt2d(final @NonNull Point2D pt) {
        return new Point2D.Double(pt.getX(), pt.getY());
    }

    public static @NonNull Rectangle2D.Double outset(final @NonNull Rectangle2D r, final double d) {
        return new Rectangle2D.Double(r.getX()-d, r.getY()-d, r.getWidth()+2*d, r.getHeight()+2*d);
    }

    public static @NonNull Point2D.Double delta(final @NonNull Point2D from, final @NonNull Point2D to) {
        return new Point2D.Double(to.getX()-from.getX(), to.getY()-from.getY());
    }

    private static final @NonNull Stroke STROKE_SIMPLE = new BasicStroke(1F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);

    public static @NonNull Stroke simpleStroke() {
        return STROKE_SIMPLE;
    }

    public static @NonNull Stroke simpleStroke(final float width) {
        return new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
    }

    public static @NonNull Dimension scale(final double scale, final @NonNull Dimension dim) {
        return new Dimension(toInt(scale*dim.getWidth()), toInt(scale*dim.getHeight()));
    }

    public static final @NonNull Map<RenderingHints.@NonNull Key, @NonNull Object> GLOBAL_RENDERING_HINTS = Map.of(
        KEY_RENDERING, VALUE_RENDER_SPEED,
        KEY_ANTIALIASING, VALUE_ANTIALIAS_ON, //
        KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON, //
        KEY_FRACTIONALMETRICS, VALUE_FRACTIONALMETRICS_ON, //
        KEY_INTERPOLATION, VALUE_INTERPOLATION_NEAREST_NEIGHBOR,
        KEY_STROKE_CONTROL, VALUE_STROKE_PURE,
        KEY_COLOR_RENDERING, VALUE_COLOR_RENDER_SPEED,
        KEY_ALPHA_INTERPOLATION, VALUE_ALPHA_INTERPOLATION_SPEED,
        KEY_DITHERING, VALUE_DITHER_DISABLE
    );

    private static int toInt(final double d) {
        return (int)Math.round(Math.rint(d));
    }

    public static @NonNull Color mix(final @NonNull Color c1, final @NonNull Color c2, final double ratio) {
        final double r = Math.max(0D, Math.min(1D, ratio));
        final int rd = (int)(r*c1.getRed  () + (1-r)*c2.getRed  ());
        final int gn = (int)(r*c1.getGreen() + (1-r)*c2.getGreen());
        final int bl = (int)(r*c1.getBlue () + (1-r)*c2.getBlue ());
        return new Color(rd, gn, bl);
    }
}
