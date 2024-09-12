import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class Main {
    private static void startBot() {
        try {
            TelegramBotsLongPollingApplication application = new TelegramBotsLongPollingApplication();
            Bot botyara = new Bot();
            application.registerBot(TokenReader.readToken(), botyara);
            botyara = null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        startBot();
    }
}
