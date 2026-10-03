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

package io.appium.java_client.internal;

import org.jspecify.annotations.Nullable;

/**
 * Argument checks. The messages use the {@code %s} placeholder.
 */
public final class Preconditions {
    private Preconditions() {
    }

    /**
     * Ensures the truth of an argument expression.
     *
     * @param expression the expression that must be true
     * @throws IllegalArgumentException if the expression is false
     */
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Ensures the truth of an argument expression.
     *
     * @param expression the expression that must be true
     * @param errorMessage the message of the exception
     * @throws IllegalArgumentException if the expression is false
     */
    public static void checkArgument(boolean expression, @Nullable Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    /**
     * Ensures the truth of an argument expression.
     *
     * @param expression the expression that must be true
     * @param template the message template, in which each {@code %s} is replaced by an argument
     * @param args the template arguments
     * @throws IllegalArgumentException if the expression is false
     */
    public static void checkArgument(boolean expression, String template, @Nullable Object... args) {
        if (!expression) {
            throw new IllegalArgumentException(format(template, args));
        }
    }

    /**
     * Ensures that a reference is not null.
     *
     * @param reference the reference to check
     * @param <T> the type of the reference
     * @return the reference
     * @throws NullPointerException if the reference is null
     */
    public static <T> T checkNotNull(@Nullable T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * Ensures that a reference is not null.
     *
     * @param reference the reference to check
     * @param errorMessage the message of the exception
     * @param <T> the type of the reference
     * @return the reference
     * @throws NullPointerException if the reference is null
     */
    public static <T> T checkNotNull(@Nullable T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    private static String format(String template, @Nullable Object... args) {
        StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template, templateStart, placeholderStart).append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template, templateStart, template.length());
        if (i < args.length) {
            builder.append(" [").append(args[i++]);
            while (i < args.length) {
                builder.append(", ").append(args[i++]);
            }
            builder.append(']');
        }
        return builder.toString();
    }
}
