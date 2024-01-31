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
package org.loboevolution.apache.xpath.objects;

import java.util.ArrayList;
import java.util.List;

import org.loboevolution.apache.xml.dtm.ref.DTMNodeIterator;
import org.loboevolution.apache.xml.dtm.ref.DTMNodeList;
import org.loboevolution.apache.xml.utils.WrappedRuntimeException;
import org.loboevolution.apache.xpath.NodeSetDTM;
import org.loboevolution.apache.xpath.axes.NodeSequence;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMIterator;
import org.loboevolution.apache.xml.dtm.DTMManager;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.traversal.NodeIterator;
import org.loboevolution.javax.xml.transform.TransformerException;

/**
 * This class represents an XPath nodeset object, and is capable of converting the nodeset to other
 * types, such as a string.
 */
public class XNodeSet extends NodeSequence {

  /**
   * Construct a XNodeSet object.
   *
   * @param val Value of the XNodeSet object
   */
  public XNodeSet(final DTMIterator val) {
    super();
    if (val instanceof XNodeSet) {
      final XNodeSet nodeSet = (XNodeSet) val;
      setIter(nodeSet.m_iter);
      m_dtmMgr = nodeSet.m_dtmMgr;
      m_last = nodeSet.m_last;
      // First make sure the DTMIterator val has a cache,
      // so if it doesn't have one, make one.
      if (!nodeSet.hasCache()) nodeSet.setShouldCacheNodes(true);

      // Get the cache from val and use it ourselves (we share it).
      setObject(nodeSet.getIteratorCache());
    } else setIter(val);
  }

  /**
   * Construct an empty XNodeSet object. This is used to create a mutable nodeset to which random
   * nodes may be added.
   */
  public XNodeSet(final DTMManager dtmMgr) {
    this(DTM.NULL, dtmMgr);
  }

  /**
   * Construct a XNodeSet object for one node.
   *
   * @param n Node to add to the new XNodeSet object
   */
  public XNodeSet(final int n, final DTMManager dtmMgr) {

    super(new NodeSetDTM(dtmMgr));
    m_dtmMgr = dtmMgr;

    if (DTM.NULL != n) {
      ((NodeSetDTM) m_obj).addNode(n);
      m_last = 1;
    } else m_last = 0;
  }

  /** {@inheritDoc} */
  @Override
  public int getType() {
    return CLASS_NODESET;
  }

  /** {@inheritDoc} */
  @Override
  public String getTypeString() {
    return "#NODESET";
  }

  /**
   * Get numeric value of the string conversion from a single node.
   *
   * @param n Node to convert
   * @return numeric value of the string conversion from a single node.
   */
  public double getNumberFromNode(final int n) {
    final XString xstr = m_dtmMgr.getDTM(n).getStringValue(n);
    return xstr.toDouble();
  }

  /** {@inheritDoc} */
  @Override
  public double num() {

    final int node = item(0);
    return (node != DTM.NULL) ? getNumberFromNode(node) : Double.NaN;
  }

  /** {@inheritDoc} */
  @Override
  public double numWithSideEffects() {
    final int node = nextNode();

    return (node != DTM.NULL) ? getNumberFromNode(node) : Double.NaN;
  }

  /** {@inheritDoc} */
  @Override
  public boolean bool() {
    return item(0) != DTM.NULL;
  }

  /** {@inheritDoc} */
  @Override
  public boolean boolWithSideEffects() {
    return nextNode() != DTM.NULL;
  }

  /**
   * Get the string conversion from a single node.
   *
   * @param n Node to convert
   * @return the string conversion from a single node.
   */
  public XString getStringFromNode(final int n) {
    // %OPT%
    // I guess we'll have to get a static instance of the DTM manager...
    if (DTM.NULL != n) {
      return m_dtmMgr.getDTM(n).getStringValue(n);
    }
    return org.loboevolution.apache.xpath.objects.XString.EMPTYSTRING;
  }

  /** {@inheritDoc} */
  @Override
  public XString xstr() {
    final int node = item(0);
    return (node != DTM.NULL) ? getStringFromNode(node) : XString.EMPTYSTRING;
  }

  /** {@inheritDoc} */
  @Override
  public String str() {
    final int node = item(0);
    return (node != DTM.NULL) ? getStringFromNode(node).toString() : "";
  }

  /** {@inheritDoc} */
  @Override
  public Object object() {
    if (null == m_obj) {
      return this;
    }
    return m_obj;
  }

  /** {@inheritDoc} */
  @Override
  public NodeIterator nodeset() throws TransformerException {
    return new DTMNodeIterator(iter());
  }

  /** {@inheritDoc} */
  @Override
  public NodeList nodelist() throws TransformerException {
    final DTMNodeList nodelist =
        new DTMNodeList(this);
    // Creating a DTMNodeList has the side-effect that it will create a clone
    // XNodeSet with cache and run m_iter to the end. You cannot get any node
    // from m_iter after this call. As a fix, we call SetVector() on the clone's
    // cache. See Bugzilla 14406.
    final XNodeSet clone = (XNodeSet) nodelist.getDTMIterator();
    SetVector(clone.getVector());
    return nodelist;
  }

