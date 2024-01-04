/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
package org.loboevolution.html.renderer;

import lombok.Data;
import org.loboevolution.html.dom.nodeimpl.ModelNode;

import java.awt.*;

@Data
final class RFloatInfo implements Renderable {
	private final BoundableRenderable renderable;
	private final boolean leftFloat;
	private final ModelNode modelNode;

	/**
	 * <p>Constructor for RFloatInfo.</p>
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @param renderable a {@link org.loboevolution.html.renderer.BoundableRenderable} object.
	 * @param leftFloat a boolean.
	 */
	public RFloatInfo(final ModelNode node, final BoundableRenderable renderable, final boolean leftFloat) {
		this.modelNode = node;
		this.renderable = renderable;
		this.leftFloat = leftFloat;
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g) {
		// nop
	}
}
