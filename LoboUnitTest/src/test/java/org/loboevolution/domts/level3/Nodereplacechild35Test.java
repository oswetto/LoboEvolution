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


import org.htmlunit.cssparser.dom.DOMException;
import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.EntityReference;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Using replaceChild on a new Attr node, replace its new EntityRefernece Child with a
 * new Attr Node and verify if a HIERARCHY_REQUEST_ERR is thrown.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class Nodereplacechild35Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final Attr parent;
        final EntityReference oldChild;
        final Attr newChild;
        doc = sampleXmlFile("hc_staff.xml");
        parent = doc.createAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang");
        oldChild = doc.createEntityReference("delta");
        parent.appendChild(oldChild);
        newChild = doc.createAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang");

        {
            boolean success = false;
            try {
                parent.replaceChild(newChild, oldChild);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.HIERARCHY_REQUEST_ERR);
            }
            assertTrue(success, "Nodereplacechild35Assert2");
        }
    }
}

