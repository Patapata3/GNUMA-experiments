package org.unibayreuth.gnumaexperiments.handlers.messagehandling;

import com.rabbitmq.client.Channel;
import org.axonframework.extensions.amqp.eventhandling.AMQPMessageConverter;
import org.axonframework.extensions.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.unibayreuth.gnumaexperiments.logging.GnumaLogger.*;

@Component("experimentMessageSource")
public class ExperimentMessageSource extends SpringAMQPMessageSource {
    private final Logger log = LoggerFactory.getLogger(ExperimentMessageSource.class);
    private static final String HEADER_KEY = "event";

    private MessageHandlerRepository messageHandlerRepository;

    @Autowired
    public ExperimentMessageSource(AMQPMessageConverter messageConverter, MessageHandlerRepository messageHandlerRepository) {
        super(messageConverter);
        this.messageHandlerRepository = messageHandlerRepository;
    }

    @RabbitListener(queues = "exp.queue.test")
    @Override
    public void onMessage(Message message, Channel channel) {
        if (!message.getMessageProperties().getHeaders().containsKey(HEADER_KEY) ||
                Objects.isNull(message.getMessageProperties().getHeader(HEADER_KEY))) {
            String errorMessage = String.format("Message must contain a non-empty header with the key \"%s\"", HEADER_KEY);
            log(log::error, errorMessage);
            return;
        }

        String messageType = message.getMessageProperties().getHeader(HEADER_KEY);
        log(log::info, String.format("Received message of type {%s}, starting handling", messageType));
        try {
            messageHandlerRepository.getHandler(messageType.trim()).handle(message);
        } catch (Exception e) {
            log(log::error, e.getMessage(), e);
        }
    }
}
