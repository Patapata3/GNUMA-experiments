package org.unibayreuth.gnumaexperiments.logging;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GnumaLogger {
    /**
     * log a message without exception with a given logging methof
     * @param loggerMethod - method reference
     * @param message - message being logged
     */
    public static void log(Consumer<String> loggerMethod, String message) {
        loggerMethod.accept(message);
        //TODO: send request to the Logging service
    }

    /**
     * log a message with exception with a given logging method
     * @param loggerMethod - method reference
     * @param message - message being logged
     * @param throwable - exception being logged
     */
    public static void log(BiConsumer<String, Throwable> loggerMethod, String message, Throwable throwable) {
        loggerMethod.accept(message, throwable);
        //TODO: send a request to the Logging service
    }
}
