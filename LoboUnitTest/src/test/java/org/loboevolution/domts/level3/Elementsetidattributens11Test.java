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
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Declares the attribute specified by local name and namespace URI to be of type ID. If the value of the
 * specified attribute is unique then this element node can later be retrieved using getElementById on Document.
 * Note, however, that this simply affects this node and does not change any grammar that may be in use.
 * <p>
 * Invoke setIdAttributeNS on two existing namespace attributes with same local name but different values.  Verify by calling
 * isId on the attributes node and getElementById with different values on document node.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNS">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNS</a>
 */
public class Elementsetidattributens11Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection elemList;
        final Element pElem1;
        final Element pElem2;
        NamedNodeMap attributesMap;
        Attr attr;
        boolean id;
        Element elem;
        String elemName;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagNameNS("*", "p");
        pElem1 = (Element) elemList.item(1);
        pElem2 = (Element) elemList.item(2);
        pElem1.setIdAttributeNS("http://www.usa.com", "dmstc", true);
        pElem2.setIdAttributeNS("http://www.usa.com", "dmstc", true);
        attributesMap = pElem1.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("xmlns:dmstc");
        id = attr.isId();
        assertTrue(id, "Elementsetidattributens11Assert3");
        attributesMap = pElem2.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("xmlns:dmstc");
        id = attr.isId();
        assertTrue(id, "Elementsetidattributens11Assert4");
        elem = doc.getElementById("http://www.netzero.com");
        elemName = elem.getTagName();
        assertEquals("P", elemName, "Elementsetidattributens11Assert5");
        elem = doc.getElementById("http://www.usa.com");
        elemName = elem.getTagName();
        assertEquals("P", elemName, "Elementsetidattributens11Assert6");
    }
}

