import org.telegram.telegrambots.abilitybots.api.objects.*;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.telegram.telegrambots.abilitybots.api.objects.Locality.*;
import static org.telegram.telegrambots.abilitybots.api.objects.Privacy.*;
import static org.telegram.telegrambots.abilitybots.api.util.AbilityUtils.getChatId;

public class Ability_Bot extends AbilityBot implements LongPollingSingleThreadUpdateConsumer {
    boolean reverse = false;
    private final OkHttpTelegramClient telegramClient = new OkHttpTelegramClient(TokenReader.readToken());

    public Ability_Bot() throws IOException {
        super(new OkHttpTelegramClient(TokenReader.readToken()), "Faerie");
        super.onRegister();
    }

    @Override
    public long creatorId() {
        long id;
        try {
            id = TokenReader.readCreatorId();
        } catch (IOException e) {
            id = 0;
        }
        return id;
    }

    public Ability sayHelloWorld() {
        Consumer<MessageContext> hello = ctx -> silent.send("Hello, world!", ctx.chatId());
        Consumer<MessageContext> bye = ctx -> silent.send("Bye, world~", ctx.chatId());

        return Ability
                .builder()
                .name("hello")
                .info("says hello world!")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(hello)
                .post(bye)
                .build();
    }

    public Ability revMessage() {
        Consumer<MessageContext> reversal = ctx -> {reverse = !reverse;};
        return Ability.builder()
                .name("reverse")
                .info("reverses all messages!")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(reversal)
                .build();
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText() && reverse) {
            String chatId = update.getMessage().getChatId().toString();
            String msg = update.getMessage().getText();

            String reversedMsg = new StringBuilder(msg).reverse().toString();

            SendMessage sendMessage = new SendMessage(chatId, reversedMsg);
            System.out.println("Received \"" + msg + "\" from " + chatId);
            System.out.println("Sent \"" + reversedMsg + "\" to " + chatId);

            try {
                telegramClient.execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
