package io.appium.java_client.service.local;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import org.junit.Test;

public class ThreadSafetyTest {

    private final AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
    private final Action run = new Action() {
        @Override protected Object perform() {
            service.start();
            return "OK";
        }
    };
    private final Action run2 = run.clone();
    private final Action isRunning = new Action() {
        @Override protected Object perform() {
            return service.isRunning();
        }
    };
    private final Action isRunning2 = isRunning.clone();
    private final Action stop = new Action() {
        @Override protected Object perform() {
            service.stop();
            return "OK";
        }
    };
    private final Action stop2 = stop.clone();

    @Test public void whenFewTreadsDoTheSameWork() throws Throwable {

        TestThread runTestThread = new TestThread(run);
        TestThread runTestThread2 = new TestThread(run2);

        TestThread isRunningTestThread = new TestThread(isRunning);
        TestThread isRunningTestThread2 = new TestThread(isRunning2);

        TestThread stopTestThread = new TestThread(stop);
        TestThread stopTestThread2 = new TestThread(stop2);

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

            assertTrue("OK".equals(runTestThread.result));
            assertTrue("OK".equals(runTestThread2.result));
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

            assertTrue(isRunningTestThread.result.equals(true));
            assertTrue(isRunningTestThread2.result.equals(true));

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

            assertTrue("OK".equals(stopTestThread.result));
            assertTrue("OK".equals(stopTestThread2.result));
            assertFalse(service.isRunning());
        } finally {
            if (service.isRunning()) {
                service.stop();
            }
        }

    }

    @Test public void whenFewTreadsDoDifferentWork() throws Throwable {
        TestThread runTestThread = new TestThread(run);
        TestThread runTestThread2 = new TestThread(run2);

        TestThread isRunningTestThread = new TestThread(isRunning);
        TestThread isRunningTestThread2 = new TestThread(isRunning2);

        TestThread stopTestThread = new TestThread(stop);
        TestThread stopTestThread2 = new TestThread(stop2);

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

            assertTrue("OK".equals(runTestThread.result)); //the service had been started firstly (see (1))
            assertTrue(Boolean.TRUE.equals(isRunningTestThread.result)); //it was running (see (2))
            assertTrue("OK".equals(stopTestThread.result)); //and then the test tried to shut down it (see (3))
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
            assertTrue(Boolean.FALSE.equals(isRunningTestThread2.result));
            //the service had not been started firstly (see (2)), it is ok
            assertTrue("OK".equals(stopTestThread2.result));
            assertTrue("OK".equals(runTestThread2.result)); //and then it was started (see (3))
            assertTrue(service.isRunning());
        } finally {
            if (service.isRunning()) {
                service.stop();
            }
        }
    }


    private abstract static class Action implements Cloneable {
        protected abstract Object perform();

        public Action clone() {
            try {
                return (Action) super.clone();
            } catch (Throwable t) {
                throw new AppiumServerHasNotBeenStartedLocallyException(t.getMessage(), t);
            }
        }
    }


    private static class TestThread implements Runnable {
        private final Action action;
        private Object result;
        private Throwable throwable;

        TestThread(Action action) {
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
