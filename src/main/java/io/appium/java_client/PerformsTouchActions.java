package io.appium.java_client;

public interface PerformsTouchActions {
	/**
	 * Performs a chain of touch actions, which together can be considered an
	 * entire gesture. See the Webriver 3 spec
	 * https://dvcs.w3.org/hg/webdriver/raw-file/default/webdriver-spec.html
	 * 
	 * It's more convenient to call the perform() method of the TouchAction
	 * object itself.
	 * 
	 * @param touchAction
	 *            A TouchAction object, which contains a list of individual
	 *            touch actions to perform
	 * @return the same touch action object
	 */
	public TouchAction performTouchAction(TouchAction touchAction);

	/**
	 * Performs multiple TouchAction gestures at the same time, to simulate
	 * multiple fingers/touch inputs. See the Webriver 3 spec
	 * https://dvcs.w3.org/hg/webdriver/raw-file/default/webdriver-spec.html
	 * 
	 * It's more convenient to call the perform() method of the MultiTouchAction
	 * object.
	 * 
	 * @param multiAction
	 *            the MultiTouchAction object to perform.
	 */	
	public void performMultiTouchAction(MultiTouchAction multiAction);
}
