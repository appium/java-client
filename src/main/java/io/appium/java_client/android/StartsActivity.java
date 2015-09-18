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

package io.appium.java_client.android;

public interface StartsActivity {
	/**
	   * This method should start arbitrary activity during a test. If the activity belongs to
	   * another application, that application is started and the activity is opened.
	   *
	   * @param appPackage The package containing the activity. [Required]
	   * @param appActivity The activity to start. [Required]
	   * @param appWaitPackage Automation will begin after this package starts. [Optional]
	   * @param appWaitActivity Automation will begin after this activity starts. [Optional]
	   * @example
	   * *.startActivity("com.foo.bar", ".MyActivity", null, null);
	   */
	  public void startActivity(String appPackage, String appActivity, String appWaitPackage, String appWaitActivity)
	                                                                                  throws IllegalArgumentException;
	  
	   /**
	   * This method should start arbitrary activity during a test. If the activity belongs to
	   * another application, that application is started and the activity is opened.
	   *
	   * @param appPackage The package containing the activity. [Required]
	   * @param appActivity The activity to start. [Required]
	   * @example
	   * *.startActivity("com.foo.bar", ".MyActivity");
	   */	  
	  public void startActivity(String appPackage, String appActivity)
              throws IllegalArgumentException;	  

}
