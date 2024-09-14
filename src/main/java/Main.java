import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class Main {
    private static void startBot() {
        try {
            TelegramBotsLongPollingApplication application = new TelegramBotsLongPollingApplication();
            application.registerBot(TokenReader.readToken(), new Ability_Bot());
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
