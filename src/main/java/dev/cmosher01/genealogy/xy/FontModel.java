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

import dev.cmosher01.font.Fonts;
import lombok.*;

import java.nio.file.Path;

// TODO let user choose font
// TODO with huge charts zoomed way in, text display gets distorted

public class FontModel {
    private static final @NonNull Path FILENAME_PLAIN = Path.of("NotoSans-Regular.ttf");
    private static final @NonNull Path FILENAME_BOLD = Path.of("NotoSans-Bold.ttf");

    private @NonNull FontPalette palette;

    @SneakyThrows
    public FontModel() {
        val family = Fonts.registerOrFallback(FILENAME_PLAIN);
        if (!family.isBlank()) {
            Fonts.registerOrFallback(FILENAME_BOLD);
        }

        this.palette = new FontPalette(8.0D, family);
    }

    public @NonNull FontPalette palette() {
        return this.palette;
    }

    public void palette(final @NonNull FontPalette palette) {
        this.palette = palette;
    }
}
