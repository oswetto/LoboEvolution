
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;

import static org.junit.Assert.*;


/**
 * The "createAttribute(name)" method creates an Attribute
 * node of the given name.
 * <p>
 * Retrieve the entire DOM document and invoke its
 * "createAttribute(name)" method.  It should create a
 * new Attribute node with the given name. The name, value
 * and type of the newly created object are retrieved and
 * output.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1084891198">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1084891198</a>
 */
public class documentcreateattributeTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        final Document doc;
        final Attr newAttrNode;
        final String attrValue;
        final String attrName;
        final int attrType;
        doc = sampleXmlFile("staff.xml");
        newAttrNode = doc.createAttribute("district");
        attrValue = newAttrNode.getNodeValue();
        assertEquals("value", null, attrValue);
        attrName = newAttrNode.getNodeName();
        assertEquals("name", "district", attrName);
        attrType = newAttrNode.getNodeType();
        assertEquals("type", 2, attrType);
    }
}

