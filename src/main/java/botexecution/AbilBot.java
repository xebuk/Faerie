package botexecution;

import common.Constants;
import common.Dice;
import common.SiteParser;
import common.DataReader;

import org.telegram.telegrambots.abilitybots.api.objects.*;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.io.IOException;
import java.util.function.Consumer;

import static java.lang.Integer.parseInt;
import static org.telegram.telegrambots.abilitybots.api.objects.Locality.*;
import static org.telegram.telegrambots.abilitybots.api.objects.Privacy.*;

public class AbilBot extends AbilityBot {

    public AbilBot() throws IOException {
        super(new OkHttpTelegramClient(DataReader.readToken()), "Faerie");
        super.onRegister();
    }

    @Override
    public long creatorId() {
        long id;
        try {
            id = DataReader.readCreatorId();
        } catch (IOException e) {
            id = 0;
        }
        return id;
    }

    public void search(MessageContext ctx) {
        SendMessage search = new SendMessage(ctx.chatId().toString(), Constants.SEARCH_MESSAGE);
        search.setReplyMarkup(KeyboardFactory.searchEngine());
        silent.execute(search);
        if (ctx.update().hasCallbackQuery()) {
            CallbackQuery callbackQuery = ctx.update().getCallbackQuery();
            System.out.println(callbackQuery.getData());
        }
    }

    public Ability showHelp() {
        Consumer<MessageContext> helpHand = ctx ->
                silent.send(Constants.HELP_MESSAGE, ctx.chatId());

        return Ability.builder()
                .name("help")
                .info("shows all commands")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(helpHand)
                .build();
    }

    public Ability sayHelloWorld() {
        Consumer<MessageContext> hello = ctx ->
                silent.send("Hello, world!", ctx.chatId());
        Consumer<MessageContext> bye = ctx ->
                silent.send("Bye, world~", ctx.chatId());

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
        //Consumer<MessageContext> search = ctx -> silent.send(SiteParser.SpellsItemsBestiaryGrabber(ctx.firstArg(), ctx.secondArg()), ctx.chatId());
        Consumer<MessageContext> search = this::search;

        return Ability
                .builder()
                .name("search")
                .info("searches article on DnD.su")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(search)
                .build();
    }

    public Ability diceRoll() {
        Consumer<MessageContext> roll = ctx ->
                silent.send(new Dice(parseInt(ctx.firstArg()), parseInt(ctx.secondArg()))
                        .diceRoller().toString(), ctx.chatId());

        return Ability
                .builder()
                .name("roll")
                .info("rolls a dice")
                .input(2)
                .locality(USER)
                .privacy(PUBLIC)
                .action(roll)
                .build();
    }
}
