package org.unibayreuth.gnumaexperiments.handlers.messagehandling;

import org.springframework.amqp.core.Message;

public interface MessageHandler {
    String getType();
    void handle(Message message);
}
