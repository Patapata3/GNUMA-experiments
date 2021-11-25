package org.unibayreuth.gnumaexperiments;

import com.mongodb.client.MongoClient;
import com.rabbitmq.client.Channel;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.extensions.amqp.eventhandling.AMQPMessageConverter;
import org.axonframework.extensions.amqp.eventhandling.RoutingKeyResolver;
import org.axonframework.extensions.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    @Value("${axon.amqp.exchange}")
    private String exchange;

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.topicExchange(exchange).build();
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.nonDurable("expQueue").build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with("experiments").noargs();
    }

    @Bean
    public SpringAMQPMessageSource myMessageSource(AMQPMessageConverter messageConverter) {
        return new SpringAMQPMessageSource(messageConverter) {
            @RabbitListener(queues = "expQueue")
            @Override
            public void onMessage(Message message, Channel channel) {
                super.onMessage(message, channel);
            }
        };
    }

    @Bean
    public EmbeddedEventStore eventStore(EventStorageEngine storageEngine, AxonConfiguration configuration) {
        return EmbeddedEventStore.builder()
                .storageEngine(storageEngine)
                .messageMonitor(configuration.messageMonitor(EventStore.class, "eventStore"))
                .build();
    }

    @Bean
    public EventStorageEngine storageEngine(MongoClient client) {
        return MongoEventStorageEngine.builder()
                .mongoTemplate(
                        DefaultMongoTemplate.builder()
                                .mongoDatabase(client)
                                .build())
                .build();
    }

    @Bean
    public RoutingKeyResolver routingKeyResolver() {
        return eventMessage -> "experiments";
    }
}
