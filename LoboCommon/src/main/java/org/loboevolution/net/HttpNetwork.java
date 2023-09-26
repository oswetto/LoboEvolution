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

package org.loboevolution.net;

import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLImageElement;
import org.loboevolution.html.dom.HTMLInputElement;
import org.loboevolution.html.dom.svg.SVGImageElement;
import org.loboevolution.info.TimingInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

/**
 * <p>HttpNetwork class.</p>
 */
public class HttpNetwork {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(HttpNetwork.class.getName());

	/** Constant GZIP_ENCODING="gzip" */
	public static final String GZIP_ENCODING = "gzip";
	
	/** Constant TIMEOUT_VALUE="2000" */
	public static final int TIMEOUT_VALUE = 2000;

	private static InputStream getGzipStream(final URLConnection con) throws IOException {
		final InputStream cis = con.getInputStream();
		if (cis != null) {
			if (GZIP_ENCODING.equals(con.getContentEncoding())) {
				return new GZIPInputStream(con.getInputStream());
			} else {
				return con.getInputStream();
			}
		} else {
			return null;
		}
	}

	private static InputStream getGzipStreamError(final HttpURLConnection con) throws IOException {
		final InputStream cis = con.getErrorStream();
		if (cis != null) {
			if (GZIP_ENCODING.equals(con.getContentEncoding())) {
				return new GZIPInputStream(con.getErrorStream());
			} else {
				return con.getErrorStream();
			}
		} else {
			return null;
		}
	}

	/**
	 * <p>getInputStream.</p>
	 *
	 * @param connection a {@link java.net.URLConnection} object.
	 * @return a {@link java.io.InputStream} object.
	 * @throws java.io.IOException if any.
	 */
	public static InputStream getInputStream(final URLConnection connection) throws IOException {
		InputStream in;
		if (connection instanceof HttpURLConnection) {
			in = getGzipStreamError((HttpURLConnection) connection);
			if (in == null) {
				in = getGzipStream(connection);
			}
		} else {
			in = connection.getInputStream();
		}
		return in;
	}

