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

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLMarqueeElement;
import org.loboevolution.events.Event;
import org.loboevolution.events.EventListener;
import org.loboevolution.html.renderstate.BlockRenderState;
import org.loboevolution.html.renderstate.RenderState;

public class HTMLMarqueeElementImpl extends HTMLElementImpl implements HTMLMarqueeElement {

    /**
     * <p>Constructor for HTMLMarqueeElementImpl.</p>
     *
     * @param name a {@link String} object.
     */
    public HTMLMarqueeElementImpl(final String name) {
        super(name);
    }

    @Override
    protected RenderState createRenderState(final RenderState prevRenderState) {
        return new BlockRenderState(prevRenderState, this);
    }

    @Override
    public String getBehavior() {
        return null;
    }

    @Override
    public void setBehavior(final String behavior) {

    }

    @Override
    public String getBgColor() {
        return null;
    }

    @Override
    public void setBgColor(final String bgColor) {

    }

    @Override
    public String getDirection() {
        return null;
    }

    @Override
    public void setDirection(final String direction) {

    }

    @Override
    public String getHeight() {
        return null;
    }

    @Override
    public void setHeight(final String height) {

    }

    @Override
    public double getHspace() {
        return 0;
    }

    @Override
    public void setHspace(final double hspace) {

    }

    @Override
    public double getLoop() {
        return 0;
    }

    @Override
    public void setLoop(final double loop) {

    }

    @Override
    public EventListener<Event> getOnbounce() {
        return null;
    }

    @Override
    public void setOnbounce(final EventListener<Event> onbounce) {

    }

    @Override
    public EventListener<Event> getOnfinish() {
        return null;
    }

    @Override
    public void setOnfinish(final EventListener<Event> onfinish) {

    }

    @Override
    public EventListener<Event> getOnstart() {
        return null;
    }

    @Override
    public void setOnstart(final EventListener<Event> onstart) {

    }

    @Override
    public double getScrollAmount() {
        return 0;
    }

    @Override
    public void setScrollAmount(final double scrollAmount) {

    }

    @Override
    public double getScrollDelay() {
        return 0;
    }

    @Override
    public void setScrollDelay(final double scrollDelay) {

    }

    @Override
    public boolean isTrueSpeed() {
        return false;
    }

    @Override
    public void setTrueSpeed(final boolean trueSpeed) {

    }

    @Override
    public double getVspace() {
        return 0;
    }

    @Override
    public void setVspace(final double vspace) {

    }

    @Override
    public String getWidth() {
        return null;
    }

    @Override
    public void setWidth(final String width) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
