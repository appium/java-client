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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A command to execute in a session.
 */
public class Command {
    private final @Nullable SessionId sessionId;
    private final CommandPayload payload;

    /**
     * Creates a command without parameters.
     *
     * @param sessionId the session the command is executed in
     * @param name the name of the command
     */
    public Command(SessionId sessionId, String name) {
        this(sessionId, name, new HashMap<>());
    }

    /**
     * Creates a command with parameters.
     *
     * @param sessionId the session the command is executed in, or {@code null} for session-less commands
     * @param name the name of the command
     * @param parameters the parameters of the command
     */
    public Command(@Nullable SessionId sessionId, String name, Map<String, ?> parameters) {
        this(sessionId, new CommandPayload(name, parameters));
    }

    /**
     * Creates a command from a payload.
     *
     * @param sessionId the session the command is executed in, or {@code null} for session-less commands
     * @param payload the name and the parameters of the command
     */
    public Command(@Nullable SessionId sessionId, CommandPayload payload) {
        this.sessionId = sessionId;
        this.payload = payload;
    }

    /**
     * Returns the session the command is executed in.
     *
     * @return the session id, or {@code null} for session-less commands
     */
    @Nullable
    public SessionId getSessionId() {
        return sessionId;
    }

    /**
     * Returns the name of the command.
     *
     * @return the command name
     */
    public String getName() {
        return payload.getName();
    }

    /**
     * Returns the parameters of the command.
     *
     * @return the command parameters
     */
    public Map<String, ?> getParameters() {
        return payload.getParameters();
    }

    @Override
    public String toString() {
        return "[" + sessionId + ", " + getName() + " " + getParameters() + "]";
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (!(o instanceof Command)) {
            return false;
        }
        Command that = (Command) o;
        return Objects.equals(sessionId, that.sessionId)
                && Objects.equals(getName(), that.getName())
                && Objects.equals(getParameters(), that.getParameters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, getName(), getParameters());
    }
}
