package botexecution.handlers.dndhandlers;

import botexecution.handlers.corehandlers.DataHandler;
import botexecution.handlers.corehandlers.TextHandler;
import botexecution.mainobjects.ChatSession;
import botexecution.mainobjects.KeyboardFactory;
import common.Constants;
import dnd.mainobjects.DungeonMasterDnD;
import dnd.mainobjects.PlayerDnD;
import dnd.values.RoleParameters;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;

import java.util.Map;
import java.util.Objects;

public class DnDCampaignHandler {
    private final DataHandler knowledge;
    private final TextHandler walkieTalkie;
    private final DnDNotificationHandler secretMessages;

    public DnDCampaignHandler(DataHandler knowledge, TextHandler walkieTalkie, DnDNotificationHandler secretMessages) {
        this.knowledge = knowledge;
        this.walkieTalkie = walkieTalkie;
        this.secretMessages = secretMessages;

    }

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

    public void createCampaign(MessageContext ctx) {
        String userChatId = knowledge.findChatId("@" + ctx.user().getUserName());
        ChatSession currentUser = knowledge.getSession(userChatId);
        ChatSession currentGroup = knowledge.getSession(ctx.chatId().toString());

        if (currentGroup.role == RoleParameters.CAMPAIGN) {
            walkieTalkie.patternExecute(currentGroup, Constants.CAMPAIGN_CREATION_EXISTS);
            return;
        }

        walkieTalkie.patternExecute(currentGroup, Constants.CAMPAIGN_CREATION_START);

        currentGroup.activeDm = new DungeonMasterDnD();
        currentGroup.role = RoleParameters.CAMPAIGN;

        currentGroup.activeDm.campaignName = "Новая_кампания";
        currentGroup.activeDm.lockVault = true;
        currentGroup.activeDm.dungeonMasterChatId = currentUser.getChatId();
        currentGroup.activeDm.dungeonMasterUsername = currentUser.username;
        currentGroup.currentCampaign = currentGroup.getChatId();

        walkieTalkie.patternExecute(currentUser, Constants.CAMPAIGN_CREATION_NOTIFICATION);
        currentUser.campaignsAsDungeonMaster.put("Новая_компания", currentGroup.getChatId());

        knowledge.renewListChat(currentGroup);
        knowledge.renewListChat(currentUser);
    }

    public void endCampaign(MessageContext ctx) {
        ChatSession currentGroup = knowledge.getSession(ctx.chatId().toString());

        if (currentGroup.activeDm.dungeonMasterUsername.contains(ctx.user().getUserName())) {
            currentGroup.role = RoleParameters.CAMPAIGN_END_STAGE;
            walkieTalkie.patternExecute(currentGroup, Constants.CAMPAIGN_END_AFFIRMATION, KeyboardFactory.YesOrNoBoard(), false);
        }
        else {
            walkieTalkie.patternExecute(currentGroup, Constants.CAMPAIGN_END_RESTRICTION);
        }
        knowledge.renewListChat(currentGroup);
    }

    public void showCampaigns(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        StringBuilder campaigns = new StringBuilder();
        int index = 1;
        switch (ctx.firstArg()) {
            case "-p" -> {
                if (currentUser.campaignsAsPlayer.isEmpty()) {
                    walkieTalkie.patternExecute(currentUser,
                            "На данный момент вы не участвуете в кампаниях.");
                    return;
                }

                campaigns.append("Текущие компании, в которых вы участвуете: \n");
                for (String i : currentUser.campaignsAsPlayer.keySet()) {
                    campaigns.append(index);
                    campaigns.append(". ");
                    campaigns.append(i);
                    campaigns.append("\n");
                    index++;
                }
            }
            case "-d" -> {
                if (currentUser.campaignsAsDungeonMaster.isEmpty()) {
                    walkieTalkie.patternExecute(currentUser,
                            "На данный момент вы не ведете никакие компании.");
                    return;
                }

                campaigns.append("Текущие компании, которые ведете вы: \n");
                for (String i : currentUser.campaignsAsDungeonMaster.keySet()) {
                    campaigns.append(index);
                    campaigns.append(". ");
                    campaigns.append(i);
                    campaigns.append("\n");
                    index++;
                }
            }
            default -> walkieTalkie.patternExecute(currentUser,
                    "Произошла ошибка - такого параметра у команды нет.\n" +
                            "Проверьте правильность ввода по справке в /help [команда]");
        }
        walkieTalkie.patternExecute(currentUser, campaigns.toString());
        knowledge.renewListChat(currentUser);
    }

    public void showCampaignGroup(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        switch (currentUser.role) {
            case NONE -> {
                if (currentUser.getChatId() > 0) {
                    walkieTalkie.patternExecute(currentUser,
                            "Произошла ошибка - перед использованием этой функции установите текущую компанию.");
                } else {
                    walkieTalkie.patternExecute(currentUser, Constants.SHOW_CAMPAIGN_GROUP_NOTHING);
                }
                return;
            }
            case PLAYER, DUNGEON_MASTER -> {
                ChatSession currentGroup = knowledge.getSession(currentUser.currentCampaign.toString());
                walkieTalkie.patternExecute(currentUser, currentGroup.activeDm.campaignStatus());
            }
            case CAMPAIGN -> {
                walkieTalkie.patternExecute(currentUser, currentUser.activeDm.campaignStatus());
            }
        }

        if (currentUser.activeDm == null) {
            walkieTalkie.patternExecute(currentUser, Constants.SHOW_CAMPAIGN_GROUP_NOTHING);
            return;
        }

        walkieTalkie.patternExecute(currentUser, currentUser.activeDm.campaignStatus());
        knowledge.renewListChat(currentUser);
    }

