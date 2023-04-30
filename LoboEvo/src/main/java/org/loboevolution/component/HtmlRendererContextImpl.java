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
/*
 * Created on Oct 22, 2005
 */
package org.loboevolution.component;

import org.loboevolution.common.RecordedInputStream;
import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.config.HtmlRendererConfigImpl;
import org.loboevolution.gui.HtmlContextMenu;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLLinkElement;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLImageElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLLinkElementImpl;
import org.loboevolution.html.dom.input.FormInput;
import org.loboevolution.html.parser.DocumentBuilderImpl;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.img.ImageViewer;
import org.loboevolution.info.BookmarkInfo;
import org.loboevolution.net.HttpNetwork;
import org.loboevolution.net.UserAgent;
import org.loboevolution.pdf.PDFViewer;
import org.loboevolution.store.GeneralStore;
import org.loboevolution.store.LinkStore;
import org.loboevolution.store.NavigationStore;
import org.loboevolution.store.TabStore;
import org.xml.sax.InputSource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The HtmlRendererContextImpl class implements the
 * {@link HtmlRendererContextImpl} interface. Note that this
 * class provides rudimentary implementations of most callback methods.
 * Overridding some methods in this class will usually be necessary in a
 * professional application.
 * <p>
 * A simple way to load a URL into the {@link HtmlPanel} of the renderer context
 * is to invoke {@link #navigate(String)}.
 */
public class HtmlRendererContextImpl implements HtmlRendererContext {

	private static final Logger logger = Logger.getLogger(HtmlRendererContextImpl.class.getName());

	private UserAgentContext bcontext;

	protected URLConnection currentConnection;

	private HtmlPanel htmlPanel;

	private volatile HtmlRendererContext opener;

	private final HtmlRendererContext parentRcontext;

	private final HtmlRendererConfig config;

	private double scrollx;

	private double scrolly;

	/**
	 * Constructs a HtmlRendererContextImpl that is a child of another
	 * {@link HtmlRendererContextImpl}.
	 *
	 * @param parentRcontext   The parent's renderer context.
	 * @param htmlPanel a {@link HtmlPanel} object.
	 * @param config a {@link HtmlRendererConfig} object.
	 */
	public HtmlRendererContextImpl(HtmlPanel htmlPanel, HtmlRendererContext parentRcontext, HtmlRendererConfig config) {
		this.htmlPanel = htmlPanel;
		this.parentRcontext = parentRcontext;
		this.bcontext = parentRcontext == null ? null : parentRcontext.getUserAgentContext();
		this.config = config;
	}

	/**
	 * Constructs a HtmlRendererContextImpl.
	 *
	 * @param htmlPanel a {@link HtmlPanel} object.
	 * @param ucontext a {@link UserAgentContext} object.
	 * @param config a {@link HtmlRendererConfig} object.
	 */
	public HtmlRendererContextImpl(HtmlPanel htmlPanel, UserAgentContext ucontext, HtmlRendererConfig config) {
		this.htmlPanel = htmlPanel;
		this.parentRcontext = null;
		this.bcontext = ucontext;
		this.config = config;
	}

	/** {@inheritDoc} */
	@Override
	public void alert(String message) {
		JOptionPane.showMessageDialog(this.htmlPanel, message);
	}

	/** {@inheritDoc} */
	@Override
	public void back() {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		final ITabbedPane tabbedPane = bpanel.getTabbedPane();
		final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
		final IToolBar toolbar = browserFrame.getToolbar();
		final JTextField addressBar = toolbar.getAddressBar();
		String url = addressBar.getText();
		tabbedPane.setComponentPopupMenu(bpanel);
		NavigationStore nh = new NavigationStore();
        final int indexPanel = tabbedPane.getSelectedIndex();
        List<BookmarkInfo> tabsById = nh.getRecentHost(indexPanel, true);
		for (int i = 0; i < tabsById.size(); i++) {
			BookmarkInfo info = tabsById.get(i);
			String tab = info.getUrl();
			if (tab.equals(url) && i > 0) {
				url = tabsById.get(i - 1).getUrl();
			}
		}

        final HtmlPanel hpanel = NavigatorFrame.createHtmlPanel(bpanel, url);
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
        tabbedPane.remove(indexPanel);
        tabbedPane.insertTab(title, null, htmlPanel, title, indexPanel);
		
        browserFrame.getToolbar().getAddressBar().setText(url);
        bpanel.getScroll().getViewport().add((Component)tabbedPane);
        
        TabStore.deleteTab(indexPanel);
        TabStore.insertTab(indexPanel, url, title);
	}

	/** {@inheritDoc} */
	@Override
	public void blur() {
		this.warn("back(): Not overridden");
	}

	/** {@inheritDoc} */
	@Override
	public void close() {
		this.warn("close(): Not overridden");
	}

	/** {@inheritDoc} */
	@Override
	public boolean confirm(String message) {
		final int retValue = JOptionPane.showConfirmDialog(this.htmlPanel, message, "Confirm",
				JOptionPane.YES_NO_OPTION);
		return retValue == JOptionPane.YES_OPTION;
	}

	/** {@inheritDoc} */
	@Override
	public void error(String message) {
		if (logger.isLoggable(Level.SEVERE)) {
			logger.log(Level.SEVERE, message);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void error(String message, Throwable throwable) {
		if (logger.isLoggable(Level.SEVERE)) {
			logger.log(Level.SEVERE, message, throwable);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void focus() {
		this.warn("focus(): Not overridden");
	}

	/** {@inheritDoc} */
	@Override
	public void forward() {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		final ITabbedPane tabbedPane = bpanel.getTabbedPane();
		final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
		final IToolBar toolbar = browserFrame.getToolbar();
		final JTextField addressBar = toolbar.getAddressBar();
		String url = addressBar.getText();
		tabbedPane.setComponentPopupMenu(bpanel);
		NavigationStore nh = new NavigationStore();
        final int indexPanel = tabbedPane.getSelectedIndex();
        List<BookmarkInfo> tabsById = nh.getRecentHost(indexPanel, true);
		for (int i = 0; i < tabsById.size(); i++) {
			BookmarkInfo info = tabsById.get(i);
			String tab = info.getUrl();
			if (tab.equals(url) && i < tabsById.size() - 1) {
				url = tabsById.get(i + 1).getUrl();
			}
		}

        final HtmlPanel hpanel = NavigatorFrame.createHtmlPanel(bpanel, url);
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
        tabbedPane.remove(indexPanel);
        tabbedPane.insertTab(title, null, htmlPanel, title, indexPanel);
		
        browserFrame.getToolbar().getAddressBar().setText(url);
        bpanel.getScroll().getViewport().add((Component)tabbedPane);
        
        TabStore.deleteTab(indexPanel);
        TabStore.insertTab(indexPanel, url, title);
	}

	/** {@inheritDoc} */
	@Override
	public String getCurrentURL() {
		HtmlPanel html = htmlPanel;
		IBrowserPanel panel = html.getBrowserPanel();
		if (panel != null) {
			IBrowserFrame frame = panel.getBrowserFrame();
			IToolBar toolbar = frame.getToolbar();
			JTextField jtf = toolbar.getAddressBar();
			return jtf.getText();
		} else {
			return "";
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getDefaultStatus() {
		this.warn("getDefaultStatus(): Not overridden");
		return "";
	}

	/** {@inheritDoc} */
	@Override
	public HtmlPanel getHtmlPanel() {
		return this.htmlPanel;
	}

	/** {@inheritDoc} */
	@Override
	public HtmlRendererContext getOpener() {
		return this.opener;
	}

	/** {@inheritDoc} */
	@Override
	public HtmlRendererContext getParent() {
		return this.parentRcontext;
	}

	/** {@inheritDoc} */
	@Override
	public HtmlRendererContext getTop() {
		final HtmlRendererContext ancestor = this.parentRcontext;
		if (ancestor == null) {
			return this;
		}
		return ancestor.getTop();
	}

	/** {@inheritDoc} */
	@Override
	public UserAgentContext getUserAgentContext() {
		synchronized (this) {
			if (this.bcontext == null) {
				this.warn("getUserAgentContext(): UserAgentContext not provided in constructor. Creating a simple one.");
				this.bcontext = new UserAgentContext(config);
			}
			return this.bcontext;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean isClosed() {
		this.warn("isClosed(): Not overridden");
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isVisitedLink(HTMLLinkElement link) {
		return LinkStore.isVisited(link.getHref());
	}


	/** {@inheritDoc} */
	@Override
	public void linkClicked(URL url, boolean isNewTab) {
		String fullURL = url.toString();
		if (fullURL.endsWith(".pdf")) {
			PDFViewer viewer = new PDFViewer(true);
			viewer.doOpen(fullURL);
		} else {
			final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
			final ITabbedPane tabbedPane = bpanel.getTabbedPane();
			tabbedPane.setComponentPopupMenu(bpanel);
			int index;

			if (isNewTab) {
				index = TabStore.getTabs().size();
			} else {
				index = tabbedPane.getIndex();
			}

			final HtmlPanel hpanel = NavigatorFrame.createHtmlPanel(bpanel, fullURL);
			final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
			final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
			if (!isNewTab) {
				tabbedPane.remove(index);
			}
			tabbedPane.insertTab(title, null, hpanel, title, index);
			tabbedPane.setSelectedIndex(index);
			final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
			browserFrame.getToolbar().getAddressBar().setText(fullURL);
			TabStore.insertTab(index, fullURL, title);
			LinkStore.insertLinkVisited(fullURL);
			bpanel.getScroll().getViewport().add((Component)tabbedPane);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void moveInHistory(int offset) {
		if (logger.isLoggable(Level.WARNING)) {
			logger.log(Level.WARNING, "moveInHistory() does nothing, unless overridden.");
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getPreviousURL() {
		if (logger.isLoggable(Level.WARNING)) {
			logger.log(Level.WARNING, "getPreviousURL() does nothing, unless overridden.");
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getNextURL() {
		if (logger.isLoggable(Level.WARNING)) {
			logger.log(Level.WARNING, "getNextURL() does nothing, unless overridden.");
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public int getHistoryLength() {
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void navigate(String fullURL) throws Exception {
		final URL href = Urls.createURL(null, fullURL);
		this.navigate(href, "_this");
	}

	/** {@inheritDoc} */
	@Override
	public void navigate(final URL href, String target) {
		submitForm("GET", href, target, null, null);
	}

	/** {@inheritDoc} */
	@Override
	public boolean onContextMenu(HTMLElement element, MouseEvent event) {
		HTMLElementImpl elem = (HTMLElementImpl) element;
		HTMLImageElementImpl elmImg = new HTMLImageElementImpl();
		if (elem.getCurrentStyle() != null && Strings.isNotBlank(elem.getCurrentStyle().getBackgroundImage())) {
			final String backgroundImageText = elem.getCurrentStyle().getBackgroundImage();
			String start = "url(";
			int startIdx = start.length() + 1;
			int closingIdx = backgroundImageText.lastIndexOf(')') - 1;
			String quotedUri = backgroundImageText.substring(startIdx, closingIdx);
			elmImg.setSrc(quotedUri);
			element = elmImg;
		}
		HtmlContextMenu menu = new HtmlContextMenu(element, this);
		
		if (element instanceof HTMLImageElementImpl) {
			JPopupMenu popupMenuImage = menu.popupMenuImage(htmlPanel.getBrowserPanel());
			popupMenuImage.show(event.getComponent(), event.getX(), event.getY());
			return false;
		} else if (element instanceof HTMLLinkElementImpl) {
			JPopupMenu popupMenuLink = menu.popupMenuLink(htmlPanel.getBrowserPanel());
			popupMenuLink.show(event.getComponent(), event.getX(), event.getY());
			return false;
		} else if (element instanceof HTMLElementImpl) {
			JPopupMenu popupMenuAbstract = menu.popupMenuAbstractUI();
			popupMenuAbstract.show(event.getComponent(), event.getX(), event.getY());
			return false;
		}
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public HtmlRendererContext open(URL url, String windowName, String windowFeatures, boolean replace) {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		final ITabbedPane tabbedPane = bpanel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(bpanel);
		int index = TabStore.getTabs().size();
		String fullURL = url.toString();
		final HtmlPanel hpanel = NavigatorFrame.createHtmlPanel(bpanel, fullURL);
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
		tabbedPane.insertTab(title, null, hpanel, title, index);
		tabbedPane.setSelectedIndex(index);
		final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
		browserFrame.getToolbar().getAddressBar().setText(fullURL);
		TabStore.insertTab(index, fullURL, title);
		LinkStore.insertLinkVisited(fullURL);
		bpanel.getScroll().getViewport().add((Component)tabbedPane);
		return nodeImpl.getHtmlRendererContext();
	}


	/** {@inheritDoc} */
	@Override
	public void openImageViewer(URL srcUrl) {
		try {
			final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
			final ITabbedPane tabbedPane = bpanel.getTabbedPane();
			final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
			final BufferedImage img = ImageIO.read(srcUrl);
			final ImageViewer viewer = new ImageViewer(img);
			final String title = "Image Viewer";
			String fullURL = srcUrl.toString();
			int index = TabStore.getTabs().size();
			JPanel jPanel = new JPanel();
			jPanel.add(viewer.getComponent());
			browserFrame.getToolbar().getAddressBar().setText(fullURL);
			tabbedPane.insertTab(title, null, viewer.getComponent(), title, index);
			TabStore.insertTab(index, fullURL, title);
			LinkStore.insertLinkVisited(fullURL);
			bpanel.getScroll().getViewport().add((Component)tabbedPane);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void openImageViewer(String fullURL, InputStream stream) {
		try {
			final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
			final ITabbedPane tabbedPane = bpanel.getTabbedPane();
			final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
			final BufferedImage img = ImageIO.read(stream);
			final ImageViewer viewer = new ImageViewer(img);
			final String title = "Image Viewer";
			int index = TabStore.getTabs().size();
			JPanel jPanel = new JPanel();
			jPanel.add(viewer.getComponent());
			browserFrame.getToolbar().getAddressBar().setText(fullURL);
			tabbedPane.insertTab(title, null, viewer.getComponent(), title, index);
			bpanel.getScroll().getViewport().add((Component)tabbedPane);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public String prompt(String message, String inputDefault) {
		return JOptionPane.showInputDialog(this.htmlPanel, message);
	}

	/** {@inheritDoc} */
	@Override
	public void reload() {
		final HTMLDocumentImpl document = (HTMLDocumentImpl) this.htmlPanel.getRootNode();
		if (document != null) {
			try {
				final URL url = new URL(document.getDocumentURI());
				this.navigate(url, null);
			} catch (final MalformedURLException throwable) {
				this.warn("reload(): Malformed URL", throwable);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void resizeBy(double byWidth, double byHeight) {
		final Window window = getWindow(this.htmlPanel);
		if (window != null) {
			window.setSize(window.getWidth() + (int)byWidth, window.getHeight() + (int)byHeight);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void resizeTo(double width, double height) {
		final Window window = getWindow(this.htmlPanel);
		if (window != null) {
			window.setSize((int)width, (int)height);
		}
	}

	/** {@inheritDoc} */
	@Override
	public int getInnerHeight() {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		if (bpanel != null && bpanel.getHeight() > 0) {
			return bpanel.getHeight();
		}
		final Rectangle initialWindowBounds = GeneralStore.getInitialWindowBounds();
		return Double.valueOf(initialWindowBounds.getHeight()).intValue();
	}

	/** {@inheritDoc} */
	@Override
	public int getInnerWidth() {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		if (bpanel != null && bpanel.getWidth() > 0) {
			return bpanel.getWidth();
		}
		final Rectangle initialWindowBounds = GeneralStore.getInitialWindowBounds();
		return Double.valueOf(initialWindowBounds.getWidth()).intValue();
	}

	/** {@inheritDoc} */
	@Override
	public int getOuterHeight() {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		if (bpanel != null && bpanel.getHeight() > 0) {
			return bpanel.getHeight();
		}
		final Rectangle initialWindowBounds = GeneralStore.getInitialWindowBounds();
		return Double.valueOf(initialWindowBounds.getHeight()).intValue();
	}

	/** {@inheritDoc} */
	@Override
	public int getOuterWidth() {
		final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
		if (bpanel != null && bpanel.getWidth() > 0) {
			return bpanel.getWidth();
		}
		final Rectangle initialWindowBounds = GeneralStore.getInitialWindowBounds();
		return Double.valueOf(initialWindowBounds.getWidth()).intValue();
	}


	/** {@inheritDoc} */
	@Override
	public void scroll(double x, double y) {
		this.htmlPanel.scroll(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public void scrollBy(double x, double y) {
		this.htmlPanel.scrollBy(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public double getScrollx() {
		return scrollx;
	}

	/** {@inheritDoc} */
	@Override
	public void setScrollx(double scrollx) {
		this.scrollx = scrollx;
	}

	/** {@inheritDoc} */
	@Override
	public double getScrolly() {
		return scrolly;
	}

	/** {@inheritDoc} */
	@Override
	public void setScrolly(double scrolly) {
		this.scrolly = scrolly;
	}

	/** {@inheritDoc} */
	@Override
	public void setDefaultStatus(String message) {
		this.warn("setDefaultStatus(): Not overridden.");
	}

	/** {@inheritDoc} */
	@Override
	public void setHtmlPanel(HtmlPanel panel) {
		this.htmlPanel = panel;
	}

	/** {@inheritDoc} */
	@Override
	public void setOpener(HtmlRendererContext opener) {
		this.opener = opener;
	}

	/** {@inheritDoc} */
	@Override
	public void setStatus(String message) {
		this.warn("setStatus(): Not overridden");
	}

	/** {@inheritDoc} */
	@Override
    public void setCursor(Optional<Cursor> cursorOpt) {
        Cursor cursor = cursorOpt.orElse(Cursor.getDefaultCursor());
        htmlPanel.setCursor(cursor);
    }

	/** {@inheritDoc} */
	@Override
	public void submitForm(final String method, final URL action, final String target, final String enctype, final FormInput[] formInputs) {
		if (target != null) {
			final String actualTarget = target.trim().toLowerCase();
			switch (actualTarget) {
				case "_top":
					getTop().navigate(action, null);
					break;
				case "_parent":
					final HtmlRendererContext parent = getParent();
					if (parent != null) {
						parent.navigate(action, null);
						return;
					}
					break;
				case "_blank":
					open(action, "cobra.blank", "", false);
					break;
				default:
					break;
			}
		}

		try {
			final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
			final ITabbedPane tabbedPane = bpanel.getTabbedPane();
			final int indexPanel = tabbedPane.getSelectedIndex();
			final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
			final HtmlPanel hpanel = NavigatorFrame.createHtmlPanel(bpanel, action.toString());
			final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
			final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
			tabbedPane.remove(indexPanel);
			tabbedPane.insertTab(title, null, htmlPanel, title, indexPanel);
			browserFrame.getToolbar().getAddressBar().setText(action.toString());
			bpanel.getScroll().getViewport().add((Component)tabbedPane);
			submitFormSync(method, action, target, enctype, formInputs);
		} catch (final Exception err) {
			error("navigate(): Error loading or parsing request.", err);
		}

	}

	/** {@inheritDoc} */
	@Override
	public void warn(String message) {
		if (logger.isLoggable(Level.WARNING)) {
			logger.log(Level.WARNING, message);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void warn(String message, Throwable throwable) {
		if (logger.isLoggable(Level.WARNING)) {
			logger.log(Level.WARNING, message, throwable);
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getStatus() {
		this.warn("getStatus(): Not overridden");
		return "";
	}

	private static Window getWindow(Component c) {
		Component current = c;
		while (current != null && !(current instanceof Window)) {
			current = current.getParent();
		}
		return (Window) current;
	}

	private HTMLDocumentImpl createDocument(InputSource inputSource) throws Exception {
		final HtmlRendererConfig config = new HtmlRendererConfigImpl();
		final DocumentBuilderImpl builder = new DocumentBuilderImpl(getUserAgentContext(), this, config);
		return (HTMLDocumentImpl) builder.createDocument(inputSource);
	}

	private Charset getDocumentCharset(URLConnection connection) {
		final String encoding = Urls.getCharset(connection);
		return encoding == null ? StandardCharsets.UTF_8 : Charset.forName(encoding);
	}

	private Proxy getProxy() {
		return Proxy.NO_PROXY;
	}

	private void submitFormSync(final String method, final java.net.URL action, final String target, String enctype,
								final FormInput[] formInputs) throws Exception {
		final String actualMethod = method.toUpperCase();
		URL resolvedURL;
		if ("GET".equals(actualMethod) && formInputs != null) {
			boolean firstParam = true;
			final URL noRefAction = new URL(action.getProtocol(), action.getHost(), action.getPort(), action.getFile());
			final StringBuilder newUrlBuffer = new StringBuilder(noRefAction.toExternalForm());
			if (action.getQuery() == null) {
				newUrlBuffer.append("?");
			} else {
				newUrlBuffer.append("&");
			}
			for (final FormInput parameter : formInputs) {
				final String name = parameter.getName();
				final String encName = URLEncoder.encode(name, "UTF-8");
				if (parameter.isText()) {
					if (firstParam) {
						firstParam = false;
					} else {
						newUrlBuffer.append("&");
					}
					final String valueStr = parameter.getTextValue();
					final String encValue = URLEncoder.encode(valueStr, "UTF-8");
					newUrlBuffer.append(encName);
					newUrlBuffer.append("=");
					newUrlBuffer.append(encValue);
				} else {
					logger.warning("postData(): Ignoring non-textual parameter " + name + " for GET.");
				}
			}
			resolvedURL = new java.net.URL(newUrlBuffer.toString());
		} else {
			resolvedURL = action;
		}
		URL urlForLoading;
		if (resolvedURL.getProtocol().equalsIgnoreCase("file")) {
			// Remove query so it works.
			try {
				final String ref = action.getRef();
				final String refText = Strings.isCssBlank(ref) ? "" : "#" + ref;
				urlForLoading = new URL(resolvedURL.getProtocol(), action.getHost(), action.getPort(),
						action.getPath() + refText);
			} catch (final java.net.MalformedURLException throwable) {
				this.warn("malformed", throwable);
				urlForLoading = action;
			}
		} else {
			urlForLoading = resolvedURL;
		}
		if (logger.isLoggable(Level.INFO)) {
			logger.info("process(): Loading URI=[" + urlForLoading + "].");
		}
		System.currentTimeMillis();
		// Using potentially different URL for loading.
		final Proxy proxy = getProxy();
		final boolean isPost = "POST".equals(actualMethod);
		final URLConnection connection = proxy == null || proxy == Proxy.NO_PROXY ? urlForLoading.openConnection()
				: urlForLoading.openConnection(proxy);
		this.currentConnection = connection;
		try {
			connection.setRequestProperty("User-Agent", UserAgent.getUserAgent());
			connection.getHeaderField("Set-Cookie");
			if (connection instanceof HttpURLConnection) {
				final HttpURLConnection hc = (HttpURLConnection) connection;
				hc.setRequestMethod(actualMethod);
				hc.setInstanceFollowRedirects(false);
			}
			if (isPost) {
				connection.setDoOutput(true);
				final ByteArrayOutputStream bufOut = new ByteArrayOutputStream();
				boolean firstParam = true;
				if (formInputs != null) {
					for (final FormInput parameter : formInputs) {
						final String name = parameter.getName();
						final String encName = URLEncoder.encode(name, "UTF-8");
						if (parameter.isText()) {
							if (firstParam) {
								firstParam = false;
							} else {
								bufOut.write((byte) '&');
							}
							final String valueStr = parameter.getTextValue();
							final String encValue = URLEncoder.encode(valueStr, "UTF-8");
							bufOut.write(encName.getBytes(StandardCharsets.UTF_8));
							bufOut.write((byte) '=');
							bufOut.write(encValue.getBytes(StandardCharsets.UTF_8));
						} else {
							logger.warning("postData(): Ignoring non-textual parameter " + name + " for POST.");
						}
					}
				}
				// Do not add a line break to post content. Some servers
				// can be picky about that (namely, java.net).
				final byte[] postContent = bufOut.toByteArray();
				if (connection instanceof HttpURLConnection) {
					((HttpURLConnection) connection).setFixedLengthStreamingMode(postContent.length);
				}
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				// connection.setRequestProperty("Content-Length",
				// String.valueOf(postContent.length));
				final OutputStream postOut = connection.getOutputStream();
				postOut.write(postContent);
				postOut.flush();
			}
			if (connection instanceof HttpURLConnection) {
				final HttpURLConnection hc = (HttpURLConnection) connection;
				final int responseCode = hc.getResponseCode();
				if (logger.isLoggable(Level.INFO)) {
					logger.info("process(): HTTP response code: " + responseCode);
				}
				if (responseCode == HttpURLConnection.HTTP_MOVED_PERM
						|| responseCode == HttpURLConnection.HTTP_MOVED_TEMP
						|| responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
					final String location = hc.getHeaderField("Location");
					if (location == null) {
						logger.warning("No Location header in redirect from " + action + ".");
					} else {
						URL href;
						href = Urls.createURL(action, location);
						navigate(href, target);
					}
					return;
				}
			}
			try (InputStream in = HttpNetwork.openConnectionCheckRedirects(connection)) {
				final RecordedInputStream rin = new RecordedInputStream(in, 1000000);
				final InputStream bin = new BufferedInputStream(rin, 8192);
				final String actualURI = urlForLoading.toExternalForm();
				// Only create document, don't parse.
				final HTMLDocumentImpl document = createDocument(
						new InputSourceImpl(bin, actualURI, getDocumentCharset(connection)));
				// Set document in HtmlPanel. Safe to call outside GUI thread.
				final HtmlPanel panel = this.htmlPanel;
				panel.setDocument(document, HtmlRendererContextImpl.this);
				// Now start loading.
				document.load();
				final String ref = urlForLoading.getRef();
				if (ref != null && ref.length() != 0) {
					panel.scrollToElement(ref);
				}
			} catch (SocketTimeoutException e) {
				logger.log(Level.SEVERE, "More than " + connection.getConnectTimeout() + " elapsed.");
			}
		} finally {
			this.currentConnection = null;
		}
	}

	/**
	 * <p>isTest.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isTestEnabled() {
		return false;
	}
}