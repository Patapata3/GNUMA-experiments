package org.unibayreuth.gnumaexperiments.handlers.messagehandling;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MessageHandlerRepository {

    private final Map<String, MessageHandler> handlerMap;

    /**
     * Constructor, collecting all the beans, implementing {@link MessageHandler}
     * @param messageHandlers - list of handlers from the application context
     */
    @Autowired
    public MessageHandlerRepository(List<MessageHandler> messageHandlers) {
        handlerMap = messageHandlers.stream()
                .collect(Collectors.toMap(MessageHandler::getType, value -> value));
    }

    public MessageHandler getHandler(String type) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(type), "Event type cannot be empty");
        if (!handlerMap.containsKey(type)) {
            String errorMessage = String.format("No handler exists for the event of type {%s}", type);
            throw new IllegalArgumentException(errorMessage);
        }
        return handlerMap.get(type);
    }
}
