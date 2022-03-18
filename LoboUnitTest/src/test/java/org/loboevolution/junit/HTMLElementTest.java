/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2022 Lobo Evolution
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

package org.loboevolution.junit;

import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;
import com.gargoylesoftware.css.dom.DOMException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.domimpl.ElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLCollectionImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.html.style.AbstractCSSProperties;

import static org.junit.Assert.*;

public class HTMLElementTest extends LoboUnitTest {

	private static Document document;

	@BeforeClass
	public static void setUpBeforeClass() {
		document = sampleHtmlFile();
	}

	@Test
	public void testCreateElementError() {
		try {
			document.createElement("p'");
			fail("Must throw exception");
		} catch (DOMException e) {
			assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
		}
	}

	@Test
	public void testCreateElementNSError() {
		try {
			document.createElementNS("http://www.example.com/examplens", "e:p'");
			fail("Must throw exception");
		} catch (DOMException e) {
			assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
		}
	}

	@Test
	public void testCreateElementNSError2() {
		try {
			document.createElementNS(null, "foo:bar");
			fail("Must throw an exception");
		} catch (DOMException e) {
		}
	}

	@Test
	public void testCreateAttributeStyle() {
		Attr attr = document.createAttribute("style");
		assertNotNull(attr);
		attr.setValue("display:block");
		assertEquals(0, attr.getChildNodes().getLength());
		assertNull(attr.getFirstChild());
		assertNull(attr.getLastChild());
		assertNull(attr.getParentNode());
		assertEquals("style", attr.getName());
		assertEquals("style", attr.getNodeName());
		assertEquals("display:block", attr.getValue());
		assertEquals("display:block", attr.getNodeValue());
		HTMLElementImpl html = (HTMLElementImpl)document.getDocumentElement();
		html.setAttributeNode(attr);
		CSSStyleDeclarationImpl style = html.getStyle().getStyleDeclarations().get(0);
		assertNotNull(style);
		assertEquals("display: block", style.getCssText());
		assertEquals("display:block", html.getAttribute("style"));
		style.setCssText("margin-top: 10%");
		assertEquals("margin-top: 10%", html.getAttribute("style"));
	}

	@Test
	public void testCreateAttributeNS() {
		Attr attr = document.createAttributeNS(Document.XMLNS_NAMESPACE_URI, "xmlns");
		assertNotNull(attr);
		attr.setValue(HTMLDocument.HTML_NAMESPACE_URI);
		assertEquals(0, attr.getChildNodes().getLength());
		assertNull(attr.getFirstChild());
		assertNull(attr.getLastChild());
		assertNull(attr.getParentNode());
		assertEquals("xmlns", attr.getName());
		assertEquals("http://www.w3.org/2000/xmlns/", attr.getNamespaceURI());
		assertEquals(HTMLDocument.HTML_NAMESPACE_URI, attr.getValue());
		assertEquals("xmlns", attr.getNodeName());
		assertEquals(HTMLDocument.HTML_NAMESPACE_URI, attr.getNodeValue());
	}

	@Test
	public void testCreateAttributeNS2() {
		Attr attr = document.createAttributeNS("http://www.w3.org/2000/svg", "version");
		assertNotNull(attr);
		attr.setValue("1.1");
		assertEquals(0, attr.getChildNodes().getLength());
		assertNull(attr.getFirstChild());
		assertNull(attr.getLastChild());
		assertNull(attr.getParentNode());
		assertEquals("http://www.w3.org/2000/svg", attr.getNamespaceURI());
		assertEquals("version", attr.getName());
		assertEquals("version", attr.getNodeName());
		assertEquals("1.1", attr.getValue());
		assertEquals("1.1", attr.getNodeValue());
		assertEquals("[object Attr]", attr.toString());
	}

