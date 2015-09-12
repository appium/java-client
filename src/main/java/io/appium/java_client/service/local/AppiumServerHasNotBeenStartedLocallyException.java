package io.appium.java_client.service.local;


public class AppiumServerHasNotBeenStartedLocallyException extends RuntimeException{

    public AppiumServerHasNotBeenStartedLocallyException(String messege, Throwable t){
        super(messege, t);
    }

    public AppiumServerHasNotBeenStartedLocallyException(String messege){
        super(messege);
    }
}
