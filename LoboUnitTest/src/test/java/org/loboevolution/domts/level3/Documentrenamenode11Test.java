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


import org.htmlunit.cssparser.dom.DOMException;
import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.Text;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * The method renameNode renames an existing node and raises a  NAMESPACE_ERR
 * if the qualifiedName has a prefix and the namespaceURI is null but a
 * NOT_SUPPORTED_ERR should be raised since the the type of the specified node is
 * neither ELEMENT_NODE nor ATTRIBUTE_NODE.
 * <p>
 * Invoke the renameNode method on this document node to rename a text node such that its
 * qualifiedName has a prefix and namespaceURI is null.
 * Check if a NOT_SUPPORTED_ERR gets thrown instead of a NAMESPACE_ERR.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode</a>
 */
public class Documentrenamenode11Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final String textEntry = "hello";
        final Text textNode;
        final Node renamedNode;
        final DocumentType nullDocType = null;

        final String nullNSURI = null;

        doc = sampleXmlFile("hc_staff.xml");
        textNode = doc.createTextNode(textEntry);


        boolean success = false;
        try {
            renamedNode = doc.renameNode(textNode, nullNSURI, "pre:fix");
        } catch (final DOMException ex) {
            success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
        }
        assertTrue(success, "Documentrenamenode11Assert2");

    }
}
