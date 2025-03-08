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

package io.appium.java_client.plugins.storage;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.json.Json;
import org.openqa.selenium.remote.ErrorCodec;
import org.openqa.selenium.remote.codec.AbstractHttpResponseCodec;
import org.openqa.selenium.remote.codec.w3c.W3CHttpResponseCodec;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.Contents;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpHeader;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.WebSocket;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static io.appium.java_client.plugins.storage.StorageUtils.calcSha1Digest;
import static io.appium.java_client.plugins.storage.StorageUtils.streamFileToWebSocket;

/**
 * This is a Java implementation of the Appium server storage plugin client.
 * See <a href="https://github.com/appium/appium/blob/master/packages/storage-plugin/README.md">the plugin README</a>
 * for more details.
 */
public class StorageClient {
    public static final String PREFIX = "/storage";
    private final Json json = new Json();
    private final AbstractHttpResponseCodec responseCodec = new W3CHttpResponseCodec();
    private final ErrorCodec errorCodec = ErrorCodec.createDefault();

    private final URL baseUrl;
    private final HttpClient httpClient;

    public StorageClient(URL baseUrl) {
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.Factory.createDefault().createClient(baseUrl);
    }

    public StorageClient(ClientConfig clientConfig) {
        this.httpClient = HttpClient.Factory.createDefault().createClient(clientConfig);
        this.baseUrl = clientConfig.baseUrl();
    }

    /**
     * Adds a local file to the server storage.
     * The remote file name is be set to the same value as the local file name.
     *
     * @param file File instance.
     */
    public void add(File file) {
        add(file, file.getName());
    }

    /**
     * Adds a local file to the server storage.
     *
     * @param file File instance.
     * @param name The remote file name.
     */
    public void add(File file, String name) {
        var request = new HttpRequest(HttpMethod.POST, formatPath(baseUrl, PREFIX, "add").toString());
        var httpResponse = httpClient.execute(setJsonPayload(request, Map.of(
                "name", name,
                "sha1", calcSha1Digest(file)
        )));
        Map<String, Object> value = requireResponseValue(httpResponse);
        final var wsTtlMs = (Long) value.get("ttlMs");
        //noinspection unchecked
        var wsInfo = (Map<String, Object>) value.get("ws");
        var streamWsPathname = (String) wsInfo.get("stream");
        var eventWsPathname = (String) wsInfo.get("events");
        final var completion = new CountDownLatch(1);
        final var lastException = new AtomicReference<Throwable>(null);
        try (var streamWs = httpClient.openSocket(
                new HttpRequest(HttpMethod.POST, formatPath(baseUrl, streamWsPathname).toString()),
                new WebSocket.Listener() {}
        ); var eventsWs = httpClient.openSocket(
                new HttpRequest(HttpMethod.POST, formatPath(baseUrl, eventWsPathname).toString()),
                new EventWsListener(lastException, completion)
        )) {
            streamFileToWebSocket(file, streamWs);
            streamWs.close();
            if (!completion.await(wsTtlMs, TimeUnit.MILLISECONDS)) {
                throw new IllegalStateException(String.format(
                        "Could not receive a confirmation about adding '%s' to the server storage within %sms timeout",
                        name, wsTtlMs
                ));
            }
            var exc = lastException.get();
            if (exc != null) {
                throw exc instanceof RuntimeException ? (RuntimeException) exc : new WebDriverException(exc);
            }
        } catch (InterruptedException e) {
            throw new WebDriverException(e);
        }
    }

    /**
     * Lists items that exist in the storage.
     *
     * @return All storage items.
     */
    public List<StorageItem> list() {
        var request = new HttpRequest(HttpMethod.GET, formatPath(baseUrl, PREFIX, "list").toString());
        var httpResponse = httpClient.execute(request);
        List<Map<String, Object>> items = requireResponseValue(httpResponse);
        return items.stream().map(item -> new StorageItem(
                (String) item.get("name"),
                (String) item.get("path"),
                (Long) item.get("size")
        )).collect(Collectors.toList());
    }

    /**
     * Deletes an item from the server storage.
     *
     * @param name The name of the item to be deleted.
     * @return true if the dletion was successful.
     */
    public boolean delete(String name) {
        var request = new HttpRequest(HttpMethod.POST, formatPath(baseUrl, PREFIX, "delete").toString());
        var httpResponse = httpClient.execute(setJsonPayload(request, Map.of(
                "name", name
        )));
        return requireResponseValue(httpResponse);
    }

    /**
     * Resets all items of the server storage.
     */
    public void reset() {
        var request = new HttpRequest(HttpMethod.POST, formatPath(baseUrl, PREFIX, "reset").toString());
        var httpResponse = httpClient.execute(request);
        requireResponseValue(httpResponse);
    }

    private static URL formatPath(URL url, String... suffixes) {
        if (suffixes.length == 0) {
            return url;
        }
        try {
            var uri = url.toURI();
            var updatedPath = (uri.getPath() + "/" + String.join("/", suffixes)).replaceAll("(/{2,})", "/");
            return new URI(
                    uri.getScheme(),
                    uri.getAuthority(),
                    uri.getHost(),
                    uri.getPort(),
                    updatedPath,
                    uri.getQuery(),
                    uri.getFragment()
            ).toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private HttpRequest setJsonPayload(HttpRequest request, Map<String, Object> payload) {
        var strData = json.toJson(payload);
        var data = strData.getBytes(StandardCharsets.UTF_8);
        request.setHeader(HttpHeader.ContentLength.getName(), String.valueOf(data.length));
        request.setHeader(HttpHeader.ContentType.getName(), "application/json; charset=utf-8");
        request.setContent(Contents.bytes(data));
        return request;
    }

    private <T> T requireResponseValue(HttpResponse httpResponse) {
        var response = responseCodec.decode(httpResponse);
        var value = response.getValue();
        if (value instanceof WebDriverException) {
            throw (WebDriverException) value;
        }
        //noinspection unchecked
        return (T) response.getValue();
    }

    private final class EventWsListener implements WebSocket.Listener {
        private final AtomicReference<Throwable> lastException;
        private final CountDownLatch completion;

        public EventWsListener(AtomicReference<Throwable> lastException, CountDownLatch completion) {
            this.lastException = lastException;
            this.completion = completion;
        }

        @Override
        public void onBinary(byte[] data) {
            extractException(new String(data, StandardCharsets.UTF_8)).ifPresent(lastException::set);
            completion.countDown();
        }

        @Override
        public void onText(CharSequence data) {
            extractException(data.toString()).ifPresent(lastException::set);
            completion.countDown();
        }

        @Override
        public void onError(Throwable cause) {
            lastException.set(cause);
            completion.countDown();
        }

        private Optional<WebDriverException> extractException(String payload) {
            try {
                Map<String, Object> record = json.toType(payload, Json.MAP_TYPE);
                //noinspection unchecked
                var value = (Map<String, Object>) record.get("value");
                if ((Boolean) value.get("success")) {
                    return Optional.empty();
                }
                return Optional.of(errorCodec.decode(record));
            } catch (Exception e) {
                return Optional.of(new WebDriverException(payload, e));
            }
        }
    }
}
