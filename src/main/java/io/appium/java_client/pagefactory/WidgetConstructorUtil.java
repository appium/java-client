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

package io.appium.java_client.pagefactory;

import org.openqa.selenium.WebElement;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

class WidgetConstructorUtil {
    private WidgetConstructorUtil() {
        super();
    }

    @SuppressWarnings("unchecked")
    static Constructor<? extends Widget> findConvenientConstructor(Class<? extends Widget> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            Class<?>[] params = constructor.getParameterTypes();
            if (constructor.getParameterTypes().length != 1) {
                continue;
            }

            Class<?> param = params[0];
            if (WebElement.class.isAssignableFrom(param)) {
                constructor.setAccessible(true);
                return (Constructor<? extends Widget>) constructor;
            }
        }
        List<Constructor<?>> declared = Arrays.asList(clazz.getDeclaredConstructors());
        throw new NoSuchMethodError(
            clazz.getName() + " has no convenient constructor which could pass a "
                    + WebElement.class.getName()
                    + " instance as a parameter. The actual list of constructors: "
                    + declared.toString());
    }
}
