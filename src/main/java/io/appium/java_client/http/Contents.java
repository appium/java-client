/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
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

package io.appium.java_client.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Factory methods for HTTP message bodies.
 */
public final class Contents {

    /**
     * A body that can be read more than once if it is backed by bytes.
     */
    public interface Supplier extends java.util.function.Supplier<InputStream>, AutoCloseable {
        /**
         * The body length in bytes.
         *
         * @return the length or -1 if unknown
         */
        long length();

        @Override
        void close() throws IOException;

        /**
         * Reads the whole body as a string.
         *
         * @param charset the charset to decode the body with
         * @return the body as string
         */
        String contentAsString(Charset charset);

        /**
         * Opens a reader over the body.
         *
         * @param charset the charset to decode the body with
         * @return the reader
         */
        default Reader reader(Charset charset) {
            return new InputStreamReader(get(), charset);
        }
    }

    private Contents() {
    }

    /**
     * Creates an empty body.
     *
     * @return the empty body
     */
    public static Supplier empty() {
        return bytes(new byte[0]);
    }

    /**
     * Creates a body from the given bytes.
     *
     * @param bytes the body bytes (copied)
     * @return the body
     */
    public static Supplier bytes(byte[] bytes) {
        return new BytesSupplier(bytes);
    }

    /**
     * Reads the whole body.
     *
     * @param supplier the body
     * @return the body bytes
     */
    public static byte[] bytes(Supplier supplier) {
        try (InputStream in = supplier.get()) {
            return in.readAllBytes();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Creates a body from the given string encoded as UTF-8.
     *
     * @param value the body text
     * @return the body
     */
    public static Supplier utf8String(CharSequence value) {
        return string(value, UTF_8);
    }

    /**
     * Reads the whole body as a UTF-8 string.
     *
     * @param supplier the body
     * @return the body as string
     */
    public static String utf8String(Supplier supplier) {
        return supplier.contentAsString(UTF_8);
    }

    /**
     * Creates a body from the given string.
     *
     * @param value   the body text
     * @param charset the charset to encode the text with
     * @return the body
     */
    public static Supplier string(CharSequence value, Charset charset) {
        return bytes(value.toString().getBytes(charset));
    }

    /**
     * The message body decoded with the encoding declared by the message.
     *
     * @param message the HTTP message
     * @return the body as string
     */
    public static String string(HttpMessage<?> message) {
        return message.contentAsString();
    }

    /**
     * Creates a body backed by a stream, which can be read only once.
     *
     * @param stream the body stream
     * @param length the body length in bytes or -1 if unknown
     * @return the body
     */
    public static Supplier fromStream(InputStream stream, long length) {
        return new StreamSupplier(stream, length);
    }

    private static final class BytesSupplier implements Supplier {
        private final byte[] bytes;

        BytesSupplier(byte[] bytes) {
            this.bytes = bytes.clone();
        }

        @Override
        public InputStream get() {
            return new ByteArrayInputStream(bytes);
        }

        @Override
        public long length() {
            return bytes.length;
        }

        @Override
        public void close() {
        }

        @Override
        public String contentAsString(Charset charset) {
            return new String(bytes, charset);
        }

        @Override
        public String toString() {
            return new String(bytes, UTF_8);
        }
    }

    private static final class StreamSupplier implements Supplier {
        private final InputStream stream;
        private final long length;

        StreamSupplier(InputStream stream, long length) {
            this.stream = stream;
            this.length = length;
        }

        @Override
        public InputStream get() {
            return stream;
        }

        @Override
        public long length() {
            return length;
        }

        @Override
        public void close() throws IOException {
            stream.close();
        }

        @Override
        public String contentAsString(Charset charset) {
            try (InputStream in = stream) {
                return new String(in.readAllBytes(), charset);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        @Override
        public String toString() {
            return "(binary stream)";
        }
    }
}
