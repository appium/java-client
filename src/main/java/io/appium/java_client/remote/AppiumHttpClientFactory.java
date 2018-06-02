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

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import com.google.common.base.Strings;

import okhttp3.ConnectionPool;
import okhttp3.Credentials;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.internal.OkHttpClient;

import java.net.URL;
import java.time.Duration;
import java.util.Objects;

/**
 * We must use the customized factory, because the default one
 * inside Selenium has retryOnConnectionFailure set to false
 * which causes unexpected connection issues, for example:
 * https://github.com/appium/java-client/issues/927
 */
public class AppiumHttpClientFactory extends OkHttpClient.Factory {

    private final ConnectionPool pool = new ConnectionPool();
    private final long connectionTimeout;
    private final long readTimeout;

    public AppiumHttpClientFactory() {
        this(Duration.ofMinutes(2), Duration.ofHours(3));
    }

    /**
     * Creates a factory instance for HttpOK client with customized
     * Appium config.
     *
     * @param connectionTimeout http connection timeout
     * @param readTimeout http read timeout
     */
    public AppiumHttpClientFactory(Duration connectionTimeout, Duration readTimeout) {
        Objects.requireNonNull(connectionTimeout, "Connection timeout cannot be null");
        Objects.requireNonNull(readTimeout, "Read timeout cannot be null");

        this.connectionTimeout = connectionTimeout.toMillis();
        this.readTimeout = readTimeout.toMillis();
    }

    @Override
    public HttpClient createClient(URL url) {
        okhttp3.OkHttpClient.Builder client = new okhttp3.OkHttpClient.Builder()
                .connectionPool(pool)
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .readTimeout(readTimeout, MILLISECONDS)
                .connectTimeout(connectionTimeout, MILLISECONDS);

        String info = url.getUserInfo();
        if (!Strings.isNullOrEmpty(info)) {
            String[] parts = info.split(":", 2);
            String user = parts[0];
            String pass = parts.length > 1 ? parts[1] : null;

            String credentials = Credentials.basic(user, pass);

            client.authenticator((route, response) -> {
                if (response.request().header("Authorization") != null) {
                    return null; // Give up, we've already attempted to authenticate.
                }

                return response.request().newBuilder()
                        .header("Authorization", credentials)
                        .build();
            });
        }

        return new OkHttpClient(client.build(), url);
    }

    @Override
    public void cleanupIdleClients() {
        pool.evictAll();
    }
}
