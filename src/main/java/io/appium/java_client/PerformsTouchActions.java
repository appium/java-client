package io.appium.java_client;

public interface PerformsTouchActions {
	public TouchAction performTouchAction(TouchAction touchAction);

	public void performMultiTouchAction(MultiTouchAction multiAction);
}
