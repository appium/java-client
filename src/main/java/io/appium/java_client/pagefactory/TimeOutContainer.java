package io.appium.java_client.pagefactory;

import java.util.concurrent.TimeUnit;

/**
 * Instances of this class contain
 * implicit time outs which are used by {@link AppiumElementLocator} 
 */
class TimeOutContainer implements ResetsImplicitlyWaitTimeOut{
	private long timeOutValue;
	private TimeUnit timeUnit;
	
	TimeOutContainer(long initialTimeOutValue, TimeUnit initialTimeUnit){
		this.timeOutValue = initialTimeOutValue;
		this.timeUnit     = initialTimeUnit;
	}

	@Override
	public void resetImplicitlyWaitTimeOut(long timeOut, TimeUnit timeUnit) {
		this.timeOutValue = timeOut;
		this.timeUnit     = timeUnit;		
	}
	
	long getTimeValue(){
		return timeOutValue;
	}
	
	TimeUnit getTimeUnitValue(){
		return timeUnit;
	}

}
