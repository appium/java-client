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

package io.appium.java_client.ios.options.wda;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsXcodeCertificateOptions<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String XCODE_ORG_ID_OPTION = "xcodeOrgId";
    String XCODE_SIGNING_ID_OPTION = "xcodeSigningId";
    String DEFAULT_XCODE_SIGNING_ID = "iPhone Developer";

    /**
     * Provides a signing certificate for WebDriverAgent compilation.
     * If signing id is not provided/null then it defaults to "iPhone Developer"
     *
     * @param cert Certificate credentials.
     * @return self instance for chaining.
     */
    default T setXcodeCertificate(XcodeCertificate cert) {
        String signingId = cert.getXcodeSigningId() == null
                ? DEFAULT_XCODE_SIGNING_ID
                : cert.getXcodeSigningId();
        return amend(XCODE_ORG_ID_OPTION, cert.getXcodeOrgId())
                .amend(XCODE_SIGNING_ID_OPTION, signingId);
    }

    /**
     * Get a signing certificate for WebDriverAgent compilation.
     *
     * @return Certificate value.
     */
    default Optional<XcodeCertificate> getXcodeCertificate() {
        String orgId = (String) getCapability(XCODE_ORG_ID_OPTION);
        String signingId = (String) getCapability(XCODE_SIGNING_ID_OPTION);
        return orgId == null
            ? Optional.empty()
            : Optional.of(new XcodeCertificate(orgId, signingId));
    }
}
