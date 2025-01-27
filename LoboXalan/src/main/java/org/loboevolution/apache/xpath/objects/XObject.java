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
package org.loboevolution.apache.xpath.objects;

import org.loboevolution.apache.xpath.Expression;
import org.loboevolution.apache.xpath.NodeSetDTM;
import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xpath.XPathException;
import org.loboevolution.apache.xpath.XPathVisitor;
import org.loboevolution.apache.xpath.res.XPATHErrorResources;
import org.loboevolution.apache.xpath.res.XPATHMessages;
import org.loboevolution.apache.xml.dtm.DTMIterator;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.traversal.NodeIterator;
import javax.xml.transform.TransformerException;

/**
 * This class represents an XPath object, and is capable of converting the object to various types,
 * such as a string. This class acts as the base class to other XPath type objects, such as XString,
 * and provides polymorphic casting capabilities.
 */
public class XObject extends Expression {

  /**
   * The java object which this object wraps.
   *
   * @serial
   */
  protected Object m_obj; // This may be NULL!!!

  /** Create an XObject. */
  public XObject() {}

  /**
   * Create an XObject.
   *
   * @param obj Can be any object, should be a specific type for derived classes, or null.
   */
  public XObject(final Object obj) {
    setObject(obj);
  }

  protected void setObject(final Object obj) {
    m_obj = obj;
  }

  /** {@inheritDoc} */
  @Override
  public XObject execute(final XPathContext xctxt) throws javax.xml.transform.TransformerException {
    return this;
  }

  /**
   * Specify if it's OK for detach to release the iterator for reuse. This function should be called
   * with a value of false for objects that are stored in variables. Calling this with a value of
   * false on a XNodeSet will cause the nodeset to be cached.
   *
   * @param allowRelease true if it is OK for detach to release this iterator for pooling.
   */
  public void allowDetachToRelease(final boolean allowRelease) {}

  /**
   * Detaches the <code>DTMIterator</code> from the set which it iterated over, releasing any
   * computational resources and placing the iterator in the INVALID state. After <code>detach
   * </code> has been invoked, calls to <code>nextNode</code> or <code>previousNode</code> will
   * raise a runtime exception.
   */
  public void detach() {}

  /** Constant for NULL object type */
  public static final int CLASS_NULL = -1;

  /** Constant for UNKNOWN object type */
  public static final int CLASS_UNKNOWN = 0;

  /** Constant for BOOLEAN object type */
  public static final int CLASS_BOOLEAN = 1;

  /** Constant for NUMBER object type */
  public static final int CLASS_NUMBER = 2;

  /** Constant for STRING object type */
  public static final int CLASS_STRING = 3;

  /** Constant for NODESET object type */
  public static final int CLASS_NODESET = 4;

  /** Constant for RESULT TREE FRAGMENT object type */
  public static final int CLASS_RTREEFRAG = 5;

  /**
   * Tell what kind of class this is.
   *
   * @return CLASS_UNKNOWN
   */
  public int getType() {
    return CLASS_UNKNOWN;
  }

  /**
   * Given a request type, return the equivalent string. For diagnostic purposes.
   *
   * @return type string "#UNKNOWN" + object class name
   */
  public String getTypeString() {
    return "#UNKNOWN (" + object().getClass().getName() + ")";
  }

  /**
   * Cast result object to a number. Always issues an error.
   *
   * @return 0.0
   * @throws javax.xml.transform.TransformerException in case of error in case of error
   */
  public double num() throws javax.xml.transform.TransformerException {

    error(XPATHErrorResources.ER_CANT_CONVERT_TO_NUMBER, new Object[] {getTypeString()});

    return 0.0;
  }

  /**
   * Cast result object to a number, but allow side effects, such as the incrementing of an
   * iterator.
   *
   * @return numeric value of the string conversion from the next node in the NodeSetDTM, or NAN if
   *     no node was found
   */
  public double numWithSideEffects() throws javax.xml.transform.TransformerException {
    return num();
  }

  /**
   * Cast result object to a boolean. Always issues an error.
   *
   * @return false
   * @throws javax.xml.transform.TransformerException in case of error in case of error
   */
  public boolean bool() throws javax.xml.transform.TransformerException {

    error(XPATHErrorResources.ER_CANT_CONVERT_TO_NUMBER, new Object[] {getTypeString()});

    return false;
  }

  /**
   * Cast result object to a boolean, but allow side effects, such as the incrementing of an
   * iterator.
   *
   * @return True if there is a next node in the nodeset
   */
  public boolean boolWithSideEffects() throws javax.xml.transform.TransformerException {
    return bool();
  }

  /**
   * Cast result object to a string.
   *
   * @return The string this wraps or the empty string if null
   */
  public XString xstr() {
    return new XString(str());
  }

  /**
   * Cast result object to a string.
   *
   * @return The object as a string
   */
  public String str() {
    return (m_obj != null) ? m_obj.toString() : "";
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return str();
  }

  /**
   * Return a java object that's closest to the representation that should be handed to an
   * extension.
   *
   * @return The object that this class wraps
   */
  public Object object() {
    return m_obj;
  }

  /**
   * Cast result object to a nodelist. Always issues an error.
   *
   * @return null
   * @throws javax.xml.transform.TransformerException in case of error in case of error
   */
  public DTMIterator iter() throws javax.xml.transform.TransformerException {

    error(XPATHErrorResources.ER_CANT_CONVERT_TO_NODELIST, new Object[] {getTypeString()});

    return null;
  }

  /**
   * Cast result object to a nodelist. Always issues an error.
   *
   * @return null
   * @throws TransformerException in case of error in case of error
   */
  public NodeIterator nodeset() throws TransformerException {
    error(XPATHErrorResources.ER_CANT_CONVERT_TO_NODELIST, new Object[] {getTypeString()});
    return null;
  }

