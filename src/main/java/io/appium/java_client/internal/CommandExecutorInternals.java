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

package io.appium.java_client.internal;

import io.appium.java_client.remote.AppiumCommandExecutor;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * Internal bridge exposing private command executor state without widening public API.
 *
 * @deprecated The logs broadcasting web socket of the Appium drivers is deprecated and will be removed
 *     together with this API. Subscribe to the BiDi {@code log.entryAdded} event instead, see the
 *     <a href="https://github.com/appium/java-client/blob/master/docs/selenium-bridge.md#bidi">Selenium
 *     bridge documentation</a>.
 */
@NullMarked
@Deprecated(since = "11.1.0", forRemoval = true)
@SuppressWarnings("removal")
public final class CommandExecutorInternals {
    private static final AtomicReference<@Nullable Function<AppiumCommandExecutor, @Nullable URL>>
            INITIAL_SERVER_URL_RESOLVER = new AtomicReference<>();

    private CommandExecutorInternals() {
    }

    /**
     * Registers the internal resolver that reads an executor's initial server URL before any URL overrides.
     *
     * @param lookup a private lookup belonging to {@link AppiumCommandExecutor}
     * @param resolver the function returning the initial server URL for an executor
     * @throws SecurityException if {@code lookup} does not have private access to {@link AppiumCommandExecutor}
     */
    @Deprecated(since = "11.1.0", forRemoval = true)
    public static void registerInitialServerUrlResolver(
            MethodHandles.Lookup lookup,
            Function<AppiumCommandExecutor, @Nullable URL> resolver
    ) {
        requireNonNull(lookup, "Lookup");
        requireNonNull(resolver, "Resolver");
        if (lookup.lookupClass() != AppiumCommandExecutor.class
                || (lookup.lookupModes() & MethodHandles.Lookup.PRIVATE) == 0) {
            throw new SecurityException(
                    "Only AppiumCommandExecutor may register the initial server URL resolver"
            );
        }
        INITIAL_SERVER_URL_RESOLVER.compareAndSet(null, resolver);
    }

    /**
     * Returns the server URL configured on {@code executor} before any {@code directConnect} or
     * {@code overrideServerUrl} override took effect.
     *
     * @param executor the command executor to inspect
     * @return the initial server URL, or {@code null} if none was configured
     */
    @Nullable
    @Deprecated(since = "11.1.0", forRemoval = true)
    public static URL getInitialServerUrl(AppiumCommandExecutor executor) {
        requireNonNull(executor, "Command executor");
        Function<AppiumCommandExecutor, @Nullable URL> resolver = INITIAL_SERVER_URL_RESOLVER.get();
        return resolver != null ? resolver.apply(executor) : executor.getAddressOfRemoteServer();
    }
}
