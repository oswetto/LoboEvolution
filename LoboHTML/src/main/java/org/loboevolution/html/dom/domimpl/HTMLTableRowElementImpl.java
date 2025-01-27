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
/*
 * Created on Dec 4, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLTableCellElement;
import org.loboevolution.html.dom.HTMLTableRowElement;
import org.loboevolution.html.dom.filter.ElementFilter;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.TableRowRenderState;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>HTMLTableRowElementImpl class.</p>
 */
public class HTMLTableRowElementImpl extends HTMLElementImpl implements HTMLTableRowElement {
	
	private int index = -1;
	
	/**
	 * <p>Constructor for HTMLTableRowElementImpl.</p>
	 */
	public HTMLTableRowElementImpl() {
		super("TR");
	}
	
    /** {@inheritDoc} */
    @Override
    protected RenderState createRenderState(final RenderState prevRenderState) {
        return new TableRowRenderState(prevRenderState, this);
    }

	/**
	 * <p>Constructor for HTMLTableRowElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLTableRowElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public void deleteCell(final int idx) {
		int trcount = 0;
		int index = idx;
		if (index == -1) index = this.nodeList.size() -1;
		for (final Node node : nodeList) {
			if ("TD".equalsIgnoreCase(node.getNodeName())) {
				if (trcount == index) {
					removeChildAt(nodeList.indexOf(node));
					return;
				}
				trcount++;
			}
		}
		
		if (this.nodeList.size() < index) {
            throw new DOMException(DOMException.INDEX_SIZE_ERR, "The index is minor than the number of cells in the table ");
		}
		
		if (this.nodeList.size() > index) {
            throw new DOMException(DOMException.INDEX_SIZE_ERR, "The index is greater than the number of cells in the table ");
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	/** {@inheritDoc} */
	@Override
	public String getBgColor() {
		return getAttribute("bgcolor");
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getCells() {
		if (getParentNode() != null && ArrayUtilities.isBlank(this.nodeList)) {
			return new HTMLCollectionImpl((NodeImpl) getParentNode(), new ElementFilter("TD"));
		}
		return new HTMLCollectionImpl(this, new ElementFilter("TD"));
	}

	/** {@inheritDoc} */
	@Override
	public String getCh() {
		return getAttribute("ch");
	}

	/** {@inheritDoc} */
	@Override
	public String getChOff() {
		return getAttribute("choff");
	}

	/** {@inheritDoc} */
	@Override
	public int getRowIndex() {
		if (index >= 0) {
			return index;
		} else {
			final AtomicInteger index = new AtomicInteger(-1);
			if (getParentNode() != null) {
				final NodeListImpl childNodes = (NodeListImpl) getParentNode().getChildNodes();
				childNodes.forEach(node -> {
					if (node instanceof HTMLTableRowElement) {
						index.incrementAndGet();
					}
				});
			}
			return index.get();
		}
	}

	/** {@inheritDoc} */
	@Override
	public int getSectionRowIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public String getvAlign() {
		return getAttribute("valign");
	}
	
	/** {@inheritDoc} */
	@Override
	public HTMLTableCellElement insertCell() {
		final Document doc = this.document;
		if (doc == null) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Orphan element");
		}
		final HTMLTableCellElementImpl cellElement = (HTMLTableCellElementImpl) doc.createElement("TD");
		appendChild(cellElement);
		return cellElement;
	}

	/** {@inheritDoc} */
	@Override
	public HTMLTableCellElementImpl insertCell(final Object index) {
		return this.insertCell(index, "TD");
	}

	private HTMLTableCellElementImpl insertCell(final Object objIndex, final String tagName) {
		final Document doc = this.document;
		if (doc == null) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Orphan element");
		}
		final HTMLTableCellElementImpl cellElement = (HTMLTableCellElementImpl) doc.createElement(tagName);
		
		int index;
		if (objIndex instanceof Double) {
			index = ((Double) objIndex).intValue();
		} else {
			if (objIndex == null || "".equals(objIndex)) {
				index = 0;
			} else {
				index = Integer.parseInt(objIndex.toString());
			}
		}
		
		if (index  == 0 || index  == - 1) {
			appendChild(cellElement);
			final AtomicInteger cellIndex = new AtomicInteger(-1);
			if (index == -1) {
				final NodeListImpl childNodes = (NodeListImpl) getParentNode().getChildNodes();
				childNodes.forEach(node -> {
					if (node instanceof HTMLTableCellElementImpl) {
						cellIndex.incrementAndGet();
					}
				});
			}
			cellElement.setIndex(index == -1 ? cellIndex.get() : 0);
			return cellElement;
		}

		final AtomicInteger trcount = new AtomicInteger();
		nodeList.forEach(node -> {
			if (node instanceof HTMLTableCellElement) {
				trcount.incrementAndGet();
			}
		});
		
		if (trcount.get() < index) {
            throw new DOMException(DOMException.INDEX_SIZE_ERR, "The index is greater than the number of cells in the table ");
		} else {
			cellElement.setIndex(index);
			insertAt(cellElement, index);
		}

		return cellElement;
	}

	/**
	 * Inserts a TH element at the specified index.
	 * <p>
	 * Note: This method is non-standard.
	 *
	 * @param index The cell index to insert at.
	 * @return The element that was inserted.
	 * @throws java.lang.Exception if any.
	 */
	public HTMLElement insertHeader(final int index) throws Exception {
		return this.insertCell(index, "TH");
	}
	
	/**
	 * <p>Setter for the field <code>index</code>.</p>
	 *
	 * @param index a {@link java.lang.Integer} object.
	 */
	protected void setIndex(final int index) {
		this.index = index;
	}

	/** {@inheritDoc} */
	@Override
	public void setAlign(final String align) {
		setAttribute("align", align);
	}

	/** {@inheritDoc} */
	@Override
	public void setBgColor(final String bgColor) {
		setAttribute("bgcolor", bgColor);
	}

	/** {@inheritDoc} */
	@Override
	public void setCh(final String ch) {
		setAttribute("ch", ch);
	}

	/** {@inheritDoc} */
	@Override
	public void setChOff(final String chOff) {
		setAttribute("choff", chOff);
	}

	/** {@inheritDoc} */
	@Override
	public void setvAlign(final String vAlign) {
		setAttribute("valign", vAlign);
	}

	@Override
	public Integer getOffsetWidth() {
		return null;
	}

	public Integer getClientWidth() {
		return null;
	}

	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLTableRowElement]";
	}
}
