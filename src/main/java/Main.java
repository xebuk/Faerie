import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class Main {
    public static void startBot() {
        try {
            String token = TokenReader.readToken();
            TelegramBotsLongPollingApplication application = new TelegramBotsLongPollingApplication();
            application.registerBot(token, new Bot());
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
