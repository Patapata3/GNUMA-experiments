package org.unibayreuth.gnumaexperiments.handlers.messagehandling.handlers;

import com.google.gson.Gson;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.unibayreuth.gnumaexperiments.commands.classifiers.CreateClassifierCommand;
import org.unibayreuth.gnumaexperiments.commands.classifiers.UpdateClassifierCommand;
import org.unibayreuth.gnumaexperiments.dataModel.entity.HyperParameter;
import org.unibayreuth.gnumaexperiments.dto.ClassifierDTO;
import org.unibayreuth.gnumaexperiments.handlers.messagehandling.MessageHandler;
import org.unibayreuth.gnumaexperiments.queries.classifiers.RetrieveAddressClassifiersQuery;
import org.unibayreuth.gnumaexperiments.service.DTOConverterService;
import org.unibayreuth.gnumaexperiments.views.ClassifierView;

import java.util.List;
import java.util.Objects;

import static org.unibayreuth.gnumaexperiments.logging.GnumaLogger.*;
import static org.axonframework.messaging.responsetypes.ResponseTypes.*;

@Component
public class ClassifierStartHandler implements MessageHandler {
    private final Logger log = LoggerFactory.getLogger(ClassifierStartHandler.class);
    private static final String TYPE = "ClassifierStart";

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private DTOConverterService dtoConverterService;
    @Autowired
    private QueryGateway queryGateway;

    @Override
    public String getType() {
        return TYPE;
    }

    /**
     * Handling of a classifier startup message
     * @param message - message from the classifier
     */
    @Override
    public void handle(Message message) {
        String messageBody = new String(message.getBody());
        log(log::info, String.format("Started handling of a Classifier startup message, message body:\n%s", messageBody));
        ClassifierDTO newClassifier = new Gson().fromJson(messageBody, ClassifierDTO.class);

        log(log::debug, "finding and removing all classifiers with the same address, but different id");
        ClassifierView existingClassifier = queryGateway.query(new RetrieveAddressClassifiersQuery(newClassifier.getAddress()),
                instanceOf(ClassifierView.class)).join();

        List<HyperParameter> newHyperParameters = dtoConverterService.createHyperParametersFromDTO(newClassifier.getHyperParameters());
        commandGateway.send(!Objects.isNull(existingClassifier) ?
                new UpdateClassifierCommand(newClassifier.getClassifierName(), existingClassifier.getAddress(), newHyperParameters) :
                new CreateClassifierCommand(newClassifier.getClassifierName(), newClassifier.getAddress(), newHyperParameters));
        log(log::info, String.format("Successfully saved classifier with id {%s} and address {%s}",
                newClassifier.getClassifierName(), newClassifier.getAddress()));
    }
}
