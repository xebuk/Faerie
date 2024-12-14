package multithreading;

import botexecution.mainobjects.AbilBot;
import common.DataReader;
import logger.BotLogger;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BotThread extends Thread {
    private static TelegramBotsLongPollingApplication application;
    private static BotSession botSession;
    private static AbilBot bot;

    private static void setupBot() {
        try {
            bot = new AbilBot();
            application = new TelegramBotsLongPollingApplication();
            botSession = application.registerBot(DataReader.readToken(), bot);
            BotLogger.info("Bot started");
        } catch (TelegramApiException e) {
            BotLogger.severe(e.getMessage());
        }
    }

    @Override
    public void run() {
        if (bot == null) {
            setupBot();
        }
        else if (bot.isActive()) {
            bot.setActive(false);
            botSession.getRunningPolling().cancel(true);
            botSession.setRunningPolling(null);
            BotLogger.info("Bot stopped");
        }
        else {
            bot.setActive(true);
            try {
                botSession.start();
            } catch (TelegramApiException e) {
                BotLogger.severe(e.getMessage());
            }
            BotLogger.info("Bot started");
        }
    }
}
