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
package org.loboevolution.html.style;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderstate.RenderState;

import java.awt.*;

/**
 * <p>HtmlInsets class.</p>
 */
@Data
@Slf4j
public class HtmlInsets {
	
	/** Constant TYPE_UNDEFINED=0 */
	public static final int TYPE_UNDEFINED = 0;
	
	/** Constant TYPE_PIXELS=1 */
	public static final int TYPE_PIXELS = 1;
	
	/** Constant TYPE_AUTO=2 */
	public static final int TYPE_AUTO = 2;

	/** Constant TYPE_PERCENT=3 */
	public static final int TYPE_PERCENT = 3;
	
	private int top;

	private int bottom;

	private int left;

	private int right;

	private int topType;

	private int bottomType;

	private int leftType;

	private int rightType;

	/**
	 * <p>Constructor for HtmlInsets.</p>
	 */
	public HtmlInsets() {
	}

	/**
	 * <p>Constructor for HtmlInsets.</p>
	 */
	public HtmlInsets(final int value, final int valueType) {
		top = left = right = bottom = value;
		topType = leftType = rightType = bottomType = valueType;
	}

	/**
	 * <p>getAWTInsets.</p>
	 *
	 * @param availWidth a {@link java.lang.Integer} object.
	 * @param availHeight a {@link java.lang.Integer} object.
	 * @param autoX a {@link java.lang.Integer} object.
	 * @param autoY a {@link java.lang.Integer} object.
	 * @return a {@link java.awt.Insets} object.
	 */
	public Insets getAWTInsets(final int availWidth, final int availHeight, final int autoX, final int autoY) {
		final int top = getInsetPixels(this.top, this.topType, availHeight, autoY);
		final int bottom = getInsetPixels(this.bottom, this.bottomType, availHeight, autoX);
		final int left = getInsetPixels(this.left, this.leftType, availWidth, autoY);
		final int right = getInsetPixels(this.right, this.rightType, availWidth, autoX);
		return new Insets(top, left, bottom, right);
	}

	/**
	 * <p>htmlInsetsIsVoid.</p>
	 * @return a {@link java.lang.Boolean} object.
	 */
	public boolean htmlInsetsIsVoid() {
		return top == 0 && bottom == 0 && left == 0 && right == 0;
	}

	protected static HtmlInsets getInsets(final String topText, final String leftText, final String bottomText, final String rightText,final HTMLElementImpl element, final RenderState renderState) {
		BasicInset basicInset;
		final HtmlInsets insets = new HtmlInsets();

		basicInset = updateInset(element, topText, renderState);
		insets.top = basicInset.getValue();
		insets.topType = basicInset.getType();

		basicInset = updateInset(element, leftText, renderState);
		insets.left = basicInset.getValue();
		insets.leftType = basicInset.getType();

		basicInset = updateInset(element, bottomText, renderState);
		insets.bottom = basicInset.getValue();
		insets.bottomType = basicInset.getType();

		basicInset = updateInset(element, rightText, renderState);
		insets.right = basicInset.getValue();
		insets.rightType = basicInset.getType();

		return insets;
	}

	private static BasicInset updateInset(final HTMLElementImpl element, final String sizeText, final RenderState renderState) {
		int type = 0;
		int value = 0;
		if (Strings.isNotBlank(sizeText)) {
			if ("auto".equalsIgnoreCase(sizeText)) {
				type = HtmlInsets.TYPE_AUTO;
			} else if (sizeText.endsWith("%")) {
				type = HtmlInsets.TYPE_PERCENT;
				try {
					value = Integer.parseInt(sizeText.substring(0, sizeText.length() - 1));
				} catch (final Exception e) {
					log.info(e.getMessage());
				}
			} else {
				final HTMLDocumentImpl doc = (HTMLDocumentImpl)element.getDocumentNode();
				type = HtmlInsets.TYPE_PIXELS;
				value = HtmlValues.getPixelSize(sizeText, renderState, doc.getDefaultView(), 0);
			}
		}
		return new BasicInset(type, value);
	}

	private int getInsetPixels(final int value, final int type, final int availSize, final int autoValue) {
        return switch (type) {
            case TYPE_PIXELS -> value;
            case TYPE_UNDEFINED -> 0;
            case TYPE_AUTO -> autoValue;
            case TYPE_PERCENT -> availSize * value / 100;
            default -> throw new IllegalStateException();
        };
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[" + this.top + "," + this.left + "," + this.bottom + "," + this.right + "]";
	}
}
