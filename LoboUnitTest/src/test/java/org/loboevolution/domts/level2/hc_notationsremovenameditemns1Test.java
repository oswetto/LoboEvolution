
/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.domts.level2;

import com.gargoylesoftware.css.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


/**
 * An attempt to add remove an notation using removeNamedItemNS should result in
 * a NO_MODIFICATION_ERR or a NOT_FOUND_ERR.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-D46829EF">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-D46829EF</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-removeNamedItemNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-removeNamedItemNS</a>
 */
public class hc_notationsremovenameditemns1Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        NamedNodeMap notations;
        DocumentType docType;
        Node retval;
        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();

        assertNotNull("docTypeNotNull", docType);
        notations = docType.getNotations();
        assertNotNull("notationsNotNull", notations);

        try {
            retval = notations.removeNamedItemNS("http://www.w3.org/1999/xhtml", "alpha");
            fail("throw_NO_MOD_OR_NOT_FOUND_ERR");

        } catch (DOMException ex) {
            switch (ex.getCode()) {
                case 7:
                case 8:
                    break;
                default:
                    throw ex;
            }
        }
    }
}

