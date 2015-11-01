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

import org.openqa.selenium.By;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

class WidgetByBuilder extends DefaultElementByBuilder {

    private enum WhatIsNeeded {
        DEFAULT_OR_HTML,
        MOBILE_NATIVE
    }

    protected WidgetByBuilder(String platform, String automation) {
        super(platform, automation);
    }

    private static Class<?> getClassFromAListField(Field field){
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return null;
        }

        Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
        if (ParameterizedType.class.isAssignableFrom(listType.getClass()))
            listType = ((ParameterizedType) listType).getRawType();

        return (Class<?>) listType;
    }

    private By getByFromDeclaredClass(WhatIsNeeded whatIsNeeded){
        AnnotatedElement annotatedElement = annotatedElementContainer.getAnnotated();
        By result = null;
        Field field = null;
        try {
            Class<?> declaredClass;
            if (Field.class.isAssignableFrom(annotatedElement.getClass())) {
                field = Field.class.cast(annotatedElement);
                if (List.class.isAssignableFrom(field.getType()))
                    declaredClass = getClassFromAListField(field);
                else
                    declaredClass = field.getType();
            } else {
                declaredClass = Class.class.cast(annotatedElement);
            }

            while (result == null && !declaredClass.equals(Object.class)) {
                setAnnotated(declaredClass);
                if (whatIsNeeded.equals(WhatIsNeeded.DEFAULT_OR_HTML))
                    result = super.buildDefaultBy();
                else
                    result = super.buildMobileNativeBy();
                declaredClass = declaredClass.getSuperclass();
            }
            return result;
        }
        finally {
            if (field != null)
                setAnnotated(field);
        }
    }

    @Override
    protected By buildDefaultBy() {
        By defaultBy = super.buildDefaultBy();

        if (defaultBy != null)
            return defaultBy;
        else
            return getByFromDeclaredClass(WhatIsNeeded.DEFAULT_OR_HTML);
    }

    @Override
    protected By buildMobileNativeBy() {
        By mobileBy = super.buildMobileNativeBy();

        if (mobileBy != null)
            return mobileBy;
        else
            return getByFromDeclaredClass(WhatIsNeeded.MOBILE_NATIVE);
    }
}
