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

import dev.cmosher01.font.TextBlock;
import dev.cmosher01.genealogy.xy.*;
import dev.cmosher01.geom.*;
import lombok.*;

import java.awt.Graphics2D;
import java.text.AttributedString;
import java.util.*;


public class Plaque {
    private final @NonNull GedcomIndiName name;
    private final @NonNull String lifespan;
    private final @NonNull String place;

    private TextBlock text;



    private Plaque(final @NonNull GedcomIndiName name, final @NonNull String lifespan, final @NonNull String place) {
        this.name = name;
        this.lifespan = lifespan;
        this.place = place;
    }



    public static Plaque create(final @NonNull FontModel fonts, final @NonNull GedcomIndiName name, final @NonNull String lifespan, final @NonNull String place) {
        val o =  new Plaque(name, lifespan, place);
        o.refresh(fonts);
        return o;
    }



    public void refresh(final @NonNull FontModel fonts) {
        val palette = fonts.palette();

        val attrsPlain = palette.plain().getAttributes();
        val attrsSmall = palette.small().getAttributes();

        val paragraphs = new ArrayList<AttributedString>();

        {
            val asName = new AttributedString(this.name.simple(), attrsPlain); // TODO fix name
            paragraphs.add(asName);
        }
        if (!this.lifespan.isBlank()) {
            val asLifespan = new AttributedString(this.lifespan, attrsSmall);
            paragraphs.add(asLifespan);
        }
        if (!this.place.isBlank()) {
            val asPlace = new AttributedString(this.place, attrsPlain);
            paragraphs.add(asPlace);
        }

        this.text = TextBlock.create(paragraphs, palette.widthMax());
    }



    public void paint(final @NonNull Graphics2D g, final @NonNull Point c) {
        this.text.paint(g, c);
    }

    public RectDim dim() {
        return this.text.dim();
    }
}