  /** Return the iterator without cloning, etc. */
  public DTMIterator iterRaw() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public DTMIterator iter() {
    try {
      if (hasCache()) {
        return cloneWithReset();
      }
      return this; // don't bother to clone... won't do any good!
    } catch (final CloneNotSupportedException cnse) {
      throw new RuntimeException(cnse.getMessage());
    }
  }

  /** {@inheritDoc} */
  @Override
  public NodeSetDTM mutableNodeset() {
    final NodeSetDTM mnl;

    if (m_obj instanceof NodeSetDTM) {
      mnl = (NodeSetDTM) m_obj;
    } else {
      mnl = new NodeSetDTM(iter());
      setObject(mnl);
      setCurrentPos(0);
    }

    return mnl;
  }

  /** Less than comparator */
  static final LessThanComparator S_LT = new LessThanComparator();

  /** Less than or equal comparator */
  static final LessThanOrEqualComparator S_LTE = new LessThanOrEqualComparator();

  /** Greater than comparator */
  static final GreaterThanComparator S_GT = new GreaterThanComparator();

  /** Greater than or equal comparator */
  static final GreaterThanOrEqualComparator S_GTE = new GreaterThanOrEqualComparator();

  /** Equal comparator */
  static final EqualComparator S_EQ = new EqualComparator();

  /** Not equal comparator */
  static final NotEqualComparator S_NEQ = new NotEqualComparator();

  /**
   * Tell if one object is less than the other.
   *
   * @param obj2 Object to compare this nodeset to
   * @param comparator Comparator to use
   * @return See the comments below for each object type comparison
   * @throws TransformerException in case of error
   */
  public boolean compare(final XObject obj2, final Comparator comparator)
      throws TransformerException {

    boolean result = false;
    final int type = obj2.getType();

    if (XObject.CLASS_NODESET == type) {
      // %OPT% This should be XMLString based instead of string based...

      // From http://www.w3.org/TR/xpath:
      // If both objects to be compared are node-sets, then the comparison
      // will be true if and only if there is a node in the first node-set
      // and a node in the second node-set such that the result of performing
      // the comparison on the string-values of the two nodes is true.
      // Note this little gem from the draft:
      // NOTE: If $x is bound to a node-set, then $x="foo"
      // does not mean the same as not($x!="foo"): the former
      // is true if and only if some node in $x has the string-value
      // foo; the latter is true if and only if all nodes in $x have
      // the string-value foo.
      final DTMIterator list1 = iterRaw();
      final DTMIterator list2 = ((XNodeSet) obj2).iterRaw();
      int node1;
      List<XString> node2Strings = null;

      while (DTM.NULL != (node1 = list1.nextNode())) {
        final XString s1 = getStringFromNode(node1);

        if (null == node2Strings) {
          int node2;

          while (DTM.NULL != (node2 = list2.nextNode())) {
            final XString s2 = getStringFromNode(node2);

            if (comparator.compareStrings(s1, s2)) {
              result = true;

              break;
            }

            if (null == node2Strings) node2Strings = new ArrayList<>();

            node2Strings.add(s2);
          }
        } else {
          for (final XString node2String : node2Strings) {
            if (comparator.compareStrings(s1, node2String)) {
              result = true;

              break;
            }
          }
        }
      }
      list1.reset();
      list2.reset();
    } else if (XObject.CLASS_BOOLEAN == type) {

      // From http://www.w3.org/TR/xpath:
      // If one object to be compared is a node-set and the other is a boolean,
      // then the comparison will be true if and only if the result of
      // performing the comparison on the boolean and on the result of
      // converting the node-set to a boolean using the boolean function
      // is true.
      final double num1 = bool() ? 1.0 : 0.0;
      final double num2 = obj2.num();

      result = comparator.compareNumbers(num1, num2);
    } else if (XObject.CLASS_NUMBER == type) {

      // From http://www.w3.org/TR/xpath:
      // If one object to be compared is a node-set and the other is a number,
      // then the comparison will be true if and only if there is a
      // node in the node-set such that the result of performing the
      // comparison on the number to be compared and on the result of
      // converting the string-value of that node to a number using
      // the number function is true.
      final DTMIterator list1 = iterRaw();
      final double num2 = obj2.num();
      int node;

      while (DTM.NULL != (node = list1.nextNode())) {
        final double num1 = getNumberFromNode(node);

        if (comparator.compareNumbers(num1, num2)) {
          result = true;

          break;
        }
      }
      list1.reset();
    } else if (XObject.CLASS_RTREEFRAG == type) {
      final XString s2 = obj2.xstr();
      final DTMIterator list1 = iterRaw();
      int node;

      while (DTM.NULL != (node = list1.nextNode())) {
        final XString s1 = getStringFromNode(node);

        if (comparator.compareStrings(s1, s2)) {
          result = true;

          break;
        }
      }
      list1.reset();
    } else if (XObject.CLASS_STRING == type) {

      // From http://www.w3.org/TR/xpath:
      // If one object to be compared is a node-set and the other is a
      // string, then the comparison will be true if and only if there
      // is a node in the node-set such that the result of performing
      // the comparison on the string-value of the node and the other
      // string is true.
      final XString s2 = obj2.xstr();
      final DTMIterator list1 = iterRaw();
      int node;

      while (DTM.NULL != (node = list1.nextNode())) {
        final XString s1 = getStringFromNode(node);
        if (comparator.compareStrings(s1, s2)) {
          result = true;

          break;
        }
      }
      list1.reset();
    } else {
      result = comparator.compareNumbers(this.num(), obj2.num());
    }

    return result;
  }

