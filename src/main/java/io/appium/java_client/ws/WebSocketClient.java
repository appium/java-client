package io.appium.java_client.ws;

import org.openqa.selenium.WebDriverException;

import java.io.IOException;
import java.net.URI;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;

public abstract class WebSocketClient {
    private URI endpoint;

    private void setEndpoint(URI endpoint) {
        this.endpoint = endpoint;
    }

    public URI getEndpoint() {
        return this.endpoint;
    }

    /**
     * Connects web socket client.
     *
     * @param endpoint The full address of an endpoint to connect to.
     *                 Usually starts with 'ws://'.
     */
    public void connect(URI endpoint) {
        try {
            ContainerProvider
                    .getWebSocketContainer()
                    .connectToServer(this, endpoint);
            setEndpoint(endpoint);
        } catch (IOException | DeploymentException e) {
            throw new WebDriverException(e);
        }
    }
}
