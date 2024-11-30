package botexecution.handlers.dndhandlers;

import botexecution.handlers.corehandlers.DataHandler;
import botexecution.handlers.corehandlers.TextHandler;
import botexecution.mainobjects.ChatSession;
import botexecution.mainobjects.KeyboardFactory;
import common.Constants;
import dnd.mainobjects.DungeonMasterDnD;
import dnd.values.RoleParameters;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;

public class DnDCampaignHandler {
    private final DataHandler knowledge;
    private final TextHandler walkieTalkie;

    public DnDCampaignHandler(DataHandler knowledge, TextHandler walkieTalkie) {
        this.knowledge = knowledge;
        this.walkieTalkie = walkieTalkie;
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
                            "Произошла ошибка - перед использованием этой функции устанивите текущую компанию.");
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
}
