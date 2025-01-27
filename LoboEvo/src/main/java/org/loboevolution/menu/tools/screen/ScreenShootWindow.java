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

package org.loboevolution.menu.tools.screen;

import java.awt.*;
import java.io.Serial;

import javax.swing.*;

import org.loboevolution.component.BrowserFrame;

/**
 * <p>ScreenShootWindow class.</p>
 */
public class ScreenShootWindow extends JFrame {

    /**
     * The Constant serialVersionUID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor for ScreenShootWindow.</p>
     *
     * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
     */
    public ScreenShootWindow(final BrowserFrame frame) {
		initLayout();
		initComponents(frame);
    }

    private void initComponents(final BrowserFrame frame) {
        add(new CapturePane(frame));
    }

    private void initLayout() {
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final Rectangle bounds = getVirtualBounds();
        setLocation(bounds.getLocation());
        setSize(bounds.getSize());
        setAlwaysOnTop(true);
        setVisible(true);
    }

    public static Rectangle getVirtualBounds() {
        final Rectangle bounds = new Rectangle(0, 0, 0, 0);
        final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final GraphicsDevice[] lstGDs = ge.getScreenDevices();
        for (final GraphicsDevice gd : lstGDs) {
            bounds.add(gd.getDefaultConfiguration().getBounds());
        }
        return bounds;
    }
}