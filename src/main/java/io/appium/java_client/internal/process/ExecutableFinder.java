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

package io.appium.java_client.internal.process;

import org.jspecify.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Locates a named executable by scanning the PATH.
 * Adapted from Selenium's {@code org.openqa.selenium.os.ExecutableFinder}
 * (Apache License 2.0), vendored here to drop the {@code selenium-os} dependency and
 * trimmed to bare-name PATH lookup, the only way this class is actually called.
 */
public class ExecutableFinder {
    /**
     * Creates a new instance.
     */
    public ExecutableFinder() {
    }

    private static final boolean IS_WINDOWS = osNameContains("win");
    private static final boolean IS_MAC = osNameContains("mac");

    private static final List<String> ENDINGS = IS_WINDOWS
            ? List.of("", ".cmd", ".exe", ".com", ".bat")
            : singletonList("");

    private static boolean osNameContains(String needle) {
        return System.getProperty("os.name", "").toLowerCase(Locale.ROOT).contains(needle);
    }

    /**
     * Finds a named executable by scanning the PATH. On Windows, common executable
     * endings (".com", ".bat" and ".exe") are tried in addition to the bare name.
     *
     * @param named the name of the executable to find, e.g. "node"
     * @return the absolute path to the executable, or {@code null} if no match is found
     */
    @Nullable
    public String find(String named) {
        List<String> pathSegments = new ArrayList<>(fromEnvironment());
        if (IS_MAC) {
            pathSegments.addAll(macSpecificPathSegments());
        }

        for (String pathSegment : pathSegments) {
            for (String ending : ENDINGS) {
                File file = new File(pathSegment, named + ending);
                if (canExecute(file)) {
                    return file.getAbsolutePath();
                }
            }
        }
        return null;
    }

    private List<String> fromEnvironment() {
        String pathName = "PATH";
        Map<String, String> env = System.getenv();
        if (!env.containsKey(pathName)) {
            for (String key : env.keySet()) {
                if (pathName.equalsIgnoreCase(key)) {
                    pathName = key;
                    break;
                }
            }
        }
        String path = env.get(pathName);
        return path != null ? List.of(path.split(File.pathSeparator)) : emptyList();
    }

    private List<String> macSpecificPathSegments() {
        File pathFile = new File("/etc/paths");
        if (pathFile.exists()) {
            try {
                return Files.readAllLines(pathFile.toPath());
            } catch (IOException e) {
                // Ignore, PATH env var segments are still used
            }
        }
        return emptyList();
    }

    private static boolean canExecute(File file) {
        return file.exists() && !file.isDirectory() && file.canExecute();
    }
}
