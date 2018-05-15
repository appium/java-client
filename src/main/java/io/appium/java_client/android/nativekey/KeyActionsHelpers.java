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

package io.appium.java_client.android.nativekey;

import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

public class KeyActionsHelpers {
    private static final Map<ASCIICharacter, KeyEvent> ASCII_CHARS_MAPPING = new HashMap<>();
    /**
     * It is necessary to shift the meta codes to avoid
     * unexpected matches with key codes.
     * Unfortunately there is no other way to distinguish
     * key codes from meta codes, since W3C standard only provides a single field
     * to keep the code value.
     */
    public static final int META_CODES_SHIFT = 0x1000;

    static {
        ASCII_CHARS_MAPPING.put(ASCIICharacter.BS, new KeyEvent(AndroidKey.DEL));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.HT, new KeyEvent(AndroidKey.TAB));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.LF, new KeyEvent(AndroidKey.ENTER));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.ESC, new KeyEvent(AndroidKey.ESCAPE));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.DEL, new KeyEvent(AndroidKey.FORWARD_DEL));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.APOSTROPHE, new KeyEvent(AndroidKey.APOSTROPHE));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.ASTERISK, new KeyEvent(AndroidKey.STAR));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.CLOSING_BRACKET, new KeyEvent(AndroidKey.RIGHT_BRACKET));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.CLOSING_PARENTHESIS, new KeyEvent(AndroidKey.NUMPAD_RIGHT_PAREN));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.COMMA, new KeyEvent(AndroidKey.COMMA));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.COMMERCIAL_AT, new KeyEvent(AndroidKey.AT));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.EQUALS, new KeyEvent(AndroidKey.EQUALS));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.GRAVE_ACCENT, new KeyEvent(AndroidKey.GRAVE));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.HYPHEN, new KeyEvent(AndroidKey.MINUS));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.NUMBER_SIGN, new KeyEvent(AndroidKey.POUND));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.OPENING_BRACKET, new KeyEvent(AndroidKey.LEFT_BRACKET));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.OPENING_PARENTHESIS, new KeyEvent(AndroidKey.NUMPAD_LEFT_PAREN));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.PERIOD, new KeyEvent(AndroidKey.PERIOD));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.PLUS, new KeyEvent(AndroidKey.PLUS));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.REVERSE_SLANT, new KeyEvent(AndroidKey.BACKSLASH));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.SEMICOLON, new KeyEvent(AndroidKey.SEMICOLON));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.SLANT, new KeyEvent(AndroidKey.SLASH));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.SPACE, new KeyEvent(AndroidKey.SPACE));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.EXCLAMATION_POINT, new KeyEvent(AndroidKey.DIGIT_1)
                .withMetaModifier(KeyEventMetaModifier.SHIFT_ON));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.COLON, new KeyEvent(AndroidKey.SEMICOLON)
                .withMetaModifier(KeyEventMetaModifier.SHIFT_ON));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.LESS_THAN, new KeyEvent(AndroidKey.COMMA)
                .withMetaModifier(KeyEventMetaModifier.SHIFT_ON));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.GREATER_THAN, new KeyEvent(AndroidKey.NUMPAD_DOT)
                .withMetaModifier(KeyEventMetaModifier.SHIFT_ON));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.DOLLAR_SIGN, new KeyEvent(AndroidKey.DIGIT_4)
                .withMetaModifier(KeyEventMetaModifier.SHIFT_ON));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.PERCENT, new KeyEvent(AndroidKey.DIGIT_5)
                .withMetaModifier(KeyEventMetaModifier.SHIFT_ON));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.AMPERSAND, new KeyEvent(AndroidKey.DIGIT_7)
                .withMetaModifier(KeyEventMetaModifier.SHIFT_ON));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.QUESTION_MARK, new KeyEvent(AndroidKey.SLASH)
                .withMetaModifier(KeyEventMetaModifier.SHIFT_ON));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.OPENING_BRACE, new KeyEvent(AndroidKey.LEFT_BRACKET)
                .withMetaModifier(KeyEventMetaModifier.SHIFT_ON));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.CLOSING_BRACE, new KeyEvent(AndroidKey.RIGHT_BRACKET)
                .withMetaModifier(KeyEventMetaModifier.SHIFT_ON));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.UNDERLINE, new KeyEvent(AndroidKey.MINUS)
                .withMetaModifier(KeyEventMetaModifier.SHIFT_ON));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.CIRCUMFLEX, new KeyEvent(AndroidKey.DIGIT_6)
                .withMetaModifier(KeyEventMetaModifier.SHIFT_ON));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.VERTICAL_LINE, new KeyEvent(AndroidKey.BACKSLASH)
                .withMetaModifier(KeyEventMetaModifier.SHIFT_ON));
        ASCII_CHARS_MAPPING.put(ASCIICharacter.QUOTATION_MARKS, new KeyEvent(AndroidKey.APOSTROPHE)
                .withMetaModifier(KeyEventMetaModifier.SHIFT_ON));

        IntStream.rangeClosed(ASCIICharacter.DIGIT_0.ordinal(), ASCIICharacter.DIGIT_9.ordinal())
                .forEach(x -> ASCII_CHARS_MAPPING.put(ASCIICharacter.fromCode(x),
                        new KeyEvent(AndroidKey
                                .fromCode(AndroidKey.DIGIT_0.getCode() + x - ASCIICharacter.DIGIT_0.ordinal()))));
        IntStream.rangeClosed(ASCIICharacter.LOWERCASE_A.ordinal(), ASCIICharacter.LOWERCASE_Z.ordinal())
                .forEach(x -> ASCII_CHARS_MAPPING.put(ASCIICharacter.fromCode(x),
                        new KeyEvent(AndroidKey
                                .fromCode(AndroidKey.A.getCode() + x - ASCIICharacter.LOWERCASE_A.ordinal()))));
        //noinspection ConstantConditions
        IntStream.rangeClosed(ASCIICharacter.UPPERCASE_A.ordinal(), ASCIICharacter.UPPERCASE_Z.ordinal())
                .forEach(x -> ASCII_CHARS_MAPPING.put(ASCIICharacter.fromCode(x),
                        new KeyEvent(AndroidKey
                                .fromCode(AndroidKey.A.getCode() + x - ASCIICharacter.UPPERCASE_A.ordinal())))
                        .withMetaModifier(KeyEventMetaModifier.SHIFT_ON));
    }

    /**
     * Creates an action chain for sending key codes to Android
     * via W3C actions. The normal sendKeys routine won't work there,
     * since Android key codes are different from ASCII key codes used
     * by browsers.
     *
     * @param actions W3C actions instance
     * @param str     The actual string to type. Not all characters are
     *                going to be transformed, but only these, that are present
     *                in ASCII_CHARS_MAPPING.
     * @return The altered actions.
     */
    public static Actions sendNativeKeys(Actions actions, String str) {
        Actions result = actions;
        final int length = str.length();
        for (int offset = 0; offset < length; ) {
            final int codePoint = str.codePointAt(offset);
            offset += Character.charCount(codePoint);
            final KeyEvent keyEvent = toKeyEvent(codePoint);
            if (keyEvent == null) {
                continue;
            }

            //noinspection ConstantConditions
            final String keyCode = new String(Character.toChars(keyEvent.getKeyCode()));
            if (keyEvent.getMetaState() == null) {
                result = result
                        .keyDown(keyCode)
                        .keyUp(keyCode);
            } else {
                final String metaCode = new String(Character
                        .toChars(META_CODES_SHIFT + keyEvent.getMetaState()));
                result = result.keyDown(metaCode)
                        .keyDown(keyCode)
                        .keyUp(keyCode)
                        .keyUp(metaCode);
            }
        }
        return result;
    }

    /**
     * Transforms the given code point to the native key event.
     *
     * @param codePoint The actual code point.
     * @return KeyEvent instance or null if no match can be found for the given code point.
     */
    @Nullable
    public static KeyEvent toKeyEvent(int codePoint) {
        return ASCII_CHARS_MAPPING.keySet().stream()
                .filter(x -> x.ordinal() == codePoint)
                .findFirst()
                .map(ASCII_CHARS_MAPPING::get)
                .orElse(null);
    }
}
