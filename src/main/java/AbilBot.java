import org.telegram.telegrambots.abilitybots.api.objects.*;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;

import java.io.IOException;
import java.util.function.Consumer;

import static org.telegram.telegrambots.abilitybots.api.objects.Locality.*;
import static org.telegram.telegrambots.abilitybots.api.objects.Privacy.*;

public class AbilBot extends AbilityBot {
    public AbilBot() throws IOException {
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

    public Ability requestArticle() {
        Consumer<MessageContext> search = ctx -> silent.send(SiteParser.SpellsItemsBestiaryGrabber(ctx.firstArg(), ctx.secondArg()), ctx.chatId());

        return Ability.builder()
                .name("search")
                .info("searches article on DnD.su")
                .input(2)
                .locality(USER)
                .privacy(PUBLIC)
                .action(search)
                .build();
    }
}
