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

package org.loboevolution.domts.level3;


import org.htmlunit.cssparser.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertTrue;


/**
 * Invoke the renameNode method on this document node to rename a node such that its
 * qualifiedName has a prefix that is "xml:html" and namespaceURI is
 * "http://www.example.com/namespace".
 * Check if a NAMESPACE_ERR gets thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode</a>
 */
public class documentrenamenode20Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Node renamedNode;
        Element docElem;
        String rootNS;
        String rootTagname;
        doc = sampleXmlFile("barfoo.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootTagname = docElem.getTagName();
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootTagname = docElem.getTagName();
        element = doc.createElementNS(rootNS, rootTagname);

        {
            boolean success = false;
            try {
                renamedNode = doc.renameNode(element, "http://www.example.com/xml", "xml:html");
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NAMESPACE_ERR);
            }
            assertTrue("throw_NAMESPACE_ERR", success);
        }
    }
}