	@Test
	public void testCreateAttributeNSError() {
		try {
			document.createAttributeNS("http://www.example.com/examplens", "xmlns");
			fail("Must throw exception");
		} catch (DOMException e) {
			assertEquals(DOMException.NAMESPACE_ERR, e.getCode());
		}
		Attr attr = document.createAttributeNS("http://www.example.com/examplens", "doc");
		try {
			attr.setPrefix("xmlns");
			fail("Must throw exception");
		} catch (DOMException e) {
			assertEquals(DOMException.NAMESPACE_ERR, e.getCode());
		}
		try {
			attr.setPrefix("xml");
			fail("Must throw exception");
		} catch (DOMException e) {
			assertEquals(DOMException.NAMESPACE_ERR, e.getCode());
		}
		try {
			attr.setPrefix("foo:");
			fail("Must throw exception");
		} catch (DOMException e) {
			assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
		}
		try {
			document.createAttributeNS(null, "foo:bar");
			fail("Must throw exception");
		} catch (DOMException e) {
			assertEquals(DOMException.NAMESPACE_ERR, e.getCode());
		}
		attr = document.createAttributeNS(Document.XML_NAMESPACE_URI, "doc");
		attr.setPrefix("xml");
	}

	@Test
	public void setAttribute() {
		HTMLElementImpl html = (HTMLElementImpl)document.getDocumentElement();
		Element body = document.createElement("body");
		html.appendChild(body);
		assertFalse(body.hasAttributes());
		body.setAttribute("foo", "bar");
		assertTrue(body.hasAttributes());
		assertEquals("bar", body.getAttribute("foo"));
		Attr attr = body.getAttributeNode("foo");
		assertFalse(attr.isId());
		assertNull(attr.getSchemaTypeInfo().getTypeName());
		assertEquals("https://www.w3.org/TR/xml/", attr.getSchemaTypeInfo().getTypeNamespace());
		body.setAttribute("id", "bodyId");
		assertTrue(body.hasAttributes());
		assertEquals(2, body.getAttributes().getLength());
		assertEquals("bodyId", body.getAttribute("id"));
		attr = body.getAttributeNode("id");
		assertTrue(attr.isId());
		assertEquals("ID", attr.getSchemaTypeInfo().getTypeName());
		assertEquals("https://www.w3.org/TR/xml/", attr.getSchemaTypeInfo().getTypeNamespace());
		body.setAttribute("id", "newId");
		assertEquals("newId", body.getAttribute("id"));
		assertEquals(2, body.getAttributes().getLength());
	}

	@Test
	public void setAttributeCI() {
		HTMLElementImpl html = (HTMLElementImpl)document.getDocumentElement();
		Element body = document.createElement("BODY");
		html.appendChild(body);
		assertFalse(body.hasAttributes());
		body.setAttribute("FOO", "bar");
		assertTrue(body.hasAttributes());
		assertTrue(body.hasAttribute("foo"));
		assertEquals("bar", body.getAttribute("foo"));
		Attr attr = body.getAttributeNode("FOO");
		assertFalse(attr.isId());
		assertNull(attr.getSchemaTypeInfo().getTypeName());
		assertEquals("https://www.w3.org/TR/xml/", attr.getSchemaTypeInfo().getTypeNamespace());
		body.setAttribute("ID", "bodyId");
		assertTrue(body.hasAttributes());
		assertEquals(2, body.getAttributes().getLength());
		assertEquals("bodyId", body.getAttribute("ID"));
		attr = body.getAttributeNode("ID");
		assertTrue(attr.isId());
		assertEquals("ID", attr.getSchemaTypeInfo().getTypeName());
		assertEquals("https://www.w3.org/TR/xml/", attr.getSchemaTypeInfo().getTypeNamespace());
		body.setAttribute("id", "newId");
		assertEquals("newId", body.getAttribute("id"));
		assertEquals(2, body.getAttributes().getLength());
	}

	@Test
	public void testSetAttributeError() {
		Element p = document.createElement("p");
		try {
			p.setAttribute("foo=", "bar");
			fail("Must throw an exception");
		} catch (DOMException e) {
			assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
		}
	}

