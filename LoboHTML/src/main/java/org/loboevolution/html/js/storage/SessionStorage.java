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

package org.loboevolution.html.js.storage;

import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.html.node.js.webstorage.Storage;

import java.util.Map;

/**
 * <p>SessionStorage class.</p>
 */
public class SessionStorage implements Storage {
	
	private int index;

	private final HtmlRendererConfig config;
	
	/**
	 * <p>Constructor for SessionStorage.</p>
	 */
	public SessionStorage(final HtmlRendererConfig config) {
		this.config = config;
		this.index = config.getTabs().size();
		if(this.index > 0) this.index = this.index -1;
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		return config.countStorage(index);
	}
	
	/** {@inheritDoc} */
	@Override
	public Object key(final int index) {
        int counter = 0;
        final Map<String, String> store = config.getMapStorage(index, 1);
        for (final String key : store.keySet()) {
            if (counter++ == index) {
                return key;
            }
        }
        return null;
    }
	
	/** {@inheritDoc} */
	@Override
	public Object getItem(final String key) {
		return config.getValue(key, 1, index);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setItem(final String keyName, final String keyValue) {
		config.deleteStorage(keyName, 1, index);
		config.insertStorage(keyName, keyValue, 1, index);
	}

	/** {@inheritDoc} */
	@Override
	public void removeItem(final String keyName) {
		config.deleteStorage(keyName, 1, index);

	}
	
	/** {@inheritDoc} */
	@Override
	public void clear() {
		config.deleteStorage(1, index);
	}

	@Override
	public String toString() {
		return "[object Storage]";
	}
}
