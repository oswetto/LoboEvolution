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

package org.loboevolution.html.renderstate;

import org.loboevolution.info.WordInfo;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.laf.FontKey;
import org.loboevolution.laf.FontType;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>FontStyleRenderState class.</p>
 */
public class FontStyleRenderState extends RenderStateDelegator {

	private Font iFont;

	private FontMetrics iFontMetrics;

	private FontType fontType;

	Map<String, WordInfo> iWordInfoMap = null;

	private int superscript;

	/**
	 * <p>Constructor for FontStyleRenderState.</p>
	 *
	 * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 */
	public FontStyleRenderState(RenderState prevRenderState, FontType fontType) {
		super(prevRenderState);
		this.iFont = null;
		this.fontType = fontType;
	}

	/**
	 * <p>Constructor for FontStyleRenderState.</p>
	 *
	 * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param superscript a {@link java.lang.Integer} object.
	 */
	public FontStyleRenderState(RenderState prevRenderState, Integer superscript) {
		super(prevRenderState);
		this.iFont = prevRenderState.getFont();
		this.superscript = superscript;
	}

	/** {@inheritDoc} */
	@Override
	public Font getFont() {
		FontKey fontkey = new FontKey();

		if (iFont != null) {
			fontkey.setSuperscript(this.superscript);
			return  FontFactory.getInstance().scriptFont(this.iFont, fontkey);
		}

		if (FontType.BOLD.equals(fontType)) {
			fontkey.setFontWeight(FontType.BOLD.getValue());
		}

		if (FontType.ITALIC.equals(fontType)) {
			fontkey.setFontStyle(FontType.ITALIC.getValue());
		}

		if (FontType.MONOSPACED.equals(fontType)) {
			fontkey.setFontFamily(FontType.MONOSPACED.getValue());
		}

		return FontFactory.getInstance().getFont(fontkey);
	}

	/** {@inheritDoc} */
	@Override
	public FontMetrics getFontMetrics() {
		FontMetrics fm = this.iFontMetrics;
		if (fm == null) {
			// TODO getFontMetrics deprecated. How to get text width?
			fm = Toolkit.getDefaultToolkit().getFontMetrics(getFont());
			this.iFontMetrics = fm;
		}
		return fm;
	}

	/** {@inheritDoc} */
	@Override
	public final WordInfo getWordInfo(String word) {
		// Expected to be called only in the GUI (rendering) thread.
		// No synchronization necessary.
		Map<String, WordInfo> map = this.iWordInfoMap;
		if (map == null) {
			map = new HashMap<>();
			this.iWordInfoMap = map;
		}
		WordInfo wi = map.get(word);
		if (wi != null) {
			return wi;
		}
		final FontMetrics fm = getFontMetrics();
		wi = WordInfo.builder()
				.fontMetrics(fm)
				.ascentPlusLeading(fm.getAscent() + fm.getLeading())
				.descent(fm.getDescent())
				.height(fm.getHeight())
				.width(fm.stringWidth(word))
				.build();
		map.put(word, wi);
		return wi;
	}

	/** {@inheritDoc} */
	@Override
	public void invalidate() {
		this.delegate.invalidate();
		this.iFont = null;
		this.iFontMetrics = null;
		final Map<String, WordInfo> map = this.iWordInfoMap;
		if (map != null) {
			map.clear();
		}
	}
}
