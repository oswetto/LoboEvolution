
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Create and populate a new DocumentFragment object and
 * append it to the second employee.   After the
 * "appendChild(newChild)" method is invoked retrieve the
 * new nodes at the end of the list, they should be the
 * two Element nodes from the DocumentFragment.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-184E7107">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-184E7107</a>
 */
public class NodeappendchilddocfragmentTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection elementList;
        final Node employeeNode;
        final NodeList childList;
        final DocumentFragment newdocFragment;
        final Node newChild1;
        final Node newChild2;
        Node child;
        String childName;
        final List<String> result = new java.util.ArrayList<String>();

        int nodeType;
        final List<String> expected = new ArrayList<String>();
        expected.add("EMPLOYEEID");
        expected.add("NAME");
        expected.add("POSITION");
        expected.add("SALARY");
        expected.add("GENDER");
        expected.add("ADDRESS");
        expected.add("NEWCHILD1");
        expected.add("NEWCHILD2");

        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("employee");
        employeeNode = elementList.item(1);
        childList = employeeNode.getChildNodes();
        newdocFragment = doc.createDocumentFragment();
        newChild1 = doc.createElement("newChild1");
        newChild2 = doc.createElement("newChild2");
        newdocFragment.appendChild(newChild1);
        newdocFragment.appendChild(newChild2);
        employeeNode.appendChild(newdocFragment);
        for (int indexN1009F = 0; indexN1009F < childList.getLength(); indexN1009F++) {
            child = childList.item(indexN1009F);
            nodeType = child.getNodeType();

            if (nodeType == 1) {
                childName = child.getNodeName();
                result.add(childName);
            }
        }
        assertEquals(expected, result, "NodeappendchilddocfragmentAssert1");
    }
}

