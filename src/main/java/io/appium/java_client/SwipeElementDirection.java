package io.appium.java_client;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

public enum SwipeElementDirection {
	/**
	 * Up from the center of the lower
	 */
	UP{
		@Override
		void swipe(AppiumDriver driver, MobileElement element, 
				int xOffsetStart, int xOffsetEnd, int yOffsetStart, 
				int yOffsetEnd, int duration) throws IllegalCoordinatesException {			
			Point p = element.getCenter();			
			Point location = element.getLocation();
			Dimension size = element.getSize();
			driver.swipe(p.getX(), location.getY() + size.getHeight(), p.getX(), location.getY(), duration);
		}
	},
	/**
	 * Down from the center of the upper
	 */
	DOWN{
		@Override
		void swipe(AppiumDriver driver, MobileElement element, 
				int xOffsetStart, int xOffsetEnd, int yOffsetStart, 
				int yOffsetEnd, int duration) throws IllegalCoordinatesException {
			Point p = element.getCenter();
			Point location = element.getLocation();
			Dimension size = element.getSize();
			driver.swipe(p.getX(), location.getY(), p.getX(), location.getY() + size.getHeight(), duration);
		}
	},
	/**
	 * To the left from the center of the rightmost
	 */
	LEFT{
		@Override
		void swipe(AppiumDriver driver, MobileElement element, 
				int xOffsetStart, int xOffsetEnd, int yOffsetStart, 
				int yOffsetEnd, int duration) throws IllegalCoordinatesException {
			Point p = element.getCenter();
			Point location = element.getLocation();
			Dimension size = element.getSize();
			driver.swipe(location.getX() + size.getWidth(), p.getY(), location.getX(), p.getY(), duration);
		}
	},
	/**
	 * To the right from the center of the leftmost
	 */
	RIGHT{
		@Override
		void swipe(AppiumDriver driver, MobileElement element, 
				int xOffsetStart, int xOffsetEnd, int yOffsetStart, 
				int yOffsetEnd, int duration) throws IllegalCoordinatesException {
			Point p = element.getCenter();
			Point location = element.getLocation();
			Dimension size = element.getSize();
			driver.swipe(location.getX(), p.getY(), location.getX()+ size.getWidth(), p.getY(), duration);
		}
	};
	
	abstract void swipe(AppiumDriver driver, MobileElement element, 
			int xOffsetStart, int xOffsetEnd, int yOffsetStart, 
			int yOffsetEnd, int duration) throws IllegalCoordinatesException;
}
