package botexecution;

import common.Constants;
import common.UserDataHandler;
import dnd.DungeonMasterDnD;
import dnd.PlayerCreationStageDnD;
import dnd.PlayerDnD;
import game.entities.PlayerCharacter;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;

import static dnd.PlayerCreationStageDnD.*;

public class ChatSession implements Serializable {
    private final long chatId;
    private final boolean isPM;
    public String username;

    public String sectionId = "";
    public boolean searchSuccess = false;
    public String title = "";

    public boolean rollCustom = false;
    public ArrayDeque<String> dicePresets = new ArrayDeque<>();

    public boolean creationOfPlayerCharacter = false;
    public boolean nameIsChosen = false;
    public HashSet<String> statProgress = new HashSet<>();
    public PlayerCharacter playerCharacter;
    public ArrayList<Integer> luck;

    public boolean isHavingACampaign = false;
    public boolean isEndingACampaign = false;
    public boolean campaignNameIsChosen = false;

    public boolean creationOfPlayerDnD = false;
    public boolean haltCreation = true;
    public PlayerCreationStageDnD creationStage = NAME;

    public DungeonMasterDnD activeDm;
    public PlayerDnD activePc;

    public ChatSession(Update update) {
        this.chatId = AbilityUtils.getChatId(update);
        this.username = UserDataHandler.findUsername(chatId);
        this.isPM = !(this.chatId < 0);
        UserDataHandler.createChatFile(String.valueOf(chatId));
    }

    public ChatSession(MessageContext ctx) {
        this.chatId = ctx.chatId();
        this.username = "@" + ctx.user().getUserName();
        this.isPM = !(this.chatId < 0);

        UserDataHandler.addUsernameToChatIdEntry(username, chatId);
        UserDataHandler.createChatFile(String.valueOf(chatId));
    }

    public Long getChatId() {
        return chatId;
    }

    public boolean isPM() {
        return isPM;
    }

    public void checkPresetsSize() {
        while (dicePresets.size() > Constants.MAX_CUSTOM_DICE_PRESETS) {
            dicePresets.removeFirst();
        }
    }
}
