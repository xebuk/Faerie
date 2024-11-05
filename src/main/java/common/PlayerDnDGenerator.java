package common;

import botexecution.ChatSession;
import botexecution.KeyboardFactory;
import dnd.characteristics.BackgroundDnD;
import dnd.characteristics.JobDnD;
import dnd.characteristics.RaceDnD;
import dnd.mainobjects.PlayerDnD;
import org.telegram.telegrambots.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static dnd.values.PlayerDnDCreationStage.*;

public class PlayerDnDGenerator {

    private static void patternExecute(ChatSession cs, String message, ReplyKeyboard function, SilentSender silent) {
        StringBuilder sign = new StringBuilder();
        if (!cs.isPM()) {
            sign.append(cs.username).append("\n").append("----------------------------------").append("\n");
        }
        sign.append(message);
        SendMessage text = new SendMessage(cs.getChatId().toString(), sign.toString());
        if (function != null) {
            text.setReplyMarkup(function);
        }
        silent.execute(text);
    }

    private static String variantsMessageConfigurator(List<String> variants) {
        StringBuilder text = new StringBuilder();

        for (int i = 1; i < variants.size() + 1; i++) {
            text.append(i).append(". ").append(variants.get(i - 1)).append("\n\n");
        }

        return text.toString();
    }

    public static void nameSetter(ChatSession cs, String response, SilentSender silent) {
        cs.activePc.name = response;
        patternExecute(cs, "Выберите расу из предложенных.", KeyboardFactory.raceDnDSelectionBoard(), silent);

        cs.creationStage = RACE;
    }

    public static void raceSetter(ChatSession cs, String response, HashMap<String, RaceDnD> raceDnDAllocator, SilentSender silent) {
        cs.activePc.race = raceDnDAllocator.get(response);
        cs.activePc.size = cs.activePc.race.size;
        cs.activePc.speed = cs.activePc.race.walkingSpeed;
        cs.activePc.knownLanguages.addAll(cs.activePc.race.languages);
        cs.activePc.knownScripts.addAll(cs.activePc.race.scripts);
        cs.activePc.bonusLanguages = cs.activePc.bonusLanguages + cs.activePc.race.bonusLanguages;

        if (cs.activePc.race.personality.isEmpty()) {
            patternExecute(cs, "Выберите класс из предложенных.", KeyboardFactory.jobDnDSelectionBoard(), silent);

            cs.creationStage = JOB;
        } else {
            patternExecute(cs, "Выберите черту характера персонажа: \n"
                            + variantsMessageConfigurator(cs.activePc.race.personality),
                    KeyboardFactory.variantsBoard(cs.activePc.race.personality), silent);

            cs.creationStage = RACE_PERSONALITY;
        }
    }

    public static void racePersonalitySetter(ChatSession cs, String response, SilentSender silent) {
        cs.activePc.personality.put(cs.activePc.race.name, response);

        patternExecute(cs, "Выберите идеал персонажа: \n"
                        + variantsMessageConfigurator(cs.activePc.race.ideal),
                KeyboardFactory.variantsBoard(cs.activePc.race.ideal), silent);

        cs.creationStage = RACE_IDEAL;
    }

    public static void raceIdealSetter(ChatSession cs, String response, SilentSender silent) {
        cs.activePc.ideals.put(cs.activePc.race.name, response);

        patternExecute(cs, "Выберите привязанность персонажа: \n"
                        + variantsMessageConfigurator(cs.activePc.race.bond),
                KeyboardFactory.variantsBoard(cs.activePc.race.bond), silent);

        cs.creationStage = RACE_BOND;
    }

    public static void raceBondSetter(ChatSession cs, String response, SilentSender silent) {
        cs.activePc.bonds.put(cs.activePc.race.name, response);

        patternExecute(cs, "Выберите слабость персонажа: \n"
                        + variantsMessageConfigurator(cs.activePc.race.flaw),
                KeyboardFactory.variantsBoard(cs.activePc.race.flaw), silent);

        cs.creationStage = RACE_FLAW;
    }

    public static void raceFlawSetter(ChatSession cs, String response, SilentSender silent) {
        cs.activePc.flaws.put(cs.activePc.race.name, response);

        patternExecute(cs, "Выберите класс из предложенных.", KeyboardFactory.jobDnDSelectionBoard(), silent);

        cs.creationStage = JOB;
    }

    public static void jobSetter(ChatSession cs, String response, HashMap<String, JobDnD> jobDnDAllocator, SilentSender silent) {
        cs.activePc.mainJob = jobDnDAllocator.get(response);

        cs.activePc.initBookOfSpellsDnD();
        cs.activePc.initStartHealth();

        patternExecute(cs, "Выберите предысторию из предложенных.", KeyboardFactory.backgroundDnDSelectionBoard(), silent);

        cs.creationStage = BACKGROUND;
    }

