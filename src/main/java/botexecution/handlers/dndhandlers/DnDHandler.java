package botexecution.handlers.dndhandlers;

import botexecution.handlers.corehandlers.DataHandler;
import botexecution.handlers.corehandlers.TextHandler;
import botexecution.mainobjects.ChatSession;
import botexecution.mainobjects.KeyboardFactory;
import common.Constants;
import dnd.dmtools.NoteDnD;
import dnd.dmtools.QuestDnDForDm;
import dnd.dmtools.QuestDnDForPlayers;
import dnd.mainobjects.PlayerDnD;
import dnd.values.EditingParameters;
import dnd.values.RoleParameters;
import dnd.values.characteristicsvalues.JobsDnD;
import dnd.values.masteryvalues.AdvantageTypeDnD;
import dnd.values.masteryvalues.MasteryTypeDnD;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;

import java.util.*;

public class DnDHandler {
    private final DataHandler knowledge;
    private final TextHandler walkieTalkie;
    private final DnDNotificationHandler secretMessages;

    //конструктор для хендлера
    public DnDHandler(DataHandler knowledge, TextHandler walkieTalkie, DnDNotificationHandler secretMessages) {
        this.knowledge = knowledge;
        this.walkieTalkie = walkieTalkie;
        this.secretMessages = secretMessages;
    }

