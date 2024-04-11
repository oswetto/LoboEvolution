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
package org.loboevolution.css;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for ComputedFont.
 */
@ExtendWith(AlertsExtension.class)
public class ComputedFontTest extends LoboUnitTest {


    @Test
    @Alerts({"", "16px", "2em", "32px", "150%", "24px"})
    public void fontSizeEm() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('mydiv');\n"
                + "    var style = window.getComputedStyle(div, null);\n"
                + "   alert(div.style.fontSize);\n"
                + "   alert(style.fontSize);\n"
                + "    div.style.fontSize = '2em';\n"
                + "   alert(div.style.fontSize);\n"
                + "   alert(style.fontSize);\n"
                + "    div.style.fontSize = '150%';\n"
                + "   alert(div.style.fontSize);\n"
                + "   alert(style.fontSize);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='mydiv'></div>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "16px serif", "", "normal", "", "normal", "", "400",
            "", "16px", "", "normal", "", "serif"})
    public void fontInitial() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.createElement('div');\n"
                + "    debug(div);\n"
                + "    document.body.appendChild(div);\n"
                + "    debug(div);\n"
                + "  }\n"
                + "  function debug(div) {\n"
                + "    var style = window.getComputedStyle(div, null);\n"
                + "   alert(div.style.font);\n"
                + "   alert(style.font);\n"
                + "   alert(div.style.fontStyle);\n"
                + "   alert(style.fontStyle);\n"
                + "   alert(div.style.fontVariant);\n"
                + "   alert(style.fontVariant);\n"
                + "   alert(div.style.fontWeight);\n"
                + "   alert(style.fontWeight);\n"
                + "   alert(div.style.fontSize);\n"
                + "   alert(style.fontSize);\n"
                + "   alert(div.style.lineHeight);\n"
                + "   alert(style.lineHeight);\n"
                + "   alert(div.style.fontFamily);\n"
                + "   alert(style.fontFamily);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"15px arial, sans-serif", "15px arial, sans-serif", "normal", "normal",
            "oblique 15px arial, sans-serif", "oblique 15px arial, sans-serif", "oblique", "oblique"})
    public void fontStyle() {
        font("15px arial, sans-serif", "fontStyle", "oblique");
    }

    private void font(final String fontToSet, final String property, final String value) {
        String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('mydiv');\n"
                + "    div.style.font = '" + fontToSet + "';\n"
                + "    debug(div);\n";

        if (value != null) {
            html += "    div.style." + property + " = '" + value + "';\n"
                    + "    debug(div);\n";
        }

        html += "  }\n"
                + "  function debug(div) {\n"
                + "    var style = window.getComputedStyle(div, null);\n"
                + "   alert(div.style.font);\n"
                + "   alert(style.font);\n"
                + "   alert(div.style." + property + ");\n"
                + "   alert(style." + property + ");\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='mydiv'></div>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "16px \"Times New Roman\"", "", "\"Times New Roman\""})
    public void wrongFontFamily() {
        font("xyz", "fontFamily", null);
    }

    @Test
    @Alerts({"1px xyz", "1px xyz", "xyz", "xyz", "1px abc", "1px abc", "abc", "abc"})
    public void minimalFontFamily() {
        font("1px xyz", "fontFamily", "abc");
    }

    @Test
    @Alerts({"", "16px \"Times New Roman\"",
            "", "\"Times New Roman\"", "", "16px abc", "abc", "abc"})
    public void minimalFontFamilyReversed() {
        font("xyz 1px", "fontFamily", "abc");
    }

    @Test
    @Alerts({"1px / 2px xyz", "1px / 2px xyz",
            "2px", "2px", "1px xyz", "1px xyz", "normal", "normal"})
    public void minimalLineHeight() {
        font("1px/2px xyz", "lineHeight", "normal");
    }

    @Test
    @Alerts({"1px / 2px xyz", "1px / 2px xyz",
            "2px", "2px", "1px xyz", "1px xyz", "normal", "normal"})
    public void minimalLineHeightSpace() {
        font("1px / 2px xyz", "lineHeight", "normal");
    }

    @Test
    @Alerts({"1px / 2px xyz", "1px / 2px xyz",
            "2px", "2px", "1px xyz", "1px xyz", "normal", "normal"})
    public void minimalLineHeightSpace2() {
        font("1px/ 2px xyz", "lineHeight", "normal");
    }

    @Test
    @Alerts({"1px / 2px xyz", "1px / 2px xyz",
            "2px", "2px", "1px xyz", "1px xyz", "normal", "normal"})
    public void minimalLineHeightSpace3() {
        font("1px /2px xyz", "lineHeight", "normal");
    }

    @Test
    @Alerts({"1px / 2px xyz", "1px / 2px xyz",
            "2px", "2px", "1px xyz", "1px xyz", "normal", "normal"})
    public void minimalLineHeightSpace4() {
        font("1px  /2px xyz", "lineHeight", "normal");
    }
}
