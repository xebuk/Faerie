package botexecution;

import common.*;

import dnd.mainobjects.DungeonMasterDnD;
import dnd.mainobjects.PlayerDnD;
import dnd.characteristics.BackgroundDnD;
import dnd.characteristics.JobDnD;
import dnd.characteristics.RaceDnD;
import dnd.characteristics.backgroundsdnd.*;
import dnd.characteristics.jobsdnd.*;
import dnd.characteristics.racesdnd.*;
import dnd.values.PlayerDnDCreationStage;
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

import static dnd.values.PlayerDnDCreationStage.*;
import static org.telegram.telegrambots.abilitybots.api.objects.Locality.*;
import static org.telegram.telegrambots.abilitybots.api.objects.Privacy.*;
import static org.telegram.telegrambots.abilitybots.api.util.AbilityUtils.getChatId;

public class AbilBot extends AbilityBot {
    private final HashMap<String, Consumer<ChatSession>> methodsAllocator = new HashMap<>();

    private final HashMap<String, Job> jobAllocatorGame = new HashMap<>();
    private final HashMap<String, BiConsumer<PlayerCharacter, Integer>> statAllocatorGame = new HashMap<>();

    private final HashMap<PlayerDnDCreationStage, BiConsumer<ChatSession, String>> playerDnDGeneratorAllocator = new HashMap<>();
    private final HashMap<String, RaceDnD> raceDnDAllocator = new HashMap<>();
    private final HashMap<String, JobDnD> jobDnDAllocator = new HashMap<>();
    private final HashMap<String, BackgroundDnD> backgroundDnDAllocator = new HashMap<>();
    private final HashMap<String, BiConsumer<PlayerDnD, Integer>> statAllocatorDnD = new HashMap<>();
    private final HashMap<String, Consumer<PlayerDnD>> skillAllocatorDnD = new HashMap<>();
    private final HashMap<String, Consumer<PlayerDnD>> languagesAllocatorDnD = new HashMap<>();

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

