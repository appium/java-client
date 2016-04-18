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

package io.appium.java_client.service.local;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


enum Scripts {
    GET_PATH_TO_DEFAULT_NODE_UNIX("get_path_to_default_node.sh"),
    GET_NODE_JS_EXECUTABLE("getExe.js");
    private static final String RESOURCE_FOLDER = "/scripts/";
    private final String script;

    Scripts(String script) {
        this.script = script;
    }

    public File getScriptFile() {
        InputStream inputStream = getClass().getResourceAsStream(RESOURCE_FOLDER + this.script);
        byte[] bytes;
        try {
            bytes = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] splittedName = this.script.split("\\.");
        File scriptFile;
        try {
            scriptFile = File.createTempFile(splittedName[0], "." + splittedName[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!scriptFile.exists()) {
            try {
                scriptFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileOutputStream output;
        try {
            output = new FileOutputStream(scriptFile, true);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            output.write(bytes);
            output.flush();
            output.close();
            return scriptFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
