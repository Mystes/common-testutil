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
package fi.mystes.synapse.testutil.xslt;

import fi.mystes.synapse.testutil.xpath.XPathUtil;
import org.junit.Test;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static fi.mystes.synapse.testutil.ResourceProvider.classpathResource;
import static fi.mystes.synapse.testutil.StreamHelper.stringAsInputStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class XsltTestHelperTest {

    @Test(expected = NullPointerException.class)
    public void refusesToTransformWithNullSourceXmlStream() throws TransformerException {
        InputStream xsltStream = readResource("SampleXsltNoNamespaces.xsl");
        testTransformWith(null, xsltStream);
    }

    @Test(expected = NullPointerException.class)
    public void refusesToTransformWithNullXsltStream() throws TransformerException {
        String xmlString = sampleXmlString();
        testTransformWith(stringAsInputStream(xmlString), null);
    }

    @Test
    public void transformsSourceXmlWithSpecifiedXslt() throws TransformerException, XPathExpressionException {
        String xmlString = sampleXmlString();
        InputStream xsltStream = readResource("SampleXsltNoNamespaces.xsl");
        String result = testTransformWith(stringAsInputStream(xmlString), xsltStream);
        assertEquals("value1", XPathUtil.selectStringValue(result, "/transformedData/transformedField1"));
        assertEquals("value2", XPathUtil.selectStringValue(result, "/transformedData/transformedField2"));
    }

    @Test
    public void transformsSourceXmlWithDateFormatFunction() throws TransformerException, XPathExpressionException {
        String xmlString = sampleXmlStringWithDate();
        InputStream xsltStream = readResource("SampleXsltWithDateFormat.xsl");
        String result = testTransformWith(stringAsInputStream(xmlString), xsltStream);
        assertEquals("1.8.2013", XPathUtil.selectStringValue(result, "/transformedData/transformedDate"));
    }

    @Test
    public void appliesSpecifiedParametersToTransformation() throws TransformerException, XPathExpressionException {
        String xmlString = sampleXmlString();
        InputStream xsltStream = readResource("SampleXsltNoNamespaces.xsl");
        Map<String,String> params = new HashMap<String,String>();
        params.put("param1", "paramValue1");
        params.put("param2", "paramValue2");
        String result = testTransformWith(stringAsInputStream(xmlString), xsltStream, params);
        assertEquals("paramValue1", XPathUtil.selectStringValue(result, "/transformedData/parameterValue1"));
        assertEquals("paramValue2", XPathUtil.selectStringValue(result, "/transformedData/parameterValue2"));
    }

    @Test
    public void appliesSpecifiedEncodingToTransformation() throws XPathExpressionException, TransformerException, IOException {
        String encoding = "ISO-8859-15";
        String xmlString = sampleXmlStringWithNonAsciiChars();
        InputStream xsltStream = readResource("SampleXsltWithIsoOutputEncoding.xsl");
        String result = testTransformWith(stringAsInputStream(xmlString, encoding), xsltStream, encoding);
        assertEquals("valueööäääöö", XPathUtil.selectStringValue(result, "/transformedData/transformedField1"));
        assertEquals("valueååååäääööö€", XPathUtil.selectStringValue(result, "/transformedData/transformedField2"));
    }

    @Test
    public void readsImportedXslFilesFromClassPath() throws TransformerException {
        String xmlString = sampleXmlString();
        InputStream xsltStream = readResource("SampleXsltWithImport.xsl");
        String result = testTransformWith(stringAsInputStream(xmlString), xsltStream);
        assertNotNull(result);
    }

    private String testTransformWith(InputStream xmlSourceStream, InputStream xsltStream) throws TransformerException {
        return XsltTestHelper.transform(xmlSourceStream, xsltStream);
    }

    private String testTransformWith(InputStream xmlSourceStream, InputStream xsltStream, String resultEncoding) throws TransformerException, UnsupportedEncodingException {
        return XsltTestHelper.transformWithEncoding(xmlSourceStream, xsltStream, resultEncoding);
    }

    private String testTransformWith(InputStream xmlSourceStream, InputStream xsltStream, Map<String,String> params) throws TransformerException {
        return XsltTestHelper.transform(xmlSourceStream, xsltStream, params);
    }

    private InputStream readResource(String name) {
        return classpathResource(name);
    }

    private String sampleXmlString() {
        return "<data>" + "<field1>value1</field1>" + "<field2>value2</field2>" + "</data>";
    }

    private String sampleXmlStringWithDate() {
        return "<data>" + "<date>2013-08-01</date>" + "</data>";
    }

    private String sampleXmlStringWithNonAsciiChars() {
        return "<data>" + "<field1>valueööäääöö</field1>" + "<field2>valueååååäääööö€</field2>" + "</data>";
    }
}
