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
package fi.mystes.testutil;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import static org.junit.Assert.*;

public class StreamHelperTest {

    @Test(expected = NullPointerException.class)
    public void stringAsInputStreamFailsForNullString() {
        stringAsInputStream(null);
    }

    @Test
    public void returnsValidContentInInputStreamFromString() throws IOException {
        String string = "Test" + System.getProperty("line.separator") + "String";
        InputStream is = stringAsInputStream(string);
        assertNotNull("InputStream shouldn't have been null", is);
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(is));
        assertEquals("Unexpected content at first row", "Test", reader.readLine());
        assertEquals("Unexpected content at second row", "String", reader.readLine());
        assertNull("No more content expected", reader.readLine());
        reader.close();
    }

    @Test
    public void returnsValidContentInInputStreamFromStringWithEncoding() throws IOException {
        String encoding = "ISO-8859-15";
        String string = "äää" + System.getProperty("line.separator") + "ööö€";
        InputStream is = stringAsInputStream(string, encoding);
        assertNotNull("InputStream shouldn't have been null", is);
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(is, encoding));
        assertEquals("Unexpected content at first row", "äää", reader.readLine());
        assertEquals("Unexpected content at second row", "ööö€", reader.readLine());
        assertNull("No more content expected", reader.readLine());
        reader.close();
    }

    @Test
    public void returnsValidContentInStringFromInputStream() throws IOException {
        String string = "Test String";
        InputStream is = stringAsInputStream(string);
        String result = inputStreamAsString(is);
        assertEquals("Unexpected content in result",string, result);
    }

    private InputStream stringAsInputStream(String string) {
        return StreamHelper.stringAsInputStream(string);
    }

    private InputStream stringAsInputStream(String string, String encoding) throws IOException {
        return StreamHelper.stringAsInputStream(string, encoding);
    }

    private String inputStreamAsString(InputStream input) throws IOException { return StreamHelper.inputStreamAsString(input);}
}
