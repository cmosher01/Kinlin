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

import dev.cmosher01.genealogy.xy.genealogy.*;
import dev.cmosher01.geom.*;
import dev.cmosher01.io.FileName;
import lombok.*;
import nu.mine.mosher.gedcom.exception.InvalidLevel;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.nio.file.Path;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class DocumentModel {
    @Getter
    private Optional<ChartModel> model = Optional.empty();

    private @NonNull Optional<Path> file = Optional.empty();
    private FileName filename;
    private final @NonNull FontModel modelFont;
    private final @NonNull Grid grid;
    private boolean dirty ;

    public DocumentModel(final @NonNull FontModel modelFont, final @NonNull Grid grid) {
        this.modelFont = modelFont;
        this.grid = grid;
    }






    public boolean isPresent() {
        return this.model.isPresent();
    }

    public boolean isEmpty() {
        return this.model.isEmpty();
    }

    public ChartModel model() {
        if (this.model.isEmpty()) {
            throw new IllegalStateException("no chart is loaded");
        }
        return this.model.get();
    }


    public void open(final @NonNull Path file) throws IOException, InvalidLevel {
        this.file = Optional.of(file);
        this.filename = FileName.of(file.getFileName().toString());

        // TODO choose FTM or Gedcom
        this.model = Optional.of(IoGedcom.read(file, this.grid, this.modelFont));
        // TODO update grid based on heuristic detection from tree
    }

    public @NonNull String getTitle() {
        String ret = "(no file open)";
        if (isOpen()) {
            ret = this.filename.name();
            if (isModified()) {
                ret += " *";
            }
        }
        return ret;
    }

    public @NonNull Optional<Path> getPath() {
        return this.file;
    }

    public void setPath(final @NonNull Path path) {
        this.file = Optional.of(path);
        this.filename = FileName.of(path.getFileName().toString());
    }

    public @NonNull Path suggestSaveAsPath() {
        if (!isOpen()) {
            // should never happen
            return Path.of(timestamp()+".ged");
        }
        val name = removeTimestamp(this.filename.name());
        return Path.of(name+"-"+timestamp()+this.filename.extensionWithDotOrEmpty());
    }

    public boolean save() {
        // TODO on error, save exception as "last error", with getter
        return true; // true = no error; false = error (assume not saved)
    }

    public void onSaved() {
        this.dirty = false;
        this.model.get().flip();
    }

    public boolean isFileReadOnly() {
        return !this.filename.extensionOrEmpty().equalsIgnoreCase("ftm");
    }

    public void refreshModified() {
        this.dirty = this.model.get().isModified();
    }

    public boolean isModified() {
        return this.dirty;
    }

    public void setModified() {
        this.dirty = true;
    }

    public void setUnmodified() {
        this.dirty = false;
    }

    public boolean isOpen() {
        return this.file.isPresent();
    }

    public void close() {
        this.dirty = false;
        this.file = Optional.empty();
        this.model = Optional.empty();
    }

    public Rectangle2D bounds() {
        return Awts.rect(this.model.get().bounds());
    }





    private static final @NonNull DateTimeFormatter TS_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmssv");

    private @NonNull String timestamp() {
        return ZonedDateTime.now(ZoneOffset.UTC).format(TS_FORMAT);
    }

    private static final String REGEX_TS = "-\\d{8}T\\d{6}Z$";

    private static String removeTimestamp(final String name) {
        return name.replaceAll(REGEX_TS, "");
    }



}
