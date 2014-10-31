/*
 +Copyright 2014 Appium contributors
 +Copyright 2014 Software Freedom Conservancy
 +
 +Licensed under the Apache License, Version 2.0 (the "License");
 +you may not use this file except in compliance with the License.
 +You may obtain a copy of the License at
 +
 +     http://www.apache.org/licenses/LICENSE-2.0
 +
 +Unless required by applicable law or agreed to in writing, software
 +distributed under the License is distributed on an "AS IS" BASIS,
 +WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 +See the License for the specific language governing permissions and
 +limitations under the License.
 + */

package io.appium.java_client;

import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.List;

public abstract class MobileElement extends RemoteWebElement implements FindsByAccessibilityId, TouchableElement {

	protected FileDetector fileDetector;

	public List<WebElement> findElements(By by) {
		return by.findElements(this);
	}

	public WebElement findElement(By by) {
		return by.findElement(this);
	}

	public WebElement findElementByAccessibilityId(String using) {
		return findElement("accessibility id", using);
	}

	public List<WebElement> findElementsByAccessibilityId(String using) {
		return findElements("accessibility id", using);
	}

	public void setValue(String value) {
		ImmutableMap.Builder builder = ImmutableMap.builder();
		builder.put("id", id).put("value", value);
		execute(MobileCommand.SET_VALUE, builder.build());
	}

	public Point getCenter() {
		Point upperLeft = this.getLocation();
		Dimension dimensions = this.getSize();
		return new Point(upperLeft.getX() + dimensions.getWidth() / 2, upperLeft.getY() + dimensions.getHeight() / 2);
	}

	@Override
	public void pinch() {
		((AppiumDriver) parent).pinch(this);	
	}

	@Override
	public void tap(int fingers, int duration) {
		((AppiumDriver) parent).tap(fingers, this, duration);		
	}

	@Override
	public void zoom() {
		((AppiumDriver) parent).zoom(this);		
	}
	

	@Override
	public void swipe(SwipeElementDirection direction, int duration) {
		direction.swipe((AppiumDriver) parent, this, duration);		
	}
}
