package io.appium.java_client.ios;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.Test;

import java.time.Duration;

public class IOSScreenRecordTest extends AppIOSTest {

    @Test
    public void verifyBasicScreenRecordingWorks() throws InterruptedException {
        driver.startRecordingScreen(
                new IOSStartScreenRecordingOptions()
                        .withTimeLimit(Duration.ofSeconds(10))
        );
        Thread.sleep(5000);
        String result = driver.stopRecordingScreen();
        assertThat(result, is(not(emptyString())));
    }

}
