package org.unibayreuth.gnumaexperiments.logging;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GnumaLogger {
    public static void log(Consumer<String> loggerMethod, String message) {
        loggerMethod.accept(message);
        //TODO: send request to the Logging service
    }

    public static void log(BiConsumer<String, Throwable> loggerMethod, String message, Throwable throwable) {
        loggerMethod.accept(message, throwable);
        //TODO: send a request to the Logging service
    }
}
