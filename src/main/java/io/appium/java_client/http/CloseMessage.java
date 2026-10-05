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

package io.appium.java_client.http;

public class CloseMessage implements Message {
    private final int code;
    private final String reason;

    public CloseMessage(int code) {
        this(code, "");
    }

    public CloseMessage(int code, String reason) {
        this.code = code;
        this.reason = reason == null ? "" : reason;
    }

    public int code() {
        return code;
    }

    public String reason() {
        return reason;
    }

    @Override
    public String toString() {
        return String.format("%s{code=%d, reason=%s}", getClass().getSimpleName(), code, reason);
    }
}
