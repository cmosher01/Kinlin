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

import dev.cmosher01.geom.*;
import lombok.NonNull;

import java.util.*;
/*
    NOTES

    upon opening the file/database, each indi may have zero, one, or more _XY values
    currently only the first _XY value is read/updated
    first, the layout algorithm is called to position all indi without _XY values
    [need to run a final check of all indis after automatic layout, and assign a point to any remaining missing]
    therefore, after this layout process, we are working with a single _XY value for each and every indi

    indi coord model (all are optional):
        write-once orig contains the (first) _XY, or nothing if there is no _XY, from the file/db
        layout contains the automatic laid-out position, or nothing if not automatically laid out
        user contains the current position (due to user dragging)

        raw is used to track the dragging position while displaying at the snapped-to-grid position

    get():   first of: user, layout, orig
    dirty(): get() != orig
    flip():  orig = user; layout = user = empty
 */

/**
 * A point that can be modified. There are two types of modifications:
 * those due to app automatic layout, and those due to user actions.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class MovablePoint {
    private @NonNull Optional<Point> original;
    private @NonNull Optional<Point> layout = Optional.empty();
    private @NonNull Optional<Point> user = Optional.empty();

    private @NonNull Optional<Point> cache = Optional.empty();

    private final @NonNull Grid g;
    private @NonNull Point raw = Point.NULL;



    public MovablePoint(final @NonNull Grid g) {
        this(g, Optional.empty());
    }

    public MovablePoint(final @NonNull Grid g, final @NonNull Point original) {
        this(g, Optional.of(original));
    }

    public MovablePoint(final @NonNull Grid g, final @NonNull Optional<Point> original) {
        this.g = g;
        this.original = original;
        update();
    }



    /**
     * Gets the original ("last saved") point, or empty.
     * @return the original point or empty
     */
    public @NonNull Optional<Point> getOriginal() {
        return this.original;
    }

    /**
     * Gets the resulting point, in this order of priority:
     * user modified, layout modified, original point.
     * This method is most useful when you have already
     * guaranteed the point exists (e.g., via layoutMoveTo).
     *
     * @return the point
     * @throws NoSuchElementException if no points are present
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public @NonNull Point get() {
        return this.cache.get();
    }

    /**
     * Gets the resulting point, in this order of priority:
     * user modified, layout modified, original, default.
     *
     * @return the point or default
     */
    public @NonNull Point getOrElse(final @NonNull Point ptDefault) {
        return this.cache.orElse(ptDefault);
    }

    /**
     * Gets the resulting point, in this order of priority:
     * user modified, layout modified, original.
     *
     * @return the point or empty
     */
    public @NonNull Optional<Point> getOptional() {
        return this.cache;
    }

    public boolean isEmpty() {
        return this.cache.isEmpty();
    }

    public boolean isPresent() {
        return this.cache.isPresent();
    }

    /**
     * Checks if this point has been moved from its original location.
     * "Dynamic" in that if this point is moved from its original
     * location elsewhere, and then moved back to its original
     * location, it goes back to being not dirty.
     *
     * @return true if modified
     */
    public boolean dirty() {
        return !this.cache.equals(this.original);
    }



    public void move(final @NonNull Xlation d) {
        if (this.raw.empty()) {
            anchor();
        }
        (this.raw) += d;
        userMoveTo(this.g.snap(this.raw));
    }

    public void anchor() {
        this.raw = getOrElse(Point.NULL);
    }



    /**
     * Move this point (called by an automatic layout algorithm).
     *
     * @param pt new location
     * @return true if the original point exists (and is
     * therefore being OVERRIDDEN by this layout move).
     */
    public boolean layoutMoveTo(final @NonNull Point pt) {
        this.layout = Optional.of(pt);
        update();
        return this.original.isPresent();
    }

    /**
     * Move this point (called as the result of a user's action).
     *
     * @param pt new location
     */
    public void userMoveTo(final @NonNull Point pt) {
        this.user = Optional.of(pt);
        update();
    }

    /**
     * Discards any user movement.
     */
    public void userDiscard() {
        this.user = Optional.empty();
        update();
    }

    /**
     * "Flip" this point (after it has been saved).
     */
    public void flip() {
        this.original = this.cache;
        this.layout = Optional.empty();
        this.user = Optional.empty();
        update();
    }



    private void update() {
        this.cache =
            this.user.or(
            () -> this.layout).or(
            () -> this.original);
    }
}
