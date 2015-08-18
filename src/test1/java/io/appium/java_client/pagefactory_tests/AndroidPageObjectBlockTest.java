package io.appium.java_client.pagefactory_tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.*;
import io.appium.java_client.pagefactory_tests.blocks.AndroidPageBlock;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

public class AndroidPageObjectBlockTest {

	private WebDriver driver;

	AndroidPageBlock content;
	
	@SuppressWarnings("rawtypes")
	@Before
	public void setUp() throws Exception {
	    File appDir = new File("src/test/java/io/appium/java_client");
	    File app = new File(appDir, "ApiDemos-debug.apk");
	    DesiredCapabilities capabilities = new DesiredCapabilities();
	    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
	    capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
	    driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

	    //This time out is set because test can be run on slow Android SDK emulator
		PageFactory.initElements(new AppiumFieldDecorator(driver, 5, TimeUnit.SECONDS), this);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void findByElementsTest() {
		Assert.assertNotEquals(0, content.textVieWs.size());
	}

	@Test
	public void findByElementTest() {
		Assert.assertNotEquals(null, content.textView.getAttribute("text"));
	}


	@Test
	public void androidFindByElementsTest(){
		Assert.assertNotEquals(0, content.androidTextViews.size());
	}

	@Test
	public void androidFindByElementTest(){
		Assert.assertNotEquals(null, content.androidTextView.getAttribute("text"));
	}

	@Test
	public void checkThatElementsWereNotFoundByIOSUIAutomator(){
		Assert.assertEquals(0, content.iosTextViews.size());
	}

	@Test
	public void checkThatElementWasNotFoundByIOSUIAutomator(){
		NoSuchElementException nsee = null;
		try{
			content.iosTextView.getAttribute("text");
		}
		catch (Exception e){
			nsee = (NoSuchElementException) e;
		}
		assertNotNull(nsee);
	}

	@Test
	public void androidOrIOSFindByElementsTest(){
		Assert.assertNotEquals(0, content.androidOriOsTextViews.size());
	}

	@Test
	public void androidOrIOSFindByElementTest(){
		Assert.assertNotEquals(null, content.androidOriOsTextView.getAttribute("text"));
	}

	@Test
	public void androidFindByUIAutomatorElementsTest(){
		Assert.assertNotEquals(0, content.androidUIAutomatorViews.size());
	}

	@Test
	public void androidFindByUIAutomatorElementTest(){
		Assert.assertNotEquals(null, content.androidUIAutomatorView.getAttribute("text"));
	}

	@Test
	public void areMobileElementsTest(){
		Assert.assertNotEquals(0, content.mobileElementViews.size());
	}

	@Test
	public void isMobileElementTest(){
		Assert.assertNotEquals(null, content.mobileElementView.getAttribute("text"));
	}

	@Test
	public void areMobileElements_FindByTest(){
		Assert.assertNotEquals(0, content.mobiletextVieWs.size());
	}

	@Test
	public void isMobileElement_FindByTest(){
		Assert.assertNotEquals(null, content.mobiletextVieW.getAttribute("text"));
	}

	@Test
	public void areRemoteElementsTest(){
		Assert.assertNotEquals(0, content.remoteElementViews.size());
	}

	@Test
	public void isRemoteElementTest(){
		Assert.assertNotEquals(null, content.remotetextVieW.getAttribute("text"));
	}

	@Test
	public void androidChainSearchElementsTest(){
		Assert.assertNotEquals(0, content.chainElementViews.size());
	}

	@Test
	public void androidChainSearchElementTest(){
		Assert.assertNotEquals(null, content.chainElementView.getAttribute("text"));
	}

	@Test
	public void checkThatElementsWereNotFoundByIOSUIAutomator_Chain(){
		Assert.assertEquals(0, content.iosChainTextViews.size());
	}

	@Test
	public void checkThatElementWasNotFoundByIOSUIAutomator_Chain(){
		NoSuchElementException nsee = null;
		try{
			content.iosChainTextView.getAttribute("text");
		}
		catch (Exception e){
			nsee = (NoSuchElementException) e;
		}
		assertNotNull(nsee);
	}

	@Test
	public void androidOrIOSFindByElementsTest_ChainSearches(){
		Assert.assertNotEquals(0, content.chainAndroidOrIOSUIAutomatorViews.size());
	}

	@Test
	public void androidOrIOSFindByElementTest_ChainSearches(){
		Assert.assertNotEquals(null, content.chainAndroidOrIOSUIAutomatorView.getAttribute("text"));
	}	
	
	@Test
	public void isAndroidElementTest(){
		Assert.assertNotEquals(null, content.androidElementView.getAttribute("text"));
	}	
	
	@Test
	public void areAndroidElementsTest(){
		Assert.assertNotEquals(0, content.androidElementViews.size());
	}		
	
	@Test
	public void findAllElementTest(){
		Assert.assertNotEquals(null, content.findAllElementView.getAttribute("text"));
	}	
	
	@Test
	public void findAllElementsTest(){
		Assert.assertNotEquals(0, content.findAllElementViews.size());
	}	
        
	@Test
	public void findByAndroidAnnotationOnlyTest(){
		Assert.assertNotEquals(null, content.textAndroidId.getAttribute("text"));
	}

    @Test
    public void isTouchableElement(){
        Assert.assertNotEquals(null, content.touchabletextVieW.getAttribute("text"));
    }

    @Test
    public void areTouchableElements(){
        Assert.assertNotEquals(0, content.touchabletextVieWs.size());
    }

    @Test
    public void isTheFieldAndroidElement(){
        @SuppressWarnings("unused")
		AndroidElement androidElement = (AndroidElement) content.mobiletextVieW; //declared as MobileElement
        androidElement = (AndroidElement) content.androidTextView; //declared as WedElement
        androidElement = (AndroidElement) content.remotetextVieW;  //declared as RemoteWedElement
        androidElement = (AndroidElement) content.touchabletextVieW; //declared as TouchABLEElement
    }

    @Test
    public void checkThatTestWillBeFailedBecauseOfInvalidFindBy(){
		try {
			content.elementWhenAndroidLocatorIsNotDefinedAndThereIsInvalidFindBy.getAttribute("text");
		} catch (Throwable e) {
			while(e.getCause() != null) {
				e = e.getCause();
				if(e.getClass().isAssignableFrom(UnsupportedCommandException.class)) {
					break;
				}
			}
			assertNotNull(e);
		}
    }

	@Test
    public void checkThatTestWillBeFailedBecauseOfInvalidFindBy_List(){
		try {
			content.elementsWhenAndroidLocatorIsNotDefinedAndThereIsInvalidFindBy.size();
		} catch (Throwable e) {
			while(e.getCause() != null) {
				e = e.getCause();
				if(e.getClass().isAssignableFrom(UnsupportedCommandException.class)) {
					break;
				}
			}
			assertNotNull(e);
		}
    }
}
