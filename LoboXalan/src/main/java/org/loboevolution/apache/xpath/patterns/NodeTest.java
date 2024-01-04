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
package org.loboevolution.apache.xpath.patterns;

import org.loboevolution.apache.xpath.Expression;
import org.loboevolution.apache.xpath.XPath;
import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xpath.XPathVisitor;
import org.loboevolution.apache.xpath.objects.XNumber;
import org.loboevolution.apache.xpath.objects.XObject;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMFilter;
import org.loboevolution.html.node.traversal.NodeFilter;

import java.util.Objects;

/** This is the basic node test class for both match patterns and location path steps. */
public class NodeTest extends Expression {

  /**
   * The namespace or local name for node tests with a wildcard.
   *
   * @see <a href="http://www.w3.org/TR/xpath#NT-NameTest">the XPath NameTest production.</a>
   */
  public static final String WILD = "*";

  /**
   * This attribute determines which node types are accepted.
   *
   * @serial
   */
  protected int m_whatToShow;

  /**
   * Special bitmap for match patterns starting with a function. Make sure this does not conflict
   * with {@link NodeFilter}.
   */
  public static final int SHOW_BYFUNCTION = 0x00010000;

  /**
   * This attribute determines which node types are accepted. These constants are defined in the
   * {@link NodeFilter} interface.
   *
   * @return bitset mainly defined in {@link NodeFilter}.
   */
  public int getWhatToShow() {
    return m_whatToShow;
  }

  /**
   * This attribute determines which node types are accepted. These constants are defined in the
   * {@link NodeFilter} interface.
   *
   * @param what bitset mainly defined in {@link NodeFilter}.
   */
  public void setWhatToShow(final int what) {
    m_whatToShow = what;
  }

  /**
   * The namespace to be tested for, which may be null.
   *
   * @serial
   */
  String m_namespace;

  /**
   * Return the namespace to be tested.
   *
   * @return The namespace to be tested for, or {@link #WILD}, or null.
   */
  public String getNamespace() {
    return m_namespace;
  }

  /**
   * Set the namespace to be tested.
   *
   * @param ns The namespace to be tested for, or {@link #WILD}, or null.
   */
  public void setNamespace(final String ns) {
    m_namespace = ns;
  }

  /**
   * The local name to be tested for.
   *
   * @serial
   */
  protected String m_name;

  /**
   * Return the local name to be tested.
   *
   * @return the local name to be tested, or {@link #WILD}, or an empty string.
   */
  public String getLocalName() {
    return (null == m_name) ? "" : m_name;
  }

  /**
   * Set the local name to be tested.
   *
   * @param name the local name to be tested, or {@link #WILD}, or an empty string.
   */
  public void setLocalName(final String name) {
    m_name = name;
  }

  /**
   * Statically calculated score for this test. One of {@link #SCORE_NODETEST}, {@link #SCORE_NONE},
   * {@link #SCORE_NSWILD}, {@link #SCORE_QNAME}, or {@link #SCORE_OTHER}.
   *
   * @serial
   */
  XNumber m_score;

  /**
   * The match score if the pattern consists of just a NodeTest.
   *
   * @see <a href="http://www.w3.org/TR/xslt#conflict">XSLT Specification - 5.5 Conflict Resolution
   *     for Template Rules</a>
   */
  public static final XNumber SCORE_NODETEST = new XNumber(XPath.MATCH_SCORE_NODETEST);

  /**
   * The match score if the pattern pattern has the form NCName:*.
   *
   * @see <a href="http://www.w3.org/TR/xslt#conflict">XSLT Specification - 5.5 Conflict Resolution
   *     for Template Rules</a>
   */
  public static final XNumber SCORE_NSWILD = new XNumber(XPath.MATCH_SCORE_NSWILD);

  /**
   * The match score if the pattern has the form of a QName optionally preceded by an @ character.
   *
   * @see <a href="http://www.w3.org/TR/xslt#conflict">XSLT Specification - 5.5 Conflict Resolution
   *     for Template Rules</a>
   */
  public static final XNumber SCORE_QNAME = new XNumber(XPath.MATCH_SCORE_QNAME);

  /**
   * The match score if the pattern consists of something other than just a NodeTest or just a
   * qname.
   *
   * @see <a href="http://www.w3.org/TR/xslt#conflict">XSLT Specification - 5.5 Conflict Resolution
   *     for Template Rules</a>
   */
  public static final XNumber SCORE_OTHER = new XNumber(XPath.MATCH_SCORE_OTHER);

  /**
   * The match score if no match is made.
   *
   * @see <a href="http://www.w3.org/TR/xslt#conflict">XSLT Specification - 5.5 Conflict Resolution
   *     for Template Rules</a>
   */
  public static final XNumber SCORE_NONE = new XNumber(XPath.MATCH_SCORE_NONE);

  /**
   * Construct an NodeTest that tests for namespaces and node names.
   *
   * @param whatToShow Bit set defined mainly by {@link NodeFilter}.
   * @param namespace The namespace to be tested.
   * @param name The local name to be tested.
   */
  public NodeTest(final int whatToShow, final String namespace, final String name) {
    initNodeTest(whatToShow, namespace, name);
  }

