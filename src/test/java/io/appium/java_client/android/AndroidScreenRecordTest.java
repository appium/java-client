package io.appium.java_client.android;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriverException;

import java.time.Duration;

public class AndroidScreenRecordTest extends BaseAndroidTest {

    @BeforeEach
    public void setUp() {
        Activity activity = new Activity("io.appium.android.apis", ".ApiDemos");
        driver.startActivity(activity);
    }

    @Test
    public void verifyBasicScreenRecordingWorks() throws InterruptedException {
        try {
            driver.startRecordingScreen(
                    new AndroidStartScreenRecordingOptions()
                            .withTimeLimit(Duration.ofSeconds(5))
            );
        } catch (WebDriverException e) {
            if (e.getMessage().toLowerCase().contains("emulator")) {
                // screen recording only works on real devices
                return;
            }
        }
        Thread.sleep(5000);
        String result = driver.stopRecordingScreen();
        assertThat(result, is(not(emptyString())));
    }

}
