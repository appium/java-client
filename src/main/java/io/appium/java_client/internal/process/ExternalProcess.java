/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.java_client.internal.process;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * A child process whose combined stdout/stderr is forwarded to a stream and
 * also retained (last bytes only) for diagnostics. Trimmed from Selenium's
 * {@code org.openqa.selenium.os.ExternalProcess} (Apache License 2.0).
 */
public final class ExternalProcess {
    private static final Logger LOG = LoggerFactory.getLogger(ExternalProcess.class);
    private static final int OUTPUT_BUFFER_SIZE = 32768;
    private static final Duration DEFAULT_SHUTDOWN_TIMEOUT = Duration.ofSeconds(4);

    private final Process process;
    private final TailBuffer output;
    private final Thread worker;

    private ExternalProcess(Process process, TailBuffer output, Thread worker) {
        this.process = process;
        this.output = output;
        this.worker = worker;
    }

    /**
     * Creates a process builder.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * The last bytes of the combined stdout and stderr.
     *
     * @return the output decoded with the default charset
     */
    public String getOutput() {
        return output.toString(Charset.defaultCharset());
    }

    /**
     * Checks whether the process is still running.
     *
     * @return true if the process is alive
     */
    public boolean isAlive() {
        return process.isAlive();
    }

    /**
     * Waits for the process to exit and its output to be fully forwarded.
     *
     * @param timeout how long to wait
     * @return true if the process has exited
     */
    public boolean waitFor(Duration timeout) {
        boolean exited;
        try {
            exited = process.waitFor(timeout.toMillis(), MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
        if (exited) {
            runUninterruptibly(this::stopWorker);
        }
        return exited;
    }

    /**
     * Terminates the process gracefully, then forcibly if it is still alive after the default timeout.
     */
    public void shutdown() {
        shutdown(DEFAULT_SHUTDOWN_TIMEOUT);
    }

    /**
     * Terminates the process gracefully, then forcibly if it is still alive after the given timeout.
     * The termination always completes, even if the calling thread is interrupted;
     * the interrupt status is restored afterwards.
     *
     * @param timeout how long to wait for each termination attempt
     */
    public void shutdown(Duration timeout) {
        runUninterruptibly(interrupts -> {
            try {
                // Use the handle to avoid closing the process streams
                var handle = process.toHandle();
                if (handle.supportsNormalTermination()) {
                    handle.destroy();
                    if (awaitExit(timeout, interrupts)) {
                        return;
                    }
                }
                handle.destroyForcibly();
                awaitExit(timeout, interrupts);
            } finally {
                stopWorker(interrupts);
            }
        });
    }

    private void runUninterruptibly(Consumer<AtomicBoolean> action) {
        var interrupts = new AtomicBoolean();
        try {
            action.accept(interrupts);
        } finally {
            if (interrupts.get()) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private boolean awaitExit(Duration timeout, AtomicBoolean interrupts) {
        long deadline = System.nanoTime() + timeout.toNanos();
        while (true) {
            try {
                return process.waitFor(Math.max(0, deadline - System.nanoTime()), NANOSECONDS);
            } catch (InterruptedException e) {
                interrupts.set(true);
            }
        }
    }

    private void joinWorker(Duration timeout, AtomicBoolean interrupts) {
        long deadline = System.nanoTime() + timeout.toNanos();
        while (worker.isAlive()) {
            long remainingMs = NANOSECONDS.toMillis(deadline - System.nanoTime());
            if (remainingMs <= 0) {
                return;
            }
            try {
                worker.join(remainingMs);
            } catch (InterruptedException e) {
                interrupts.set(true);
            }
        }
    }

    private void stopWorker(AtomicBoolean interrupts) {
        joinWorker(Duration.ofSeconds(8), interrupts);
        // No-op if the worker has finished, otherwise unblocks it
        worker.interrupt();
        joinWorker(Duration.ofSeconds(2), interrupts);
    }

    /**
     * Builds {@link ExternalProcess} instances.
     */
    public static final class Builder {
        private final ProcessBuilder builder = new ProcessBuilder();
        private @Nullable OutputStream copyOutputTo;

        private Builder() {
        }

        /**
         * Sets the executable and its arguments.
         *
         * @param executable the executable path
         * @param arguments the executable arguments
         * @return this instance
         */
        public Builder command(String executable, List<String> arguments) {
            var command = new ArrayList<String>(arguments.size() + 1);
            command.add(executable);
            command.addAll(arguments);
            builder.command(command);
            return this;
        }

        /**
         * Sets an environment variable of the process.
         *
         * @param name  the variable name
         * @param value the variable value
         * @return this instance
         */
        public Builder environment(String name, String value) {
            builder.environment().put(name, value);
            return this;
        }

        /**
         * Sets environment variables of the process.
         *
         * @param environment the variable names mapped to their values
         * @return this instance
         */
        public Builder environment(Map<String, String> environment) {
            environment.forEach(this::environment);
            return this;
        }

        /**
         * Sets where to forward the combined stdout and stderr.
         * The stream is never closed by the process.
         *
         * @param stream the destination stream
         * @return this instance
         */
        public Builder copyOutputTo(OutputStream stream) {
            copyOutputTo = stream;
            return this;
        }

        /**
         * Starts the process.
         *
         * @return the running process
         * @throws IOException if the process cannot be started
         */
        public ExternalProcess start() throws IOException {
            builder.redirectErrorStream(true);
            var process = builder.start();
            try {
                var buffer = new TailBuffer(OUTPUT_BUFFER_SIZE);
                var worker = new Thread(
                        () -> forwardOutput(process, buffer, copyOutputTo),
                        "External Process Output Forwarder - " + builder.command().get(0)
                );
                worker.setDaemon(true);
                worker.start();
                return new ExternalProcess(process, buffer, worker);
            } catch (Throwable t) {
                // Do not leak the process if the worker could not be started
                process.destroyForcibly();
                throw t;
            }
        }

        private static void forwardOutput(Process process, TailBuffer buffer, @Nullable OutputStream target) {
            // The process output must always be drained, otherwise the process may block
            InputStream input = process.getInputStream();
            var chunk = new byte[8192];
            try {
                int count;
                while ((count = input.read(chunk)) != -1) {
                    buffer.write(chunk, 0, count);
                    if (target != null) {
                        target.write(chunk, 0, count);
                    }
                }
            } catch (IOException e) {
                LOG.warn("Failed to copy the output of process {}", process.pid(), e);
            }
            LOG.debug("Completed copying the output of process {}", process.pid());
        }
    }

    /**
     * Keeps the last N bytes written to it.
     */
    private static final class TailBuffer extends OutputStream {
        private final byte[] buffer;
        private int end;
        private boolean filled;

        TailBuffer(int size) {
            this.buffer = new byte[size];
        }

        @Override
        public synchronized void write(int b) {
            if (end == buffer.length) {
                filled = true;
                end = 0;
            }
            buffer[end++] = (byte) b;
        }

        @Override
        public synchronized void write(byte[] b, int off, int len) {
            // Only the last buffer.length bytes can survive
            int skip = Math.max(0, len - buffer.length);
            for (int i = off + skip; i < off + len; i++) {
                write(b[i]);
            }
        }

        synchronized String toString(Charset encoding) {
            if (!filled) {
                return new String(buffer, 0, end, encoding);
            }
            var result = new byte[buffer.length];
            int tail = buffer.length - end;
            System.arraycopy(buffer, end, result, 0, tail);
            System.arraycopy(buffer, 0, result, tail, end);
            return new String(result, encoding);
        }
    }
}
