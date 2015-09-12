package io.appium.java_client.remote;


import com.google.common.base.Throwables;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.service.DriverService;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.Map;

public class AppiumCommandExecutor extends HttpCommandExecutor{

    private final DriverService service;

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands, URL addressOfRemoteServer) {
        super(additionalCommands, addressOfRemoteServer);
        service = null;
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands, DriverService service) {
        super(additionalCommands, service.getUrl());
        this.service = service;
    }

    @Override
    public Response execute(Command command) throws IOException, WebDriverException {
        if (DriverCommand.NEW_SESSION.equals(command.getName()) && service != null) {
            service.start();
        }

        try {
            return super.execute(command);
        } catch (Throwable t) {
            Throwable rootCause = Throwables.getRootCause(t);
            if (rootCause instanceof ConnectException &&
                    rootCause.getMessage().contains("Connection refused") && service != null){
                if (service.isRunning())
                    throw new WebDriverException("The session is closed!", t);

                if (!service.isRunning())
                    throw new WebDriverException("The appium server has accidentally died!", t);
            }
            Throwables.propagateIfPossible(t);
            throw new WebDriverException(t);
        } finally {
            if (DriverCommand.QUIT.equals(command.getName()) && service != null) {
                service.stop();
            }
        }
    }

}
