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
/*
 * Created on Dec 3, 2005
 */
package org.loboevolution.html.dom.domimpl;

import java.io.Serial;

class SkipVisitorException extends RuntimeException {
	/**
	 * 
	 */
	@Serial
    private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for SkipVisitorException.</p>
	 */
	public SkipVisitorException() {
		super();
	}

	/**
	 * <p>Constructor for SkipVisitorException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public SkipVisitorException(final String message) {
		super(message);
	}

	/**
	 * <p>Constructor for SkipVisitorException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param cause a {@link java.lang.Throwable} object.
	 */
	public SkipVisitorException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * <p>Constructor for SkipVisitorException.</p>
	 *
	 * @param cause a {@link java.lang.Throwable} object.
	 */
	public SkipVisitorException(final Throwable cause) {
		super(cause);
	}

}
