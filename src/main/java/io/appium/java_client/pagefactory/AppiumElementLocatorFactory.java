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

import java.lang.reflect.Field;

import io.appium.java_client.pagefactory.bys.builder.AppiumByBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

class AppiumElementLocatorFactory implements ElementLocatorFactory {
	protected final SearchContext searchContext;
	protected final TimeOutDuration timeOutDuration;
	protected final String platform;
	protected final String automation;
    protected final WebDriver originalWebDriver;
    protected final AppiumByBuilder byBuilder;

	public AppiumElementLocatorFactory(SearchContext searchContext,
									   String platform, String automation,
									   TimeOutDuration timeOutDuration,
                                       WebDriver originalWebDriver) {
		this.searchContext = searchContext;
        this.originalWebDriver = originalWebDriver;
		this.timeOutDuration = timeOutDuration;
		this.platform = platform;
		this.automation = automation;
        byBuilder = new DefaultElementByBuilder(platform, automation);
	}

	public ElementLocator createLocator(Field field) {
        TimeOutDuration customDuration;
        if (field.isAnnotationPresent(WithTimeout.class)){
            WithTimeout withTimeout = field.getAnnotation(WithTimeout.class);
            customDuration = new TimeOutDuration(withTimeout.time(), withTimeout.unit());
        }
        else
            customDuration = timeOutDuration;
        byBuilder.setAnnotated(field);
        By by = byBuilder.buildBy();
        if (by != null)
            return new AppiumElementLocator(searchContext, by, byBuilder.isLookupCached(), customDuration, originalWebDriver);
        return null;
	}
}
