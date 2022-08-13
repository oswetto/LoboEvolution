
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
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;

import static org.junit.Assert.assertTrue;


/**
 * Retrieve the entire DOM document and invoke its
 * "getImplementation()" method.  This should create a
 * DOMImplementation object whose "hasFeature(feature,
 * version)" method is invoked with "feature" equal to "html" or "xml".
 * The method should return a boolean "true".
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-5CED94D7">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-5CED94D7</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=245">http://www.w3.org/Bugs/Public/show_bug.cgi?id=245</a>
 */
public class hc_domimplementationfeaturexmlTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DOMImplementation domImpl;
        boolean state;
        doc = sampleXmlFile("hc_staff.xml");
        domImpl = doc.getImplementation();
        state = domImpl.hasFeature("html", "1.0");
        assertTrue("supports_html_1.0", state);

    }

}

