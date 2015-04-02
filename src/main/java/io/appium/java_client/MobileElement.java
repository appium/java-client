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

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.FileDetector;

import java.util.List;

@SuppressWarnings("unchecked")
/**
 * @param <RequiredElementType> means the required type from the list of allowed types below 
 * that implement {@link WebElement} Instances of the defined type will be 
 * returned via findElement* and findElements*. 
 * Warning (!!!). Allowed types:<br/>
 * {@link WebElement}<br/>
 * {@link TouchableElement}<br/>
 * {@link RemoteWebElement}<br/>
 * {@link MobileElement} and its subclasses that designed specifically for each target mobile OS (still Android and iOS)
 */
public abstract class MobileElement<RequiredElementType extends WebElement> extends DefaultGenericMobileElement<RequiredElementType> {

	protected FileDetector fileDetector;

	public Point getCenter() {
		Point upperLeft = this.getLocation();
		Dimension dimensions = this.getSize();
		return new Point(upperLeft.getX() + dimensions.getWidth() / 2, upperLeft.getY() + dimensions.getHeight() / 2);
	}

	@Override
	public void pinch() {
		((AppiumDriver<?>) parent).pinch(this);	
	}

	@Override
	public void tap(int fingers, int duration) {
		((AppiumDriver<?>) parent).tap(fingers, this, duration);		
	}

	@Override
	public void zoom() {
		((AppiumDriver<?>) parent).zoom(this);		
	}
	

	@Override
	public void swipe(SwipeElementDirection direction, int duration) {
		direction.swipe((AppiumDriver<?>) parent, this, 0, 0, duration);		
	}

	@Override
	public void swipe(SwipeElementDirection direction, int offsetFromStartBorder,
			int offsetFromEndBorder, int duration) throws IllegalCoordinatesException {
		direction.swipe((AppiumDriver<?>) parent, this, offsetFromStartBorder, 
				offsetFromEndBorder, duration);		
	}

    @Override
    public List<RequiredElementType> findElements(By by){
        return super.findElements(by);
    }

    @Override
    public List<RequiredElementType> findElementsById(String id){
        return super.findElementsById(id);
    }

	public List<RequiredElementType> findElementsByLinkText(String using) {
        return super.findElementsByLinkText(using);
    }

    public List<RequiredElementType> findElementsByPartialLinkText(String using) {
        return super.findElementsByPartialLinkText(using);
    }

    public List<RequiredElementType> findElementsByTagName(String using) {
        return super.findElementsByTagName(using);
    }

    public List<RequiredElementType> findElementsByName(String using) {
        return super.findElementsByName(using);
    }

    public List<RequiredElementType> findElementsByClassName(String using) {
        return super.findElementsByClassName(using);
    }

    public List<RequiredElementType> findElementsByCssSelector(String using) {
        return super.findElementsByCssSelector(using);
    }

	public List<RequiredElementType> findElementsByXPath(String using) {
        return super.findElementsByXPath(using);
    }

    @Override
    public List<RequiredElementType> findElementsByAccessibilityId(String using) {
        return (List<RequiredElementType>) findElements("accessibility id", using);
    }
}
