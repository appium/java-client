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

import io.appium.java_client.pagefactory.bys.ContentType;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class WidgetInterceptor implements MethodInterceptor{

    private final WebElement element;
    private final Map<ContentType, Constructor<? extends Widget>> instantiationMap;
    private final Map<ContentType, Widget> cachedInstances = new HashMap<>();
    private final AppiumFieldDecorator decorator;

    WidgetInterceptor(WebElement element, Map<ContentType, Constructor<? extends Widget>> instantiationMap,
                      AppiumFieldDecorator decorator) {
        this.element = element;
        this.instantiationMap = instantiationMap;
        this.decorator = decorator;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        //TODO
        return null;
    }
}
