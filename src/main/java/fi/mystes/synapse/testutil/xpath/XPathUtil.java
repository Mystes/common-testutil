/**
 * Copyright 2016 Mystes Oy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fi.mystes.synapse.testutil.xpath;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.util.*;

/**
 * Helper class for working with XPATHs
 *
 */
public class XPathUtil {
    private XPathUtil() {
        // suppress default constructor to prevent instantiation
        // as this class contains static utility methods only
    }

    /**
     * Run XPATH against given XML for one match.
     *
     * @param sourceXml
     *          The XML document that XPATH is going to be run against with.
     * @param expression
     *          The XPATH expression.
     * @param nsDefinitions
     *          Namespace definitions if the xml contains some.
     * @return  The match for the XPATH expression in a String.
     * @throws XPathExpressionException
     */
    public static String selectStringValue(String sourceXml, String expression, NamespaceDefinition... nsDefinitions) throws XPathExpressionException {
        validateArgs(sourceXml, expression);

        XPath xpath = instantiateXPath(nsDefinitions);
        InputSource inputSource = inputSourceFromString(sourceXml);
        return (String) xpath.evaluate(expression, inputSource, XPathConstants.STRING);
    }

    /**
     * Run XPATH against given XML for several matches.
     *
     * @param sourceXml
     *          The XML document that XPATH is going to be run against with.
     * @param expression
     *          The XPATH expression.
     * @param nsDefinitions
     *          Namespace definitions if the xml contains some.
     * @return  A list of the matches of the XPATH expression.
     * @throws XPathExpressionException
     */
    public static List<String> selectStringValues(String sourceXml, String expression, NamespaceDefinition... nsDefinitions) throws XPathExpressionException {
        NodeList nodes = selectNodeList(sourceXml, expression, nsDefinitions);

        List<String> values = new ArrayList<String>(nodes.getLength());

        for (int i = 0; i < nodes.getLength(); i++) {
            values.add(nodes.item(i).getTextContent());
        }

        return values;
    }

    /**
     * Run XPATH against given XML
     *
     * @param sourceXml
     *          The XML document that XPATH is going to be run against with.
     * @param expression
     *          The XPATH expression.
     * @param nsDefinitions
     *          Namespace definitions if the xml contains some.
     * @return Nodes that match the given XPATH.ßß
     * @throws XPathExpressionException
     */
    public static NodeList selectNodes(String sourceXml, String expression, NamespaceDefinition... nsDefinitions) throws XPathExpressionException {
        return selectNodeList(sourceXml, expression, nsDefinitions);
    }

    private static NodeList selectNodeList(String sourceXml, String expression, NamespaceDefinition... nsDefinitions) throws XPathExpressionException {
        validateArgs(sourceXml, expression);

        XPath xpath = instantiateXPath(nsDefinitions);
        InputSource inputSource = inputSourceFromString(sourceXml);
        return (NodeList) xpath.evaluate(expression, inputSource, XPathConstants.NODESET);
    }

    private static void validateArgs(String sourceXml, String expression) {
        if (sourceXml == null) {
            throw new NullPointerException("refusing to parse values from null sourceXml");
        }
        if (expression == null) {
            throw new NullPointerException("refusing to parse values with null expression");
        }
    }

    private static InputSource inputSourceFromString(String sourceXml) {
        return new InputSource(new StringReader(sourceXml));
    }

    private static XPath instantiateXPath(NamespaceDefinition... nsDefinitions) {
        XPath xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext(namespaceContextWith(nsDefinitions));

        return xpath;
    }

    private static NamespaceContext namespaceContextWith(NamespaceDefinition... nsDefinitions) {
        return new NamespaceContextImpl(nsDefinitions);
    }

    private static final class NamespaceContextImpl implements NamespaceContext {

        private final Map<String, String> nsDefinitions = new HashMap<String, String>();

        NamespaceContextImpl(NamespaceDefinition... nsDefinitions) {
            for (NamespaceDefinition ns : nsDefinitions) {
                this.nsDefinitions.put(ns.getPrefix(), ns.getUri());
            }
        }


        @Override
        public String getNamespaceURI(String prefix) {
            return nsDefinitions.get(prefix);
        }

        @Override
        public String getPrefix(String namespaceURI) {
            for (Map.Entry<String, String> entry : nsDefinitions.entrySet()) {
                if (entry.getValue().equals(namespaceURI)) {
                    return entry.getKey();
                }
            }

            return null;
        }

        @Override
        public Iterator getPrefixes(String namespaceURI) {
            return nsDefinitions.keySet().iterator();
        }
    }
}
