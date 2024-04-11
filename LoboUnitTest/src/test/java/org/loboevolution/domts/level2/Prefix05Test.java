
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

package org.loboevolution.domts.level2;

import org.htmlunit.cssparser.dom.DOMException;
import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * The "setPrefix(prefix)" method raises a
 * NAMESPACE_ERR DOMException if the specified node is an attribute
 * and the specified prefix is xmlns and the namespaceURI is different from
 * http://www.w3.org/2000/xmlns.
 * <p>
 * Attempt to insert "xmlns" as the new namespace prefix on the domestic
 * attribute within the address node.
 * An exception should be raised since the namespaceURI of this node is not
 * http://www.w3.org/2000/xmlns.

 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='NAMESPACE_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='NAMESPACE_ERR'])</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSPrefix">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSPrefix</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-NodeNSPrefix')/setraises/exception[@name='DOMException']/descr/p[substring-before(.,':')='NAMESPACE_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-NodeNSPrefix')/setraises/exception[@name='DOMException']/descr/p[substring-before(.,':')='NAMESPACE_ERR'])</a>
 */
public class Prefix05Test extends LoboUnitTest {
    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection elementList;
        final Element addrNode;
        final Attr addrAttr;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("emp:address");
        addrNode = (Element) elementList.item(0);
        assertNotNull(addrNode);
        addrAttr = addrNode.getAttributeNode("emp:domestic");
        boolean success = false;
        try {
            addrAttr.setPrefix("xmlns");
        } catch (final DOMException ex) {
            success = (ex.getCode() == DOMException.NAMESPACE_ERR);
        }
        assertTrue(success);
    }
}

