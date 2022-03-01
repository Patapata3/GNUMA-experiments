package org.unibayreuth.gnumaexperiments.handlers.messagehandling;

import org.springframework.amqp.core.Message;

/**
 * Interface which all of the classes handling messages should implement
 */
public interface MessageHandler {
    /**
     * Type of the message the implementing class can handle
     * @return - name of the event being handled (contained in the header of the messsage)
     */
    String getType();

    /**
     * Method incorporating handling logic
     * @param message - message received
     */
    void handle(Message message);
}
