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


import lombok.SneakyThrows;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.nodeimpl.bootstrap.DOMImplementationRegistry;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.DOMImplementationList;

import static org.junit.Assert.*;


/**
 * DOMImplementationRegistry.getDOMImplementationList("cOrE") should return a
 * list of at least one DOMImplementation
 * where hasFeature("Core", null) returns true.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/java-binding">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/java-binding</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/ecma-script-binding">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/ecma-script-binding</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-getDOMImpls">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-getDOMImpls</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMImplementationList-item">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMImplementationList-item</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMImplementationList-length">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMImplementationList-length</a>
 */
public class domimplementationregistry13Test extends LoboUnitTest {
    @Test
    @SneakyThrows
    public void runTest() {
       
        boolean hasFeature;
        DOMImplementation domImpl;
        DOMImplementationList domImplList;
        int length;
        String nullVersion = null;

         DOMImplementationRegistry domImplRegistry = DOMImplementationRegistry.newInstance();
        assertNotNull("domImplRegistryNotNull", domImplRegistry);
        domImplList = domImplRegistry.getDOMImplementationList("cOrE");
        length = (int) domImplList.getLength();
        domImpl = domImplList.item(((int) /*int */length));
        assertNull("item_Length_shouldBeNull", domImpl);
        assertTrue("atLeastOne", (length > 0));
        for (int indexN10067 = 0; indexN10067 < domImplList.getLength(); indexN10067++) {
            domImpl = (DOMImplementation) domImplList.item(indexN10067);
            hasFeature = domImpl.hasFeature("Core", nullVersion);
            assertTrue("hasCore", hasFeature);
        }
    }
}
