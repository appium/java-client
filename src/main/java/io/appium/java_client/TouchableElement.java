package io.appium.java_client;

import org.openqa.selenium.WebElement;

/**
 * It supposed that mobile elements could be tappable, swipeable, zoomable and so on.
 * This interface extends {@link WebElement} and describes this behavior.
 */
public interface TouchableElement extends WebElement {
	
	/**
    * Convenience method for pinching the given element.
	* "pinching" refers to the action of two appendages pressing the screen and sliding towards each other.
	* NOTE:
	* This convenience method places the initial touches around the element, if this would happen to place one of them
	* off the screen, appium with return an outOfBounds error. In this case, revert to using the MultiTouchAction api
    * instead of this method.
	*
	*/
	public void pinch();
	
	/**
	 * Convenience method for tapping the center of the given element
	 * 
	 * @param fingers
	 *            number of fingers/appendages to tap with
	 * @param duration
	 *            how long between pressing down, and lifting fingers/appendages
	 */
	public void tap(int fingers, int duration);
	
	/**
	   * Convenience method for "zooming in" on the given element.
	   * "zooming in" refers to the action of two appendages pressing the screen and sliding away from each other.
	   * NOTE:
	   * This convenience method slides touches away from the element, if this would happen to place one of them
	   * off the screen, appium will return an outOfBounds error. In this case, revert to using the MultiTouchAction api
	   * instead of this method.
	   */
	 public void zoom();
	 
	 /**
		 * Convenience method for swiping across the given element
		 *
		 * @param duration
		 *            amount of time in milliseconds for the entire swipe action to
		 *            take
		 */
	  public void swipe(int duration);

}
