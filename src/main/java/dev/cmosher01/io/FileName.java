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

package dev.cmosher01.io;

import lombok.*;

import java.util.*;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public final class FileName {
    private static final String DOT = ".";
    private static final String REGEX_DOT = Pattern.quote(DOT);
    private static final int KEEP_TRAILING_EMPTY_STRINGS = -1;



    private final @NonNull String name;
    private final @NonNull List<String> extensions;



    /**
     * Parses a "file name" into proper name and "extensions", delimited by dot(s).
     * Handles "hidden" names that begin with a dot (so that dot becomes part of the name).
     * Whitespace is not treated specially; for example a name or extension can begin with,
     * end with, or contain a space character. No trimming or stripping is performed.
     * A filename that is all dots is treated as a name with no extensions.
     *
     * @param filename full name of file, for example from File.getName() or Path.getName().toString()
     *                 Do not include any directory names or path separators.
     */
    public static @NonNull FileName of(final String filename) {
        final FileName o;

        if (Objects.isNull(filename)) {
            o = of("");
        } else if (isAllDots(filename)) {
            o = new FileName(filename, Collections.emptyList());
        } else {
            val hide = filename.startsWith(DOT);

            val segs = split(filename.substring(hide ? 1 : 0));
            val base = segs.getFirst();
            val exts = segs.subList(1, segs.size());

            val name = (hide ? DOT : "") + base;

            o = new FileName(name, exts);
        }

        return o;
    }



    public @NonNull String name() {
        return this.name;
    }

    public boolean isHidden() {
        return name().startsWith(DOT);
    }

    public boolean isAllDots() {
        return isAllDots(name());
    }

    public @NonNull String nameUnhidden() {
        return name().substring((isHidden() && !isAllDots()) ? 1 : 0);
    }



    public @NonNull List<String> extensions() {
        return this.extensions;
    }

    public @NonNull Optional<String> extension() {
        return
            (extensions().isEmpty())
            ? Optional.empty()
            : Optional.of(extensions().getLast());
    }

    public @NonNull String extensionOrEmpty() {
        return extension().orElse("");
    }



    @Override
    public boolean equals(final Object objects) {
        return
            objects instanceof FileName that &&
            this.name().equals(that.name()) &&
            this.extensions().equals(that.extensions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name(), extensions());
    }



    static boolean isAllDots(final @NonNull String s) {
        return !s.isEmpty() && s.codePoints().allMatch(c -> c == '.');
    }

    private static @NonNull List<String> split(final @NonNull String s) {
        return List.of(s.split(REGEX_DOT, KEEP_TRAILING_EMPTY_STRINGS));
    }
}