  /** {@inheritDoc} */
  @Override
  public boolean lessThan(final XObject obj2) throws TransformerException {
    return compare(obj2, S_LT);
  }

  /** {@inheritDoc} */
  @Override
  public boolean lessThanOrEqual(final XObject obj2) throws TransformerException {
    return compare(obj2, S_LTE);
  }

  /** {@inheritDoc} */
  @Override
  public boolean greaterThan(final XObject obj2) throws TransformerException {
    return compare(obj2, S_GT);
  }

  /** {@inheritDoc} */
  @Override
  public boolean greaterThanOrEqual(final XObject obj2) throws TransformerException {
    return compare(obj2, S_GTE);
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final XObject obj2) {
    try {
      return compare(obj2, S_EQ);
    } catch (final org.loboevolution.javax.xml.transform.TransformerException te) {
      throw new WrappedRuntimeException(te);
    }
  }

  /** {@inheritDoc} */
  @Override
  public boolean notEquals(final XObject obj2) throws TransformerException {
    return compare(obj2, S_NEQ);
  }
}

/** compares nodes for various boolean operations. */
abstract class Comparator {

  /**
   * Compare two strings
   *
   * @param s1 First string to compare
   * @param s2 Second String to compare
   * @return Whether the strings are equal or not
   */
  abstract boolean compareStrings(XString s1, XString s2);

  /**
   * Compare two numbers
   *
   * @param n1 First number to compare
   * @param n2 Second number to compare
   * @return Whether the numbers are equal or not
   */
  abstract boolean compareNumbers(double n1, double n2);
}

/** Compare strings or numbers for less than. */
class LessThanComparator extends Comparator {

  /** {@inheritDoc} */
  @Override
  boolean compareStrings(final XString s1, final XString s2) {
    return s1.toDouble() < s2.toDouble();
    // return s1.compareTo(s2) < 0;
  }

  /** {@inheritDoc} */
  @Override
  boolean compareNumbers(final double n1, final double n2) {
    return n1 < n2;
  }
}

/** Compare strings or numbers for less than or equal. */
class LessThanOrEqualComparator extends Comparator {

  /** {@inheritDoc} */
  @Override
  boolean compareStrings(final XString s1, final XString s2) {
    return s1.toDouble() <= s2.toDouble();
    // return s1.compareTo(s2) <= 0;
  }

  /** {@inheritDoc} */
  @Override
  boolean compareNumbers(final double n1, final double n2) {
    return n1 <= n2;
  }
}

/** Compare strings or numbers for greater than. */
class GreaterThanComparator extends Comparator {

  /** {@inheritDoc} */
  @Override
  boolean compareStrings(final XString s1, final XString s2) {
    return s1.toDouble() > s2.toDouble();
    // return s1.compareTo(s2) > 0;
  }

  /** {@inheritDoc} */
  @Override
  boolean compareNumbers(final double n1, final double n2) {
    return n1 > n2;
  }
}

/** Compare strings or numbers for greater than or equal. */
class GreaterThanOrEqualComparator extends Comparator {

  /** {@inheritDoc} */
  @Override
  boolean compareStrings(final XString s1, final XString s2) {
    return s1.toDouble() >= s2.toDouble();
    // return s1.compareTo(s2) >= 0;
  }

  /** {@inheritDoc} */
  @Override
  boolean compareNumbers(final double n1, final double n2) {
    return n1 >= n2;
  }
}

/** Compare strings or numbers for equality. */
class EqualComparator extends Comparator {

  /** {@inheritDoc} */
  @Override
  boolean compareStrings(final XString s1, final XString s2) {
    return s1.equals(s2);
  }

  /** {@inheritDoc} */
  @Override
  boolean compareNumbers(final double n1, final double n2) {
    return n1 == n2;
  }
}

/** Compare strings or numbers for non-equality. */
class NotEqualComparator extends Comparator {

  /** {@inheritDoc} */
  @Override
  boolean compareStrings(final XString s1, final XString s2) {
    return !s1.equals(s2);
  }

  /** {@inheritDoc} */
  @Override
  boolean compareNumbers(final double n1, final double n2) {
    return n1 != n2;
  }
}
