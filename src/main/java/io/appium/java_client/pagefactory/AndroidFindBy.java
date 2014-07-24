package io.appium.java_client.pagefactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Used to mark a field on a Page Object to indicate an alternative mechanism for locating the
 * element or a list of elements. Used in conjunction with
 * {@link org.openqa.selenium.support.PageFactory}
 * this allows users to quickly and easily create PageObjects.
 * using Android UI selectors, accessibility, id, name, class name, tag and xpath
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface AndroidFindBy {
	String uiAutomator() default "";
	String accessibility()  default "";
	String id() default "";
	String name() default "";
	String className() default "";
	String tagName() default "";
	String xpath()  default "";
}
