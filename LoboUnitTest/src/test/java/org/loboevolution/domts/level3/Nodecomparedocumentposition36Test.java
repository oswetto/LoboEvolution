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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Using compareDocumentPosition to check if the document position of an Attribute compared with
 * the element that follows its parent as a parameter is FOLLOWING, and is PRECEDING
 * vice versa.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class Nodecomparedocumentposition36Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection elemList;
        final Element elem;
        final HTMLCollection elemListFollows;
        final Element elemFollows;
        final Attr attr;
        final int attrPosition;
        final int elemFollowsPosition;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("p");
        elem = (Element) elemList.item(3);
        attr = elem.getAttributeNodeNS("*", "dir");
        elemListFollows = doc.getElementsByTagName("strong");
        elemFollows = (Element) elemListFollows.item(3);
        attrPosition = attr.compareDocumentPosition(elemFollows);
        assertEquals(4, attrPosition, "Nodecomparedocumentposition36Assert2");
        elemFollowsPosition = elemFollows.compareDocumentPosition(attr);
        assertEquals(2, elemFollowsPosition, "Nodecomparedocumentposition36Assert3");
    }
}
