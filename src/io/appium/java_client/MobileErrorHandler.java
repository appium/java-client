package io.appium.java_client;

import org.openqa.selenium.WebDriverException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by jonahss on 4/8/14.
 */
public class MobileErrorHandler {

  private final ErrorCodesMobile errorCodes = new ErrorCodesMobile();

  public void throwIfMobileError(WebDriverException originalException) throws RuntimeException {

    Class<? extends WebDriverException> mobileErrorType =
            errorCodes.getExceptionType(originalException.getMessage());

    if (mobileErrorType == null) {
      throw originalException;
    }


    WebDriverException toThrow = null;

    if (toThrow == null) {
      toThrow = createThrowable(mobileErrorType,
              new Class<?>[]{String.class, Throwable.class},
              new Object[]{originalException.getMessage(), originalException.getCause()});
    }

    if (toThrow == null) {
      toThrow = createThrowable(mobileErrorType,
              new Class<?>[]{String.class},
              new Object[]{originalException.getMessage()});
    }

    if (toThrow == null) {
      toThrow = originalException;
    }

    throw toThrow;

  }

  private <T extends Throwable> T createThrowable(
          Class<T> clazz, Class<?>[] parameterTypes, Object[] parameters) {
    try {
      Constructor<T> constructor = clazz.getConstructor(parameterTypes);
      return constructor.newInstance(parameters);
    } catch (NoSuchMethodException e) {
      // Do nothing - fall through.
    } catch (InvocationTargetException e) {
      // Do nothing - fall through.
    } catch (InstantiationException e) {
      // Do nothing - fall through.
    } catch (IllegalAccessException e) {
      // Do nothing - fall through.
    } catch (OutOfMemoryError error) {
      // It can happen...
    }
    return null;
  }

}
