package botexecution;

import common.UserDataHandler;
import game.entities.PlayerCharacter;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class ChatSession implements Serializable {
    private final long chatId;

    public String sectionId = "";
    public boolean searchSuccess = false;
    public String title = "";

    public boolean rollCustom = false;
    public HashSet<String> dicePresets;

    public boolean creationOfPlayerCharacter = false;
    public HashSet<String> statProgress = new HashSet<>();
    public PlayerCharacter playerCharacter;
    public ArrayList<Integer> luck;

    public ChatSession(Update update) {
        this.chatId = AbilityUtils.getChatId(update);
        UserDataHandler.createChatFile(String.valueOf(chatId));
    }

    public ChatSession(MessageContext ctx) {
        this.chatId = ctx.chatId();
        UserDataHandler.createChatFile(String.valueOf(chatId));
    }

    public Long getChatId() {
        return chatId;
    }
}