	@Test
	public void setAttributeNode() {
		HTMLElementImpl html = (HTMLElementImpl)document.getDocumentElement();
		Element body = document.createElement("body");
		html.appendChild(body);
		Attr attr = document.createAttribute("id");
		attr.setValue("bodyId");
		body.setAttributeNode(attr);
		assertTrue(body.hasAttributes());
		assertTrue(attr.isId());
		assertNull(attr.getParentNode());
		assertEquals("bodyId", body.getAttribute("id"));
		assertEquals(1, body.getAttributes().getLength());
		// Set the attribute to itself
		assertNotNull(body.setAttributeNode(attr));
		assertEquals(1, body.getAttributes().getLength());
		// Remove
		Attr rmattr = body.removeAttributeNode(attr);
		assertSame(rmattr, attr);
		assertFalse(body.hasAttributes());
		assertEquals(0, body.getAttributes().getLength());
		assertEquals("bodyId", attr.getValue());
		// Class attribute
		body.setAttribute("class", "fooclass");
		assertTrue(body.hasAttributes());
		assertEquals("fooclass", body.getAttribute("class"));
		assertFalse(body.getAttributeNode("class").isId());
		// Replace class attribute, first with another namespace
		attr = document.createAttributeNS("http://www.example.com/examplens", "e:class");
		attr.setValue("barclass");
		assertEquals("e:class", attr.getLocalName());
		body.setAttributeNodeNS(attr);
		assertEquals("fooclass", body.getAttribute("class"));
		attr = document.createAttribute("class");
		attr.setValue("barclass");
		body.setAttributeNode(attr);
		assertEquals("barclass", body.getAttribute("class"));
	}

	@Test
	public void setAttributeNodeCI() {
		HTMLElementImpl html = (HTMLElementImpl)document.getDocumentElement();
		Element body = document.createElement("body");
		html.appendChild(body);
		Attr attr = document.createAttribute("ID");
		attr.setValue("bodyId");
		body.setAttributeNode(attr);
		assertTrue(body.hasAttributes());
		assertTrue(attr.isId());
		assertNull(attr.getParentNode());
		assertEquals("ID", attr.getLocalName());
		assertEquals("bodyId", body.getAttribute("ID"));
		assertEquals(1, body.getAttributes().getLength());
		// Set the attribute to itself
		assertNotNull(body.setAttributeNode(attr));
		assertEquals(1, body.getAttributes().getLength());
		// Remove
		Attr rmattr = body.removeAttributeNode(attr);
		assertSame(rmattr, attr);
		assertFalse(body.hasAttributes());
		assertEquals(0, body.getAttributes().getLength());
		assertEquals("bodyId", attr.getValue());
		// Class attribute
		body.setAttribute("CLASS", "fooclass");
		assertTrue(body.hasAttributes());
		assertEquals("fooclass", body.getAttribute("CLASS"));
		assertFalse(body.getAttributeNode("class").isId());
		// Set CLASS attribute with another namespace
		attr = document.createAttributeNS("http://www.example.com/examplens", "e:CLASS");
		attr.setValue("barclass");
		assertEquals("e:CLASS", attr.getLocalName());
		body.setAttributeNodeNS(attr);
		assertEquals("fooclass", body.getAttribute("CLASS"));
		attr = document.createAttribute("CLASS");
		attr.setValue("barclass");
		assertEquals("CLASS", attr.getName());
		body.setAttributeNode(attr);
		assertEquals("barclass", body.getAttribute("CLASS"));
	}