    public void setCurrentCampaign(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        if (currentUser.role == RoleParameters.CAMPAIGN) {
            walkieTalkie.patternExecute(currentUser,
                    "Произошла ошибка - данный момент вы находитесь внутри беседы компании.\n" +
                            "Для установки компании пройдите в личные сообщения бота и введите команду там.");
            return;
        }

        if (secretMessages.isNotLegal(ctx, "2")) {
            return;
        }

        switch (ctx.firstArg()) {
            case "-p" -> {
                currentUser.role = RoleParameters.PLAYER;
                currentUser.currentCampaign = currentUser.campaignsAsPlayer.get(ctx.secondArg());
                if (currentUser.currentCampaign == null) {
                    walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NOT_FOUND);
                    return;
                }
                walkieTalkie.patternExecute(currentUser, "Вы были установлены как игрок компании "
                                + ctx.secondArg()
                                + ". Теперь вы можете экипировать предметы и делать запросы ДМ-у.");
            }
            case "-d" -> {
                currentUser.role = RoleParameters.DUNGEON_MASTER;
                currentUser.currentCampaign = currentUser.campaignsAsDungeonMaster.get(ctx.secondArg());
                if (currentUser.currentCampaign == null) {
                    walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NOT_FOUND);
                    return;
                }
                walkieTalkie.patternExecute(currentUser, "Вы были установлены как ДМ компании "
                                + ctx.secondArg()
                                + ". Теперь вы можете менять её параметры и добавлять свои предметы.");
            }
            default -> walkieTalkie.patternExecute(currentUser,
                    "Произошла ошибка - такого параметра у команды нет.\n" +
                            "Проверьте правильность ввода по справке в /help [команда]");
        }
        knowledge.renewListChat(currentUser);
    }

    public void setCampaignName(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        if (currentUser.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(currentUser, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (ctx.firstArg() == null || Objects.equals(ctx.firstArg(), "")) {
            walkieTalkie.patternExecute(ctx,
                    "Пустое поля в качестве названия запрещено.\n" +
                            "Попробуйте иное название или проверьте правильность ввода по справке в /help [команда].");
            return;
        }

        for (String tag: currentCampaign.activeDm.campaignParty.keySet()) {
            ChatSession affectedUser = knowledge.getSession(knowledge.findChatId(tag));
            affectedUser.campaignsAsPlayer.remove(currentCampaign.activeDm.campaignName);
            affectedUser.campaignsAsPlayer.put(ctx.firstArg(), currentCampaign.getChatId());
        }

        currentUser.campaignsAsDungeonMaster.remove(currentCampaign.activeDm.campaignName);
        currentCampaign.activeDm.campaignName = ctx.firstArg();
        currentUser.campaignsAsDungeonMaster.put(currentCampaign.activeDm.campaignName, currentUser.currentCampaign);

        walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_SUCCESS);

        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void setPassword(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        if (currentUser.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(currentUser, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (ctx.firstArg() == null || Objects.equals(ctx.firstArg(), "")) {
            walkieTalkie.patternExecute(ctx,
                    "Пустое поля в качестве пароля запрещено.\n" +
                            "Попробуйте иное выражение или проверьте правильность ввода по справке в /help [команда].");
            return;
        }

        currentCampaign.activeDm.campaignPassword = ctx.firstArg();
        walkieTalkie.patternExecute(currentUser, Constants.SET_PASSWORD_SUCCESS);

        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void setMulticlassLimit(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        if (currentUser.role != RoleParameters.DUNGEON_MASTER) {
            walkieTalkie.patternExecute(currentUser, "Доступ запрещен - вы не ДМ данной компании.");
            return;
        }

        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "int1")) {
            return;
        }

        currentCampaign.activeDm.multiclassLimit = Integer.parseInt(ctx.firstArg());

        walkieTalkie.patternExecute(currentUser, "Лимит классов на персонажа установлен. Теперь максимальное количество классов - "
                + currentCampaign.activeDm.multiclassLimit);
        walkieTalkie.patternExecute(currentUser, Constants.SET_MULTICLASS_LIMIT_ZERO);

        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void showPlayers(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null) {
            return;
        }

        StringBuilder playersList = new StringBuilder();
        playersList.append("Игроки текущей компании: \n");
        int index = 1;
        for (Map.Entry<String, PlayerDnD> player : currentCampaign.activeDm.campaignParty.entrySet()) {
            playersList.append(index).append(". ").append(player.getKey()).append(" - ").append(player.getValue().name);
            index++;
        }

        walkieTalkie.patternExecute(currentUser, playersList.toString());
        knowledge.renewListChat(currentUser);
        knowledge.renewListChat(currentCampaign);
    }

    public void showPlayerProfile(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "prof1")) {
            return;
        }

        String username = ctx.firstArg();
        if (currentUser.role != RoleParameters.DUNGEON_MASTER && !Objects.equals(username, currentUser.username)) {
            walkieTalkie.patternExecute(currentUser, "Доступ запрещен - вы не ДМ данной компании.\n" +
                    "На данный момент вы можете посмотреть только профиль своего персонажа, введя ваш тег.");
            return;
        }

        String profile = currentCampaign.activeDm.campaignParty.get(username).characterProfile();;
        walkieTalkie.articleMessaging(profile, currentUser, null);
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }
}
