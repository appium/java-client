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

import java.util.stream.Stream;

public enum ASCIICharacter {
    /**
     * Null.
     */
    NUL,
    /**
     * Start of Heading (Communication Control).
     */
    SOH,
    /**
     * Start of Text (Communication Control).
     */
    STX,
    /**
     * End of Text (Communication Control).
     */
    ETX,
    /**
     * End of Transmission (Communication Control).
     */
    EOT,
    /**
     * Enquiry (Communication Control).
     */
    ENW,
    /**
     * Acknowledge (Communication Control).
     */
    ACK,
    /**
     * Bell (audible or attention signal).
     */
    BEL,
    /**
     * Backspace (Format Effector).
     */
    BS,
    /**
     * Horizontal Tabulation (punched card skip) (Format Effector).
     */
    HT,
    /**
     * Line Feed (Format Effector).
     */
    LF,
    /**
     * Vertical Tabulation (Format Effector).
     */
    VT,
    /**
     * Form Feed (Format Effector).
     */
    FF,
    /**
     * Carriage Return (Format Effector).
     */
    CR,
    /**
     * Shift Out.
     */
    SO,
    /**
     * Shift In.
     */
    SI,
    /**
     * Data Link Escape (Communication Control).
     */
    DLE,
    /**
     * Device Control 1.
     */
    DC1,
    /**
     * Device Control 2.
     */
    DC2,
    /**
     * Device Control 3.
     */
    DC3,
    /**
     * Device Control 4 (Stop).
     */
    DC4,
    /**
     * Negative Acknowledge (Communication Control).
     */
    NAK,
    /**
     * Synchronous Idle (Communication Control).
     */
    SYN,
    /**
     * End of Transmission Block (Communication Control).
     */
    ETB,
    /**
     * Cancel.
     */
    CAN,
    /**
     * End of Medium.
     */
    EM,
    /**
     * Substitute.
     */
    SUB,
    /**
     * Escape.
     */
    ESC,
    /**
     * File Separator (Information Separator).
     */
    FS,
    /**
     * Group Separator (Information Separator).
     */
    GS,
    /**
     * Record Separator (Information Separator).
     */
    RS,
    /**
     * Unit Separator (Information Separator).
     */
    US,
    /**
     * Space (Normally Non-Printing).
     */
    SPACE,
    /**
     * Exclamation Point.
     */
    EXCLAMATION_POINT,
    /**
     * Quotation Marks.
     */
    QUOTATION_MARKS,
    /**
     * Number Sign.
     */
    NUMBER_SIGN,
    /**
     * Dollar Sign.
     */
    DOLLAR_SIGN,
    /**
     * Percent.
     */
    PERCENT,
    /**
     * Ampersand.
     */
    AMPERSAND,
    /**
     * Apostrophe.
     */
    APOSTROPHE,
    /**
     * Opening Parenthesis.
     */
    OPENING_PARENTHESIS,
    /**
     * Closing Parenthesis.
     */
    CLOSING_PARENTHESIS,
    /**
     * Asterisk.
     */
    ASTERISK,
    /**
     * Plus.
     */
    PLUS,
    /**
     * Comma.
     */
    COMMA,
    /**
     * Hyphen (Minus).
     */
    HYPHEN,
    /**
     * Period (Decimal Point).
     */
    PERIOD,
    /**
     * Slant.
     */
    SLANT,
    /**
     * '0'.
     */
    DIGIT_0,
    /**
     * '1'.
     */
    DIGIT_1,
    /**
     * '2'.
     */
    DIGIT_2,
    /**
     * '3'.
     */
    DIGIT_3,
    /**
     * '4'.
     */
    DIGIT_4,
    /**
     * '5'.
     */
    DIGIT_5,
    /**
     * '6'.
     */
    DIGIT_6,
    /**
     * '7'.
     */
    DIGIT_7,
    /**
     * '8'.
     */
    DIGIT_8,
    /**
     * '9'.
     */
    DIGIT_9,
    /**
     * Colon.
     */
    COLON,
    /**
     * Semicolon.
     */
    SEMICOLON,
    /**
     * Less Than.
     */
    LESS_THAN,
    /**
     * Equals.
     */
    EQUALS,
    /**
     * Greater Than.
     */
    GREATER_THAN,
    /**
     * Question Mark.
     */
    QUESTION_MARK,
    /**
     * Commercial At.
     */
    COMMERCIAL_AT,
    /**
     * 'A'.
     */
    UPPERCASE_A,
    /**
     * 'B'.
     */
    UPPERCASE_B,
    /**
     * 'C'.
     */
    UPPERCASE_C,
    /**
     * 'D'.
     */
    UPPERCASE_D,
    /**
     * 'E'.
     */
    UPPERCASE_E,
    /**
     * 'F'.
     */
    UPPERCASE_F,
    /**
     * 'G'.
     */
    UPPERCASE_G,
    /**
     * 'H'.
     */
    UPPERCASE_H,
    /**
     * 'I'.
     */
    UPPERCASE_I,
    /**
     * 'J'.
     */
    UPPERCASE_J,
    /**
     * 'K'.
     */
    UPPERCASE_K,
    /**
     * 'L'.
     */
    UPPERCASE_L,
    /**
     * 'M'.
     */
    UPPERCASE_M,
    /**
     * 'N'.
     */
    UPPERCASE_N,
    /**
     * 'O'.
     */
    UPPERCASE_O,
    /**
     * 'P'.
     */
    UPPERCASE_P,
    /**
     * 'Q'.
     */
    UPPERCASE_Q,
    /**
     * 'R'.
     */
    UPPERCASE_R,
    /**
     * 'S'.
     */
    UPPERCASE_S,
    /**
     * 'T'.
     */
    UPPERCASE_T,
    /**
     * 'U'.
     */
    UPPERCASE_U,
    /**
     * 'V'.
     */
    UPPERCASE_V,
    /**
     * 'W'.
     */
    UPPERCASE_W,
    /**
     * 'X'.
     */
    UPPERCASE_X,
    /**
     * 'Y'.
     */
    UPPERCASE_Y,
    /**
     * 'Z'.
     */
    UPPERCASE_Z,
    /**
     * Opening Bracket.
     */
    OPENING_BRACKET,
    /**
     * Reverse Slant.
     */
    REVERSE_SLANT,
    /**
     * Closing Bracket.
     */
    CLOSING_BRACKET,
    /**
     * Circumflex.
     */
    CIRCUMFLEX,
    /**
     * Underline.
     */
    UNDERLINE,
    /**
     * Grave Accent (Opening Single Quotation Mark).
     */
    GRAVE_ACCENT,
    /**
     * 'a'.
     */
    LOWERCASE_A,
    /**
     * 'b'.
     */
    LOWERCASE_B,
    /**
     * 'c'.
     */
    LOWERCASE_C,
    /**
     * 'd'.
     */
    LOWERCASE_D,
    /**
     * 'e'.
     */
    LOWERCASE_E,
    /**
     * 'f'.
     */
    LOWERCASE_F,
    /**
     * 'g'.
     */
    LOWERCASE_G,
    /**
     * 'h'.
     */
    LOWERCASE_H,
    /**
     * 'i'.
     */
    LOWERCASE_I,
    /**
     * 'j'.
     */
    LOWERCASE_J,
    /**
     * 'k'.
     */
    LOWERCASE_K,
    /**
     * 'l'.
     */
    LOWERCASE_L,
    /**
     * 'm'.
     */
    LOWERCASE_M,
    /**
     * 'n'.
     */
    LOWERCASE_N,
    /**
     * 'o'.
     */
    LOWERCASE_O,
    /**
     * 'p'.
     */
    LOWERCASE_P,
    /**
     * 'q'.
     */
    LOWERCASE_Q,
    /**
     * 'r'.
     */
    LOWERCASE_R,
    /**
     * 's'.
     */
    LOWERCASE_S,
    /**
     * 't'.
     */
    LOWERCASE_T,
    /**
     * 'u'.
     */
    LOWERCASE_U,
    /**
     * 'v'.
     */
    LOWERCASE_V,
    /**
     * 'w'.
     */
    LOWERCASE_W,
    /**
     * 'x'.
     */
    LOWERCASE_X,
    /**
     * 'y'.
     */
    LOWERCASE_Y,
    /**
     * 'z'.
     */
    LOWERCASE_Z,
    /**
     * Opening Brace.
     */
    OPENING_BRACE,
    /**
     * Vertical Line.
     */
    VERTICAL_LINE,
    /**
     * Closing Brace.
     */
    CLOSING_BRACE,
    /**
     * Tilde.
     */
    TILDE,
    /**
     * Delete.
     */
    DEL;

    /**
     * Creates ASCII character instance based on its code.
     *
     * @param codePoint The code of the character.
     * @return Character instance.
     * @throws IllegalArgumentException if the ASCII char with the given code point is unknown.
     */
    public static ASCIICharacter fromCode(int codePoint) {
        return Stream.of(ASCIICharacter.values())
                .filter(x -> x.ordinal() == codePoint)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("ASCII character with code %d does not exist", codePoint)));
    }
}
