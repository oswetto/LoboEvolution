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

/**
 * <p>SVGTextPathElement interface.</p> 
 */
public interface SVGTextPathElement extends SVGTextContentElement, SVGURIReference {
	// textPath Method Types
	/** Constant TEXTPATH_METHODTYPE_UNKNOWN=0 */
    short TEXTPATH_METHODTYPE_UNKNOWN = 0;
	/** Constant TEXTPATH_METHODTYPE_ALIGN=1 */
    short TEXTPATH_METHODTYPE_ALIGN = 1;
	/** Constant TEXTPATH_METHODTYPE_STRETCH=2 */
    short TEXTPATH_METHODTYPE_STRETCH = 2;
	// textPath Spacing Types
	/** Constant TEXTPATH_SPACINGTYPE_UNKNOWN=0 */
    short TEXTPATH_SPACINGTYPE_UNKNOWN = 0;
	/** Constant TEXTPATH_SPACINGTYPE_AUTO=1 */
    short TEXTPATH_SPACINGTYPE_AUTO = 1;
	/** Constant TEXTPATH_SPACINGTYPE_EXACT=2 */
    short TEXTPATH_SPACINGTYPE_EXACT = 2;

	/**
	 * <p>getStartOffset.</p>
	 *
	 * @return a {@link SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getStartOffset();

	/**
	 * <p>getMethod.</p>
	 *
	 * @return a {@link SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getMethod();

	/**
	 * <p>getSpacing.</p>
	 *
	 * @return a {@link SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getSpacing();
}
