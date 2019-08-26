// Licensed to the Software Freedom Conservancy (SFC) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The SFC licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package io.appium.java_client.selenium.support.pagefactory;


import io.appium.java_client.selenium.By;

/**
 * Abstract class to work with fields in Page Objects.
 * Provides methods to process {@link org.openqa.selenium.support.FindBy},
 * {@link org.openqa.selenium.support.FindBys} and
 * {@link org.openqa.selenium.support.FindAll} annotations.
 */
public abstract class AbstractAnnotations {

    /**
     * Defines how to transform given object (field, class, etc)
     * into {@link org.openqa.selenium.By} class used by webdriver to locate elements.
     *
     * @return By object
     */
    public abstract By buildBy();

    /**
     * Defines whether or not given element
     * should be returned from cache on further calls.
     *
     * @return boolean if lookup cached
     */
    public abstract boolean isLookupCached();

}

