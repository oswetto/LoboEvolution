
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

package org.loboevolution.domts.level1;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.NodeList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * The "createDocumentFragment()" method creates an empty
 * DocumentFragment object.
 * Retrieve the entire DOM document and invoke its
 * "createDocumentFragment()" method.  The content, name,
 * type and value of the newly created object are output.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-35CB04B5">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-35CB04B5</a>
 */
public class hc_documentcreatedocumentfragmentTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DocumentFragment newDocFragment;
        NodeList children;
        int length;
        String newDocFragmentName;
        int newDocFragmentType;
        String newDocFragmentValue;
        doc = sampleXmlFile("hc_staff.xml");
        newDocFragment = doc.createDocumentFragment();
        children = newDocFragment.getChildNodes();
        length = children.getLength();
        assertEquals("length", 0, length);
        newDocFragmentName = newDocFragment.getNodeName();
        assertEquals("strong", "[object DocumentFragment]", newDocFragmentName);
        newDocFragmentType = newDocFragment.getNodeType();
        assertEquals("type", 11, newDocFragmentType);
        newDocFragmentValue = newDocFragment.getNodeValue();
        assertNull("value", newDocFragmentValue);
    }
}
