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
package com.jtattoo.plaf.noire;

import java.awt.Color;
import java.awt.Font;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

import com.jtattoo.plaf.AbstractTheme;
import com.jtattoo.plaf.ColorHelper;

/**
 * <p>NoireDefaultTheme class.</p>
 *
 * Author Michael Hagen
 *
 */
public class NoireDefaultTheme extends AbstractTheme {

	/**
	 * <p>Constructor for NoireDefaultTheme.</p>
	 */
	public NoireDefaultTheme() {
		super();
		// Setup theme with defaults
		setUpColor();
		// Overwrite defaults with user props
		loadProperties();
		// Setup the color arrays
		setUpColorArrs();
	}

	/** {@inheritDoc} */
	@Override
	public String getPropertyFileName() {
		return "NoireTheme.properties";
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColor() {
		super.setUpColor();

		// Defaults for NoireLookAndFeel
		textShadow = true;
		foregroundColor = WHITE;
		disabledForegroundColor = GRAY;
		disabledBackgroundColor = new ColorUIResource(48, 48, 48);

		backgroundColor = new ColorUIResource(24, 26, 28);
		backgroundColorLight = new ColorUIResource(24, 26, 28);
		backgroundColorDark = new ColorUIResource(4, 5, 6);
		alterBackgroundColor = new ColorUIResource(78, 84, 90);

		selectionForegroundColor = new ColorUIResource(255, 220, 120);
		selectionBackgroundColor = BLACK;
		frameColor = BLACK;
		gridColor = BLACK;
		focusCellColor = ORANGE;

		inputBackgroundColor = new ColorUIResource(52, 55, 59);
		inputForegroundColor = foregroundColor;

		rolloverForegroundColor = WHITE;
		rolloverColor = new ColorUIResource(240, 168, 0);
		rolloverColorLight = new ColorUIResource(240, 168, 0);
		rolloverColorDark = new ColorUIResource(196, 137, 0);

		pressedForegroundColor = foregroundColor;
		pressedBackgroundColor = new ColorUIResource(12, 12, 12);
		pressedBackgroundColorLight = new ColorUIResource(ColorHelper.brighter(pressedBackgroundColor, 2));
		pressedBackgroundColorDark = new ColorUIResource(ColorHelper.darker(pressedBackgroundColor, 40));

		buttonForegroundColor = BLACK;
		buttonBackgroundColor = new ColorUIResource(120, 129, 148);
		buttonColorLight = new ColorUIResource(232, 238, 244);
		buttonColorDark = new ColorUIResource(196, 200, 208);

		controlForegroundColor = foregroundColor;
		controlBackgroundColor = new ColorUIResource(52, 55, 59); // netbeans use this for selected tab in the toolbar
		controlColorLight = new ColorUIResource(44, 47, 50);
		controlColorDark = new ColorUIResource(16, 18, 20);
		controlHighlightColor = new ColorUIResource(96, 96, 96);
		controlShadowColor = new ColorUIResource(32, 32, 32);
		controlDarkShadowColor = BLACK;

		windowTitleForegroundColor = foregroundColor;
		windowTitleBackgroundColor = new ColorUIResource(16, 17, 15);
		windowTitleColorLight = new ColorUIResource(64, 67, 60);
		windowTitleColorDark = BLACK;
		windowBorderColor = BLACK;
		windowIconColor = LIGHT_GRAY;
		windowIconShadowColor = BLACK;
		windowIconRolloverColor = ORANGE;

		windowInactiveTitleForegroundColor = new ColorUIResource(196, 196, 196);
		windowInactiveTitleBackgroundColor = new ColorUIResource(16, 16, 16);
		windowInactiveTitleColorLight = new ColorUIResource(64, 64, 64);
		windowInactiveTitleColorDark = new ColorUIResource(32, 32, 32);
		windowInactiveBorderColor = BLACK;

		menuForegroundColor = WHITE;
		menuBackgroundColor = new ColorUIResource(24, 26, 28);
		menuSelectionForegroundColor = BLACK;
		menuSelectionBackgroundColor = new ColorUIResource(196, 137, 0);
		menuColorLight = new ColorUIResource(96, 96, 96);
		menuColorDark = new ColorUIResource(32, 32, 32);

		toolbarBackgroundColor = new ColorUIResource(24, 26, 28);
		toolbarColorLight = new ColorUIResource(96, 96, 96);
		toolbarColorDark = new ColorUIResource(32, 32, 32);

		tabAreaBackgroundColor = backgroundColor;
		desktopColor = new ColorUIResource(52, 55, 59);

		tooltipForegroundColor = WHITE;
		tooltipBackgroundColor = BLACK;// new ColorUIResource(16, 16, 16);

		controlFont = new FontUIResource("Dialog", Font.BOLD, 12);
		systemFont = new FontUIResource("Dialog", Font.BOLD, 12);
		userFont = new FontUIResource("Dialog", Font.BOLD, 12);
		menuFont = new FontUIResource("Dialog", Font.BOLD, 12);
		windowTitleFont = new FontUIResource("Dialog", Font.BOLD, 12);
		smallFont = new FontUIResource("Dialog", Font.PLAIN, 10);
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColorArrs() {
		super.setUpColorArrs();
		Color topHi = ColorHelper.brighter(buttonColorLight, 50);
		Color topLo = buttonColorLight;
		Color bottomHi = buttonColorDark;
		Color bottomLo = ColorHelper.darker(buttonColorDark, 40);
		Color[] topColors = ColorHelper.createColorArr(topHi, topLo, 10);
		Color[] bottomColors = ColorHelper.createColorArr(bottomHi, bottomLo, 12);
		BUTTON_COLORS = new Color[22];
		System.arraycopy(topColors, 0, BUTTON_COLORS, 0, 10);
		System.arraycopy(bottomColors, 0, BUTTON_COLORS, 10, 12);
		CHECKBOX_COLORS = BUTTON_COLORS;

		topHi = ColorHelper.brighter(controlColorLight, 40);
		topLo = ColorHelper.brighter(controlColorDark, 40);
		bottomHi = controlColorLight;
		bottomLo = controlColorDark;
		topColors = ColorHelper.createColorArr(topHi, topLo, 10);
		bottomColors = ColorHelper.createColorArr(bottomHi, bottomLo, 12);
		DEFAULT_COLORS = new Color[22];
		System.arraycopy(topColors, 0, DEFAULT_COLORS, 0, 10);
		System.arraycopy(bottomColors, 0, DEFAULT_COLORS, 10, 12);

		HIDEFAULT_COLORS = ColorHelper.createColorArr(ColorHelper.brighter(controlColorLight, 15),
				ColorHelper.brighter(controlColorDark, 15), 20);
		ACTIVE_COLORS = ColorHelper.createColorArr(controlColorLight, controlColorDark, 20);
		INACTIVE_COLORS = ColorHelper.createColorArr(ColorHelper.brighter(controlColorLight, 5),
				ColorHelper.brighter(controlColorDark, 5), 20);
		SELECTED_COLORS = BUTTON_COLORS;

		topHi = ColorHelper.brighter(rolloverColorLight, 40);
		topLo = rolloverColorLight;
		bottomHi = rolloverColorDark;
		bottomLo = ColorHelper.darker(rolloverColorDark, 20);
		topColors = ColorHelper.createColorArr(topHi, topLo, 10);
		bottomColors = ColorHelper.createColorArr(bottomHi, bottomLo, 12);
		ROLLOVER_COLORS = new Color[22];
		System.arraycopy(topColors, 0, ROLLOVER_COLORS, 0, 10);
		System.arraycopy(bottomColors, 0, ROLLOVER_COLORS, 10, 12);
		DISABLED_COLORS = ColorHelper.createColorArr(new Color(80, 80, 80), new Color(64, 64, 64), 20);
		topHi = ColorHelper.brighter(windowTitleColorLight, 40);
		topLo = ColorHelper.brighter(windowTitleColorDark, 40);
		bottomHi = windowTitleColorLight;
		bottomLo = windowTitleColorDark;
		topColors = ColorHelper.createColorArr(topHi, topLo, 8);
		bottomColors = ColorHelper.createColorArr(bottomHi, bottomLo, 12);
		WINDOW_TITLE_COLORS = new Color[20];
		System.arraycopy(topColors, 0, WINDOW_TITLE_COLORS, 0, 8);
		System.arraycopy(bottomColors, 0, WINDOW_TITLE_COLORS, 8, 12);
		WINDOW_INACTIVE_TITLE_COLORS = ColorHelper.createColorArr(windowInactiveTitleColorLight,
				windowInactiveTitleColorDark, 20);
		MENUBAR_COLORS = DEFAULT_COLORS;
		TOOLBAR_COLORS = MENUBAR_COLORS;
		SLIDER_COLORS = BUTTON_COLORS;
		PROGRESSBAR_COLORS = DEFAULT_COLORS;
		THUMB_COLORS = DEFAULT_COLORS;
		TRACK_COLORS = ColorHelper.createColorArr(ColorHelper.darker(inputBackgroundColor, 5),
				ColorHelper.brighter(inputBackgroundColor, 10), 20);

		TAB_COLORS = DEFAULT_COLORS;
		COL_HEADER_COLORS = DEFAULT_COLORS;
	}

} // end of class NoireDefaultTheme