  /**
   * Construct an NodeTest that doesn't test for node names.
   *
   * @param whatToShow Bit set defined mainly by {@link NodeFilter}.
   */
  public NodeTest(final int whatToShow) {
    initNodeTest(whatToShow);
  }

  /** {@inheritDoc} */
  @Override
  public boolean deepEquals(final Expression expr) {
    if (!isSameClass(expr)) return false;

    final NodeTest nt = (NodeTest) expr;

    if (null != nt.m_name) {
      if (null == m_name) return false;
      else if (!nt.m_name.equals(m_name)) return false;
    } else if (null != m_name) return false;

    if (null != nt.m_namespace) {
      if (null == m_namespace) return false;
      else if (!nt.m_namespace.equals(m_namespace)) return false;
    } else if (null != m_namespace) return false;

    if (m_whatToShow != nt.m_whatToShow) return false;

    return m_isTotallyWild == nt.m_isTotallyWild;
  }

  /** Null argument constructor. */
  public NodeTest() {}

  /**
   * Initialize this node test by setting the whatToShow property, and calculating the score that
   * this test will return if a test succeeds.
   *
   * @param whatToShow Bit set defined mainly by {@link NodeFilter}.
   */
  public void initNodeTest(final int whatToShow) {

    m_whatToShow = whatToShow;

    calcScore();
  }

  /**
   * Initialize this node test by setting the whatToShow property and the namespace and local name,
   * and calculating the score that this test will return if a test succeeds.
   *
   * @param whatToShow Bit set defined mainly by {@link NodeFilter}.
   * @param namespace The namespace to be tested.
   * @param name The local name to be tested.
   */
  public void initNodeTest(final int whatToShow, final String namespace, final String name) {

    m_whatToShow = whatToShow;
    m_namespace = namespace;
    m_name = name;

    calcScore();
  }

  /**
   * True if this test has a null namespace and a local name of {@link #WILD}.
   *
   * @serial
   */
  private boolean m_isTotallyWild;

  /**
   * Get the static score for this node test.
   *
   * @return Should be one of the SCORE_XXX constants.
   */
  public XNumber getStaticScore() {
    return m_score;
  }

  /**
   * Set the static score for this node test.
   *
   * @param score Should be one of the SCORE_XXX constants.
   */
  public void setStaticScore(final XNumber score) {
    m_score = score;
  }

  /** Static calc of match score. */
  protected void calcScore() {

    if ((m_namespace == null) && (m_name == null)) m_score = SCORE_NODETEST;
    else if (((Objects.equals(m_namespace, WILD)) || (m_namespace == null)) && (Objects.equals(m_name, WILD)))
      m_score = SCORE_NODETEST;
    else if ((!Objects.equals(m_namespace, WILD)) && (Objects.equals(m_name, WILD))) m_score = SCORE_NSWILD;
    else m_score = SCORE_QNAME;

    m_isTotallyWild = m_namespace == null && Objects.equals(m_name, WILD);
  }

