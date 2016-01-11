package io.appium.java_client.localserver;


import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ThreadSafetyTest {

    private static abstract class Action implements Cloneable {
        abstract Object perform();

        public Action clone() {
            try {
                return (Action) super.clone();
            }
            catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }
    }

    private static class TestThread implements Runnable {
        private final Action action;
        private Object result;
        private Throwable t;

        TestThread(Action action) {
            this.action = action;
        }

        @Override
        public void run() {
            try {
                result = action.perform();
            }
            catch (Throwable t) {
                this.t = t;
            }

        }
    }

    final AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
    final Action run = new Action() {
        @Override
        Object perform() {
            service.start();
            return "OK";
        }
    };
    final Action run2 = run.clone();

    final Action isRunning = new Action() {
        @Override
        Object perform() {
            return service.isRunning();
        }
    };
    final Action isRunning2 = isRunning.clone();

    final Action stop = new Action() {
        @Override
        Object perform() {
            service.stop();
            return "OK";
        }
    };

    final Action stop2 = stop.clone();

    @Test
    public void whenFewTreadsDoTheSameWork() throws Throwable {

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

            if (runTestThread.t != null) {
                throw runTestThread.t;
            }

            if (runTestThread2.t != null) {
                throw runTestThread2.t;
            }

            assertTrue(runTestThread.result.equals("OK"));
            assertTrue(runTestThread2.result.equals("OK"));
            assertTrue(service.isRunning());

            isRunningThread.start();
            isRunningThread2.start();

            while (isRunningThread.isAlive() || isRunningThread2.isAlive()) {
                //do nothing
            }

            if (isRunningTestThread.t != null) {
                throw isRunningTestThread.t;
            }

            if (isRunningTestThread2.t != null) {
                throw isRunningTestThread2.t;
            }

            assertTrue(isRunningTestThread.result.equals(true));
            assertTrue(isRunningTestThread2.result.equals(true));

            stopThread.start();
            stopThread2.start();

            while (stopThread.isAlive() || stopThread2.isAlive()) {
                //do nothing
            }

            if (stopTestThread.t != null) {
                throw stopTestThread.t;
            }

            if (stopTestThread2.t != null) {
                throw stopTestThread2.t;
            }

            assertTrue(stopTestThread.result.equals("OK"));
            assertTrue(stopTestThread2.result.equals("OK"));
            assertTrue(!service.isRunning());
        }
        finally {
            if (service.isRunning()) {
                service.stop();
            }
        }

    }

    @Test
    public void whenFewTreadsDoDifferentWork() throws Throwable {
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
            isRunningThread.start();//(2)
            stopThread.start(); //(3)

            while (runThread.isAlive() || isRunningThread.isAlive() || stopThread.isAlive()) {
                //do nothing
            }

            if (runTestThread.t != null) {
                throw runTestThread.t;
            }

            if (isRunningTestThread.t != null) {
                throw isRunningTestThread.t;
            }

            if (stopTestThread.t != null) {
                throw stopTestThread.t;
            }

            assertTrue(runTestThread.result.equals("OK")); //the service had been started firstly (see (1))
            assertTrue(isRunningTestThread.result.equals(true)); //it was running (see (2))
            assertTrue(stopTestThread.result.equals("OK")); //and then the test tried to shut down it (see (3))
            assertTrue(!service.isRunning());

            stopThread2.start(); // (1)
            isRunningThread2.start(); // (2)
            runThread2.start(); //(3)

            while (runThread2.isAlive() || isRunningThread2.isAlive() || stopThread2.isAlive()) {
                //do nothing
            }

            if (runTestThread2.t != null) {
                throw runTestThread.t;
            }

            if (isRunningTestThread2.t != null) {
                throw isRunningTestThread.t;
            }

            if (stopTestThread2.t != null) {
                throw stopTestThread.t;
            }

            assertTrue(stopTestThread2.result.equals("OK")); //the service had not been started firstly (see (1)), it is ok
            assertTrue(isRunningTestThread2.result.equals(false)); //so it couldn't being running (see (2))
            assertTrue(runTestThread2.result.equals("OK")); //and then it was started (see (3))
            assertTrue(service.isRunning());
        }
        finally {
            if (service.isRunning()) {
                service.stop();
            }
        }
    }
}
