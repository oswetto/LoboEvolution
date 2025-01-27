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

package org.loboevolution.html.node;

import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Node;

/**
 * <p>ParentNode interface.</p>
 */
public interface ParentNode extends Node {

    /**
     * <p>getChildElementCount.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    int getChildElementCount();

    /**
     * Returns the child elements.
     *
     * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
     */
    HTMLCollection getChildren();

    /**
     * Returns the first child that is an element, and null otherwise.
     *
     * @return a {@link org.loboevolution.html.node.Element} object.
     */
    Element getFirstElementChild();

    /**
     * Returns the last child that is an element, and null otherwise.
     *
     * @return a {@link org.loboevolution.html.node.Element} object.
     */
    Element getLastElementChild();

    /**
     * Returns the first element that is a descendant of node that matches selectors.
     *
     * @param selectors a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.html.node.Element} object.
     */
    Element querySelector(String selectors);

    /**
     * Returns all element descendants of node that match selectors.
     *
     * @param selectors a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.html.node.NodeList} object.
     */
    NodeList querySelectorAll(String selectors);
}

