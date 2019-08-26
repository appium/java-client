package io.appium.java_client.selenium.support.pagefactory;

import java.lang.reflect.Field;

/**
 * Allows the PageFactory to decorate fields.
 */
public interface FieldDecorator {
    /**
     * This method is called by PageFactory on all fields to decide how to decorate the field.
     *
     * @param loader The class loader that was used for the page object
     * @param field The field that may be decorated.
     * @return Value to decorate the field with or null if it shouldn't be decorated. If non-null,
     *         must be assignable to the field.
     */
    Object decorate(ClassLoader loader, Field field);
}
