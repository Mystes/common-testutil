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
package fi.mystes.testutil.xslt;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Helper class for XSLT transformations.
 *
 */
public class XsltTestHelper {

    private XsltTestHelper() {
        // suppress default constructor to prevent instantiation
        // as this class contains static utility methods only
    }

    /**
     * Does a XSLT transformation to the given source XML.
     *
     * @param sourceXml
     *          The source XML to be transformed.
     * @param xslt
     *          The XSLT transformation document.
     * @param encoding
     *          Encoding used in transformation. If not given, then default system charset used.
     * @param parameters
     *          Parameters for the XSLT transformation.
     * @return Transformed result xml.
     * @throws TransformerException
     * @throws UnsupportedEncodingException
     */
    public static String transformWithEncoding(InputStream sourceXml, InputStream xslt, String encoding, Map<String, String> parameters) throws TransformerException, UnsupportedEncodingException {
        if (sourceXml == null) {
            throw new NullPointerException("refusing to transform null sourceXml");
        }

        if (xslt == null) {
            throw new NullPointerException("refusing to transform with null xslt");
        }

        Charset charset = encoding == null ? Charset.defaultCharset() : Charset.forName(encoding);
        Source xmlSource = new StreamSource(new InputStreamReader(sourceXml, charset) );
        Source xsltSource = new StreamSource(xslt);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        TransformerFactory factory = new net.sf.saxon.TransformerFactoryImpl();
        factory.setURIResolver(new ClasspathResourceURIResolver());

        Transformer transformer = factory.newTransformer(xsltSource);

        if (parameters != null) {
            for (String key : parameters.keySet()) {
                transformer.setParameter(key, parameters.get(key));
            }
        }

        transformer.transform(xmlSource, new StreamResult(output));

        return encoding == null ? output.toString() : output.toString(encoding);
    }

    /**
     * Does a XSLT transformation to a XML file and writes the output to a new XML file.
     *
     * @param sourceXmlFile
     *          Path to the source XML file.
     * @param xsltFile
     *          Path to the XSLT file.
     * @param outputFile
     *          Path to the desired output file.
     * @throws IOException
     * @throws TransformerException
     */
    public static void transformToFile(String sourceXmlFile, String xsltFile, String outputFile) throws IOException, TransformerException {
        Source xmlSource = new StreamSource(new File(sourceXmlFile));

        Source xsltSource = new StreamSource(new File(xsltFile));
        FileOutputStream output = new FileOutputStream(outputFile);

        TransformerFactory factory = new net.sf.saxon.TransformerFactoryImpl();
        factory.setURIResolver(new ClasspathResourceURIResolver());

        Transformer optimusPrime = factory.newTransformer(xsltSource);

        optimusPrime.transform(xmlSource, new StreamResult(output));
        output.close();

    }

    /**
     * Does a XSLT transformation to a XML.
     *
     * @param sourceXml
     *          The XML to be transformed.
     * @param xslt
     *          The XSLT document that should be used in transformation.
     * @return Transformed result xml.
     * @throws TransformerException
     */
    public static String transform(InputStream sourceXml, InputStream xslt) throws TransformerException {
        return transform(sourceXml, xslt, null);
    }

    /**
     * Does a XSLT transformation to a XML with given encoding.
     *
     * @param sourceXml
     *          The XML to be transformed.
     * @param xslt
     *          The XSLT document that should be used in transformation.
     * @param resultEncoding
     *          The encoding of the result XML.
     * @return Transformed result xml.
     * @throws TransformerException
     * @throws UnsupportedEncodingException
     */
    public static String transformWithEncoding(InputStream sourceXml, InputStream xslt, String resultEncoding) throws TransformerException, UnsupportedEncodingException {
        return transformWithEncoding(sourceXml, xslt, resultEncoding, null);
    }

    /**
     * Does a XSLT transformation to a XML with given XSLT parameters.
     *
     * @param sourceXml
     *          The XML to be transformed.
     * @param xslt
     *          The XSLT document that should be used in transformation.
     * @param params
     *          Parameters for the XSLT transformation.
     * @return Transformed result xml.
     * @throws TransformerException
     */
    public static String transform(InputStream sourceXml, InputStream xslt, Map<String,String> params) throws TransformerException {
        try {
            return transformWithEncoding(sourceXml, xslt, null, params);
        } catch (UnsupportedEncodingException e) {
            // since we pass null as resultencoding this exception cannot happen
            throw new RuntimeException("this should never happen.", e);
        }
    }

    /**
     * Classpath resource reader for the XSLT transformation factory.
     */
    private static class ClasspathResourceURIResolver implements URIResolver {
        @Override
        public Source resolve(String href, String base) throws TransformerException {
            return new StreamSource(XsltTestHelper.class.getClassLoader().getResourceAsStream(href));
        }
    }
}
