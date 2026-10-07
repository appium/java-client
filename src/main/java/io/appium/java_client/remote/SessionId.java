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

import java.io.Serializable;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

/**
 * An opaque session identifier.
 */
public class SessionId implements Serializable {
    private static final long serialVersionUID = 1L;

    /** The raw session identifier. */
    private final String opaqueKey;

    /**
     * Creates a session id from a UUID.
     *
     * @param uuid the UUID of the session
     */
    public SessionId(UUID uuid) {
        this(requireNonNull(uuid, "Session ID key").toString());
    }

    /**
     * Creates a session id from a raw value.
     *
     * @param opaqueKey the raw session identifier
     */
    public SessionId(String opaqueKey) {
        this.opaqueKey = requireNonNull(opaqueKey, "Session ID key");
    }

    @Override
    public String toString() {
        return opaqueKey;
    }

    @Override
    public int hashCode() {
        return opaqueKey.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof SessionId && opaqueKey.equals(((SessionId) obj).opaqueKey);
    }
}
