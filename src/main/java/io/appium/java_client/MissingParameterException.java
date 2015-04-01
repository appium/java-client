package io.appium.java_client;

@SuppressWarnings("serial")
public class MissingParameterException extends IllegalArgumentException {

  public MissingParameterException(String reason) {
    super(reason);
  }

  public MissingParameterException(String reason, Throwable cause) {
    super(reason, cause);
  }

}