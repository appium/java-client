package io.appium.java_client.service.local;

public class InvalidNodeJSInstance extends RuntimeException{
    public InvalidNodeJSInstance(String message){
        super(message);
    }

    public InvalidNodeJSInstance(String message, Throwable t){
        super(message, t);
    }
}
