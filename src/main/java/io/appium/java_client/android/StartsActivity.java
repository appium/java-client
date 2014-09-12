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
