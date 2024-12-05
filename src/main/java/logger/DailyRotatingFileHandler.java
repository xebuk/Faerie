package logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.Constants;

public class DailyRotatingFileHandler extends FileHandler {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static String currentDate;
    private static FileHandler fileHandler;

    public DailyRotatingFileHandler() throws IOException {
        super(generateFileName(), true);
        currentDate = dateFormat.format(new Date());
    }

    private static String generateFileName() {
        return Constants.LOGS_PATH + "logfile_" + dateFormat.format(new Date()) + ".log";
    }

    public static void rotate(Logger logger) throws IOException {
        String newDate = dateFormat.format(new Date());
        if (!newDate.equals(currentDate)) {
            currentDate = newDate;
            fileHandler.close();
            fileHandler = new FileHandler(generateFileName(), true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new BotLogFormatter());
            logger.addHandler(fileHandler);
            logger.info("Log file rotated to: " + generateFileName());
        }
    }
}
