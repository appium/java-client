package io.appium.java_client.remote;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static io.appium.java_client.internal.CapabilityHelpers.APPIUM_PREFIX;

// TODO: simplify with lombok as another PR
public class DirectConnect {
    private static final String DIRECT_CONNECT_PROTOCOL = "directConnectProtocol";
    private static final String DIRECT_CONNECT_PATH = "directConnectPath";
    private static final String DIRECT_CONNECT_HOST = "directConnectHost";
    private static final String DIRECT_CONNECT_PORT = "directConnectPort";

    public final String protocol;

    public final String path;

    public final String host;

    public final String port;

    public DirectConnect(Map<String, ?> responseValue) {
        this.protocol = this.getDirectConnectValue(responseValue, DIRECT_CONNECT_PROTOCOL);
        this.path = this.getDirectConnectValue(responseValue, DIRECT_CONNECT_PATH);
        this.host = this.getDirectConnectValue(responseValue, DIRECT_CONNECT_HOST);
        this.port = this.getDirectConnectValue(responseValue, DIRECT_CONNECT_PORT);
    }

    @Nullable
    private String getDirectConnectValue(Map<String, ?> responseValue, String key) {
        Object directConnectPath = responseValue.get(APPIUM_PREFIX + key);
        if (directConnectPath != null) {
            return String.valueOf(directConnectPath);
        }
        directConnectPath = responseValue.get(key);
        return directConnectPath == null ? null : String.valueOf(directConnectPath);
    }

    /**
     * Returns true if the {@link DirectConnect} instance has a valid connection data
     * @return true if all connection information have each value.
     */
    public boolean isValid() {
        return Stream.of(this.protocol, this.path, this.host, this.port).noneMatch(Objects::isNull);
    }

    /**
     * Return a URL with stored protocol, host, port and path
     * @return A URL object
     * @throws MalformedURLException
     */
    public URL getUrl() throws MalformedURLException {
        String newUrlCandidate = String.format("%s://%s:%s%s", this.protocol, this.host, this.port, this.path);

        try {
            return new URL(newUrlCandidate);
        } catch (MalformedURLException e) {
            throw new MalformedURLException(
                    String.format("The remote server returned an invalid value to build the direct connect URL: %s",
                            newUrlCandidate)
            );
        }
    }
}
