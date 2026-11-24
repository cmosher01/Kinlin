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

import static dev.cmosher01.genealogy.xy.MenuAccelerators.*;



public class MenuView extends JMenuBar {
    private static final @NonNull String APP_NAME = System.getProperty("apple.awt.application.name", "");

    @SuppressWarnings("UnnecessaryUnicodeEscape")
    // ellipsis, "dot dot dot"
    private static final @NonNull String DDD = Character.toString(0x2026);

    // TODO store as resources for internationalization purposes
    private static final @NonNull String MENU_FILE = "File";
    private static final @NonNull String CMD_NEW = "New";
    private static final @NonNull String CMD_OPEN = "Open"+DDD;
    private static final @NonNull String CMD_CLOSE = "Close";
    private static final @NonNull String CMD_SAVE = "Save";
    private static final @NonNull String CMD_SAVE_AS = "Save As"+DDD;
    private static final @NonNull String CMD_EXPORT_PDF = "Export PDF"+DDD; // domain-specific
    private static final @NonNull String CMD_EXPORT_MOD = "Export Modified As Skeleton"+DDD; // domain-specific
    private static final @NonNull String CMD_EXPORT_ALL = "Export All As Skeleton"+DDD; // domain-specific
    private static final @NonNull String CMD_PAGE_SETUP = "Page Setup";
    private static final @NonNull String CMD_PRINT = "Print"+DDD;
    private static final @NonNull String CMD_QUIT = "Quit "+APP_NAME;

    private static final @NonNull String MENU_EDIT = "Edit";
    private static final @NonNull String CMD_UNDO = "Undo";
    private static final @NonNull String CMD_REDO = "Redo";
    private static final @NonNull String CMD_CUT = "Cut";
    private static final @NonNull String CMD_COPY = "Copy";
    private static final @NonNull String CMD_PASTE = "Paste";
    private static final @NonNull String CMD_DELETE = "Delete";
    private static final @NonNull String CMD_SELECT_All = "Select All";
    private static final @NonNull String CMD_FIND = "Find"+DDD;
    private static final @NonNull String CMD_FIND_NEXT = "Find Next";
    private static final @NonNull String CMD_FIND_PREVIOUS = "Find Previous";
    private static final @NonNull String CMD_NUDGE = "Nudge"; // domain-specific
    private static final @NonNull String CMD_GRID = "Grid"+DDD; // domain-specific
    private static final @NonNull String CMD_PREFERENCES = "Preferences"+DDD;

    private static final @NonNull String MENU_VIEW = "View";
    private static final @NonNull String CMD_FIT = "Fit"; // domain-specific
    private static final @NonNull String CMD_CENTER = "Center"; // domain-specific
    private static final @NonNull String CMD_JUMP = "Jump"; // domain-specific
    private static final @NonNull String CMD_RESET = "Reset"; // domain-specific

    private static final @NonNull String MENU_HELP = "Help";
    private static final @NonNull String CMD_HELP = "Help";
    private static final @NonNull String CMD_ABOUT = "About "+APP_NAME;





    private final @NonNull DocumentModel modelDocument;

    private final @NonNull ActiveMenu actNew = ActiveMenu.create(CMD_NEW, ACCEL_NEW);
    private final @NonNull ActiveMenu actOpen = ActiveMenu.create(CMD_OPEN, ACCEL_OPEN);
    private final @NonNull ActiveMenu actClose = ActiveMenu.create(CMD_CLOSE, ACCEL_CLOSE);
    private final @NonNull ActiveMenu actSave = ActiveMenu.create(CMD_SAVE, ACCEL_SAVE);
    private final @NonNull ActiveMenu actSaveAs = ActiveMenu.create(CMD_SAVE_AS, ACCEL_SAVE_AS);
    private final @NonNull ActiveMenu actExportPdf = ActiveMenu.create(CMD_EXPORT_PDF, ACCEL_EXPORT_PDF);
    private final @NonNull ActiveMenu actExportMod = ActiveMenu.create(CMD_EXPORT_MOD, ACCEL_EXPORT_MOD);
    private final @NonNull ActiveMenu actExportAll = ActiveMenu.create(CMD_EXPORT_ALL, ACCEL_EXPORT_ALL);
    private final @NonNull ActiveMenu actPageSetup = ActiveMenu.create(CMD_PAGE_SETUP, ACCEL_PAGE_SETUP);
    private final @NonNull ActiveMenu actPrint = ActiveMenu.create(CMD_PRINT, ACCEL_PRINT);
    private final @NonNull ActiveMenu actQuit = ActiveMenu.create(CMD_QUIT, ACCEL_QUIT);

