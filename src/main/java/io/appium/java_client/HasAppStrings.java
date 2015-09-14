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

package io.appium.java_client;


public interface HasAppStrings {
	
	/**
	* Get all defined Strings from an app for the default language
	*
	* @return a string of all the localized strings defined in the app
	*/
	public String getAppStrings();

	/**
	* Get all defined Strings from an app for the specified language
	*
	* @param language strings language code
	* @return a string of all the localized strings defined in the app
	*/
	public String getAppStrings(String language);

}
