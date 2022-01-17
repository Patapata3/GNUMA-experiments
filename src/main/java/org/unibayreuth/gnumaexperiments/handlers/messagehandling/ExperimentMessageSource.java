package org.unibayreuth.gnumaexperiments.handlers.messagehandling;

import com.rabbitmq.client.Channel;
import org.axonframework.extensions.amqp.eventhandling.AMQPMessageConverter;
import org.axonframework.extensions.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.json.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("experimentMessageSource")
public class ExperimentMessageSource extends SpringAMQPMessageSource {
    private MessageHandlerRepository messageHandlerRepository;

    @Autowired
    public ExperimentMessageSource(AMQPMessageConverter messageConverter, MessageHandlerRepository messageHandlerRepository) {
        super(messageConverter);
        this.messageHandlerRepository = messageHandlerRepository;
    }

    @RabbitListener(queues = "exp.queue")
    @Override
    public void onMessage(Message message, Channel channel) {
        JSONObject jsonBody = new JSONObject(new String(message.getBody()));
        int i = 0;
    }
}
