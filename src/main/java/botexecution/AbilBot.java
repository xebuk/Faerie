package botexecution;

import common.*;

import org.telegram.telegrambots.abilitybots.api.objects.*;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

import static java.lang.System.exit;
import static org.telegram.telegrambots.abilitybots.api.objects.Locality.*;
import static org.telegram.telegrambots.abilitybots.api.objects.Privacy.*;
import static org.telegram.telegrambots.abilitybots.api.util.AbilityUtils.getChatId;

public class AbilBot extends AbilityBot {
    public String sectionId = "";
    public boolean searchSuccess = false;
    public String title = "";
    public boolean rollCustom = false;

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

    public boolean searchEngine(String section, String entry, Update update) {
        ArrayList<String> matches;
        boolean gotId = false;
        try {
            matches = DataReader.searchArticleIds(section, entry);
        } catch (IOException e) {
            matches = new ArrayList<>();
            matches.add("1");
        }

        if (matches.size() == 1 && Objects.equals(matches.get(0), "1")) {
            ArrayList<String> article;
            switch (section) {
                case "spells", "items", "bestiary":
                    article = SiteParser.SpellsItemsBestiaryGrabber(section, matches.get(0));
                    break;
                case "races":
                    article = SiteParser.RacesGrabber(matches.get(0));
                    break;
                case "feats":
                    article = SiteParser.FeatsGrabber(matches.get(0));
                    break;
                case "backgrounds":
                    article = SiteParser.BackgroundsGrabber(matches.get(0));
                    break;
                default:
                    return false;
            }

            articleMessaging(article, update);
            return false;
        }

        else {
            String searchPage = SiteParser.addressWriter(matches, section);
            SendMessage page = new SendMessage(getChatId(update).toString(), searchPage);
            page.setParseMode("HTML");
            silent.execute(page);
            return true;
        }
    }

    public void sendList(Update update) {
        SendMessage list = new SendMessage(getChatId(update).toString(), Constants.CLASSES_LIST);
        list.setParseMode("HTML");
        silent.execute(list);
    }

    public void generateKeyboard(MessageContext ctx) {
        SendMessage gen = new SendMessage(ctx.chatId().toString(), Constants.START_MESSAGE);
        gen.setReplyMarkup(KeyboardFactory.setOfCommandsBoard());
        silent.execute(gen);
    }

    public void search(MessageContext ctx) {
        SendMessage search = new SendMessage(ctx.chatId().toString(), Constants.SEARCH_MESSAGE);
        search.setReplyMarkup(KeyboardFactory.searchBoard());
        silent.execute(search);
    }

    public void roll(MessageContext ctx) {
        SendMessage roll = new SendMessage(ctx.chatId().toString(), Constants.ROLL_MESSAGE);
        roll.setReplyMarkup(KeyboardFactory.rollVariantsBoard());
        silent.execute(roll);
    }

    public void rollAdvantage(Update update) {
        SendMessage rollAdv = new SendMessage(getChatId(update).toString(), Constants.ROLL_MESSAGE_ADVANTAGE);
        rollAdv.setReplyMarkup(KeyboardFactory.rollAdvantageBoard());
        silent.execute(rollAdv);
    }

    public void articleMessaging(ArrayList<String> article, Update update) {
        StringBuilder partOfArticle = new StringBuilder();
        int lengthOfMessage = 0;

        for (String paragraph: article) {
            if (lengthOfMessage + paragraph.length() <= 4095) {
                partOfArticle.append(paragraph);
                lengthOfMessage = lengthOfMessage + paragraph.length();
            }
            else {
                silent.send(partOfArticle.toString(), getChatId(update));
                partOfArticle.setLength(0);
                lengthOfMessage = paragraph.length();
                partOfArticle.append(paragraph);
            }
        }
        silent.send(partOfArticle.toString(), getChatId(update));
    }

    public Ability startOut() {
        Consumer<MessageContext> start = this::generateKeyboard;

        return Ability
                .builder()
                .name("start")
                .info("starts up the bot")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(start)
                .build();
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

            switch (responseQuery) {
                case Constants.SPELLS:
                    silent.send(Constants.SEARCH_MESSAGE_SPELLS, getChatId(update));
                    sectionId = "spells";
                    break;
                case Constants.ITEMS:
                    silent.send(Constants.SEARCH_MESSAGE_ITEMS, getChatId(update));
                    sectionId = "items";
                    break;
                case Constants.BESTIARY:
                    silent.send(Constants.SEARCH_MESSAGE_BESTIARY, getChatId(update));
                    sectionId = "bestiary";
                    break;
                case Constants.RACES:
                    silent.send(Constants.SEARCH_MESSAGE_RACES, getChatId(update));
                    sectionId = "race";
                    break;
                case Constants.CLASSES:
                    sendList(update);
                    break;
                case Constants.FEATS:
                    silent.send(Constants.SEARCH_MESSAGE_FEATS, getChatId(update));
                    sectionId = "feats";
                    break;
                case Constants.BACKGROUNDS:
                    silent.send(Constants.SEARCH_MESSAGE_BACKGROUNDS, getChatId(update));
                    sectionId = "backgrounds";
                    break;
                case Constants.ROLL_D20:
                    silent.send(DiceNew.D20(), getChatId(update));
                    break;
                case Constants.ROLL_2D20:
                    rollAdvantage(update);
                    break;
                case Constants.ADVANTAGE:
                    silent.send(DiceNew.D20_two_times(true), getChatId(update));
                    break;
                case Constants.DISADVANTAGE:
                    silent.send(DiceNew.D20_two_times(false), getChatId(update));
                    break;
                case Constants.ROLL_D8:
                    silent.send(DiceNew.D8(), getChatId(update));
                    break;
                case Constants.ROLL_D6:
                    silent.send(DiceNew.D6(), getChatId(update));
                    break;
                case Constants.ROLL_4D6:
                    silent.send(DiceNew.D6_four_times(), getChatId(update));
                    break;
                case Constants.ROLL_D4:
                    silent.send(DiceNew.D4(), getChatId(update));
                    break;
                case Constants.CUSTOM_DICE:
                    silent.send(Constants.CUSTOM_DICE_MESSAGE, getChatId(update));
                    rollCustom = true;
                    break;
                default:
                    break;
            }
        }

        if (update.hasMessage() && update.getMessage().hasText() && !update.getMessage().isCommand()) {
            if (rollCustom) {
                String[] dices = update.getMessage().getText().trim().split("d");
                silent.send(DiceNew.customDice(Integer.parseInt(dices[0]), Integer.parseInt(dices[1])), getChatId(update));
                rollCustom = false;
            }

            if (searchSuccess) {
                title = update.getMessage().getText();
                switch (sectionId) {
                    case "spells", "items", "bestiary":
                        articleMessaging(SiteParser.SpellsItemsBestiaryGrabber(sectionId, title), update);
                        break;
                    case "race":
                        articleMessaging(SiteParser.RacesGrabber(title), update);
                        break;
                    case "feats":
                        articleMessaging(SiteParser.FeatsGrabber(title), update);
                        break;
                    case "backgrounds":
                        articleMessaging(SiteParser.BackgroundsGrabber(title), update);
                        break;
                    default:
                        searchSuccess = false;
                        break;
                }
                sectionId = "";
                searchSuccess = false;
                title = "";
            }

            if (!sectionId.isEmpty()) {
                searchSuccess = searchEngine(sectionId, update.getMessage().getText(), update);
            }
        }
    }
}