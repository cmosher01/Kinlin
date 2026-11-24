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

package dev.cmosher01.util;

import lombok.*;

import java.util.*;

/**
 * Modification tracker, for undo/redo functionality.
 * Not thread-safe.
 * "make" means to make the modification.
 * "undo" means to undo (i.e., reverse) the modification.
 * "redo" means to re-make a previously undone modification.
 */

/*
S = state
M = modification


Make n modifications, leaving current state at S[n]
Modifications are pushed onto the undo stack. Redo stack is empty.

S[0]      S[1]      S[2] ... S[i-1]         S[i]          S[i+1] ... S[n-2]        S[n-1]          S[n]
                                                                                                     ^
     M[0]      M[1]                 M[i-1]        M[i]                      M[n-2]        M[n-1]<-+  |  +-> [empty]
                                                                                                  |  |  |
                                                                                               undo  |  redo
                                                                                                     |
                                                                                                  current
                                                                                                   state

Undo.
The last modification (M[n-1]) is un-done, leaving current state at S[n-1].
Then M[n-1] is pushed onto the redo stack.


S[0]      S[1]      S[2] ... S[i-1]         S[i]          S[i+1] ... S[n-2]           S[n-1]         S[n]
                                                                                        ^
     M[0]      M[1]                 M[i-1]        M[i]                      M[n-2] <-+  |  +-> M[n-1]
                                                                                     |  |  |
                                                                                  undo  |  redo
                                                                                        |
                                                                                     current
                                                                                      state

More undos.

S[0]      S[1]      S[2] ... S[i-1]            S[i]        S[i+1]         S[i+2] ... S[n-1]        S[n]
                                                ^
     M[0]      M[1]                 M[i-1] <-+  |  +-> M[i]        M[i+1]                   M[n-1]
                                             |  |  |
                                          undo  |  redo
                                                |
                                             current
                                              state

Redo.
Modification M[i] popped of redo stack, and is re-done, leaving current state at S[i+1],
Then M[i] is pushed onto the undo stack.

S[0]      S[1]      S[2] ... S[i-1]           S[i]         S[i+1]           S[i+2] ... S[n-1]        S[n]
                                                             ^
     M[0]      M[1]                 M[i-1]         M[i] <-+  |  +->  M[i+1]                   M[n-1]
                                                          |  |  |
                                                       undo  |  redo
                                                             |
                                                          current
                                                           state

 */
public class ModificationTracker {
    public interface Modification {
        void make();
        void undo();
        void redo();
    }



    private final @NonNull Stack<@NonNull Modification> undoables = new Stack<>();
    private final @NonNull Stack<@NonNull Modification> redoables = new Stack<>();



    public void make(final @NonNull Modification c) {
        c.make();
        this.undoables.push(c);
        this.redoables.clear();
    }

    public boolean canUndo() {
        return !this.undoables.isEmpty();
    }

    public void undo() {
        if (canUndo()) {
            val c = undoables.pop();
            c.undo();
            this.redoables.push(c);
        }
    }

    public boolean canRedo() {
        return !this.redoables.isEmpty();
    }

    public void redo() {
        if (canRedo()) {
            val c = redoables.pop();
            c.redo();
            this.undoables.push(c);
        }
    }
}
