package botexecution;

import common.Constants;
import common.Dice;
import common.SiteParser;
import common.DataReader;

import org.telegram.telegrambots.abilitybots.api.objects.*;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

import static java.lang.Integer.parseInt;
import static org.telegram.telegrambots.abilitybots.api.objects.Locality.*;
import static org.telegram.telegrambots.abilitybots.api.objects.Privacy.*;
import static org.telegram.telegrambots.abilitybots.api.util.AbilityUtils.getChatId;

public class AbilBot extends AbilityBot {
    public String sectionId = "";
    public int diceId = 0;

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
    }

    public void roll(MessageContext ctx) {
        SendMessage roll = new SendMessage(ctx.chatId().toString(), Constants.ROLL_MESSAGE);
        roll.setReplyMarkup(KeyboardFactory.rollVariants());
        silent.execute(roll);
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
        Consumer<MessageContext> roll = this::roll;

        return Ability
                .builder()
                .name("roll")
                .info("rolls a dice")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(roll)
                .build();
    }

    @Override
    public void consume(Update update) {
        super.consume(update);

        if (update.hasCallbackQuery()) {
            CallbackQuery query = update.getCallbackQuery();
            String responseQuery = query.getData();

            if (Objects.equals(responseQuery, Constants.SPELLS)) {
                silent.send(Constants.SEARCH_MESSAGE_SPELLS, getChatId(update));
                sectionId = "spells";
            }

            else if (Objects.equals(responseQuery, Constants.ITEMS)) {
                silent.send(Constants.SEARCH_MESSAGE_ITEMS, getChatId(update));
                sectionId = "items";
            }

            else if (Objects.equals(responseQuery, Constants.BESTIARY)) {
                silent.send(Constants.SEARCH_MESSAGE_BESTIARY, getChatId(update));
                sectionId = "bestiary";
            }

            else if (Objects.equals(responseQuery, Constants.ROLL_D20)) {
                silent.send(Constants.ROLL_MESSAGE_QUANTITY, getChatId(update));
                diceId = 20;
            }

            else if (Objects.equals(responseQuery, Constants.ROLL_D8)) {
                silent.send(Constants.ROLL_MESSAGE_QUANTITY, getChatId(update));
                diceId = 8;
            }

            else if (Objects.equals(responseQuery, Constants.ROLL_D4)) {
                silent.send(Constants.ROLL_MESSAGE_QUANTITY, getChatId(update));
                diceId = 4;
            }
        }

        if (update.hasMessage() && update.getMessage().hasText() && !update.getMessage().isCommand()) {
            if (!sectionId.isEmpty()) {
                silent.send(SiteParser.SpellsItemsBestiaryGrabber(sectionId, update.getMessage().getText()), getChatId(update));
                sectionId = "";
            }

            if (diceId != 0) {
                silent.send(new Dice(parseInt(update.getMessage().getText()), diceId).diceRoller().toString(), getChatId(update));
                diceId = 0;
            }
        }
    }
}