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
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.*;


public class Plaque {
    private final @NonNull GedcomIndiName name;
    private final @NonNull String lifespan;
    private final @NonNull String place;

    private TextBlock text;



    public Plaque(final @NonNull GedcomIndiName name, final @NonNull String lifespan, final @NonNull String place) {
        this.name = name;
        this.lifespan = lifespan;
        this.place = place;
    }



    public void refresh(final @NonNull FontModel fonts) {
        val palette = fonts.palette();

        val attrsPlain = palette.plain().getAttributes();
        val attrsSmall = palette.small().getAttributes();

        val paragraphs = new ArrayList<AttributedString>();

        paragraphs.add(buildAttributedName(this.name, attrsPlain));
        if (!this.lifespan.isBlank()) {
            paragraphs.add(new AttributedString(this.lifespan, attrsSmall));
        }
        if (!this.place.isBlank()) {
            paragraphs.add(new AttributedString(this.place, attrsPlain));
        }

        this.text = TextBlock.create(paragraphs, palette.widthMax());
    }



    public void paint(final @NonNull Graphics2D g, final @NonNull Point c) {
        this.text.paint(g, c);
    }

    public RectDim dim() {
        return this.text.dimMargin();
    }

    public String simpleName() {
        return this.name.simple();
    }


    private record Range(int begin, int end) {
        public Range(StringBuilder sb, String s) {
            this(sb.length(), sb.length()+s.length());
        }
    }

    private static @NonNull AttributedString buildAttributedName(final @NonNull GedcomIndiName name,  final @NonNull Map<TextAttribute,?> plain) {
        val sb = new StringBuilder(32);

        val bolds = new ArrayList<@NonNull Range>(2);

        for (val tok : name.tokenized()) {
            sb.append(switch (tok) {
                case NULL, UNKNOWN -> "";
                case SPACE -> " ";
                case GIVEN0 -> {
                    val s = name.given0();
                    bolds.add(new Range(sb, s));
                    yield s;
                }
                case SUR -> name.sur();
                case GIVEN1 -> {
                    val s = name.given1();
                    bolds.add(new Range(sb, s));
                    yield s;
                }
            });
        }
        if (sb.isEmpty()) {
            sb.append("?");
        }

        val ret = new AttributedString(sb.toString(), plain);

        for (val bold : bolds) {
            ret.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD, bold.begin(), bold.end());
        }

        return ret;
    }
}
