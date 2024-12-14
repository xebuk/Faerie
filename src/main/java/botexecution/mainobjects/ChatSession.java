package botexecution.mainobjects;

import botexecution.commands.CurrentProcess;
import botexecution.commands.KeyboardValues;
import botexecution.handlers.corehandlers.DataHandler;
import common.Constants;
import common.SearchCategories;
import dnd.mainobjects.DungeonMasterDnD;
import dnd.values.EditingParameters;
import dnd.values.PlayerDnDCreationStage;
import dnd.mainobjects.PlayerDnD;
import dnd.values.RoleParameters;
import game.environment.DungeonController;
import game.entities.PlayerCharacter;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.io.Serializable;
import java.util.*;

import static dnd.values.PlayerDnDCreationStage.*;

public class ChatSession implements Serializable {
    public String chatTitle;
    private final long chatId;
    private final boolean isPM;
    public String username = "";

    //общие настройки
    public KeyboardValues currentKeyboard = KeyboardValues.COMMON;
    public ArrayDeque<Message> messagesOnDeletion = new ArrayDeque<>();
    public CurrentProcess currentContext = CurrentProcess.FREE;

    //параметры для поисковика
    public SearchCategories sectionId = SearchCategories.NONE;
    public boolean searchSuccess = false;
    public String title = "";

    //параметры для дайсроллера
    public boolean rollCustom = false;
    public ArrayDeque<String> dicePresets = new ArrayDeque<>();

    //параметры для игры
    public boolean nameIsChosen = false;
    public HashSet<String> statProgress = new HashSet<>();
    public PlayerCharacter playerCharacter;
    public ArrayList<Integer> luck;

    public boolean pauseGame = false;
    public DungeonController crawler;

    //параметры для менеджера компаний
    public boolean haltCreation = false;
    public PlayerDnDCreationStage creationStage = NAME;

    public HashMap<String, Long> campaignsAsDungeonMaster = new HashMap<>();
    public HashMap<String, Long> campaignsAsPlayer = new HashMap<>();
    public String whoIsRolling = "";

    public RoleParameters role = RoleParameters.NONE;
    public Long currentCampaign;

    public EditingParameters editNote = EditingParameters.NONE;
    public int editNoteIndex = 0;

    public String whoIsStyling = "";

    public String editQuestParameter = "";

    public int editPrestigeJobIndex = 0;

    public String whoIsEdited = "";
    public String lastParameter = "";

    public DungeonMasterDnD activeDm;
    public PlayerDnD activePc;

    //функции
    public ChatSession(String chatId) {
        this.chatId = Long.parseLong(chatId);
        this.isPM = !(this.chatId < 0);
        DataHandler.createChatFile(chatId);
    }

    public ChatSession(Update update) {
        this.chatId = AbilityUtils.getChatId(update);
        this.isPM = !(this.chatId < 0);
        DataHandler.createChatFile(String.valueOf(chatId));
    }

    public ChatSession(MessageContext ctx) {
        this.chatId = ctx.chatId();
        this.username = "@" + ctx.user().getUserName();
        this.isPM = !(this.chatId < 0);
        DataHandler.createChatFile(String.valueOf(chatId));
    }

    public Long getChatId() {
        return chatId;
    }

    public boolean isPM() {
        return isPM;
    }

    public void setUsername(String username) {
        try {
            this.username = "@" + username;
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
