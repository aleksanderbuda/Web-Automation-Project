package config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

    public static Logger log = LogManager.getLogger(Log.class.getName());

    public static void info(String message) {
        log.info(message);
    }

    public static void error(String message) {
        log.error(message);
    }
    public static void startTestCase(String testCaseName) {
        log.info("======== Start Test Case: " + testCaseName + " ========");
    }

    public static void endTestCase(String testCaseName) {
        log.info("======== End Test Case: " + testCaseName + " ========");
    }
}