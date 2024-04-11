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
import org.loboevolution.html.js.css.CSSKeyFramesRuleImpl;

/**
 * Tests for {@link CSSKeyFramesRuleImpl}.
 */
@ExtendWith(AlertsExtension.class)
public class CSSKeyframesRuleTest extends LoboUnitTest {


    @Test
    @Alerts({"[object CSSKeyframesRule]", "7"})
    public void simple() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @keyframes identifier { 0% { top: 0; left: 0; } 100% { top: 100px; left: 100%; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  if (styleSheet.cssRules) {\n"
                + "    var rule = styleSheet.cssRules[0];\n"
                + "   alert(rule);\n"
                + "   alert(rule.type);\n"
                + "  } else {\n"
                + "   alert('Your browser does not support this example');\n"
                + "  }\n"
                + "</script>\n"

                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object CSSKeyframesRule]", "identifier"})
    public void name() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @keyframes identifier { 0% { top: 0; left: 0; } 100% { top: 100px; left: 100%; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  if (styleSheet.cssRules) {\n"
                + "    var rule = styleSheet.cssRules[0];\n"
                + "   alert(rule);\n"
                + "   alert(rule.name);\n"
                + "  } else {\n"
                + "   alert('Your browser does not support this example');\n"
                + "  }\n"
                + "</script>\n"

                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object CSSRuleList]")
    public void cssRules() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @keyframes identifier { 0% { top: 0; left: 0; } 100% { top: 100px; left: 100%; }}\n"
                + "</style>\n"

                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  if (styleSheet.cssRules) {\n"
                + "    var rule = styleSheet.cssRules[0];\n"
                + "   alert(rule.cssRules);\n"
                + "  } else {\n"
                + "   alert('Your browser does not support this example');\n"
                + "  }\n"
                + "</script>\n"

                + "</body></html>";

        checkHtmlAlert(html);
    }
}
