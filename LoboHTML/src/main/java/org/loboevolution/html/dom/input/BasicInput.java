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

package org.loboevolution.html.dom.input;

import lombok.Data;
import org.loboevolution.common.Strings;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.html.dom.HTMLInputElement;
import org.loboevolution.html.dom.domimpl.HTMLBasicInputElement;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.js.WindowImpl;
import org.loboevolution.js.LoboContextFactory;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>BasicInput class.</p>
 */
@Data
public class BasicInput implements FocusListener, KeyListener, CaretListener, MouseListener {

    private HTMLBasicInputElement element;

    private JTextComponent jComponent;

    @Override
    public void focusGained(final FocusEvent e) {
        if (element.getOnfocus() != null) {
            Executor.executeFunction(element, element.getOnfocus(), new Object[]{}, getWindowFactory());
        }

        if (element.getOnfocusin() != null) {
            Executor.executeFunction(element, element.getOnfocusin(), new Object[]{}, getWindowFactory());
        }
    }

    @Override
    public void focusLost(final FocusEvent event) {
        final boolean autocomplete = element.isAutocomplete();
        final String baseUrl = element.getBaseURI();
        final String type = element instanceof HTMLInputElement ? ((HTMLInputElement)element).getType() : "";

        final String selectedText = jComponent.getSelectedText();
        if (Strings.isNotBlank(selectedText) && Strings.isNotBlank(element.getValue())) {
            final Pattern word = Pattern.compile(selectedText);
            final Matcher match = word.matcher(element.getValue());

            while (match.find()) {
                element.setSelectionRange(match.start(), match.end() - 1);
            }
        }

        if (autocomplete || "password".equals(type)) {
            final HTMLInputElementImpl im =  (HTMLInputElementImpl)element;
            final WindowImpl win = (WindowImpl) im.getDocumentNode().getDefaultView();
            final HtmlRendererConfig config = win.getConfig();
            final String text = jComponent.getText();
            final boolean isNavigation = element.getUserAgentContext().isNavigationEnabled();
            config.deleteInput(text, baseUrl);
            config.insertLogin(type, text, baseUrl, isNavigation);
        }

        if (element.getOnblur() != null) {
            Executor.executeFunction(element, element.getOnblur(), new Object[]{}, getWindowFactory());
        }

        if (element.getOnfocusout() != null) {
            jComponent.setText(element.getValue());
            Executor.executeFunction(element, element.getOnfocusout(), new Object[]{}, getWindowFactory());
        }
    }

    @Override
    public void keyTyped(final KeyEvent e) {
        if (element.getOnkeydown() != null) {
            Executor.executeFunction(element, element.getOnkeydown(), new Object[]{}, getWindowFactory());
        }

        if (element.getOnkeypress() != null) {
            Executor.executeFunction(element, element.getOnkeypress(), new Object[]{}, getWindowFactory());
        }

        if (element.getOninput() != null) {
            element.setValue(Strings.isBlank(element.getValue()) ? String.valueOf(e.getKeyChar()) : element.getValue() + e.getKeyChar());
            Executor.executeFunction(element, element.getOninput(), new Object[]{}, getWindowFactory());
        }
    }

    @Override
    public void keyPressed(final KeyEvent e) {

    }

    @Override
    public void keyReleased(final KeyEvent e) {
        if (element.getOnkeyup() != null) {
            Executor.executeFunction(element, element.getOnkeyup(), new Object[]{}, getWindowFactory());
        }
    }

    @Override
    public void caretUpdate(final CaretEvent e) {
        final int dot = e.getDot();
        final int mark = e.getMark();

        if (dot != mark && element.getOnselect() != null) {
            Executor.executeFunction(element, element.getOnselect(), new Object[]{}, getWindowFactory());
        }
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
        if (element.getOnmouseover() != null) {
            Executor.executeFunction(element, element.getOnmouseover(), new Object[]{}, getWindowFactory());
        }
    }

    public void mousePressed(final MouseEvent e) {
        if (element.getOnkeypress() != null) {
            Executor.executeFunction(element, element.getOnkeypress(), new Object[]{}, getWindowFactory());
        }

        if (element.getOnkeydown() != null) {
            Executor.executeFunction(element, element.getOnkeydown(), new Object[]{}, getWindowFactory());
        }
    }

    public void mouseReleased(final MouseEvent e) {
        if (element.getOnkeyup() != null) {
            Executor.executeFunction(element, element.getOnkeyup(), new Object[]{}, getWindowFactory());
        }
    }

    @Override
    public void mouseExited(final MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        // TODO Auto-generated method stub
    }

    private LoboContextFactory getWindowFactory() {
        final HTMLDocumentImpl doc = (HTMLDocumentImpl) element.getOwnerDocument();
        final WindowImpl window = (WindowImpl) doc.getDefaultView();
        return window.getContextFactory();
    }
}
