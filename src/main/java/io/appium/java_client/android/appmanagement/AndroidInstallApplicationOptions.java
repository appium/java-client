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

package io.appium.java_client.android.appmanagement;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.appmanagement.BaseInstallApplicationOptions;

import java.time.Duration;
import java.util.Map;

public class AndroidInstallApplicationOptions extends
        BaseInstallApplicationOptions<AndroidInstallApplicationOptions> {
    private Boolean replace;
    private Duration timeout;
    private Boolean allowTestPackages;
    private Boolean useSdcard;
    private Boolean grantPermissions;

    /**
     * Enables the possibility to upgrade/reinstall the application
     * if it is already present on the device (the default behavior).
     *
     * @return self instance for chaining.
     */
    public AndroidInstallApplicationOptions withReplaceEnabled() {
        this.replace = true;
        return this;
    }

    /**
     * Disables the possibility to upgrade/reinstall the application
     * if it is already present on the device.
     *
     * @return self instance for chaining.
     */
    public AndroidInstallApplicationOptions withReplaceDisabled() {
        this.replace = false;
        return this;
    }

    /**
     * The time to wait until the app is installed (60000ms by default).
     *
     * @param timeout the actual timeout value. The minimum time resolution
     *                unit is one millisecond.
     * @return self instance for chaining.
     */
    public AndroidInstallApplicationOptions withTimeout(Duration timeout) {
        checkArgument(!checkNotNull(timeout).isNegative(), "The timeout value cannot be negative");
        this.timeout = timeout;
        return this;
    }

    /**
     * Allows to install packages marked as test in the manifest.
     *
     * @return self instance for chaining.
     */
    public AndroidInstallApplicationOptions withAllowTestPackagesEnabled() {
        this.allowTestPackages = true;
        return this;
    }

    /**
     * Disables a possibility to install packages marked as test in
     * the manifest (the default setting).
     *
     * @return self instance for chaining.
     */
    public AndroidInstallApplicationOptions withAllowTestPackagesDisabled() {
        this.allowTestPackages = false;
        return this;
    }

    /**
     * Forces the application to be installed of SD card
     * instead of the internal memory.
     *
     * @return self instance for chaining.
     */
    public AndroidInstallApplicationOptions withUseSdcardEnabled() {
        this.useSdcard = true;
        return this;
    }

    /**
     * Forces the application to be installed to the internal memory
     * (the default behavior).
     *
     * @return self instance for chaining.
     */
    public AndroidInstallApplicationOptions withUseSdcardDisabled() {
        this.useSdcard = false;
        return this;
    }

    /**
     * Grants all the permissions requested in the
     * application's manifest automatically after the installation
     * is completed under Android 6+.
     *
     * @return self instance for chaining.
     */
    public AndroidInstallApplicationOptions withGrantPermissionsEnabled() {
        this.grantPermissions = true;
        return this;
    }

    /**
     * Does not grant all the permissions requested in the
     * application's manifest automatically after the installation
     * is completed (the default behavior).
     *
     * @return self instance for chaining.
     */
    public AndroidInstallApplicationOptions withGrantPermissionsDisabled() {
        this.grantPermissions = false;
        return this;
    }

    @Override
    public Map<String, Object> build() {
        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        ofNullable(replace).map(x -> builder.put("replace", x));
        ofNullable(timeout).map(x -> builder.put("timeout", x.toMillis()));
        ofNullable(allowTestPackages).map(x -> builder.put("allowTestPackages", x));
        ofNullable(useSdcard).map(x -> builder.put("useSdcard", x));
        ofNullable(grantPermissions).map(x -> builder.put("grantPermissions", x));
        return builder.build();
    }
}
