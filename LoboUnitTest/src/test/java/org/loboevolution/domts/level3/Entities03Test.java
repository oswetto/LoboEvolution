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


import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.DOMConfiguration;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.domimpl.DOMErrorMonitor;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Normalize document with entities set to false, check that
 * unbound entity references are preserved.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-entities">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-entities</a>
 */
public class Entities03Test extends LoboUnitTest {
    
    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        HTMLCollection pList;
        Element pElem;
        final DOMConfiguration domConfig;
        final DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        Node child;
        final String childName;
        final EntityReference entRef;
        final int childType;
        doc = sampleXmlFile("barfoo.xml");
        domConfig = doc.getDomConfig();
        domConfig.setParameter("entities", Boolean.FALSE);
        /*DOMErrorMonitor */
        domConfig.setParameter("error-handler", errorMonitor);
        pList = doc.getElementsByTagName("p");
        pElem = (Element) pList.item(0);
        entRef = doc.createEntityReference("ent3");
        pElem.appendChild(entRef);
        doc.normalizeDocument();
        assertTrue(errorMonitor.assertLowerSeverity(2), "Entities03Assert1");
        pList = doc.getElementsByTagName("p");
        pElem = (Element) pList.item(0);
        child = pElem.getLastChild();
        assertNotNull(child, "Entities03Assert2");
        childType = child.getNodeType();
        assertEquals(5, childType, "Entities03Assert3");
        childName = child.getNodeName();
        assertEquals("ent3", childName, "Entities03Assert4");
    }
}

