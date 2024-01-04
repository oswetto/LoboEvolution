
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
package org.loboevolution.html.dom.svg;

/**
 * <p>SVGLinearGradientElement interface.</p>
 *
 *
 *
 */
public interface SVGLinearGradientElement extends SVGGradientElement {
	/**
	 * <p>getX1.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getX1();

	/**
	 * <p>getY1.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getY1();

	/**
	 * <p>getX2.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getX2();

	/**
	 * <p>getY2.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getY2();
}
