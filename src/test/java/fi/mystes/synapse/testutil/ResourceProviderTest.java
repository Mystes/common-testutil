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
package fi.mystes.synapse.testutil;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import static org.junit.Assert.*;

public class ResourceProviderTest {

    @Test
    public void returnsNullInputStreamForNotExistingClassPathResource() {
        InputStream is = streamFromResourceProvider("notExistingFile.txt");
        assertNull( "InputStream should have been null", is );
    }

    @Test
    public void returnsNonNullInputStreamForExistingClassPathResource() throws IOException {
        InputStream is = streamFromResourceProvider("test.txt");
        assertNotNull("InputStream shouldn't have been null", is);
        is.close();
    }

    @Test
    public void returnsValidContentInClassPathResourceStream() throws IOException {
        InputStream is = streamFromResourceProvider("test.txt");
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(is));
        String line = reader.readLine();
        assertEquals("Unexpected file content", "Test", line);
        assertNull("No more content expected", reader.readLine());
                reader.close();
    }

    private InputStream streamFromResourceProvider( String name ) {
        return ResourceProvider.classpathResource(name);
    }
}
