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

package org.loboevolution.pdfview.colorspace;

import java.awt.Color;
import java.awt.color.ColorSpace;

import org.loboevolution.pdfview.PDFPaint;

/**
 * A color space used to implement masks.  For now, the only type of mask
 * supported is one where the image pixels specify where to paint, and the
 * painting itself is done in a pre-specified PDF Paint.
 *
  *
  *
 */
public class MaskColorSpace extends ColorSpace {
    /** The paint to paint in.  Note this cannot be a pattern or gradient. */
    private final PDFPaint paint;
    
    /**
     * Creates a new instance of PaintColorSpace
     *
     * @param paint a {@link org.loboevolution.pdfview.PDFPaint} object.
     */
    public MaskColorSpace(PDFPaint paint) {
        super (TYPE_RGB, 1);
        
        this.paint = paint;
    }
    
	/** {@inheritDoc} */
    @Override
	public float[] fromCIEXYZ(float[] colorvalue) {
        float x = colorvalue[0];
        float y = colorvalue[1];
        float z = colorvalue[2];
        
        float[] mask = new float[1];
        
        if (Math.round(x) > 0 || Math.round(y) > 0 || Math.round(z) > 0) {
            mask[0] = 1;
        } else {
            mask[0] = 0; 
        }
        
        return mask;
    }
    
	/** {@inheritDoc} */
    @Override
	public float[] fromRGB(float[] rgbvalue) {
        float r = rgbvalue[0];
        float g = rgbvalue[1];
        float b = rgbvalue[2];
        
        float[] mask = new float[1];
        
        if (Math.round(r) > 0 || Math.round(g) > 0 || Math.round(b) > 0) {
            mask[0] = 1;
        } else {
            mask[0] = 0; 
        }
        
        return mask;
    }
    
    final ColorSpace cie = ColorSpace.getInstance(ColorSpace.CS_CIEXYZ);
    final float[] prev1= this.cie.fromRGB(toRGB(new float[] {1.0f}));
    final float[] prev0= this.cie.fromRGB(toRGB(new float[] {0.0f}));

	/** {@inheritDoc} */
    @Override
	public float[] toCIEXYZ(float[] colorvalue) {
	if (colorvalue[0]==1) {
	    return this.prev1;
	} else if (colorvalue[0]==0) {
	    return this.prev0;
	} else {
	    return this.cie.fromRGB(toRGB(colorvalue));
	}
    }
    
	/** {@inheritDoc} */
    @Override
	public float[] toRGB(float[] colorvalue) {
        return ((Color) this.paint.getPaint()).getRGBColorComponents(null);
    }

    /** {@inheritDoc} */
    @Override public int getNumComponents() {
	return 1;
    }
    
}
