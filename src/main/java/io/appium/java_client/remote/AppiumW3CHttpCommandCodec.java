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

import static org.openqa.seleniumone.remote.DriverCommand.GET_ELEMENT_ATTRIBUTE;
import static org.openqa.seleniumone.remote.DriverCommand.GET_ELEMENT_LOCATION;
import static org.openqa.seleniumone.remote.DriverCommand.GET_ELEMENT_LOCATION_ONCE_SCROLLED_INTO_VIEW;
import static org.openqa.seleniumone.remote.DriverCommand.GET_ELEMENT_SIZE;
import static org.openqa.seleniumone.remote.DriverCommand.GET_PAGE_SOURCE;
import static org.openqa.seleniumone.remote.DriverCommand.IS_ELEMENT_DISPLAYED;
import static org.openqa.seleniumone.remote.DriverCommand.SEND_KEYS_TO_ACTIVE_ELEMENT;
import static org.openqa.seleniumone.remote.DriverCommand.SEND_KEYS_TO_ELEMENT;
import static org.openqa.seleniumone.remote.DriverCommand.SET_TIMEOUT;
import static org.openqa.seleniumone.remote.DriverCommand.SUBMIT_ELEMENT;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import org.openqa.seleniumone.interactions.KeyInput;
import org.openqa.seleniumone.interactions.Sequence;
import org.openqa.seleniumone.remote.codec.w3c.W3CHttpCommandCodec;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppiumW3CHttpCommandCodec extends W3CHttpCommandCodec {
    /**
     * This class overrides the built-in seleniumone W3C commands codec,
     * since the latter hardcodes many commands in Javascript,
     * which does not work with Appium.
     * Check https://www.w3.org/TR/webdriver/ to see all available W3C
     * endpoints.
     */
    public AppiumW3CHttpCommandCodec() {
        defineCommand(GET_ELEMENT_ATTRIBUTE, get("/session/:sessionId/element/:id/attribute/:name"));
        defineCommand(IS_ELEMENT_DISPLAYED, get("/session/:sessionId/element/:id/displayed"));
        defineCommand(GET_PAGE_SOURCE, get("/session/:sessionId/source"));
        defineCommand(SEND_KEYS_TO_ACTIVE_ELEMENT, post("/session/:sessionId/actions"));
    }

    @Override
    public void alias(String commandName, String isAnAliasFor) {
        // This blocks parent constructor from undesirable aliases assigning
        switch (commandName) {
            case GET_ELEMENT_ATTRIBUTE:
            case GET_ELEMENT_LOCATION:
            case GET_ELEMENT_LOCATION_ONCE_SCROLLED_INTO_VIEW:
            case GET_ELEMENT_SIZE:
            case IS_ELEMENT_DISPLAYED:
            case SUBMIT_ELEMENT:
            case GET_PAGE_SOURCE:
                return;
            default:
                super.alias(commandName, isAnAliasFor);
                break;
        }
    }

    @Override
    protected Map<String, ?> amendParameters(String name, Map<String, ?> parameters) {
        // This blocks parent constructor from undesirable parameters amending
        switch (name) {
            case SEND_KEYS_TO_ACTIVE_ELEMENT:
                Object rawValue = parameters.get("value");
                //noinspection unchecked
                Stream<CharSequence> source = (rawValue instanceof Collection)
                        ? ((Collection<CharSequence>) rawValue).stream()
                        : Stream.of((CharSequence[]) rawValue);
                String text = source
                        .flatMap(Stream::of)
                        .collect(Collectors.joining());

                final KeyInput keyboard = new KeyInput("keyboard");
                Sequence sequence = new Sequence(keyboard, 0);
                for (int i = 0; i < text.length(); ++i) {
                    sequence.addAction(keyboard.createKeyDown(text.charAt(i)))
                            .addAction(keyboard.createKeyUp(text.charAt(i)));
                }
                return ImmutableMap.<String, Object>builder()
                        .put("actions", ImmutableList.of(sequence.toJson()))
                        .build();
            case SEND_KEYS_TO_ELEMENT:
            case SET_TIMEOUT:
                return super.amendParameters(name, parameters);
            default:
                return parameters;
        }
    }
}
