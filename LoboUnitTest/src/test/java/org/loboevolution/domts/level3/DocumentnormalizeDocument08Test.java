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
import org.loboevolution.html.dom.DOMError;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.domimpl.DOMErrorMonitor;
import org.loboevolution.html.node.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Add two CDATASections containing "]]>" perform normalization with split-cdata-sections=true.
 * Should result in two warnings and at least 4 nodes.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-split-cdata-sections">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-split-cdata-sections</a>
 */
public class DocumentnormalizeDocument08Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        Element elem;
        final DOMConfiguration domConfig;
        HTMLCollection elemList;
        CDATASection newChild;
        final Node oldChild;
        final DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        final List<DOMError> errors;

        DOMError error;
        final int length;
        final NodeList childNodes;
        String type;
        int splittedCount = 0;
        int severity;
        doc = sampleXmlFile("barfoo.xml");
        elemList = doc.getElementsByTagName("p");
        elem = (Element) elemList.item(0);
        newChild = doc.createCDATASection("this is not ]]> good");
        oldChild = elem.getFirstChild();
        elem.replaceChild(newChild, oldChild);
        newChild = doc.createCDATASection("this is not ]]> good");
        elem.appendChild(newChild);
        domConfig = doc.getDomConfig();
        domConfig.setParameter("split-cdata-sections", Boolean.TRUE);
        /*DOMErrorMonitor */
        domConfig.setParameter("error-handler", errorMonitor);
        doc.normalizeDocument();
        errors = errorMonitor.getErrors();
        for (DOMError domError : errors) {
            error = domError;
            type = error.getType();
            severity = error.getSeverity();

            if ("cdata-sections-splitted".equals(type)) {
                splittedCount += 1;
            } else {
                assertEquals(1, severity, "DocumentnormalizeDocument08Assert3");
            }

        }
        assertEquals(2, splittedCount, "DocumentnormalizeDocument08Assert4");
        elemList = doc.getElementsByTagName("p");
        elem = (Element) elemList.item(0);
        childNodes = elem.getChildNodes();
        length = childNodes.getLength();
        assertTrue(length > 3, "DocumentnormalizeDocument08Assert5");
    }
}

