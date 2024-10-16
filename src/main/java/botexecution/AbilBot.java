package botexecution;

import common.*;

import game.characteristics.Job;
import game.characteristics.jobs.*;
import game.entities.PlayerCharacter;
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
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.telegram.telegrambots.abilitybots.api.objects.Locality.*;
import static org.telegram.telegrambots.abilitybots.api.objects.Privacy.*;
import static org.telegram.telegrambots.abilitybots.api.util.AbilityUtils.getChatId;

public class AbilBot extends AbilityBot {
    private final HashMap<String, Consumer<ChatSession>> methodsAllocator = new HashMap<>();

    private final HashMap<String, Job> jobAllocator = new HashMap<>();
    private final HashMap<String, BiConsumer<PlayerCharacter, Integer>> statAllocator = new HashMap<>();

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

    private boolean reportImpossible(ChatSession cs) {
        silent.send(Constants.SEARCH_MESSAGE_IMPOSSIBLE, cs.getChatId());
        return false;
    }

    private boolean reportIncorrect(ChatSession cs) {
        silent.send(Constants.SEARCH_MESSAGE_INCORRECT, cs.getChatId());
        return false;
    }

    private boolean searchEngine(ChatSession cs, String entry) {
        ArrayList<String> matches;

        try {
            matches = DataReader.searchArticleIds(cs.sectionId, entry);
        } catch (IOException e) {
            return reportIncorrect(cs);
        }

        if (matches.isEmpty()) {
            silent.send(Constants.SEARCH_MESSAGE_FAIL, cs.getChatId());
            return false;
        }

        else if (matches.size() == 2) {
            ArrayList<String> article;
            try {
                switch (cs.sectionId) {
                    case "spells":
                        article = SiteParser.SpellsGrabber(matches.getFirst());
                        break;
                    case "items":
                        article = SiteParser.ItemsGrabber(matches.getFirst());
                        break;
                    case "bestiary":
                        article = SiteParser.BestiaryGrabber(matches.getFirst());
                        break;
                    case "races":
                        article = SiteParser.RacesGrabber(matches.getFirst());
                        break;
                    case "feats":
                        article = SiteParser.FeatsGrabber(matches.getFirst());
                        break;
                    case "backgrounds":
                        article = SiteParser.BackgroundsGrabber(matches.getFirst());
                        break;
                    default:
                        reportImpossible(cs);
                        return false;
                }
            } catch (Exception e) {
                return reportIncorrect(cs);
            }

            articleMessaging(article, cs);
            return false;
        }

        patternExecute(cs, SiteParser.addressWriter(matches, cs.sectionId), null, true);
        return true;
    }

    private void patternExecute(ChatSession cs, String message, ReplyKeyboard Function, boolean parseMode) {
        SendMessage text = new SendMessage(cs.getChatId().toString(), message);
        if (Function != null) {
            text.setReplyMarkup(Function);
        }
        if (parseMode) {
            text.setParseMode("HTML");
            text.disableWebPagePreview();
        }
        silent.execute(text);
    }

