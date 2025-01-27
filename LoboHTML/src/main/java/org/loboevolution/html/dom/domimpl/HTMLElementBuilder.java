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
/*
 * Created on Oct 8, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.rss.*;
import org.loboevolution.svg.*;

import java.awt.font.TextAttribute;

/**
 * <p>Abstract HTMLElementBuilder class.</p>
 */
public abstract class HTMLElementBuilder {

	public static class Anchor extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLAnchorElementImpl(name);
		}
	}

	public static class Base extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLBaseElementImpl(name);
		}
	}

	public static class Head extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLHeadElementImpl(name);
		}
	}

	public static class Quote extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLQuoteElementImpl(name);
		}
	}

	public static class Dd extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLDDElementImpl(name);
		}
	}
	public static class Body extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLBodyElementImpl(name);
		}
	}

	public static class Br extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLBRElementImpl(name);
		}
	}

	public static class Button extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLButtonElementImpl(name);
		}
	}

	public static final class Canvas extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new HTMLCanvasElementImpl(name);
		}
	}

	public static class Center extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLCenterElementImpl(name);
		}
	}
	
	public static class Caption extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLTableCaptionElementImpl(name);
		}
	}

	public static class Code extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLMonospacedElementImpl(name);
		}
	}

	public static class Div extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLDivElementImpl(name);
		}
	}

	public static class Dir extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLDirectoryElementImpl(name);
		}
	}

	public static class Dl extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLDListElementImpl(name);
		}
	}

	public static class Details extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLDetailsElementImpl(name);
		}
	}

	public static class Dialog extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLDialogElementImpl(name);
		}
	}
	public static class Em extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLEmElementImpl(name);
		}
	}

	public static class Fieldset extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLFieldsetElementImpl(name);
		}
	}

	public static class Figure extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLFigureElementImpl(name);
		}
	}

	public static class Form extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLFormElementImpl(name);
		}
	}

	public static class Heading extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLHeadingElementImpl(name);
		}
	}

	public static class Hr extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLHRElementImpl(name);
		}
	}

	public static class Html extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLHtmlElementImpl(name);
		}
	}

	public static class HtmlObject extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLObjectElementImpl(name);
		}
	}

	public static class IFrame extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLIFrameElementImpl(name);
		}
	}

	public static class Img extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLImageElementImpl(name);
		}
	}

	public static class Input extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLInputElementImpl(name);
		}
	}

	public static class Legend extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLLegendElementImpl(name);
		}
	}

	public static class Li extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLLIElementImpl(name);
		}
	}

	public static class Link extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLLinkElementImpl(name);
		}
	}

	public static class NonStandard extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLNonStandardElement(name);
		}
	}

	public static class NoScript extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLNoScriptElementImpl(name);
		}
	}

	public static class OptGroup extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLOptGroupElementImpl(name);
		}
	}

	public static class Ol extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLOListElementImpl(name);
		}
	}

	public static class Option extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLOptionElementImpl(name);
		}
	}

	public static class P extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLPElementImpl(name);
		}
	}

	public static class Pre extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLPreElementImpl(name);
		}
	}

	public static class Progress extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLProgressElementImpl(name);
		}
	}

	public static class Script extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLScriptElementImpl(name);
		}
	}

	public static class Select extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLSelectElementImpl(name);
		}
	}

	public static class Small extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLSmallElementImpl(name);
		}
	}

	public static class Span extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLSpanElementImpl(name);
		}
	}

	public static class Strike extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLStrikeElementImpl(name);
		}
	}

	public static class Strong extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLStrongElementImpl(name);
		}
	}

	public static class Style extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLStyleElementImpl(name);
		}
	}

	public static class Sub extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLSuperscriptElementImpl(name, TextAttribute.SUPERSCRIPT_SUB);
		}
	}

	public static class Sup extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLSuperscriptElementImpl(name, TextAttribute.SUPERSCRIPT_SUPER);
		}
	}

	public static class Table extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLTableElementImpl(name);
		}
	}

	public static class Td extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLTableCellElementImpl(name);
		}
	}

	public static class Textarea extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLTextAreaElementImpl(name);
		}
	}

	public static class Th extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLTableCellElementImpl(name);
		}
	}

	public static class Title extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLTitleElementImpl(name);
		}
	}

	public static class Tr extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLTableRowElementImpl(name);
		}
	}
	
	public static class TFoot extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLTableSectionElementImpl(name);
		}
	}
	
	public static class THead extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLTableSectionElementImpl(name);
		}
	}
	
	public static class TBody extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLTableSectionElementImpl(name);
		}
	}
	
	public static class Col extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLTableColElementImpl(name);
		}
	}

	public static class ColGroup extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLTableColGroupElementImpl(name);
		}
	}

	public static class Tt extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLMonospacedElementImpl(name);
		}
	}

	public static class Ul extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLUListElementImpl(name);
		}
	}

	public static class Underline extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLUnderlineElementImpl(name);
		}
	}
	
	public static final class SVG extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new SVGSVGElementImpl(name);
		}		
	}

	public static final class SVGAnimateTrasform extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new SVGAnimateTransformElementImpl(name);
		}
	}

	public static final class SVGAnimate extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new SVGAnimateElementImpl(name);
		}
	}

	public static final class SVGStop extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new SVGStopElementImpl(name);
		}
	}

	public static final class SVGClipPath extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new SVGClipPathElementImpl(name);
		}
	}

	public static final class SVGLinearGradient extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new SVGLinearGradientElementImpl(name);
		}
	}

	public static final class SVGRadialGradient extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new SVGRadialGradientElementImpl(name);
		}
	}

	public static final class SVGSymbol extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new SVGSymbolElementImpl(name);
		}
	}

	public static final class SVGText extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new SVGTextElementImpl(name);
		}
	}

	public static final class SVGUse extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new SVGUseElementImpl(name);
		}
	}

	public static final class SVGG extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new SVGGElementImpl(name);
		}
	}

	public static final class SVGDefs extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new SVGDefsElementImpl(name);
		}
	}

	public static final class SVGRect extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new SVGRectElementImpl(name);
		}
	}
	
	public static final class SVGCircle extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new SVGCircleElementImpl(name);
		}
	}
	
	public static final class SVGEllipse extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new SVGEllipseElementImpl(name);
		}
	}
	
	public static final class SVGLine extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new SVGLineElementImpl(name);
		}
	}
	
	public static final class SVGPolyline extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new SVGPolylineElementImpl(name);
		}
	}

	public static final class SVGPolygon extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new SVGPolygonElementImpl(name);
		}
	}
	
	public static final class SVGPath extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new SVGPathElementImpl(name);
		}
	}
	
	public static final class SVGImage extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new SVGImageElementImpl(name);
		}
	}
	
	public static final class RSS extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new RSSElementImpl(name);
		}		
	}
	
	public static final class Channel extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new RSSChanelElementImpl(name);
		}		
	}
	
	public static final class RSSTitle extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new RSSTitleElementImpl(name);
		}		
	}

	public static final class RSSDescription extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new RSSDescriptionElementImpl(name);
		}		
	}
	
	public static final class RSSItem extends HTMLElementBuilder {
		@Override
		protected HTMLElementImpl build(final String name) {
			return new RSSItemElementImpl(name);
		}		
	}

	public static class Marquee extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) { return new HTMLMarqueeElementImpl(name); }
	}

	public static class Meter extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLMeterElementImpl(name);
		}
	}

	public static class Video extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(final String name) {
			return new HTMLVideoElementImpl(name);
		}
	}
	
	/**
	 * <p>build.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	protected abstract HTMLElementImpl build(final String name);
}
