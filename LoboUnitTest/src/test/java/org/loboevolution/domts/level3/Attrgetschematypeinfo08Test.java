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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.TypeInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * The getSchemaTypeInfo method retrieves the type information associated with this attribute.
 * Load a valid document with an XML Schema.
 * Invoke getSchemaTypeInfo method on an attribute having [type definition] property.  Expose {name} and {target namespace}
 * properties of the [type definition] property.  Verity that the typeName and typeNamespace of the 'title' attribute's (of first 'acronym' element)
 * schemaTypeInfo are correct.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Attr-schemaTypeInfo">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Attr-schemaTypeInfo</a>
 */
public class Attrgetschematypeinfo08Test extends LoboUnitTest {


    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection elemList;
        final Element acElem;
        final Attr attr;
        final TypeInfo attrTypeInfo;
        final String typeName;
        final String typeNamespace;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("acronym");
        acElem = (Element) elemList.item(0);
        attr = acElem.getAttributeNode("title");
        attrTypeInfo = attr.getSchemaTypeInfo();
        typeName = attrTypeInfo.getTypeName();
        typeNamespace = attrTypeInfo.getTypeNamespace();
        assertEquals("string", typeName, "Attrgetschematypeinfo08Assert2");
        assertEquals("http://www.w3.org/2001/XMLSchema", typeNamespace, "Attrgetschematypeinfo08Assert3");
    }
}