    private void rollCustom(ChatSession cs) {
        SendMessage rollVar;
        if (!cs.dicePresets.isEmpty()) {
            cs.checkPresetsSize();
            rollVar = new SendMessage(cs.getChatId().toString(), Constants.CUSTOM_DICE_MESSAGE_WITH_PRESETS);
            rollVar.setReplyMarkup(KeyboardFactory.rollCustomBoard(cs));
        }
        else {
            rollVar = new SendMessage(cs.getChatId().toString(), Constants.CUSTOM_DICE_MESSAGE);
        }
        silent.execute(rollVar);
        cs.rollCustom = true;
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

    private void startNewUser(MessageContext ctx) {
        patternExecute(new ChatSession(ctx), Constants.START_MESSAGE, KeyboardFactory.setOfCommandsBoard(), false);

        UserDataHandler.createChatFile(ctx.chatId().toString());
        ChatSession newUser = new ChatSession(ctx);
        UserDataHandler.saveSession(newUser);
    }

    private void createPlayer(MessageContext ctx) {
        ChatSession newUser = UserDataHandler.readSession(ctx.update());
        patternExecute(newUser, Constants.CREATION_MENU_CHOOSE_NAME, null, false);

        newUser.creationOfPlayerCharacter = true;
        UserDataHandler.saveSession(newUser);
    }

    private void articleMessaging(ArrayList<String> article, ChatSession cs) {
        StringBuilder partOfArticle = new StringBuilder();
        int lengthOfMessage = 0;

        for (String paragraph: article) {
            if (lengthOfMessage + paragraph.length() <= 4095) {
                partOfArticle.append(paragraph);
                lengthOfMessage = lengthOfMessage + paragraph.length();
            }
            else {
                silent.send(partOfArticle.toString(), cs.getChatId());
                partOfArticle.setLength(0);
                lengthOfMessage = paragraph.length();
                partOfArticle.append(paragraph);
            }
        }
        silent.send(partOfArticle.toString(), cs.getChatId());
    }

    private void allocate() {
         Consumer<ChatSession> Spell = cs -> {silent.send(Constants.SEARCH_MESSAGE_SPELLS, cs.getChatId());
             cs.sectionId = "spells";};
         Consumer<ChatSession> Item = cs -> {silent.send(Constants.SEARCH_MESSAGE_ITEMS, cs.getChatId());
             cs.sectionId = "items";};
         Consumer<ChatSession> Bestiary = cs -> {silent.send(Constants.SEARCH_MESSAGE_BESTIARY, cs.getChatId());
             cs.sectionId = "bestiary";};
         Consumer<ChatSession> Race = cs -> {silent.send(Constants.SEARCH_MESSAGE_RACES, cs.getChatId());
             cs.sectionId = "race";};
         Consumer<ChatSession> Class = cs -> patternExecute(cs, Constants.CLASSES_LIST, null, true);
         Consumer<ChatSession> Feat = cs -> {silent.send(Constants.SEARCH_MESSAGE_FEATS, cs.getChatId());
             cs.sectionId = "feats";};
         Consumer<ChatSession> Background = cs -> {silent.send(Constants.SEARCH_MESSAGE_BACKGROUNDS, cs.getChatId());
             cs.sectionId = "backgrounds";};

         Consumer<ChatSession> RollD20 = cs -> silent.send(DiceNew.D20(), cs.getChatId());
         Consumer<ChatSession> Roll2D20 = cs -> patternExecute(cs, Constants.ROLL_MESSAGE_ADVANTAGE, KeyboardFactory.rollAdvantageBoard(), false);
         Consumer<ChatSession> RollAdvantage = cs -> silent.send(DiceNew.D20TwoTimes(true), cs.getChatId());
         Consumer<ChatSession> RollDisadvantage = cs -> silent.send(DiceNew.D20TwoTimes(false), cs.getChatId());
         Consumer<ChatSession> RollD8 = cs -> silent.send(DiceNew.D8(), cs.getChatId());
         Consumer<ChatSession> RollD6 = cs -> silent.send(DiceNew.D6(), cs.getChatId());
         Consumer<ChatSession> Roll4D6 = cs -> silent.send(DiceNew.D6FourTimes(), cs.getChatId());
         Consumer<ChatSession> RollD4 = cs -> silent.send(DiceNew.D4(), cs.getChatId());
         Consumer<ChatSession> CustomDice = this::rollCustom;

         methodsAllocator.put(Constants.SPELLS, Spell);
         methodsAllocator.put(Constants.ITEMS, Item);
         methodsAllocator.put(Constants.BESTIARY, Bestiary);
         methodsAllocator.put(Constants.RACES, Race);
         methodsAllocator.put(Constants.CLASSES, Class);
         methodsAllocator.put(Constants.FEATS, Feat);
         methodsAllocator.put(Constants.BACKGROUNDS, Background);
         methodsAllocator.put(Constants.ROLL_D20, RollD20);
         methodsAllocator.put(Constants.ROLL_2D20, Roll2D20);
         methodsAllocator.put(Constants.ADVANTAGE, RollAdvantage);
         methodsAllocator.put(Constants.DISADVANTAGE, RollDisadvantage);
         methodsAllocator.put(Constants.ROLL_D8, RollD8);
         methodsAllocator.put(Constants.ROLL_D6, RollD6);
         methodsAllocator.put(Constants.ROLL_4D6, Roll4D6);
         methodsAllocator.put(Constants.ROLL_D4, RollD4);
         methodsAllocator.put(Constants.CUSTOM_DICE, CustomDice);

         jobAllocator.put(Constants.CREATION_MENU_FIGHTER, new Fighter());
         jobAllocator.put(Constants.CREATION_MENU_CLERIC, new Cleric());
         jobAllocator.put(Constants.CREATION_MENU_MAGE, new Mage());
         jobAllocator.put(Constants.CREATION_MENU_ROGUE, new Rogue());
         jobAllocator.put(Constants.CREATION_MENU_RANGER, new Ranger());

         BiConsumer<PlayerCharacter, Integer> strengthMod = PlayerCharacter::initStrength;
         BiConsumer<PlayerCharacter, Integer> dexterityMod = PlayerCharacter::initDexterity;
         BiConsumer<PlayerCharacter, Integer> constitutionMod = PlayerCharacter::initConstitution;
         BiConsumer<PlayerCharacter, Integer> intelligenceMod = PlayerCharacter::initIntelligence;
         BiConsumer<PlayerCharacter, Integer> wisdomMod = PlayerCharacter::initWisdom;
         BiConsumer<PlayerCharacter, Integer> charismaMod = PlayerCharacter::initCharisma;

         statAllocator.put(Constants.CREATION_MENU_STRENGTH, strengthMod);
         statAllocator.put(Constants.CREATION_MENU_DEXTERITY, dexterityMod);
         statAllocator.put(Constants.CREATION_MENU_CONSTITUTION, constitutionMod);
         statAllocator.put(Constants.CREATION_MENU_INTELLIGENCE, intelligenceMod);
         statAllocator.put(Constants.CREATION_MENU_WISDOM, wisdomMod);
         statAllocator.put(Constants.CREATION_MENU_CHARISMA, charismaMod);
    }

    public Ability startOut() {
        Consumer<MessageContext> start = this::startNewUser;

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
                ctx -> patternExecute(new ChatSession(ctx), Constants.SEARCH_MESSAGE, KeyboardFactory.searchBoard(), false);

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
                ctx -> patternExecute(new ChatSession(ctx), Constants.ROLL_MESSAGE, KeyboardFactory.rollVariantsBoard(), false);

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

    public Ability createPlayerCharacter() {
        Consumer<MessageContext> createNewPc = this::createPlayer;

        return Ability
                .builder()
                .name("createacharacter")
                .info("creates a player character")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(createNewPc)
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

        ChatSession currentUser;
        try {
            currentUser = UserDataHandler.readSession(update);
        } catch (Exception e) {
            UserDataHandler.createChatFile(getChatId(update).toString());
            currentUser = new ChatSession(update);
        }

        if (currentUser.creationOfPlayerCharacter && update.hasCallbackQuery() && Objects.equals(currentUser.getChatId(), getChatId(update))) {
            if (currentUser.statProgress.isEmpty()) {
                currentUser.playerCharacter.setJob(jobAllocator.get(update.getCallbackQuery().getData()));
                silent.send(Constants.CREATION_MENU_SET_STATS, currentUser.getChatId());

                currentUser.statProgress.add(update.getCallbackQuery().getData());
                currentUser.luck = DiceNew.D6FourTimesCreation();

                patternExecute(currentUser, DiceNew.D6FourTimes(currentUser.luck), KeyboardFactory.assignStatsBoard(currentUser.statProgress), false);
                UserDataHandler.saveSession(currentUser);
            }
            else {
                if (currentUser.statProgress.size() == 6) {
                    statAllocator.get(update.getCallbackQuery().getData()).accept(currentUser.playerCharacter, currentUser.luck.get(4));

                    currentUser.playerCharacter.initHealth();
                    currentUser.playerCharacter.setArmorClass();
                    currentUser.playerCharacter.setAttackDice();

                    silent.send(currentUser.playerCharacter.statWindow(), currentUser.getChatId());
                    UserDataHandler.savePlayerCharacter(currentUser.playerCharacter, update);

                    currentUser.playerCharacter = null;
                    currentUser.statProgress.clear();
                    currentUser.creationOfPlayerCharacter = false;
                    currentUser.nameIsChosen = false;
                    UserDataHandler.saveSession(currentUser);
                }
                else {
                    statAllocator.get(update.getCallbackQuery().getData()).accept(currentUser.playerCharacter, currentUser.luck.get(4));
                    currentUser.statProgress.add(update.getCallbackQuery().getData());
                    currentUser.luck = DiceNew.D6FourTimesCreation();

                    patternExecute(currentUser, DiceNew.D6FourTimes(currentUser.luck), KeyboardFactory.assignStatsBoard(currentUser.statProgress), false);
                    UserDataHandler.saveSession(currentUser);
                }
            }
        }

        else if (update.hasCallbackQuery() && Objects.equals(currentUser.getChatId(), getChatId(update))) {
            CallbackQuery query = update.getCallbackQuery();
            String responseQuery = query.getData();

            if (currentUser.rollCustom) {
                String[] dices = responseQuery.trim().split("d");
                try {
                    silent.send(DiceNew.customDice(Integer.parseInt(dices[0]), Integer.parseInt(dices[1])), getChatId(update));
                } catch (NumberFormatException e) {
                    silent.send(Constants.CUSTOM_DICE_ERROR, currentUser.getChatId());
                }
                currentUser.rollCustom = false;
            }
            else {
                methodsAllocator.get(responseQuery).accept(currentUser);
            }
            UserDataHandler.saveSession(currentUser);
        }

        else if (update.hasMessage() && update.getMessage().hasText() && !update.getMessage().isCommand() && Objects.equals(currentUser.getChatId(), getChatId(update))) {

            if (currentUser.creationOfPlayerCharacter && !currentUser.nameIsChosen) {
                currentUser.playerCharacter = new PlayerCharacter();
                currentUser.playerCharacter.setName(update.getMessage().getText());
                currentUser.nameIsChosen = true;

                patternExecute(currentUser, Constants.CREATION_MENU_CHOOSE_JOB, KeyboardFactory.jobSelectionBoard(), false);

                UserDataHandler.saveSession(currentUser);
            }

            else if (currentUser.rollCustom) {
                try {
                    currentUser.dicePresets = UserDataHandler.readDicePresets(update);
                } catch (Exception ignored) {}

                if (!currentUser.dicePresets.contains(update.getMessage().getText())) {
                    currentUser.dicePresets.add(update.getMessage().getText());
                }
                UserDataHandler.saveDicePresets(currentUser.dicePresets, update);

                String[] dices = update.getMessage().getText().trim().split("d");
                silent.send(DiceNew.customDice(Integer.parseInt(dices[0]), Integer.parseInt(dices[1])), getChatId(update));

                currentUser.rollCustom = false;
                UserDataHandler.saveSession(currentUser);
            }

            else if (currentUser.searchSuccess) {
                currentUser.title = update.getMessage().getText();
                try {
                    switch (currentUser.sectionId) {
                        case "spells":
                            articleMessaging(SiteParser.SpellsGrabber(currentUser.title), currentUser);
                            break;
                        case "items":
                            articleMessaging(SiteParser.ItemsGrabber(currentUser.title), currentUser);
                            break;
                        case "bestiary":
                            articleMessaging(SiteParser.BestiaryGrabber(currentUser.title), currentUser);
                            break;
                        case "race":
                            articleMessaging(SiteParser.RacesGrabber(currentUser.title), currentUser);
                            break;
                        case "feats":
                            articleMessaging(SiteParser.FeatsGrabber(currentUser.title), currentUser);
                            break;
                        case "backgrounds":
                            articleMessaging(SiteParser.BackgroundsGrabber(currentUser.title), currentUser);
                            break;
                        default:
                            reportImpossible(currentUser);
                            break;
                    }
                    currentUser.sectionId = "";
                    currentUser.searchSuccess = false;
                    currentUser.title = "";
                } catch (IOException e) {
                    silent.send(Constants.SEARCH_MESSAGE_FAIL_SECOND_STAGE, currentUser.getChatId());
                }
                UserDataHandler.saveSession(currentUser);
            }

            else if (!currentUser.sectionId.isEmpty()) {
                currentUser.searchSuccess = searchEngine(currentUser, update.getMessage().getText());
                UserDataHandler.saveSession(currentUser);
            }
        }
    }
}