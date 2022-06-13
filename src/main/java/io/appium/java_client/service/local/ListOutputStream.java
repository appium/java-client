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

package io.appium.java_client.service.local;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ListOutputStream extends OutputStream {

    private final List<OutputStream> streams = new ArrayList<>();

    ListOutputStream add(OutputStream stream) {
        streams.add(stream);
        return this;
    }

    Optional<OutputStream> remove(OutputStream stream) {
        return streams.remove(stream) ? Optional.of(stream) : Optional.empty();
    }

    @Override public void write(int i) throws IOException {
        for (OutputStream stream : streams) {
            stream.write(i);
        }
    }

    @Override public void write(byte[] var1) throws IOException {
        for (OutputStream stream : streams) {
            stream.write(var1, 0, var1.length);
        }
    }

    @Override public void write(byte[] var1, int var2, int var3) throws IOException {
        for (OutputStream stream : streams) {
            stream.write(var1, var2, var3);
        }
    }

    public void flush() throws IOException {
        for (OutputStream stream : streams) {
            stream.flush();
        }
    }

    public void close() throws IOException {
        for (OutputStream stream : streams) {
            stream.close();
        }
    }

    /**
     * Clears all the existing output streams.
     *
     * @return true if at least one output stream has been cleared
     */
    public boolean clear() {
        if (streams.isEmpty()) {
            return false;
        }
        streams.clear();
        return true;
    }
}
