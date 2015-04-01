package io.appium.java_client;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.remote.ExecuteMethod;
import org.openqa.selenium.remote.Response;

import java.util.Map;

public class AppiumExecutionMethod implements ExecuteMethod {
  private final AppiumDriver<?> driver;

  public AppiumExecutionMethod(AppiumDriver<?> driver) {
    this.driver = driver;
  }

  public Object execute(String commandName, Map<String, ?> parameters) {
    Response response;

    if (parameters == null || parameters.isEmpty()) {
      response = driver.execute(commandName, ImmutableMap.<String, Object>of());
    } else {
      response = driver.execute(commandName, parameters);
    }

    return response.getValue();
  }
}
