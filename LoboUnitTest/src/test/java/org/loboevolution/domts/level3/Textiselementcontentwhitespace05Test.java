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

package org.loboevolution.domts.level3;


import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.DOMConfiguration;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.domimpl.DOMErrorMonitor;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Replace the whitespace before the "p" element in barfoo with non-whitespace and normalize with validation.
 * isElementContentWhitespace should be false since the node is not whitespace.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Text3-isElementContentWhitespace">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Text3-isElementContentWhitespace</a>
 */
public class Textiselementcontentwhitespace05Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        HTMLCollection bodyList;
        Element bodyElem;
        final Text textNode;
        final Text nonBlankNode;
        final boolean isElemContentWhitespace;
        final DOMConfiguration domConfig;
        final boolean canSetValidation;
        final Node refChild;
        final DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        doc = sampleXmlFile("barfoo.xml");
        domConfig = doc.getDomConfig();
        canSetValidation = domConfig.canSetParameter("validate", Boolean.TRUE);

        if (canSetValidation) {
            domConfig.setParameter("validate", Boolean.TRUE);
            /*DOMErrorMonitor */
            domConfig.setParameter("error-handler", errorMonitor);
            bodyList = doc.getElementsByTagName("body");
            bodyElem = (Element) bodyList.item(0);
            refChild = bodyElem.getFirstChild();
            nonBlankNode = doc.createTextNode("not blank");
            bodyElem.insertBefore(nonBlankNode, refChild);
            doc.normalizeDocument();
            assertTrue(errorMonitor.assertLowerSeverity(2), "Textiselementcontentwhitespace05Assert3");
            bodyList = doc.getElementsByTagName("body");
            bodyElem = (Element) bodyList.item(0);
            textNode = (Text) bodyElem.getFirstChild();
            isElemContentWhitespace = textNode.isElementContentWhitespace();
            assertFalse(isElemContentWhitespace, "Textiselementcontentwhitespace05Assert4");
        }
    }
}

