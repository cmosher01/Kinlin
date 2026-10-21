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

import dev.cmosher01.font.TextBlock;
import dev.cmosher01.geom.Dim;
import lombok.*;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.*;

public class FontPalette {
    private static final float SMALL_SCALE = 0.8F;

    private final @NonNull Font plain;
    private final @NonNull Font bold;
    private final @NonNull Font small;

    private @NonNull Dim widthMax;

    public FontPalette(final double size, final @NonNull String family) {
        val sizeRegular = (float)size;
        val sizeSmall = SMALL_SCALE * sizeRegular;

        val attrs = Map.of(
            TextAttribute.KERNING, TextAttribute.KERNING_ON,
            TextAttribute.JUSTIFICATION, TextAttribute.JUSTIFICATION_NONE,
            TextAttribute.FAMILY, family,
            TextAttribute.SIZE, sizeRegular);

        this.plain = Font.getFont(attrs);
        this.small = this.plain.deriveFont(sizeSmall);
        this.bold = this.plain.deriveFont(Font.BOLD);

        this.widthMax = maxWidth(this);
    }

    // allow user-specified override of default max width for plaques
    public void widthMax(final @NonNull Dim widthMax) {
        this.widthMax = widthMax;
    }

    public Font plain() {
        return this.plain;
    }

    public Font bold() {
        return this.bold;
    }

    public Font small() {
        return this.small;
    }

    public Dim widthMax() {
        return this.widthMax;
    }



    private static final int COUNT_EM = 16;
    private static final String PLAQUE_MAX = "M".repeat(COUNT_EM);

    private static @NonNull Dim maxWidth(final @NonNull FontPalette f) {
        val attrs = f.bold().getAttributes();
        val MMMM = new AttributedString(PLAQUE_MAX, attrs);
        return TextBlock.create(Collections.singletonList(MMMM)).dim().w();
    }
}
