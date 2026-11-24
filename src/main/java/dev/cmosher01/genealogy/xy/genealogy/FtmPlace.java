/*
 *     Copyright © 2018-2026, Christopher Alan Mosher, New York, New York, USA, <cmosher01@gmail.com>.
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


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.regex.*;

// Taken from Ftm-Web-View

@Slf4j
public class FtmPlace
{   private final @NonNull List<@NonNull String> hierarchy;
    private final @NonNull String description;

    private FtmPlace(final @NonNull List<@NonNull String> hierarchy, final @NonNull String description) {
        this.hierarchy = hierarchy;
        this.description = description;
    }

    @Override
    public @NonNull String toString() {
        return this.description;
    }

    public static @NonNull FtmPlace fromFtmPlace(final @Nullable String s) {
        final FtmPlace place = new Builder(s).build();
        log.debug("FtmPlace=\"{}\" --> \"{}\"", s, place);
        return place;
    }

    @Override
    public boolean equals(final @Nullable Object object) {
        if (!(object instanceof FtmPlace that)) {
            return false;
        }
        return this.hierarchy.equals(that.hierarchy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.hierarchy);
    }












    private static class Builder {
        private @NonNull String description;
        private final @NonNull List<@NonNull String> hierarchy = new ArrayList<>(5);

        public Builder(final @Nullable String description) {
            if (Objects.isNull(description) || description.isBlank()) {
                this.description = "";
            } else {
                // default value if any parsing fails:
                this.description = String.format("\u201C%s\u201D", description.strip());
                // try to parse
                try {
                    parseDescription(description.strip());
                } catch (final Throwable e) {
                    log.warn("unknown place name format for {}", description, e);
                }
            }
        }

        public @NonNull FtmPlace build() {
            return new FtmPlace(hierarchy, description);
        }


        /*
        Family Tree Maker does some encoding within the place name column.

        There are two main types of place names.

        Both types start with a slash.

        Both types end with

               ... [CODE] / [LATITUDE] / [LONGITUDE]

        where CODE could be negative (which is obviously a flag that represents something), and
        LATITUDE/LONGITUDE are coordinates, in radians.

        Immediately preceding this will be either a slash or a vertical bar, which
        is used as an indicator of the main type of the place name, and dictates the
        format of the rest of the string preceding that:


        "/" Slashed place names are in this format:

            / [p0] / [p1] / [p2] / [p3] / [p4] / ...

        where p0 through p4 are place names in a hierarchy.
        The parts of "resolved" places are at the end.


        "|" Vertical bar place names are in this format:

            / [name] | ...

         */


        /*
                /Hamilton, Madison, New York, USA|/0.7474722/-1.318502
                /Place, Name w/some slash/es | and, vertical | bars|//
         */
        private static final @NonNull Pattern FTM_PLACE_WITH_VERTICALBAR = Pattern.compile("^/(?<name>.*)\\|(?<code>[^/|]*?)/(?<lat>[^/|]*?)/(?<lon>[^/|]*?)$");

        /*
                /Room 401, Flint Hall, Syracuse University/Syracuse/Onondaga/New York/USA/11269/0.7513314/-1.329023
                /another place / with slashes | and  bars, but, resolved, in///Connecticut/USA/-9//

            Use the first capture group ("name") from the first pattern as input to the second pattern:
        */
        private static final @NonNull Pattern FTM_PLACE_WITH_SLASH = Pattern.compile("^/(?<name>.*)/(?<code>[^/|]*?)/(?<lat>[^/|]*?)/(?<lon>[^/|]*?)$");

        private static final @NonNull Pattern FTM_PLACE_HIERARCHICAL = Pattern.compile("^(?<p0>.*)/(?<p1>[^/|]*?)/(?<p2>[^/|]*?)/(?<p3>[^/|]*?)/(?<p4>[^/|]*?)$");


        private void parseDescription(final @NonNull String description) {
            //-------------------------------------------------------
            {
                val withBar = FTM_PLACE_WITH_VERTICALBAR.matcher(description);
                if (withBar.matches()) {
                    parseAndAddHierarchy(withBar.group("name"));

                    buildDescription();
                    return;
                }
            }
            //-------------------------------------------------------
            {
                val withSlash = FTM_PLACE_WITH_SLASH.matcher(description);
                if (withSlash.matches()) {
                    val hier = FTM_PLACE_HIERARCHICAL.matcher(withSlash.group("name"));
                    if (hier.matches()) {
                        parseAndAddHierarchy(hier.group("p0"));
                        addHierarchy(hier.group("p1"));
                        addHierarchy(hier.group("p2"));
                        addHierarchy(hier.group("p3"));
                        addHierarchy(hier.group("p4"));
                    } else {
                        parseAndAddHierarchy(withSlash.group("name"));
                    }

                    buildDescription();
                }
            }
            //-------------------------------------------------------
        }

        private void buildDescription() {
            this.description = String.join(", ", this.hierarchy);
        }

        private void parseAndAddHierarchy(final @Nullable String csvParts) {
            if (Objects.nonNull(csvParts)) {
                Arrays.stream(csvParts.split(",")).
                    map(String::trim).
                    forEach(this::addHierarchy);
            }
        }

        private void addHierarchy(final @Nullable String part) {
            if (Objects.nonNull(part) && !part.isBlank()) {
                this.hierarchy.add(part.strip());
            }
        }
    }
}
