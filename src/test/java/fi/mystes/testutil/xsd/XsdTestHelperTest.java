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
package fi.mystes.testutil.xsd;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import static fi.mystes.testutil.ResourceProvider.classpathResource;

public class XsdTestHelperTest {

    InputStream finvoiceXsd;

    @Before
    public void setUp() {
        finvoiceXsd = classpathResource("XsdTestSchema.xsd");
    }

    @Test
    public void shouldReturnTrueWhenXmlIsValid() throws IOException, SAXException {
        InputStream validXml = classpathResource("ValidXsdTest.xml");
        XsdTestHelper.validateAgainstXSD(validXml, finvoiceXsd);
    }

    @Test(expected = SAXException.class)
    public void shouldReturnFalseWhenXmlIsInvalid() throws IOException, SAXException {
        InputStream invalidXml = classpathResource("InvalidXsdTest.xml");
        XsdTestHelper.validateAgainstXSD(invalidXml, finvoiceXsd);
    }

}