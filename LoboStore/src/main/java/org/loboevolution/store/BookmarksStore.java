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

package org.loboevolution.store;

import org.loboevolution.common.Strings;
import org.loboevolution.info.BookmarkInfo;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Class BookmarksStore.
 */
public class BookmarksStore implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(BookmarksStore.class.getName());

	/** The Constant DB_PATH. */
	private static final String DB_PATH = DatabseSQLite.getDatabaseDirectory();

	private final String DELETE_BOOKMARKS = "DELETE FROM BOOKMARKS";
	
	private final String DELETE_BOOKMARKS_BY_URL = "DELETE FROM BOOKMARKS WHERE baseUrl = ?";

	private final String INSERT_BOOKMARKS = "INSERT INTO BOOKMARKS (name, description, baseUrl, tags) VALUES(?,?,?,?)";

	/**
	 * <p>deleteBookmarks.</p>
	 */
	public void deleteBookmarks() {
		try (Connection conn = DriverManager.getConnection(DB_PATH);
				PreparedStatement pstmt = conn.prepareStatement(this.DELETE_BOOKMARKS)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	/**
	 * <p>deleteBookmarks.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 */
	public void deleteBookmark(String url) {
		try (Connection conn = DriverManager.getConnection(DB_PATH);
				PreparedStatement pstmt = conn.prepareStatement(this.DELETE_BOOKMARKS_BY_URL)) {
			pstmt.setString(1, url.trim());
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * Gets the bookmarks
	 *
	 * @return bookmarks
	 * @param num a {@link java.lang.Integer} object.
	 */
	public List<BookmarkInfo> getBookmarks(Integer num) {
		synchronized (this) {
			final List<BookmarkInfo> values = new ArrayList<>();
			if (DatabseSQLite.storeExist()) {
				String query = "SELECT name, description, baseUrl, tags FROM BOOKMARKS";

				if (num != null) {
					query = query + " LIMIT " + num;
				}
				try (Connection conn = DriverManager.getConnection(DB_PATH);
					 Statement stmt = conn.createStatement();
					 ResultSet rs = stmt.executeQuery(query)) {
					while (rs != null && rs.next()) {
						final BookmarkInfo info = new BookmarkInfo();
						info.setTitle(rs.getString(1));
						info.setDescription(rs.getString(2));
						info.setUrl(rs.getString(3));
						info.setTags(Strings.splitUsingTokenizer(rs.getString(4), " "));
						values.add(info);
					}
				} catch (final Exception e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
				}
			}
			return values;
		}
	}

	/**
	 * <p>insertBookmark.</p>
	 *
	 * @param info a {@link org.loboevolution.info.BookmarkInfo} object.
	 */
	public void insertBookmark(BookmarkInfo info) {
		try (Connection conn = DriverManager.getConnection(DB_PATH);
				PreparedStatement pstmt = conn.prepareStatement(this.INSERT_BOOKMARKS)) {
			pstmt.setString(1, info.getTitle());
			pstmt.setString(2, info.getDescription());
			pstmt.setString(3, info.getUrl());
			pstmt.setString(4, info.getTagsText());
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
