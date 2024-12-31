
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

package org.loboevolution.domts.level2;

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * The "hasAttributeNS()" method for an Element should
 * return false if the element does not have an attribute with the given local name
 * and/or namespace URI specified on this element or does not have a default value.
 * Retrieve the first "address" element.
 * The boolean value returned by the "hasAttributeNS()" should be false
 * since the attribute does not have a default value.

 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElHasAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElHasAttrNS</a>
 */
public class HasAttributeNS03Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final String localName = "blank";
        final String namespaceURI = "http://www.nist.gov";
        final Document doc;
        final HTMLCollection elementList;
        final Element testNode;
        final boolean state;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("address");
        testNode = (Element) elementList.item(0);
        assertNotNull(testNode);
        state = testNode.hasAttributeNS(namespaceURI, localName);
        assertFalse(state);
    }
}

