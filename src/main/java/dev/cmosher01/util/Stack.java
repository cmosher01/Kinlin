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

package dev.cmosher01.util;

import lombok.NonNull;

import java.util.*;

public class Stack<@NonNull E> {
    private final @NonNull Deque<@NonNull E> deque = new ArrayDeque<>();

    public void push(final @NonNull E item) {
        this.deque.push(item);
    }

    public @NonNull E pop() {
        return this.deque.pop();
    }

    public void clear() {
        this.deque.clear();
    }

    public boolean isEmpty() {
        return this.deque.isEmpty();
    }
}
