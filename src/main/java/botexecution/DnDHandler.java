package botexecution;

import common.Constants;
import common.DiceNew;
import common.UserDataHandler;
import dnd.characteristics.BackgroundDnD;
import dnd.characteristics.JobDnD;
import dnd.characteristics.RaceDnD;
import dnd.characteristics.backgroundsdnd.*;
import dnd.characteristics.jobsdnd.*;
import dnd.characteristics.racesdnd.*;
import dnd.mainobjects.DungeonMasterDnD;
import dnd.mainobjects.PlayerDnD;
import dnd.values.PlayerDnDCreationStage;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static dnd.values.PlayerDnDCreationStage.*;

public class DnDHandler {
    private TextHandler walkieTalkie;

    public void createCampaign(MessageContext ctx) {
        long userChatId = UserDataHandler.findChatId("@" + ctx.user().getUserName());
        ChatSession currentUser = UserDataHandler.readSession(userChatId);
        ChatSession currentGroup = UserDataHandler.readSession(ctx.update());
        currentGroup.username = "@" + ctx.user().getUserName();

        if (currentGroup.isHavingACampaign) {
            walkieTalkie.patternExecute(currentGroup, Constants.CAMPAIGN_CREATION_EXISTS, null, false);
            return;
        }

        currentGroup.isHavingACampaign = true;
        currentGroup.isEndingACampaign = false;

        walkieTalkie.patternExecute(currentGroup, Constants.CAMPAIGN_CREATION_START, null, false);

        currentGroup.campaignNameIsChosen = true;
        currentGroup.activeDm = new DungeonMasterDnD();
        currentGroup.activeDm.chatId = currentUser.getChatId();
        currentGroup.activeDm.username = currentUser.username;

        UserDataHandler.saveSession(currentGroup);
    }

    public void endCampaign(MessageContext ctx) {
        ChatSession currentGroup = UserDataHandler.readSession(ctx.chatId());

        if (currentGroup.activeDm.username.contains(ctx.user().getUserName())) {
            currentGroup.isEndingACampaign = true;
            walkieTalkie.patternExecute(currentGroup, Constants.CAMPAIGN_END_AFFIRMATION, KeyboardFactory.YesOrNoBoard(), false);
        }
        else {
            walkieTalkie.patternExecute(currentGroup, Constants.CAMPAIGN_END_RESTRICTION, null, false);
        }
    }

    public void showCampaigns(MessageContext ctx) {
        ChatSession currentUser = UserDataHandler.readSession(ctx.update());

        if (currentUser.campaigns.isEmpty()) {
            walkieTalkie.patternExecute(currentUser, Constants.SHOW_CAMPAIGNS_NOTHING, null, false);
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

        walkieTalkie.articleMessaging(campaigns, currentUser);
    }

    public void setCurrentCampaign(MessageContext ctx) {
        ChatSession currentUser = UserDataHandler.readSession(ctx.update());

        try {
            currentUser.currentCampaign = currentUser.campaigns.get(ctx.firstArg());
            currentUser.currentPlayer = null;
            currentUser.editCurrentPlayer = false;
            walkieTalkie.patternExecute(currentUser, "Компания "
                    + currentUser.currentCampaign
                    + " была установлена как основная. Теперь вы можете менять её параметры и добавлять свои предметы.",
                    null, false);
        } catch (Exception e) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NOT_FOUND, null, false);
        }

