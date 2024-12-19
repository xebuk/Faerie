package botexecution.handlers.dndhandlers;

import botexecution.handlers.corehandlers.DataHandler;
import botexecution.handlers.DiceHandler;
import botexecution.handlers.corehandlers.TextHandler;
import botexecution.mainobjects.ChatSession;
import botexecution.commands.CurrentProcess;
import botexecution.mainobjects.KeyboardFactory;
import common.Constants;
import dnd.mainobjects.PlayerDnD;
import dnd.values.characteristicsvalues.BackgroundsDnD;
import dnd.values.characteristicsvalues.JobsDnD;
import dnd.values.PlayerDnDCreationStage;
import dnd.values.characteristicsvalues.RacesDnD;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static dnd.values.PlayerDnDCreationStage.*;
import static dnd.values.PlayerDnDCreationStage.NAME;

public class DnDPlayerHandler {
    private final DataHandler knowledge;
    private final TextHandler walkieTalkie;
    private final DiceHandler diceHoarder;

    public void addAPlayerDnD(MessageContext ctx) {
        String userChatId = knowledge.findChatId("@" + ctx.user().getUserName());
        ChatSession currentUser = knowledge.getSession(userChatId);
        ChatSession currentGroup = knowledge.getSession(ctx.chatId().toString());
        currentGroup.username = "@" + ctx.user().getUserName();

        if (currentUser == null) {
            walkieTalkie.patternExecute(currentGroup, "Перед созданием персонажа начните диалог с ботом.");
            return;
        }

        if (currentUser.currentContext != CurrentProcess.FREE) {
            walkieTalkie.patternExecute(currentGroup, "Закончите процесс внутри личной беседы с ботом перед созданием персонажа.");
            return;
        }

        if (currentGroup.activeDm == null) {
            walkieTalkie.patternExecute(currentGroup,
                    "Произошла ошибка - в данной группе нет компании.");
            return;
        }
//        if (Objects.equals(currentGroup.activeDm.dungeonMasterUsername, currentGroup.dungeonMasterUsername)) {
//            walkieTalkie.patternExecute(currentGroup,
//                    "Произошла ошибка - вы являетесь ДМ-ом этой группы.");
//            return;
//        }

        walkieTalkie.patternExecute(currentGroup, Constants.PLAYER_CREATION_START);
        currentGroup.activeDm.campaignParty.put(currentGroup.username, new PlayerDnD());
        currentUser.campaignsAsPlayer.put(currentGroup.activeDm.campaignName, currentGroup.getChatId());

        currentUser.activePc = new PlayerDnD();
        currentUser.activePc.playerName = currentGroup.username;
        currentUser.activePc.campaignChatId = currentGroup.getChatId();
        currentUser.currentContext = CurrentProcess.CREATING_A_CHARACTER_DND;
        walkieTalkie.patternExecute(currentUser, Constants.PLAYER_CREATION_NAME);

        knowledge.renewListChat(currentGroup);
        knowledge.renewListChat(currentUser);
    }

    public void haltCreationOfPlayerDnD(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        if (currentUser.currentContext == CurrentProcess.FREE && !currentUser.haltCreation) {
            walkieTalkie.patternExecute(currentUser, Constants.PLAYER_CREATION_RESTRICTED);
            return;
        }
        if (currentUser.currentContext == CurrentProcess.CREATING_A_CHARACTER_DND) {
            currentUser.currentContext = CurrentProcess.FREE;
            currentUser.haltCreation = true;
            walkieTalkie.patternExecute(currentUser, Constants.PLAYER_CREATION_PAUSE);
        }
        else {
            walkieTalkie.patternExecute(currentUser, Constants.PLAYER_CREATION_CONTINUE);
            playerDnDGeneratorAllocator.get(currentUser.creationStage).accept(currentUser, "");
        }

        knowledge.renewListChat(currentUser);
    }

