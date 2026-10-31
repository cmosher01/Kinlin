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

import dev.cmosher01.genealogy.xy.genealogy.ChartCommandController;

/**
 * Sets itself as a listener of actions on the menu view,
 * in order to forward them to the command controller
 */
public class MenuController {
    public MenuController(final MenuView view, final DocumentController controllerDocument, final CommandController controllerCommand, final QuitController controllerQuit, final ChartCommandController controllerChart) {
//        view.setNew(controllerCommand::newFile);
        view.setOpen(controllerDocument::open);
        view.setClose(controllerDocument::close);
//        view.setExport(); TODO
        view.setSave(controllerDocument::save);
        view.setSaveAs(controllerDocument::saveAs);
//        view.setPageSetup(controllerCommand::pageSetup);
//        view.setPrint(controllerCommand::print);
        view.setQuit(controllerQuit::quit);

        view.setUndo(controllerCommand::undo);
        view.setRedo(controllerCommand::redo);
//        view.setCut(controllerCommand::cut);
//        view.setCopy(controllerCommand::copy);
//        view.setPaste(controllerCommand::paste);
//        view.setDelete(controllerCommand::delete);
        view.setSelectAll(controllerCommand::selectAll);
        view.setFind(controllerChart::find);
        view.setFindNext(controllerChart::findNext);
        view.setFindPrevious(controllerChart::findPrevious);
//        view.setNudge(); TODO
        view.setPreferences(controllerCommand::preferences);

        view.setFit(controllerChart::fit);
        view.setCenter(controllerChart::center);
//        view.setJump(); TODO
        view.setReset(controllerChart::reset);

        view.setHelp(controllerCommand::help);
        view.setAbout(controllerCommand::about);
    }
}
