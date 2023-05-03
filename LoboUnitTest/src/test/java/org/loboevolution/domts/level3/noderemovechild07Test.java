/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.domts.level3;


import org.htmlunit.cssparser.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.Notation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertTrue;


/**
 * Attempts to remove a notation from a Document node.  Since notations are children of
 * DocumentType, not Document the operation should fail with a NOT_FOUND_ERR.  Attempting
 * to remove Document from a Notation should also fail either with a NOT_FOUND_ERR
 * or a NO_MODIFICATION_ALLOWED_ERR.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=418">http://www.w3.org/Bugs/Public/show_bug.cgi?id=418</a>
 */
public class noderemovechild07Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        NamedNodeMap notations;
        Notation notation;
        Node removedChild;
        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();
        notations = docType.getNotations();
        notation = (Notation) notations.getNamedItem("notation1");

        {
            boolean success = false;
            try {
                removedChild = doc.removeChild(notation);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NOT_FOUND_ERR);
            }
            assertTrue("NOT_FOUND_ERR_noderemovechild07_1", success);
        }

        try {
            removedChild = notation.removeChild(doc);

        } catch (DOMException ex) {
            switch (ex.getCode()) {
                case 8:
                    break;
                case 7:
                    break;
                default:
                    throw ex;
            }
        }
    }
}

