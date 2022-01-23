package org.unibayreuth.gnumaexperiments.handlers.messagehandling;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.unibayreuth.gnumaexperiments.logging.GnumaLogger.*;

@Component
public class MessageHandlerRepository {
    private final Logger log = LoggerFactory.getLogger(MessageHandlerRepository.class);

    private final Map<String, MessageHandler> handlerMap;

    @Autowired
    public MessageHandlerRepository(List<MessageHandler> messageHandlers) {
        handlerMap = messageHandlers.stream()
                .collect(Collectors.toMap(MessageHandler::getType, value -> value));
    }

    public void handle(String type, Message message) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(type), "Event type cannot be empty");
        Preconditions.checkNotNull(message, "Message cannot be null");
        log(log::info, String.format("Started processing of the message of type {%s}", type));

        if (!handlerMap.containsKey(type)) {
            String errorMessage = String.format("No handler exists for the event of type {%s}", type);
            throw new IllegalArgumentException(errorMessage);
        }
        handlerMap.get(type).handle(message);
    }
}
