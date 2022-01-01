/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2022 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.domimpl;

/**
 * <p>ImageListener interface.</p>
 *
 *
 *
 */
public interface ImageListener extends java.util.EventListener {
	/** Constant EMPTY_ARRAY */
	ImageListener[] EMPTY_ARRAY = new ImageListener[0];

	/**
	 * <p>imageLoaded.</p>
	 *
	 * @param event a {@link org.loboevolution.html.dom.domimpl.ImageEvent} object.
	 */
	void imageLoaded(ImageEvent event);
}