	@Test
	public void getAttributes() {
		HTMLElementImpl html = (HTMLElementImpl)document.getDocumentElement();
		Element body = document.createElement("body");
		html.appendChild(body);
		body.setAttribute("ID", "bodyId");
		NamedNodeMap nnm = body.getAttributes();
		assertNotNull(nnm);
		assertEquals(1, nnm.getLength());
		Attr attr = nnm.item(0);
		assertTrue(attr.isId());
		assertNull(attr.getParentNode());
		assertEquals("ID", attr.getLocalName());
		assertSame(attr, nnm.getNamedItem("ID"));
		assertSame(attr, nnm.getNamedItemNS(null, "id"));
		assertNull(nnm.getNamedItemNS(HTMLDocument.HTML_NAMESPACE_URI, "id"));
		// Set the attribute to itself
		assertNotNull(nnm.setNamedItem(attr));
		assertEquals(1, nnm.getLength());
		// Remove
		Attr rmattr = nnm.removeNamedItem(attr.getName());
		assertSame(rmattr, attr);
		assertFalse(body.hasAttributes());
		assertEquals(0, nnm.getLength());
		assertEquals("bodyId", attr.getValue());
		// Class attribute
		Attr classAttr = document.createAttribute("CLASS");
		classAttr.setValue("fooclass");
		nnm.setNamedItem(classAttr);
		assertTrue(body.hasAttributes());
		assertEquals("fooclass", body.getAttribute("CLASS"));
		assertFalse(body.getAttributeNode("class").isId());
		assertSame(classAttr, nnm.getNamedItem("class"));
		// Set CLASS attribute with another namespace
		attr = document.createAttributeNS("http://www.example.com/examplens", "e:CLASS");
		attr.setValue("barclass");
		nnm.setNamedItem(attr);
		assertSame(attr, nnm.getNamedItem("e:CLASS"));
		assertSame(attr, nnm.getNamedItemNS("http://www.example.com/examplens", "CLASS"));
		//
		assertEquals("fooclass", body.getAttribute("CLASS"));
		attr = document.createAttribute("CLASS");
		attr.setValue("barclass");
		assertEquals("CLASS", attr.getName());
		nnm.setNamedItem(attr);
		assertEquals("barclass", body.getAttribute("CLASS"));
		Attr attr2 = nnm.getNamedItem("CLASS");
		assertSame(attr, attr2);
		assertEquals(2, nnm.getLength());
		//
		rmattr = nnm.removeNamedItem(attr.getName());
		assertSame(rmattr, attr);
		assertEquals(1, nnm.getLength());
	}

	@Test
	public void setAttributeNodeClass() {
		HTMLCollectionImpl fooelms = (HTMLCollectionImpl) document.getElementsByClassName("foo");
		assertEquals(0, fooelms.getLength());
		Element body = document.createElement("body");
		document.getDocumentElement().appendChild(body);
		Attr attr = document.createAttribute("class");
		attr.setValue("foo bar");
		assertEquals("class", attr.getName());
		assertEquals("foo bar", attr.getValue());
		body.setAttributeNode(attr);
		assertTrue(body.hasAttribute("class"));
		assertNull(attr.getParentNode());
		assertEquals("foo bar", attr.getValue());
		assertEquals(1, fooelms.getLength());
		assertEquals(fooelms.toString(), document.getElementsByClassName("foo").toString());
		assertSame(body, fooelms.item(0));
		HTMLCollectionImpl barelms = (HTMLCollectionImpl) document.getElementsByClassName("bar");
		assertEquals(1, barelms.getLength());
		HTMLCollectionImpl foobarelms = (HTMLCollectionImpl) document.getElementsByClassName("bar foo");
		assertEquals(1, foobarelms.getLength());
		assertSame(body, foobarelms.item(0));
		body.getClassList().remove("bar");
		assertEquals(0, barelms.getLength());
		assertEquals(0, foobarelms.getLength());
		body.getClassList().toggle("bar");
		assertEquals(1, barelms.getLength());
		body.removeAttribute("class");
		assertEquals("foo bar", attr.getValue());
		assertEquals(0, fooelms.getLength());
		assertEquals(0, barelms.getLength());
	}

