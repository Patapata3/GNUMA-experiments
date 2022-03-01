package org.unibayreuth.gnumaexperiments.handlers.messagehandling.handlers;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.unibayreuth.gnumaexperiments.dataModel.enums.ExperimentStatus;
import org.unibayreuth.gnumaexperiments.dto.ClassifierInterruptDTO;
import org.unibayreuth.gnumaexperiments.handlers.messagehandling.MessageHandler;
import org.unibayreuth.gnumaexperiments.handlers.messagehandling.workers.ExperimentWorker;

import static org.unibayreuth.gnumaexperiments.logging.GnumaLogger.log;

@Component
public class ClassifierErrorHandler implements MessageHandler {
    private Logger log = LoggerFactory.getLogger(ClassifierErrorHandler.class);

    @Autowired
    private ExperimentWorker experimentWorker;

    @Override
    public String getType() {
        return "ClassifierError";
    }

    /**
     * Handling of the message with an error occured in the classifier
     * @param message - message from the classifier
     */
    @Override
    public void handle(Message message) {
        String messageBody = new String(message.getBody());
        log(log::info, String.format("Started handling of a classifier error message, message body:\n%s", messageBody));

        Gson gson = new Gson();
        ClassifierInterruptDTO classifierInterrupt = gson.fromJson(messageBody, ClassifierInterruptDTO.class);
        experimentWorker.updateExperimentStatus(classifierInterrupt.getAddress(), classifierInterrupt.getModelId(), ExperimentStatus.ERROR);
    }
}