  /**
   * Cast result object to a nodelist. Always issues an error.
   *
   * @return null
   * @throws javax.xml.transform.TransformerException in case of error in case of error
   */
  public NodeList nodelist() throws javax.xml.transform.TransformerException {

    error(XPATHErrorResources.ER_CANT_CONVERT_TO_NODELIST, new Object[] {getTypeString()});

    return null;
  }

  /**
   * Cast result object to a nodelist. Always issues an error.
   *
   * @return The object as a NodeSetDTM.
   * @throws javax.xml.transform.TransformerException in case of error in case of error
   */
  public NodeSetDTM mutableNodeset() throws javax.xml.transform.TransformerException {

    error(XPATHErrorResources.ER_CANT_CONVERT_TO_MUTABLENODELIST, new Object[] {getTypeString()});

    return (NodeSetDTM) m_obj;
  }

  /**
   * Tell if one object is less than the other.
   *
   * @param obj2 Object to compare this to
   * @return True if this object is less than the given object
   * @throws javax.xml.transform.TransformerException in case of error in case of error
   */
  public boolean lessThan(final XObject obj2) throws javax.xml.transform.TransformerException {

    // In order to handle the 'all' semantics of
    // nodeset comparisons, we always call the
    // nodeset function. Because the arguments
    // are backwards, we call the opposite comparison
    // function.
    if (obj2.getType() == XObject.CLASS_NODESET) return obj2.greaterThan(this);

    return this.num() < obj2.num();
  }

  /**
   * Tell if one object is less than or equal to the other.
   *
   * @param obj2 Object to compare this to
   * @return True if this object is less than or equal to the given object
   * @throws javax.xml.transform.TransformerException in case of error
   */
  public boolean lessThanOrEqual(final XObject obj2) throws javax.xml.transform.TransformerException {

    // In order to handle the 'all' semantics of
    // nodeset comparisons, we always call the
    // nodeset function. Because the arguments
    // are backwards, we call the opposite comparison
    // function.
    if (obj2.getType() == XObject.CLASS_NODESET) return obj2.greaterThanOrEqual(this);

    return this.num() <= obj2.num();
  }

  /**
   * Tell if one object is greater than the other.
   *
   * @param obj2 Object to compare this to
   * @return True if this object is greater than the given object
   * @throws javax.xml.transform.TransformerException in case of error
   */
  public boolean greaterThan(final XObject obj2) throws javax.xml.transform.TransformerException {

    // In order to handle the 'all' semantics of
    // nodeset comparisons, we always call the
    // nodeset function. Because the arguments
    // are backwards, we call the opposite comparison
    // function.
    if (obj2.getType() == XObject.CLASS_NODESET) return obj2.lessThan(this);

    return this.num() > obj2.num();
  }

  /**
   * Tell if one object is greater than or equal to the other.
   *
   * @param obj2 Object to compare this to
   * @return True if this object is greater than or equal to the given object
   * @throws javax.xml.transform.TransformerException in case of error
   */
  public boolean greaterThanOrEqual(final XObject obj2) throws javax.xml.transform.TransformerException {

    // In order to handle the 'all' semantics of
    // nodeset comparisons, we always call the
    // nodeset function. Because the arguments
    // are backwards, we call the opposite comparison
    // function.
    if (obj2.getType() == XObject.CLASS_NODESET) return obj2.lessThanOrEqual(this);

    return this.num() >= obj2.num();
  }

  /**
   * Tell if two objects are functionally equal.
   *
   * @param obj2 Object to compare this to
   * @return True if this object is equal to the given object
   */
  public boolean equals(final XObject obj2) {

    // In order to handle the 'all' semantics of
    // nodeset comparisons, we always call the
    // nodeset function.
    if (obj2.getType() == XObject.CLASS_NODESET) return obj2.equals(this);

    if (null != m_obj) {
      return m_obj.equals(obj2.m_obj);
    }
    return obj2.m_obj == null;
  }

  /**
   * Tell if two objects are functionally not equal.
   *
   * @param obj2 Object to compare this to
   * @return True if this object is not equal to the given object
   * @throws javax.xml.transform.TransformerException in case of error
   */
  public boolean notEquals(final XObject obj2) throws javax.xml.transform.TransformerException {

    // In order to handle the 'all' semantics of
    // nodeset comparisons, we always call the
    // nodeset function.
    if (obj2.getType() == XObject.CLASS_NODESET) return obj2.notEquals(this);

    return !equals(obj2);
  }

  /**
   * Tell the user of an error, and probably throw an exception.
   *
   * @param msg Error message to issue
   * @throws javax.xml.transform.TransformerException in case of error
   */
  protected void error(final String msg) throws javax.xml.transform.TransformerException {
    error(msg, null);
  }

  /**
   * Tell the user of an error, and probably throw an exception.
   *
   * @param msg Error message to issue
   * @param args Arguments to use in the message
   * @throws javax.xml.transform.TransformerException in case of error
   */
  protected void error(final String msg, final Object[] args) throws javax.xml.transform.TransformerException {

    final String fmsg = XPATHMessages.createXPATHMessage(msg, args);
    throw new XPathException(fmsg, this);
  }

  /** {@inheritDoc} */
  @Override
  public void callVisitors(final XPathVisitor visitor) {
    assertion(false, "callVisitors should not be called for this object!!!");
  }

  /** {@inheritDoc} */
  @Override
  public boolean deepEquals(final Expression expr) {
    if (!isSameClass(expr)) return false;

    // If equals at the expression level calls deepEquals, I think we're
    // still safe from infinite recursion since this object overrides
    // equals. I hope.
    if (!this.equals((XObject) expr)) return false;

    return true;
  }
}
