/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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
package org.loboevolution.apache.xpath.operations;

import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xpath.objects.XNumber;
import org.loboevolution.apache.xpath.objects.XObject;

/** The 'number()' operation expression executer. */
public class Number extends UnaryOperation {

  /** {@inheritDoc} */
  @Override
  public XObject operate(final XObject right) throws org.loboevolution.javax.xml.transform.TransformerException {

    if (XObject.CLASS_NUMBER == right.getType()) {
      return right;
    }
    return new XNumber(right.num());
  }

  /** {@inheritDoc} */
  @Override
  public double num(final XPathContext xctxt) throws org.loboevolution.javax.xml.transform.TransformerException {

    return m_right.num(xctxt);
  }
}
