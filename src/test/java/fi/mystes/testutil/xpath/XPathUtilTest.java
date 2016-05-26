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
package fi.mystes.testutil.xpath;

import org.junit.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class XPathUtilTest {

    private static final String SAMPLE_NS_URI = "http://sample.mystes.fi";

    @Test(expected = NullPointerException.class)
    public void selectingSingleStringValueFailsWithNullSourceXml() throws XPathExpressionException {
        testSelectStringValue(null, "/data/field1");
    }

    @Test(expected = NullPointerException.class)
    public void selectingSingleStringValueFailsWithNullExpression() throws XPathExpressionException {
        String xmlString = xmlWithNoNamespace();

        testSelectStringValue(xmlString, null);
    }

    @Test
    public void selectsSingleStringValueByXPathExpression() throws XPathExpressionException {
        String xmlString = xmlWithNoNamespace();

        String value = testSelectStringValue(xmlString, "/data/field1");
        assertEquals("value1", value);
    }

    @Test
    public void selectsSingleStringValueByXPathExpressionWithNamespace() throws XPathExpressionException {
        String xmlString = xmlWithSampleNamespace();

        String value = testSelectStringValue(xmlString, "/sample:data/sample:field1", new NamespaceDefinition("sample", SAMPLE_NS_URI));
        assertEquals("value1", value);
    }

    @Test
    public void returnsEmptyStringForNonExistingStringValueExpression() throws XPathExpressionException {
        String xmlString = xmlWithNoNamespace();

        assertEquals("", testSelectStringValue(xmlString, "/data/notExisting"));
    }

    @Test(expected = NullPointerException.class)
    public void selectingMultipleStringValuesFailsWithNullSourceXml() throws XPathExpressionException {
        testSelectMultipleStringValues(null, "/data/field1");
    }

    @Test(expected = NullPointerException.class)
    public void selectingMultipleStringValuesFailsWithNullExpression() throws XPathExpressionException {
        String xmlString = xmlWithNoNamespace();

        testSelectMultipleStringValues(xmlString, null);
    }

    @Test
    public void selectsMultipleStringValuesByXPathExpression() throws XPathExpressionException {
        String xmlString = xmlWithNoNamespace();

        List<String> values = testSelectMultipleStringValues(xmlString, "/data/node()");
        assertEquals("Should have found two values", 2, values.size());
        assertEquals("value1", values.get(0));
        assertEquals("value2", values.get(1));
    }

    @Test
    public void selectsMultipleStringValuesByXPathExpressionWithNamespace() throws XPathExpressionException {
        String xmlString = xmlWithSampleNamespace();

        List<String> values = testSelectMultipleStringValues(xmlString, "/ns2:data/node()", new NamespaceDefinition("ns2", SAMPLE_NS_URI));
        assertEquals("Should have found two values", 2, values.size());
        assertEquals("value1", values.get(0));
        assertEquals("value2", values.get(1));
    }

    @Test
    public void returnsEmptyListForNonExistingMultiStringValueExpression() throws XPathExpressionException {
        String xmlString = xmlWithNoNamespace();

        List<String> values = testSelectMultipleStringValues(xmlString, "/data/notExisting");

        assertEquals(0, values.size());
    }

    @Test(expected = NullPointerException.class)
    public void selectingNodesFailsWithNullSourceXml() throws XPathExpressionException {
        testSelectNodes(null, "/data/field1");
    }

    @Test(expected = NullPointerException.class)
    public void selectingNodesFailsWithNullExpression() throws XPathExpressionException {
        String xmlString = xmlWithNoNamespace();

        testSelectNodes(xmlString, null);
    }

    @Test
    public void selectsMultipleNodesByXPathExpression() throws XPathExpressionException {
        String xmlString = xmlWithNoNamespace();

        NodeList nodes = testSelectNodes(xmlString, "/data/node()");
        assertEquals("Should have found two nodes", 2, nodes.getLength());
        assertNode(nodes.item(0), "field1", "value1");
        assertNode(nodes.item(1), "field2", "value2");
    }

    @Test
    public void selectsMultipleNodesByXPathExpressionWithNamespace() throws XPathExpressionException {
        String xmlString = xmlWithSampleNamespace();

        NodeList nodes = testSelectNodes(xmlString, "/ns1:data/node()", new NamespaceDefinition("ns1", SAMPLE_NS_URI));
        assertEquals("Should have found two nodes", 2, nodes.getLength());
    }

    @Test
    public void returnsEmptyListForNonExistingNodeExpression() throws XPathExpressionException {
        String xmlString = xmlWithNoNamespace();

        NodeList nodes = testSelectNodes(xmlString, "/data/notExisting");

        assertEquals(0, nodes.getLength());
    }

    private void assertNode(Node node, String expectedName, String expectedTextContent) {
        assertEquals(expectedName, node.getLocalName());
        assertEquals(expectedTextContent, node.getTextContent());
    }

    private NodeList testSelectNodes(String sourceXml, String expression, NamespaceDefinition... nsDefinitions) throws XPathExpressionException {
        return XPathUtil.selectNodes(sourceXml, expression, nsDefinitions);
    }


    private String testSelectStringValue(String sourceXml, String expression, NamespaceDefinition... nsDefinitions) throws XPathExpressionException {
        return XPathUtil.selectStringValue(sourceXml, expression, nsDefinitions);
    }

    private List<String> testSelectMultipleStringValues(String sourceXml, String expression, NamespaceDefinition... nsDefinitions) throws XPathExpressionException {
        return XPathUtil.selectStringValues(sourceXml, expression, nsDefinitions);
    }

    private String xmlWithNoNamespace() {
        return "<data>" + "<field1>value1</field1>" + "<field2>value2</field2>" + "</data>";
    }

    private String xmlWithSampleNamespace() {
        return "<data xmlns=\"" + SAMPLE_NS_URI + "\">" + "<field1>value1</field1>" + "<field2>value2</field2>" + "</data>";
    }
}
