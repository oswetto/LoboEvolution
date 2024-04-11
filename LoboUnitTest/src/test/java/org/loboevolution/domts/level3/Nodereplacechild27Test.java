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

package org.loboevolution.domts.level3;


import org.htmlunit.cssparser.dom.DOMException;
import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The method replaceChild replaces the child node oldChild with newChild in the list of
 * children, and returns the oldChild node.
 * Using replaceChild on an Element node attempt to replace an Element node with another
 * Element from another document and verify if a WRONG_DOCUMENT_ERR gets thrown.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class Nodereplacechild27Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final Document doc2;
        final HTMLCollection childList;
        final HTMLCollection childList2;
        final Element elem2;
        final Element elem;
        final Node firstChild;
        String nodeName;
        final Node replaced;
        doc = sampleXmlFile("hc_staff.xml");
        childList = doc.getElementsByTagNameNS("*", "p");
        elem = (Element) childList.item(0);
        firstChild = elem.getFirstChild();
        doc2 = sampleXmlFile("hc_staff.xml");
        childList2 = doc2.getElementsByTagNameNS("*", "p");
        elem2 = (Element) childList2.item(0);

        {
            boolean success = false;
            try {
                replaced = elem.replaceChild(elem2, firstChild);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.WRONG_DOCUMENT_ERR);
            }
            assertTrue(success, "Nodereplacechild27Assert2");
        }
    }
}