	@Test
	public void testSetAttributeNodeClassCI() {
		HTMLCollectionImpl fooelms = (HTMLCollectionImpl) document.getElementsByClassName("foo");
		assertEquals(0, fooelms.getLength());
		Element body = document.createElement("body");
		document.getDocumentElement().appendChild(body);
		Attr attr = document.createAttribute("CLASS");
		attr.setValue("foo bar");
		assertEquals("CLASS", attr.getName());
		assertEquals("foo bar", attr.getValue());
		body.setAttributeNode(attr);
		assertTrue(body.hasAttribute("CLASS"));
		assertNull(attr.getParentNode());
		assertEquals("foo bar", attr.getValue());
		assertEquals(1, fooelms.getLength());
		assertEquals(fooelms.toString(), document.getElementsByClassName("foo").toString());
		assertSame(body, fooelms.item(0));
		HTMLCollectionImpl barelms = (HTMLCollectionImpl)document.getElementsByClassName("bar");
		assertEquals(1, barelms.getLength());
		HTMLCollectionImpl foobarelms = (HTMLCollectionImpl) document.getElementsByClassName("bar foo");
		assertEquals(1, foobarelms.getLength());
		assertSame(body, foobarelms.item(0));
		body.getClassList().remove("bar");
		assertEquals(0, barelms.getLength());
		assertEquals(0, foobarelms.getLength());
		body.getClassList().toggle("bar");
		assertEquals(1, barelms.getLength());
		body.removeAttribute("CLASS");
		assertEquals("foo bar", attr.getValue());
		assertEquals(0, fooelms.getLength());
		assertEquals(0, barelms.getLength());
	}

	@Test
	public void getClassList() {
		Element body = document.createElement("body");
		DOMTokenList list = body.getClassList();
		assertNotNull(list);
		assertEquals(0, list.getLength());
		Attr attr = document.createAttribute("class");
		attr.setValue("foo");
		body.setAttributeNode(attr);
		assertEquals(1, list.getLength());
		assertEquals("foo", list.item(0));
		assertEquals("foo", list.getValue());
		attr.setValue("foo bar");
		assertEquals(2, list.getLength());
		assertEquals("foo", list.item(0));
		assertEquals("foo bar", list.getValue());
		list.add("000");
		assertEquals(3, list.getLength());
		assertEquals("foo", list.item(0));
		assertEquals("000", list.item(2));
		assertEquals("foo bar 000", list.getValue());
		assertEquals("foo bar 000", attr.getValue());
		body.removeAttribute("class");
		assertEquals(0, list.getLength());
		assertEquals("foo bar 000", attr.getValue());
	}

	@Test
	public void testGetClassList2() {
		Element body = document.createElement("body");
		DOMTokenList list = body.getClassList();
		assertNotNull(list);
		assertEquals(0, list.getLength());
		list.add("foo");
		assertTrue(body.hasAttribute("class"));
		assertEquals("foo", body.getAttribute("class"));
	}

	@Test
	public void matchesStringString() {
		Element body = document.createElement("body");
		document.getDocumentElement().appendChild(body);
		body.setAttribute("id", "bodyId");
		Element div1 = document.createElement("div");
		Element div2 = document.createElement("div");
		body.appendChild(div1);
		body.appendChild(div2);
		assertFalse(body.matches(".foo"));
		DOMTokenList list = body.getClassList();
		assertNotNull(list);
		assertEquals(0, list.getLength());
		Attr attr = document.createAttribute("class");
		attr.setValue("foo");
		body.setAttributeNode(attr);
		assertTrue(body.matches(".foo"));
		assertTrue(body.matches("#bodyId"));
		attr.setValue("foo bar");
		assertTrue(body.matches(".bar"));
		assertTrue(div1.matches(".bar div"));
		assertTrue(div1.matches("body > div"));
		assertTrue(div1.matches("div:first-child"));
		assertFalse(div2.matches("div:first-child"));
		assertTrue(div2.matches("div:last-child"));
		body.removeAttribute("class");
		assertFalse(body.matches(".bar"));
	}

