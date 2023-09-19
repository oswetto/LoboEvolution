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

package org.sexydock.tabs.event;

import org.sexydock.tabs.Tab;

import javax.swing.*;

public class TabMovedEvent extends TabbedPaneEvent {
    public final Tab movedTab;
    public final int prevIndex;
    public final int newIndex;
    public TabMovedEvent(JTabbedPane tabbedPane, long timestamp, Tab movedTab, int prevIndex, int newIndex) {
        super(tabbedPane, timestamp);
        this.movedTab = movedTab;
        this.prevIndex = prevIndex;
        this.newIndex = newIndex;
    }

    public Tab getMovedTab() {
        return movedTab;
    }

    public int getPrevIndex() {
        return prevIndex;
    }

    public int getNewIndex() {
        return newIndex;
    }

    @Override
    public String toString() {
        return String.format("%s[tabbedPane: %s, timestamp: %d, movedTab: %s, prevIndex: %d, newIndex: %d]", getClass().getName(), tabbedPane, timestamp, movedTab, prevIndex, newIndex);
    }
}
