package io.appium.java_client.ws;

import java.util.List;

/**
 * This interface might be assigned to classes, which
 * are defined as web socket clients.
 */
public interface CanHandleMessages<T extends MessagesHandler> {
    /**
     * @return The list of web socket message handlers.
     */
    List<T> getMessageHandlers();

    /**
     * Register a new message handler.
     *
     * @param msgHandler an instance of a class, which
     *                   implements MessagesHandler interface
     */
    default void addMessageHandler(T msgHandler) {
        getMessageHandlers().add(msgHandler);
    }

    /**
     * Removes an existing message handler.
     *
     * @param msgHandler an instance of a class, which
     *                   implements MessagesHandler interface
     * @return true if the given class instance was registered before and has been successfully removed.
     */
    default boolean removeMessageHandler(T msgHandler) {
        return getMessageHandlers().remove(msgHandler);
    }

    /**
     * @return The count of registered message handlers.
     */
    default int messageHandlersCount() {
        return getMessageHandlers().size();
    }

    /**
     * Removes all registered message handlers.
     */
    default void removeAllMessageHandlers() {
        getMessageHandlers().clear();
    }
}