	@Test
	public void matchesStringString2() {
		Element body = document.createElement("body");
		document.getDocumentElement().appendChild(body);
		body.setAttribute("id", "bodyId");
		Element div1 = document.createElement("div");
		Element div2 = document.createElement("div");
		body.appendChild(div1);
		body.appendChild(div2);
		assertTrue(div2.matches("div:last-child"));
		assertFalse(div1.matches("div:last-child"));
		assertFalse(body.matches("div:last-child"));
		NodeList elements = document.querySelectorAll("div:last-child");
		assertNotNull(elements);
		assertEquals(1, elements.getLength());
		assertSame(div2, elements.item(0));
		elements = document.querySelectorAll("div:first-child");
		assertNotNull(elements);
		assertEquals(1, elements.getLength());
		assertSame(div1, elements.item(0));
		elements = document.querySelectorAll("#nosuchID");
		assertNotNull(elements);
		assertEquals(0, elements.getLength());
	}

	@Test
	public void createElement() {
		HTMLElementImpl html = (HTMLElementImpl)document.getDocumentElement();
		HTMLElementImpl body = (HTMLElementImpl)document.createElement("body");
		html.appendChild(body);
		Element svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
		body.appendChild(svg);
		assertEquals("http://www.w3.org/2000/svg", svg.getNamespaceURI());
		Element p = document.createElementNS(null, "p");
		body.appendChild(p);
		assertEquals(HTMLDocument.HTML_NAMESPACE_URI, p.getNamespaceURI());
	}

	@Test
	public void testGetStyle() {
		HTMLElementImpl body = (HTMLElementImpl)document.createElement("body");
		assertFalse(body.hasAttributes());
		document.getDocumentElement().appendChild(body);
		body.setAttribute("style", "font-family: Arial");
		assertTrue(body.hasAttributes());
		assertTrue(body.hasAttribute("style"));
		assertEquals("font-family: Arial", body.getAttribute("style"));
		AbstractCSSProperties style = body.getStyle();
		assertNotNull(style);
		assertEquals(1, style.getStyleDeclarations().size());
		assertEquals("font-family: Arial", style.getCssText());
		style.setCssText("font-family: Helvetica");
		assertEquals("font-family: Helvetica", style.getCssText());
		assertEquals("font-family: Helvetica", body.getAttribute("style"));
		body.removeAttribute("style");
		// Upper case
		body.setAttribute("STYLE", "font-family: Arial");
		assertTrue(body.hasAttributes());
		assertTrue(body.hasAttribute("STYLE"));
		assertEquals("font-family: Arial", body.getAttribute("STYLE"));
		style = body.getStyle();
		assertNotNull(style);
		assertEquals(1, style.getStyleDeclarations().size());
		assertEquals("font-family: Arial", style.getCssText());
	}

	@Test
	public void getChildren() {
		Element html = document.getDocumentElement();
		Element body = document.createElement("body");
		html.appendChild(body);
		body.appendChild(document.createTextNode("\n   \n"));
		Element div1 = document.createElement("div");
		body.appendChild(div1);
		body.appendChild(document.createTextNode("\n   \n"));
		Element div2 = document.createElement("div");
		body.appendChild(div2);
		body.appendChild(document.createTextNode("\n   \n"));
		Element div3 = document.createElement("div");
		body.appendChild(div3);
		body.appendChild(document.createTextNode("\n   \n"));
		body.appendChild(document.createComment("This is a comment"));
		Element div4 = document.createElement("div");
		body.appendChild(div4);
		body.appendChild(document.createTextNode("\n   \n"));
		body.appendChild(document.createTextNode("\n   \n"));
		HTMLCollection list = body.getChildren();
		assertNotNull(list);
		assertEquals(4, list.getLength());
		assertSame(div1, list.item(0));
		assertSame(div2, list.item(1));
		assertSame(div3, list.item(2));
		assertSame(div4, list.item(3));
		assertNull(list.item(4));
		assertSame(div1, body.getFirstElementChild());
		assertSame(div4, body.getLastElementChild());
		assertEquals(list.getLength(), body.getChildElementCount());
		assertSame(list, body.getChildren());
		list = document.getChildren();
		assertNotNull(list);
		assertEquals(1, list.getLength());
		assertSame(html, list.item(0));
		assertEquals(1, document.getChildElementCount());
		list = html.getChildren();
		assertNotNull(list);
		assertEquals(1, list.getLength());
		assertSame(body, list.item(0));
		assertNull(list.item(1));
		assertNull(list.item(-1));
		assertSame(1, html.getChildElementCount());
		assertSame(body, html.getFirstElementChild());
		assertSame(body, html.getLastElementChild());
		assertSame(list, html.getChildren());
	}

