package io.appium.java_client.pagefactory;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
/**
 This annotation is used when some element waits for time
 that differs from defined by default
 */
public @interface WithTimeout {
    long timeout();
    TimeUnit timeUnit();
}
