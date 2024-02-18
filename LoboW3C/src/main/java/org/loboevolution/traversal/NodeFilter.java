/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.traversal;

import org.loboevolution.html.node.Node;

/**
 * <p>NodeFilter interface.</p>
 */
public interface NodeFilter  {

	/**
	 * Show all <code>Nodes</code>.
	 */
	int SHOW_ALL = 0xFFFFFFFF;

	/**
	 * Show <code>Element</code> nodes.
	 */
	int SHOW_ELEMENT = 0x00000001;

	/**
	 * Show <code>Attr</code> nodes. This is meaningful only when creating an
	 * iterator or tree-walker with an attribute node as its
	 * <code>root</code>; in this case, it means that the attribute node
	 * will appear in the first position of the iteration or traversal.
	 * Since attributes are never children of other nodes, they do not
	 * appear when traversing over the main document tree.
	 */
	int SHOW_ATTRIBUTE = 0x00000002;

	/**
	 * Show <code>Text</code> nodes.
	 */
	int SHOW_TEXT = 0x00000004;

	/**
	 * Show <code>CDATASection</code> nodes.
	 */
	int SHOW_CDATA_SECTION = 0x00000008;

	/**
	 * Show <code>EntityReference</code> nodes. Note that if Entity References
	 * have been fully expanded while the tree was being constructed, these
	 * nodes will not appear and this mask has no effect.
	 */
	int SHOW_ENTITY_REFERENCE = 0x00000010;

	/**
	 * Show <code>Entity</code> nodes. This is meaningful only when creating
	 * an iterator or tree-walker with an<code> Entity</code> node as its
	 * <code>root</code>; in this case, it means that the <code>Entity</code>
	 * node will appear in the first position of the traversal. Since
	 * entities are not part of the document tree, they do not appear when
	 * traversing over the main document tree.
	 */
	int SHOW_ENTITY = 0x00000020;

	/**
	 * Show <code>ProcessingInstruction</code> nodes.
	 */
	int SHOW_PROCESSING_INSTRUCTION = 0x00000040;

	/**
	 * Show <code>Comment</code> nodes.
	 */
	int SHOW_COMMENT = 0x00000080;

	/**
	 * Show <code>Document</code> nodes. (Of course, as with Attributes
	 * and such, this is meaningful only when the iteration root is the
	 * Document itself, since Document has no parent.)
	 */
	int SHOW_DOCUMENT = 0x00000100;

	/**
	 * Show <code>DocumentType</code> nodes.
	 */
	int SHOW_DOCUMENT_TYPE = 0x00000200;

	/**
	 * Show <code>DocumentFragment</code> nodes. (Of course, as with
	 * Attributes and such, this is meaningful only when the iteration
	 * root is the Document itself, since DocumentFragment has no parent.)
	 */
	int SHOW_DOCUMENT_FRAGMENT = 0x00000400;

	/**
	 * Show <code>Notation</code> nodes. This is meaningful only when creating
	 * an iterator or tree-walker with a <code>Notation</code> node as its
	 * <code>root</code>; in this case, it means that the
	 * <code>Notation</code> node will appear in the first position of the
	 * traversal. Since notations are not part of the document tree, they do
	 * not appear when traversing over the main document tree.
	 */
	int SHOW_NOTATION = 0x00000800;

	/**
	 * Accept the node.
	 */
	short FILTER_ACCEPT = 1;

	/**
	 * Reject the node. Same behavior as FILTER_SKIP. (In the DOM these
	 * differ when applied to a TreeWalker but have the same result when
	 * applied to a NodeIterator).
	 */
	short FILTER_REJECT = 2;

	/**
	 * Skip this single node.
	 */
	short FILTER_SKIP = 3;

	/**
	 * <p>acceptNode.</p>
	 *
	 * @param node a {@link org.loboevolution.html.node.Node} object.
	 * @return a short.
	 */
	short acceptNode(Node node);

    
}