	@Test
	public void getAttributeNS() {
		HTMLElementImpl html = (HTMLElementImpl)document.getDocumentElement();
		HTMLElementImpl body = (HTMLElementImpl)document.createElement("body");
		Attr idattr = document.createAttributeNS(HTMLDocument.HTML_NAMESPACE_URI, "id");
		idattr.setValue("bodyId");
		body.setAttributeNode(idattr);
		assertNull(idattr.getParentNode());
		assertEquals("bodyId", body.getAttribute("id"));
		assertEquals("bodyId", body.getAttributeNode("id").getValue());
		assertEquals("bodyId", body.getAttributeNodeNS(HTMLDocument.HTML_NAMESPACE_URI, "id").getValue());
		assertTrue(body.hasAttributeNS(HTMLDocument.HTML_NAMESPACE_URI, "id"));
		assertFalse(body.hasAttributeNS(HTMLDocument.HTML_NAMESPACE_URI, "foo"));
		assertEquals(null, body.getAttribute("foo"));
		assertNull(body.getAttributeNode("foo"));
		assertEquals(null, body.getAttributeNS("http://www.w3.org/2000/svg", "id"));
		assertNull(body.getAttributeNodeNS("http://www.w3.org/2000/svg", "id"));
		html.appendChild(body);
		body.removeAttribute("foo");
		assertTrue(body.hasAttributes());
		body.removeAttributeNS("http://www.w3.org/2000/svg", "id");
		assertTrue(body.hasAttribute("id"));
		idattr = body.removeAttributeNode(idattr);
		assertFalse(body.hasAttributes());
		body.setAttributeNode(idattr);
		assertTrue(body.hasAttribute("id"));
		body.removeAttributeNS(HTMLDocument.HTML_NAMESPACE_URI, "id");
		assertFalse(body.hasAttribute("id"));
		assertFalse(body.hasAttributes());
		HTMLElementImpl svg = (HTMLElementImpl)document.createElementNS("http://www.w3.org/2000/svg", "svg");
		Attr version = document.createAttributeNS("http://www.w3.org/2000/svg", "version");
		version.setValue("1.1");
		assertEquals("http://www.w3.org/2000/svg", version.getNamespaceURI());
		svg.setAttributeNodeNS(version);
		assertEquals("1.1", svg.getAttribute("version"));
		assertEquals("1.1", svg.getAttributeNode("version").getValue());
		assertEquals("1.1", svg.getAttributeNodeNS("http://www.w3.org/2000/svg", "version").getValue());
		assertEquals(null, svg.getAttributeNS(HTMLDocument.HTML_NAMESPACE_URI, "version"));
		assertNull(svg.getAttributeNodeNS(HTMLDocument.HTML_NAMESPACE_URI, "version"));
		assertFalse(svg.hasAttributeNS(HTMLDocument.HTML_NAMESPACE_URI, "version"));
		assertTrue(svg.hasAttributeNS("http://www.w3.org/2000/svg", "version"));
		svg.removeAttributeNS(HTMLDocument.HTML_NAMESPACE_URI, "version");
		assertTrue(svg.hasAttributeNS("http://www.w3.org/2000/svg", "version"));
		svg.removeAttributeNS("http://www.w3.org/2000/svg", "version");
		assertFalse(svg.hasAttributeNS("http://www.w3.org/2000/svg", "version"));
		body.appendChild(svg);
	}

