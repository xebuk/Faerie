package logger;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BotLogger {
    private static final Logger logger = Logger.getLogger(BotLogger.class.getName());
    private static final long ONE_DAY_MS = 24 * 60 * 60 * 1000;

    static {
        BotLogFormatter botLogFormatter = new BotLogFormatter();

        // Console logging
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
        consoleHandler.setFormatter(botLogFormatter);
        logger.addHandler(consoleHandler);

        try {
            // File logging
            DailyRotatingFileHandler rotatingFileHandler = new DailyRotatingFileHandler();
            rotatingFileHandler.setLevel(Level.ALL);
            rotatingFileHandler.setFormatter(botLogFormatter);
            logger.addHandler(rotatingFileHandler);

            // Rotation every day at midnight
            Timer timer = new Timer("LogRotator", true);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        DailyRotatingFileHandler.rotate(logger);
                        BotLogger.info("Log file was successfully rotated");
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, "Error rotating log file", e);
                    }
                }
            }, getMidnightTime(), ONE_DAY_MS);
        } catch (IOException e) {
            BotLogger.severe("File logging crashed");
            throw new RuntimeException(e);
        }
        finally {
            logger.setUseParentHandlers(false);
        }
    }

    public static void log(Level level, String message) {
        logger.log(level, message);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void warning(String message) {
        logger.warning(message);
    }

    public static void severe(String message) {
        logger.severe(message);
    }

    private static long getMidnightTime() {
        long currentTime = System.currentTimeMillis();
        return (currentTime / ONE_DAY_MS + 1) * ONE_DAY_MS;
    }
}
