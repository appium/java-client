package io.appium.java_client;

public interface ScrollsTo {
	
	/**
	* Scroll to an element which contains the given text.
	* @param text
	*/	
	public MobileElement scrollTo(String text);
	
	/**
	* Scroll to an element with the given text.
	* @param text
	*/	
	public MobileElement scrollToExact(String text);

}
