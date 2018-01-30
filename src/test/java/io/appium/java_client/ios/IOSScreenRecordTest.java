package io.appium.java_client.ios;

import org.junit.Test;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;

public class IOSScreenRecordTest extends AppIOSTest {

    @Test
    public void verifyBasicScreenRecordingWorks() throws InterruptedException {
        driver.startRecordingScreen(
                new IOSStartScreenRecordingOptions()
                        .withTimeLimit(Duration.ofSeconds(10))
        );
        Thread.sleep(5000);
        String result = driver.stopRecordingScreen();
        assertThat(result, not(isEmptyString()));
    }

}
