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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Invoke the renameNode method to rename the class attribute node of the
 * second element whose localName is acronym and namespaceURI <a href="http://www.nist.gov">...</a>
 * with the new namespaceURI as <a href="http://www.w3.org/DOM/Test">...</a> and name as pre0fix:renamedNode.
 * Check if this attribute has been renamed successfully by verifying the
 * nodeName, namespaceURI, nodeType attributes of the renamed node.
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode</a>
 */
public class Documentrenamenode01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final Element element;
        final Attr attr;
        final HTMLCollection childList;
        final Node renamedclass;
        final String nodeName;
        final int nodeType;
        final String namespaceURI;
        doc = sampleXmlFile("hc_staff.xml");
        childList = doc.getElementsByTagName("acronym");
        element = (Element) childList.item(1);
        attr = element.getAttributeNode("class");
        renamedclass = doc.renameNode(attr, "http://www.w3.org/DOM/Test", "renamedNode");
        nodeName = renamedclass.getNodeName();
        namespaceURI = renamedclass.getNamespaceURI();
        nodeType = renamedclass.getNodeType();
        assertEquals("renamedNode", nodeName, "Documentrenamenode01Assert2");
        assertEquals(2, nodeType, "Documentrenamenode01Assert3");
        assertEquals("http://www.w3.org/DOM/Test", namespaceURI, "Documentrenamenode01Assert4");
    }
}