    public static void backgroundSetter(ChatSession cs, String response, HashMap<String, BackgroundDnD> backgroundDnDAllocator, HashMap<String, Consumer<PlayerDnD>> skillAllocatorDnD, SilentSender silent) {
        cs.activePc.background = backgroundDnDAllocator.get(response);
        cs.activePc.knownLanguages.addAll(cs.activePc.background.languages);
        cs.activePc.knownScripts.addAll(cs.activePc.background.scripts);
        cs.activePc.bonusLanguages = cs.activePc.bonusLanguages + cs.activePc.background.bonusLanguages;

        for (String skill : cs.activePc.background.learnedSkills) {
            skillAllocatorDnD.get(skill).accept(cs.activePc);
            cs.activePc.learnedSkills.add(skill);
        }

        if (cs.activePc.background.personality.isEmpty()) {
            patternExecute(cs, "Выберите свое мировоззрение:", KeyboardFactory.alignmentDnDSelectionBoard(), silent);

            cs.creationStage = ALIGNMENT;

        } else if (!cs.activePc.background.specialInfo.isEmpty()) {
            patternExecute(cs, "Выберите специальное качество предыстории персонажа: \n"
                            + variantsMessageConfigurator(cs.activePc.background.specialInfo),
                    KeyboardFactory.variantsBoard(cs.activePc.background.specialInfo), silent);

            cs.creationStage = BACKGROUND_SPECIAL_INFO;

        } else {
            patternExecute(cs, "Выберите черту характера персонажа: \n"
                            + variantsMessageConfigurator(cs.activePc.background.personality),
                    KeyboardFactory.variantsBoard(cs.activePc.background.personality), silent);

            cs.creationStage = BACKGROUND_PERSONALITY;
        }
    }

    public static void backgroundSpecialInfoSetter(ChatSession cs, String response, SilentSender silent) {
        cs.activePc.specialBackgroundQuality = response;

        patternExecute(cs, "Выберите черту характера персонажа: \n"
                        + variantsMessageConfigurator(cs.activePc.background.personality),
                KeyboardFactory.variantsBoard(cs.activePc.background.personality), silent);

        cs.creationStage = BACKGROUND_PERSONALITY;
    }

    public static void backgroundPersonalitySetter(ChatSession cs, String response, SilentSender silent) {
        cs.activePc.personality.put(cs.activePc.background.name, response);

        patternExecute(cs, "Выберите идеал персонажа: \n"
                        + variantsMessageConfigurator(cs.activePc.background.ideal),
                KeyboardFactory.variantsBoard(cs.activePc.background.ideal), silent);

        cs.creationStage = BACKGROUND_IDEAL;
    }

    public static void backgroundIdealSetter(ChatSession cs, String response, SilentSender silent) {
        cs.activePc.ideals.put(cs.activePc.background.name, response);

        patternExecute(cs, "Выберите привязанность персонажа: \n"
                        + variantsMessageConfigurator(cs.activePc.background.bond),
                KeyboardFactory.variantsBoard(cs.activePc.background.bond), silent);

        cs.creationStage = BACKGROUND_BOND;
    }

    public static void backgroundBondSetter(ChatSession cs, String response, SilentSender silent) {
        cs.activePc.bonds.put(cs.activePc.background.name, response);

        patternExecute(cs, "Выберите слабость персонажа: \n"
                        + variantsMessageConfigurator(cs.activePc.background.flaw),
                KeyboardFactory.variantsBoard(cs.activePc.background.flaw), silent);

        cs.creationStage = BACKGROUND_FLAW;
    }

    public static void backgroundFlawSetter(ChatSession cs, String response, SilentSender silent) {
        cs.activePc.flaws.put(cs.activePc.background.name, response);

        patternExecute(cs, "Выберите свое мировоззрение:", KeyboardFactory.alignmentDnDSelectionBoard(), silent);
        cs.creationStage = ALIGNMENT;
    }

    public static void alignmentSetter(ChatSession cs, String response, SilentSender silent) {
        cs.activePc.alignment = response;

        patternExecute(cs, "Распределите характеристики.", null, silent);

        cs.activePc.luck = DiceNew.D6FourTimesCreation();
        patternExecute(cs, DiceNew.D6FourTimes(cs.activePc.luck), KeyboardFactory.assignStatsBoardDnD(cs.activePc.allocatedStats), silent);

        cs.creationStage = STATS1;
    }

