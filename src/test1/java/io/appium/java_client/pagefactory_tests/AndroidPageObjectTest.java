/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.java_client.pagefactory_tests;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchableElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.SelendroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.pagefactory.iOSFindBys;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AndroidPageObjectTest {

	private static WebDriver driver;
    private static AppiumDriverLocalService service;
    private boolean populated = false;

	@FindBy(className = "android.widget.TextView")
	private List<WebElement> textVieWs;

	@AndroidFindBy(className = "android.widget.TextView")
	private List<WebElement> androidTextViews;

	@iOSFindBy(uiAutomator = ".elements()[0]")
	private List<WebElement> iosTextViews;

	@iOSFindBy(uiAutomator = ".elements()[0]")
	@AndroidFindBy(className = "android.widget.TextView")
	private List<WebElement> androidOriOsTextViews;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
	private List<WebElement> androidUIAutomatorViews;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
	private List<MobileElement> mobileElementViews;

	@FindBy(className = "android.widget.TextView")
	private List<MobileElement> mobiletextVieWs;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
	private List<RemoteWebElement> remoteElementViews;

	@AndroidFindBys({
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")"),
		@AndroidFindBy(className = "android.widget.TextView")
		})
	private List<WebElement> chainElementViews;

	@iOSFindBys({@iOSFindBy(uiAutomator = ".elements()[0]"),
		@iOSFindBy(xpath = "//someElement")})
	private List<WebElement> iosChainTextViews;

	@AndroidFindBys({
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/content\")"),
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")"),
		@AndroidFindBy(id = "android:id/text1")
		})
	@iOSFindBys({@iOSFindBy(uiAutomator = ".elements()[0]"),
		@iOSFindBy(xpath = "//someElement")})
	private List<WebElement> chainAndroidOrIOSUIAutomatorViews;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
    private List<TouchableElement> touchabletextVieWs;

	@FindBy(id = "android:id/text1")
	private WebElement textView;

	@AndroidFindBy(className = "android.widget.TextView")
	private WebElement androidTextView;

	@iOSFindBy(uiAutomator = ".elements()[0]")
	private WebElement iosTextView;

	@AndroidFindBy(className = "android.widget.TextView")
	@iOSFindBy(uiAutomator = ".elements()[0]")
	private WebElement androidOriOsTextView;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
	private WebElement androidUIAutomatorView;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
	private MobileElement mobileElementView;

	@FindBy(className = "android.widget.TextView")
	private MobileElement mobiletextVieW;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
	private RemoteWebElement remotetextVieW;

	@AndroidFindBys({
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")"),
		@AndroidFindBy(className = "android.widget.TextView")
		})
	private WebElement chainElementView;

	@iOSFindBys({@iOSFindBy(uiAutomator = ".elements()[0]"),
		@iOSFindBy(xpath = "//someElement")})
	private WebElement iosChainTextView;

	@AndroidFindBys({
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/content\")"),
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")"),
		@AndroidFindBy(id = "android:id/text1")
		})
	@iOSFindBys({@iOSFindBy(uiAutomator = ".elements()[0]"),
		@iOSFindBy(xpath = "//someElement")})
	private WebElement chainAndroidOrIOSUIAutomatorView;
	
	@AndroidFindBys({
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/content\")"),
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")"),
		@AndroidFindBy(id = "android:id/text1")
		})
	private AndroidElement androidElementView;
	
	@AndroidFindBys({
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/content\")"),
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")"),
		@AndroidFindBy(id = "android:id/text1")
		})
	private List<AndroidElement> androidElementViews;
	
	@AndroidFindAll({
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/Fakecontent\")"),
		@AndroidFindBy(id = "android:id/Faketext1"),
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")"), //by this locator element is found
		@AndroidFindBy(id = "android:id/FakeId")
		})
	private List<WebElement> findAllElementViews;
	
	@AndroidFindAll({
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/Fakecontent\")"),
		@AndroidFindBy(id = "android:id/Faketext1"),
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")"), //by this locator element is found
		@AndroidFindBy(id = "android:id/FakeId")
		})
	private WebElement findAllElementView;
        
        @AndroidFindBy(id = "android:id/text1")
        @SelendroidFindBy(id = "Invalid Identifier")
        private WebElement textAndroidId;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
    private TouchableElement touchabletextVieW;

    @iOSFindBy(uiAutomator = ".elements()[0]")
    @FindBy(css = "e.e1.e2")
    private List<WebElement> elementsWhenAndroidLocatorIsNotDefinedAndThereIsInvalidFindBy;

    @iOSFindBy(uiAutomator = ".elements()[0]")
    @FindBy(css = "e.e1.e2")
    private WebElement elementWhenAndroidLocatorIsNotDefinedAndThereIsInvalidFindBy;
	
	@SuppressWarnings("rawtypes")
	@BeforeClass
	public static void beforeClass() throws Exception {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

	    File appDir = new File("src/test/java/io/appium/java_client");
	    File app = new File(appDir, "ApiDemos-debug.apk");
	    DesiredCapabilities capabilities = new DesiredCapabilities();
	    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
	    capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
	    driver = new AndroidDriver(service.getUrl(), capabilities);
	}

    @Before
    public void setUp() throws Exception {
        if (!populated)
            //This time out is set because test can be run on slow Android SDK emulator
            PageFactory.initElements(new AppiumFieldDecorator(driver, 5, TimeUnit.SECONDS), this);

        populated = true;
    }

	@AfterClass
	public static void afterClass() throws Exception {
        if (driver != null)
		driver.quit();

        if (service != null)
            service.stop();
	}

	@Test
	public void findByElementsTest() {
		Assert.assertNotEquals(0, textVieWs.size());
	}

	@Test
	public void findByElementTest() {
		Assert.assertNotEquals(null, textView.getAttribute("text"));
	}


	@Test
	public void androidFindByElementsTest(){
		Assert.assertNotEquals(0, androidTextViews.size());
	}

	@Test
	public void androidFindByElementTest(){
		Assert.assertNotEquals(null, androidTextView.getAttribute("text"));
	}

	@Test
	public void checkThatElementsWereNotFoundByIOSUIAutomator(){
		Assert.assertEquals(0, iosTextViews.size());
	}

	@Test
	public void checkThatElementWasNotFoundByIOSUIAutomator(){
		NoSuchElementException nsee = null;
		try{
			iosTextView.getAttribute("text");
		}
		catch (Exception e){
			nsee = (NoSuchElementException) e;
		}
		Assert.assertNotNull(nsee);
	}

	@Test
	public void androidOrIOSFindByElementsTest(){
		Assert.assertNotEquals(0, androidOriOsTextViews.size());
	}

	@Test
	public void androidOrIOSFindByElementTest(){
		Assert.assertNotEquals(null, androidOriOsTextView.getAttribute("text"));
	}

	@Test
	public void androidFindByUIAutomatorElementsTest(){
		Assert.assertNotEquals(0, androidUIAutomatorViews.size());
	}

	@Test
	public void androidFindByUIAutomatorElementTest(){
		Assert.assertNotEquals(null, androidUIAutomatorView.getAttribute("text"));
	}

	@Test
	public void areMobileElementsTest(){
		Assert.assertNotEquals(0, mobileElementViews.size());
	}

	@Test
	public void isMobileElementTest(){
		Assert.assertNotEquals(null, mobileElementView.getAttribute("text"));
	}

	@Test
	public void areMobileElements_FindByTest(){
		Assert.assertNotEquals(0, mobiletextVieWs.size());
	}

	@Test
	public void isMobileElement_FindByTest(){
		Assert.assertNotEquals(null, mobiletextVieW.getAttribute("text"));
	}

	@Test
	public void areRemoteElementsTest(){
		Assert.assertNotEquals(0, remoteElementViews.size());
	}

	@Test
	public void isRemoteElementTest(){
		Assert.assertNotEquals(null, remotetextVieW.getAttribute("text"));
	}

	@Test
	public void androidChainSearchElementsTest(){
		Assert.assertNotEquals(0, chainElementViews.size());
	}

	@Test
	public void androidChainSearchElementTest(){
		Assert.assertNotEquals(null, chainElementView.getAttribute("text"));
	}

	@Test
	public void checkThatElementsWereNotFoundByIOSUIAutomator_Chain(){
		Assert.assertEquals(0, iosChainTextViews.size());
	}

	@Test
	public void checkThatElementWasNotFoundByIOSUIAutomator_Chain(){
		NoSuchElementException nsee = null;
		try{
			iosChainTextView.getAttribute("text");
		}
		catch (Exception e){
			nsee = (NoSuchElementException) e;
		}
		Assert.assertNotNull(nsee);
	}

	@Test
	public void androidOrIOSFindByElementsTest_ChainSearches(){
		Assert.assertNotEquals(0, chainAndroidOrIOSUIAutomatorViews.size());
	}

	@Test
	public void androidOrIOSFindByElementTest_ChainSearches(){
		Assert.assertNotEquals(null, chainAndroidOrIOSUIAutomatorView.getAttribute("text"));
	}	
	
	@Test
	public void isAndroidElementTest(){
		Assert.assertNotEquals(null, androidElementView.getAttribute("text"));
	}	
	
	@Test
	public void areAndroidElementsTest(){
		Assert.assertNotEquals(0, androidElementViews.size());
	}		
	
	@Test
	public void findAllElementTest(){
		Assert.assertNotEquals(null, findAllElementView.getAttribute("text"));
	}	
	
	@Test
	public void findAllElementsTest(){
		Assert.assertNotEquals(0, findAllElementViews.size());
	}	
        
	@Test
	public void findByAndroidAnnotationOnlyTest(){
		Assert.assertNotEquals(null, textAndroidId.getAttribute("text"));
	}

    @Test
    public void isTouchableElement(){
        Assert.assertNotEquals(null, touchabletextVieW.getAttribute("text"));
    }

    @Test
    public void areTouchableElements(){
        Assert.assertNotEquals(0, touchabletextVieWs.size());
    }

    @Test
    public void isTheFieldAndroidElement(){
        @SuppressWarnings("unused")
		AndroidElement androidElement = (AndroidElement) mobiletextVieW; //declared as MobileElement
        androidElement = (AndroidElement) androidTextView; //declared as WedElement
        androidElement = (AndroidElement) remotetextVieW;  //declared as RemoteWedElement
        androidElement = (AndroidElement) touchabletextVieW; //declared as TouchABLEElement
    }

    @Test
    public void checkThatTestWillNotBeFailedBecauseOfInvalidFindBy(){
        try {
            Assert.assertNotEquals(null, elementWhenAndroidLocatorIsNotDefinedAndThereIsInvalidFindBy.getAttribute("text"));
        }
        catch (NoSuchElementException ignored){
            return;
        }
        throw new RuntimeException(NoSuchElementException.class.getName() + " has been expected.");
    }

    @Test
    public void checkThatTestWillNotBeFailedBecauseOfInvalidFindBy_List(){
        Assert.assertEquals(0, elementsWhenAndroidLocatorIsNotDefinedAndThereIsInvalidFindBy.size());
    }
}
