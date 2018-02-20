/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.gui;

/**
 * Whenever {@link FramePanel} is used, browser windows should implement this
 * interface or extend {@link AbstractBrowserWindow}. Note that
 * {@link BrowserPanel} implements this interface already, so it is not
 * necessary to have windows implement it when <code>BrowserPanel</code> is
 * used.
 */
public interface BrowserWindow {

	/**
	 * Gets the top frame panel.
	 *
	 * @return the top frame panel
	 */
	FramePanel getTopFramePanel();

	/**
	 * Gets the window callback.
	 *
	 * @return the window callback
	 */
	WindowCallback getWindowCallback();
}