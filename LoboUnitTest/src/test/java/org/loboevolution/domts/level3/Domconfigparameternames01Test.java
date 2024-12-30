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

package org.loboevolution.domts.level3;


import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.DOMConfiguration;
import org.loboevolution.html.dom.DOMStringList;
import org.loboevolution.html.node.Document;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Checks getParameterNames and canSetParameter for Document.domConfig.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-domConfig">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-domConfig</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-parameterNames">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-parameterNames</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-canonical-form">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-canonical-form</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-cdata-sections">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-cdata-sections</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-check-character-normalization">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-check-character-normalization</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-comments">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-comments</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-datatype-normalization">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-datatype-normalization</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-entities">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-entities</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-error-handler">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-error-handler</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-infoset">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-infoset</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-namespaces">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-namespaces</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-namespace-declarations">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-namespace-declarations</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-normalize-characters">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-normalize-characters</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-split-cdata-sections">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-split-cdata-sections</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-validate">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-validate</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-validate-if-schema">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-validate-if-schema</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-well-formed">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-well-formed</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-element-content-whitespace">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-element-content-whitespace</a>
 */
public class Domconfigparameternames01Test extends LoboUnitTest {

    @Test
    public void runTest() {
        final Document doc;
        final DOMConfiguration config;
        final DOMStringList parameterNames;
        String parameterName;
        int matchCount = 0;
        Object paramValue;
        boolean canSet;
        doc = sampleXmlFile("barfoo.xml");
        config = doc.getDomConfig();
        assertNotNull(config, "Domconfigparameternames01Assert1");
        parameterNames = config.getParameterNames();
        assertNotNull(parameterNames, "Domconfigparameternames01Assert2");
        for (int indexN1008C = 0; indexN1008C < parameterNames.getLength(); indexN1008C++) {
            parameterName = parameterNames.item(indexN1008C);
            paramValue = config.getParameter(parameterName);
            canSet = config.canSetParameter(parameterName, paramValue);
            assertTrue(canSet, "Domconfigparameternames01Assert3");
            config.setParameter(parameterName, paramValue);

            if (isCount(parameterName)) {
                matchCount += 1;
            }
        }
        assertEquals(16, matchCount, "Domconfigparameternames01Assert4");
    }

    private boolean isCount(String parameterName) {
        List<String> list = new ArrayList<>();
        list.add("canonical-form");
        list.add("cdata-sections");
        list.add("check-character-normalization");
        list.add("comments");
        list.add("datatype-normalization");
        list.add("entities");
        list.add("error-handler");
        list.add("infoset");
        list.add("namespaces");
        list.add("namespace-declarations");
        list.add("normalize-characters");
        list.add("split-cdata-sections");
        list.add("validate");
        list.add("validate-if-schema");
        list.add("well-formed");
        list.add("element-content-whitespace");
        return list.stream().anyMatch(l -> l.equals(parameterName.toLowerCase()));
    }
}

