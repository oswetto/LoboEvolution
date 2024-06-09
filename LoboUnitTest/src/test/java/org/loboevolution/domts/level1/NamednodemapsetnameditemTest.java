
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

package org.loboevolution.domts.level1;

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * The "setNamedItem(arg)" method adds a node using its
 * nodeName attribute.
 * <p>
 * Retrieve the second employee and create a NamedNodeMap
 * object from the attributes of the last child by
 * invoking the "getAttributes()" method.  Once the
 * list is created an invocation of the "setNamedItem(arg)"
 * method is done with arg=newAttr, where newAttr is a
 * new Attr Node previously created.  The "setNamedItem(arg)"
 * method should add then new node to the NamedNodeItem
 * object by using its "nodeName" attribute("district').
 * This node is then retrieved using the "getNamedItem(name)"
 * method.  This test uses the "createAttribute(name)"
 * method from the document interface.

 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1025163788">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1025163788</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-349467F9">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-349467F9</a>
 */
public class NamednodemapsetnameditemTest extends LoboUnitTest {


    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection elementList;
        final Attr newAttribute;
        final Element testAddress;
        final NamedNodeMap attributes;
        final Attr districtNode;
        final String attrName;
        final Node setNode;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("address");
        testAddress = (Element) elementList.item(1);
        newAttribute = doc.createAttribute("district");
        attributes = testAddress.getAttributes();
        attributes.setNamedItem(newAttribute);
        districtNode = (Attr) attributes.getNamedItem("district");
        attrName = districtNode.getNodeName();
        assertEquals("district", attrName, "NamednodemapsetnameditemAssert1");
    }
}

