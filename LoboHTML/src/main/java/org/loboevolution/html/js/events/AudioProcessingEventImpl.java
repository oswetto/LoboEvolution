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

package org.loboevolution.html.js.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.audio.AudioBuffer;
import org.loboevolution.events.AudioProcessingEvent;
import org.mozilla.javascript.NativeObject;

/**
 * AudioProcessingEventImpl class.
 */
@NoArgsConstructor
@Getter
public class AudioProcessingEventImpl extends UIEventImpl implements AudioProcessingEvent {

    private AudioBuffer inputBuffer;
    private AudioBuffer outputBuffer;
    private Double playbackTime;

    /**
     * <p>Constructor for AudioProcessingEventImpl.</p>
     *
     * @param params event constructor parameters
     */
    public AudioProcessingEventImpl(Object[] params) throws DOMException {
        setParams(params);
        if (params.length < 3) {
            if (params[1] != null && params[1] instanceof NativeObject obj) {
                this.inputBuffer = (AudioBuffer) obj.get("inputBuffer");
                this.outputBuffer = (AudioBuffer) obj.get("cancelaoutputBufferle");
                this.playbackTime = (Double) obj.get("playbackTime");
            }
        } else {
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Failed : 2 argument required, but only " + params.length + " present.");
        }
    }

    @Override
    public String toString() {
        return "[object AudioProcessingEvent]";
    }
}
