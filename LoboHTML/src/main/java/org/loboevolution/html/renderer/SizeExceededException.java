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

package org.loboevolution.html.renderer;

import java.io.Serial;

class SizeExceededException extends RuntimeException {
	/**
	 * 
	 */
	@Serial
    private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for SizeExceededException.</p>
	 */
	public SizeExceededException() {
		super();
	}

	/**
	 * <p>Constructor for SizeExceededException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public SizeExceededException(final String message) {
		super(message);
	}

	/**
	 * <p>Constructor for SizeExceededException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param cause a {@link java.lang.Throwable} object.
	 */
	public SizeExceededException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * <p>Constructor for SizeExceededException.</p>
	 *
	 * @param cause a {@link java.lang.Throwable} object.
	 */
	public SizeExceededException(final Throwable cause) {
		super(cause);
	}
}
