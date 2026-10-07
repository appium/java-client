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

import lombok.Data;
import lombok.ToString;

/**
 * Signing certificate details for the WebDriverAgent compilation.
 */
@ToString()
@Data()
public class XcodeCertificate {
    private final String xcodeOrgId;
    private final String xcodeSigningId;

    /**
     * Creates a certificate from the given team and signing identifiers.
     *
     * @param xcodeOrgId Apple developer team identifier.
     * @param xcodeSigningId Signing identity.
     */
    public XcodeCertificate(String xcodeOrgId, String xcodeSigningId) {
        this.xcodeOrgId = xcodeOrgId;
        this.xcodeSigningId = xcodeSigningId;
    }

    /**
     * Creates a certificate with the default signing identity.
     *
     * @param xcodeOrgId Apple developer team identifier.
     */
    public XcodeCertificate(String xcodeOrgId) {
        this(xcodeOrgId, null);
    }
}
