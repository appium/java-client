/*
 * Copyright 2015 Appium Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
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

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

class AppiumElementLocatorFactory implements ElementLocatorFactory {
    private final SearchContext searchContext;
	private final TimeOutDuration timeOutDuration;

	public AppiumElementLocatorFactory(SearchContext searchContext,
			TimeOutDuration timeOutDuration) {
		this.searchContext = searchContext;
		this.timeOutDuration = timeOutDuration;
	}
	
	public AppiumElementLocatorFactory(SearchContext searchContext) {
		this(searchContext, new TimeOutDuration(AppiumFieldDecorator.DEFAULT_IMPLICITLY_WAIT_TIMEOUT,
				AppiumFieldDecorator.DEFAULT_TIMEUNIT));
	}	

	public ElementLocator createLocator(Field field) {
		return new AppiumElementLocator(searchContext, field, timeOutDuration);
	}
}
