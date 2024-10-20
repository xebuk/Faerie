package botexecution;

import common.*;

import dnd.DungeonMasterDnD;
import dnd.PlayerDnD;
import dnd.characteristics.BackgroundDnD;
import dnd.characteristics.JobDnD;
import dnd.characteristics.RaceDnD;
import dnd.characteristics.backgroundsdnd.*;
import dnd.characteristics.jobsdnd.*;
import dnd.characteristics.racesdnd.*;
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

import static dnd.PlayerCreationStageDnD.*;
import static org.telegram.telegrambots.abilitybots.api.objects.Locality.*;
import static org.telegram.telegrambots.abilitybots.api.objects.Privacy.*;
import static org.telegram.telegrambots.abilitybots.api.util.AbilityUtils.getChatId;

public class AbilBot extends AbilityBot {
    private final HashMap<String, Consumer<ChatSession>> methodsAllocator = new HashMap<>();

    private final HashMap<String, Job> jobAllocatorGame = new HashMap<>();
    private final HashMap<String, BiConsumer<PlayerCharacter, Integer>> statAllocatorGame = new HashMap<>();

    private final HashMap<String, RaceDnD> raceDnDAllocator = new HashMap<>();
    private final HashMap<String, JobDnD> jobDnDAllocator = new HashMap<>();
    private final HashMap<String, BackgroundDnD> backgroundDnDAllocator = new HashMap<>();
    private final HashMap<String, BiConsumer<PlayerDnD, Integer>> statAllocatorDnD = new HashMap<>();
    private final HashMap<String, Consumer<PlayerDnD>> skillAllocatorDnD = new HashMap<>();

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
            cs.sectionId = "";
            cs.searchSuccess = false;
            cs.title = "";
            return false;
        }

        patternExecute(cs, SiteParser.addressWriter(matches, cs.sectionId), null, true);
        return true;
    }

    private void patternExecute(ChatSession cs, String message, ReplyKeyboard function, boolean parseMode) {
        StringBuilder sign = new StringBuilder();
        if (!cs.isPM()) {
            sign.append(cs.username).append("\n").append("----------------------------------").append("\n");
        }
        sign.append(message);
        SendMessage text = new SendMessage(cs.getChatId().toString(), sign.toString());
        if (function != null) {
            text.setReplyMarkup(function);
        }
        if (parseMode) {
            text.setParseMode("HTML");
            text.disableWebPagePreview();
        }
        silent.execute(text);
    }

    private String variantsMessageConfigurator(List<String> variants) {
        StringBuilder text = new StringBuilder();

        for (int i = 1; i < variants.size() + 1; i++) {
            text.append(i).append(".").append(variants.get(i - 1)).append("\n\n");
        }

        return text.toString();
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

    private void placeholder(MessageContext ctx) {
        silent.send("Данная функция пока не работает в данное время. Ожидайте обновлений!", ctx.chatId());
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
        ChatSession newUser = new ChatSession(ctx);
        patternExecute(newUser, Constants.START_MESSAGE, KeyboardFactory.commonSetOfCommandsBoard(), false);

        UserDataHandler.createChatFile(ctx.chatId().toString());
        UserDataHandler.saveSession(newUser);
    }

    private void createPlayer(MessageContext ctx) {
        ChatSession newUser = UserDataHandler.readSession(ctx.update());
        patternExecute(newUser, Constants.CREATION_MENU_CHOOSE_NAME, null, false);

        newUser.creationOfPlayerCharacter = true;
        UserDataHandler.saveSession(newUser);
    }

    private void createCampaign(MessageContext ctx) {
        long userChatId = UserDataHandler.findChatId("@" + ctx.user().getUserName());
        ChatSession currentUser = UserDataHandler.readSession(userChatId);
        ChatSession currentGroup = UserDataHandler.readSession(ctx.update());
        currentGroup.username = "@" + ctx.user().getUserName();

        if (currentGroup.isHavingACampaign) {
            patternExecute(currentGroup, Constants.CAMPAIGN_CREATION_EXISTS, null, false);
            return;
        }

        currentGroup.isHavingACampaign = true;
        currentGroup.isEndingACampaign = false;

        patternExecute(currentGroup, Constants.CAMPAIGN_CREATION_START, null, false);

        currentGroup.campaignNameIsChosen = true;
        currentGroup.activeDm = new DungeonMasterDnD();
        currentGroup.activeDm.chatId = currentUser.getChatId();
        currentGroup.activeDm.username = currentUser.username;

        UserDataHandler.saveSession(currentGroup);
    }

    private void endCampaign(MessageContext ctx) {
        ChatSession currentGroup = UserDataHandler.readSession(ctx.chatId());

        if (currentGroup.activeDm.username.contains(ctx.user().getUserName())) {
            currentGroup.isEndingACampaign = true;
            patternExecute(currentGroup, Constants.CAMPAIGN_END_AFFIRMATION, KeyboardFactory.YesOrNoBoard(), false);
        }
        else {
            silent.send(Constants.CAMPAIGN_END_RESTRICTION, ctx.chatId());
        }
    }

    private void addAPlayerDnD(MessageContext ctx) {
        long userChatId = UserDataHandler.findChatId("@" + ctx.user().getUserName());
        ChatSession currentUser = UserDataHandler.readSession(userChatId);
        ChatSession currentGroup = UserDataHandler.readSession(ctx.update());
        currentGroup.username = "@" + ctx.user().getUserName();

        patternExecute(currentGroup, Constants.PLAYER_CREATION_START, null, false);

        currentUser.activePc = new PlayerDnD();
        currentUser.activePc.playerName = currentGroup.username;
        currentUser.activePc.campaignChatId = currentGroup.getChatId();
        currentUser.creationOfPlayerDnD = true;
        currentUser.haltCreation = false;
        patternExecute(currentUser, Constants.PLAYER_CREATION_NAME, null, false);

        UserDataHandler.saveSession(currentGroup);
        UserDataHandler.saveSession(currentUser);
    }

    private void haltCreationOfPlayerDnD(MessageContext ctx) {
        ChatSession currentUser = UserDataHandler.readSession(ctx.update());
        currentUser.haltCreation = !currentUser.haltCreation;
        UserDataHandler.saveSession(currentUser);
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
        Consumer<ChatSession> Spell = cs -> {
            silent.send(Constants.SEARCH_MESSAGE_SPELLS, cs.getChatId());
            cs.sectionId = "spells";
        };
        Consumer<ChatSession> Item = cs -> {
            silent.send(Constants.SEARCH_MESSAGE_ITEMS, cs.getChatId());
            cs.sectionId = "items";
        };
        Consumer<ChatSession> Bestiary = cs -> {
            silent.send(Constants.SEARCH_MESSAGE_BESTIARY, cs.getChatId());
            cs.sectionId = "bestiary";
        };
        Consumer<ChatSession> Race = cs -> {
            silent.send(Constants.SEARCH_MESSAGE_RACES, cs.getChatId());
            cs.sectionId = "race";
        };
        Consumer<ChatSession> Class = cs -> patternExecute(cs, Constants.CLASSES_LIST, null, true);
        Consumer<ChatSession> Feat = cs -> {
            silent.send(Constants.SEARCH_MESSAGE_FEATS, cs.getChatId());
            cs.sectionId = "feats";
        };
        Consumer<ChatSession> Background = cs -> {
            silent.send(Constants.SEARCH_MESSAGE_BACKGROUNDS, cs.getChatId());
            cs.sectionId = "backgrounds";
        };

        Consumer<ChatSession> RollD20 = cs -> patternExecute(cs, DiceNew.D20(), null, false);
        Consumer<ChatSession> Roll2D20 = cs -> patternExecute(cs, Constants.ROLL_MESSAGE_ADVANTAGE, KeyboardFactory.rollAdvantageBoard(), false);
        Consumer<ChatSession> RollAdvantage = cs -> patternExecute(cs, DiceNew.D20TwoTimes(true), null, false);
        Consumer<ChatSession> RollDisadvantage = cs -> patternExecute(cs, DiceNew.D20TwoTimes(false), null, false);
        Consumer<ChatSession> RollD8 = cs -> patternExecute(cs, DiceNew.D8(), null, false);
        Consumer<ChatSession> RollD6 = cs -> patternExecute(cs, DiceNew.D6(), null, false);
        Consumer<ChatSession> Roll4D6 = cs -> patternExecute(cs, DiceNew.D6FourTimes(), null, false);
        Consumer<ChatSession> RollD4 = cs -> patternExecute(cs, DiceNew.D4(), null, false);
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

        jobAllocatorGame.put(Constants.CREATION_MENU_FIGHTER, new Fighter());
        jobAllocatorGame.put(Constants.CREATION_MENU_CLERIC, new Cleric());
        jobAllocatorGame.put(Constants.CREATION_MENU_MAGE, new Mage());
        jobAllocatorGame.put(Constants.CREATION_MENU_ROGUE, new Rogue());
        jobAllocatorGame.put(Constants.CREATION_MENU_RANGER, new Ranger());

        BiConsumer<PlayerCharacter, Integer> strengthModGame = PlayerCharacter::initStrength;
        BiConsumer<PlayerCharacter, Integer> dexterityModGame = PlayerCharacter::initDexterity;
        BiConsumer<PlayerCharacter, Integer> constitutionModGame = PlayerCharacter::initConstitution;
        BiConsumer<PlayerCharacter, Integer> intelligenceModGame = PlayerCharacter::initIntelligence;
        BiConsumer<PlayerCharacter, Integer> wisdomModGame = PlayerCharacter::initWisdom;
        BiConsumer<PlayerCharacter, Integer> charismaModGame = PlayerCharacter::initCharisma;

        statAllocatorGame.put(Constants.CREATION_MENU_STRENGTH, strengthModGame);
        statAllocatorGame.put(Constants.CREATION_MENU_DEXTERITY, dexterityModGame);
        statAllocatorGame.put(Constants.CREATION_MENU_CONSTITUTION, constitutionModGame);
        statAllocatorGame.put(Constants.CREATION_MENU_INTELLIGENCE, intelligenceModGame);
        statAllocatorGame.put(Constants.CREATION_MENU_WISDOM, wisdomModGame);
        statAllocatorGame.put(Constants.CREATION_MENU_CHARISMA, charismaModGame);

        raceDnDAllocator.put("Гном", new GnomeDnD());
        raceDnDAllocator.put("Дварф", new DwarfDnD());
        raceDnDAllocator.put("Драконорожденный", new DragonbornDnD());
        raceDnDAllocator.put("Полуорк", new HalfOrcDnD());
        raceDnDAllocator.put("Полурослик", new HalflingDnD());
        raceDnDAllocator.put("Полуэльф", new HalfElfDnD());
        raceDnDAllocator.put("Тифлинг", new TieflingDnD());
        raceDnDAllocator.put("Человек", new HumanDnD());
        raceDnDAllocator.put("Человек (Вариант)", new HumanVariantDnD());
        raceDnDAllocator.put("Эльф", new ElfDnD());

        jobDnDAllocator.put("Бард", new BardDnD());
        jobDnDAllocator.put("Варвар", new BarbarianDnD());
        jobDnDAllocator.put("Воин", new FighterDnD());
        jobDnDAllocator.put("Волшебник", new WizardDnD());
        jobDnDAllocator.put("Друид", new DruidDnD());
        jobDnDAllocator.put("Жрец", new ClericDnD());
        jobDnDAllocator.put("Изобретатель", new ArtificerDnD());
        jobDnDAllocator.put("Колдун", new WarlockDnD());
        jobDnDAllocator.put("Монах", new MonkDnD());
        jobDnDAllocator.put("Паладин", new PaladinDnD());
        jobDnDAllocator.put("Плут", new RogueDnD());
        jobDnDAllocator.put("Следопыт", new RangerDnD());
        jobDnDAllocator.put("Чародей", new SorcererDnD());

        backgroundDnDAllocator.put("Артист", new EntertainerDnD());
        backgroundDnDAllocator.put("Беспризорник", new UrchinDnD());
        backgroundDnDAllocator.put("Благородный", new NobleDnD());
        backgroundDnDAllocator.put("Гильдейский ремесленник", new GuildArtisanDnD());
        backgroundDnDAllocator.put("Моряк", new SailorDnD());
        backgroundDnDAllocator.put("Мудрец", new SageDnD());
        backgroundDnDAllocator.put("Народный герой", new FolkHeroDnD());
        backgroundDnDAllocator.put("Отшельник", new HermitDnD());
        backgroundDnDAllocator.put("Пират", new PirateDnD());
        backgroundDnDAllocator.put("Преступник", new CriminalDnD());
        backgroundDnDAllocator.put("Прислужник", new AcolyteDnD());
        backgroundDnDAllocator.put("Солдат", new SoldierDnD());
        backgroundDnDAllocator.put("Чужеземец", new OutlanderDnD());
        backgroundDnDAllocator.put("Шарлатан", new CharlatanDnD());

        BiConsumer<PlayerDnD, Integer> strengthModDnD = PlayerDnD::initStrength;
        BiConsumer<PlayerDnD, Integer> dexterityModDnD = PlayerDnD::initDexterity;
        BiConsumer<PlayerDnD, Integer> constitutionModDnD = PlayerDnD::initConstitution;
        BiConsumer<PlayerDnD, Integer> intelligenceModDnD = PlayerDnD::initIntelligence;
        BiConsumer<PlayerDnD, Integer> wisdomModDnD = PlayerDnD::initWisdom;
        BiConsumer<PlayerDnD, Integer> charismaModDnD = PlayerDnD::initCharisma;

        statAllocatorDnD.put("Сила", strengthModDnD);
        statAllocatorDnD.put("Ловкость", dexterityModDnD);
        statAllocatorDnD.put("Выносливость", constitutionModDnD);
        statAllocatorDnD.put("Интеллект", intelligenceModDnD);
        statAllocatorDnD.put("Мудрость", wisdomModDnD);
        statAllocatorDnD.put("Харизма", charismaModDnD);

        Consumer<PlayerDnD> acrobaticsModDnD = PlayerDnD::setAcrobaticsMastery;
        Consumer<PlayerDnD> analysisModDnD = PlayerDnD::setAnalysisMastery;
        Consumer<PlayerDnD> athleticsModDnD = PlayerDnD::setAthleticsMastery;
        Consumer<PlayerDnD> perceptionModDnD = PlayerDnD::setPerceptionMastery;
        Consumer<PlayerDnD> survivalModDnD = PlayerDnD::setSurvivalMastery;
        Consumer<PlayerDnD> performanceModDnD = PlayerDnD::setPerformanceMastery;
        Consumer<PlayerDnD> intimidationModDnD = PlayerDnD::setIntimidationMastery;
        Consumer<PlayerDnD> historyModDnD = PlayerDnD::setHistoryMastery;
        Consumer<PlayerDnD> sleightOfHandModDnD = PlayerDnD::setSleightOfHandMastery;
        Consumer<PlayerDnD> arcaneModDnD = PlayerDnD::setArcaneMastery;
        Consumer<PlayerDnD> medicineModDnD = PlayerDnD::setMedicineMastery;
        Consumer<PlayerDnD> deceptionModDnD = PlayerDnD::setDeceptionMastery;
        Consumer<PlayerDnD> natureModDnD = PlayerDnD::setNatureMastery;
        Consumer<PlayerDnD> insightModDnD = PlayerDnD::setInsightMastery;
        Consumer<PlayerDnD> religionModDnD = PlayerDnD::setReligionMastery;
        Consumer<PlayerDnD> stealthModDnD = PlayerDnD::setStealthMastery;
        Consumer<PlayerDnD> persuasionModDnD = PlayerDnD::setPersuasionMastery;
        Consumer<PlayerDnD> animalHandlingModDnD = PlayerDnD::setAnimalHandlingMastery;

        skillAllocatorDnD.put("Акробатика", acrobaticsModDnD);
        skillAllocatorDnD.put("Анализ", analysisModDnD);
        skillAllocatorDnD.put("Атлетика", athleticsModDnD);
        skillAllocatorDnD.put("Восприятие", perceptionModDnD);
        skillAllocatorDnD.put("Выживание", survivalModDnD);
        skillAllocatorDnD.put("Выступление", performanceModDnD);
        skillAllocatorDnD.put("Запугивание", intimidationModDnD);
        skillAllocatorDnD.put("История", historyModDnD);
        skillAllocatorDnD.put("Ловкость рук", sleightOfHandModDnD);
        skillAllocatorDnD.put("Магия", arcaneModDnD);
        skillAllocatorDnD.put("Медицина", medicineModDnD);
        skillAllocatorDnD.put("Обман", deceptionModDnD);
        skillAllocatorDnD.put("Природа", natureModDnD);
        skillAllocatorDnD.put("Проницательность", insightModDnD);
        skillAllocatorDnD.put("Религия", religionModDnD);
        skillAllocatorDnD.put("Скрытность", stealthModDnD);
        skillAllocatorDnD.put("Убеждение", persuasionModDnD);
        skillAllocatorDnD.put("Уход за животными", animalHandlingModDnD);
    }

    public Ability startOut() {
        Consumer<MessageContext> start = this::startNewUser;

        return Ability
                .builder()
                .name("start")
                .info("starts up the bot")
                .input(0)
                .locality(ALL)
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
                .locality(ALL)
                .privacy(PUBLIC)
                .action(helpHand)
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
                .locality(ALL)
                .privacy(PUBLIC)
                .action(credits)
                .build();
    }

    public Ability moveToCommonKeyboard() {
        Consumer<MessageContext> commonKeyboard =
                ctx -> patternExecute(new ChatSession(ctx), Constants.CHANGE_TO_COMMON_KEYBOARD, KeyboardFactory.commonSetOfCommandsBoard(), false);

        return Ability
                .builder()
                .name("common")
                .info("shows a common keyboard")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(commonKeyboard)
                .build();
    }

    public Ability moveToGameKeyboard() {
        Consumer<MessageContext> gameKeyboard =
                ctx -> patternExecute(new ChatSession(ctx), Constants.CHANGE_TO_GAME_KEYBOARD, KeyboardFactory.gameSetOfCommandsBoard(), false);

        return Ability
                .builder()
                .name("game")
                .info("shows a game keyboard")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(gameKeyboard)
                .build();
    }

    public Ability moveToDndKeyboard() {
        Consumer<MessageContext> dndKeyboard =
                ctx -> patternExecute(new ChatSession(ctx), Constants.CHANGE_TO_DND_KEYBOARD, KeyboardFactory.dndSetOfCommandsBoard(), false);

        return Ability
                .builder()
                .name("dnd")
                .info("shows a dnd keyboard")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(dndKeyboard)
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
                .locality(ALL)
                .privacy(PUBLIC)
                .action(mofu)
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
                .locality(ALL)
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
                .locality(ALL)
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
                .locality(ALL)
                .privacy(PUBLIC)
                .action(createNewPc)
                .build();
    }

    public Ability startAGame() {
        Consumer<MessageContext> game = this::placeholder;

        return Ability
                .builder()
                .name("startagame")
                .info("starts a game")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .build();
    }

    public Ability sendPhotoOnDemand() {
        Consumer<MessageContext> pic = this::sendPic;

        return Ability
                .builder()
                .name("photoondemand")
                .info("sends a photo")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(pic)
                .build();
    }

    public Ability createCampaign() {
        Consumer<MessageContext> campaign = this::createCampaign;

        return Ability
                .builder()
                .name("createacampaign")
                .info("creates a campaign")
                .input(0)
                .locality(GROUP)
                .privacy(GROUP_ADMIN)
                .action(campaign)
                .build();
    }

    public Ability endCampaign() {
        Consumer<MessageContext> end = this::endCampaign;

        return Ability
                .builder()
                .name("endacampaign")
                .info("ends a campaign")
                .input(0)
                .locality(GROUP)
                .privacy(GROUP_ADMIN)
                .action(end)
                .build();
    }

    public Ability createPlayerDnD() {
        Consumer<MessageContext> player = this::addAPlayerDnD;

        return Ability
                .builder()
                .name("createaplayer")
                .info("creates a player")
                .input(0)
                .locality(GROUP)
                .privacy(PUBLIC)
                .action(player)
                .build();
    }

    public Ability haltCreation() {
        Consumer<MessageContext> halt = this::haltCreationOfPlayerDnD;

        return Ability
                .builder()
                .name("haltcreation")
                .info("puts player's creation on pause")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(halt)
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
                currentUser.playerCharacter.setJob(jobAllocatorGame.get(update.getCallbackQuery().getData()));
                silent.send(Constants.CREATION_MENU_SET_STATS, currentUser.getChatId());

                currentUser.statProgress.add(update.getCallbackQuery().getData());
                currentUser.luck = DiceNew.D6FourTimesCreation();

                patternExecute(currentUser, DiceNew.D6FourTimes(currentUser.luck), KeyboardFactory.assignStatsBoardGame(currentUser.statProgress), false);
                UserDataHandler.saveSession(currentUser);
            }
            else {
                if (currentUser.statProgress.size() == 6) {
                    statAllocatorGame.get(update.getCallbackQuery().getData()).accept(currentUser.playerCharacter, currentUser.luck.get(4));

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
                    statAllocatorGame.get(update.getCallbackQuery().getData()).accept(currentUser.playerCharacter, currentUser.luck.get(4));
                    currentUser.statProgress.add(update.getCallbackQuery().getData());
                    currentUser.luck = DiceNew.D6FourTimesCreation();

                    patternExecute(currentUser, DiceNew.D6FourTimes(currentUser.luck), KeyboardFactory.assignStatsBoardGame(currentUser.statProgress), false);
                    UserDataHandler.saveSession(currentUser);
                }
            }
        }

        else if (update.hasCallbackQuery() && Objects.equals(currentUser.getChatId(), getChatId(update))) {
            CallbackQuery query = update.getCallbackQuery();
            String responseQuery = query.getData();

            try {
                currentUser.username = "@" + query.getFrom().getUserName();
            } catch (Exception e) {
                currentUser.username = "@[ДАННЫЕ УДАЛЕНЫ]";
            }
            if (Objects.equals(currentUser.username, "@") || Objects.equals(currentUser.username, "@null")) {
                currentUser.username = "@[ДАННЫЕ УДАЛЕНЫ]";
            }

            if (currentUser.rollCustom) {
                String[] dices = responseQuery.trim().split("d");
                try {
                    patternExecute(currentUser, DiceNew.customDice(Integer.parseInt(dices[0]), Integer.parseInt(dices[1])), null, false);
                } catch (NumberFormatException e) {
                    silent.send(Constants.CUSTOM_DICE_ERROR, currentUser.getChatId());
                }
                currentUser.rollCustom = false;
            }
            else if (currentUser.isEndingACampaign) {
                if (!Objects.equals(currentUser.activeDm.username, currentUser.username)) {
                    silent.send(Constants.CAMPAIGN_END_RESTRICTION, currentUser.getChatId());
                }
                else {
                    switch (responseQuery) {
                        case "Да":
                            currentUser.activeDm = null;
                            currentUser.isHavingACampaign = false;
                            silent.send(Constants.CAMPAIGN_END, currentUser.getChatId());
                            break;
                        case "Нет":
                            currentUser.isEndingACampaign = false;
                            silent.send(Constants.CAMPAIGN_END_FALSE_ALARM, currentUser.getChatId());
                            break;
                        default:
                            currentUser.isEndingACampaign = false;
                            silent.send(Constants.CAMPAIGN_END_FALSE_ALARM, currentUser.getChatId());
                            break;
                    }
                }
            }
            else if (currentUser.creationOfPlayerDnD && !currentUser.haltCreation && currentUser.creationStage == RACE) {
                currentUser.activePc.race = raceDnDAllocator.get(responseQuery);
                currentUser.activePc.initSpeed();

                if (currentUser.activePc.race.personality.isEmpty()) {
                    patternExecute(currentUser, "Выберите класс из предложенных.", KeyboardFactory.jobDnDSelectionBoard(), false);
                    currentUser.creationStage = JOB;
                }
                else {
                    patternExecute(currentUser, "Выберите черту характера персонажа: \n" + variantsMessageConfigurator(currentUser.activePc.race.personality), KeyboardFactory.variantsBoard(currentUser.activePc.race.personality), false);
                    currentUser.creationStage = RACE_PERSONALITY;
                }
            }
            else if (currentUser.creationOfPlayerDnD && !currentUser.haltCreation && currentUser.creationStage == RACE_PERSONALITY) {
                currentUser.activePc.personality.put(currentUser.activePc.race.name, responseQuery);

                patternExecute(currentUser, "Выберите идеал персонажа: \n" + variantsMessageConfigurator(currentUser.activePc.race.ideal), KeyboardFactory.variantsBoard(currentUser.activePc.race.ideal), false);
                currentUser.creationStage = RACE_IDEAL;
            }
            else if (currentUser.creationOfPlayerDnD && !currentUser.haltCreation && currentUser.creationStage == RACE_IDEAL) {
                currentUser.activePc.ideals.put(currentUser.activePc.race.name, responseQuery);

                patternExecute(currentUser, "Выберите привязанность персонажа: \n" + variantsMessageConfigurator(currentUser.activePc.race.bond), KeyboardFactory.variantsBoard(currentUser.activePc.race.bond), false);
                currentUser.creationStage = RACE_BOND;
            }
            else if (currentUser.creationOfPlayerDnD && !currentUser.haltCreation && currentUser.creationStage == RACE_BOND) {
                currentUser.activePc.bonds.put(currentUser.activePc.race.name, responseQuery);

                patternExecute(currentUser, "Выберите слабость персонажа: \n" + variantsMessageConfigurator(currentUser.activePc.race.flaw), KeyboardFactory.variantsBoard(currentUser.activePc.race.flaw), false);
                currentUser.creationStage = RACE_FLAW;
            }
            else if (currentUser.creationOfPlayerDnD && !currentUser.haltCreation && currentUser.creationStage == RACE_FLAW) {
                currentUser.activePc.flaws.put(currentUser.activePc.race.name, responseQuery);

                patternExecute(currentUser, "Выберите класс из предложенных.", KeyboardFactory.jobDnDSelectionBoard(), false);
                currentUser.creationStage = JOB;
            }
            else if (currentUser.creationOfPlayerDnD && !currentUser.haltCreation && currentUser.creationStage == JOB) {
                currentUser.activePc.jobs.add(jobDnDAllocator.get(responseQuery));

                currentUser.activePc.initBookOfSpellsDnD();
                currentUser.activePc.initStartHealth();

                patternExecute(currentUser, "Выберите предысторию из предложенных.", KeyboardFactory.backgroundDnDSelectionBoard(), false);
                currentUser.creationStage = BACKGROUND;
            }
            else if (currentUser.creationOfPlayerDnD && !currentUser.haltCreation && currentUser.creationStage == BACKGROUND) {
                currentUser.activePc.background = backgroundDnDAllocator.get(responseQuery);

                for (String skill : currentUser.activePc.background.learnedSkills) {
                    skillAllocatorDnD.get(skill).accept(currentUser.activePc);
                    currentUser.activePc.learnedSkills.add(skill);
                }

                if (currentUser.activePc.background.personality.isEmpty()) {
                    patternExecute(currentUser, "Выберите свое мировоззрение:", KeyboardFactory.alignmentDnDSelectionBoard(), false);
                    currentUser.creationStage = ALIGNMENT;
                }
                else {
                    patternExecute(currentUser, "Выберите черту характера персонажа: \n" + variantsMessageConfigurator(currentUser.activePc.background.personality), KeyboardFactory.variantsBoard(currentUser.activePc.background.personality), false);
                    currentUser.creationStage = BACKGROUND_PERSONALITY;
                }
            }
            else if (currentUser.creationOfPlayerDnD && !currentUser.haltCreation && currentUser.creationStage == BACKGROUND_PERSONALITY) {
                currentUser.activePc.personality.put(currentUser.activePc.background.name, responseQuery);

                patternExecute(currentUser, "Выберите идеал персонажа: \n" + variantsMessageConfigurator(currentUser.activePc.background.ideal), KeyboardFactory.variantsBoard(currentUser.activePc.background.ideal), false);
                currentUser.creationStage = BACKGROUND_IDEAL;
            }
            else if (currentUser.creationOfPlayerDnD && !currentUser.haltCreation && currentUser.creationStage == BACKGROUND_IDEAL) {
                currentUser.activePc.ideals.put(currentUser.activePc.background.name, responseQuery);

                patternExecute(currentUser, "Выберите привязанность персонажа: \n" + variantsMessageConfigurator(currentUser.activePc.background.bond), KeyboardFactory.variantsBoard(currentUser.activePc.background.bond), false);
                currentUser.creationStage = BACKGROUND_BOND;
            }
            else if (currentUser.creationOfPlayerDnD && !currentUser.haltCreation && currentUser.creationStage == BACKGROUND_BOND) {
                currentUser.activePc.bonds.put(currentUser.activePc.background.name, responseQuery);

                patternExecute(currentUser, "Выберите слабость персонажа: \n" + variantsMessageConfigurator(currentUser.activePc.background.flaw), KeyboardFactory.variantsBoard(currentUser.activePc.background.flaw), false);
                currentUser.creationStage = BACKGROUND_FLAW;
            }
            else if (currentUser.creationOfPlayerDnD && !currentUser.haltCreation && currentUser.creationStage == BACKGROUND_FLAW) {
                currentUser.activePc.flaws.put(currentUser.activePc.background.name, responseQuery);

                patternExecute(currentUser, "Выберите свое мировоззрение:", KeyboardFactory.alignmentDnDSelectionBoard(), false);
                currentUser.creationStage = ALIGNMENT;
            }
            else if (currentUser.creationOfPlayerDnD && !currentUser.haltCreation && currentUser.creationStage == ALIGNMENT) {
                currentUser.activePc.alignment = responseQuery;

                patternExecute(currentUser, "Распределите характеристики.", null, false);

                currentUser.luck = DiceNew.D6FourTimesCreation();
                patternExecute(currentUser, DiceNew.D6FourTimes(currentUser.luck), KeyboardFactory.assignStatsBoardDnD(currentUser.statProgress), false);

                currentUser.creationStage = STATS1;
            }
            else if (currentUser.creationOfPlayerDnD && !currentUser.haltCreation & currentUser.creationStage.ordinal() < STATS6.ordinal()) {
                statAllocatorDnD.get(responseQuery).accept(currentUser.activePc, currentUser.luck.get(4));

                currentUser.statProgress.add(responseQuery);
                currentUser.luck = DiceNew.D6FourTimesCreation();

                patternExecute(currentUser, DiceNew.D6FourTimes(currentUser.luck), KeyboardFactory.assignStatsBoardDnD(currentUser.statProgress), false);
                currentUser.creationStage = currentUser.creationStage.next();
            }
            else if (currentUser.creationOfPlayerDnD && !currentUser.haltCreation & currentUser.creationStage == STATS6) {
                statAllocatorDnD.get(responseQuery).accept(currentUser.activePc, currentUser.luck.get(4));

                currentUser.statProgress.clear();
                currentUser.luck.clear();

                patternExecute(currentUser, "Выберите навыки персонажа. Осталось навыков: " + currentUser.activePc.jobs.get(0).startingSkillAmount, KeyboardFactory.assignSkillsBoardDnD(currentUser.activePc.jobs.get(0).skillRoster, currentUser.activePc.learnedSkills), false);
                currentUser.creationStage = SKILLS;
            }
            else if (currentUser.creationOfPlayerDnD && !currentUser.haltCreation && currentUser.creationStage == SKILLS) {
                if (currentUser.activePc.jobs.get(0).startingSkillAmount == 1) {
                    skillAllocatorDnD.get(responseQuery).accept(currentUser.activePc);

                    currentUser.activePc.learnedSkills.add(responseQuery);
                    currentUser.activePc.jobs.get(0).startingSkillAmount--;

                    patternExecute(currentUser, "Введите возраст персонажа.", null, false);
                    currentUser.creationStage = AGE;
                }
                else {
                    skillAllocatorDnD.get(responseQuery).accept(currentUser.activePc);

                    currentUser.activePc.learnedSkills.add(responseQuery);
                    currentUser.activePc.jobs.get(0).startingSkillAmount--;

                    patternExecute(currentUser, "Осталось навыков: " + currentUser.activePc.jobs.get(0).startingSkillAmount, KeyboardFactory.assignSkillsBoardDnD(currentUser.activePc.jobs.get(0).skillRoster, currentUser.activePc.learnedSkills), false);
                }
            }
            else {
                methodsAllocator.get(responseQuery).accept(currentUser);
            }
            UserDataHandler.saveSession(currentUser);
        }

        else if (update.hasMessage() && update.getMessage().hasText() && !update.getMessage().isCommand() && Objects.equals(currentUser.getChatId(), getChatId(update))) {
            try {
                currentUser.username = "@" + update.getMessage().getForwardSenderName();
            } catch (Exception e) {
                currentUser.username = "@[ДАННЫЕ УДАЛЕНЫ]";
            }
            if (Objects.equals(currentUser.username, "")) {
                currentUser.username = "@[ДАННЫЕ УДАЛЕНЫ]";
            }

            if (currentUser.campaignNameIsChosen) {
                currentUser.activeDm.campaignName = update.getMessage().getText();
                currentUser.campaignNameIsChosen = false;
                silent.send(Constants.CAMPAIGN_CREATION_CONGRATULATION, currentUser.getChatId());
                silent.send(Constants.PLAYER_CREATION_WARNING, currentUser.getChatId());
            }

            else if (currentUser.creationOfPlayerCharacter && !currentUser.nameIsChosen) {
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
                patternExecute(currentUser, DiceNew.customDice(Integer.parseInt(dices[0]), Integer.parseInt(dices[1])), null, false);

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

            else if (currentUser.creationOfPlayerDnD && !currentUser.haltCreation) {
                switch (currentUser.creationStage) {
                    case NAME:
                        currentUser.activePc.name = update.getMessage().getText();
                        patternExecute(currentUser, "Выберите расу из предложенных.", KeyboardFactory.raceDnDSelectionBoard(), false);
                        currentUser.creationStage = RACE;
                        break;
                    case AGE:
                        currentUser.activePc.age = update.getMessage().getText();
                        patternExecute(currentUser, "Введите рост персонажа.", null, false);
                        currentUser.creationStage = HEIGHT;
                        break;
                    case HEIGHT:
                        currentUser.activePc.height = update.getMessage().getText();
                        patternExecute(currentUser, "Введите вес персонажа.", null, false);
                        currentUser.creationStage = WEIGHT;
                        break;
                    case WEIGHT:
                        currentUser.activePc.weight = update.getMessage().getText();
                        patternExecute(currentUser, "Введите описание глаз персонажа.", null, false);
                        currentUser.creationStage = EYES;
                        break;
                    case EYES:
                        currentUser.activePc.eyes = update.getMessage().getText();
                        patternExecute(currentUser, "Введите описание кожи персонажа.", null, false);
                        currentUser.creationStage = SKIN;
                        break;
                    case SKIN:
                        currentUser.activePc.skin = update.getMessage().getText();
                        patternExecute(currentUser, "Введите описание волос персонажа.", null, false);
                        currentUser.creationStage = HAIR;
                        break;
                    case HAIR:
                        currentUser.activePc.hair = update.getMessage().getText();
                        ChatSession currentGroup = UserDataHandler.readSession(currentUser.activePc.campaignChatId);
                        currentGroup.activeDm.playerDnDHashMap.put(currentUser.username, currentUser.activePc);
                        currentUser.activePc = null;
                        currentUser.creationOfPlayerDnD = false;
                        currentUser.creationStage = NAME;
                        UserDataHandler.saveSession(currentGroup); // небезопасная вещь, надо либо вывесить предупреждение (сделано), либо что-то с этим решить
                        UserDataHandler.saveSession(currentUser);
                        patternExecute(currentUser, Constants.PLAYER_CREATION_END, null, false);
                        break;
                    default:
                        patternExecute(currentUser, "А?", null, false);
                        break;
                }

            }
        }
    }
}