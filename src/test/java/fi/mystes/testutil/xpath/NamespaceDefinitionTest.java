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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class NamespaceDefinitionTest {

    @Test(expected = NullPointerException.class)
    public void creationFailsWithNullPrefix() {
        instantiateWith(null, "http://test.mystes.fi");
    }

    @Test(expected = NullPointerException.class)
    public void creationFailsWithNullUri() {
        instantiateWith("ns1", null);
    }

    @Test
    public void returnsValidPrefixAndUri() {
        NamespaceDefinition ns = instantiateWith("ns1", "http://test.mystes.fi");

        assertEquals("ns1", ns.getPrefix());
        assertEquals("http://test.mystes.fi", ns.getUri());
    }

    private NamespaceDefinition instantiateWith( String prefix, String uri ) {
        return new NamespaceDefinition(prefix,uri);
    }

}
