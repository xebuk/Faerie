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
import java.util.HashMap;
import java.util.function.Consumer;

import static org.telegram.telegrambots.abilitybots.api.objects.Locality.*;
import static org.telegram.telegrambots.abilitybots.api.objects.Privacy.*;
import static org.telegram.telegrambots.abilitybots.api.util.AbilityUtils.getChatId;

public class AbilBot extends AbilityBot {
    private String sectionId = "";
    private boolean searchSuccess = false;
    private String title = "";
    private boolean rollCustom = false;
    private HashMap<String, Consumer> allocator = new HashMap<>();

    public AbilBot() throws IOException {
        super(new OkHttpTelegramClient(DataReader.readToken()), "Faerie");
        super.onRegister();
        allocate();
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

    private boolean reportImpossible(Update update) {
        silent.send(Constants.SEARCH_MESSAGE_IMPOSSIBLE, getChatId(update));
        return false;
    }

    private boolean reportIncorrect(Update update) {
        silent.send(Constants.SEARCH_MESSAGE_INCORRECT, getChatId(update));
        return false;
    }

    private boolean searchEngine(String section, String entry, Update update) {
        ArrayList<String> matches;

        try {
            matches = DataReader.searchArticleIds(section, entry);
        } catch (IOException e) {
            return reportIncorrect(update);
        }

        if (matches.size() == 0) {
            silent.send(Constants.SEARCH_MESSAGE_FAIL, getChatId(update));
            return false;
        } else if (matches.size() == 2) {
            ArrayList<String> article;
            switch (section) {
                case "spells":
                    try {
                        article = SiteParser.SpellsGrabber(matches.get(0));
                    } catch (IOException e) {
                        return reportIncorrect(update);
                    }
                    break;
                case "items":
                    try {
                        article = SiteParser.ItemsGrabber(matches.get(0));
                    } catch (IOException e) {
                        return reportIncorrect(update);
                    }
                    break;
                case "bestiary":
                    try {
                        article = SiteParser.BestiaryGrabber(matches.get(0));
                    } catch (IOException e) {
                        return reportIncorrect(update);
                    }
                    break;
                case "races":
                    try {
                        article = SiteParser.RacesGrabber(matches.get(0));
                    } catch (IOException e) {
                        return reportIncorrect(update);
                    }
                    break;
                case "feats":
                    try {
                        article = SiteParser.FeatsGrabber(matches.get(0));
                    } catch (IOException e) {
                        return reportIncorrect(update);
                    }
                    break;
                case "backgrounds":
                    try {
                        article = SiteParser.BackgroundsGrabber(matches.get(0));
                    } catch (IOException e) {
                        return reportIncorrect(update);
                    }
                    break;
                default:
                    return false;
            }

            articleMessaging(article, update);
            return false;
        }
        String searchPage = SiteParser.addressWriter(matches, section);
        SendMessage page = new SendMessage(getChatId(update).toString(), searchPage);
        page.setParseMode("HTML");
        silent.execute(page);
        return true;
    }

    private void sendList(Update update) {
        SendMessage list = new SendMessage(getChatId(update).toString(), Constants.CLASSES_LIST);
        list.setParseMode("HTML");
        silent.execute(list);
    }

    private void generateKeyboard(MessageContext ctx) {
        SendMessage gen = new SendMessage(ctx.chatId().toString(), Constants.START_MESSAGE);
        gen.setReplyMarkup(KeyboardFactory.setOfCommandsBoard());
        silent.execute(gen);
    }

    private void search(MessageContext ctx) {
        SendMessage search = new SendMessage(ctx.chatId().toString(), Constants.SEARCH_MESSAGE);
        search.setReplyMarkup(KeyboardFactory.searchBoard());
        silent.execute(search);
    }

    private void roll(MessageContext ctx) {
        SendMessage roll = new SendMessage(ctx.chatId().toString(), Constants.ROLL_MESSAGE);
        roll.setReplyMarkup(KeyboardFactory.rollVariantsBoard());
        silent.execute(roll);
    }

    private void rollAdvantage(Update update) {
        SendMessage rollAdv = new SendMessage(getChatId(update).toString(), Constants.ROLL_MESSAGE_ADVANTAGE);
        rollAdv.setReplyMarkup(KeyboardFactory.rollAdvantageBoard());
        silent.execute(rollAdv);
    }

    private void articleMessaging(ArrayList<String> article, Update update) {
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

    private void allocate() {
         Consumer<Update> Spell = update -> {silent.send(Constants.SEARCH_MESSAGE_SPELLS, getChatId(update));
             sectionId = "spells";};
         Consumer<Update> Item = update -> {silent.send(Constants.SEARCH_MESSAGE_ITEMS, getChatId(update));
             sectionId = "items";};
         Consumer<Update> Bestiary = update -> {silent.send(Constants.SEARCH_MESSAGE_BESTIARY, getChatId(update));
             sectionId = "bestiary";};
         Consumer<Update> Race = update -> {silent.send(Constants.SEARCH_MESSAGE_RACES, getChatId(update));
             sectionId = "race";};
         Consumer<Update> Class = this::sendList;
         Consumer<Update> Feat = update -> {silent.send(Constants.SEARCH_MESSAGE_FEATS, getChatId(update));
             sectionId = "feats";};
         Consumer<Update> Background = update -> {silent.send(Constants.SEARCH_MESSAGE_BACKGROUNDS, getChatId(update));
             sectionId = "backgrounds";};
         Consumer<Update> RollD20 = update -> silent.send(DiceNew.D20(), getChatId(update));
         Consumer<Update> Roll2D20 = this::rollAdvantage;
         Consumer<Update> RollAdvantage = update -> silent.send(DiceNew.D20TwoTimes(true), getChatId(update));
         Consumer<Update> RollDisadvantage = update -> silent.send(DiceNew.D20TwoTimes(false), getChatId(update));
         Consumer<Update> RollD8 = update -> silent.send(DiceNew.D8(), getChatId(update));
         Consumer<Update> RollD6 = update -> silent.send(DiceNew.D6(), getChatId(update));
         Consumer<Update> Roll4D6 = update -> silent.send(DiceNew.D6FourTimes(), getChatId(update));
         Consumer<Update> RollD4 = update -> silent.send(DiceNew.D4(), getChatId(update));
         Consumer<Update> CustomDice = update -> {silent.send(Constants.CUSTOM_DICE_MESSAGE, getChatId(update));
             rollCustom = true;};

         allocator.put(Constants.SPELLS, Spell);
         allocator.put(Constants.ITEMS, Item);
         allocator.put(Constants.BESTIARY, Bestiary);
         allocator.put(Constants.RACES, Race);
         allocator.put(Constants.CLASSES, Class);
         allocator.put(Constants.FEATS, Feat);
         allocator.put(Constants.BACKGROUNDS, Background);
         allocator.put(Constants.ROLL_D20, RollD20);
         allocator.put(Constants.ROLL_2D20, Roll2D20);
         allocator.put(Constants.ADVANTAGE, RollAdvantage);
         allocator.put(Constants.DISADVANTAGE, RollDisadvantage);
         allocator.put(Constants.ROLL_D8, RollD8);
         allocator.put(Constants.ROLL_D6, RollD6);
         allocator.put(Constants.ROLL_4D6, Roll4D6);
         allocator.put(Constants.ROLL_D4, RollD4);
         allocator.put(Constants.CUSTOM_DICE, CustomDice);
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

    public Ability sayMofu() {
        Consumer<MessageContext> mofu = ctx ->
                silent.send("Mofu Mofu!", ctx.chatId());

        return Ability
                .builder()
                .name("mofu")
                .info("mofu")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(mofu)
                .build();
    }

    public Ability showCredits() {
        Consumer<MessageContext> credits = ctx ->
                silent.send(Constants.CREDITS, ctx.chatId());

        return Ability
                .builder()
                .name("credits")
                .info("shows authors and coders for this bot")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(credits)
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

            allocator.get(responseQuery).accept(update);
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
                    case "spells":
                        try {
                            articleMessaging(SiteParser.SpellsGrabber(title), update);
                        } catch (IOException e) {
                            reportImpossible(update);
                        }
                        break;
                    case "items":
                        try {
                            articleMessaging(SiteParser.ItemsGrabber(title), update);
                        } catch (IOException e) {
                            reportImpossible(update);
                        }
                        break;
                    case "bestiary":
                        try {
                            articleMessaging(SiteParser.BestiaryGrabber(title), update);
                        } catch (IOException e) {
                            reportImpossible(update);
                        }
                        break;
                    case "race":
                        try {
                            articleMessaging(SiteParser.RacesGrabber(title), update);
                        } catch (IOException e) {
                            reportImpossible(update);
                        }
                        break;
                    case "feats":
                        try {
                            articleMessaging(SiteParser.FeatsGrabber(title), update);
                        } catch (IOException e) {
                            reportImpossible(update);
                        }
                        break;
                    case "backgrounds":
                        try {
                            articleMessaging(SiteParser.BackgroundsGrabber(title), update);
                        } catch (IOException e) {
                            reportImpossible(update);
                        }
                        break;
                    default:
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