	@Test
	public void testGetInnerText() {
		Element html = document.getDocumentElement();
		ElementImpl body = (ElementImpl)document.createElement("body");
		html.appendChild(body);
		body.appendChild(document.createTextNode("    "));
		Element div = document.createElement("div");
		body.appendChild(div);
		div.appendChild(document.createTextNode("   "));
		Element span1 = document.createElement("span");
		span1.appendChild(document.createTextNode(" span   1 "));
		div.appendChild(span1);
		div.appendChild(document.createTextNode("   "));
		Element span2 = document.createElement("span");
		span2.appendChild(document.createTextNode(" span     2 "));
		span2.setAttribute("style", "text-transform: capitalize");
		div.appendChild(span2);
		Element pre = document.createElement("pre");
		pre.appendChild(document.createTextNode("  white  space   must   be\n   preserved   "));
		div.appendChild(pre);
		Element video = document.createElement("video");
		video.appendChild(document.createTextNode("Your browser doesn't support embedded videos."));
		div.appendChild(video);
		body.appendChild(document.createTextNode("   "));
		Element span3 = document.createElement("span");
		span3.appendChild(document.createTextNode(" span 3"));
		body.appendChild(span3);
		body.appendChild(document.createTextNode("     "));
		body.appendChild(document.createComment("This is a comment"));
		Element span4 = document.createElement("span");
		span4.appendChild(document.createTextNode(" span \n 4 "));
		span4.setAttribute("style", "white-space: pre-line; text-transform: uppercase");
		body.appendChild(span4);
		body.appendChild(document.createTextNode("   "));
		assertNotNull(body.getInnerText());
	}

	@Test
	public void cloneNode() {
		HTMLElementImpl html = (HTMLElementImpl)document.getDocumentElement();
		HTMLElementImpl body = (HTMLElementImpl)document.createElement("body");
		html.appendChild(body);
		Attr attr = document.createAttribute("id");
		attr.setValue("bodyId");
		body.setAttributeNode(attr);
		body.setAttribute("class", "fooclass");
		Element div = document.createElement("div");
		body.appendChild(div);
		div.appendChild(document.createTextNode("foo"));
		Element elm = (Element) body.cloneNode(false);
		assertEquals(body.getNodeName(), elm.getNodeName());
		assertEquals(body.getAttributes(), elm.getAttributes());
		elm = (Element) body.cloneNode(false);
		assertFalse(body.isEqualNode(elm));
		elm = (Element) body.cloneNode(true);
		assertTrue(body.isEqualNode(elm));
		assertEquals("div", elm.getChildNodes().item(0).getNodeName());
		assertEquals("foo", elm.getChildNodes().item(0).getChildNodes().item(0).getNodeValue());
	}

	@Test
	public void testGetStartTag() {
		Element elm = document.createElement("p");
		Attr attr = document.createAttribute("foo");
		attr.setValue("bar\"");
		elm.setAttributeNode(attr);
	}

	@Test
	public void testGetTagName() {
		Element elm = document.createElement("p");
		assertEquals("p", elm.getTagName());
		elm = document.createElementNS("http://www.example.com/examplens", "e:p");
		assertEquals("e:p", elm.getTagName());
	}

	@Test
	public void testToString() {
		Element elm = document.createElement("p");
		Attr attr = document.createAttribute("foo");
		attr.setValue("bar\"");
		elm.setAttributeNode(attr);
		assertEquals("[object HTMLPElement]", elm.toString());
	}

	@Test
	public void testToString2() {
		Element elm = document.createElement("link");
		Attr attr = document.createAttribute("href");
		attr.setValue("http://www.example.com/");
		elm.setAttributeNode(attr);
		assertEquals("http://www.example.com/", elm.toString());
	}

}