    public static void statSetter(ChatSession cs, String response, HashMap<String, BiConsumer<PlayerDnD, Integer>> statAllocatorDnD, SilentSender silent) {
        statAllocatorDnD.get(response).accept(cs.activePc, cs.activePc.luck.get(4));
        if (cs.creationStage == STATS6) {
            cs.activePc.allocatedStats.clear();
            cs.activePc.luck.clear();

            patternExecute(cs, "Выберите навыки персонажа. Осталось навыков: " + cs.activePc.mainJob.startingSkillAmount,
                    KeyboardFactory.assignSkillsBoardDnD(cs.activePc.mainJob.skillRoster, cs.activePc.learnedSkills), silent);
            cs.creationStage = SKILLS;
        }

        cs.activePc.allocatedStats.add(response);
        cs.activePc.luck = DiceNew.D6FourTimesCreation();

        patternExecute(cs, DiceNew.D6FourTimes(cs.activePc.luck), KeyboardFactory.assignStatsBoardDnD(cs.activePc.allocatedStats), silent);
        cs.creationStage = cs.creationStage.next();
    }

    public static void skillsSetter(ChatSession cs, String response, HashMap<String, Consumer<PlayerDnD>> skillAllocatorDnD, SilentSender silent) {
        skillAllocatorDnD.get(response).accept(cs.activePc);
        cs.activePc.learnedSkills.add(response);
        cs.activePc.mainJob.startingSkillAmount--;

        if (cs.activePc.mainJob.startingSkillAmount == 0) {
            patternExecute(cs, "Выберите языки персонажа. Осталось языков: " + cs.activePc.bonusLanguages,
                    KeyboardFactory.languagesDnDSelectionBoard(cs.activePc.knownLanguages), silent);
            cs.creationStage = LANGUAGE;
        } else {
            patternExecute(cs, "Осталось навыков: " + cs.activePc.mainJob.startingSkillAmount,
                    KeyboardFactory.assignSkillsBoardDnD(cs.activePc.mainJob.skillRoster, cs.activePc.learnedSkills), silent);
        }
    }

    public static void languageSetter(ChatSession cs, String response, HashMap<String, Consumer<PlayerDnD>> languageDnDAllocator, SilentSender silent) {
        languageDnDAllocator.get(response).accept(cs.activePc);
        cs.activePc.bonusLanguages--;

        if (cs.activePc.bonusLanguages == 0) {
            patternExecute(cs, "Введите возраст персонажа.", null, silent);
            cs.creationStage = AGE;
        } else {
            patternExecute(cs, "Осталось языков: " + cs.activePc.bonusLanguages,
                    KeyboardFactory.languagesDnDSelectionBoard(cs.activePc.knownLanguages), silent);
        }
    }

    public static void ageSetter(ChatSession cs, String response, SilentSender silent) {
        cs.activePc.age = response;
        patternExecute(cs, "Введите рост персонажа.", null, silent);

        cs.creationStage = HEIGHT;
    }

    public static void heightSetter(ChatSession cs, String response, SilentSender silent) {
        cs.activePc.height = response;
        patternExecute(cs, "Введите вес персонажа.", null, silent);

        cs.creationStage = WEIGHT;
    }

    public static void weightSetter(ChatSession cs, String response, SilentSender silent) {
        cs.activePc.weight = response;
        patternExecute(cs, "Введите описание глаз персонажа.", null, silent);

        cs.creationStage = EYES;
    }

    public static void eyesSetter(ChatSession cs, String response, SilentSender silent) {
        cs.activePc.eyes = response;
        patternExecute(cs, "Введите описание кожи персонажа.", null, silent);

        cs.creationStage = SKIN;
    }

    public static void skinSetter(ChatSession cs, String response, SilentSender silent) {
        cs.activePc.skin = response;
        patternExecute(cs, "Введите описание волос персонажа.", null, silent);

        cs.creationStage = HAIR;
    }

    public static void hairSetter(ChatSession cs, String response, SilentSender silent) {
        cs.activePc.hair = response;
        ChatSession currentGroup = UserDataHandler.readSession(cs.activePc.campaignChatId);
        currentGroup.activeDm.playerDnDHashMap.put(cs.username, cs.activePc);

        cs.activePc = null;
        cs.creationOfPlayerDnD = false;
        cs.creationStage = NAME;

        UserDataHandler.saveSession(currentGroup); // небезопасная вещь, надо либо вывесить предупреждение (сделано), либо что-то с этим решить
        UserDataHandler.saveSession(cs);

        patternExecute(cs, Constants.PLAYER_CREATION_END, null, silent);
    }
}