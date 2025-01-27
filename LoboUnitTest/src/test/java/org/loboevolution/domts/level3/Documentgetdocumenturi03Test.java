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
import org.loboevolution.html.dom.DOMImplementation;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertNull;


/**
 * Import the documentElement node of this document into a new document.  Since this node is
 * now owned by the importing document, its documentURI attribute value should be null

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-documentURI">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-documentURI</a>
 */
public class Documentgetdocumenturi03Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final Document newDoc;
        final Document importedOwner;
        final Element docElem;
        final Node docElemImported;
        final String docURI;
        final DOMImplementation domImpl;

        final String rootNS;
        final String rootName;
        doc = sampleXmlFile("hc_staff.xml");
        domImpl = doc.getImplementation();
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootName = docElem.getTagName();
        newDoc = domImpl.createDocument(rootNS, rootName, null);
        docElemImported = newDoc.importNode(docElem, false);
        importedOwner = docElemImported.getOwnerDocument();
        docURI = importedOwner.getDocumentURI();
        assertNull(docURI, "Documentgetdocumenturi03Assert2");
    }
}

