package io.appium.java_client.remote;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.WebSocket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SwitchableHttpClientTest {

    private static final HttpRequest REQUEST = new HttpRequest(HttpMethod.GET, "/status");

    private static class FakeHttpClient implements HttpClient {
        private final int status;
        private boolean closed;

        FakeHttpClient(int status) {
            this.status = status;
        }

        @Override
        public HttpResponse execute(HttpRequest req) {
            return new HttpResponse().setStatus(status);
        }

        @Override
        public WebSocket openSocket(HttpRequest request, WebSocket.Listener listener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void close() {
            closed = true;
        }
    }

    @Test
    void sendsRequestsToTheCurrentClient() {
        var client = new SwitchableHttpClient(new FakeHttpClient(200));

        assertEquals(200, client.execute(REQUEST).getStatus());
    }

    @Test
    void sendsRequestsToTheNewClientAfterSwitching() {
        var client = new SwitchableHttpClient(new FakeHttpClient(200));

        client.switchTo(new FakeHttpClient(201));

        assertEquals(201, client.execute(REQUEST).getStatus());
    }

    @Test
    void closesThePreviousClientWhenSwitching() {
        var previous = new FakeHttpClient(200);
        var next = new FakeHttpClient(201);
        var client = new SwitchableHttpClient(previous);

        client.switchTo(next);

        assertTrue(previous.closed);
        assertFalse(next.closed);
    }

    @Test
    void closesTheCurrentClient() {
        var current = new FakeHttpClient(200);
        var client = new SwitchableHttpClient(current);

        client.close();

        assertTrue(current.closed);
    }
}
