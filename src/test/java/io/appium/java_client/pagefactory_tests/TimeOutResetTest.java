package io.appium.java_client.pagefactory_tests;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TimeOutResetTest {
	private WebDriver driver;
	private final static long ACCEPTABLE_DELTA_MILLS = 500;


	@FindAll({@FindBy(className = "ClassWhichDoesNotExist"),
	@FindBy(className = "OneAnotherClassWhichDoesNotExist")})
	private List<WebElement> stubElements;
	private AppiumFieldDecorator afd;

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		afd = new AppiumFieldDecorator(driver);

		PageFactory.initElements(afd, this);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	private static void checkTimeDifference(long etalonTime,
			TimeUnit etalonTimeUnit, long currentMillis) {
		long etalonMillis = TimeUnit.MILLISECONDS.convert(etalonTime,
				etalonTimeUnit);
		try{
			Assert.assertEquals(true,
					((currentMillis - etalonMillis) < ACCEPTABLE_DELTA_MILLS)
							&& ((currentMillis - etalonMillis) >= 0));
		}
		catch (Error e){
			String message = String.valueOf(etalonTime) + " "  + etalonTimeUnit.toString() + " current duration in millis " +
					String.valueOf(currentMillis) + " Failed";
			throw new RuntimeException(message, e);
		}
	}

	private long getBenchMark() {
		long startMark = Calendar.getInstance().getTimeInMillis();
		stubElements.size();
		long endMark = Calendar.getInstance().getTimeInMillis();
		return endMark - startMark;
	}

	@Test
	public void test() {
		checkTimeDifference(AppiumFieldDecorator.DEFAULT_IMPLICITLY_WAIT_TIMEOUT, AppiumFieldDecorator.DEFAULT_TIMEUNIT,
				getBenchMark());
		System.out.println(String.valueOf(AppiumFieldDecorator.DEFAULT_IMPLICITLY_WAIT_TIMEOUT)
				+ " " + AppiumFieldDecorator.DEFAULT_TIMEUNIT.toString() + ": Fine");

		afd.resetImplicitlyWaitTimeOut(15500000, TimeUnit.MICROSECONDS);
		checkTimeDifference(15500000, TimeUnit.MICROSECONDS, getBenchMark());
		System.out.println("Change time: " + String.valueOf(15500000) + " "
				+ TimeUnit.MICROSECONDS.toString() + ": Fine");

		afd.resetImplicitlyWaitTimeOut(3, TimeUnit.SECONDS);
		checkTimeDifference(3, TimeUnit.SECONDS, getBenchMark());
		System.out.println("Change time: " + String.valueOf(3) + " "
				+ TimeUnit.SECONDS.toString() + ": Fine");

	}

}
