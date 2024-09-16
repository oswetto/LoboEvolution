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
package org.loboevolution.js;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * <p>JavaClassWrapperFactory class.</p>
 */
public final class JavaClassWrapperFactory {

    private final Map<Class, WeakReference<JavaClassWrapper>> classWrappers = new WeakHashMap();

	private JavaClassWrapperFactory() { }

    private static final class InstanceHolder {
        private static final JavaClassWrapperFactory instance = new JavaClassWrapperFactory();
    }

    /**
	 * <p>Getter for the field instance.</p>
	 *
	 * @return a {@link org.loboevolution.js.JavaClassWrapperFactory} object.
	 */
	public static JavaClassWrapperFactory getInstance() {
        return InstanceHolder.instance;
	}

	/**
	 * <p>getClassWrapper.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @return a {@link org.loboevolution.js.JavaClassWrapper} object.
	 */
	public JavaClassWrapper getClassWrapper(final Class clazz) {
		synchronized (this) {
			// WeakHashMaps where the value refers to
			// the key will retain keys. Must make it
			// refer to the value weakly too.
			final WeakReference<JavaClassWrapper> jcwr = this.classWrappers.get(clazz);
			JavaClassWrapper jcw = null;
			if (jcwr != null) {
				jcw = jcwr.get();
			}
			if (jcw == null) {
				jcw = new JavaClassWrapper(clazz);
				this.classWrappers.put(clazz, new WeakReference<>(jcw));
			}
			return jcw;
		}
	}
}
