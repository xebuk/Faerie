package botexecution.handlers;

import botexecution.mainobjects.ChatSession;
import botexecution.mainobjects.KeyboardFactory;
import common.Constants;
import common.DiceNew;
import dnd.characteristics.BackgroundDnD;
import dnd.characteristics.JobDnD;
import dnd.characteristics.RaceDnD;
import dnd.equipment.ItemDnD;
import dnd.mainobjects.DungeonMasterDnD;
import dnd.mainobjects.PlayerDnD;
import dnd.values.PlayerDnDCreationStage;
import dnd.values.MasteryTypeDnD;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static dnd.values.PlayerDnDCreationStage.*;

public class DnDHandler {
    private final DataHandler knowledge;
    private final TextHandler walkieTalkie;

    public void createCampaign(MessageContext ctx) {
        String userChatId = knowledge.findChatId("@" + ctx.user().getUserName());
        ChatSession currentUser = knowledge.getSession(userChatId);
        ChatSession currentGroup = knowledge.getSession(ctx.chatId().toString());
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
        currentGroup.activeDm.campaignName = "Новая_кампания";
        currentGroup.activeDm.chatId = currentUser.getChatId();
        currentGroup.activeDm.username = currentUser.username;

        walkieTalkie.patternExecute(currentUser, Constants.CAMPAIGN_CREATION_NOTIFICATION, null, false);
        currentUser.campaigns.put("Новая_компания", currentGroup.getChatId());

        knowledge.renewListChat(currentGroup);
        knowledge.renewListChat(currentUser);
    }

    public void endCampaign(MessageContext ctx) {
        ChatSession currentGroup = knowledge.getSession(ctx.chatId().toString());

        if (currentGroup.activeDm.username.contains(ctx.user().getUserName())) {
            currentGroup.isEndingACampaign = true;
            walkieTalkie.patternExecute(currentGroup, Constants.CAMPAIGN_END_AFFIRMATION, KeyboardFactory.YesOrNoBoard(), false);
        }
        else {
            walkieTalkie.patternExecute(currentGroup, Constants.CAMPAIGN_END_RESTRICTION, null, false);
        }
        knowledge.renewListChat(currentGroup);
    }

    public void showCampaigns(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        if (currentUser.campaigns.isEmpty()) {
            walkieTalkie.patternExecute(currentUser, Constants.SHOW_CAMPAIGNS_NOTHING, null, false);
            return;
        }

        StringBuilder campaigns = new StringBuilder();
        campaigns.append("Текущие компании, которые ведете вы: \n");
        int index = 1;
        for (String i : currentUser.campaigns.keySet()) {
            campaigns.append(index);
            campaigns.append(". ");
            campaigns.append(i);
            campaigns.append("\n");
            index++;
        }

        walkieTalkie.patternExecute(currentUser, campaigns.toString(), null, false);
        knowledge.renewListChat(currentUser);
    }

    public void showCampaignGroup(MessageContext ctx) {
        ChatSession currentGroup = knowledge.getSession(ctx.chatId().toString());

        if (currentGroup.activeDm == null) {
            walkieTalkie.patternExecute(currentGroup, Constants.SHOW_CAMPAIGN_GROUP_NOTHING, null, false);
            return;
        }

        walkieTalkie.patternExecute(currentGroup, currentGroup.activeDm.campaignStatus(), null, false);
        knowledge.renewListChat(currentGroup);
    }

    public void addAPlayerDnD(MessageContext ctx) {
        String userChatId = knowledge.findChatId("@" + ctx.user().getUserName());
        ChatSession currentUser = knowledge.getSession(userChatId);
        ChatSession currentGroup = knowledge.getSession(ctx.chatId().toString());
        currentGroup.username = "@" + ctx.user().getUserName();

        if (currentGroup.activeDm == null) {
            walkieTalkie.patternExecute(currentGroup,
                    "В данной группе нет компании.", null, false);
            return;
        }
//        if (Objects.equals(currentGroup.activeDm.username, currentGroup.username)) {
//            walkieTalkie.patternExecute(currentGroup,
//                    "Вы являетесь ДМ-ом этой группы.", null, false);
//            return;
//        }

        walkieTalkie.patternExecute(currentGroup, Constants.PLAYER_CREATION_START, null, false);
        currentGroup.activeDm.playerDnDHashMap.put(currentGroup.username, new PlayerDnD());

        currentUser.activePc = new PlayerDnD();
        currentUser.activePc.playerName = currentGroup.username;
        currentUser.activePc.campaignChatId = currentGroup.getChatId();
        currentUser.creationOfPlayerDnD = true;
        walkieTalkie.patternExecute(currentUser, Constants.PLAYER_CREATION_NAME, null, false);

        knowledge.renewListChat(currentGroup);
        knowledge.renewListChat(currentUser);
    }

    public void haltCreationOfPlayerDnD(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        currentUser.creationOfPlayerDnD = !currentUser.creationOfPlayerDnD;

        if (!currentUser.creationOfPlayerDnD) {
            walkieTalkie.patternExecute(currentUser, Constants.PLAYER_CREATION_PAUSE, null, false);
        }
        else {
            currentUser.haltCreation = true;
            walkieTalkie.patternExecute(currentUser, Constants.PLAYER_CREATION_CONTINUE, null, false);
            playerDnDGeneratorAllocator.get(currentUser.creationStage).accept(currentUser, "");

        }

        knowledge.renewListChat(currentUser);
    }

