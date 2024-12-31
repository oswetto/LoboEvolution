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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.EntityReference;
import org.loboevolution.html.node.ProcessingInstruction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * Using compareDocumentPosition check the document position of the EntityReference node ent4's
 * first child and last child.  Invoke compareDocumentPositon on first child with last child as a parameter
 * should return FOLLOWING, and should return PRECEDING vice versa.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class Nodecomparedocumentposition28Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection varList;
        final Element varElem;
        final EntityReference entRef;
        final Element entRefChild1;
        final ProcessingInstruction entRefChild2;
        final int entRefChild1Position;
        final int entRefChild2Position;
        doc = sampleXmlFile("hc_staff.xml");

        varList = doc.getElementsByTagName("var");
        varElem = (Element) varList.item(2);
        assertNotNull(varElem, "Nodecomparedocumentposition28Assert3");
        entRef = (EntityReference) varElem.getFirstChild();
        assertNotNull(entRef, "Nodecomparedocumentposition28Assert4");

        entRefChild1 = (Element) entRef.getFirstChild();
        assertNotNull(entRefChild1, "Nodecomparedocumentposition28Assert5");
        entRefChild2 = (ProcessingInstruction) entRef.getLastChild();
        assertNotNull(entRefChild2, "Nodecomparedocumentposition28Assert6");
        entRefChild1Position = entRefChild1.compareDocumentPosition(entRefChild2);
        assertEquals(4, entRefChild1Position, "Nodecomparedocumentposition28Assert7");
        entRefChild2Position = entRefChild2.compareDocumentPosition(entRefChild1);
        assertEquals(2, entRefChild2Position, "Nodecomparedocumentposition28Assert8");
    }
}

