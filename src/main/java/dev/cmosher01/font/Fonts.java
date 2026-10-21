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

import lombok.*;

import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class Fonts {
    private static final GraphicsEnvironment ENV = GraphicsEnvironment.getLocalGraphicsEnvironment();

    public static void dumpFamilies() {
        val families = List.of(ENV.getAvailableFontFamilyNames());
        for (val family : families) {
            System.out.println(family);
        }
    }

    public static Font loadFromResource(final @NonNull Path font) throws IOException, FontFormatException {
        try (val in = Fonts.class.getResourceAsStream(font.toString())) {
            return Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(in));
        }
    }

    public static void register(final @NonNull Font font) {
        ENV.registerFont(font);
    }

    public static String register(final @NonNull Path font) throws IOException, FontFormatException {
        val f = loadFromResource(font);
        register(f);
        return f.getFamily();
    }

    public static String registerOrFallback(final @NonNull Path font) {
        try {
            return register(font);
        } catch (final Exception e) {
            return "";
        }
    }
}
