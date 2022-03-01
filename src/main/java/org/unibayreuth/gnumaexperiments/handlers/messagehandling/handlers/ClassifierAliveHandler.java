package org.unibayreuth.gnumaexperiments.handlers.messagehandling.handlers;

import com.google.gson.Gson;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.unibayreuth.gnumaexperiments.commands.classifiers.UpdateClassifierCommand;
import org.unibayreuth.gnumaexperiments.dto.ClassifierDTO;
import org.unibayreuth.gnumaexperiments.handlers.messagehandling.MessageHandler;
import org.unibayreuth.gnumaexperiments.queries.classifiers.RetrieveAddressClassifiersQuery;
import org.unibayreuth.gnumaexperiments.views.ClassifierView;

import static org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf;
import static org.unibayreuth.gnumaexperiments.logging.GnumaLogger.log;

@Component
public class ClassifierAliveHandler implements MessageHandler {
    private final Logger log = LoggerFactory.getLogger(ClassifierAliveHandler.class);

    @Autowired
    private QueryGateway queryGateway;
    @Autowired
    private CommandGateway commandGateway;

    @Override
    public String getType() {
        return "ClassifierAlive";
    }

    /**
     * Handle the message communicating that certain classifier is still online
     * @param message - message from the classifier
     */
    @Override
    public void handle(Message message) {
        String messageBody = new String(message.getBody());
        log(log::info, String.format("Started handling a classifier alive message, message body:\n%s", messageBody));
        ClassifierDTO updatedClassifier = new Gson().fromJson(messageBody, ClassifierDTO.class);

        log(log::debug, String.format("Looking for a classifier with address %s", updatedClassifier.getAddress()));
        ClassifierView existingClassifier = queryGateway.query(new RetrieveAddressClassifiersQuery(updatedClassifier.getAddress()),
                instanceOf(ClassifierView.class)).join();

        if (existingClassifier == null) {
            log(log::error, String.format("No classifier found with address %s", updatedClassifier.getAddress()));
            return;
        }

        commandGateway.send(new UpdateClassifierCommand(existingClassifier.getId(), existingClassifier.getAddress(), existingClassifier.getHyperParameters()));
        log(log::info, String.format("Classifier %s at %s successfully updated", existingClassifier.getId(), existingClassifier.getAddress()));
    }
}
