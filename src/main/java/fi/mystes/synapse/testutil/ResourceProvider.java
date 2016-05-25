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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringBufferInputStream;

/**
 * Helper class to read a resource from classpath.
 *
 */
public class ResourceProvider {

    private ResourceProvider() {
        // suppress default constructor to prevent instantiation
        // as this class contains static utility methods only
    }

    /**
     * Reads a resource from classpath with given name.
     *
     * @param name
     *          Name of the resource.
     * @return InputStream to the resource.
     */
    public static InputStream classpathResource(String name) {
        return ResourceProvider.class.getClassLoader().getResourceAsStream(name);
    }

}
