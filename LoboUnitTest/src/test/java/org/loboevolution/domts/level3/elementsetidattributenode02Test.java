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


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.*;


/**
 * Invoke setIdAttributeNode on the 3rd acronym element using the class attribute as a parameter .  Verify by calling
 * isID on the attribute node and getElementById on document node.  Call setIdAttributeNode again with isId=false
 * to reset.  Invoke isId on the attribute node should return false.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNode</a>
 */
public class elementsetidattributenode02Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element acronymElem;
        NamedNodeMap attributesMap;
        Attr attr;
        boolean id = false;
        Element elem;
        String elemName;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("acronym");
        acronymElem = (Element) elemList.item(2);
        attributesMap = acronymElem.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("class");
        acronymElem.setIdAttributeNode(attr, true);
        id = attr.isId();
        assertTrue("elementsetidattributenodeIsIdTrue02", id);
        elem = doc.getElementById("No");
        elemName = elem.getTagName();
        assertEquals("elementsetidattributenodeGetElementById02", "ACRONYM", elemName);
        elem.setIdAttributeNode(attr, false);
        id = attr.isId();
        assertFalse("elementsetidattributenodeIsIdFalse02", id);
    }
}

