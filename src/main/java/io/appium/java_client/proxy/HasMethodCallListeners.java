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

public interface HasMethodCallListeners {
    /**
     * The setter is dynamically created by ByteBuddy to store
     * method call listeners on the instrumented proxy instance.
     *
     * @param methodCallListeners Array of method call listeners assigned to the proxy instance.
     */
    void setMethodCallListeners(MethodCallListener[] methodCallListeners);

    /**
     * The getter is dynamically created by ByteBuddy to access
     * method call listeners on the instrumented proxy instance.
     *
     * @return Array of method call listeners assigned the proxy instance.
     */
    MethodCallListener[] getMethodCallListeners();
}
