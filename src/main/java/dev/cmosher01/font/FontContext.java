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

package dev.cmosher01.font;

import dev.cmosher01.genealogy.xy.Swings;
import lombok.*;

import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;

public final class FontContext {
    private static final @NonNull BufferedImage IMG_ARGB = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private static @NonNull FontRenderContext CTX = getFontRenderContext();

    @Deprecated
    private FontContext() {
        throw new UnsupportedOperationException();
    }

    public static @NonNull FontRenderContext ctx() {
        return CTX;
    }

    public static void refreshCtx() {
        CTX = getFontRenderContext();
    }

    private static @NonNull FontRenderContext getFontRenderContext() {
        val g = IMG_ARGB.createGraphics();
        g.addRenderingHints(Swings.GLOBAL_RENDERING_HINTS);
        return g.getFontRenderContext();
    }
}
