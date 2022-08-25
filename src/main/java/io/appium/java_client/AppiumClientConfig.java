package io.appium.java_client;

/**
 * Manage Appium Client options.
 */

public class AppiumClientConfig {
    private boolean directConnect = false;

    public AppiumClientConfig() {}

    /**
     * @return the instance of AppiumClientConfig.
     */
    public static AppiumClientConfig defaultConfig() {
        return new AppiumClientConfig();
    }

    /**
     * Whether enable directConnect feature described in
     * https://appiumpro.com/editions/86-connecting-directly-to-appium-hosts-in-distributed-environments
     *
     * @param directConnect if enable the directConnect feature
     * @return A self reference
     */
    public AppiumClientConfig directConnect(boolean directConnect) {
        this.directConnect = directConnect;
        return this;
    }

    /**
     * Whether enable directConnect feature is enabled.
     *
     * @return If the directConnect is enabled.Defaults false.
     */
    public boolean isDirectConnectEnabled() { return this.directConnect; }
}
