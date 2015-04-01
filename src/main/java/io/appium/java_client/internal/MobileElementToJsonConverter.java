package io.appium.java_client.internal;

import io.appium.java_client.MobileElement;

import java.util.Collection;
import java.util.Map;

import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.internal.WebElementToJsonConverter;

import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Converts {@link RemoteWebElement} objects, which may be
 * {@link WrapsElement wrapped}, into their JSON representation as defined by
 * the WebDriver wire protocol. This class will recursively convert Lists and
 * Maps to catch nested references.
 *
 * @see <a href="http://code.google.com/p/selenium/wiki/JsonWireProtocol#WebElement_JSON_Object">
 *     WebDriver JSON Wire Protocol</a>
 */
public class MobileElementToJsonConverter extends WebElementToJsonConverter {

  public Object apply(Object arg) {
    if (arg == null || arg instanceof String || arg instanceof Boolean ||
        arg instanceof Number) {
      return arg;
    }

    while (arg instanceof WrapsElement) {
      arg = ((WrapsElement) arg).getWrappedElement();
    }

    if (arg instanceof MobileElement) {
      return ImmutableMap.of("ELEMENT", ((MobileElement<?>) arg).getId());
    }

    if (arg.getClass().isArray()) {
      arg = Lists.newArrayList((Object[]) arg);
    }

    if (arg instanceof Collection<?>) {
      Collection<?> args = (Collection<?>) arg;
      return Collections2.transform(args, this);
    }

    if (arg instanceof Map<?, ?>) {
      Map<?, ?> args = (Map<?, ?>) arg;
      Map<String, Object> converted = Maps.newHashMapWithExpectedSize(args.size());
      for (Map.Entry<?, ?> entry : args.entrySet()) {
        Object key = entry.getKey();
        if (!(key instanceof String)) {
          throw new IllegalArgumentException(
              "All keys in Map script arguments must be strings: " + key.getClass().getName());
        }
        converted.put((String) key, apply(entry.getValue()));
      }
      return converted;
    }

    throw new IllegalArgumentException("Argument is of an illegal type: "
        + arg.getClass().getName());
  }
}