    private final @NonNull ActiveMenu actUndo = ActiveMenu.create(CMD_UNDO, ACCEL_UNDO);
    private final @NonNull ActiveMenu actRedo = ActiveMenu.create(CMD_REDO, ACCEL_REDO);
    private final @NonNull ActiveMenu actCut = ActiveMenu.create(CMD_CUT, ACCEL_CUT);
    private final @NonNull ActiveMenu actCopy = ActiveMenu.create(CMD_COPY, ACCEL_COPY);
    private final @NonNull ActiveMenu actPaste = ActiveMenu.create(CMD_PASTE, ACCEL_PASTE);
    private final @NonNull ActiveMenu actDelete = ActiveMenu.create(CMD_DELETE, ACCEL_DELETE);
    private final @NonNull ActiveMenu actSelectAll = ActiveMenu.create(CMD_SELECT_All, ACCEL_SELECT_ALL);
    private final @NonNull ActiveMenu actFind = ActiveMenu.create(CMD_FIND, ACCEL_FIND);
    private final @NonNull ActiveMenu actFindNext = ActiveMenu.create(CMD_FIND_NEXT, ACCEL_FIND_NEXT);
    private final @NonNull ActiveMenu actFindPrevious = ActiveMenu.create(CMD_FIND_PREVIOUS, ACCEL_FIND_PREVIOUS);
    private final @NonNull ActiveMenu actNudge = ActiveMenu.create(CMD_NUDGE, ACCEL_NUDGE);
    private final @NonNull ActiveMenu actGrid = ActiveMenu.create(CMD_GRID, ACCEL_GRID);
    private final @NonNull ActiveMenu actPreferences = ActiveMenu.create(CMD_PREFERENCES, ACCEL_PREFERENCES);

    private final @NonNull ActiveMenu actFit = ActiveMenu.create(CMD_FIT, ACCEL_FIT);
    private final @NonNull ActiveMenu actCenter = ActiveMenu.create(CMD_CENTER, ACCEL_CENTER);
    private final @NonNull ActiveMenu actJump = ActiveMenu.create(CMD_JUMP, ACCEL_JUMP);
    private final @NonNull ActiveMenu actReset = ActiveMenu.create(CMD_RESET, ACCEL_RESET);

    private final @NonNull ActiveMenu actHelp = ActiveMenu.create(CMD_HELP, ACCEL_HELP);
    private final @NonNull ActiveMenu actAbout = ActiveMenu.create(CMD_ABOUT, ACCEL_ABOUT);





    public MenuView(final @NonNull DocumentModel modelDocument) {
        this.modelDocument = modelDocument;

        val menuFile = new JMenu(MENU_FILE);
//        menuFile.add(this.actNew.menu());
        menuFile.add(this.actOpen.menu());
        menuFile.addSeparator();
        menuFile.add(this.actClose.menu());
        menuFile.addSeparator();
        menuFile.add(this.actSave.menu());
        menuFile.add(this.actSaveAs.menu());
        menuFile.addSeparator();
        menuFile.add(this.actExportPdf.menu());
        menuFile.add(this.actExportMod.menu());
        menuFile.add(this.actExportAll.menu());
//        menuFile.addSeparator();
//        menuFile.add(this.actPageSetup.menu());
//        menuFile.add(this.actPrint.menu());
        menuFile.addSeparator();
        menuFile.add(this.actQuit.menu());
        add(menuFile);

        val menuEdit = new JMenu(MENU_EDIT);
        menuEdit.add(this.actUndo.menu());
        menuEdit.add(this.actRedo.menu());
        menuEdit.addSeparator();
//        menuEdit.add(this.actCut.menu());
//        menuEdit.add(this.actCopy.menu());
//        menuEdit.add(this.actPaste.menu());
//        menuEdit.add(this.actDelete.menu());
        menuEdit.add(this.actSelectAll.menu());
        menuEdit.addSeparator();
        menuEdit.add(this.actFind.menu());
        menuEdit.add(this.actFindNext.menu());
        menuEdit.add(this.actFindPrevious.menu());
        menuEdit.addSeparator();
        menuEdit.add(this.actNudge.menu());
        menuEdit.addSeparator();
        menuEdit.add(this.actGrid.menu());
        menuEdit.addSeparator();
        menuEdit.add(this.actPreferences.menu());
        add(menuEdit);

        val menuView = new JMenu(MENU_VIEW);
        menuView.add(this.actFit.menu());
        menuView.add(this.actCenter.menu());
        menuView.add(this.actJump.menu());
        menuView.add(this.actReset.menu());
        add(menuView);

        val menuHelp = new JMenu(MENU_HELP);
        menuHelp.add(this.actHelp.menu());
        menuHelp.addSeparator();
        menuHelp.add(this.actAbout.menu());
        add(menuHelp);
    }





