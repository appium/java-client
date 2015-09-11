package io.appium.java_client.service.local;


public class InvalidServerInstanceException extends RuntimeException {

    private static String MESSAGE_PREFIX = "Invalid server instance exception has occured: ";

    public InvalidServerInstanceException(String messege, Throwable t){
        super(MESSAGE_PREFIX + messege, t);
    }

    public InvalidServerInstanceException(String messege){
        super(MESSAGE_PREFIX + messege);
    }
}