    public void setCurrentCampaign(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        currentUser.currentCampaign = currentUser.campaigns.get(ctx.firstArg());
        currentUser.currentPlayer = null;
        currentUser.editCurrentPlayer = false;
        if (currentUser.currentCampaign == null) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NOT_FOUND, null, false);
            return;
        }
        walkieTalkie.patternExecute(currentUser, "Компания "
                        + ctx.firstArg()
                        + " была установлена как основная. Теперь вы можете менять её параметры и добавлять свои предметы.",
                null, false);

        knowledge.renewListChat(currentUser);
    }

    //функции для ДМ-а

    //настройка кампаний
    public void setCampaignName(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign;
        try {
            currentCampaign = knowledge.getSession(currentUser.currentCampaign.toString());
        } catch (NullPointerException e) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
        if (currentCampaign == null) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }

        if (ctx.firstArg() == null || ctx.firstArg() == "") {
            walkieTalkie.patternExecute(currentUser,
                    "Пустое поля в качестве названия запрещено. Попробуйте иное название",
                    null, false);
            return;
        }

        currentUser.campaigns.remove(currentCampaign.activeDm.campaignName);
        currentCampaign.activeDm.campaignName = ctx.firstArg();
        currentUser.campaigns.put(currentCampaign.activeDm.campaignName, currentUser.currentCampaign);

        walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_SUCCESS, null, false);

        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void setPassword(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign;
        try {
            currentCampaign = knowledge.getSession(currentUser.currentCampaign.toString());
        } catch (NullPointerException e) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
        if (currentCampaign == null) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }

        currentCampaign.activeDm.password = ctx.firstArg();
        walkieTalkie.patternExecute(currentUser, Constants.SET_PASSWORD_SUCCESS, null, false);

        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void setMulticlassLimit(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign;
        try {
            currentCampaign = knowledge.getSession(currentUser.currentCampaign.toString());
        } catch (NullPointerException e) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
        if (currentCampaign == null) {
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

        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void showPlayers(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign;
        try {
            currentCampaign = knowledge.getSession(currentUser.currentCampaign.toString());
        } catch (NullPointerException e) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
        if (currentCampaign == null) {
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
        knowledge.renewListChat(currentUser);
        knowledge.renewListChat(currentCampaign);
    }

    public void showPlayerProfile(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign;
        try {
            currentCampaign = knowledge.getSession(currentUser.currentCampaign.toString());
        } catch (NullPointerException e) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
        if (currentCampaign == null) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
        String profile = "";
        try {
            profile = currentCampaign.activeDm.playerDnDHashMap.get(ctx.firstArg()).characterProfile();
        } catch (Exception e) {
            walkieTalkie.patternExecute(currentUser, Constants.GET_PLAYER_NOT_FOUND, null, false);
            return;
        }
        walkieTalkie.articleMessaging(profile, currentUser, null);
    }

    //основная игровая механика
    public void requestARoll(MessageContext ctx) {
        ChatSession dungeonMaster = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign = knowledge.getSession(dungeonMaster.currentCampaign.toString());
        ChatSession requestedUser = knowledge.getSession(knowledge.findChatId(ctx.firstArg()));
        if (currentCampaign == null) {
            walkieTalkie.patternExecute(dungeonMaster,
                    "Установите текущую компанию, чтобы запрашивать результаты бросков у игроков",
                    null, false);
            return;
        } else if (!currentCampaign.activeDm.playerDnDHashMap.containsKey(ctx.firstArg())) {
            walkieTalkie.patternExecute(dungeonMaster, Constants.GET_PLAYER_NOT_FOUND, null, false);
            return;
        } else if (requestedUser == null) {
            walkieTalkie.patternExecute(dungeonMaster,
                    "Игрок в компании не имеет тега. Попросите его установить тег телеграма.",
                    null, false);
            return;
        }

        PlayerDnD affectedPlayer = currentCampaign.activeDm.playerDnDHashMap.get(ctx.firstArg());

        StringBuilder diceRollMessage = new StringBuilder();
        String dice = "";
        String situation = "";
        String advantage = "";
        StringBuilder bonus = new StringBuilder();
        boolean publicMessage = true;
        boolean clearThrow = false;
        boolean deathSaveThrow = false;

        String[] parameters = ctx.secondArg().split("-");
        for (String parameter: parameters) {
            if (parameter.contains("str")) {
                if (parameter.contains("svth")) {
                    bonus.setLength(0);
                    situation = "Спасение (Сила)";
                    if (affectedPlayer.characterMasteries.contains(MasteryTypeDnD.STRENGTH)) {
                        bonus.append(affectedPlayer.strengthSaveThrow);
                    }
                    else {
                        bonus.append("0");
                    }
                }
                else {
                    situation = "Сила";
                    bonus.append(affectedPlayer.strengthModifier);
                }
            }
            else if (parameter.contains("dex")) {
                bonus.setLength(0);
                if (parameter.contains("svth")) {
                    situation = "Спасение (Ловкость)";
                    if (affectedPlayer.characterMasteries.contains(MasteryTypeDnD.DEXTERITY)) {
                        bonus.append(affectedPlayer.dexteritySaveThrow);
                    }
                    else {
                        bonus.append("0");
                    }
                }
                else {
                    situation = "Ловкость";
                    bonus.append(affectedPlayer.dexterityModifier);
                }
            }
            else if (parameter.contains("con")) {
                bonus.setLength(0);
                if (parameter.contains("svth")) {
                    situation = "Спасение (Выносливость)";
                    if (affectedPlayer.characterMasteries.contains(MasteryTypeDnD.CONSTITUTION)) {
                        bonus.append(affectedPlayer.constitutionSaveThrow);
                    }
                    else {
                        bonus.append("0");
                    }
                }
                else {
                    situation = "Выносливость";
                    bonus.append(affectedPlayer.constitutionModifier);
                }
            }
            else if (parameter.contains("int")) {
                bonus.setLength(0);
                if (parameter.contains("svth")) {
                    situation = "Спасение (Интеллект)";
                    if (affectedPlayer.characterMasteries.contains(MasteryTypeDnD.INTELLIGENCE)) {
                        bonus.append(affectedPlayer.intelligenceSaveThrow);
                    }
                    else {
                        bonus.append("0");
                    }
                }
                else {
                    situation = "Интеллект";
                    bonus.append(affectedPlayer.intelligenceModifier);
                }
            }
            else if (parameter.contains("wis")) {
                bonus.setLength(0);
                if (parameter.contains("svth")) {
                    situation = "Спасение (Мудрость)";
                    if (affectedPlayer.characterMasteries.contains(MasteryTypeDnD.WISDOM)) {
                        bonus.append(affectedPlayer.wisdomSaveThrow);
                    }
                    else {
                        bonus.append("0");
                    }
                }
                else {
                    situation = "Мудрость";
                    bonus.append(affectedPlayer.wisdomModifier);
                }
            }
            else if (parameter.contains("cha")) {
                bonus.setLength(0);
                if (parameter.contains("svth")) {
                    situation = "Спасение (Харизма)";
                    if (affectedPlayer.characterMasteries.contains(MasteryTypeDnD.CHARISMA)) {
                        bonus.append(affectedPlayer.charismaSaveThrow);
                    }
                    else {
                        bonus.append("0");
                    }
                }
                else {
                    situation = "Харизма";
                    bonus.append(affectedPlayer.charismaModifier);
                }
            }
            else if (parameter.contains("dsvth")) {
                bonus.setLength(0);
                situation = "Спасение (смерть)";
                bonus.append("Успехи: ").append(affectedPlayer.deathThrowsSuccess).append("\n");
                bonus.append("Провалы: ").append(affectedPlayer.deathThrowsFailure).append("\n");
                deathSaveThrow = true;

            }
            else if (Objects.equals(parameter, "adv")) {
                advantage = "Преимущество\n";
            }
            else if (Objects.equals(parameter, "dis")) {
                advantage = "Помеха\n";
            }
            else if (Objects.equals(parameter, "clr")) {
                bonus.setLength(0);
                advantage = "Чистый бросок";
                clearThrow = true;
            }
            else if (Objects.equals(parameter, "prv")) {
                publicMessage = false;
            }
            else if (parameter.contains("d")) {
                dice = parameter;
            }
        }

        diceRollMessage.append("ДМ (").append(dungeonMaster.username).append(") вашей компании (")
                .append(currentCampaign.activeDm.campaignName).append(") запрашивает бросок: \n");

        if (!clearThrow && !deathSaveThrow) {
            diceRollMessage.append(dice).append(", ").append(situation).append(" (")
                .append((Integer.parseInt(bonus.toString()) > 0 ? "+" + bonus : bonus)).append(")").append("\n")
                .append(advantage);
        }
        else if (clearThrow) {
            diceRollMessage.append(dice).append(", ").append(situation).append("\n")
                .append(advantage);
        }
        else {
            diceRollMessage.append(dice).append(", ").append(situation).append("\n")
                    .append(bonus).append(advantage);
        }

        if (publicMessage) {
            walkieTalkie.patternExecute(currentCampaign, ctx.firstArg(), diceRollMessage.toString(), null, false);
        }
        else {
            walkieTalkie.patternExecute(requestedUser, diceRollMessage.toString(), null, false);
        }
    }

    //функции, связанные с предметами
    //стандартный вид функции
    public void addAspect(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign;
        try {
            currentCampaign = knowledge.getSession(currentUser.currentCampaign.toString());
        } catch (NullPointerException e) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
        if (currentCampaign == null) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
    }

    public void addAspectSecondStage(ChatSession cs, String searchLine) {

    }

    public void showAspect(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign;
        try {
            currentCampaign = knowledge.getSession(currentUser.currentCampaign.toString());
        } catch (NullPointerException e) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
        if (currentCampaign == null) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
    }

    public void setAspect(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign;
        try {
            currentCampaign = knowledge.getSession(currentUser.currentCampaign.toString());
        } catch (NullPointerException e) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
        if (currentCampaign == null) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
    }

    public void editAspect(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign;
        try {
            currentCampaign = knowledge.getSession(currentUser.currentCampaign.toString());
        } catch (NullPointerException e) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
        if (currentCampaign == null) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
    }

    public void deleteAspect(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign;
        try {
            currentCampaign = knowledge.getSession(currentUser.currentCampaign.toString());
        } catch (NullPointerException e) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
        if (currentCampaign == null) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
    }

    public void giveAspect(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign;
        try {
            currentCampaign = knowledge.getSession(currentUser.currentCampaign.toString());
        } catch (NullPointerException e) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
        if (currentCampaign == null) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
    }

    public void takeAspect(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign;
        try {
            currentCampaign = knowledge.getSession(currentUser.currentCampaign.toString());
        } catch (NullPointerException e) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
        if (currentCampaign == null) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
    }

    //обычные предметы
    public void addItem(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign;
        try {
            currentCampaign = knowledge.getSession(currentUser.currentCampaign.toString());
        } catch (NullPointerException e) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }
        if (currentCampaign == null) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL, null, false);
            return;
        }

        ArrayList<String> foundItems = searchItems(ctx.firstArg());
        if (foundItems.size() > 1) {
            currentUser.addingAnItem = true;
            walkieTalkie.patternExecute(currentUser, itemWriter(foundItems), null, false);
        }
        else if (foundItems.isEmpty()) {
            walkieTalkie.patternExecute(currentUser, "Ваш предмет не был найден.\n" +
                    "Вы можете создать свой предмет, введя 'Свой предмет'.", null, false);
        }
        else {
            ItemDnD item = itemAllocator.get(searchItem(foundItems.getFirst()));
            StringBuilder itemAddition = new StringBuilder();
            itemAddition.append("В ваш набор предметов добавлено: ").append(item.name).append("\n")
                    .append("Вы можете изменить описание предмета, выбрав его командой /setitem");
            walkieTalkie.patternExecute(currentUser, itemAddition.toString(), null, false);
            currentCampaign.activeDm.itemCollection.add(item);
        }
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void addItemSecondStage(ChatSession cs, String itemLine) {
        ChatSession currentCampaign = knowledge.getSession(cs.currentCampaign.toString());
        ItemDnD item = itemAllocator.get(searchItem(itemLine));

        StringBuilder itemAddition = new StringBuilder();
        itemAddition.append("В ваш набор предметов добавлено: ").append(item.name).append("\n")
                .append("Вы можете изменить описание предмета, выбрав его командой /setitem");
        walkieTalkie.patternExecute(cs, itemAddition.toString(), null, false);
        currentCampaign.activeDm.itemCollection.add(item);
        cs.addingAnItem = false;

        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(cs);
    }

    public void showItems(MessageContext ctx) {

    }

    public void setItem(MessageContext ctx) {

    }

    public void editItem(MessageContext ctx) {

    }

    //магические предметы
    public void addMagicItem(MessageContext ctx) {
        
    }

    public void showMagicItems(MessageContext ctx) {

    }

    public void setMagicItem(MessageContext ctx) {

    }

    public void editMagicItem(MessageContext ctx) {

    }

    //оружие
    public void addWeapon(MessageContext ctx) {

    }

    public void showWeapons(MessageContext ctx) {

    }

    public void setWeapon(MessageContext ctx) {

    }

    public void editWeapon(MessageContext ctx) {

    }

    //магическое оружие
    public void addMagicWeapon(MessageContext ctx) {

    }

    public void showMagicWeapons(MessageContext ctx) {

    }

    public void setMagicWeapon(MessageContext ctx) {

    }

    public void editMagicWeapon(MessageContext ctx) {

    }

    //броня
    public void addArmor(MessageContext ctx) {

    }

    public void showArmors(MessageContext ctx) {

    }

    public void setArmor(MessageContext ctx) {

    }

    public void editArmor(MessageContext ctx) {

    }

    //магическая броня
    public void addMagicArmor(MessageContext ctx) {

    }

    public void showMagicArmors(MessageContext ctx) {

    }

    public void setMagicArmor(MessageContext ctx) {

    }

    public void editMagicArmor(MessageContext ctx) {

    }

    //инструменты
    public void addInstrument(MessageContext ctx) {

    }

    public void showInstruments(MessageContext ctx) {

    }

    public void setInstrument(MessageContext ctx) {

    }

    public void editInstrument(MessageContext ctx) {

    }

    //Наборы
    public void addKit(MessageContext ctx) {

    }

    public void showKits(MessageContext ctx) {

    }

    public void setKit(MessageContext ctx) {

    }

    public void editKit(MessageContext ctx) {

    }

    //черты
    public void addFeat(MessageContext ctx) {

    }

    public void showFeats(MessageContext ctx) {

    }

    public void setFeat(MessageContext ctx) {

    }

    public void editFeat(MessageContext ctx) {

    }

    //способности
    public void addAbility(MessageContext ctx) {

    }

    public void showAbilities(MessageContext ctx) {

    }

    public void setAbility(MessageContext ctx) {

    }

    public void editAbility(MessageContext ctx) {

    }

    //заклинания
    public void addSpell(MessageContext ctx) {

    }

    public void showSpells(MessageContext ctx) {

    }

    public void setSpell(MessageContext ctx) {

    }

    public void editSpell(MessageContext ctx) {

    }

    //генератор персонажа
    public final HashMap<PlayerDnDCreationStage, BiConsumer<ChatSession, String>> playerDnDGeneratorAllocator = new HashMap<>();
    private final HashMap<String, RaceDnD> raceDnDAllocator = new HashMap<>();
    private final HashMap<String, JobDnD> jobDnDAllocator = new HashMap<>();
    private final HashMap<String, BackgroundDnD> backgroundDnDAllocator = new HashMap<>();
    private final HashMap<String, BiConsumer<PlayerDnD, Integer>> statAllocatorDnD = new HashMap<>();
    private final HashMap<String, Consumer<PlayerDnD>> skillAllocatorDnD = new HashMap<>();
    private final HashMap<String, Consumer<PlayerDnD>> languagesAllocatorDnD = new HashMap<>();

    public void nameSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, Constants.PLAYER_CREATION_NAME, null, false);
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
        cs.activePc.race = raceDnDAllocator.get(response);
        cs.activePc.size = cs.activePc.race.size;
        cs.activePc.speed = cs.activePc.race.walkingSpeed;
        cs.activePc.knownLanguages.addAll(cs.activePc.race.languages);
        cs.activePc.knownScripts.addAll(cs.activePc.race.scripts);
        cs.activePc.bonusLanguages = cs.activePc.bonusLanguages + cs.activePc.race.bonusLanguages;

        if (raceDnDAllocator.get(response).personality.isEmpty()) {
            walkieTalkie.patternExecute(cs, "Выберите класс из предложенных.", KeyboardFactory.jobDnDSelectionBoard(), false);

            cs.creationStage = JOB;
            knowledge.renewListChat(cs);
        } else {
            String racePersonality = "Черты характера персонажа: \n" + walkieTalkie.variantsMessageConfigurator(cs.activePc.race.personality);
            InlineKeyboardMarkup racePersonalityVariants = KeyboardFactory.variantsBoard(cs.activePc.race.personality.size());
            walkieTalkie.articleMessaging(racePersonality, cs, racePersonalityVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", racePersonalityVariants, false);

            cs.creationStage = RACE_PERSONALITY;
            knowledge.renewListChat(cs);
        }
    }

    public void racePersonalitySetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String racePersonality = "Черты характера персонажа: \n" + walkieTalkie.variantsMessageConfigurator(cs.activePc.race.personality);
            InlineKeyboardMarkup racePersonalityVariants = KeyboardFactory.variantsBoard(cs.activePc.race.personality.size());
            walkieTalkie.articleMessaging(racePersonality, cs, racePersonalityVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", racePersonalityVariants, false);
            return;
        }
        cs.activePc.personality.put(cs.activePc.race.name, cs.activePc.race.personality.get(Integer.parseInt(response)));

        String raceIdeal = "Идеалы персонажа: \n" + walkieTalkie.variantsMessageConfigurator(cs.activePc.race.ideal);
        InlineKeyboardMarkup raceIdealVariants = KeyboardFactory.variantsBoard(cs.activePc.race.ideal.size());
        walkieTalkie.articleMessaging(raceIdeal, cs, raceIdealVariants);
        //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", raceIdealVariants, false);

        cs.creationStage = RACE_IDEAL;
        knowledge.renewListChat(cs);
    }

    public void raceIdealSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String raceIdeal = "Идеалы персонажа: \n" + walkieTalkie.variantsMessageConfigurator(cs.activePc.race.ideal);
            InlineKeyboardMarkup raceIdealVariants = KeyboardFactory.variantsBoard(cs.activePc.race.ideal.size());
            walkieTalkie.articleMessaging(raceIdeal, cs, raceIdealVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", raceIdealVariants, false);
            return;
        }
        cs.activePc.ideals.put(cs.activePc.race.name, cs.activePc.race.ideal.get(Integer.parseInt(response)));

        String raceBond = "Привязанности персонажа: \n" + walkieTalkie.variantsMessageConfigurator(cs.activePc.race.bond);
        InlineKeyboardMarkup raceBondVariants = KeyboardFactory.variantsBoard(cs.activePc.race.bond.size());
        walkieTalkie.articleMessaging(raceBond, cs, raceBondVariants);
        //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", raceBondVariants, false);

        cs.creationStage = RACE_BOND;
        knowledge.renewListChat(cs);
    }

    public void raceBondSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String raceBond = "Привязанности персонажа: \n" + walkieTalkie.variantsMessageConfigurator(cs.activePc.race.bond);
            InlineKeyboardMarkup raceBondVariants = KeyboardFactory.variantsBoard(cs.activePc.race.bond.size());
            walkieTalkie.articleMessaging(raceBond, cs, raceBondVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", raceBondVariants, false);
            return;
        }
        cs.activePc.bonds.put(cs.activePc.race.name, cs.activePc.race.bond.get(Integer.parseInt(response)));

        String raceFlaw = "Слабости персонажа: \n" + walkieTalkie.variantsMessageConfigurator(cs.activePc.race.flaw);
        InlineKeyboardMarkup raceFlawVariants = KeyboardFactory.variantsBoard(cs.activePc.race.flaw.size());
        walkieTalkie.articleMessaging(raceFlaw, cs, raceFlawVariants);
        //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", raceFlawVariants, false);

        cs.creationStage = RACE_FLAW;
        knowledge.renewListChat(cs);
    }

    public void raceFlawSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String raceFlaw = "Слабости персонажа: \n" + walkieTalkie.variantsMessageConfigurator(cs.activePc.race.flaw);
            InlineKeyboardMarkup raceFlawVariants = KeyboardFactory.variantsBoard(cs.activePc.race.flaw.size());
            walkieTalkie.articleMessaging(raceFlaw, cs, raceFlawVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", raceFlawVariants, false);
            return;
        }
        cs.activePc.flaws.put(cs.activePc.race.name, cs.activePc.race.flaw.get(Integer.parseInt(response)));

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
        cs.activePc.mainJob = jobDnDAllocator.get(response);
        cs.activePc.characterMasteries.addAll(cs.activePc.mainJob.saveMastery);
        cs.activePc.armorProficiency.addAll(cs.activePc.mainJob.armorMastery);

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
        cs.activePc.background = backgroundDnDAllocator.get(response);
        cs.activePc.knownLanguages.addAll(cs.activePc.background.languages);
        cs.activePc.knownScripts.addAll(cs.activePc.background.scripts);
        cs.activePc.bonusLanguages = cs.activePc.bonusLanguages + cs.activePc.background.bonusLanguages;

        for (String skill : cs.activePc.background.learnedSkills) {
            skillAllocatorDnD.get(skill).accept(cs.activePc);
            cs.activePc.learnedSkills.add(skill);
        }

        if (backgroundDnDAllocator.get(response).personality.isEmpty()) {
            walkieTalkie.patternExecute(cs, "Выберите свое мировоззрение:", KeyboardFactory.alignmentDnDSelectionBoard(), false);

            cs.creationStage = ALIGNMENT;
            knowledge.renewListChat(cs);

        } else if (!backgroundDnDAllocator.get(response).specialInfo.isEmpty()) {
            String specialInfo = "Специальные качества предыстории персонажа: \n" + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.specialInfo);
            InlineKeyboardMarkup specialInfoVariants = KeyboardFactory.variantsBoard(cs.activePc.background.specialInfo.size());
            walkieTalkie.articleMessaging(specialInfo, cs, specialInfoVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", specialInfoVariants, false);

            cs.creationStage = BACKGROUND_SPECIAL_INFO;
            knowledge.renewListChat(cs);

        } else {
            String personality = "Черты характера персонажа: \n" + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.personality);
            InlineKeyboardMarkup personalityVariants = KeyboardFactory.variantsBoard(cs.activePc.background.personality.size());
            walkieTalkie.articleMessaging(personality, cs, personalityVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", personalityVariants, false);

            cs.creationStage = BACKGROUND_PERSONALITY;
            knowledge.renewListChat(cs);
        }
    }

    public void backgroundSpecialInfoSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String specialInfo = "Специальные качества предыстории персонажа: \n" + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.specialInfo);
            InlineKeyboardMarkup specialInfoVariants = KeyboardFactory.variantsBoard(cs.activePc.background.specialInfo.size());
            walkieTalkie.articleMessaging(specialInfo, cs, specialInfoVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", specialInfoVariants, false);
            return;
        }
        cs.activePc.specialBackgroundQuality = cs.activePc.background.specialInfo.get(Integer.parseInt(response));

        String personality = "Черты характера персонажа: \n" + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.personality);
        InlineKeyboardMarkup personalityVariants = KeyboardFactory.variantsBoard(cs.activePc.background.personality.size());
        walkieTalkie.articleMessaging(personality, cs, personalityVariants);
        //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", personalityVariants, false);

        cs.creationStage = BACKGROUND_PERSONALITY;
        knowledge.renewListChat(cs);
    }

    public void backgroundPersonalitySetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String personality = "Черты характера персонажа: \n" + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.personality);
            InlineKeyboardMarkup personalityVariants = KeyboardFactory.variantsBoard(cs.activePc.background.personality.size());
            walkieTalkie.articleMessaging(personality, cs, personalityVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", personalityVariants, false);
            return;
        }
        cs.activePc.personality.put(cs.activePc.background.name, cs.activePc.background.personality.get(Integer.parseInt(response)));

        String ideal = "Идеалы персонажа: \n" + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.ideal);
        InlineKeyboardMarkup idealVariants = KeyboardFactory.variantsBoard(cs.activePc.background.ideal.size());
        walkieTalkie.articleMessaging(ideal, cs, idealVariants);
        //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", idealVariants, false);

        cs.creationStage = BACKGROUND_IDEAL;
        knowledge.renewListChat(cs);
    }

    public void backgroundIdealSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String ideal = "Идеалы персонажа: \n" + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.ideal);
            InlineKeyboardMarkup idealVariants = KeyboardFactory.variantsBoard(cs.activePc.background.ideal.size());
            walkieTalkie.articleMessaging(ideal, cs, idealVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", idealVariants, false);
            return;
        }
        cs.activePc.ideals.put(cs.activePc.background.name, cs.activePc.background.ideal.get(Integer.parseInt(response)));

        String bond = "Привязанности персонажа: \n" + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.bond);
        InlineKeyboardMarkup bondVariants = KeyboardFactory.variantsBoard(cs.activePc.background.bond.size());
        walkieTalkie.articleMessaging(bond, cs, bondVariants);
        //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", bondVariants, false);

        cs.creationStage = BACKGROUND_BOND;
        knowledge.renewListChat(cs);
    }

    public void backgroundBondSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String bond = "Привязанности персонажа: \n" + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.bond);
            InlineKeyboardMarkup bondVariants = KeyboardFactory.variantsBoard(cs.activePc.background.bond.size());
            walkieTalkie.articleMessaging(bond, cs, bondVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", bondVariants, false);
            return;
        }
        cs.activePc.bonds.put(cs.activePc.background.name, cs.activePc.background.bond.get(Integer.parseInt(response)));

        String flaw = "Слабости персонажа: \n" + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.flaw);
        InlineKeyboardMarkup flawVariants = KeyboardFactory.variantsBoard(cs.activePc.background.flaw.size());
        walkieTalkie.articleMessaging(flaw, cs, flawVariants);
        //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", flawVariants, false);

        cs.creationStage = BACKGROUND_FLAW;
        knowledge.renewListChat(cs);
    }

    public void backgroundFlawSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String flaw = "Слабости персонажа: \n" + walkieTalkie.variantsMessageConfigurator(cs.activePc.background.flaw);
            InlineKeyboardMarkup flawVariants = KeyboardFactory.variantsBoard(cs.activePc.background.flaw.size());
            walkieTalkie.articleMessaging(flaw, cs, flawVariants);
            //walkieTalkie.patternExecute(cs, "Выберите вариант из данных:                          ", flawVariants, false);
            return;
        }
        cs.activePc.flaws.put(cs.activePc.background.name, cs.activePc.background.flaw.get(Integer.parseInt(response)));

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

        walkieTalkie.patternExecute(cs, "Распределите характеристики.", null, false);

        cs.activePc.luck = DiceNew.D6FourTimesCreation();
        walkieTalkie.patternExecute(cs, DiceNew.D6FourTimes(cs.activePc.luck), KeyboardFactory.assignStatsBoardDnD(cs.activePc.allocatedStats), false);

        cs.creationStage = STATS1;
        knowledge.renewListChat(cs);
    }

    public void statSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            cs.activePc.luck = DiceNew.D6FourTimesCreation();
            walkieTalkie.patternExecute(cs, DiceNew.D6FourTimes(cs.activePc.luck), KeyboardFactory.assignStatsBoardDnD(cs.activePc.allocatedStats), false);
            return;
        }
        statAllocatorDnD.get(response).accept(cs.activePc, cs.activePc.luck.get(4));
        if (cs.creationStage == STATS6) {
            cs.activePc.allocatedStats.clear();
            cs.activePc.luck.clear();

            String skills = "Выберите навыки персонажа. Осталось навыков: " + cs.activePc.mainJob.startingSkillAmount;
            InlineKeyboardMarkup skillsVariants = KeyboardFactory.assignSkillsBoardDnD(cs.activePc.mainJob.skillRoster, cs.activePc.learnedSkills);
            walkieTalkie.patternExecute(cs, skills, skillsVariants, false);
            cs.creationStage = SKILLS;
            knowledge.renewListChat(cs);
            return;
        }

        cs.activePc.allocatedStats.add(response);
        cs.activePc.luck = DiceNew.D6FourTimesCreation();

        walkieTalkie.patternExecute(cs, DiceNew.D6FourTimes(cs.activePc.luck), KeyboardFactory.assignStatsBoardDnD(cs.activePc.allocatedStats), false);
        cs.creationStage = cs.creationStage.next();
        knowledge.renewListChat(cs);
    }

    public void skillsSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            String skills = "Выберите навыки персонажа. Осталось навыков: " + cs.activePc.mainJob.startingSkillAmount;
            InlineKeyboardMarkup skillsVariants = KeyboardFactory.assignSkillsBoardDnD(cs.activePc.mainJob.skillRoster, cs.activePc.learnedSkills);
            walkieTalkie.patternExecute(cs, skills, skillsVariants, false);
            return;
        }
        skillAllocatorDnD.get(response).accept(cs.activePc);
        cs.activePc.learnedSkills.add(response);
        cs.activePc.mainJob.startingSkillAmount--;

        if (cs.activePc.mainJob.startingSkillAmount == 0) {
            String languages = "Выберите языки персонажа. Осталось языков: " + cs.activePc.bonusLanguages;
            InlineKeyboardMarkup languagesVariants = KeyboardFactory.languagesDnDSelectionBoard(cs.activePc.knownLanguages);
            walkieTalkie.patternExecute(cs, languages, languagesVariants, false);
            cs.creationStage = LANGUAGE;
            knowledge.renewListChat(cs);
        } else {
            String skills = "Осталось навыков: " + cs.activePc.mainJob.startingSkillAmount;
            InlineKeyboardMarkup skillsVariants = KeyboardFactory.assignSkillsBoardDnD(cs.activePc.mainJob.skillRoster, cs.activePc.learnedSkills);
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

        if (cs.activePc.bonusLanguages == 0) {
            walkieTalkie.patternExecute(cs, "Введите возраст персонажа.", null, false);
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
            walkieTalkie.patternExecute(cs, "Введите возраст персонажа.", null, false);
            return;
        }
        cs.activePc.age = response;
        walkieTalkie.patternExecute(cs, "Введите рост персонажа.", null, false);

        cs.creationStage = HEIGHT;
        knowledge.renewListChat(cs);
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
        knowledge.renewListChat(cs);
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
        knowledge.renewListChat(cs);
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
        knowledge.renewListChat(cs);
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
        knowledge.renewListChat(cs);
    }

    public void hairSetter(ChatSession cs, String response) {
        if (cs.haltCreation) {
            cs.haltCreation = false;
            walkieTalkie.patternExecute(cs, "Введите описание волос персонажа.", null, false);
            return;
        }
        cs.activePc.hair = response;
        ChatSession currentGroup = knowledge.getSession(cs.activePc.campaignChatId.toString());
        currentGroup.activeDm.playerDnDHashMap.put(cs.username, cs.activePc);

        cs.activePc = null;
        cs.creationOfPlayerDnD = false;
        cs.creationStage = NAME;

        knowledge.renewListChat(currentGroup); // небезопасная вещь, надо либо вывесить предупреждение (сделано), либо что-то с этим решить
        knowledge.renewListChat(cs);

        walkieTalkie.patternExecute(cs, Constants.PLAYER_CREATION_END, null, false);
    }

    //список предметов
    public final HashMap<String, ItemDnD> itemAllocator = new HashMap<>();

    public ArrayList<String> searchItems(String askedItem) {
        Set<String> items = itemAllocator.keySet();
        ArrayList<String> searchedItems = new ArrayList<>();

        for (String item : items) {
            if (item.contains(askedItem)) {
                searchedItems.add(item);
            }
        }

        return searchedItems;
    }

    public String searchItem(String askedItem) {
        Set<String> items = itemAllocator.keySet();
        int distance;

        LevenshteinDistance env = new LevenshteinDistance();
        int minSimilarityDistance = 1999999999;
        String resItem = "";

        for (String item: items) {
            distance = env.apply(item.toLowerCase(), askedItem.toLowerCase());
            if (distance < minSimilarityDistance) {
                resItem = item;
                minSimilarityDistance = distance;
            }
        }

        return resItem;
    }

    public String itemWriter(ArrayList<String> items) {
        StringBuilder result = new StringBuilder();

        result.append("Результаты поиска:").append("\n");

        for (String item : items) {
            result.append(item).append("\n");
        }

        result.append("Введите имя предмета, который вы хотите добавить в свой набор.");
        return result.toString();
    }

    //конструктор для хендлера
    public DnDHandler(DataHandler knowledge, TextHandler walkieTalkie) {
        this.knowledge = knowledge;
        this.walkieTalkie = walkieTalkie;

        raceDnDAllocator.put("Гном", new RaceDnD.GnomeDnD());
        raceDnDAllocator.put("Дварф", new RaceDnD.DwarfDnD());
        raceDnDAllocator.put("Драконорожденный", new RaceDnD.DragonbornDnD());
        raceDnDAllocator.put("Полуорк", new RaceDnD.HalfOrcDnD());
        raceDnDAllocator.put("Полурослик", new RaceDnD.HalflingDnD());
        raceDnDAllocator.put("Полуэльф", new RaceDnD.HalfElfDnD());
        raceDnDAllocator.put("Тифлинг", new RaceDnD.TieflingDnD());
        raceDnDAllocator.put("Человек", new RaceDnD.HumanDnD());
        raceDnDAllocator.put("Человек (Вариант)", new RaceDnD.HumanVariantDnD());
        raceDnDAllocator.put("Эльф", new RaceDnD.ElfDnD());

        jobDnDAllocator.put("Бард", new JobDnD.BardDnD());
        jobDnDAllocator.put("Варвар", new JobDnD.BarbarianDnD());
        jobDnDAllocator.put("Воин", new JobDnD.FighterDnD());
        jobDnDAllocator.put("Волшебник", new JobDnD.WizardDnD());
        jobDnDAllocator.put("Друид", new JobDnD.DruidDnD());
        jobDnDAllocator.put("Жрец", new JobDnD.ClericDnD());
        jobDnDAllocator.put("Изобретатель", new JobDnD.ArtificerDnD());
        jobDnDAllocator.put("Колдун", new JobDnD.WarlockDnD());
        jobDnDAllocator.put("Монах", new JobDnD.MonkDnD());
        jobDnDAllocator.put("Паладин", new JobDnD.PaladinDnD());
        jobDnDAllocator.put("Плут", new JobDnD.RogueDnD());
        jobDnDAllocator.put("Следопыт", new JobDnD.RangerDnD());
        jobDnDAllocator.put("Чародей", new JobDnD.SorcererDnD());

        backgroundDnDAllocator.put("Артист", new BackgroundDnD.EntertainerDnD());
        backgroundDnDAllocator.put("Беспризорник", new BackgroundDnD.UrchinDnD());
        backgroundDnDAllocator.put("Благородный", new BackgroundDnD.NobleDnD());
        backgroundDnDAllocator.put("Гильдейский ремесленник", new BackgroundDnD.GuildArtisanDnD());
        backgroundDnDAllocator.put("Моряк", new BackgroundDnD.SailorDnD());
        backgroundDnDAllocator.put("Мудрец", new BackgroundDnD.SageDnD());
        backgroundDnDAllocator.put("Народный герой", new BackgroundDnD.FolkHeroDnD());
        backgroundDnDAllocator.put("Отшельник", new BackgroundDnD.HermitDnD());
        backgroundDnDAllocator.put("Пират", new BackgroundDnD.PirateDnD());
        backgroundDnDAllocator.put("Преступник", new BackgroundDnD.CriminalDnD());
        backgroundDnDAllocator.put("Прислужник", new BackgroundDnD.AcolyteDnD());
        backgroundDnDAllocator.put("Солдат", new BackgroundDnD.SoldierDnD());
        backgroundDnDAllocator.put("Чужеземец", new BackgroundDnD.OutlanderDnD());
        backgroundDnDAllocator.put("Шарлатан", new BackgroundDnD.CharlatanDnD());

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

        itemAllocator.put("Новый предмет", new ItemDnD());

        itemAllocator.put("Абак", new ItemDnD.AbacusDnD());
        itemAllocator.put("Флакон кислоты", new ItemDnD.VialOfAcidDnD());
        itemAllocator.put("Фляга алхимического огня", new ItemDnD.FlaskOfAlchemistFireDnD());

        itemAllocator.put("Стрела", new ItemDnD.ArrowDnD());
        itemAllocator.put("Игла для духовой трубки", new ItemDnD.BlowgunNeedleDnD());
        itemAllocator.put("Арбалетный болт", new ItemDnD.CrossbowBoltDnD());
        itemAllocator.put("Снаряд для пращи", new ItemDnD.SlingBulletDnD());

        itemAllocator.put("Флакон противоядия", new ItemDnD.VialOfAntitoxinDnD());

        itemAllocator.put("Кристалл", new ItemDnD.CrystalDnD());
        itemAllocator.put("Сфера", new ItemDnD.OrbDnD());
        itemAllocator.put("Жезл", new ItemDnD.RodDnD());
        itemAllocator.put("Посох", new ItemDnD.StaffDnD());
        itemAllocator.put("Волшебная палочка", new ItemDnD.WandDnD());

        itemAllocator.put("Рюкзак", new ItemDnD.BackpackDnD());
        itemAllocator.put("Металлический шарик", new ItemDnD.BallBearingDnD());
        itemAllocator.put("Бочка", new ItemDnD.BarrelDnD());
        itemAllocator.put("Корзина", new ItemDnD.BasketDnD());
        itemAllocator.put("Спальник", new ItemDnD.BedrollDnD());
        itemAllocator.put("Колокольчик", new ItemDnD.BellDnD());
        itemAllocator.put("Одеяло", new ItemDnD.BlanketDnD());
        itemAllocator.put("Блок и лебедка", new ItemDnD.BlockAndTackleDnD());
        itemAllocator.put("Книга", new ItemDnD.BookDnD());
        itemAllocator.put("Стеклянная бутылка", new ItemDnD.GlassBottleDnD());
        itemAllocator.put("Ведро", new ItemDnD.BucketDnD());
        itemAllocator.put("Калтроп", new ItemDnD.CaltropDnD());
        itemAllocator.put("Свеча", new ItemDnD.CandleDnD());
        itemAllocator.put("Контейнер для арбалетных болтов", new ItemDnD.CaseForCrossbowBoltsDnD());
        itemAllocator.put("Контейнер для карт и свитков", new ItemDnD.CaseForMapsAndScrollsDnD());
        itemAllocator.put("Цепь (10 футов)", new ItemDnD.ChainDnD());
        itemAllocator.put("Мел", new ItemDnD.ChalkDnD());
        itemAllocator.put("Сундук", new ItemDnD.ChestDnD());
        itemAllocator.put("Комплект для лазания", new ItemDnD.ClimberKitDnD());
        itemAllocator.put("Обычная одежда", new ItemDnD.CommonClothesDnD());
        itemAllocator.put("Костюм", new ItemDnD.CostumeClothesDnD());
        itemAllocator.put("Отличная одежда", new ItemDnD.FineClothesDnD());
        itemAllocator.put("Дорожная одежда", new ItemDnD.TravelerClothesDnD());
        itemAllocator.put("Мешочек с компонентами", new ItemDnD.ComponentPouchDnD());
        itemAllocator.put("Ломик", new ItemDnD.CrowbarDnD());

        itemAllocator.put("Веточка омелы", new ItemDnD.SprigOfMistletoeDnD());
        itemAllocator.put("Тотем", new ItemDnD.TotemDnD());
        itemAllocator.put("Деревянный посох", new ItemDnD.WoodenStaffDnD());
        itemAllocator.put("Тисовая палочка", new ItemDnD.YewWandDnD());

        itemAllocator.put("Комплект для рыбалки", new ItemDnD.FishingTackleDnD());
        itemAllocator.put("Фляга или большая кружка", new ItemDnD.FlaskOrTankardDnD());
        itemAllocator.put("Крюк-кошка", new ItemDnD.GrapplingHookDnD());
        itemAllocator.put("Молоток", new ItemDnD.HammerDnD());
        itemAllocator.put("Кузнечный молот", new ItemDnD.SledgehammerDnD());
        itemAllocator.put("Комплект целителя", new ItemDnD.HealerKitDnD());

        itemAllocator.put("Амулет", new ItemDnD.AmuletDnD());
        itemAllocator.put("Эмблема", new ItemDnD.EmblemDnD());
        itemAllocator.put("Реликварий", new ItemDnD.ReliquaryDnD());

        itemAllocator.put("Фляга святой воды", new ItemDnD.FlaskOfHolyWaterDnD());
        itemAllocator.put("Песочные часы", new ItemDnD.HourglassDnD());
        itemAllocator.put("Охотничий капкан", new ItemDnD.HuntingTrapDnD());
        itemAllocator.put("Чернила (бутылочка на 30 грамм)", new ItemDnD.InkDnD());
        itemAllocator.put("Писчее перо", new ItemDnD.InkPenDnD());
        itemAllocator.put("Кувшин или графин", new ItemDnD.JugOrPitcherDnD());
        itemAllocator.put("Лестница (10 футов)", new ItemDnD.LadderDnD());
        itemAllocator.put("Лампа", new ItemDnD.LampDnD());
        itemAllocator.put("Направленный фонарь", new ItemDnD.BullseyeLanternDnD());
        itemAllocator.put("Закрытый фонарь", new ItemDnD.HoodedLanternDnD());
        itemAllocator.put("Замок", new ItemDnD.LockDnD());
        itemAllocator.put("Увеличительное стекло", new ItemDnD.MagnifyingGlassDnD());
        itemAllocator.put("Кандалы", new ItemDnD.ManaclesDnD());
        itemAllocator.put("Столовый набор", new ItemDnD.MessKitDnD());
        itemAllocator.put("Стальное зеркало", new ItemDnD.SteelMirrorDnD());
        itemAllocator.put("Фляга масла", new ItemDnD.FlaskOfOilDnD());
        itemAllocator.put("Бумага", new ItemDnD.PaperDnD());
        itemAllocator.put("Пергамент", new ItemDnD.ParchmentDnD());
        itemAllocator.put("Флакон духов", new ItemDnD.VialOfPerfumeDnD());
        itemAllocator.put("Горняцкая кирка", new ItemDnD.MinersPickDnD());
        itemAllocator.put("Шлямбур", new ItemDnD.PitonDnD());
        itemAllocator.put("Флакон простого яда", new ItemDnD.VialOfBasicPoisonDnD());
        itemAllocator.put("Шест (10 футов)", new ItemDnD.PoleDnD());
        itemAllocator.put("Железный горшок", new ItemDnD.IronPotDnD());
        itemAllocator.put("Зелье лечения", new ItemDnD.PotionOfHealingDnD());
        itemAllocator.put("Кошель", new ItemDnD.PouchDnD());
        itemAllocator.put("Колчан", new ItemDnD.QuiverDnD());
        itemAllocator.put("Портативный таран", new ItemDnD.PortableRamDnD());
        itemAllocator.put("Рационы (1 день)", new ItemDnD.RationsDnD());
        itemAllocator.put("Ряса", new ItemDnD.RobesDnD());
        itemAllocator.put("Пеньковая веревка (50 футов)", new ItemDnD.HempenRopeDnD());
        itemAllocator.put("Шелковая веревка (50 футов)", new ItemDnD.SilkRopeDnD());
        itemAllocator.put("Мешок", new ItemDnD.SackDnD());
        itemAllocator.put("Торговые весы", new ItemDnD.MerchantScaleDnD());
        itemAllocator.put("Воск", new ItemDnD.SealingWaxDnD());
        itemAllocator.put("Лопата", new ItemDnD.ShovelDnD());
        itemAllocator.put("Сигнальный свисток", new ItemDnD.SignalWhistleDnD());
        itemAllocator.put("Кольцо-печатка", new ItemDnD.SignetRingDnD());
        itemAllocator.put("Мыло", new ItemDnD.SoapDnD());
        itemAllocator.put("Книга заклинаний", new ItemDnD.SpellbookDnD());
        itemAllocator.put("Железный шип", new ItemDnD.IronSpikeDnD());
        itemAllocator.put("Подзорная труба", new ItemDnD.SpyglassDnD());
        itemAllocator.put("Двухместная палатка", new ItemDnD.TwoPersonTentDnD());
        itemAllocator.put("Трутница", new ItemDnD.TinderboxDnD());
        itemAllocator.put("Факел", new ItemDnD.TorchDnD());
        itemAllocator.put("Флакон", new ItemDnD.VialDnD());
        itemAllocator.put("Бурдюк", new ItemDnD.WaterskinDnD());
        itemAllocator.put("Точильный камень", new ItemDnD.WhetstoneDnD());
    }
}