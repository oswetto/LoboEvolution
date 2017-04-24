/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;

public class HtmlFormTest extends SimpleLoboTest {

	@Test
    public void basicTest() throws Exception {
        final String htmlSource
            = "<html><head><title>First</title></head><body>"
            + "<form id='myId' method='get' action='javascript:alert(\"clicked\")'>"
            + "<input name='button' type='submit' value='PushMe' id='button'/></form>"
            + "</body></html>";

        HTMLDocumentImpl doc = loadPage(htmlSource);
		assertTrue(FORM.equals(doc.getElementById("myId").getNodeName()));
    }
}