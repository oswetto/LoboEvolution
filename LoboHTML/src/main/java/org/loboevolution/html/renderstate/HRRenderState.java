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

package org.loboevolution.html.renderstate;

import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.style.BorderInsets;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.info.BorderInfo;

import java.awt.*;

public class HRRenderState extends BlockRenderState {

    /**
     * <p>Constructor for BlockRenderState.</p>
     *
     * @param prevRenderState a {@link RenderState} object.
     * @param element         a {@link HTMLElementImpl} object.
     */
    public HRRenderState(final RenderState prevRenderState, final HTMLElementImpl element) {
        super(prevRenderState, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HtmlInsets getMarginInsets() {
        HtmlInsets insets = this.marginInsets;

        if (insets != INVALID_INSETS) {
            return insets;
        }
        insets = super.getMarginInsets();
        if (insets == null || insets.htmlInsetsIsVoid()) {

            insets = new HtmlInsets();
            final int topBottom = HtmlValues.getPixelSize("0.5em", null, this.document.getDefaultView(), 0);
            insets.setTop(topBottom);
            insets.setBottom(topBottom);
            insets.setTopType(HtmlInsets.TYPE_PIXELS);
            insets.setBottomType(HtmlInsets.TYPE_PIXELS);
        }

        this.marginInsets = insets;
        return insets;
    }

    /** {@inheritDoc} */
    @Override
    public BorderInfo getBorderInfo() {
        BorderInfo binfo = this.borderInfo;
        if (binfo != INVALID_BORDER_INFO) {
            return binfo;
        }
        binfo = super.getBorderInfo();
        if (binfo == null || binfo.borderInfoIsVoid()) {

            if (binfo == null) {
                binfo = new BorderInfo();
            }

            binfo.setInsets(getDefaultBordernsets());
            binfo.setTopColor(Color.DARK_GRAY);
            binfo.setLeftColor(Color.DARK_GRAY);
            binfo.setRightColor(Color.DARK_GRAY);
            binfo.setBottomColor(Color.DARK_GRAY);
            binfo.setTopStyle(BorderInsets.BORDER_STYLE_SOLID);
            binfo.setLeftStyle(BorderInsets.BORDER_STYLE_SOLID);
            binfo.setRightStyle(BorderInsets.BORDER_STYLE_SOLID);
            binfo.setBottomStyle(BorderInsets.BORDER_STYLE_SOLID);

        }
        this.borderInfo = binfo;
        return binfo;
    }

    /** {@inheritDoc} */
    @Override
    public int getAlignXPercent() {
        return 50;
    }

    /** {@inheritDoc} */
    @Override
    public int getAlignYPercent() {
        return 50;
    }

    private HtmlInsets getDefaultBordernsets() {
        final int border = HtmlValues.getPixelSize("1px", null, element.getDocumentNode().getDefaultView(), -1);
        return new HtmlInsets(border, HtmlInsets.TYPE_PIXELS);
    }
}
