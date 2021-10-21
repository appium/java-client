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

package io.appium.java_client.remote;

import com.google.common.io.CountingOutputStream;
import com.google.common.io.FileBackedOutputStream;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.internal.Either;
import org.openqa.selenium.json.Json;
import org.openqa.selenium.json.JsonOutput;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.NewSessionPayload;
import org.openqa.selenium.remote.ProtocolHandshake;
import org.openqa.selenium.remote.http.HttpHandler;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

@SuppressWarnings("UnstableApiUsage")
public class AppiumProtocolHandshake extends ProtocolHandshake {
    private static void writeJsonPayload(NewSessionPayload srcPayload, Appendable destination) {
        try (JsonOutput json = new Json().newOutput(destination)) {
            json.beginObject();

            json.name("capabilities");
            json.beginObject();

            json.name("firstMatch");
            json.beginArray();
            json.endArray();

            json.name("alwaysMatch");
            try {
                Method getW3CMethod = NewSessionPayload.class.getDeclaredMethod("getW3C");
                getW3CMethod.setAccessible(true);
                //noinspection unchecked
                ((Stream<Map<String, Object>>) getW3CMethod.invoke(srcPayload)).forEach(json::write);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new WebDriverException(e);
            }

            json.endObject();  // Close "capabilities" object

            try {
                Method writeMetaDataMethod = NewSessionPayload.class.getDeclaredMethod(
                        "writeMetaData", JsonOutput.class);
                writeMetaDataMethod.setAccessible(true);
                writeMetaDataMethod.invoke(srcPayload, json);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new WebDriverException(e);
            }

            json.endObject();
        }
    }

    @Override
    public Result createSession(HttpHandler client, Command command) throws IOException {
        //noinspection unchecked
        Capabilities desired = ((Set<Map<String, Object>>) command.getParameters().get("capabilities"))
                .stream()
                .findAny()
                .map(ImmutableCapabilities::new)
                .orElseGet(ImmutableCapabilities::new);
        try (NewSessionPayload payload = NewSessionPayload.create(desired)) {
            Either<SessionNotCreatedException, Result> result = createSession(client, payload);
            if (result.isRight()) {
                return result.right();
            }
            throw result.left();
        }
    }

    @Override
    public Either<SessionNotCreatedException, Result> createSession(
            HttpHandler client, NewSessionPayload payload) throws IOException {
        int threshold = (int) Math.min(Runtime.getRuntime().freeMemory() / 10, Integer.MAX_VALUE);
        FileBackedOutputStream os = new FileBackedOutputStream(threshold);

        try (CountingOutputStream counter = new CountingOutputStream(os);
             Writer writer = new OutputStreamWriter(counter, UTF_8)) {
            writeJsonPayload(payload, writer);

            try (InputStream rawIn = os.asByteSource().openBufferedStream();
                 BufferedInputStream contentStream = new BufferedInputStream(rawIn)) {
                Method createSessionMethod = ProtocolHandshake.class.getDeclaredMethod("createSession",
                        HttpHandler.class, InputStream.class, long.class);
                createSessionMethod.setAccessible(true);
                //noinspection unchecked
                return (Either<SessionNotCreatedException, Result>) createSessionMethod.invoke(
                        this, client, contentStream, counter.getCount()
                );
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new WebDriverException(e);
            }
        } finally {
            os.reset();
        }
    }
}
