package io.appium.java_client.remote;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static io.appium.java_client.internal.CapabilityHelpers.APPIUM_PREFIX;

@Accessors(fluent = false, chain = false)
public class DirectConnect {
    private static final String DIRECT_CONNECT_PROTOCOL = "directConnectProtocol";
    private static final String DIRECT_CONNECT_PATH = "directConnectPath";
    private static final String DIRECT_CONNECT_HOST = "directConnectHost";
    private static final String DIRECT_CONNECT_PORT = "directConnectPort";

    @Getter(AccessLevel.PUBLIC) private final String protocol;

    @Getter(AccessLevel.PUBLIC) private final String path;

    @Getter(AccessLevel.PUBLIC) private final String host;

    @Getter(AccessLevel.PUBLIC) private final String port;

    /**
     * Create a DirectConnect instance.
     * @param responseValue is the response body
     */
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
     * Returns true if the {@link DirectConnect} instance member has nonnull values.
     * @return true if each connection information has a nonnull value
     */
    public boolean isValid() {
        return Stream.of(this.protocol, this.path, this.host, this.port).noneMatch(Objects::isNull);
    }

    /**
     * Returns a URL instance built with members in the DirectConnect instance.
     * @return A URL object
     * @throws MalformedURLException if the built url was invalid
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
