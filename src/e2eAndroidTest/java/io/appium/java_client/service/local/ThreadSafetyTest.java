package io.appium.java_client.service.local;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ThreadSafetyTest {

    private final AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
    private final Action<String> run = new Action<String>() {
        @Override protected String perform() {
            service.start();
            return "OK";
        }
    };
    private final Action<String> run2 = run.clone();
    private final Action<Boolean> isRunning = new Action<Boolean>() {
        @Override protected Boolean perform() {
            return service.isRunning();
        }
    };
    private final Action<Boolean> isRunning2 = isRunning.clone();
    private final Action<String> stop = new Action<String>() {
        @Override protected String perform() {
            service.stop();
            return "OK";
        }
    };
    private final Action<String> stop2 = stop.clone();

    @Test
    void whenFewTreadsDoTheSameWork() throws Throwable {

        TestThread<String> runTestThread = new TestThread<>(run);
        TestThread<String> runTestThread2 = new TestThread<>(run2);

        TestThread<Boolean> isRunningTestThread = new TestThread<>(isRunning);
        TestThread<Boolean> isRunningTestThread2 = new TestThread<>(isRunning2);

        TestThread<String> stopTestThread = new TestThread<>(stop);
        TestThread<String> stopTestThread2 = new TestThread<>(stop2);

        Thread runThread = new Thread(runTestThread);
        Thread runThread2 = new Thread(runTestThread2);

        Thread isRunningThread = new Thread(isRunningTestThread);
        Thread isRunningThread2 = new Thread(isRunningTestThread2);

        Thread stopThread = new Thread(stopTestThread);
        Thread stopThread2 = new Thread(stopTestThread2);

        try {
            runThread.start();
            runThread2.start();

            while (runThread.isAlive() || runThread2.isAlive()) {
                //do nothing
            }

            if (runTestThread.throwable != null) {
                throw runTestThread.throwable;
            }

            if (runTestThread2.throwable != null) {
                throw runTestThread2.throwable;
            }

            assertEquals("OK", runTestThread.result);
            assertEquals("OK", runTestThread2.result);
            assertTrue(service.isRunning());

            isRunningThread.start();
            isRunningThread2.start();

            while (isRunningThread.isAlive() || isRunningThread2.isAlive()) {
                //do nothing
            }

            if (isRunningTestThread.throwable != null) {
                throw isRunningTestThread.throwable;
            }

            if (isRunningTestThread2.throwable != null) {
                throw isRunningTestThread2.throwable;
            }

            assertTrue(isRunningTestThread.result);
            assertTrue(isRunningTestThread2.result);

            stopThread.start();
            stopThread2.start();

            while (stopThread.isAlive() || stopThread2.isAlive()) {
                //do nothing
            }

            if (stopTestThread.throwable != null) {
                throw stopTestThread.throwable;
            }

            if (stopTestThread2.throwable != null) {
                throw stopTestThread2.throwable;
            }

            assertEquals("OK", stopTestThread.result);
            assertEquals("OK", stopTestThread2.result);
            assertFalse(service.isRunning());
        } finally {
            if (service.isRunning()) {
                service.stop();
            }
        }

    }

    @Test
    void whenFewTreadsDoDifferentWork() throws Throwable {
        TestThread<String> runTestThread = new TestThread<>(run);
        TestThread<String> runTestThread2 = new TestThread<>(run2);

        TestThread<Boolean> isRunningTestThread = new TestThread<>(isRunning);
        TestThread<Boolean> isRunningTestThread2 = new TestThread<>(isRunning2);

        TestThread<String> stopTestThread = new TestThread<>(stop);
        TestThread<String> stopTestThread2 = new TestThread<>(stop2);

        Thread runThread = new Thread(runTestThread);
        Thread runThread2 = new Thread(runTestThread2);

        Thread isRunningThread = new Thread(isRunningTestThread);
        Thread isRunningThread2 = new Thread(isRunningTestThread2);

        Thread stopThread = new Thread(stopTestThread);
        Thread stopThread2 = new Thread(stopTestThread2);

        try {
            runThread.start(); //(1)
            Thread.sleep(10);
            isRunningThread.start();//(2)
            Thread.sleep(10);
            stopThread.start(); //(3)

            while (runThread.isAlive() || isRunningThread.isAlive() || stopThread.isAlive()) {
                //do nothing
            }

            if (runTestThread.throwable != null) {
                throw runTestThread.throwable;
            }

            if (isRunningTestThread.throwable != null) {
                throw isRunningTestThread.throwable;
            }

            if (stopTestThread.throwable != null) {
                throw stopTestThread.throwable;
            }

            assertEquals("OK", runTestThread.result); //the service had been started firstly (see (1))
            assertTrue(isRunningTestThread.result); //it was running (see (2))
            assertEquals("OK", stopTestThread.result); //and then the test tried to shut down it (see (3))
            assertFalse(service.isRunning());

            isRunningThread2.start(); // (1)
            Thread.sleep(10);
            stopThread2.start(); // (2)
            Thread.sleep(10);
            runThread2.start(); //(3)

            while (runThread2.isAlive() || isRunningThread2.isAlive() || stopThread2.isAlive()) {
                //do nothing
            }

            if (runTestThread2.throwable != null) {
                throw runTestThread.throwable;
            }

            if (isRunningTestThread2.throwable != null) {
                throw isRunningTestThread.throwable;
            }

            if (stopTestThread2.throwable != null) {
                throw stopTestThread.throwable;
            }

            //the service wasn'throwable being running (see (1))
            assertFalse(isRunningTestThread2.result);
            //the service had not been started firstly (see (2)), it is ok
            assertEquals("OK", stopTestThread2.result);
            assertEquals("OK", runTestThread2.result); //and then it was started (see (3))
            assertTrue(service.isRunning());
        } finally {
            if (service.isRunning()) {
                service.stop();
            }
        }
    }


    private abstract static class Action<T> implements Cloneable {
        protected abstract T perform();

        public Action<T> clone() {
            try {
                return (Action<T>) super.clone();
            } catch (Throwable t) {
                throw new AppiumServerHasNotBeenStartedLocallyException(t.getMessage(), t);
            }
        }
    }


    private static class TestThread<T> implements Runnable {
        private final Action<T> action;
        private T result;
        private Throwable throwable;

        TestThread(Action<T> action) {
            this.action = action;
        }

        @Override public void run() {
            try {
                result = action.perform();
            } catch (Throwable t) {
                this.throwable = t;
            }

        }
    }
}
