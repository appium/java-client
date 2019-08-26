package io.appium.java_client.selenium.remote;

import org.openqa.selenium.remote.ExecuteMethod;
import org.openqa.selenium.remote.Response;

import java.util.Map;

public class RemoteExecuteMethod implements ExecuteMethod {
    private final RemoteWebDriver driver;

    public RemoteExecuteMethod(RemoteWebDriver driver) {
        this.driver = driver;
    }

    @Override
    public Object execute(String commandName, Map<String, ?> parameters) {
        Response response;

        if (parameters == null || parameters.isEmpty()) {
            response = driver.execute(commandName);
        } else {
            response = driver.execute(commandName, parameters);
        }

        return response.getValue();
    }
}