    public void refresh() {
        this.actOpen.setEnabled(!this.modelDocument.isOpen());
        this.actClose.setEnabled(this.modelDocument.isOpen());
        this.actSave.setEnabled(this.modelDocument.isOpen());
        this.actSaveAs.setEnabled(this.modelDocument.isOpen());
        this.actExportPdf.setEnabled(this.modelDocument.isOpen());
        this.actExportMod.setEnabled(this.modelDocument.isOpen());
        this.actExportAll.setEnabled(this.modelDocument.isOpen());

        this.actUndo.setEnabled(this.modelDocument.canUndo());
        this.actRedo.setEnabled(this.modelDocument.canRedo());
        this.actSelectAll.setEnabled(this.modelDocument.isOpen());
        this.actFind.setEnabled(this.modelDocument.isOpen());
        this.actFindNext.setEnabled(this.modelDocument.isOpen());
        this.actFindPrevious.setEnabled(this.modelDocument.isOpen());
        this.actNudge.setEnabled(this.modelDocument.hasSelected());
        this.actGrid.setEnabled(this.modelDocument.isOpen());

        this.actFit.setEnabled(this.modelDocument.isOpen());
        this.actCenter.setEnabled(this.modelDocument.isOpen());
        this.actJump.setEnabled(this.modelDocument.isOpen());
        this.actReset.setEnabled(this.modelDocument.isOpen());
    }



    public void setNew(final @NonNull Runnable lambda) {
        this.actNew.setAction(lambda);
    }
    public void setOpen(final @NonNull Runnable lambda) {
        this.actOpen.setAction(lambda);
    }
    public void setClose(final @NonNull Runnable lambda) {
        this.actClose.setAction(lambda);
    }
    public void setSave(final @NonNull Runnable lambda) {
        this.actSave.setAction(lambda);
    }
    public void setSaveAs(final @NonNull Runnable lambda) {
        this.actSaveAs.setAction(lambda);
    }
    public void setExportPdf(final @NonNull Runnable lambda) {
        this.actExportPdf.setAction(lambda);
    }
    public void setExportMod(final @NonNull Runnable lambda) {
        this.actExportMod.setAction(lambda);
    }
    public void setExportAll(final @NonNull Runnable lambda) {
        this.actExportAll.setAction(lambda);
    }
    public void setPageSetup(final @NonNull Runnable lambda) {
        this.actPageSetup.setAction(lambda);
    }
    public void setPrint(final @NonNull Runnable lambda) {
        this.actPrint.setAction(lambda);
    }
    public void setQuit(final @NonNull Runnable lambda) {
        this.actQuit.setAction(lambda);
    }

    public void setUndo(final @NonNull Runnable lambda) {
        this.actUndo.setAction(lambda);
    }
    public void setRedo(final @NonNull Runnable lambda) {
        this.actRedo.setAction(lambda);
    }
    public void setCut(final @NonNull Runnable lambda) {
        this.actCut.setAction(lambda);
    }
    public void setCopy(final @NonNull Runnable lambda) {
        this.actCopy.setAction(lambda);
    }
    public void setPaste(final @NonNull Runnable lambda) {
        this.actPaste.setAction(lambda);
    }
    public void setDelete(final @NonNull Runnable lambda) {
        this.actDelete.setAction(lambda);
    }
    public void setSelectAll(final @NonNull Runnable lambda) {
        this.actSelectAll.setAction(lambda);
    }
    public void setFind(final @NonNull Runnable lambda) {
        this.actFind.setAction(lambda);
    }
    public void setFindNext(final @NonNull Runnable lambda) {
        this.actFindNext.setAction(lambda);
    }
    public void setFindPrevious(final @NonNull Runnable lambda) {
        this.actFindPrevious.setAction(lambda);
    }
    public void setNudge(final @NonNull Runnable lambda) {
        this.actNudge.setAction(lambda);
    }
    public void setGrid(final @NonNull Runnable lambda) {
        this.actGrid.setAction(lambda);
    }
    public void setPreferences(final @NonNull Runnable lambda) {
        this.actPreferences.setAction(lambda);
    }

    public void setFit(final @NonNull Runnable lambda) {
        this.actFit.setAction(lambda);
    }
    public void setCenter(final @NonNull Runnable lambda) {
        this.actCenter.setAction(lambda);
    }
    public void setJump(final @NonNull Runnable lambda) {
        this.actJump.setAction(lambda);
    }
    public void setReset(final @NonNull Runnable lambda) {
        this.actReset.setAction(lambda);
    }

    public void setHelp(final @NonNull Runnable lambda) {
        this.actHelp.setAction(lambda);
    }
    public void setAbout(final @NonNull Runnable lambda) {
        this.actAbout.setAction(lambda);
    }
}
