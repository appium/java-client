package io.appium.java_client.pagefactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchableElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dolf on 17.08.15
 */
public class AppiumElementUtils {
    private static final List<Class<? extends WebElement>> AVAILABLE_ELEMENT_CLASSES =
            new ArrayList<Class<? extends WebElement>>(){
                private static final long serialVersionUID = 1L;
                {
                    add(WebElement.class);
                    add(RemoteWebElement.class);
                    add(MobileElement.class);
                    add(TouchableElement.class);
                    add(IOSElement.class);
                    add(AndroidElement.class);
                }

            };

    public static boolean isDecoratableElement(Field field) {
        return AVAILABLE_ELEMENT_CLASSES.contains(field.getType());
    }

    public static boolean isDecoratableElement(Class clazz) {
        return AVAILABLE_ELEMENT_CLASSES.contains(clazz);
    }

    public static boolean isDecoratableList(Field field) {
        if (!isParametrizedList(field)) {
            return false;
        }
        Class listParameterClass = getGenericParameterClass(field);
        return isDecoratableElement(listParameterClass);
    }

    public static boolean isDecoratableMobileElement(Field field) {
        return isDecoratableMobileElement(field.getType());
    }

    public static boolean isDecoratableMobileElement(Class clazz) {
        return isIOSElement(clazz)
            || isAndroidElement(clazz)
            || MobileElement.class.isAssignableFrom(clazz);
    }

    public static boolean isAndroidElement(Class clazz) {
        return AndroidElement.class.isAssignableFrom(clazz);
    }

    public static boolean isIOSElement(Class clazz) {
        return IOSElement.class.isAssignableFrom(clazz);
    }

    public static boolean isDecoratableMobileElementsList(Field field) {
        if (!isParametrizedList(field)) {
            return false;
        }
        Class listParameterClass = getGenericParameterClass(field);
        return isDecoratableMobileElement(listParameterClass);
    }

    public static Class getGenericParameterClass(Field field) {
        Type genericType = field.getGenericType();
        return (Class) ((ParameterizedType) genericType).getActualTypeArguments()[0];
    }

    private static boolean isParametrizedList(Field field) {
        return isList(field) && hasGenericParameter(field);
    }

    private static boolean isList(Field field) {
        return List.class.isAssignableFrom(field.getType());
    }

    private static boolean hasGenericParameter(Field field) {
        return field.getGenericType() instanceof ParameterizedType;
    }
}