    //функции для ДМ-а
    //ключевая функция: связь ДМ-а и игроков с компанией
    public ChatSession getCampaignSession(ChatSession currentUser) {
        if (currentUser.currentCampaign == null) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL);
            return null;
        }
        ChatSession currentCampaign = knowledge.getSession(currentUser.currentCampaign.toString());
        if (currentCampaign == null) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL);
            return null;
        }
        return currentCampaign;
    }

    //основная игровая механика
    public void requestARoll(MessageContext ctx) {
        ChatSession dungeonMaster = knowledge.getSession(ctx.chatId().toString());

        if (dungeonMaster.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(dungeonMaster, "Доступ запрещен - вы не ДМ данной компании.\n" +
                            "Для игроков существует возможность запросить бросок у ДМ-а." +
                            "Для этого перейдите на клавиатуру игрока.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(dungeonMaster);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "prof1-ct1-par_line2")) {
            return;
        }

        ChatSession requestedUser = knowledge.getSession(knowledge.findChatId(ctx.firstArg()));
        PlayerDnD affectedPlayer = currentCampaign.activeDm.campaignParty.get(ctx.firstArg());
        requestedUser.whoIsRolling = affectedPlayer.playerName;
        currentCampaign.whoIsRolling = affectedPlayer.playerName;
        affectedPlayer.diceComb = "";
        affectedPlayer.statCheck = MasteryTypeDnD.NONE;
        affectedPlayer.inPublic = true;
        affectedPlayer.advantage = AdvantageTypeDnD.CLEAR_THROW;
        affectedPlayer.specialCase = MasteryTypeDnD.NONE;

        StringBuilder diceRollMessage = new StringBuilder();
        MasteryTypeDnD superposition;
        String situation = "";
        StringBuilder bonus = new StringBuilder();

        String[] parameters = ctx.secondArg().split("-");
        for (String parameter: parameters) {
            superposition = MasteryTypeDnD.getParameter(parameter);
            if (superposition != null) {
                affectedPlayer.specialCase = MasteryTypeDnD.NONE;
                affectedPlayer.statCheck = superposition;
                situation = affectedPlayer.statCheck.toString();
            }
            else {
                switch (parameter) {
                    case "luck" -> {
                        affectedPlayer.specialCase = MasteryTypeDnD.LUCK;
                        situation = "Удача";
                    }
                    case "svth" -> {
                        affectedPlayer.specialCase = MasteryTypeDnD.SAVE;
                        situation = "Спасение (" + affectedPlayer.statCheck.toString() + ")";
                    }
                    case "dsvth" -> {
                        affectedPlayer.specialCase = MasteryTypeDnD.DEATH_SAVE;
                        situation = "Спасение (Смерть)";
                    }
                    case "pres" -> {
                        affectedPlayer.specialCase = MasteryTypeDnD.PRECISION;
                        situation = "Точность";
                    }
                    case "dam" -> {
                        affectedPlayer.specialCase = MasteryTypeDnD.DAMAGE;
                        situation = "Нанесение урона";
                    }
                    case "adv" -> {
                        affectedPlayer.advantage = AdvantageTypeDnD.ADVANTAGE;
                    }
                    case "dis" -> {
                        affectedPlayer.advantage = AdvantageTypeDnD.DISADVANTAGE;
                    }
                    case "clr" -> {
                        affectedPlayer.advantage = AdvantageTypeDnD.CLEAR_THROW;
                    }
                    case "prv" -> {
                        affectedPlayer.inPublic = false;
                    }
                }
                if (parameter.contains("d")) {
                    affectedPlayer.diceComb = parameter;
                }
            }
        }

        if (affectedPlayer.specialCase == MasteryTypeDnD.SAVE) {
            switch (affectedPlayer.statCheck) {
                case STRENGTH -> bonus.append(affectedPlayer.strengthSaveThrow);
                case DEXTERITY -> bonus.append(affectedPlayer.dexteritySaveThrow);
                case CONSTITUTION -> bonus.append(affectedPlayer.constitutionSaveThrow);
                case INTELLIGENCE -> bonus.append(affectedPlayer.intelligenceSaveThrow);
                case WISDOM -> bonus.append(affectedPlayer.wisdomSaveThrow);
                case CHARISMA -> bonus.append(affectedPlayer.charismaSaveThrow);
                case NONE -> {
                    walkieTalkie.patternExecute(dungeonMaster,
                            "Произошла ошибка - вы не определили стат для проверки.\n" +
                                    "Попробуйте ещё раз.");
                    return;
                }
            }
        }
        else if (affectedPlayer.specialCase == MasteryTypeDnD.NONE) {
            switch (affectedPlayer.statCheck) {
                case STRENGTH -> bonus.append(affectedPlayer.strengthModifier);
                case DEXTERITY -> bonus.append(affectedPlayer.dexterityModifier);
                case CONSTITUTION -> bonus.append(affectedPlayer.constitutionModifier);
                case INTELLIGENCE -> bonus.append(affectedPlayer.intelligenceModifier);
                case WISDOM -> bonus.append(affectedPlayer.wisdomModifier);
                case CHARISMA -> bonus.append(affectedPlayer.charismaModifier);
                case NONE -> {
                    walkieTalkie.patternExecute(dungeonMaster,
                            "Произошла ошибка - вы не определили стат для проверки.\n" +
                                    "Попробуйте ещё раз.");
                    return;
                }
            }
        }
        else {
            bonus.setLength(0);
        }

        if (affectedPlayer.diceComb.isEmpty()) {
            if (affectedPlayer.advantage != AdvantageTypeDnD.CLEAR_THROW) {
                affectedPlayer.diceComb = "2d20";
            }
            else if (affectedPlayer.specialCase == MasteryTypeDnD.DEATH_SAVE) {
                affectedPlayer.diceComb = "d20";
            }
            else {
                walkieTalkie.patternExecute(dungeonMaster,
                        "Произошла ошибка - не введены кости для броска.");
                return;
            }
        }

        diceRollMessage.append("ДМ (").append(dungeonMaster.username).append(") вашей компании (")
                .append(currentCampaign.activeDm.campaignName).append(") запрашивает бросок: \n");

        if (affectedPlayer.specialCase == MasteryTypeDnD.DEATH_SAVE) {
            diceRollMessage.append(affectedPlayer.diceComb).append(", ").append(situation)
                    .append(", ").append(affectedPlayer.advantage.toString()).append("\n");
            diceRollMessage.append("Текущие значения счетчиков бросков смерти:\n")
                    .append("Успехов: ").append(affectedPlayer.deathThrowsSuccess).append("\n")
                    .append("Провалов: ").append(affectedPlayer.deathThrowsFailure);
        }
        else if (bonus.isEmpty()) {
            diceRollMessage.append(affectedPlayer.diceComb).append(", ").append(situation)
                    .append(", ").append(affectedPlayer.advantage.toString());
        }
        else {
            diceRollMessage.append(affectedPlayer.diceComb).append(", ").append(situation)
                    .append(" (").append(bonus).append("), ").append(affectedPlayer.advantage.toString());
        }

        if (affectedPlayer.inPublic) {
            walkieTalkie.patternExecute(currentCampaign, requestedUser.username, diceRollMessage.toString(),
                    KeyboardFactory.YesOrNoBoard(), false);
        }
        else {
            walkieTalkie.patternExecute(requestedUser, diceRollMessage.toString(),
                    KeyboardFactory.YesOrNoBoard(), false);
        }

        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(requestedUser);
        knowledge.renewListChat(dungeonMaster);
    }

    //запрос броска для игроков
    public void askDmForARoll(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "par_line1")) {
            return;
        }

        ChatSession dungeonMaster = knowledge.getSession(String.valueOf(currentCampaign.activeDm.dungeonMasterChatId));

        StringBuilder messageToDm = new StringBuilder();
        messageToDm.append("Игрок ").append(currentUser.username)
                .append(" (персонаж - ").append(currentCampaign.activeDm.campaignParty.get(currentUser.username).name)
                .append(") ").append("хочет сделать бросок: ").append(ctx.firstArg()).append("\n");
        messageToDm.append("Вы подтверждаете данный бросок?");

        walkieTalkie.patternExecute(dungeonMaster, messageToDm.toString(),
                KeyboardFactory.YesOrNoBoard(currentUser.username + " " + ctx.firstArg()), false);
        walkieTalkie.patternExecute(currentUser,
                "Запрос был отправлен ДМ-у. Ожидайте.");

        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
        knowledge.renewListChat(dungeonMaster);
    }

    public void askDmForARollResponse(ChatSession dungeonMaster, String response) {
        ChatSession currentCampaign = getCampaignSession(dungeonMaster);
        if (currentCampaign == null) {
            return;
        }

        String[] answer = response.split(" ");
        ChatSession affectedUser = knowledge.getSession(knowledge.findChatId(answer[1]));
        if (Objects.equals(answer[0], "Нет")) {
            walkieTalkie.patternExecute(affectedUser,
                    "ДМ вашей группы (" + dungeonMaster.username + ") отказал вашему запросу.");
        }
        else {
            walkieTalkie.patternExecute(affectedUser,
                    "ДМ вашей группы (" + dungeonMaster.username + ") одобрил ваш запрос.");
            requestARoll(dungeonMaster, currentCampaign, affectedUser, answer[2]);
        }
    }

    public void requestARoll(ChatSession dungeonMaster, ChatSession currentCampaign,
                             ChatSession requestedUser, String settings) {
        PlayerDnD affectedPlayer = currentCampaign.activeDm.campaignParty.get(requestedUser.username);
        requestedUser.whoIsRolling = affectedPlayer.playerName;
        currentCampaign.whoIsRolling = affectedPlayer.playerName;
        affectedPlayer.diceComb = "";
        affectedPlayer.statCheck = MasteryTypeDnD.NONE;
        affectedPlayer.inPublic = true;
        affectedPlayer.advantage = AdvantageTypeDnD.CLEAR_THROW;
        affectedPlayer.specialCase = MasteryTypeDnD.NONE;

        StringBuilder diceRollMessage = new StringBuilder();
        MasteryTypeDnD superposition;
        String situation = "";
        StringBuilder bonus = new StringBuilder();

        String[] parameters = settings.split("-");
        for (String parameter: parameters) {
            superposition = MasteryTypeDnD.getParameter(parameter);
            if (superposition != null) {
                affectedPlayer.specialCase = MasteryTypeDnD.NONE;
                affectedPlayer.statCheck = superposition;
                situation = affectedPlayer.statCheck.toString();
            }
            else {
                switch (parameter) {
                    case "luck" -> {
                        affectedPlayer.specialCase = MasteryTypeDnD.LUCK;
                        situation = "Удача";
                    }
                    case "svth" -> {
                        affectedPlayer.specialCase = MasteryTypeDnD.SAVE;
                        situation = "Спасение (" + affectedPlayer.statCheck.toString() + ")";
                    }
                    case "dsvth" -> {
                        affectedPlayer.specialCase = MasteryTypeDnD.DEATH_SAVE;
                        situation = "Спасение (Смерть)";
                    }
                    case "pres" -> {
                        affectedPlayer.specialCase = MasteryTypeDnD.PRECISION;
                        situation = "Точность";
                    }
                    case "dam" -> {
                        affectedPlayer.specialCase = MasteryTypeDnD.DAMAGE;
                        situation = "Нанесение урона";
                    }
                    case "adv" -> {
                        affectedPlayer.advantage = AdvantageTypeDnD.ADVANTAGE;
                    }
                    case "dis" -> {
                        affectedPlayer.advantage = AdvantageTypeDnD.DISADVANTAGE;
                    }
                    case "clr" -> {
                        affectedPlayer.advantage = AdvantageTypeDnD.CLEAR_THROW;
                    }
                    case "prv" -> {
                        affectedPlayer.inPublic = false;
                    }
                }
                if (parameter.contains("d")) {
                    affectedPlayer.diceComb = parameter;
                }
            }
        }

        if (affectedPlayer.specialCase == MasteryTypeDnD.SAVE) {
            switch (affectedPlayer.statCheck) {
                case STRENGTH -> bonus.append(affectedPlayer.strengthSaveThrow);
                case DEXTERITY -> bonus.append(affectedPlayer.dexteritySaveThrow);
                case CONSTITUTION -> bonus.append(affectedPlayer.constitutionSaveThrow);
                case INTELLIGENCE -> bonus.append(affectedPlayer.intelligenceSaveThrow);
                case WISDOM -> bonus.append(affectedPlayer.wisdomSaveThrow);
                case CHARISMA -> bonus.append(affectedPlayer.charismaSaveThrow);
                case NONE -> {
                    walkieTalkie.patternExecute(dungeonMaster,
                            "Произошла ошибка - вы не определили стат для проверки.\n" +
                                    "Попробуйте ещё раз.");
                    walkieTalkie.patternExecute(requestedUser,
                            "Произошла ошибка - вы не определили стат для проверки.\n" +
                                    "Попробуйте ещё раз.");
                    return;
                }
            }
        }
        else if (affectedPlayer.specialCase == MasteryTypeDnD.NONE) {
            switch (affectedPlayer.statCheck) {
                case STRENGTH -> bonus.append(affectedPlayer.strengthModifier);
                case DEXTERITY -> bonus.append(affectedPlayer.dexterityModifier);
                case CONSTITUTION -> bonus.append(affectedPlayer.constitutionModifier);
                case INTELLIGENCE -> bonus.append(affectedPlayer.intelligenceModifier);
                case WISDOM -> bonus.append(affectedPlayer.wisdomModifier);
                case CHARISMA -> bonus.append(affectedPlayer.charismaModifier);
                case NONE -> {
                    walkieTalkie.patternExecute(dungeonMaster,
                            "Произошла ошибка - вы не определили стат для проверки.\n" +
                                    "Попробуйте ещё раз.");
                    walkieTalkie.patternExecute(requestedUser,
                            "Произошла ошибка - вы не определили стат для проверки.\n" +
                                    "Попробуйте ещё раз.");
                    return;
                }
            }
        }
        else {
            bonus.setLength(0);
        }

        if (affectedPlayer.diceComb.isEmpty()) {
            if (affectedPlayer.advantage != AdvantageTypeDnD.CLEAR_THROW) {
                affectedPlayer.diceComb = "2d20";
            }
            else if (affectedPlayer.specialCase == MasteryTypeDnD.DEATH_SAVE) {
                affectedPlayer.diceComb = "d20";
            }
            else {
                walkieTalkie.patternExecute(dungeonMaster,
                        "Произошла ошибка - не введены кости для броска.");
                walkieTalkie.patternExecute(requestedUser,
                        "Произошла ошибка - не введены кости для броска.");
                return;
            }
        }

        diceRollMessage.append("ДМ (").append(dungeonMaster.username).append(") вашей компании (")
                .append(currentCampaign.activeDm.campaignName).append(") запрашивает бросок: \n");

        if (affectedPlayer.specialCase == MasteryTypeDnD.DEATH_SAVE) {
            diceRollMessage.append(affectedPlayer.diceComb).append(", ").append(situation)
                    .append(", ").append(affectedPlayer.advantage.toString()).append("\n");
            diceRollMessage.append("Текущие значения счетчиков бросков смерти:\n")
                    .append("Успехов: ").append(affectedPlayer.deathThrowsSuccess).append("\n")
                    .append("Провалов: ").append(affectedPlayer.deathThrowsFailure);
        }
        else if (bonus.isEmpty()) {
            diceRollMessage.append(affectedPlayer.diceComb).append(", ").append(situation)
                    .append(", ").append(affectedPlayer.advantage.toString());
        }
        else {
            diceRollMessage.append(affectedPlayer.diceComb).append(", ").append(situation)
                    .append(" (").append(bonus).append("), ").append(affectedPlayer.advantage.toString());
        }

        if (affectedPlayer.inPublic) {
            walkieTalkie.patternExecute(currentCampaign, requestedUser.username, diceRollMessage.toString(),
                    KeyboardFactory.YesOrNoBoard(), false);
        }
        else {
            walkieTalkie.patternExecute(requestedUser, diceRollMessage.toString(),
                    KeyboardFactory.YesOrNoBoard(), false);
        }

        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(requestedUser);
        knowledge.renewListChat(dungeonMaster);
    }

    public void addQuest(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        if (currentUser.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(currentUser, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "")) {
            return;
        }

        currentCampaign.activeDm.questRoster.add(new QuestDnDForDm());
        walkieTalkie.patternExecute(currentUser, "Вы успешно добавили новый квест.");
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void editQuest(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        if (currentUser.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(currentUser, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "int<2")) {
            return;
        }

        currentCampaign.activeDm.editQuestIndex = Integer.parseInt(ctx.secondArg()) - 1;
        if (currentCampaign.activeDm.editQuestIndex >= currentCampaign.activeDm.questRoster.size()) {
            walkieTalkie.patternExecute(currentUser, "Произошла ошибка - введено число вне возможного набора индексов.\n"
                    + "Проверьте правильность ввода по справке в /help [команда]");
            currentCampaign.activeDm.editQuestIndex = 0;
            return;
        }

        if (!"-t-p-s-n".contains(ctx.firstArg()) || ctx.firstArg().length() != 2) {
            walkieTalkie.patternExecute(currentUser, "Произошла ошибка - введены некорректные параметры.\n" +
                                    "Попробуйте ещё раз.");
            return;
        }

        currentUser.editingAQuest = true;
        currentUser.editQuestParameter = ctx.firstArg();
        walkieTalkie.patternExecute(currentUser, "Введите изменения, применяемые к заданию.");
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void editQuestSecondStage(ChatSession cs, String response) {
        ChatSession currentCampaign = getCampaignSession(cs);

        if (cs.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(cs, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        if (currentCampaign == null) {
            return;
        }

        switch (cs.editQuestParameter) {
            case "-t" -> currentCampaign.activeDm.questRoster.get(currentCampaign.activeDm.editQuestIndex).setTitle(response);
            case "-p" -> currentCampaign.activeDm.questRoster.get(currentCampaign.activeDm.editQuestIndex).setProcess(response);
            case "-s" -> currentCampaign.activeDm.questRoster.get(currentCampaign.activeDm.editQuestIndex).setSummary(response);
            case "-n" -> currentCampaign.activeDm.questRoster.get(currentCampaign.activeDm.editQuestIndex).setNotes(response);
        }

        walkieTalkie.patternExecute(cs, "Изменение задания прошло успешно.");
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(cs);
    }

    public void postQuest(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        if (currentUser.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(currentUser, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "int<1")) {
            return;
        }

        int index = Integer.parseInt(ctx.firstArg()) - 1;
        if (index >= currentCampaign.activeDm.questRoster.size()) {
            walkieTalkie.patternExecute(currentUser, "Произошла ошибка - введено число вне возможного набора индексов.\n"
                    + "Проверьте правильность ввода по справке в /help [команда]");
            return;
        }

        currentCampaign.activeDm.questBoard.add(new QuestDnDForPlayers(currentCampaign.activeDm.questRoster.get(index)));

        walkieTalkie.patternExecute(currentUser, "Квест успешно добавлен на доску заданий.");
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void closeQuest(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        if (currentUser.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(currentUser, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "int<1")) {
            return;
        }

        int index = Integer.parseInt(ctx.firstArg()) - 1;
        if (index >= currentCampaign.activeDm.questRoster.size()) {
            walkieTalkie.patternExecute(currentUser, "Произошла ошибка - введено число вне возможного набора индексов.\n"
                    + "Проверьте правильность ввода по справке в /help [команда]");
            return;
        }

        currentCampaign.activeDm.questBoard.remove(index);

        walkieTalkie.patternExecute(currentUser, "Квест успешно убран с доски заданий.");
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void showQuestBoard(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "")) {
            return;
        }

        if (Objects.equals(ctx.firstArg(), "-p") && currentUser.role == RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(currentUser, currentCampaign.activeDm.questBoardAsString());
            return;
        }

        switch (currentUser.role) {
            case DUNGEON_MASTER -> walkieTalkie.patternExecute(currentUser, currentCampaign.activeDm.questRosterAsString());
            case PLAYER -> walkieTalkie.patternExecute(currentUser, currentCampaign.activeDm.questBoardAsString());
        }
    }

    public void showQuest(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "int<1")) {
            return;
        }

        int index = Integer.parseInt(ctx.firstArg()) - 1;
        switch (currentUser.role) {
            case PLAYER -> {
                if (index >= currentCampaign.activeDm.questBoard.size()) {
                    walkieTalkie.patternExecute(currentUser, "Произошла ошибка - введено число вне возможного набора индексов.\n"
                            + "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }

                walkieTalkie.patternExecute(ctx, currentCampaign.activeDm.getBoardQuest(index));
            }
            case DUNGEON_MASTER -> {
                if (index >= currentCampaign.activeDm.questRoster.size()) {
                    walkieTalkie.patternExecute(currentUser, "Произошла ошибка - введено число вне возможного набора индексов.\n"
                            + "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }

                walkieTalkie.patternExecute(ctx, currentCampaign.activeDm.getRosterQuest(index));
            }
        }
    }

    public void showNotes(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        
        StringBuilder notes = new StringBuilder();
        switch (currentUser.role) {
            case NONE -> {
                walkieTalkie.patternExecute(currentUser, "Произошла ошибка - укажите текущую компанию.");
                return;
            }
            case CAMPAIGN -> {
                PlayerDnD affectedPlayer = currentUser.activeDm.campaignParty.get("@" + ctx.user().getUserName());
                notes.append("Заметки персонажа ").append(affectedPlayer.name).append(": ");
                if (affectedPlayer.notes.isEmpty()) {
                    notes.append("На данный момент их нет.");
                } else {
                    for (int i = 1; i < affectedPlayer.notes.size(); i++) {
                        notes.append(i).append(". ")
                                .append(affectedPlayer.notes.get(i - 1).title).append("\n");
                    }
                }

                walkieTalkie.patternExecute(currentUser, notes.toString());
            }
            case DUNGEON_MASTER -> {
                ChatSession currentCampaign = knowledge.getSession(currentUser.currentCampaign.toString());
                notes.append("Заметки о мире и персонажах (для ДМ-а): ");
                if (currentCampaign.activeDm.settingNotes.isEmpty()) {
                    notes.append("На данный момент их нет.");
                } else {
                    for (int i = 1; i < currentCampaign.activeDm.settingNotes.size(); i++) {
                        notes.append(i).append(". ")
                                .append(currentCampaign.activeDm.settingNotes.get(i - 1).title).append("\n");
                    }
                }

                walkieTalkie.patternExecute(currentUser, notes.toString());
            }
            case PLAYER -> {
                ChatSession currentCampaign = knowledge.getSession(currentUser.currentCampaign.toString());
                PlayerDnD affectedPlayer = currentCampaign.activeDm.campaignParty.get(currentUser.username);
                notes.append("Заметки персонажа ").append(affectedPlayer.name).append(": ");
                if (affectedPlayer.notes.isEmpty()) {
                    notes.append("На данный момент их нет.");
                } else {
                    for (int i = 1; i < affectedPlayer.notes.size(); i++) {
                        notes.append(i).append(". ")
                                .append(affectedPlayer.notes.get(i - 1).title).append("\n");
                    }
                }

                walkieTalkie.patternExecute(currentUser, notes.toString());
            }
        }

        knowledge.renewListChat(currentUser);
    }

    public void addNote(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "")) {
            return;
        }

        switch (currentUser.role) {
            case NONE -> {
                walkieTalkie.patternExecute(currentUser, "Произошла ошибка - укажите текущую компанию.");
                return;
            }
            case PLAYER -> currentCampaign.activeDm.campaignParty.get(currentUser.username).notes.add(new NoteDnD());
            case DUNGEON_MASTER -> currentCampaign.activeDm.settingNotes.add(new NoteDnD());
            case CAMPAIGN -> currentCampaign.activeDm.campaignParty.get("@" + ctx.user().getUserName()).notes.add(new NoteDnD());
        }

        walkieTalkie.patternExecute(currentUser, "Заметка была добавлена.");
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void editNote(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "int<2")) {
            return;
        }

        switch (currentUser.role) {
            case NONE -> {
                walkieTalkie.patternExecute(currentUser, "Произошла ошибка - укажите текущую компанию.");
                return;
            }
            case CAMPAIGN -> {
                walkieTalkie.patternExecute(currentUser, "Перейдите в личные сообщения перед редакцией заметок.");
            }
            case PLAYER, DUNGEON_MASTER -> {
                currentUser.editingANote = true;
                currentUser.editNoteIndex = Integer.parseInt(ctx.secondArg()) - 1;
                switch (ctx.firstArg()) {
                    case "-t" -> currentUser.editNote = EditingParameters.NOTE_TITLE;
                    case "-c" -> currentUser.editNote = EditingParameters.NOTE_CONTENTS;
                    case "-p" -> currentUser.editNote = EditingParameters.NOTE_POINTS_OF_INTEREST;
                }
                walkieTalkie.patternExecute(currentUser, "Введите изменения.");
            }
        }

        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void editNoteSecondStage(ChatSession cs, String response) {
        switch (cs.role) {
            case DUNGEON_MASTER -> {
                cs.editingANote = false;
                ChatSession currentCampaign = knowledge.getSession(cs.currentCampaign.toString());
                switch (cs.editNote) {
                    case NOTE_TITLE -> currentCampaign.activeDm.settingNotes.get(cs.editNoteIndex).title =
                            response;
                    case NOTE_CONTENTS ->
                            currentCampaign.activeDm.settingNotes.get(cs.editNoteIndex).contents =
                                    response;
                    case NOTE_POINTS_OF_INTEREST ->
                            currentCampaign.activeDm.settingNotes.get(cs.editNoteIndex).pointsOfInterest =
                                    response;
                    case NONE -> walkieTalkie.patternExecute(cs,
                            "Произошла непредвиденная ошибка - категория изменения равна ничему.\n" +
                                    "Сообщите об этом создателям бота.");
                }
                cs.editNote = EditingParameters.NONE;
            }
            case PLAYER -> {
                cs.editingANote = false;
                ChatSession currentCampaign = knowledge.getSession(cs.currentCampaign.toString());
                switch (cs.editNote) {
                    case NOTE_TITLE -> currentCampaign.activeDm.campaignParty.get(cs.username)
                            .notes.get(cs.editNoteIndex).title =
                            response;
                    case NOTE_CONTENTS -> currentCampaign.activeDm.campaignParty.get(cs.username)
                            .notes.get(cs.editNoteIndex).contents =
                            response;
                    case NOTE_POINTS_OF_INTEREST -> currentCampaign.activeDm.campaignParty.get(cs.username)
                            .notes.get(cs.editNoteIndex).pointsOfInterest =
                            response;
                    case NONE -> walkieTalkie.patternExecute(cs,
                            "Произошла непредвиденная ошибка - категория изменения равна ничему.\n" +
                                    "Сообщите об этом создателям бота.");
                }
                cs.editNote = EditingParameters.NONE;
            }
        }
        walkieTalkie.patternExecute(cs, "Изменение заметок прошло успешно.");
        knowledge.renewListChat(cs);
    }

    //изменение параметров персонажа игрока
    public int[] bonusMastery = {2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6};

    public void setPrestigeJob(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        if (currentUser.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(currentUser, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "prof1")) {
            return;
        }

        if (!"-m-s".contains(ctx.secondArg())) {
            walkieTalkie.patternExecute(currentUser,
                        "Введите корректный параметр.");
            return;
        }

        if (ctx.secondArg().contains("-m")) {
            currentUser.editPrestigeJobIndex = 0;
        }
        else if (ctx.secondArg().contains("-s")) {
            if (secretMessages.isNotLegal(ctx, "int3")) {
                return;
            }
            currentUser.editPrestigeJobIndex = Integer.parseInt(ctx.thirdArg());
        }

        currentUser.editingAPrestigeJob = true;
        currentUser.whoIsEdited = ctx.firstArg();

        walkieTalkie.patternExecute(currentUser, "Введите название подкласса персонажа.");
        knowledge.renewListChat(currentUser);
        knowledge.renewListChat(currentCampaign);
    }

    public void giveASecondaryJob(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        if (currentUser.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(currentUser, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "prof1")) {
            return;
        }

        if (!JobsDnD.getJobs().containsKey(ctx.secondArg())) {
            walkieTalkie.patternExecute(currentUser,
                    "Произошла ошибка - название класса введено не корректно.\n" +
                    "Процесс приостановлен.");
            return;
        }

        currentCampaign.activeDm.campaignParty.get(ctx.firstArg()).secondaryJobs.add(JobsDnD.getJob(ctx.secondArg()));
        currentCampaign.activeDm.campaignParty.get(ctx.firstArg()).secondaryJobsTitles.add(ctx.secondArg());
        currentCampaign.activeDm.campaignParty.get(ctx.firstArg()).secondaryJobsPrestigeTitles.add("Не выбрано");
        currentCampaign.activeDm.campaignParty.get(ctx.firstArg()).secondaryJobsLevels.add(1);
        currentCampaign.activeDm.campaignParty.get(ctx.firstArg()).totalLevel += 1;

        walkieTalkie.patternExecute(currentUser,
                "Добавление дополнительного класса прошло успешно.");
        knowledge.renewListChat(currentUser);
        knowledge.renewListChat(currentCampaign);
    }

    public void setPrestigeJobSecondStage(ChatSession dungeonMaster, String response) {
        if (dungeonMaster.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(dungeonMaster, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(dungeonMaster);
        if (currentCampaign == null) {
            return;
        }

        if (dungeonMaster.editPrestigeJobIndex == 0) {
            currentCampaign.activeDm.campaignParty.get(dungeonMaster.whoIsEdited)
                    .mainPrestigeJobTitle = response;
        }
        else {
            if (currentCampaign.activeDm.campaignParty.get(dungeonMaster.whoIsEdited)
                    .secondaryJobsPrestigeTitles.size() <= dungeonMaster.editPrestigeJobIndex - 1
                    || dungeonMaster.editPrestigeJobIndex - 1 < 0) {
                walkieTalkie.patternExecute(dungeonMaster,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Попробуйте заново.");
                dungeonMaster.editingAPrestigeJob = false;
                return;
            }
            currentCampaign.activeDm.campaignParty.get(dungeonMaster.whoIsEdited).secondaryJobsPrestigeTitles
                    .set(dungeonMaster.editPrestigeJobIndex - 1, response);
        }

        walkieTalkie.patternExecute(dungeonMaster,
                "Изменение подкласса прошло успешно.");
        knowledge.renewListChat(dungeonMaster);
        knowledge.renewListChat(currentCampaign);
    }

    public void changeHealth(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        if (currentUser.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(currentUser, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "prof1-int2")) {
            return;
        }

        int delta = Integer.parseInt(ctx.secondArg());
        currentCampaign.activeDm.campaignParty.get(ctx.firstArg()).healthPoints += delta;
        walkieTalkie.patternExecute(currentUser, "Изменение здоровья произошло успешно.");
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void changeDeathSaveThrowCounter(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        if (currentUser.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(currentUser, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "prof1")) {
            return;
        }

        if (!"- + -cr +cr -res".contains(ctx.secondArg()) || ctx.secondArg().length() % 2 != 0) {
            walkieTalkie.patternExecute(currentUser, "Введите корректный параметр.");
            return;
        }

        switch (ctx.secondArg()) {
            case "-" -> currentCampaign.activeDm.campaignParty.get(ctx.firstArg()).deathThrowsFailure += 1;
            case "+" -> currentCampaign.activeDm.campaignParty.get(ctx.firstArg()).deathThrowsSuccess += 1;
            case "-cr" -> currentCampaign.activeDm.campaignParty.get(ctx.firstArg()).deathThrowsFailure += 2;
            case "+cr" -> currentCampaign.activeDm.campaignParty.get(ctx.firstArg()).deathThrowsSuccess += 2;
            case "-res" -> {
                currentCampaign.activeDm.campaignParty.get(ctx.firstArg()).deathThrowsSuccess = 0;
                currentCampaign.activeDm.campaignParty.get(ctx.firstArg()).deathThrowsFailure = 0;
            }
        }

        StringBuilder deathSave = new StringBuilder("Cтатистика бросков спасения от смерти: \n");
        deathSave.append("Успехи: ")
                .append(currentCampaign.activeDm.campaignParty.get(ctx.secondArg()).deathThrowsSuccess).append("\n");
        deathSave.append("Провалы: ")
                .append(currentCampaign.activeDm.campaignParty.get(ctx.secondArg()).deathThrowsFailure);

        walkieTalkie.patternExecute(currentUser, deathSave.toString());
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void changeExperience(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        if (currentUser.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(currentUser, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "prof1-int2")) {
            return;
        }

        int exp = Integer.parseInt(ctx.secondArg());

        currentCampaign.activeDm.campaignParty.get(ctx.firstArg()).experience += exp;
        walkieTalkie.patternExecute(currentUser, "Опыт был успешно изменен.");
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void giveInspiration(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        if (currentUser.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(currentUser, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "prof1-int2")) {
            return;
        }

        int insp = Integer.parseInt(ctx.secondArg());

        currentCampaign.activeDm.campaignParty.get(ctx.firstArg()).inspiration += insp;
        walkieTalkie.patternExecute(currentUser, "Очки вдохновления были успешно начислены.");
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void levelUp(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        if (currentUser.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(currentUser, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "prof1")) {
            return;
        }

        if (!"-m-s".contains(ctx.secondArg())) {
            walkieTalkie.patternExecute(currentUser,
                        "Введите корректный параметр.");
                return;
        }

        if (ctx.secondArg().contains("-m")) {
            currentCampaign.activeDm.campaignParty.get(ctx.firstArg()).mainJobLevel += 1;
        } else if (ctx.secondArg().contains("-s")) {
            int jobIndex;
            try {
                jobIndex = Integer.parseInt(ctx.thirdArg()) - 1;
            } catch (NumberFormatException e) {
                walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено не число.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                return;
            }
            
            if (jobIndex >= currentCampaign.activeDm.campaignParty.get(ctx.firstArg()).secondaryJobs.size() 
                    || jobIndex < 0) {
                walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                return;
            }
            
            int level = currentCampaign.activeDm.campaignParty.get(ctx.firstArg())
                    .secondaryJobsLevels.get(Integer.parseInt(ctx.thirdArg()));
            
            currentCampaign.activeDm.campaignParty.get(ctx.firstArg())
                    .secondaryJobsLevels.set(Integer.parseInt(ctx.thirdArg()), level + 1);
        }

        currentCampaign.activeDm.campaignParty.get(ctx.firstArg()).totalLevel += 1;
        if (currentCampaign.activeDm.campaignParty.get(ctx.firstArg()).totalLevel <= 20) {
            currentCampaign.activeDm.campaignParty.get(ctx.firstArg()).bonusMastery =
                    bonusMastery[currentCampaign.activeDm.campaignParty.get(ctx.firstArg()).totalLevel - 1];
        }
        walkieTalkie.patternExecute(currentUser, "Уровень персонажа был успешно изменен.");
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void changeStat(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        if (currentUser.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(currentUser, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "prof1-par_stat2-int<3")) {
            return;
        }

        PlayerDnD affectedPlayer = currentCampaign.activeDm.campaignParty.get(ctx.firstArg());
        switch (ctx.secondArg()) {
            case "-str" -> affectedPlayer.setStrength(Integer.parseInt(ctx.thirdArg()));
            case "-dex" -> affectedPlayer.setDexterity(Integer.parseInt(ctx.thirdArg()));
            case "-con" -> affectedPlayer.setConstitution(Integer.parseInt(ctx.thirdArg()));
            case "-int" -> affectedPlayer.setIntelligence(Integer.parseInt(ctx.thirdArg()));
            case "-wis" -> affectedPlayer.setWisdom(Integer.parseInt(ctx.thirdArg()));
            case "-cha" -> affectedPlayer.setCharisma(Integer.parseInt(ctx.thirdArg()));
        }

        currentCampaign.activeDm.campaignParty.put(ctx.firstArg(), affectedPlayer);
        walkieTalkie.patternExecute(currentUser, "Характеристика персонажа изменена успешно.");
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void changeAdvantages(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        if (currentUser.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(currentUser, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "prof1-par_adv3")) {
            return;
        }

        PlayerDnD affectedPlayer = currentCampaign.activeDm.campaignParty.get(ctx.firstArg());
        switch (ctx.secondArg()) {
            case "-addpersadv" -> affectedPlayer.personalAdvantages.add(MasteryTypeDnD.getEditAdvantage(ctx.thirdArg()));
            case "-delpersadv" -> affectedPlayer.personalAdvantages.remove(MasteryTypeDnD.getEditAdvantage(ctx.thirdArg()));
            case "-addextadv" -> affectedPlayer.externalAdvantages.add(MasteryTypeDnD.getEditAdvantage(ctx.thirdArg()));
            case "-delextadv" -> affectedPlayer.externalAdvantages.remove(MasteryTypeDnD.getEditAdvantage(ctx.thirdArg()));
            case "-addpersdis" -> affectedPlayer.personalDisadvantages.add(MasteryTypeDnD.getEditAdvantage(ctx.thirdArg()));
            case "-delpersdis" -> affectedPlayer.personalDisadvantages.remove(MasteryTypeDnD.getEditAdvantage(ctx.thirdArg()));
            case "-addextdis" -> affectedPlayer.externalDisadvantages.add(MasteryTypeDnD.getEditAdvantage(ctx.thirdArg()));
            case "-delextdis" -> affectedPlayer.externalDisadvantages.remove(MasteryTypeDnD.getEditAdvantage(ctx.thirdArg()));
            default -> {
                walkieTalkie.patternExecute(ctx,
                            "Произошла ошибка - введен некорректный параметр.\n" +
                                    "Попробуйте ещё раз.");
                return;
            }
        }

        affectedPlayer.totalAdvantages.clear();
        affectedPlayer.totalAdvantages.addAll(affectedPlayer.personalAdvantages);
        affectedPlayer.totalAdvantages.addAll(affectedPlayer.externalAdvantages);
        affectedPlayer.totalDisadvantages.clear();
        affectedPlayer.totalDisadvantages.addAll(affectedPlayer.personalDisadvantages);
        affectedPlayer.totalDisadvantages.addAll(affectedPlayer.externalDisadvantages);

        currentCampaign.activeDm.campaignParty.put(ctx.firstArg(), affectedPlayer);
        walkieTalkie.patternExecute(currentUser, "Изменение прошло успешно.");
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void changeLook(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        if (currentUser.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(currentUser, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "prof1")) {
            return;
        }

        currentUser.editingALook = true;
        currentUser.whoIsEdited = ctx.secondArg() + ctx.firstArg();
        walkieTalkie.patternExecute(currentUser, "Введите изменения.");
    }

    public void changeLookSecondStage(ChatSession cs, String response) {
        if (cs.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(cs, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }
        ChatSession currentCampaign = getCampaignSession(cs);
        String[] parameters = cs.whoIsStyling.split("@");

        switch (parameters[0]) {
            case "-a" -> currentCampaign.activeDm.campaignParty.get("@" + parameters[1]).age = response;
            case "-he" -> currentCampaign.activeDm.campaignParty.get("@" + parameters[1]).height = response;
            case "-w" -> currentCampaign.activeDm.campaignParty.get("@" + parameters[1]).weight = response;
            case "-e" -> currentCampaign.activeDm.campaignParty.get("@" + parameters[1]).eyes = response;
            case "-s" -> currentCampaign.activeDm.campaignParty.get("@" + parameters[1]).skin = response;
            case "-ha" -> currentCampaign.activeDm.campaignParty.get("@" + parameters[1]).hair = response;
            default -> {
                walkieTalkie.patternExecute(cs, "Произошла ошибка - введен некорректный параметр.");
                return;
            }
        }

        walkieTalkie.patternExecute(cs, "Изменение прошло успешно.");
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(cs);
    }

    public void changeValuables(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        if (currentUser.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(currentUser, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "prof1-int3")) {
            return;
        }

        switch (ctx.secondArg()) {
            case "-g" -> currentCampaign.activeDm
                    .campaignParty.get(ctx.firstArg()).gold += Integer.parseInt(ctx.thirdArg());
            case "-s" -> currentCampaign.activeDm
                    .campaignParty.get(ctx.firstArg()).silver += Integer.parseInt(ctx.thirdArg());
            case "-c" -> currentCampaign.activeDm
                    .campaignParty.get(ctx.firstArg()).copper += Integer.parseInt(ctx.thirdArg());
            default -> {
                walkieTalkie.patternExecute(currentUser, "Произошла ошибка - введен некорректный параметр.");
                return;
            }
        }

        walkieTalkie.patternExecute(currentUser, "Изменение прошло успешно.");
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void editPlayer(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        if (currentUser.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(currentUser, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null) {
            return;
        }


    }
}