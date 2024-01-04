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

package com.jtattoo.plaf.acryl;

import javax.swing.border.Border;

import com.jtattoo.plaf.AbstractBorderFactory;
import com.jtattoo.plaf.BaseBorders;

/**
 * <p>AcrylBorderFactory class.</p>
 *
 * Author Michael Hagen
 *
 */
public final class AcrylBorderFactory implements AbstractBorderFactory {

	private static AcrylBorderFactory instance = null;

	/**
	 * <p>Getter for the field instance.</p>
	 *
	 * @return a {@link com.jtattoo.plaf.acryl.AcrylBorderFactory} object.
	 */
	public static synchronized AcrylBorderFactory getInstance() {
		if (instance == null) {
			instance = new AcrylBorderFactory();
		}
		return instance;
	}

	private AcrylBorderFactory() {
	}

	/** {@inheritDoc} */
	@Override
	public Border getButtonBorder() {
		return AcrylBorders.getButtonBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getComboBoxBorder() {
		return AcrylBorders.getComboBoxBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getDesktopIconBorder() {
		return BaseBorders.getDesktopIconBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getFocusFrameBorder() {
		return BaseBorders.getFocusFrameBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getInternalFrameBorder() {
		return AcrylBorders.getInternalFrameBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getMenuBarBorder() {
		return BaseBorders.getMenuBarBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getMenuItemBorder() {
		return BaseBorders.getMenuItemBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getPaletteBorder() {
		return BaseBorders.getPaletteBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getPopupMenuBorder() {
		return BaseBorders.getPopupMenuBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getProgressBarBorder() {
		return BaseBorders.getProgressBarBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getScrollPaneBorder() {
		return AcrylBorders.getScrollPaneBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getSpinnerBorder() {
		return AcrylBorders.getSpinnerBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getTabbedPaneBorder() {
		return BaseBorders.getTabbedPaneBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getTableHeaderBorder() {
		return BaseBorders.getTableHeaderBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getTableScrollPaneBorder() {
		return AcrylBorders.getTableScrollPaneBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getTextBorder() {
		return AcrylBorders.getTextBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getTextFieldBorder() {
		return AcrylBorders.getTextFieldBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getToggleButtonBorder() {
		return AcrylBorders.getToggleButtonBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getToolBarBorder() {
		return BaseBorders.getToolBarBorder();
	}

} // end of class AcrylBorderFactory
