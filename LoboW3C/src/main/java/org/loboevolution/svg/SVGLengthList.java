
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.svg;


import org.htmlunit.cssparser.dom.DOMException;

/**
 * <p>SVGLengthList interface.</p>
 */
public interface SVGLengthList {
	/**
	 * <p>getNumberOfItems.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	int getNumberOfItems();

	/**
	 * <p>clear.</p>
	 *
	 * @throws DOMException if any.
	 */
	void clear();

	/**
	 * <p>initialize.</p>
	 *
	 * @param newItem a {@link SVGLength} object.
	 * @return a {@link SVGLength} object.
	 * @throws DOMException if any.
	 * @throws SVGException if any.
	 * @throws SVGException if any.
	 * @throws SVGException if any.
	 * @throws SVGException if any.
	 * @throws SVGException if any.
	 */
	SVGLength initialize(SVGLength newItem) throws SVGException;

	/**
	 * <p>getItem.</p>
	 *
	 * @param index a {@link java.lang.Integer} object.
	 * @return a {@link SVGLength} object.
	 * @throws DOMException if any.
	 */
	SVGLength getItem(int index);

	/**
	 * <p>insertItemBefore.</p>
	 *
	 * @param newItem a {@link SVGLength} object.
	 * @param index a {@link java.lang.Integer} object.
	 * @return a {@link SVGLength} object.
	 * @throws DOMException if any.
	 * @throws SVGException if any.
	 * @throws SVGException if any.
	 * @throws SVGException if any.
	 * @throws SVGException if any.
	 * @throws SVGException if any.
	 */
	SVGLength insertItemBefore(SVGLength newItem, int index) throws SVGException;

	/**
	 * <p>replaceItem.</p>
	 *
	 * @param newItem a {@link SVGLength} object.
	 * @param index a {@link java.lang.Integer} object.
	 * @return a {@link SVGLength} object.
	 * @throws DOMException if any.
	 * @throws SVGException if any.
	 * @throws SVGException if any.
	 * @throws SVGException if any.
	 * @throws SVGException if any.
	 * @throws SVGException if any.
	 */
	SVGLength replaceItem(SVGLength newItem, int index) throws SVGException;

	/**
	 * <p>removeItem.</p>
	 *
	 * @param index a {@link java.lang.Integer} object.
	 * @return a {@link SVGLength} object.
	 * @throws DOMException if any.
	 */
	SVGLength removeItem(int index);

	/**
	 * <p>appendItem.</p>
	 *
	 * @param newItem a {@link SVGLength} object.
	 * @return a {@link SVGLength} object.
	 * @throws DOMException if any.
	 * @throws SVGException if any.
	 * @throws SVGException if any.
	 * @throws SVGException if any.
	 * @throws SVGException if any.
	 * @throws SVGException if any.
	 */
	SVGLength appendItem(SVGLength newItem) throws SVGException;
}
