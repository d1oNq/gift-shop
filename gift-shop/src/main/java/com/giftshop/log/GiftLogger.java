package com.giftshop.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GiftLogger {
    private static final Logger logger = LogManager.getLogger(GiftLogger.class);

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logWarning(String message) {
        logger.warn(message);
    }

    public static void logError(String message) {
        logger.error(message);
    }

    public static void logFatal(String message) {
        logger.fatal(message);
    }
}
