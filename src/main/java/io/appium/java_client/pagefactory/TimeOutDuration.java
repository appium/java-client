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

package io.appium.java_client.pagefactory;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.TimeUnit;

/**
 * Represents an duration of waiting for element rendering.
 */
public class TimeOutDuration {

    private long time;
    private TimeUnit unit;

    /**
     * @param time The amount of time.
     * @param unit The unit of time.
     */
    public TimeOutDuration(long time, TimeUnit unit) {
        setTime(time, unit);
    }

    public long getTime() {
        return time;
    }

    public TimeUnit getTimeUnit() {
        return unit;
    }

    public void setTime(TimeUnit newTimeUnit) {
        checkNotNull(newTimeUnit);
        unit = newTimeUnit;
    }

    public void setTime(long newTime) {
        checkArgument(newTime >= 0, "Duration < 0: %d", newTime);
        time = newTime;
    }

    public void setTime(long newTime, TimeUnit newTimeUnit) {
        setTime(newTime);
        setTime(newTimeUnit);
    }
}
