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

import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogLevelMapping;
import org.openqa.selenium.logging.Logs;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Reads the logs of the server. Adapted from Selenium's {@code RemoteLogs} (Apache License 2.0).
 */
class RemoteLogs implements Logs {
    private static final String TYPE_KEY = "type";

    private final ExecuteMethod executeMethod;

    RemoteLogs(ExecuteMethod executeMethod) {
        this.executeMethod = executeMethod;
    }

    @Override
    @SuppressWarnings("unchecked")
    public LogEntries get(String logType) {
        Object raw = executeMethod.execute(DriverCommand.GET_LOG, Map.of(TYPE_KEY, logType));
        if (!(raw instanceof List)) {
            throw new UnsupportedCommandException("malformed response to remote logs command");
        }
        List<LogEntry> entries = ((List<Map<String, Object>>) raw).stream()
                .map(RemoteLogs::createLogEntry)
                .collect(Collectors.toList());
        return new LogEntries(entries);
    }

    private static LogEntry createLogEntry(Map<String, Object> obj) {
        return new LogEntry(LogLevelMapping.toLevel((String) obj.get("level")), (Long) obj.get("timestamp"),
                (String) obj.get("message"));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<String> getAvailableLogTypes() {
        List<String> rawList = (List<String>) executeMethod.execute(DriverCommand.GET_AVAILABLE_LOG_TYPES, null);
        return Set.copyOf(new LinkedHashSet<>(rawList));
    }
}
