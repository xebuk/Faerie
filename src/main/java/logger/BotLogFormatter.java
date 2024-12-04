package logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class BotLogFormatter extends Formatter {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public String format(LogRecord record) {
        String timestamp = dateFormat.format(new Date(record.getMillis()));
        String level = record.getLevel().getName();
        String threadName = Thread.currentThread().getName();
        String source = getCallerInfo(); //record.getSourceClassName() + "." + record.getSourceMethodName();
        String message = formatMessage(record);
        String exception = "";
        if (record.getThrown() != null) {
            exception = "\nException: " + record.getThrown().getMessage();
        }

        return String.format("[%s] [%s] [%s] [%s] %s%s%n", timestamp, level, threadName, source, message, exception);
    }

    private static String getCallerInfo() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i = 3; i < stackTrace.length; i++) {   // Start from 3 to skip internal logging methods
            StackTraceElement element = stackTrace[i];
            if (!element.getClassName().contains("java.util.logging") && !element.getClassName().contains("BotLogger")) {
                return element.getClassName() + "." + element.getMethodName() + ":" + element.getLineNumber();
            }
        }
        return "Unknown Source";
    }
}
