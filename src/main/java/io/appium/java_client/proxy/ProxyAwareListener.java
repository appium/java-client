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

package io.appium.java_client.proxy;

/**
 * Extension of {@link MethodCallListener} that allows access to the proxy instance it depends on.
 * <p>
 * This interface is intended for listeners that need a reference to the proxy object.
 * <p>
 * The {@link #attachProxyInstance(Object)} method will be invoked immediately after the proxy is created,
 * allowing the listener to bind to it before any method interception begins.
 * <p>
 * Example usage: Working with elements such as
 * {@code RemoteWebElement} that require runtime mutation (e.g. setting parent driver or element ID).
 */
public interface ProxyAwareListener extends MethodCallListener {

    /**
     * Binds the listener to the proxy instance passed.
     * <p>
     * This is called once, immediately after proxy creation and before the proxy is returned to the caller.
     *
     * @param proxy the proxy instance created via {@code createProxy} that this listener is attached to.
     */
    void attachProxyInstance(Object proxy);
}

