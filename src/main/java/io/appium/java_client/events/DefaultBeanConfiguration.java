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

package io.appium.java_client.events;

import io.appium.java_client.events.api.Listener;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
class DefaultBeanConfiguration {
    public static final String LISTENABLE_OBJECT = "object";
    public static final String COMPONENT_BEAN = "component";

    protected final List<Listener> listeners = new ArrayList<>();
    protected WebDriver driver;
    protected AbstractApplicationContext context;

    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean(name = LISTENABLE_OBJECT)
    public <T> T init(T t, WebDriver driver, List<Listener> listeners,
        AbstractApplicationContext context) {
        this.driver = driver;
        this.listeners.addAll(listeners);
        this.context = context;
        return t;
    }

    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean(name = COMPONENT_BEAN)
    public <T> T  getComponent(T t) {
        return t;
    }

    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean(name = "defaultAspect")
    public DefaultAspect getAspect() {
        DefaultAspect aspect = new DefaultAspect(context, driver);
        aspect.add(listeners);
        return aspect;
    }
}
