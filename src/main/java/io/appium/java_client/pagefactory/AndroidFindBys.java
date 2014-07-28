package io.appium.java_client.pagefactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to mark a field on a Page Object to indicate that lookup should use a series of @AndroidFindBy tags
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface AndroidFindBys {
	AndroidFindBy[] value();
}
