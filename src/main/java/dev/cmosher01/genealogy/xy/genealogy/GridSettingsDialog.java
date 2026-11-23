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

import dev.cmosher01.geom.*;
import dev.cmosher01.math.doubles.DoubleUtil;
import lombok.*;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import static javax.swing.JOptionPane.*;

@RequiredArgsConstructor
public class GridSettingsDialog {
    private final @NonNull JFrame parent;
    private final @NonNull Grid grid;

    public void dialog() {
        val cbActive = new JCheckBox("Active", this.grid.isActive());
        val txGrid = new JTextField(s(this.grid.getX().k().k()), 15);
        val txOffset = new JTextField(s(this.grid.getX().d().d()), 15);

        final Runnable syncFieldState = () -> {
            txGrid.setEnabled(cbActive.isSelected());
            txOffset.setEnabled(cbActive.isSelected());
        };
        cbActive.addItemListener(e -> syncFieldState.run());
        syncFieldState.run();

        final Object[] display = { cbActive, "Grid", txGrid, "Offset", txOffset };

        boolean ask = true;

        while (ask) {
            val answer = showConfirmDialog(this.parent, display, "Grid Settings", OK_CANCEL_OPTION);

            ask = false;
            if (answer == OK_OPTION) {
                this.grid.setActive(cbActive.isSelected());
                if (cbActive.isSelected()) {
                    try {
                        setGrid(txGrid, txOffset);
                    } catch (final Exception ex) {
                        showErrorDialog();
                        ask = true;
                    }
                }
            }
        }
    }

    private static String s(final double d) {
        return Integer.toString(DoubleUtil.asInt(d), 10);
    }

    private void setGrid(final @NonNull JTextComponent txGrid, final @NonNull JTextComponent txOffset) throws NumberFormatException {
        val k = Integer.parseInt(txGrid.getText().strip());
        val d = Integer.parseInt(txOffset.getText().strip());

        this.grid.setX(new Quantizer(new ScaleFactor(DoubleUtil.fp(k)), new MoveTerm(DoubleUtil.fp(d))));
        this.grid.setY(this.grid.getX());
    }

    private void showErrorDialog() {
        showMessageDialog(this.parent, "Please enter valid integer numbers for both fields.", "Invalid Input", ERROR_MESSAGE);
    }
}
