package botexecution;

import common.Constants;
import common.UserDataHandler;
import dnd.mainobjects.DungeonMasterDnD;
import dnd.values.PlayerDnDCreationStage;
import dnd.mainobjects.PlayerDnD;
import game.DungeonController;
import game.entities.PlayerCharacter;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.*;

import static dnd.values.PlayerDnDCreationStage.*;

public class ChatSession implements Serializable {
    private final long chatId;
    private final boolean isPM;
    public String username;

    //параметры для поисковика
    public String sectionId = "";
    public boolean searchSuccess = false;
    public String title = "";

    //параметры для дайсроллера
    public boolean rollCustom = false;
    public ArrayDeque<String> dicePresets = new ArrayDeque<>();

    //параметры для игры
    public boolean creationOfPlayerCharacter = false;
    public boolean nameIsChosen = false;
    public HashSet<String> statProgress = new HashSet<>();
    public PlayerCharacter playerCharacter;
    public ArrayList<Integer> luck;

    public boolean gameInSession = false;
    public boolean pauseGame = false;
    public DungeonController crawler;

    //параметры для менеджера компаний
    public boolean isHavingACampaign = false;
    public boolean isEndingACampaign = false;
    public boolean campaignNameIsChosen = false;

    public boolean creationOfPlayerDnD = false;
    public boolean haltCreation = true;
    public PlayerDnDCreationStage creationStage = NAME;

    public HashMap<String, Long> campaigns = new HashMap<>();
    public Long currentCampaign;
    public PlayerDnD currentPlayer;
    public boolean editCurrentPlayer = false;

    public DungeonMasterDnD activeDm;
    public PlayerDnD activePc;

    //функции
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

    public void setUsername(String usernameGetter) {
        try {
            this.username = "@" + usernameGetter;
        } catch (Exception e) {
            this.username = "@[ДАННЫЕ УДАЛЕНЫ]";
        }
        if (Objects.equals(this.username, "@") || Objects.equals(this.username, "@null")) {
            this.username = "@[ДАННЫЕ УДАЛЕНЫ]";
        }
    }

    public void checkPresetsSize() {
        while (dicePresets.size() > Constants.MAX_CUSTOM_DICE_PRESETS) {
            dicePresets.removeFirst();
        }
    }
}