        UserDataHandler.saveSession(currentUser);
    }

    public void setCampaignName(MessageContext ctx) {
        ChatSession currentUser = UserDataHandler.readSession(ctx.update());
        ChatSession currentCampaign;
        try {
            currentCampaign = UserDataHandler.readSession(currentUser.currentCampaign);
        } catch (Exception e) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }

        currentUser.campaigns.remove(currentCampaign.activeDm.campaignName);
        currentCampaign.activeDm.campaignName = ctx.firstArg();
        currentUser.campaigns.put(currentCampaign.activeDm.campaignName, currentUser.currentCampaign);

        walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_SUCCESS, null, false);

        UserDataHandler.saveSession(currentCampaign);
        UserDataHandler.saveSession(currentUser);
    }

    public void setPassword(MessageContext ctx) {
        ChatSession currentUser = UserDataHandler.readSession(ctx.update());
        ChatSession currentCampaign;
        try {
            currentCampaign = UserDataHandler.readSession(currentUser.currentCampaign);
        } catch (Exception e) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }

        currentCampaign.activeDm.password = ctx.firstArg();
        walkieTalkie.patternExecute(currentUser, Constants.SET_PASSWORD_SUCCESS, null, false);

        UserDataHandler.saveSession(currentCampaign);
        UserDataHandler.saveSession(currentUser);
    }

    public void setMulticlassLimit(MessageContext ctx) {
        ChatSession currentUser = UserDataHandler.readSession(ctx.update());
        ChatSession currentCampaign;
        try {
            currentCampaign = UserDataHandler.readSession(currentUser.currentCampaign);
        } catch (Exception e) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }

        try {
            currentCampaign.activeDm.multiclassLimit = Integer.parseInt(ctx.firstArg());
        } catch (NumberFormatException e) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_MULTICLASS_LIMIT_NOT_INTEGER, null, false);
            return;
        }
        walkieTalkie.patternExecute(currentUser, "Лимит классов на персонажа установлен. Теперь максимальное количество классов - "
                + currentCampaign.activeDm.multiclassLimit, null, false);
        walkieTalkie.patternExecute(currentUser, Constants.SET_MULTICLASS_LIMIT_ZERO, null, false);

        UserDataHandler.saveSession(currentCampaign);
        UserDataHandler.saveSession(currentUser);
    }

    public void showPlayers(MessageContext ctx) {
        ChatSession currentUser = UserDataHandler.readSession(ctx.update());
        ChatSession currentCampaign;
        try {
            currentCampaign = UserDataHandler.readSession(currentUser.currentCampaign);
        } catch (Exception e) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }

        StringBuilder playersList = new StringBuilder();
        playersList.append("Игроки текущей компании: \n");
        int index = 1;
        for (Map.Entry<String, PlayerDnD> player : currentCampaign.activeDm.playerDnDHashMap.entrySet()) {
            playersList.append(index).append(". ").append(player.getKey()).append(" - ").append(player.getValue().name);
            index++;
        }

        walkieTalkie.patternExecute(currentUser, playersList.toString(), null, false);
    }

    public void addAPlayerDnD(MessageContext ctx) {
        long userChatId = UserDataHandler.findChatId("@" + ctx.user().getUserName());
        ChatSession currentUser = UserDataHandler.readSession(userChatId);
        ChatSession currentGroup = UserDataHandler.readSession(ctx.update());
        currentGroup.username = "@" + ctx.user().getUserName();

        walkieTalkie.patternExecute(currentGroup, Constants.PLAYER_CREATION_START, null, false);
        currentGroup.activeDm.playerDnDHashMap.put(currentGroup.username, new PlayerDnD());

        currentUser.activePc = new PlayerDnD();
        currentUser.activePc.playerName = currentGroup.username;
        currentUser.activePc.campaignChatId = currentGroup.getChatId();
        currentUser.creationOfPlayerDnD = true;
        walkieTalkie.patternExecute(currentUser, Constants.PLAYER_CREATION_NAME, null, false);


        UserDataHandler.saveSession(currentGroup);
        UserDataHandler.saveSession(currentUser);
    }

    public void haltCreationOfPlayerDnD(MessageContext ctx) {
        ChatSession currentUser = UserDataHandler.readSession(ctx.update());
        currentUser.creationOfPlayerDnD = !currentUser.creationOfPlayerDnD;

        if (!currentUser.creationOfPlayerDnD) {
            walkieTalkie.patternExecute(currentUser, Constants.PLAYER_CREATION_PAUSE, null, false);
        }
        else {
            currentUser.haltCreation = true;
            walkieTalkie.patternExecute(currentUser, Constants.PLAYER_CREATION_CONTINUE, null, false);
            playerDnDGeneratorAllocator.get(currentUser.creationStage).accept(currentUser, "");

        }

        UserDataHandler.saveSession(currentUser);
    }

    //генератор персонажа
    public final HashMap<PlayerDnDCreationStage, BiConsumer<ChatSession, String>> playerDnDGeneratorAllocator = new HashMap<>();
    private final HashMap<String, RaceDnD> raceDnDAllocator = new HashMap<>();
    private final HashMap<String, JobDnD> jobDnDAllocator = new HashMap<>();
    private final HashMap<String, BackgroundDnD> backgroundDnDAllocator = new HashMap<>();
    private final HashMap<String, BiConsumer<PlayerDnD, Integer>> statAllocatorDnD = new HashMap<>();
    private final HashMap<String, Consumer<PlayerDnD>> skillAllocatorDnD = new HashMap<>();
    private final HashMap<String, Consumer<PlayerDnD>> languagesAllocatorDnD = new HashMap<>();

    public DnDHandler(TextHandler walkieTalkie) {
        this.walkieTalkie = walkieTalkie;

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

        playerDnDGeneratorAllocator.put(NAME, this::nameSetter);
        playerDnDGeneratorAllocator.put(RACE, this::raceSetter);
        playerDnDGeneratorAllocator.put(RACE_PERSONALITY, this::racePersonalitySetter);
        playerDnDGeneratorAllocator.put(RACE_IDEAL, this::raceIdealSetter);
        playerDnDGeneratorAllocator.put(RACE_BOND, this::raceBondSetter);
        playerDnDGeneratorAllocator.put(RACE_FLAW, this::raceFlawSetter);
        playerDnDGeneratorAllocator.put(JOB, this::jobSetter);
        playerDnDGeneratorAllocator.put(BACKGROUND, this::backgroundSetter);
        playerDnDGeneratorAllocator.put(BACKGROUND_SPECIAL_INFO, this::backgroundSpecialInfoSetter);
        playerDnDGeneratorAllocator.put(BACKGROUND_PERSONALITY, this::backgroundPersonalitySetter);
        playerDnDGeneratorAllocator.put(BACKGROUND_IDEAL, this::backgroundIdealSetter);
        playerDnDGeneratorAllocator.put(BACKGROUND_BOND, this::backgroundBondSetter);
        playerDnDGeneratorAllocator.put(BACKGROUND_FLAW, this::backgroundFlawSetter);
        playerDnDGeneratorAllocator.put(ALIGNMENT, this::alignmentSetter);
        playerDnDGeneratorAllocator.put(STATS1, this::statSetter);
        playerDnDGeneratorAllocator.put(STATS2, this::statSetter);
        playerDnDGeneratorAllocator.put(STATS3, this::statSetter);
        playerDnDGeneratorAllocator.put(STATS4, this::statSetter);
        playerDnDGeneratorAllocator.put(STATS5, this::statSetter);
        playerDnDGeneratorAllocator.put(STATS6, this::statSetter);
        playerDnDGeneratorAllocator.put(SKILLS, this::skillsSetter);
        playerDnDGeneratorAllocator.put(LANGUAGE, this::languageSetter);
        playerDnDGeneratorAllocator.put(AGE, this::ageSetter);
        playerDnDGeneratorAllocator.put(HEIGHT, this::heightSetter);
        playerDnDGeneratorAllocator.put(WEIGHT, this::weightSetter);
        playerDnDGeneratorAllocator.put(EYES, this::eyesSetter);
        playerDnDGeneratorAllocator.put(SKIN, this::skinSetter);
        playerDnDGeneratorAllocator.put(HAIR, this::hairSetter);
    }

    public void nameSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, Constants.PLAYER_CREATION_NAME, null, false);
            return;
        }
        cs.activePc.name = response;
        walkieTalkie.patternExecute(cs, "Выберите расу из предложенных.", KeyboardFactory.raceDnDSelectionBoard(), false);

        cs.creationStage = RACE;
    }

    public void raceSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Выберите расу из предложенных.", KeyboardFactory.raceDnDSelectionBoard(), false);
            return;
        }
        cs.activePc.race = this.raceDnDAllocator.get(response);
        cs.activePc.size = cs.activePc.race.size;
        cs.activePc.speed = cs.activePc.race.walkingSpeed;
        cs.activePc.knownLanguages.addAll(cs.activePc.race.languages);
        cs.activePc.knownScripts.addAll(cs.activePc.race.scripts);
        cs.activePc.bonusLanguages = cs.activePc.bonusLanguages + cs.activePc.race.bonusLanguages;

        if (cs.activePc.race.personality.isEmpty()) {
            walkieTalkie.patternExecute(cs, "Выберите класс из предложенных.", KeyboardFactory.jobDnDSelectionBoard(), false);

            cs.creationStage = JOB;
        } else {
            walkieTalkie.patternExecute(cs, "Выберите черту характера персонажа: \n"
                            + walkieTalkie.variantsMessageConfigurator(cs.activePc.race.personality),
                    KeyboardFactory.variantsBoard(cs.activePc.race.personality), false);

            cs.creationStage = RACE_PERSONALITY;
        }
    }

    public void racePersonalitySetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Выберите черту характера персонажа: \n"
                            + walkieTalkie.variantsMessageConfigurator(cs.activePc.race.personality),
                    KeyboardFactory.variantsBoard(cs.activePc.race.personality), false);
            return;
        }
        cs.activePc.personality.put(cs.activePc.race.name, response);

        walkieTalkie.patternExecute(cs, "Выберите идеал персонажа: \n"
                        + walkieTalkie.variantsMessageConfigurator(cs.activePc.race.ideal),
                KeyboardFactory.variantsBoard(cs.activePc.race.ideal), false);

        cs.creationStage = RACE_IDEAL;
    }

    public void raceIdealSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Выберите идеал персонажа: \n"
                        + walkieTalkie.variantsMessageConfigurator(cs.activePc.race.ideal),
                KeyboardFactory.variantsBoard(cs.activePc.race.ideal), false);
            return;
        }
        cs.activePc.ideals.put(cs.activePc.race.name, response);

        walkieTalkie.patternExecute(cs, "Выберите привязанность персонажа: \n"
                        + walkieTalkie.variantsMessageConfigurator(cs.activePc.race.bond),
                KeyboardFactory.variantsBoard(cs.activePc.race.bond), false);

        cs.creationStage = RACE_BOND;
    }

    public void raceBondSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Выберите привязанность персонажа: \n"
                        + walkieTalkie.variantsMessageConfigurator(cs.activePc.race.bond),
                KeyboardFactory.variantsBoard(cs.activePc.race.bond), false);
            return;
        }
        cs.activePc.bonds.put(cs.activePc.race.name, response);

        walkieTalkie.patternExecute(cs, "Выберите слабость персонажа: \n"
                        + walkieTalkie.variantsMessageConfigurator(cs.activePc.race.flaw),
                KeyboardFactory.variantsBoard(cs.activePc.race.flaw), false);

        cs.creationStage = RACE_FLAW;
    }

    public void raceFlawSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Выберите слабость персонажа: \n"
                        + walkieTalkie.variantsMessageConfigurator(cs.activePc.race.flaw),
                KeyboardFactory.variantsBoard(cs.activePc.race.flaw), false);
            return;
        }
        cs.activePc.flaws.put(cs.activePc.race.name, response);

        walkieTalkie.patternExecute(cs, "Выберите класс из предложенных.", KeyboardFactory.jobDnDSelectionBoard(), false);

        cs.creationStage = JOB;
    }

    public void jobSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Выберите класс из предложенных.", KeyboardFactory.jobDnDSelectionBoard(), false);
            return;
        }
        cs.activePc.mainJob = this.jobDnDAllocator.get(response);

        cs.activePc.initBookOfSpellsDnD();
        cs.activePc.initStartHealth();

        walkieTalkie.patternExecute(cs, "Выберите предысторию из предложенных.", KeyboardFactory.backgroundDnDSelectionBoard(), false);

        cs.creationStage = BACKGROUND;
    }

    public void backgroundSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Выберите предысторию из предложенных.", KeyboardFactory.backgroundDnDSelectionBoard(), false);
            return;
        }
        cs.activePc.background = this.backgroundDnDAllocator.get(response);
        cs.activePc.knownLanguages.addAll(cs.activePc.background.languages);
        cs.activePc.knownScripts.addAll(cs.activePc.background.scripts);
        cs.activePc.bonusLanguages = cs.activePc.bonusLanguages + cs.activePc.background.bonusLanguages;

        for (String skill : cs.activePc.background.learnedSkills) {
            this.skillAllocatorDnD.get(skill).accept(cs.activePc);
            cs.activePc.learnedSkills.add(skill);
        }

        if (cs.activePc.background.personality.isEmpty()) {
            walkieTalkie.patternExecute(cs, "Выберите свое мировоззрение:", KeyboardFactory.alignmentDnDSelectionBoard(), false);

            cs.creationStage = ALIGNMENT;

        } else if (!cs.activePc.background.specialInfo.isEmpty()) {
            walkieTalkie.patternExecute(cs, "Выберите специальное качество предыстории персонажа: \n"
                            + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.specialInfo),
                    KeyboardFactory.variantsBoard(cs.activePc.background.specialInfo), false);

            cs.creationStage = BACKGROUND_SPECIAL_INFO;

        } else {
            walkieTalkie.patternExecute(cs, "Выберите черту характера персонажа: \n"
                            + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.personality),
                    KeyboardFactory.variantsBoard(cs.activePc.background.personality), false);

            cs.creationStage = BACKGROUND_PERSONALITY;
        }
    }

    public void backgroundSpecialInfoSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Выберите специальное качество предыстории персонажа: \n"
                            + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.specialInfo),
                    KeyboardFactory.variantsBoard(cs.activePc.background.specialInfo), false);
            return;
        }
        cs.activePc.specialBackgroundQuality = response;

        walkieTalkie.patternExecute(cs, "Выберите черту характера персонажа: \n"
                        + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.personality),
                KeyboardFactory.variantsBoard(cs.activePc.background.personality), false);

        cs.creationStage = BACKGROUND_PERSONALITY;
    }

    public void backgroundPersonalitySetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Выберите черту характера персонажа: \n"
                            + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.personality),
                    KeyboardFactory.variantsBoard(cs.activePc.background.personality), false);
            return;
        }
        cs.activePc.personality.put(cs.activePc.background.name, response);

        walkieTalkie.patternExecute(cs, "Выберите идеал персонажа: \n"
                        + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.ideal),
                KeyboardFactory.variantsBoard(cs.activePc.background.ideal), false);

        cs.creationStage = BACKGROUND_IDEAL;
    }

    public void backgroundIdealSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Выберите идеал персонажа: \n"
                        + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.ideal),
                KeyboardFactory.variantsBoard(cs.activePc.background.ideal), false);
            return;
        }
        cs.activePc.ideals.put(cs.activePc.background.name, response);

        walkieTalkie.patternExecute(cs, "Выберите привязанность персонажа: \n"
                        + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.bond),
                KeyboardFactory.variantsBoard(cs.activePc.background.bond), false);

        cs.creationStage = BACKGROUND_BOND;
    }

    public void backgroundBondSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Выберите привязанность персонажа: \n"
                        + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.bond),
                KeyboardFactory.variantsBoard(cs.activePc.background.bond), false);
            return;
        }
        cs.activePc.bonds.put(cs.activePc.background.name, response);

        walkieTalkie.patternExecute(cs, "Выберите слабость персонажа: \n"
                        + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.flaw),
                KeyboardFactory.variantsBoard(cs.activePc.background.flaw), false);

        cs.creationStage = BACKGROUND_FLAW;
    }

    public void backgroundFlawSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Выберите слабость персонажа: \n"
                        + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.flaw),
                KeyboardFactory.variantsBoard(cs.activePc.background.flaw), false);
            return;
        }
        cs.activePc.flaws.put(cs.activePc.background.name, response);

        walkieTalkie.patternExecute(cs, "Выберите свое мировоззрение:", KeyboardFactory.alignmentDnDSelectionBoard(), false);
        cs.creationStage = ALIGNMENT;
    }

    public void alignmentSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Выберите свое мировоззрение:", KeyboardFactory.alignmentDnDSelectionBoard(), false);
            return;
        }
        cs.activePc.alignment = response;

        walkieTalkie.patternExecute(cs, "Распределите характеристики.", null, false);

        cs.activePc.luck = DiceNew.D6FourTimesCreation();
        walkieTalkie.patternExecute(cs, DiceNew.D6FourTimes(cs.activePc.luck), KeyboardFactory.assignStatsBoardDnD(cs.activePc.allocatedStats), false);

        cs.creationStage = STATS1;
    }

    public void statSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            cs.activePc.luck = DiceNew.D6FourTimesCreation();
            walkieTalkie.patternExecute(cs, DiceNew.D6FourTimes(cs.activePc.luck), KeyboardFactory.assignStatsBoardDnD(cs.activePc.allocatedStats), false);
            return;
        }
        this.statAllocatorDnD.get(response).accept(cs.activePc, cs.activePc.luck.get(4));
        if (cs.creationStage == STATS6) {
            cs.activePc.allocatedStats.clear();
            cs.activePc.luck.clear();

            walkieTalkie.patternExecute(cs, "Выберите навыки персонажа. Осталось навыков: " + cs.activePc.mainJob.startingSkillAmount,
                    KeyboardFactory.assignSkillsBoardDnD(cs.activePc.mainJob.skillRoster, cs.activePc.learnedSkills), false);
            cs.creationStage = SKILLS;
        }

        cs.activePc.allocatedStats.add(response);
        cs.activePc.luck = DiceNew.D6FourTimesCreation();

        walkieTalkie.patternExecute(cs, DiceNew.D6FourTimes(cs.activePc.luck), KeyboardFactory.assignStatsBoardDnD(cs.activePc.allocatedStats), false);
        cs.creationStage = cs.creationStage.next();
    }

    public void skillsSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Выберите навыки персонажа. Осталось навыков: " + cs.activePc.mainJob.startingSkillAmount,
                    KeyboardFactory.assignSkillsBoardDnD(cs.activePc.mainJob.skillRoster, cs.activePc.learnedSkills), false);
            return;
        }
        this.skillAllocatorDnD.get(response).accept(cs.activePc);
        cs.activePc.learnedSkills.add(response);
        cs.activePc.mainJob.startingSkillAmount--;

        if (cs.activePc.mainJob.startingSkillAmount == 0) {
            walkieTalkie.patternExecute(cs, "Выберите языки персонажа. Осталось языков: " + cs.activePc.bonusLanguages,
                    KeyboardFactory.languagesDnDSelectionBoard(cs.activePc.knownLanguages), false);
            cs.creationStage = LANGUAGE;
        } else {
            walkieTalkie.patternExecute(cs, "Осталось навыков: " + cs.activePc.mainJob.startingSkillAmount,
                    KeyboardFactory.assignSkillsBoardDnD(cs.activePc.mainJob.skillRoster, cs.activePc.learnedSkills), false);
        }
    }

    public void languageSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Выберите языки персонажа. Осталось языков: " + cs.activePc.bonusLanguages,
                    KeyboardFactory.languagesDnDSelectionBoard(cs.activePc.knownLanguages), false);
            return;
        }
        this.languagesAllocatorDnD.get(response).accept(cs.activePc);
        cs.activePc.bonusLanguages--;

        if (cs.activePc.bonusLanguages == 0) {
            walkieTalkie.patternExecute(cs, "Введите возраст персонажа.", null, false);
            cs.creationStage = AGE;
        } else {
            walkieTalkie.patternExecute(cs, "Осталось языков: " + cs.activePc.bonusLanguages,
                    KeyboardFactory.languagesDnDSelectionBoard(cs.activePc.knownLanguages), false);
        }
    }

    public void ageSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Введите возраст персонажа.", null, false);
            return;
        }
        cs.activePc.age = response;
        walkieTalkie.patternExecute(cs, "Введите рост персонажа.", null, false);

        cs.creationStage = HEIGHT;
    }

    public void heightSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Введите рост персонажа.", null, false);
            return;
        }
        cs.activePc.height = response;
        walkieTalkie.patternExecute(cs, "Введите вес персонажа.", null, false);

        cs.creationStage = WEIGHT;
    }

    public void weightSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Введите вес персонажа.", null, false);
            return;
        }
        cs.activePc.weight = response;
        walkieTalkie.patternExecute(cs, "Введите описание глаз персонажа.", null, false);

        cs.creationStage = EYES;
    }

    public void eyesSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Введите описание глаз персонажа.", null, false);
            return;
        }
        cs.activePc.eyes = response;
        walkieTalkie.patternExecute(cs, "Введите описание кожи персонажа.", null, false);

        cs.creationStage = SKIN;
    }

    public void skinSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Введите описание кожи персонажа.", null, false);
            return;
        }
        cs.activePc.skin = response;
        walkieTalkie.patternExecute(cs, "Введите описание волос персонажа.", null, false);

        cs.creationStage = HAIR;
    }

    public void hairSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Введите описание волос персонажа.", null, false);
            return;
        }
        cs.activePc.hair = response;
        ChatSession currentGroup = UserDataHandler.readSession(cs.activePc.campaignChatId);
        currentGroup.activeDm.playerDnDHashMap.put(cs.username, cs.activePc);

        cs.activePc = null;
        cs.creationOfPlayerDnD = false;
        cs.creationStage = NAME;

        UserDataHandler.saveSession(currentGroup); // небезопасная вещь, надо либо вывесить предупреждение (сделано), либо что-то с этим решить
        UserDataHandler.saveSession(cs);

        walkieTalkie.patternExecute(cs, Constants.PLAYER_CREATION_END, null, false);
    }


}