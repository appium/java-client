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

import java.util.Objects;

/**
 * The result of a command execution.
 */
public class Response {
    private volatile @Nullable Object value;
    private volatile @Nullable String sessionId;
    private volatile @Nullable Integer status;
    private volatile @Nullable String state;

    /** Creates an empty response. */
    public Response() {
    }

    /**
     * Creates a response bound to a session.
     *
     * @param sessionId the session the response belongs to
     */
    public Response(SessionId sessionId) {
        this.sessionId = String.valueOf(sessionId);
    }

    /**
     * The legacy numeric status. Prefer {@link #getState()}.
     *
     * @return the status or null if it is not set
     */
    @Nullable
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets the legacy numeric status.
     *
     * @param status the status
     */
    public void setStatus(@Nullable Integer status) {
        this.status = status;
    }

    /**
     * The W3C error code or "success".
     *
     * @return the state or null if it is not set
     */
    @Nullable
    public String getState() {
        return state;
    }

    /**
     * Sets the W3C error code or "success".
     *
     * @param state the state
     */
    public void setState(@Nullable String state) {
        this.state = state;
    }

    /**
     * Returns the payload of the response.
     *
     * @return the value or null if it is not set
     */
    @Nullable
    public Object getValue() {
        return value;
    }

    /**
     * Sets the payload of the response.
     *
     * @param value the value
     */
    public void setValue(@Nullable Object value) {
        this.value = value;
    }

    /**
     * Returns the identifier of the session the response belongs to.
     *
     * @return the session id or null if it is not set
     */
    @Nullable
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets the identifier of the session the response belongs to.
     *
     * @param sessionId the session id
     */
    public void setSessionId(@Nullable String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return String.format("(Response: SessionID: %s, Status: %s, State: %s, Value: %s)",
                getSessionId(), getStatus(), getState(), getValue());
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (!(o instanceof Response)) {
            return false;
        }
        Response that = (Response) o;
        return Objects.equals(value, that.value)
                && Objects.equals(sessionId, that.sessionId)
                && Objects.equals(status, that.status)
                && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, sessionId, status, state);
    }
}
