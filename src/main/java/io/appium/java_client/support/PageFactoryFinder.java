package io.appium.java_client.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Marks a custom locator annotation with its builder class. */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface PageFactoryFinder {
    /** Specify the builder implementation used to construct a locator from the annotated type.
     * @return the builder class */
    Class<? extends AbstractFindByBuilder<?>> value();
}