    private void articleMessaging(List<String> article, ChatSession cs) {
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

    private void showCampaigns(MessageContext ctx) {
        ChatSession currentUser = UserDataHandler.readSession(ctx.update());

        if (currentUser.campaigns.isEmpty()) {
            silent.send("На данный момент вы не ведете никаких компаний.", ctx.chatId());
            return;
        }

        ArrayList<String> campaigns = new ArrayList<>();
        campaigns.add("Текущие компании, которые ведете вы: \n");
        int index = 1;
        for (String i : currentUser.campaigns.keySet()) {
            campaigns.add(String.valueOf(index));
            campaigns.add(". ");
            campaigns.add(i);
            campaigns.add("\n");
            index++;
        }

        articleMessaging(campaigns, currentUser);
    }

    private void setCurrentCampaign(MessageContext ctx) {
        ChatSession currentUser = UserDataHandler.readSession(ctx.update());

        try {
            currentUser.currentCampaign = currentUser.campaigns.get(ctx.firstArg());
            currentUser.currentPlayer = null;
            currentUser.editCurrentPlayer = false;
            silent.send("Компания " + currentUser.currentCampaign
                    + " была установлена как основная. Теперь вы можете менять её параметры и добавлять свои предметы.", ctx.chatId());
        } catch (Exception e) {
            silent.send("Такой компании не найдено. Попробуйте ещё раз.", ctx.chatId());
        }

        UserDataHandler.saveSession(currentUser);
    }

    private void setCampaignName(MessageContext ctx) {
        ChatSession currentUser = UserDataHandler.readSession(ctx.update());
        ChatSession currentCampaign;
        try {
            currentCampaign = UserDataHandler.readSession(currentUser.currentCampaign);
        } catch (Exception e) {
            silent.send("Текущая компания не была указана. Для начала укажите текущую компанию.", ctx.chatId());
            return;
        }

        currentUser.campaigns.remove(currentCampaign.activeDm.campaignName);
        currentCampaign.activeDm.campaignName = ctx.firstArg();
        currentUser.campaigns.put(currentCampaign.activeDm.campaignName, currentUser.currentCampaign);

        silent.send("Название компании было изменено.", ctx.chatId());

        UserDataHandler.saveSession(currentCampaign);
        UserDataHandler.saveSession(currentUser);
    }

    private void setPassword(MessageContext ctx) {
        ChatSession currentUser = UserDataHandler.readSession(ctx.update());
        ChatSession currentCampaign;
        try {
            currentCampaign = UserDataHandler.readSession(currentUser.currentCampaign);
        } catch (Exception e) {
            silent.send("Текущая компания не была указана. Для начала укажите текущую компанию.", ctx.chatId());
            return;
        }

        currentCampaign.activeDm.password = ctx.firstArg();
        silent.send("Пароль для удаления компании был указан. При удалении компании он отправится к вам в личные сообщения.", ctx.chatId());

        UserDataHandler.saveSession(currentCampaign);
        UserDataHandler.saveSession(currentUser);
    }

    private void setMulticlassLimit(MessageContext ctx) {
        ChatSession currentUser = UserDataHandler.readSession(ctx.update());
        ChatSession currentCampaign;
        try {
            currentCampaign = UserDataHandler.readSession(currentUser.currentCampaign);
        } catch (Exception e) {
            silent.send("Текущая компания не была указана. Для начала укажите текущую компанию.", ctx.chatId());
            return;
        }

        try {
            currentCampaign.activeDm.multiclassLimit = Integer.parseInt(ctx.firstArg());
        } catch (NumberFormatException e) {
            silent.send("Пожалуйста, введите корректное число.", ctx.chatId());
            return;
        }
        silent.send("Лимит классов на персонажа установлен. Теперь максимальное количество классов - "
                + currentCampaign.activeDm.multiclassLimit, ctx.chatId());
        silent.send("При установке лимита классов на персонажа на 0, вы снимаете ограничение на количество классов.", ctx.chatId());

        UserDataHandler.saveSession(currentCampaign);
        UserDataHandler.saveSession(currentUser);
    }

    private void showPlayers(MessageContext ctx) {
        ChatSession currentUser = UserDataHandler.readSession(ctx.update());
        ChatSession currentCampaign;
        try {
            currentCampaign = UserDataHandler.readSession(currentUser.currentCampaign);
        } catch (Exception e) {
            silent.send("Текущая компания не была указана. Для начала укажите текущую компанию.", ctx.chatId());
            return;
        }

        StringBuilder playersList = new StringBuilder();
        playersList.append("Игроки текущей компании: \n");
        int index = 1;
        for (Map.Entry<String, PlayerDnD> player : currentCampaign.activeDm.playerDnDHashMap.entrySet()) {
            playersList.append(index).append(". ").append(player.getKey()).append(" - ").append(player.getValue().name);
            index++;
        }

        silent.send(playersList.toString(), ctx.chatId());
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

        if (currentUser.haltCreation) {
            silent.send("Процесс создания персонажа ДнД был поставлен на паузу.", ctx.chatId());
        }
        else {
            silent.send("Процесс создания персонажа ДнД был возобновлен.", ctx.chatId());
        }

        UserDataHandler.saveSession(currentUser);
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

        // Аллокаторы для игры

        jobAllocatorGame.put(Constants.CREATION_MENU_FIGHTER, new Fighter());
        jobAllocatorGame.put(Constants.CREATION_MENU_CLERIC, new Cleric());
        jobAllocatorGame.put(Constants.CREATION_MENU_MAGE, new Mage());
        jobAllocatorGame.put(Constants.CREATION_MENU_ROGUE, new Rogue());
        jobAllocatorGame.put(Constants.CREATION_MENU_RANGER, new Ranger());

        statAllocatorGame.put(Constants.CREATION_MENU_STRENGTH, PlayerCharacter::initStrength);
        statAllocatorGame.put(Constants.CREATION_MENU_DEXTERITY, PlayerCharacter::initDexterity);
        statAllocatorGame.put(Constants.CREATION_MENU_CONSTITUTION, PlayerCharacter::initConstitution);
        statAllocatorGame.put(Constants.CREATION_MENU_INTELLIGENCE, PlayerCharacter::initIntelligence);
        statAllocatorGame.put(Constants.CREATION_MENU_WISDOM, PlayerCharacter::initWisdom);
        statAllocatorGame.put(Constants.CREATION_MENU_CHARISMA, PlayerCharacter::initCharisma);

        // Аллокаторы для менеджера компаний ДнД

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

        statAllocatorDnD.put("Сила", PlayerDnD::initStrength);
        statAllocatorDnD.put("Ловкость", PlayerDnD::initDexterity);
        statAllocatorDnD.put("Выносливость", PlayerDnD::initConstitution);
        statAllocatorDnD.put("Интеллект", PlayerDnD::initIntelligence);
        statAllocatorDnD.put("Мудрость", PlayerDnD::initWisdom);
        statAllocatorDnD.put("Харизма", PlayerDnD::initCharisma);

        skillAllocatorDnD.put("Акробатика", PlayerDnD::setAcrobaticsMastery);
        skillAllocatorDnD.put("Анализ", PlayerDnD::setAnalysisMastery);
        skillAllocatorDnD.put("Атлетика", PlayerDnD::setAthleticsMastery);
        skillAllocatorDnD.put("Восприятие", PlayerDnD::setPerceptionMastery);
        skillAllocatorDnD.put("Выживание", PlayerDnD::setSurvivalMastery);
        skillAllocatorDnD.put("Выступление", PlayerDnD::setPerformanceMastery);
        skillAllocatorDnD.put("Запугивание", PlayerDnD::setIntimidationMastery);
        skillAllocatorDnD.put("История", PlayerDnD::setHistoryMastery);
        skillAllocatorDnD.put("Ловкость рук", PlayerDnD::setSleightOfHandMastery);
        skillAllocatorDnD.put("Магия", PlayerDnD::setArcaneMastery);
        skillAllocatorDnD.put("Медицина", PlayerDnD::setMedicineMastery);
        skillAllocatorDnD.put("Обман", PlayerDnD::setDeceptionMastery);
        skillAllocatorDnD.put("Природа", PlayerDnD::setNatureMastery);
        skillAllocatorDnD.put("Проницательность", PlayerDnD::setInsightMastery);
        skillAllocatorDnD.put("Религия", PlayerDnD::setReligionMastery);
        skillAllocatorDnD.put("Скрытность", PlayerDnD::setStealthMastery);
        skillAllocatorDnD.put("Убеждение", PlayerDnD::setPersuasionMastery);
        skillAllocatorDnD.put("Уход за животными", PlayerDnD::setAnimalHandlingMastery);

        languagesAllocatorDnD.put("Великанский", PlayerDnD::learnGiantsLanguage);
        languagesAllocatorDnD.put("Гномий", PlayerDnD::learnGnomishLanguage);
        languagesAllocatorDnD.put("Гоблинский", PlayerDnD::learnGoblinLanguage);
        languagesAllocatorDnD.put("Дварфский", PlayerDnD::learnDwarvishLanguage);
        languagesAllocatorDnD.put("Общий", PlayerDnD::learnCommonLanguage);
        languagesAllocatorDnD.put("Орочий", PlayerDnD::learnOrcishLanguage);
        languagesAllocatorDnD.put("Язык Полуросликов", PlayerDnD::learnHalflingLanguage);
        languagesAllocatorDnD.put("Эльфийский", PlayerDnD::learnElvishLanguage);
        languagesAllocatorDnD.put("Язык Бездны", PlayerDnD::learnAbyssalLanguage);
        languagesAllocatorDnD.put("Небесный", PlayerDnD::learnCelestialLanguage);
        languagesAllocatorDnD.put("Драконий", PlayerDnD::learnDraconicLanguage);
        languagesAllocatorDnD.put("Глубинная речь", PlayerDnD::learnDeepSpeech);
        languagesAllocatorDnD.put("Инфернальный", PlayerDnD::learnInfernalLanguage);
        languagesAllocatorDnD.put("Первичный", PlayerDnD::learnPrimordialLanguage);
        languagesAllocatorDnD.put("Сильван", PlayerDnD::learnSylvanLanguage);
        languagesAllocatorDnD.put("Подземный", PlayerDnD::learnUndercommonLanguage);

        BiConsumer<ChatSession, String> name = (cs, response) -> PlayerDnDGenerator.nameSetter(cs, response, silent);
        BiConsumer<ChatSession, String> race = (cs, response) -> PlayerDnDGenerator.raceSetter(cs, response, raceDnDAllocator, silent);
        BiConsumer<ChatSession, String> racePersonality = (cs, response) -> PlayerDnDGenerator.racePersonalitySetter(cs, response, silent);
        BiConsumer<ChatSession, String> raceIdeal = (cs, response) -> PlayerDnDGenerator.raceIdealSetter(cs, response, silent);
        BiConsumer<ChatSession, String> raceBond = (cs, response) -> PlayerDnDGenerator.raceBondSetter(cs, response, silent);
        BiConsumer<ChatSession, String> raceFlaw = (cs, response) -> PlayerDnDGenerator.raceFlawSetter(cs, response, silent);
        BiConsumer<ChatSession, String> job = (cs, response) -> PlayerDnDGenerator.jobSetter(cs, response, jobDnDAllocator, silent);
        BiConsumer<ChatSession, String> background = (cs, response) -> PlayerDnDGenerator.backgroundSetter(cs, response, backgroundDnDAllocator, skillAllocatorDnD, silent);
        BiConsumer<ChatSession, String> backgroundSpecialInfo = (cs, response) -> PlayerDnDGenerator.backgroundSpecialInfoSetter(cs, response, silent);
        BiConsumer<ChatSession, String> backgroundPersonality = (cs, response) -> PlayerDnDGenerator.backgroundPersonalitySetter(cs, response, silent);
        BiConsumer<ChatSession, String> backgroundIdeal = (cs, response) -> PlayerDnDGenerator.backgroundIdealSetter(cs, response, silent);
        BiConsumer<ChatSession, String> backgroundBond = (cs, response) -> PlayerDnDGenerator.backgroundBondSetter(cs, response, silent);
        BiConsumer<ChatSession, String> backgroundFlaw = (cs, response) -> PlayerDnDGenerator.backgroundFlawSetter(cs, response, silent);
        BiConsumer<ChatSession, String> alignment = (cs, response) -> PlayerDnDGenerator.alignmentSetter(cs, response, silent);
        BiConsumer<ChatSession, String> stats = (cs, response) -> PlayerDnDGenerator.statSetter(cs, response, statAllocatorDnD, silent);
        BiConsumer<ChatSession, String> skill = (cs, response) -> PlayerDnDGenerator.skillsSetter(cs, response, skillAllocatorDnD, silent);
        BiConsumer<ChatSession, String> language = (cs, response) -> PlayerDnDGenerator.languageSetter(cs, response, languagesAllocatorDnD, silent);
        BiConsumer<ChatSession, String> age = (cs, response) -> PlayerDnDGenerator.ageSetter(cs, response, silent);
        BiConsumer<ChatSession, String> height = (cs, response) -> PlayerDnDGenerator.heightSetter(cs, response, silent);
        BiConsumer<ChatSession, String> weight = (cs, response) -> PlayerDnDGenerator.weightSetter(cs, response, silent);
        BiConsumer<ChatSession, String> eyes = (cs, response) -> PlayerDnDGenerator.eyesSetter(cs, response, silent);
        BiConsumer<ChatSession, String> skin = (cs, response) -> PlayerDnDGenerator.skinSetter(cs, response, silent);
        BiConsumer<ChatSession, String> hair = (cs, response) -> PlayerDnDGenerator.hairSetter(cs, response, silent);

        playerDnDGeneratorAllocator.put(NAME, name);
        playerDnDGeneratorAllocator.put(RACE, race);
        playerDnDGeneratorAllocator.put(RACE_PERSONALITY, racePersonality);
        playerDnDGeneratorAllocator.put(RACE_IDEAL, raceIdeal);
        playerDnDGeneratorAllocator.put(RACE_BOND, raceBond);
        playerDnDGeneratorAllocator.put(RACE_FLAW, raceFlaw);
        playerDnDGeneratorAllocator.put(JOB, job);
        playerDnDGeneratorAllocator.put(BACKGROUND, background);
        playerDnDGeneratorAllocator.put(BACKGROUND_SPECIAL_INFO, backgroundSpecialInfo);
        playerDnDGeneratorAllocator.put(BACKGROUND_PERSONALITY, backgroundPersonality);
        playerDnDGeneratorAllocator.put(BACKGROUND_IDEAL, backgroundIdeal);
        playerDnDGeneratorAllocator.put(BACKGROUND_BOND, backgroundBond);
        playerDnDGeneratorAllocator.put(BACKGROUND_FLAW, backgroundFlaw);
        playerDnDGeneratorAllocator.put(ALIGNMENT, alignment);
        playerDnDGeneratorAllocator.put(STATS1, stats);
        playerDnDGeneratorAllocator.put(STATS2, stats);
        playerDnDGeneratorAllocator.put(STATS3, stats);
        playerDnDGeneratorAllocator.put(STATS4, stats);
        playerDnDGeneratorAllocator.put(STATS5, stats);
        playerDnDGeneratorAllocator.put(STATS6, stats);
        playerDnDGeneratorAllocator.put(SKILLS, skill);
        playerDnDGeneratorAllocator.put(LANGUAGE, language);
        playerDnDGeneratorAllocator.put(AGE, age);
        playerDnDGeneratorAllocator.put(HEIGHT, height);
        playerDnDGeneratorAllocator.put(WEIGHT, weight);
        playerDnDGeneratorAllocator.put(EYES, eyes);
        playerDnDGeneratorAllocator.put(SKIN, skin);
        playerDnDGeneratorAllocator.put(HAIR, hair);
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

    public Ability moveToDMBoard() {
        Consumer<MessageContext> dmKeyboard =
                ctx -> patternExecute(new ChatSession(ctx), Constants.CHANGE_TO_DM_KEYBOARD, KeyboardFactory.dmSetOfCommandsBoard(), false);

        return Ability
                .builder()
                .name("dmboard")
                .info("shows a dm board")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(dmKeyboard)
                .build();
    }

    public Ability moveToCampaignBoard() {
        Consumer<MessageContext> campaignKeyboard =
                ctx -> patternExecute(new ChatSession(ctx), Constants.CHANGE_TO_CAMPAIGN_KEYBOARD, KeyboardFactory.campaignSettingsBoard(), false);

        return Ability
                .builder()
                .name("campaignsettings")
                .info("shows a campaign board")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(campaignKeyboard)
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

    public Ability showCampaigns() {
        Consumer<MessageContext> campaigns = this::showCampaigns;

        return Ability
                .builder()
                .name("showcampaigns")
                .info("shows your campaigns")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(campaigns)
                .build();
    }

    public Ability setCampaign() {
        Consumer<MessageContext> campaign = this::setCurrentCampaign;

        return Ability
                .builder()
                .name("setcampaign")
                .info("sets current campaign")
                .input(1)
                .locality(USER)
                .privacy(PUBLIC)
                .build();
    }

    public Ability setCampaignName() {
        Consumer<MessageContext> campaignName = this::setCampaignName;

        return Ability
                .builder()
                .name("setcampaignname")
                .info("sets a campaign name")
                .input(1)
                .locality(USER)
                .privacy(PUBLIC)
                .action(campaignName)
                .build();
    }

    public Ability setPassword() {
        Consumer<MessageContext> password = this::setPassword;

        return Ability
                .builder()
                .name("setpassword")
                .info("sets a campaign password")
                .input(1)
                .locality(USER)
                .privacy(PUBLIC)
                .action(password)
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

        else if (currentUser.creationOfPlayerDnD && !currentUser.haltCreation) {
            String response;
            try {
                if (update.hasMessage() && update.getMessage().hasText()) {
                    response = update.getMessage().getText();
                    playerDnDGeneratorAllocator.get(currentUser.creationStage).accept(currentUser, response);

                } else if (update.hasCallbackQuery()) {
                    response = update.getCallbackQuery().getData();
                    playerDnDGeneratorAllocator.get(currentUser.creationStage).accept(currentUser, response);

                } else {
                    patternExecute(currentUser, "А? Если хотите сделать что-то другое, используйте /haltcreation", null, false);
                }
            } catch (Exception e) {
                patternExecute(currentUser, "А? Если хотите сделать что-то другое, используйте /haltcreation", null, false);
            }
        }

        else if (update.hasCallbackQuery() && Objects.equals(currentUser.getChatId(), getChatId(update))) {
            CallbackQuery query = update.getCallbackQuery();
            String responseQuery = query.getData();

            currentUser.setUsername(query.getFrom().getUserName());

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
            else {
                methodsAllocator.get(responseQuery).accept(currentUser);
            }
            UserDataHandler.saveSession(currentUser);
        }

        else if (update.hasMessage() && update.getMessage().hasText() && !update.getMessage().isCommand() && Objects.equals(currentUser.getChatId(), getChatId(update))) {
            currentUser.setUsername(update.getMessage().getForwardSenderName());

            if (currentUser.campaignNameIsChosen) {
                currentUser.activeDm.campaignName = update.getMessage().getText();
                currentUser.campaignNameIsChosen = false;

                ChatSession dungeonMaster = UserDataHandler.readSession(currentUser.activeDm.chatId);
                dungeonMaster.campaigns.put(currentUser.activeDm.campaignName, currentUser.activeDm.chatId);
                UserDataHandler.saveSession(dungeonMaster);

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
        }
    }
}