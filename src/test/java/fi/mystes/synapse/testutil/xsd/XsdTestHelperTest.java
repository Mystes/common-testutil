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
package fi.mystes.synapse.testutil.xsd;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static fi.mystes.synapse.testutil.ResourceProvider.classpathResource;
import static org.junit.Assert.*;

public class XsdTestHelperTest {

    InputStream finvoiceXsd;

    @Before
    public void setUp() {
        finvoiceXsd = classpathResource("XsdTestSchema.xsd");
    }

    @Test
    public void shouldReturnTrueWhenXmlIsValid() {
        InputStream validXml = classpathResource("ValidXsdTest.xml");
        assertTrue(XsdTestHelper.validateAgainstXSD(validXml, finvoiceXsd));
    }

    @Test
    public void shouldReturnFalseWhenXmlIsInvalid() {
        InputStream invalidXml = classpathResource("InvalidXsdTest.xml");
        assertFalse(XsdTestHelper.validateAgainstXSD(invalidXml, finvoiceXsd));
    }

}