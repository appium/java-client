package io.appium.java_client;

import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.remote.RemoteWebElement;

public class ComplexFind {

  private AppiumDriver driver;
  private static final HttpClient client = HttpClients.createDefault();
  private static final JsonParser parser = new JsonParser();

  public ComplexFind(AppiumDriver driver) {
    this.driver = driver;
  }

  /**
   * Create a new remote web element.
   */
  private MobileElement newElement(final String elementId) {
    final MobileElement element = new MobileElement(new RemoteWebElement(), driver);
    element.setParent(driver);
    element.setId(elementId);
    element.setFileDetector(driver.getFileDetector());
    return element;
  }

  /*
  This is a port of the following Ruby code from github.com/appium/ruby_lib
  The Selenium Java bindings make it impossible to post JSON so we're using
  HttpPost directly.

  # Scroll to an element containing target text or description.
  # @param text [String] the text to search for in the text value and content description
  # @return [Element] the element scrolled to
  def scroll_to text
    args = 'scroll',
        # textContains(text)
        [ [3, text] ],
        # descriptionContains(text)
        [ [7, text] ]

    mobile :find, args
  end
  */
  protected MobileElement scrollTo(String text) {
    text = text.replaceAll("\"", "\\\""); // quotes must be escaped.
    //                      ["scroll",  [[3,"animation"]],     [[7,"animation"]]]
    final String complex = "[\"scroll\",[[3,\"" + text + "\"]],[[7,\"" + text + "\"]]]";
    return execute(complex);
  }

  protected MobileElement scrollToExact(String text) {
    text = text.replaceAll("\"", "\\\""); // quotes must be escaped.
    //                      ["scroll",  [[1,"Animation"]],     [[5,"Animation"]]]
    final String complex = "[\"scroll\",[[1,\"" + text + "\"]],[[5,\"" + text + "\"]]]";
    return execute(complex);
  }

  protected MobileElement execute(String complex) {
    MobileElement element = null;
    try {
      final String id = driver.getSessionId().toString();
      final String executeURL = driver.getRemoteAddress() + "/session/" + id + "/appium/app/complex_find";

      final HttpPost post = new HttpPost(executeURL);
      post.setEntity(new StringEntity(complex, "UTF8"));
      post.setHeader("Content-type", "application/json");

      final HttpEntity responseEntity = client.execute(post).getEntity();

      if (responseEntity != null) {
        try {
          final String responseString = EntityUtils.toString(responseEntity);
          // {"status":0,"value":{"ELEMENT":"1"},"sessionId":"8e982755-980f-4036-b3d1-c0e14e890273"}
          final String elementId = parser.parse(responseString)
              .getAsJsonObject().get("value").getAsJsonObject().get("ELEMENT")
              .getAsString();

          element = newElement(elementId);
        } catch (final Exception e) {
          e.printStackTrace();
        } finally {
          EntityUtils.consume(responseEntity);
        }
      }
    } catch (final Exception e) {
      e.printStackTrace();
    }
    return element;
  }
}