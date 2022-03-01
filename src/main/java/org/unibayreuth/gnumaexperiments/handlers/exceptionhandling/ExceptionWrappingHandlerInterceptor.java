package org.unibayreuth.gnumaexperiments.handlers.exceptionhandling;

import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.unibayreuth.gnumaexperiments.exceptions.ExperimentValidationException;
import org.unibayreuth.gnumaexperiments.exceptions.MissingEntityException;
import org.unibayreuth.gnumaexperiments.exceptions.ServiceRequestException;
import org.unibayreuth.gnumaexperiments.logging.GnumaLogger;

import java.util.Map;

/**
 * Command interceptor class for wrapping and handling exceptions
 */
public class ExceptionWrappingHandlerInterceptor implements MessageHandlerInterceptor<CommandMessage<?>> {
    private final Logger log = LoggerFactory.getLogger(ExceptionWrappingHandlerInterceptor.class);

    private final Map<String, ExperimentErrorCode> errorCodeMap = Map.of(
            MissingEntityException.class.getName(), ExperimentErrorCode.ENTITY_NOT_FOUND,
            ExperimentValidationException.class.getName(), ExperimentErrorCode.VALIDATION_ERROR,
            ServiceRequestException.class.getName(), ExperimentErrorCode.REQUEST_ERROR
    );

    /**
     * Wrap a command into an exception handler before it is passed for the execution
     * @param unitOfWork
     * @param interceptorChain
     * @return
     * @throws Exception
     */
    @Override
    public Object handle(UnitOfWork<? extends CommandMessage<?>> unitOfWork, InterceptorChain interceptorChain) throws Exception {
        try {
            return interceptorChain.proceed();
        } catch(Throwable e) {
            GnumaLogger.log(log::error, e.getMessage(), e);
            throw new CommandExecutionException("An exception has occured during command execution", e, getExceptionDetails(e));
        }
    }

    private ExperimentError getExceptionDetails(Throwable e) {
        ExperimentErrorCode errorCode = errorCodeMap.getOrDefault(e.getClass().getName(),
                ExperimentErrorCode.UNKNOWN);
        ExperimentError errorObject = new ExperimentError(e.getClass().getName(), e.getMessage(), errorCode);
        GnumaLogger.log(log::info, String.format("Converted %s to %s", e.getClass().getSimpleName(), errorObject.toString()));
        return errorObject;
    }
}
