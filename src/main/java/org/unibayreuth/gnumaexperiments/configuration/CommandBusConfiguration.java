package org.unibayreuth.gnumaexperiments.configuration;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.unibayreuth.gnumaexperiments.handlers.exceptionhandling.ExceptionWrappingHandlerInterceptor;

@Configuration
public class CommandBusConfiguration {
    @Bean
    public CommandBus configureCommandBus() {
        CommandBus commandBus = SimpleCommandBus.builder().build();
        commandBus.registerHandlerInterceptor(new ExceptionWrappingHandlerInterceptor());
        return commandBus;
    }
}
