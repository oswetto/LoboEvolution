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

package org.loboevolution.js.geom;

/**
 * <p>DOMRect interface.</p>
 */
public interface DOMRect extends DOMRectReadOnly {

    /**
     * <p>setBottom.</p>
     *
     * @param bottom a {@link java.lang.Integer} object.
     */
    void setBottom(int bottom);

    /**
     * <p>setHeight.</p>
     *
     * @param height a {@link java.lang.Integer} object.
     */
    void setHeight(int height);

    /**
     * <p>setLeft.</p>
     *
     * @param left a {@link java.lang.Integer} object.
     */
    void setLeft(int left);

    /**
     * <p>setRight.</p>
     *
     * @param right a {@link java.lang.Integer} object.
     */
    void setRight(int right);

    /**
     * <p>setTop.</p>
     *
     * @param top a {@link java.lang.Integer} object.
     */
    void setTop(int top);

    /**
     * <p>setWidth.</p>
     *
     * @param width a {@link java.lang.Integer} object.
     */
    void setWidth(int width);

    /**
     * <p>setX.</p>
     *
     * @param x a {@link java.lang.Integer} object.
     */
    void setX(int x);

    /**
     * <p>setY.</p>
     *
     * @param y a {@link java.lang.Integer} object.
     */
    void setY(int y);
}
