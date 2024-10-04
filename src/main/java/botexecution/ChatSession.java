package botexecution;

import game.entities.PlayerCharacter;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import static org.telegram.telegrambots.abilitybots.api.util.AbilityUtils.getChatId;

public class ChatSession implements Serializable {
    private final Long chatId;
    private final File userFile;
    public String sectionId = "";
    public boolean searchSuccess = false;
    public String title = "";

    public boolean rollCustom = false;

    public boolean creationOfPc = false;
    public HashSet<String> statProgress = new HashSet<>();
    public PlayerCharacter pc;
    public ArrayList<Integer> luck;

    public ChatSession(Update update) {
        this.chatId = AbilityUtils.getChatId(update);
        this.userFile = new File("../token_dir/userData/" + chatId);
    }

    public ChatSession(MessageContext ctx) {
        this.chatId = ctx.chatId();
        this.userFile = new File("../token_dir/userData/" + chatId);;
    }

    public Long getChatId() {
        return chatId;
    }
}
