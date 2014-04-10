package io.appium.java_client;

import org.openqa.selenium.NotFoundException;

/**
 * Thrown by {@link org.openqa.selenium.WebDriver.TargetLocator#context(String) WebDriver.switchTo().context(String
 * name)}.
 */
public class NoSuchContextException extends NotFoundException {

  public NoSuchContextException(String reason) {
    super(reason);
  }

  public NoSuchContextException(String reason, Throwable cause) {
    super(reason, cause);
  }

}