    //генератор персонажа
    public final HashMap<PlayerDnDCreationStage, BiConsumer<ChatSession, String>> playerDnDGeneratorAllocator = new HashMap<>();
    private final HashMap<String, BiConsumer<PlayerDnD, Integer>> statAllocatorDnD = new HashMap<>();
    private final HashMap<String, Consumer<PlayerDnD>> skillAllocatorDnD = new HashMap<>();
    private final HashMap<String, Consumer<PlayerDnD>> languagesAllocatorDnD = new HashMap<>();

    public void nameSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, Constants.PLAYER_CREATION_NAME);
            return;
        }
        cs.activePc.name = response;
        walkieTalkie.patternExecute(cs, "Выберите расу из предложенных.", KeyboardFactory.raceDnDSelectionBoard(), false);

        cs.creationStage = RACE;
        knowledge.renewListChat(cs);
    }

    public void raceSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Выберите расу из предложенных.", KeyboardFactory.raceDnDSelectionBoard(), false);
            return;
        }
        cs.activePc.race = RacesDnD.getRace(response);
        cs.activePc.raceName = RacesDnD.getRaceAsClass(cs.activePc.race).name;
        cs.activePc.raceSubspeciesName = RacesDnD.getRaceAsClass(cs.activePc.race).subspeciesName;
        cs.activePc.size = RacesDnD.getRaceAsClass(cs.activePc.race).size;
        cs.activePc.walkingSpeed = RacesDnD.getRaceAsClass(cs.activePc.race).walkingSpeed;
        cs.activePc.speed = RacesDnD.getRaceAsClass(cs.activePc.race).walkingSpeed;

        cs.activePc.knownLanguages.addAll(RacesDnD.getRaceAsClass(cs.activePc.race).languages);
        cs.activePc.knownScripts.addAll(RacesDnD.getRaceAsClass(cs.activePc.race).scripts);
        cs.activePc.bonusLanguages = cs.activePc.bonusLanguages + RacesDnD.getRaceAsClass(cs.activePc.race).bonusLanguages;

        if (RacesDnD.getRaceAsClass(cs.activePc.race).personality.isEmpty()) {
            walkieTalkie.patternExecute(cs, "Выберите класс из предложенных.", KeyboardFactory.jobDnDSelectionBoard(), false);

            cs.creationStage = JOB;
            knowledge.renewListChat(cs);
        } else {
            String racePersonality = "Черты характера персонажа: \n" + walkieTalkie.variantsMessageConfigurator(
                    RacesDnD.getRaceAsClass(cs.activePc.race).personality);
            InlineKeyboardMarkup racePersonalityVariants = KeyboardFactory.variantsBoard(
                    RacesDnD.getRaceAsClass(cs.activePc.race).personality.size());
            walkieTalkie.articleMessaging(racePersonality, cs, racePersonalityVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", racePersonalityVariants, false);

            cs.creationStage = RACE_PERSONALITY;
            knowledge.renewListChat(cs);
        }
    }

    public void racePersonalitySetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String racePersonality = "Черты характера персонажа: \n" + walkieTalkie.variantsMessageConfigurator(
                    RacesDnD.getRaceAsClass(cs.activePc.race).personality);
            InlineKeyboardMarkup racePersonalityVariants = KeyboardFactory.variantsBoard(
                    RacesDnD.getRaceAsClass(cs.activePc.race).personality.size());
            walkieTalkie.articleMessaging(racePersonality, cs, racePersonalityVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", racePersonalityVariants, false);
            return;
        }
        cs.activePc.personality.put(RacesDnD.getRaceAsClass(cs.activePc.race).name,
                RacesDnD.getRaceAsClass(cs.activePc.race).personality.get(Integer.parseInt(response)));

        String raceIdeal = "Идеалы персонажа: \n" + walkieTalkie.variantsMessageConfigurator(
                RacesDnD.getRaceAsClass(cs.activePc.race).ideal);
        InlineKeyboardMarkup raceIdealVariants = KeyboardFactory.variantsBoard(
                RacesDnD.getRaceAsClass(cs.activePc.race).ideal.size());
        walkieTalkie.articleMessaging(raceIdeal, cs, raceIdealVariants);
        //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", raceIdealVariants, false);

        cs.creationStage = RACE_IDEAL;
        knowledge.renewListChat(cs);
    }

    public void raceIdealSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String raceIdeal = "Идеалы персонажа: \n" + walkieTalkie.variantsMessageConfigurator(
                    RacesDnD.getRaceAsClass(cs.activePc.race).ideal);
            InlineKeyboardMarkup raceIdealVariants = KeyboardFactory.variantsBoard(
                    RacesDnD.getRaceAsClass(cs.activePc.race).ideal.size());
            walkieTalkie.articleMessaging(raceIdeal, cs, raceIdealVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", raceIdealVariants, false);
            return;
        }
        cs.activePc.ideals.put(RacesDnD.getRaceAsClass(cs.activePc.race).name,
                RacesDnD.getRaceAsClass(cs.activePc.race).ideal.get(Integer.parseInt(response)));

        String raceBond = "Привязанности персонажа: \n" + walkieTalkie.variantsMessageConfigurator(
                RacesDnD.getRaceAsClass(cs.activePc.race).bond);
        InlineKeyboardMarkup raceBondVariants = KeyboardFactory.variantsBoard(
                RacesDnD.getRaceAsClass(cs.activePc.race).bond.size());
        walkieTalkie.articleMessaging(raceBond, cs, raceBondVariants);
        //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", raceBondVariants, false);

        cs.creationStage = RACE_BOND;
        knowledge.renewListChat(cs);
    }

    public void raceBondSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String raceBond = "Привязанности персонажа: \n" + walkieTalkie.variantsMessageConfigurator(
                    RacesDnD.getRaceAsClass(cs.activePc.race).bond);
            InlineKeyboardMarkup raceBondVariants = KeyboardFactory.variantsBoard(
                    RacesDnD.getRaceAsClass(cs.activePc.race).bond.size());
            walkieTalkie.articleMessaging(raceBond, cs, raceBondVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", raceBondVariants, false);
            return;
        }
        cs.activePc.bonds.put(RacesDnD.getRaceAsClass(cs.activePc.race).name,
                RacesDnD.getRaceAsClass(cs.activePc.race).bond.get(Integer.parseInt(response)));

        String raceFlaw = "Слабости персонажа: \n" + walkieTalkie.variantsMessageConfigurator(
                RacesDnD.getRaceAsClass(cs.activePc.race).flaw);
        InlineKeyboardMarkup raceFlawVariants = KeyboardFactory.variantsBoard(
                RacesDnD.getRaceAsClass(cs.activePc.race).flaw.size());
        walkieTalkie.articleMessaging(raceFlaw, cs, raceFlawVariants);
        //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", raceFlawVariants, false);

        cs.creationStage = RACE_FLAW;
        knowledge.renewListChat(cs);
    }

    public void raceFlawSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String raceFlaw = "Слабости персонажа: \n" + walkieTalkie.variantsMessageConfigurator(
                    RacesDnD.getRaceAsClass(cs.activePc.race).flaw);
            InlineKeyboardMarkup raceFlawVariants = KeyboardFactory.variantsBoard(
                    RacesDnD.getRaceAsClass(cs.activePc.race).flaw.size());
            walkieTalkie.articleMessaging(raceFlaw, cs, raceFlawVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", raceFlawVariants, false);
            return;
        }
        cs.activePc.flaws.put(RacesDnD.getRaceAsClass(cs.activePc.race).name,
                RacesDnD.getRaceAsClass(cs.activePc.race).flaw.get(Integer.parseInt(response)));

        walkieTalkie.patternExecute(cs, "Выберите класс из предложенных.", KeyboardFactory.jobDnDSelectionBoard(), false);

        cs.creationStage = JOB;
        knowledge.renewListChat(cs);
    }

    public void jobSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Выберите класс из предложенных.", KeyboardFactory.jobDnDSelectionBoard(), false);
            return;
        }
        cs.activePc.mainJob = JobsDnD.getJob(response);
        cs.activePc.mainJobTitle = response;
        cs.activePc.mainPrestigeJobTitle = "Не выбран";
        cs.activePc.mainJobLevel = 1;
        cs.activePc.characterMasteries.addAll(JobsDnD.getJobAsClass(cs.activePc.mainJob).saveMastery);
        cs.activePc.armorProficiency.addAll(JobsDnD.getJobAsClass(cs.activePc.mainJob).armorMastery);
        cs.activePc.freeSkillPoints += JobsDnD.getJobAsClass(cs.activePc.mainJob).startingSkillAmount;

        cs.activePc.initBookOfSpellsDnD();
        cs.activePc.initStartHealth();

        walkieTalkie.patternExecute(cs, "Выберите предысторию из предложенных.", KeyboardFactory.backgroundDnDSelectionBoard(), false);

        cs.creationStage = BACKGROUND;
        knowledge.renewListChat(cs);
    }

    public void backgroundSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Выберите предысторию из предложенных.", KeyboardFactory.backgroundDnDSelectionBoard(), false);
            return;
        }
        cs.activePc.background = BackgroundsDnD.getBackground(response);
        cs.activePc.backgroundName = BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).name;
        cs.activePc.specialAbility = BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).specialAbility;
        cs.activePc.specialAbilitySummary = BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).specialAbilitySummary;

        cs.activePc.knownLanguages.addAll(BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).languages);
        cs.activePc.knownScripts.addAll(BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).scripts);
        cs.activePc.bonusLanguages = cs.activePc.bonusLanguages + BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).bonusLanguages;

        for (String skill : BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).learnedSkills) {
            skillAllocatorDnD.get(skill).accept(cs.activePc);
            cs.activePc.learnedSkills.add(skill);
        }

        if (BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).personality.isEmpty()) {
            walkieTalkie.patternExecute(cs, "Выберите свое мировоззрение:", KeyboardFactory.alignmentDnDSelectionBoard(), false);

            cs.creationStage = ALIGNMENT;
            knowledge.renewListChat(cs);

        } else if (!BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).specialInfo.isEmpty()) {
            String specialInfo = "Специальные качества предыстории персонажа: \n" + walkieTalkie.variantsMessageConfigurator(
                    BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).specialInfo);
            InlineKeyboardMarkup specialInfoVariants = KeyboardFactory.variantsBoard(
                    BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).specialInfo.size());
            walkieTalkie.articleMessaging(specialInfo, cs, specialInfoVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", specialInfoVariants, false);

            cs.creationStage = BACKGROUND_SPECIAL_INFO;
            knowledge.renewListChat(cs);

        } else {
            String personality = "Черты характера персонажа: \n" + walkieTalkie.variantsMessageConfigurator(
                    BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).personality);
            InlineKeyboardMarkup personalityVariants = KeyboardFactory.variantsBoard(
                    BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).personality.size());
            walkieTalkie.articleMessaging(personality, cs, personalityVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", personalityVariants, false);

            cs.creationStage = BACKGROUND_PERSONALITY;
            knowledge.renewListChat(cs);
        }
    }

    public void backgroundSpecialInfoSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String specialInfo = "Специальные качества предыстории персонажа: \n" + walkieTalkie.variantsMessageConfigurator(
                    BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).specialInfo);
            InlineKeyboardMarkup specialInfoVariants = KeyboardFactory.variantsBoard(
                    BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).specialInfo.size());
            walkieTalkie.articleMessaging(specialInfo, cs, specialInfoVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", specialInfoVariants, false);
            return;
        }
        cs.activePc.specialBackgroundQuality = BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).specialInfo.get(Integer.parseInt(response));

        String personality = "Черты характера персонажа: \n" + walkieTalkie.variantsMessageConfigurator(
                BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).personality);
        InlineKeyboardMarkup personalityVariants = KeyboardFactory.variantsBoard(
                BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).personality.size());
        walkieTalkie.articleMessaging(personality, cs, personalityVariants);
        //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", personalityVariants, false);

        cs.creationStage = BACKGROUND_PERSONALITY;
        knowledge.renewListChat(cs);
    }

    public void backgroundPersonalitySetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String personality = "Черты характера персонажа: \n" + walkieTalkie.variantsMessageConfigurator(
                    BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).personality);
            InlineKeyboardMarkup personalityVariants = KeyboardFactory.variantsBoard(
                    BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).personality.size());
            walkieTalkie.articleMessaging(personality, cs, personalityVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", personalityVariants, false);
            return;
        }
        cs.activePc.personality.put(BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).name,
                BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).personality.get(Integer.parseInt(response)));

        String ideal = "Идеалы персонажа: \n" + walkieTalkie.variantsMessageConfigurator(
                BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).ideal);
        InlineKeyboardMarkup idealVariants = KeyboardFactory.variantsBoard(
                BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).ideal.size());
        walkieTalkie.articleMessaging(ideal, cs, idealVariants);
        //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", idealVariants, false);

        cs.creationStage = BACKGROUND_IDEAL;
        knowledge.renewListChat(cs);
    }

    public void backgroundIdealSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String ideal = "Идеалы персонажа: \n" + walkieTalkie.variantsMessageConfigurator(
                    BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).ideal);
            InlineKeyboardMarkup idealVariants = KeyboardFactory.variantsBoard(
                    BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).ideal.size());
            walkieTalkie.articleMessaging(ideal, cs, idealVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", idealVariants, false);
            return;
        }
        cs.activePc.ideals.put(BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).name,
                BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).ideal.get(Integer.parseInt(response)));

        String bond = "Привязанности персонажа: \n" + walkieTalkie.variantsMessageConfigurator(
                BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).bond);
        InlineKeyboardMarkup bondVariants = KeyboardFactory.variantsBoard(
                BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).bond.size());
        walkieTalkie.articleMessaging(bond, cs, bondVariants);
        //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", bondVariants, false);

        cs.creationStage = BACKGROUND_BOND;
        knowledge.renewListChat(cs);
    }

    public void backgroundBondSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String bond = "Привязанности персонажа: \n" + walkieTalkie.variantsMessageConfigurator(
                    BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).bond);
            InlineKeyboardMarkup bondVariants = KeyboardFactory.variantsBoard(
                    BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).bond.size());
            walkieTalkie.articleMessaging(bond, cs, bondVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", bondVariants, false);
            return;
        }
        cs.activePc.bonds.put(BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).name,
                BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).bond.get(Integer.parseInt(response)));

        String flaw = "Слабости персонажа: \n" + walkieTalkie.variantsMessageConfigurator(
                BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).flaw);
        InlineKeyboardMarkup flawVariants = KeyboardFactory.variantsBoard(
                BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).flaw.size());
        walkieTalkie.articleMessaging(flaw, cs, flawVariants);
        //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", flawVariants, false);

        cs.creationStage = BACKGROUND_FLAW;
        knowledge.renewListChat(cs);
    }

    public void backgroundFlawSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String flaw = "Слабости персонажа: \n" + walkieTalkie.variantsMessageConfigurator(
                    BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).flaw);
            InlineKeyboardMarkup flawVariants = KeyboardFactory.variantsBoard(
                    BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).flaw.size());
            walkieTalkie.articleMessaging(flaw, cs, flawVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", flawVariants, false);
            return;
        }
        cs.activePc.flaws.put(BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).name,
                BackgroundsDnD.getBackgroundAsClass(cs.activePc.background).flaw.get(Integer.parseInt(response)));

        walkieTalkie.patternExecute(cs, "Выберите свое мировоззрение:", KeyboardFactory.alignmentDnDSelectionBoard(), false);
        cs.creationStage = ALIGNMENT;
        knowledge.renewListChat(cs);
    }

    public void alignmentSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Выберите свое мировоззрение:", KeyboardFactory.alignmentDnDSelectionBoard(), false);
            return;
        }
        cs.activePc.alignment = response;

        walkieTalkie.patternExecute(cs, "Распределите характеристики.");

        cs.activePc.luck = diceHoarder.D6FourTimesCreation();
        walkieTalkie.patternExecute(cs, diceHoarder.D6FourTimes(cs.activePc.luck), KeyboardFactory.assignStatsBoardDnD(cs.activePc.allocatedStats), false);

        cs.creationStage = STATS1;
        knowledge.renewListChat(cs);
    }

    public void statSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            cs.activePc.luck = diceHoarder.D6FourTimesCreation();
            walkieTalkie.patternExecute(cs, diceHoarder.D6FourTimes(cs.activePc.luck), KeyboardFactory.assignStatsBoardDnD(cs.activePc.allocatedStats), false);
            return;
        }
        statAllocatorDnD.get(response).accept(cs.activePc, cs.activePc.luck.get(4));
        if (cs.creationStage == STATS6) {
            cs.activePc.allocatedStats.clear();
            cs.activePc.luck.clear();

            String skills = "Выберите навыки персонажа. Осталось навыков: " + cs.activePc.freeSkillPoints;
            InlineKeyboardMarkup skillsVariants = KeyboardFactory.assignSkillsBoardDnD(
                    JobsDnD.getJobAsClass(cs.activePc.mainJob).skillRoster, cs.activePc.learnedSkills);
            walkieTalkie.patternExecute(cs, skills, skillsVariants, false);
            cs.creationStage = SKILLS;
            knowledge.renewListChat(cs);
            return;
        }

        cs.activePc.allocatedStats.add(response);
        cs.activePc.luck = diceHoarder.D6FourTimesCreation();

        walkieTalkie.patternExecute(cs, diceHoarder.D6FourTimes(cs.activePc.luck), KeyboardFactory.assignStatsBoardDnD(cs.activePc.allocatedStats), false);
        cs.creationStage = cs.creationStage.next();
        knowledge.renewListChat(cs);
    }

    public void skillsSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String skills = "Выберите навыки персонажа. Осталось навыков: " + cs.activePc.freeSkillPoints;
            InlineKeyboardMarkup skillsVariants = KeyboardFactory.assignSkillsBoardDnD(
                    JobsDnD.getJobAsClass(cs.activePc.mainJob).skillRoster, cs.activePc.learnedSkills);
            walkieTalkie.patternExecute(cs, skills, skillsVariants, false);
            return;
        }
        skillAllocatorDnD.get(response).accept(cs.activePc);
        cs.activePc.learnedSkills.add(response);
        cs.activePc.freeSkillPoints--;

        if (cs.activePc.freeSkillPoints == 0 && cs.activePc.bonusLanguages > 0) {
            String languages = "Выберите языки персонажа. Осталось языков: " + cs.activePc.bonusLanguages;
            InlineKeyboardMarkup languagesVariants = KeyboardFactory.languagesDnDSelectionBoard(cs.activePc.knownLanguages);
            walkieTalkie.patternExecute(cs, languages, languagesVariants, false);
            cs.creationStage = LANGUAGE;
            knowledge.renewListChat(cs);
        }
        else if (cs.activePc.freeSkillPoints == 0) {
            walkieTalkie.patternExecute(cs, "Введите возраст персонажа.");
            cs.creationStage = AGE;
            knowledge.renewListChat(cs);
        }
        else {
            String skills = "Осталось навыков: " + cs.activePc.freeSkillPoints;
            InlineKeyboardMarkup skillsVariants = KeyboardFactory.assignSkillsBoardDnD(
                    JobsDnD.getJobAsClass(cs.activePc.mainJob).skillRoster, cs.activePc.learnedSkills);
            walkieTalkie.patternExecute(cs, skills, skillsVariants, false);
            knowledge.renewListChat(cs);
        }
    }

    public void languageSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String languages = "Выберите языки персонажа. Осталось языков: " + cs.activePc.bonusLanguages;
            InlineKeyboardMarkup languagesVariants = KeyboardFactory.languagesDnDSelectionBoard(cs.activePc.knownLanguages);
            walkieTalkie.patternExecute(cs, languages, languagesVariants, false);
            return;
        }
        languagesAllocatorDnD.get(response).accept(cs.activePc);
        cs.activePc.bonusLanguages--;

        if (cs.activePc.bonusLanguages <= 0) {
            walkieTalkie.patternExecute(cs, "Введите возраст персонажа.");
            cs.creationStage = AGE;
            knowledge.renewListChat(cs);
        } else {
            String languages = "Осталось языков: " + cs.activePc.bonusLanguages;
            InlineKeyboardMarkup languagesVariants = KeyboardFactory.languagesDnDSelectionBoard(cs.activePc.knownLanguages);
            walkieTalkie.patternExecute(cs, languages, languagesVariants, false);
            knowledge.renewListChat(cs);
        }
    }

    public void ageSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Введите возраст персонажа.");
            return;
        }
        cs.activePc.age = response;
        walkieTalkie.patternExecute(cs, "Введите рост персонажа.");

        cs.creationStage = HEIGHT;
        knowledge.renewListChat(cs);
    }

    public void heightSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Введите рост персонажа.");
            return;
        }
        cs.activePc.height = response;
        walkieTalkie.patternExecute(cs, "Введите вес персонажа.");

        cs.creationStage = WEIGHT;
        knowledge.renewListChat(cs);
    }

    public void weightSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Введите вес персонажа.");
            return;
        }
        cs.activePc.weight = response;
        walkieTalkie.patternExecute(cs, "Введите описание глаз персонажа.");

        cs.creationStage = EYES;
        knowledge.renewListChat(cs);
    }

    public void eyesSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Введите описание глаз персонажа.");
            return;
        }
        cs.activePc.eyes = response;
        walkieTalkie.patternExecute(cs, "Введите описание кожи персонажа.");

        cs.creationStage = SKIN;
        knowledge.renewListChat(cs);
    }

    public void skinSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Введите описание кожи персонажа.");
            return;
        }
        cs.activePc.skin = response;
        walkieTalkie.patternExecute(cs, "Введите описание волос персонажа.");

        cs.creationStage = HAIR;
        knowledge.renewListChat(cs);
    }

    public void hairSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Введите описание волос персонажа.");
            return;
        }
        cs.activePc.hair = response;
        inventoryRefusal(cs);
        ChatSession currentGroup = knowledge.getSession(cs.activePc.campaignChatId.toString());
        currentGroup.activeDm.campaignParty.put(cs.username, cs.activePc);

        cs.activePc = null;
        cs.currentContext = CurrentProcess.FREE;
        cs.creationStage = NAME;

        knowledge.renewListChat(currentGroup); // небезопасная вещь, надо либо вывесить предупреждение (сделано), либо что-то с этим решить
        knowledge.renewListChat(cs);

        walkieTalkie.patternExecute(cs, Constants.PLAYER_CREATION_END);
    }

    //секция для выбора предметов
    public void inventoryRefusal(ChatSession cs) {
        String refusalDice = JobsDnD.getJobAsClass(cs.activePc.mainJob).inventoryRefusalMoney;
        cs.activePc.itemCollectionOnHands.clear();
        cs.activePc.weaponCollectionOnHands.clear();
        cs.activePc.armorCollectionOnHands.clear();
        cs.activePc.instrumentsCollectionOnHands.clear();
        cs.activePc.kitCollectionOnHands.clear();

        cs.activePc.gold = diceHoarder.customDiceResult(refusalDice);
        if (cs.activePc.mainJob != JobsDnD.MONK) {
            cs.activePc.gold = cs.activePc.gold * 10;
        }

        walkieTalkie.patternExecute(cs, "Вы получили "
                + cs.activePc.gold
                + " золотых по причине отказа от снаряжения.");
    }

    public DnDPlayerHandler(DataHandler knowledge, TextHandler walkieTalkie, DiceHandler diceHoarder) {
        this.knowledge = knowledge;
        this.walkieTalkie = walkieTalkie;
        this.diceHoarder = diceHoarder;

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
}
