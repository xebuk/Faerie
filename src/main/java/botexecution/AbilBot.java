package botexecution;

import common.*;

import org.telegram.telegrambots.abilitybots.api.objects.*;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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

    private final HashMap<String, Consumer<Update>> allocator = new HashMap<>();

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

        if (matches.isEmpty()) {
            silent.send(Constants.SEARCH_MESSAGE_FAIL, getChatId(update));
            return false;
        }
        else if (matches.size() == 2) {
            ArrayList<String> article;
            switch (section) {
                case "spells":
                    try {
                        article = SiteParser.SpellsGrabber(matches.getFirst());
                    } catch (IOException e) {
                        return reportIncorrect(update);
                    }
                    break;
                case "items":
                    try {
                        article = SiteParser.ItemsGrabber(matches.getFirst());
                    } catch (IOException e) {
                        return reportIncorrect(update);
                    }
                    break;
                case "bestiary":
                    try {
                        article = SiteParser.BestiaryGrabber(matches.getFirst());
                    } catch (IOException e) {
                        return reportIncorrect(update);
                    }
                    break;
                case "races":
                    try {
                        article = SiteParser.RacesGrabber(matches.getFirst());
                    } catch (IOException e) {
                        return reportIncorrect(update);
                    }
                    break;
                case "feats":
                    try {
                        article = SiteParser.FeatsGrabber(matches.getFirst());
                    } catch (IOException e) {
                        return reportIncorrect(update);
                    }
                    break;
                case "backgrounds":
                    try {
                        article = SiteParser.BackgroundsGrabber(matches.getFirst());
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

    private void patternExecute(MessageContext ctx, String message, ReplyKeyboard Function) {
        SendMessage text = new SendMessage(ctx.chatId().toString(), message);
        text.setReplyMarkup(Function);
        silent.execute(text);
    }

    private void sendList(Update update) {
        SendMessage list = new SendMessage(getChatId(update).toString(), Constants.CLASSES_LIST);
        list.setParseMode("HTML");
        silent.execute(list);
    }

    private void rollAdvantage(Update update) {
        SendMessage rollAdv = new SendMessage(getChatId(update).toString(), Constants.ROLL_MESSAGE_ADVANTAGE);
        rollAdv.setReplyMarkup(KeyboardFactory.rollAdvantageBoard());
        silent.execute(rollAdv);
    }

    private void sendPic(MessageContext ctx) {
        InputFile photo = new InputFile(DataReader.getFrame());
        SendPhoto pic = new SendPhoto(ctx.chatId().toString(), photo);
        try {
            telegramClient.execute(pic);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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
        Consumer<MessageContext> start =
                ctx -> patternExecute(ctx, Constants.START_MESSAGE, KeyboardFactory.setOfCommandsBoard());

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
        Consumer<MessageContext> helpHand =
                ctx -> silent.send(Constants.HELP_MESSAGE, ctx.chatId());

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
        Consumer<MessageContext> mofu =
                ctx -> silent.send("Mofu Mofu!", ctx.chatId());

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
        Consumer<MessageContext> credits =
                ctx -> silent.send(Constants.CREDITS, ctx.chatId());

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
        Consumer<MessageContext> search =
                ctx -> patternExecute(ctx, Constants.SEARCH_MESSAGE, KeyboardFactory.searchBoard());

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
        Consumer<MessageContext> roll =
                ctx -> patternExecute(ctx, Constants.ROLL_MESSAGE, KeyboardFactory.rollVariantsBoard());

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

    public Ability sendPhotoOnDemand() {
        Consumer<MessageContext> pic = this::sendPic;

        return Ability
                .builder()
                .name("photoondemand")
                .info("sends a photo")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(pic)
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

        else if (update.hasMessage() && update.getMessage().hasText() && !update.getMessage().isCommand()) {
            UserDataHandler.createChatFile(update);

            if (rollCustom) {
                try {
                    UserDataHandler.saveDicePresets(update);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String[] dices = update.getMessage().getText().trim().split("d");
                silent.send(DiceNew.customDice(Integer.parseInt(dices[0]), Integer.parseInt(dices[1])), getChatId(update));
                rollCustom = false;
            }

            else if (searchSuccess) {
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

            else if (!sectionId.isEmpty()) {
                searchSuccess = searchEngine(sectionId, update.getMessage().getText(), update);
            }
        }
    }
}