
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

package org.loboevolution.domts.level1;

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Removes the child node of an attribute and checks that the value is empty.

 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-637646024">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-637646024</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1734834066">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1734834066</a>
 */
public class Hcattrremovechild1Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection acronymList;
        final Element testNode;
        final NamedNodeMap attributes;
        final Attr titleAttr;
        String value;
        final Text textNode;
        final Node retval;
        final Node firstChild;
        doc = sampleXmlFile("hc_staff.xml");
        acronymList = doc.getElementsByTagName("acronym");
        testNode = (Element) acronymList.item(3);
        attributes = testNode.getAttributes();
        titleAttr = (Attr) attributes.getNamedItem("title");
        textNode = (Text) titleAttr.getFirstChild();
        assertNotNull(textNode, "Hcattrremovechild1Assert1");
        retval = titleAttr.removeChild(textNode);
        value = titleAttr.getValue();
        assertEquals("", value, "Hcattrremovechild1Assert2");
        value = titleAttr.getNodeValue();
        assertEquals("", value, "Hcattrremovechild1Assert3");
        value = retval.getNodeValue();
        assertEquals("Yes", value, "Hcattrremovechild1Assert4");
        firstChild = titleAttr.getFirstChild();
        assertNull(firstChild, "Hcattrremovechild1Assert5");
    }
}

