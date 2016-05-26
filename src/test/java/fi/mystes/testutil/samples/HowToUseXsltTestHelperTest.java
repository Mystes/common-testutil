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
package fi.mystes.testutil.samples;

import fi.mystes.testutil.xpath.NamespaceDefinition;
import fi.mystes.testutil.xpath.XPathUtil;
import fi.mystes.testutil.xslt.XsltTestHelper;
import fi.mystes.testutil.ResourceProvider;
import fi.mystes.testutil.StreamHelper;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static fi.mystes.testutil.StreamHelper.stringAsInputStream;
import static org.junit.Assert.assertEquals;

public class HowToUseXsltTestHelperTest {

    @Test
    public void howToTestSimpleXsltTransformation() throws TransformerException, XPathExpressionException {
        // GIVEN: A source XML. In real life this might be a test resource file or something build using
        // e.g. a JAXB based POJO XML builder
        InputStream sourceXml = StreamHelper.stringAsInputStream(sampleXmlString());

        // WHEN: The XSLT transformation is performed using XsltTestHelper.
        // xsltStream here is the XSLT you are developing/testing.
        // You can use ResourceProvider to access it if the XSLT is a file that exists in classpath
        InputStream xsltStream = ResourceProvider.classpathResource("SampleXsltNoNamespaces.xsl");
        String result = XsltTestHelper.transform(sourceXml, xsltStream);

        // THEN: we verify that the XSLT did what it was supposed to using e.g. XPath validations
        Assert.assertEquals("value1", XPathUtil.selectStringValue(result, "/transformedData/transformedField1"));
        assertEquals("value2", XPathUtil.selectStringValue(result, "/transformedData/transformedField2"));
    }

    @Test
    public void howToTestXsltTransformationWithParameters() throws TransformerException, XPathExpressionException {
        // GIVEN: The source XML (same as in previous test)
        InputStream sourceXml = StreamHelper.stringAsInputStream(sampleXmlString());

        // WHEN: Let's read the XSLT file
        InputStream xsltStream = ResourceProvider.classpathResource("SampleXsltNoNamespaces.xsl");
        // AND: This time we need to pass some parameters to the XSLT
        Map<String, String> params = new HashMap<String, String>();
        params.put("param1", "paramValue1");
        params.put("param2", "paramValue2");
        String result = XsltTestHelper.transform(sourceXml, xsltStream, params);

        // THEN: XPath validations again - this time targeted to the parameter values
        assertEquals("paramValue1", XPathUtil.selectStringValue(result, "/transformedData/parameterValue1"));
        assertEquals("paramValue2", XPathUtil.selectStringValue(result, "/transformedData/parameterValue2"));
    }

    @Test
    public void howToTestXsltWithNamespace() throws TransformerException, XPathExpressionException {
        // GIVEN: The source XML (same as in previous tests)
        InputStream sourceXml = StreamHelper.stringAsInputStream(sampleXmlString());

        // WHEN: Let's read the XSLT file and perform the transformation.
        // Note that this time the XSLT is different - it has a target namespace
        InputStream xsltStream = ResourceProvider.classpathResource("SampleXsltWithNamespaces.xsl");
        String result = XsltTestHelper.transform(sourceXml, xsltStream);

        // THEN: XPath validations - we need to pass the namespace definition to XPathUtil since
        // we use a namespace prefix in XPath expressions
        NamespaceDefinition nsDef = new NamespaceDefinition("sample", "http://sample.schema.mystes.fi");
        assertEquals("value1", XPathUtil.selectStringValue(result, "/sample:transformedData/sample:transformedField1", nsDef));
        assertEquals("value2", XPathUtil.selectStringValue(result, "/sample:transformedData/sample:transformedField2", nsDef));
    }

    private String sampleXmlString() {
        return "<testData>" + "<field1>value1</field1>" + "<field2>value2</field2>" + "</testData>";
    }
}
