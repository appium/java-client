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

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.WebDriverException;

/**
 * Thrown if a screenshot cannot be taken, or if the server attached one to an error.
 */
public class ScreenshotException extends WebDriverException {
    public ScreenshotException(String message) {
        super(message);
    }

    public ScreenshotException(Throwable cause) {
        super(cause);
    }

    public ScreenshotException(String message, @Nullable Throwable cause) {
        super(message, cause);
    }
}
