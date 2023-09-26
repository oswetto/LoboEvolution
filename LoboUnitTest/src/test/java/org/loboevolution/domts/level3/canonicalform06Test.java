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
import org.loboevolution.gui.LocalHtmlRendererConfig;
import org.loboevolution.html.dom.DOMError;
import org.loboevolution.html.dom.DOMLocator;
import org.loboevolution.html.dom.nodeimpl.DOMErrorMonitor;
import org.loboevolution.html.dom.nodeimpl.DOMImplementationImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.http.UserAgentContext;

import java.util.List;

import static org.junit.Assert.*;


/**
 * Create a document with an XML 1.1 valid but XML 1.0 invalid element and
 * normalize document with canonical-form set to true.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-canonical-form">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-canonical-form</a>
 */
public class canonicalform06Test extends LoboUnitTest {

    @Test
    public void runTest() {
        final DOMImplementation domImpl;
        final String nullString = null;

        final DocumentType nullDoctype = null;

        final Document doc;
        Element elem;
        final DOMConfiguration domConfig;
        final DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        final List<DOMError> errors;

        DOMError error;
        int severity;
        String type;
        DOMLocator locator;
        Node relatedNode;
        final boolean canSet;
        domImpl = new DOMImplementationImpl(new UserAgentContext(new LocalHtmlRendererConfig(), true));
        doc = domImpl.createDocument(nullString, nullString, nullDoctype);

        {
            boolean success = false;
            try {
                elem = doc.createElementNS("http://www.example.org/domts/wellformed01", "LegalNameࢎ");
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.INVALID_CHARACTER_ERR);
            }
            assertTrue("xml10InvalidName", success);
        }

        try {
            doc.setXmlVersion("1.1");

        } catch (final DOMException ex) {
            if (ex.getCode() == 9) {
                return;
            }
            throw ex;
        }
        elem = doc.createElementNS("http://www.example.org/domts/wellformed01", "LegalName");
        doc.appendChild(elem);
        doc.setXmlVersion("1.0");
        domConfig = doc.getDomConfig();
        canSet = domConfig.canSetParameter("canonical-form", Boolean.TRUE);

        if (canSet) {
            domConfig.setParameter("canonical-form", Boolean.TRUE);
            /*DOMErrorMonitor */
            domConfig.setParameter("error-handler", errorMonitor);
            doc.normalizeDocument();
            errors = errorMonitor.getErrors();
            for (final Object o : errors) {
                error = (DOMError) o;
                severity = error.getSeverity();
                assertEquals("severity", 2, severity);
                type = error.getType();
                assertEquals("type", "wf-invalid-character-in-node-name", type);
                locator = error.getLocation();
                relatedNode = locator.getRelatedNode();
                assertSame("relatedNode", elem, relatedNode);
            }
            assertSame("oneError", 1, errors.size());
        }
    }
}