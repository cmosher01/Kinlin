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

import lombok.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ActiveMenu extends AbstractAction {
    private @NonNull JMenuItem menu;
    private @NonNull Runnable lambda;

    public static @NonNull ActiveMenu create(final @NonNull String name, final KeyStroke accel) {
        val o = new ActiveMenu(name);
        o.menu = new JMenuItem(o);
        o.menu.setAccelerator(accel);
        return o;
    }

    private ActiveMenu(final @NonNull String name) {
        super(name);
    }

    public @NonNull JMenuItem menu() {
        return this.menu;
    }

    public void setAction(final @NonNull Runnable lambda) {
        this.lambda = lambda;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        this.lambda.run();
    }
}
