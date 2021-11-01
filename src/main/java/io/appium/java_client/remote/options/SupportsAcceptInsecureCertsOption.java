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

package io.appium.java_client.remote.options;

import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsAcceptInsecureCertsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String ACCEPT_INSECURE_CERTS_OPTION = "acceptInsecureCerts";

    /**
     * Enforces untrusted and self-signed TLS certificates are
     * implicitly trusted on navigation for the duration of the session.
     *
     * @return self instance for chaining.
     */
    default T acceptInsecureCerts() {
        return setAcceptInsecureCerts(true);
    }

    /**
     * Set whether untrusted and self-signed TLS certificates are
     * implicitly trusted on navigation for the duration of the session.
     *
     * @param bool True or false.
     * @return self instance for chaining.
     */
    default T setAcceptInsecureCerts(boolean bool) {
        return amend(ACCEPT_INSECURE_CERTS_OPTION, bool);
    }

    /**
     * Get whether untrusted and self-signed TLS certificates are
     * implicitly trusted on navigation for the duration of the session.
     *
     * @return true or false.
     */
    default Optional<Boolean> doesAcceptInsecureCerts() {
        return Optional.ofNullable(toSafeBoolean(getCapability(ACCEPT_INSECURE_CERTS_OPTION)));
    }
}
