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

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Helper class to transform InputStream to String and also other way.
 *
 */
public class StreamHelper {

    /**
     * Transforms given String to InputStream.
     *
     * @param string
     *          The String to transform to InputStream
     * @return InputStream of the given String
     */
    public static InputStream stringAsInputStream(String string) {
        if( string == null ) {
            throw new NullPointerException("refusing to create InputStream from null string");
        }

        return IOUtils.toInputStream(string);
    }

    /**
     * Transforms given String to InputStream using given encoding.
     *
     * @param string
     *          The String to transform to InputStream
     * @param encoding
     *          Valid String defining target encoding, e.g. 'UTF-8'
     * @return InputStream of the given String with given encoding.
     * @throws IOException
     *              If given encoding not valid.
     */
    public static InputStream stringAsInputStream(String string, String encoding) throws IOException {
        if( string == null ) {
            throw new NullPointerException("refusing to create InputStream from null string");
        }

        return IOUtils.toInputStream(string, encoding);
    }

    /**
     * Transforms given InputStream to String.
     *
     * @param input
     *          The InputStream to transform to String
     *
     * @return String of the given InputStream in UTF-8.
     * @throws IOException
     */
    public static String inputStreamAsString(InputStream input) throws IOException {
        if ( input == null) {
            throw new NullPointerException("refusing to create String from null InputStream");
        }
        return IOUtils.toString(input, "UTF-8");
    }
}
