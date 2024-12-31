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

package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegCurvetoQuadraticRel;

/**
 * <p>SVGPathSegCurvetoQuadraticRelImpl class.</p>
 *
 *
 *
 */
public class SVGPathSegCurvetoQuadraticRelImpl implements SVGPathSegCurvetoQuadraticRel {

	private float x;

	private float y;

	private float x1;

	private float y1;

	/**
	 * <p>Constructor for SVGPathSegCurvetoQuadraticRelImpl.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param x1 a float.
	 * @param y1 a float.
	 */
	public SVGPathSegCurvetoQuadraticRelImpl(final float x, final float y, final float x1, final float y1) {
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
	}

	/** {@inheritDoc} */
	@Override
	public short getPathSegType() {
		return PATHSEG_CURVETO_QUADRATIC_REL;
	}

	/** {@inheritDoc} */
	@Override
	public String getPathSegTypeAsLetter() {
		return "q";
	}

	/** {@inheritDoc} */
	@Override
	public float getX() {
		return x;
	}

	/** {@inheritDoc} */
	@Override
	public void setX(final float x) {
		this.x = x;
	}

	/** {@inheritDoc} */
	@Override
	public float getY() {
		return y;
	}

	/** {@inheritDoc} */
	@Override
	public void setY(final float y) {
		this.y = y;
	}

	/** {@inheritDoc} */
	@Override
	public float getX1() {
		return x1;
	}

	/** {@inheritDoc} */
	@Override
	public void setX1(final float x1) {
		this.x1 = x1;
	}

	/** {@inheritDoc} */
	@Override
	public float getY1() {
		return y1;
	}

	/** {@inheritDoc} */
	@Override
	public void setY1(final float y1) {
		this.y1 = y1;
	}
}