	/**
	 * <p>getImage.</p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.HTMLElement} object.
	 * @param useBaseUri a {@link java.lang.Boolean} object.
	 * @return a {@link java.awt.Image} object.
	 */
	public static Image getImage(final HTMLElement element, final TimingInfo info, final boolean useBaseUri) {
		final Instant start = Instant.now();
		String href = null;
		if(element instanceof HTMLImageElement) {
			href = ((HTMLImageElement) element).getSrc();
		} else if(element instanceof HTMLInputElement){
			href = ((HTMLInputElement) element).getSrc();
		} else if(element instanceof SVGImageElement){
			href = ((SVGImageElement) element).getHref().getBaseVal();
		}

		final String baseUri = useBaseUri ? element.getBaseURI() : null;
		try {
			if (Strings.isBlank(href))
				return null;

			if (href.contains(";base64,")) {
				final String base64 = href.split(";base64,")[1];
				final byte[] decodedBytes = Base64.getDecoder().decode(Strings.linearize(base64));
				try (final InputStream stream = new ByteArrayInputStream(decodedBytes)) {
					return ImageIO.read(stream);
				}
			} else {
				String scriptURI = href;
				if (Strings.isNotBlank(baseUri)) {
					final URL baseURL = new URL(baseUri);
					final URL scriptURL = Urls.createURL(baseURL, href);
					scriptURI = scriptURL == null ? href : scriptURL.toExternalForm();
				}

				info.setPath(scriptURI);
				final URL u = new URL(scriptURI);
				info.setName(u.getFile());
				URLConnection connection = u.openConnection();
				if (connection instanceof HttpURLConnection) {
					final HttpURLConnection conn =(HttpURLConnection)u.openConnection();
					conn.setRequestProperty("User-Agent", UserAgent.getUserAgent());
					conn.getHeaderField("Set-Cookie");
					info.setHttpResponse(conn.getResponseCode());
					connection = conn;
				}

				try (final InputStream in = HttpNetwork.openConnectionCheckRedirects(connection)) {
					info.setType(connection.getContentType());

					if (href.contains(";base64,")) {
						final String base64 = href.split(";base64,")[1];
						final byte[] decodedBytes = Base64.getDecoder().decode(base64);
						final InputStream stream = new ByteArrayInputStream(decodedBytes);
						return ImageIO.read(stream);
					} else if (href.endsWith(".svg")) {
						return null; //TODO SVG From URL
					} else if (href.startsWith("https")) {
						if (in != null) {
							final BufferedImage bi = ImageIO.read(in);
							if (bi != null) {
								return Toolkit.getDefaultToolkit().createImage(bi.getSource());
							}
						}
						return null;
					} else if (href.endsWith(".gif")) {
						try {
							return new ImageIcon(u).getImage();
						} catch (final Exception e) {
							return ImageIO.read(in);
						}
					} else if (href.endsWith(".bmp")) {
						try {
							return ImageIO.read(in);
						} catch (final IOException e) {
							logger.log(Level.SEVERE, e.getMessage(), e);
						}
					} else {
						return ImageIO.read(in);
					}
				} catch (final SocketTimeoutException e) {
					if (connection instanceof HttpURLConnection) {
						info.setHttpResponse(((HttpURLConnection)connection).getResponseCode());
					}

					logger.log(Level.SEVERE, "More than " + TIMEOUT_VALUE + " elapsed.");
				} catch (final FileNotFoundException e) {
					logger.log(Level.INFO, e.getMessage());
				}
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			final Instant finish = Instant.now();
			final long timeElapsed = Duration.between(start, finish).toMillis();
			info.setTimeElapsed(timeElapsed);
		}
		return null;
	}

	/**
	 * <p>getSource.</p>
	 *
	 * @param uri a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 * @throws java.lang.Exception if any.
	 */
	public static String getSource(final String uri) throws Exception {

		final URL url = new URL(uri);
		final URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent", UserAgent.getUserAgent());
		connection.getHeaderField("Set-Cookie");
		try (final InputStream in = openConnectionCheckRedirects(connection)) {
			return toString(in);
		} catch (final SocketTimeoutException e) {
			logger.log(Level.SEVERE, "More than " + TIMEOUT_VALUE + " elapsed.");
	    }
		return "";
	}

	public static String sourceResponse(final String sUri, final String type) {
		URL url;
		String scriptURI = sUri;
		try {
			if ("CSS".equals(type)) {
				try {
					if (scriptURI.startsWith("//")) {
						scriptURI = "http:" + scriptURI;
					}
					url = new URL(scriptURI);
				} catch (final MalformedURLException mfu) {
					final int idx = scriptURI.indexOf(':');
					if (idx == -1 || idx == 1) {
						url = new URL("file:" + scriptURI);
					} else {
						throw mfu;
					}
				}
			} else {
				url = new URL(scriptURI);
				final URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
				url = uri.toURL();
			}

			return getSource(url.toString());
		} catch (final Exception err) {
			logger.log(Level.SEVERE, err.getMessage(), err);
			return "";
		}
	}

	/**
	 * <p>openConnectionCheckRedirects.</p>
	 *
	 * @param c a {@link java.net.URLConnection} object.
	 * @return a {@link java.io.InputStream} object.
	 * @throws java.lang.Exception if any.
	 */
	public static InputStream openConnectionCheckRedirects(URLConnection c) throws Exception {
		boolean redir;
		int redirects = 0;
		InputStream in;

		if (c.getConnectTimeout() == 0) {
			c.setConnectTimeout(TIMEOUT_VALUE);
			c.setReadTimeout(TIMEOUT_VALUE);
		}

		do {
			if (c instanceof HttpURLConnection) {
				((HttpURLConnection) c).setInstanceFollowRedirects(false);
			}
			in = getInputStream(c);
			redir = false;
			if (c instanceof HttpURLConnection) {
				final HttpURLConnection http = (HttpURLConnection) c;
				final int stat = http.getResponseCode();
				if (stat >= 300 && stat <= 307 && stat != 306 && stat != HttpURLConnection.HTTP_NOT_MODIFIED) {
					final URL base = http.getURL();
					final String loc = http.getHeaderField("Location");
					URL target = null;
					if (loc != null) {
						target = new URL(base, loc);
					}
					http.disconnect();
					if (target == null || !(target.getProtocol().equals("http") || target.getProtocol().equals("https"))
							|| redirects >= 5) {
						throw new SecurityException("illegal URL redirect");
					}
					redir = true;
					c = target.openConnection();
					redirects++;
				}
			}
		} while (redir);
		return in;
	}

	/**
	 * <p>toString.</p>
	 *
	 * @param inputStream a {@link java.io.InputStream} object.
	 * @return a {@link java.lang.String} object.
	 * @throws java.io.IOException if any.
	 */
	public static String toString(final InputStream inputStream) throws IOException {
		final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		final Stream<String> lines = new BufferedReader(inputStreamReader).lines();
		final String text = lines.collect(Collectors.joining("\n"));
		return removeNonASCIIChar(text);
	}
	
	private static String removeNonASCIIChar(final String str) {
		final StringBuilder buff = new StringBuilder();
		final char[] chars = str.toCharArray();
		for (final char c : chars) {
			if (0 < c && c < 127) {
				buff.append(c);
			}
		}
		return buff.toString();
	}
}
