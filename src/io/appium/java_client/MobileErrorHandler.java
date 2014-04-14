/*
 +Copyright 2014 Appium contributors
 +Copyright 2014 Software Freedom Conservancy
 +
 +Licensed under the Apache License, Version 2.0 (the "License");
 +you may not use this file except in compliance with the License.
 +You may obtain a copy of the License at
 +
 +     http://www.apache.org/licenses/LICENSE-2.0
 +
 +Unless required by applicable law or agreed to in writing, software
 +distributed under the License is distributed on an "AS IS" BASIS,
 +WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 +See the License for the specific language governing permissions and
 +limitations under the License.
 + */

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
