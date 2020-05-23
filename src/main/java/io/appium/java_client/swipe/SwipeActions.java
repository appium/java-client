package io.appium.java_client.swipe;

import java.time.Duration;

import org.openqa.selenium.Dimension;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;


public class SwipeActions {
    AppiumDriver driver;
    Dimension dimension;
    public SwipeActions(AppiumDriver driver){
        this.driver = driver;
        this.dimension = this.driver.manage().window().getSize();
    }

    public void swipeTowards(SwipeOptions swipeOptions) throws InterruptedException {
        this.swipeTowards(swipeOptions, null);
    }
    
    public void swipeTowards(SwipeOptions swipeOptions, WaitOptions waitOptions) throws InterruptedException {
    	
    	if(waitOptions == null) {
    		waitOptions = WaitOptions.waitOptions(Duration.ofMillis(2000));
    	}
    	
        if(SwipeOptions.DOWN.equals(swipeOptions)) {
            this.swipeDown(waitOptions);
        }else if(SwipeOptions.UP.equals(swipeOptions)) {
            this.swipeUP(waitOptions);
        }else if(SwipeOptions.LEFT.equals(swipeOptions)) {
            this.swipeLeft(waitOptions);
        }else if(SwipeOptions.RIGHT.equals(swipeOptions)) {
            this.swipeRight(waitOptions);
        }else if(SwipeOptions.RIGHTMOST.equals(swipeOptions)) {
            this.swipeRightMost(waitOptions);
        }else if(SwipeOptions.LEFTMOST.equals(swipeOptions)) {
            this.swipeLeftMost(waitOptions);
        }else if(SwipeOptions.DOWNMOST.equals(swipeOptions)) {
            this.swipeDownMost(waitOptions);
        }else if(SwipeOptions.UPMOST.equals(swipeOptions)) {
            this.swipeUPMost(waitOptions);
        }
    }

    /** Right most swipe is use full for actions like opening hamburger menu.*/
    private void swipeRightMost(WaitOptions waitOptions) throws InterruptedException {
        int startX = 10;
        int startY = (int) (this.dimension.height / 2);
        int endX = (int) (this.dimension.width * 0.99);
        new TouchAction<>(this.driver).press(PointOption.point(startX, startY))
                .waitAction(waitOptions).moveTo(PointOption.point(endX, startY))
                .release().perform();
    }

    private void swipeLeftMost(WaitOptions waitOptions) {
        int startY = (int) (this.dimension.height / 2);
        int startX = (int) (this.dimension.width * 0.99);
        int endX = 10;
        new TouchAction<>(this.driver).press(PointOption.point(startX, startY))
                .waitAction(waitOptions).moveTo(PointOption.point(endX, startY))
                .release().perform();
    }

    private void swipeDownMost(WaitOptions waitOptions) throws InterruptedException {
        double endPercentage = 0.99;
        double anchorPercentage = 0.3;
        int anchor = (int) (this.dimension.width * anchorPercentage);
        int startPoint = 10;
        int endPoint = (int) (this.dimension.height * endPercentage);

        new TouchAction<>(this.driver).press(PointOption.point(anchor, startPoint))
                .waitAction(waitOptions)
                .moveTo(PointOption.point(anchor, endPoint)).release().perform();
    }

    private void swipeUPMost(WaitOptions waitOptions) throws InterruptedException {
        int startX = 10;
        int startY = (int) (this.dimension.height * 0.99);
        int anchor = (int) (this.dimension.width * 0.3);
        new TouchAction<>(this.driver).press(PointOption.point(anchor, startY))
                .waitAction(waitOptions)
                .moveTo(PointOption.point(anchor, startX)).release().perform();
    }

    private void swipeUP(WaitOptions waitOptions) throws InterruptedException {
        int startX = (int) (this.dimension.height * 0.10);
        int startY = (int) (this.dimension.height * 0.90);
        new TouchAction<>(this.driver).press(PointOption.point(this.dimension.width / 2, startY))
                .waitAction(waitOptions)
                .moveTo(PointOption.point(this.dimension.width / 2, startX)).release().perform();
    }

    private void swipeDown(WaitOptions waitOptions) throws InterruptedException {
        double startPercentage = 0.1;
        double endPercentage = 0.9;
        double anchorPercentage = 0.3;

        int anchor = (int) (this.dimension.width * anchorPercentage);
        int startPoint = (int) (this.dimension.height * startPercentage);
        int endPoint = (int) (this.dimension.height * endPercentage);

        new TouchAction<>(this.driver).press(PointOption.point(anchor, startPoint))
                .waitAction(waitOptions)
                .moveTo(PointOption.point(anchor, endPoint)).release().perform();
    }

    private void swipeRight(WaitOptions waitOptions) throws InterruptedException {
        int startY = (int) (this.dimension.height / 2);
        int startX = (int) (this.dimension.width * 0.10);
        int endX = (int) (dimension.width * 0.90);
        this.swipe(startX, endX, startY, waitOptions);
    }

    private void swipeLeft(WaitOptions waitOptions)  {
        int startY = (int) (this.dimension.height / 2);
        int startX = (int) (this.dimension.width * 0.90);
        int endX = (int) (this.dimension.width * 0.10);
        this.swipe(startX, endX, startY, waitOptions);
    }

    private void swipe(int startX, int endX, int startY, WaitOptions waitOptions){
        new TouchAction<>(this.driver)
                .press(PointOption.point(startX, startY))
                .waitAction(waitOptions)
                .moveTo(PointOption.point(endX, startY))
                .release()
                .perform();
    }
}
