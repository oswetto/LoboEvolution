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

package org.loboevolution.javax.xml.transform;

public abstract class TransformerFactory {

    protected TransformerFactory() {
        throw new RuntimeException("Stub!");
    }

    public static TransformerFactory newInstance()
            throws TransformerFactoryConfigurationError {
        throw new RuntimeException("Stub!");
    }

    public static TransformerFactory newInstance(final String factoryClassName, final ClassLoader classLoader)
            throws TransformerFactoryConfigurationError {
        throw new RuntimeException("Stub!");
    }

    public abstract Transformer newTransformer(Source source)
            throws TransformerConfigurationException;

    public abstract Transformer newTransformer()
            throws TransformerConfigurationException;

    public abstract Templates newTemplates(Source source)
            throws TransformerConfigurationException;

    public abstract Source getAssociatedStylesheet(Source source, final String s, final String s1, final String s2)
            throws TransformerConfigurationException;

    public abstract URIResolver getURIResolver();

    public abstract void setURIResolver(URIResolver uriresolver);

    public abstract void setFeature(final String s, boolean flag)
            throws TransformerConfigurationException;

    public abstract boolean getFeature(final String s);

    public abstract void setAttribute(final String s, Object obj);

    public abstract Object getAttribute(final String s);

    public abstract ErrorListener getErrorListener();

    public abstract void setErrorListener(ErrorListener errorlistener);
}
