package io.appium.java_client.pagefactory_tests.blocks;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchableElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.*;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

@FindBy(id = "android:id/decor_content_parent")
public class AndroidPageBlock extends AndroidElement {
	@FindBy(className = "android.widget.TextView")
	public List<WebElement> textVieWs;

	@AndroidFindBy(className = "android.widget.TextView")
	public List<WebElement> androidTextViews;

	@iOSFindBy(uiAutomator = ".elements()[0]")
	public List<WebElement> iosTextViews;

	@iOSFindBy(uiAutomator = ".elements()[0]")
	@AndroidFindBy(className = "android.widget.TextView")
	public List<WebElement> androidOriOsTextViews;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
	public List<WebElement> androidUIAutomatorViews;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
	public List<MobileElement> mobileElementViews;

	@FindBy(className = "android.widget.TextView")
	public List<MobileElement> mobiletextVieWs;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
	public List<RemoteWebElement> remoteElementViews;

	@AndroidFindBys({
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")"),
		@AndroidFindBy(className = "android.widget.TextView")
		})
	public List<WebElement> chainElementViews;

	@iOSFindBys({@iOSFindBy(uiAutomator = ".elements()[0]"),
		@iOSFindBy(xpath = "//someElement")})
	public List<WebElement> iosChainTextViews;

	@AndroidFindBys({
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/content\")"),
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")"),
		@AndroidFindBy(id = "android:id/text1")
		})
	@iOSFindBys({@iOSFindBy(uiAutomator = ".elements()[0]"),
		@iOSFindBy(xpath = "//someElement")})
	public List<WebElement> chainAndroidOrIOSUIAutomatorViews;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
    public List<TouchableElement> touchabletextVieWs;

	@FindBy(id = "android:id/text1")
	public WebElement textView;

	@AndroidFindBy(className = "android.widget.TextView")
	public WebElement androidTextView;

	@iOSFindBy(uiAutomator = ".elements()[0]")
	public WebElement iosTextView;

	@AndroidFindBy(className = "android.widget.TextView")
	@iOSFindBy(uiAutomator = ".elements()[0]")
	public WebElement androidOriOsTextView;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
	public WebElement androidUIAutomatorView;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
	public MobileElement mobileElementView;

	@FindBy(className = "android.widget.TextView")
	public MobileElement mobiletextVieW;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
	public RemoteWebElement remotetextVieW;

	@AndroidFindBys({
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")"),
		@AndroidFindBy(className = "android.widget.TextView")
		})
	public WebElement chainElementView;

	@iOSFindBys({@iOSFindBy(uiAutomator = ".elements()[0]"),
		@iOSFindBy(xpath = "//someElement")})
	public WebElement iosChainTextView;

	@AndroidFindBys({
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/content\")"),
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")"),
		@AndroidFindBy(id = "android:id/text1")
		})
	@iOSFindBys({@iOSFindBy(uiAutomator = ".elements()[0]"),
		@iOSFindBy(xpath = "//someElement")})
	public WebElement chainAndroidOrIOSUIAutomatorView;
	
	@AndroidFindBys({
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/content\")"),
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")"),
		@AndroidFindBy(id = "android:id/text1")
		})
	public AndroidElement androidElementView;
	
	@AndroidFindBys({
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/content\")"),
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")"),
		@AndroidFindBy(id = "android:id/text1")
		})
	public List<AndroidElement> androidElementViews;
	
	@AndroidFindAll({
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/Fakecontent\")"),
		@AndroidFindBy(id = "android:id/Faketext1"),
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")"), //by this locator element is found
		@AndroidFindBy(id = "android:id/FakeId")
		})
	public List<WebElement> findAllElementViews;
	
	@AndroidFindAll({
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/Fakecontent\")"),
		@AndroidFindBy(id = "android:id/Faketext1"),
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")"), //by this locator element is found
		@AndroidFindBy(id = "android:id/FakeId")
		})
	public WebElement findAllElementView;
        
        @AndroidFindBy(id = "android:id/text1")
        @SelendroidFindBy(id = "Invalid Identifier")
        public WebElement textAndroidId;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
    public TouchableElement touchabletextVieW;

    @iOSFindBy(uiAutomator = ".elements()[0]")
    @FindBy(css = "e.e1.e2")
    public List<WebElement> elementsWhenAndroidLocatorIsNotDefinedAndThereIsInvalidFindBy;

    @iOSFindBy(uiAutomator = ".elements()[0]")
    @FindBy(css = "e.e1.e2")
    public WebElement elementWhenAndroidLocatorIsNotDefinedAndThereIsInvalidFindBy;
}
