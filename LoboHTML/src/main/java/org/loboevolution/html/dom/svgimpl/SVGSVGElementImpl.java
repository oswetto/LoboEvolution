/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.svgimpl;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.filter.IdFilter;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.dom.svg.*;
import org.loboevolution.html.js.events.EventFactory;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.events.Event;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>SVGSVGElementImpl class.</p>
 */
public class SVGSVGElementImpl extends SVGLocatableImpl implements SVGSVGElement, Drawable {
	
	private final SVGRect viewport;

	private SVGViewSpec currentView;
	
	private SVGAnimatedRect viewBox;
	
	private SVGPoint currentTranslate;
	
	private AffineTransform viewboxToViewportTransform;

	private float currentScale;

	private boolean useCurrentView = false;
	
	private boolean painted;

	/**
	 * <p>Constructor for SVGSVGElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGSVGElementImpl(final String name) {
		super(name);
		currentTranslate = new SVGPointImpl();
		float x = getX().getBaseVal().getValue();
		float y = getY().getBaseVal().getValue();
		float width = getWidth().getBaseVal().getValue();
		float height = getHeight().getBaseVal().getValue();
		viewport = new SVGRectImpl(x, y, width, height);
		recalculateViewboxToViewportTransform();
	}

	@Override
	public SVGRect getBBox() {
		Shape shape = createShape(null);
		return new SVGRectImpl(shape.getBounds2D());
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedRect getViewBox() {
		if (viewBox == null) {
			final float width = getWidth().getBaseVal().getValue();
			final float height = getHeight().getBaseVal().getValue();
			viewBox = new SVGAnimatedRectImpl(new SVGRectImpl(0, 0, width, height));
		}
		return viewBox;
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedPreserveAspectRatio getPreserveAspectRatio() {
		return new SVGAnimatedPreserveAspectRatioImpl(new SVGPreserveAspectRatioImpl(), this);
	}

	/** {@inheritDoc} */
	@Override
	public short getZoomAndPan() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void setZoomAndPan(short zoomAndPan) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public Event createEvent(String eventType) {
		return EventFactory.createEvent(eventType);
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getX() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("x")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getY() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("y")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getWidth() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("width")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getHeight() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("height")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGRect getViewport() {
		float x = getX().getBaseVal().getValue();
		float y = getY().getBaseVal().getValue();
		float width = getWidth().getBaseVal().getValue();
		float height = getHeight().getBaseVal().getValue();
		return new SVGRectImpl(x, y, width, height);
	}

	/** {@inheritDoc} */
	@Override
	public float getPixelUnitToMillimeterX() {
		return (float) 0.28; 
	}

	/** {@inheritDoc} */
	@Override
	public float getPixelUnitToMillimeterY() {
		return (float) 0.28; 
	}

	/** {@inheritDoc} */
	@Override
	public float getScreenPixelToMillimeterX() {
		return (float) 0.28; 
	}

	/** {@inheritDoc} */
	@Override
	public float getScreenPixelToMillimeterY() {
		return (float) 0.28; 
	}

	/** {@inheritDoc} */
	@Override
	public boolean getUseCurrentView() {
		return useCurrentView;
	}

	/** {@inheritDoc} */
	@Override
	public void setUseCurrentView(boolean useCurrentView) {
		this.useCurrentView = useCurrentView;
	}

	/** {@inheritDoc} */
	@Override
	public SVGViewSpec getCurrentView() {
		return currentView;
	}

	/** {@inheritDoc} */
	@Override
	public float getCurrentScale() {
		return currentScale;
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentScale(float currentScale) {
		this.currentScale = currentScale;
	}

	/** {@inheritDoc} */
	@Override
	public SVGPoint getCurrentTranslate() {
		return currentTranslate;
	}

	/**
	 * <p>Setter for the field currentTranslate.</p>
	 *
	 * @param currentTranslate a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @throws DOMException if any.
	 */
	public void setCurrentTranslate(SVGPoint currentTranslate) {
		this.currentTranslate = currentTranslate;
	}

	/** {@inheritDoc} */
	@Override
	public int suspendRedraw(int max_wait_milliseconds) {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void unsuspendRedraw(int suspend_handle_id) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void unsuspendRedrawAll() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void forceRedraw() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void pauseAnimations() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void unpauseAnimations() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public boolean animationsPaused() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public float getCurrentTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentTime(float seconds) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public NodeList getIntersectionList(SVGRect rect, SVGElement referenceElement) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public NodeList getEnclosureList(SVGRect rect, SVGElement referenceElement) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean checkIntersection(SVGElement element, SVGRect rect) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean checkEnclosure(SVGElement element, SVGRect rect) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void deselectAll() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public SVGLength createSVGLength() {
		return new SVGLengthImpl();
	}

	/** {@inheritDoc} */
	@Override
	public SVGAngle createSVGAngle() {
		return new SVGAngleImpl();
	}

	/** {@inheritDoc} */
	@Override
	public SVGPoint createSVGPoint() {
		return new SVGPointImpl();
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix createSVGMatrix() {
		return new SVGMatrixImpl();
	}

	/** {@inheritDoc} */
	@Override
	public SVGNumber createSVGNumber() {
		return new SVGNumberImpl();
	}

	/** {@inheritDoc} */
	@Override
	public SVGRect createSVGRect() {
		float x = getX().getBaseVal().getValue();
		float y = getY().getBaseVal().getValue();
		float width = getWidth().getBaseVal().getValue();
		float height = getHeight().getBaseVal().getValue();
		return new SVGRectImpl(x, y, width, height);
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform createSVGTransform() {
		return new SVGTransformImpl();
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform createSVGTransformFromMatrix(SVGMatrix matrix) {
		SVGTransform transform = new SVGTransformImpl();
		transform.setMatrix(matrix);
		return transform;
	}

	/** {@inheritDoc} */
	@Override
	public Element getElementById(String elementId) {
		NodeList nodeList = getNodeList(new IdFilter(elementId));
		return nodeList != null && nodeList.getLength() > 0 ? (Element)nodeList.item(0) : null;
	}

	/**
	 * <p>isPainted.</p>
	 *
	 * @return the painted
	 */
	public boolean isPainted() {
		return painted;
	}

	/**
	 * <p>Setter for the field painted.</p>
	 *
	 * @param painted the painted to set
	 */
	public void setPainted(boolean painted) {
		this.painted = painted;
	}


	public AffineTransform getViewboxToViewportTransform() {
		return viewboxToViewportTransform;
	}

	/** {@inheritDoc} */
	@Override
	public void draw(final Graphics2D graphics) {
		recalculateViewboxToViewportTransform();
		boolean display = getDisplay();
		float opacity = getOpacity();

		if (display && opacity > 0) {
			AffineTransform oldGraphicsTransform = graphics.getTransform();
			Shape oldClip = graphics.getClip();
			graphics.translate(viewport.getX(), viewport.getY());
			if (opacity < 1) {
				SVGSVGElement root = this;
				float currentScale = root.getCurrentScale();
				// create buffer to draw on
				Shape shape = createShape(null);
				AffineTransform screenCTM = getScreenCTM().getAffineTransform();
				Shape transformedShape = screenCTM.createTransformedShape(shape);
				Rectangle2D bounds = transformedShape.getBounds2D();
				double xInc = bounds.getWidth() / 5;
				double yInc = bounds.getHeight() / 5;
				bounds.setRect(bounds.getX() - xInc, bounds.getY() - yInc, bounds.getWidth() + 2 * xInc, bounds.getHeight() + 2 * yInc);
				int imageWidth = (int) (bounds.getWidth() * currentScale);
				int imageHeight = (int) (bounds.getHeight() * currentScale);
				if (imageWidth > 0 && imageHeight > 0) {
					BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_4BYTE_ABGR);
					Graphics2D offGraphics = (Graphics2D) image.getGraphics();
					RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					offGraphics.setRenderingHints(hints);
					if (currentScale != 1) {
						offGraphics.scale(currentScale, currentScale);
					}

					offGraphics.translate(-bounds.getX(), -bounds.getY());
					offGraphics.transform(screenCTM);
					drawChildren(offGraphics);
					
					Composite oldComposite = graphics.getComposite();
					AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
					graphics.setComposite(ac);
					AffineTransform imageTransform = AffineTransform.getTranslateInstance(bounds.getX(), bounds.getY());
					imageTransform.scale(1 / currentScale, 1 / currentScale);
					try {
						imageTransform.preConcatenate(screenCTM.createInverse());
					} catch (NoninvertibleTransformException e) {
					}
					graphics.drawImage(image, imageTransform, null);
					graphics.setComposite(oldComposite);
					image.flush();
				}
			} else {
				drawChildren(graphics);
			}

			graphics.setTransform(oldGraphicsTransform);
			graphics.setClip(oldClip);
		}
	}

	private void recalculateViewboxToViewportTransform() {

		viewboxToViewportTransform = new AffineTransform();

		short align = getPreserveAspectRatio().getAnimVal().getAlign();
		short meetOrSlice = getPreserveAspectRatio().getAnimVal().getMeetOrSlice();

		float sx = getViewport().getWidth() / getViewBox().getAnimVal().getWidth();
		float sy = getViewport().getHeight() / getViewBox().getAnimVal().getHeight();

		if (align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_NONE) {
			viewboxToViewportTransform.scale(sx, sy);

			float tx = -getViewBox().getAnimVal().getX();
			float ty = -getViewBox().getAnimVal().getY();
			viewboxToViewportTransform.translate(tx, ty);

		} else {

			float scale;
			if (meetOrSlice == SVGPreserveAspectRatio.SVG_MEETORSLICE_MEET) {
				scale = Math.min(sx, sy);
			} else {
				scale = Math.max(sx, sy);
			}

			float vpX = 0;
			float vpY = 0;
			float vpWidth = getViewport().getWidth();
			float vpHeight = getViewport().getHeight();

			float vbX = getViewBox().getAnimVal().getX();
			float vbY = getViewBox().getAnimVal().getY();
			float vbWidth = getViewBox().getAnimVal().getWidth();
			float vbHeight = getViewBox().getAnimVal().getHeight();

			float tx;
			float ty;

			if (meetOrSlice == SVGPreserveAspectRatio.SVG_MEETORSLICE_MEET) {
				if (sy < sx) {
					ty = vpY / scale - vbY;
					switch (align) {
						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMIN:
						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMID:
						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMAX:
							tx = vpX / scale - vbX;
							break;

						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMIN:
						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMID:
						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMAX:
							tx = (vpX + vpWidth / 2) / scale - (vbX + vbWidth / 2);
							break;
						default:
							tx = vpX + vpWidth / scale - (vbX + vbWidth);
							break;
					}
				} else {
					tx = vpX / scale - vbX;

					switch (align) {
						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMIN:
						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMIN:
						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMAXYMIN:
							ty = vpY / scale - vbY;
							break;

						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMID:
						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMID:
						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMAXYMID:
							ty = (vpY + vpHeight / 2) / scale - (vbY + vbHeight / 2);
							break;
						default:
							ty = (vpY + vpHeight) / scale - (vbY + vbHeight);
							break;
					}
				}
			} else { // SLICE

				if (sy > sx) {
					ty = vpY / scale - vbY;

					switch (align) {
						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMIN:
						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMID:
						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMAX:
							tx = vpX / scale - vbX;
							break;

						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMIN:
						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMID:
						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMAX:
							tx = (vpX + vpWidth / 2) / scale - (vbX + vbWidth / 2);
							break;
						default:
							tx = (vpX + vpWidth) / scale - (vbX + vbWidth);
							break;
					}


				} else {
					tx = vpX - vbX * scale;

					switch (align) {
						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMIN:
						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMIN:
						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMAXYMIN:
							ty = vpY / scale - vbY;
							break;

						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMID:
						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMID:
						case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMAXYMID:
							ty = (vpY + vpHeight / 2) / scale - (vbY + vbHeight / 2);
							break;
						default:
							ty = (vpY + vpHeight) / scale - (vbY + vbHeight);
							break;
					}
				}
			}

			viewboxToViewportTransform.scale(scale, scale);
			viewboxToViewportTransform.translate(tx, ty);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Shape createShape(AffineTransform transform) {
		GeneralPath path = new GeneralPath();
		if (hasChildNodes()) {
			NodeListImpl children = (NodeListImpl)getChildNodes();
			children.forEach(child -> {
				Shape childShape = null;
				if (child instanceof SVGGElementImpl) {
					childShape = ((SVGGElementImpl) child).createShape(transform);
				} else if (child instanceof SVGAElementImpl) {
					childShape = ((SVGAElementImpl) child).createShape(transform);
				} else if (child instanceof SVGImageElementImpl) {
					SVGRect bbox = ((SVGImageElement) child).getBBox();
					childShape = new Rectangle2D.Float(bbox.getX(), bbox.getY(), bbox.getWidth(), bbox.getHeight());
				} else if (child instanceof SVGUseElementImpl) {
					SVGRect bbox = ((SVGUseElement) child).getBBox();
					childShape = new Rectangle2D.Float(bbox.getX(), bbox.getY(), bbox.getWidth(), bbox.getHeight());
				} else if (child instanceof SVGSVGElementImpl) {
					SVGSVGElement svg = (SVGSVGElement) child;
					AffineTransform ctm = AffineTransform.getTranslateInstance(viewport.getX(), viewport.getY());
					if (viewboxToViewportTransform != null) {
						ctm.concatenate(viewboxToViewportTransform);
					}
					AffineTransform inverseTransform;
					try {
						inverseTransform = ctm.createInverse();
					} catch (NoninvertibleTransformException e) {
						inverseTransform = null;
					}
					float x = ((SVGLengthImpl) svg.getX()).getTransformedLength(inverseTransform);
					float y = ((SVGLengthImpl) svg.getY()).getTransformedLength(inverseTransform);
					float width = ((SVGLengthImpl) svg.getWidth()).getTransformedLength(inverseTransform);
					float height = ((SVGLengthImpl) svg.getHeight()).getTransformedLength(inverseTransform);

					childShape = new Rectangle2D.Float(x, y, width, height);
				}
				// transform the child shapae
				if (child instanceof SVGTransformable) {
					SVGAnimatedTransformList childTransformList = ((SVGTransformable) child).getTransform();
					if (childTransformList != null) {
						AffineTransform childTransform = ((SVGTransformListImpl) childTransformList.getAnimVal()).getAffineTransform();
						childShape = childTransform.createTransformedShape(childShape);
					}
				}
				if (childShape != null) {
					path.append(childShape, false);
				}
			});
		}
		return path;
	}
	
	private void drawChildren(Graphics2D graphics) {
		List<Node> drawableChildren = new ArrayList<>();
		if (hasChildNodes()) {
			NodeListImpl children = (NodeListImpl)getChildNodes();
			children.forEach(child -> {
				if (child instanceof Drawable) {
					drawableChildren.add(child);
				}
			});
		}
		for (Node node : drawableChildren) {
			SVGElement selem = (SVGElement)node;
			selem.setOwnerSVGElement(this);
			drawStyle(node);
			Drawable child = (Drawable) node;
			child.draw(graphics);
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object SVGSVGElement]";
	}
}