  /**
   * Tell what node type to test, if not DTMFilter.SHOW_ALL.
   *
   * @param whatToShow Bit set defined mainly by {@link
   *     DTMFilter}.
   * @return the node type for the whatToShow. Since whatToShow can specify multiple types, it will
   *     return the first bit tested that is on, so the caller of this function should take care
   *     that this is the function they really want to call. If none of the known bits are set, this
   *     function will return zero.
   */
  public static int getNodeTypeTest(final int whatToShow) {
    // %REVIEW% Is there a better way?
    if (0 != (whatToShow & DTMFilter.SHOW_ELEMENT)) return DTM.ELEMENT_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_ATTRIBUTE)) return DTM.ATTRIBUTE_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_TEXT)) return DTM.TEXT_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_DOCUMENT)) return DTM.DOCUMENT_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_DOCUMENT_FRAGMENT)) return DTM.DOCUMENT_FRAGMENT_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_NAMESPACE)) return DTM.NAMESPACE_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_COMMENT)) return DTM.COMMENT_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_PROCESSING_INSTRUCTION))
      return DTM.PROCESSING_INSTRUCTION_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_DOCUMENT_TYPE)) return DTM.DOCUMENT_TYPE_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_ENTITY)) return DTM.ENTITY_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_ENTITY_REFERENCE)) return DTM.ENTITY_REFERENCE_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_NOTATION)) return DTM.NOTATION_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_CDATA_SECTION)) return DTM.CDATA_SECTION_NODE;

    return 0;
  }

  /**
   * Two names are equal if they and either both are null or the name t is wild and the name p is
   * non-null, or the two strings are equal.
   *
   * @param p part string from the node.
   * @param t target string, which may be {@link #WILD}.
   * @return true if the strings match according to the rules of this method.
   */
  private static boolean subPartMatch(final String p, final String t) {
    return Objects.equals(p, t) || (p != null && (Objects.equals(t, WILD)));
  }

  /**
   * This is temporary to patch over Xerces issue with representing DOM namespaces as "".
   *
   * @param p part string from the node, which may represent the null namespace as null or as "".
   * @param t target string, which may be {@link #WILD}.
   * @return true if the strings match according to the rules of this method.
   */
  private static boolean subPartMatchNS(final String p, final String t) {

    return Objects.equals(p, t)
            || (p != null && ((p.length() > 0) ? (Objects.equals(t, WILD) || Objects.equals(p, t)) : Objects.equals(null, t)));
  }

  /** {@inheritDoc} */
  @Override
  public XObject execute(final XPathContext xctxt, final int context)
      throws org.loboevolution.javax.xml.transform.TransformerException {

    final DTM dtm = xctxt.getDTM(context);
    final short nodeType = dtm.getNodeType(context);

    if (m_whatToShow == DTMFilter.SHOW_ALL) return m_score;

    final int nodeBit = m_whatToShow & (0x00000001 << (nodeType - 1));

    switch (nodeBit) {
      case DTMFilter.SHOW_DOCUMENT_FRAGMENT:
      case DTMFilter.SHOW_DOCUMENT:
        return SCORE_OTHER;
      case DTMFilter.SHOW_COMMENT:
        return m_score;
      case DTMFilter.SHOW_CDATA_SECTION:
      case DTMFilter.SHOW_TEXT:
        return m_score;
      case DTMFilter.SHOW_PROCESSING_INSTRUCTION:
        return subPartMatch(dtm.getNodeName(context), m_name) ? m_score : SCORE_NONE;

        // From the draft: "Two expanded names are equal if they
        // have the same local part, and either both have no URI or
        // both have the same URI."
        // "A node test * is true for any node of the principal node type.
        // For example, child::* will select all element children of the
        // context node, and attribute::* will select all attributes of
        // the context node."
        // "A node test can have the form NCName:*. In this case, the prefix
        // is expanded in the same way as with a QName using the context
        // namespace declarations. The node test will be true for any node
        // of the principal type whose expanded name has the URI to which
        // the prefix expands, regardless of the local part of the name."
      case DTMFilter.SHOW_NAMESPACE:
        {
          final String ns = dtm.getLocalName(context);

          return (subPartMatch(ns, m_name)) ? m_score : SCORE_NONE;
        }
      case DTMFilter.SHOW_ATTRIBUTE:
      case DTMFilter.SHOW_ELEMENT:
        {
          return (m_isTotallyWild
                  || (subPartMatchNS(dtm.getNamespaceURI(context), m_namespace)
                      && subPartMatch(dtm.getLocalName(context), m_name)))
              ? m_score
              : SCORE_NONE;
        }
      default:
        return SCORE_NONE;
    } // end switch(testType)
  }

  /** {@inheritDoc} */
  @Override
  public XObject execute(final XPathContext xctxt, final int context, final DTM dtm, final int expType)
      throws org.loboevolution.javax.xml.transform.TransformerException {

    if (m_whatToShow == DTMFilter.SHOW_ALL) return m_score;

    final int nodeBit = m_whatToShow & (0x00000001 << ((dtm.getNodeType(context)) - 1));

    switch (nodeBit) {
      case DTMFilter.SHOW_DOCUMENT_FRAGMENT:
      case DTMFilter.SHOW_DOCUMENT:
        return SCORE_OTHER;
      case DTMFilter.SHOW_COMMENT:
        return m_score;
      case DTMFilter.SHOW_CDATA_SECTION:
      case DTMFilter.SHOW_TEXT:
        return m_score;
      case DTMFilter.SHOW_PROCESSING_INSTRUCTION:
        return subPartMatch(dtm.getNodeName(context), m_name) ? m_score : SCORE_NONE;

        // From the draft: "Two expanded names are equal if they
        // have the same local part, and either both have no URI or
        // both have the same URI."
        // "A node test * is true for any node of the principal node type.
        // For example, child::* will select all element children of the
        // context node, and attribute::* will select all attributes of
        // the context node."
        // "A node test can have the form NCName:*. In this case, the prefix
        // is expanded in the same way as with a QName using the context
        // namespace declarations. The node test will be true for any node
        // of the principal type whose expanded name has the URI to which
        // the prefix expands, regardless of the local part of the name."
      case DTMFilter.SHOW_NAMESPACE:
        {
          final String ns = dtm.getLocalName(context);

          return (subPartMatch(ns, m_name)) ? m_score : SCORE_NONE;
        }
      case DTMFilter.SHOW_ATTRIBUTE:
      case DTMFilter.SHOW_ELEMENT:
        {
          return (m_isTotallyWild
                  || (subPartMatchNS(dtm.getNamespaceURI(context), m_namespace)
                      && subPartMatch(dtm.getLocalName(context), m_name)))
              ? m_score
              : SCORE_NONE;
        }
      default:
        return SCORE_NONE;
    } // end switch(testType)
  }

  /** {@inheritDoc} */
  @Override
  public XObject execute(final XPathContext xctxt) throws org.loboevolution.javax.xml.transform.TransformerException {
    return execute(xctxt, xctxt.getCurrentNode());
  }

  /** {@inheritDoc} */
  @Override
  public void callVisitors(final XPathVisitor visitor) {
    assertion(false, "callVisitors should not be called for this object!!!");
  }
}
