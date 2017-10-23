package io.appium.java_client.touch;

@FunctionalInterface
public interface IThrowingRunnable<E extends Throwable> {
    void run() throws E;
}
