package io.appium.java_client.pagefactory;

import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

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

    public long getTime(){
        return time;
    }

    public TimeUnit getTimeUnit(){
        return unit;
    }

    public void setTime(long newTime){
        checkArgument(newTime >= 0, "Duration < 0: %d", newTime);
        time = newTime;
    }

    public void setTime(TimeUnit newTimeUnit){
        checkNotNull(newTimeUnit);
        unit = newTimeUnit;
    }

    public void setTime(long newTime, TimeUnit newTimeUnit){
        setTime(newTime);
        setTime(newTimeUnit);
    }
}
