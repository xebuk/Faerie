package botexecution;

import common.Constants;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.HashSet;

public class KeyboardFactory {
    public static InlineKeyboardMarkup searchBoard() {
        InlineKeyboardRow inlineKeyRow1 = new InlineKeyboardRow();

        inlineKeyRow1.add(new InlineKeyboardButton("Заклинания"));
        inlineKeyRow1.get(0).setCallbackData(Constants.SPELLS);

        InlineKeyboardRow inlineKeyRow2 = new InlineKeyboardRow();

        inlineKeyRow2.add(new InlineKeyboardButton("Предметы"));
        inlineKeyRow2.get(0).setCallbackData(Constants.ITEMS);
        inlineKeyRow2.add(new InlineKeyboardButton("Бестиарий"));
        inlineKeyRow2.get(1).setCallbackData(Constants.BESTIARY);

        InlineKeyboardRow inlineKeyRow3 = new InlineKeyboardRow();

        inlineKeyRow3.add(new InlineKeyboardButton("Расы"));
        inlineKeyRow3.get(0).setCallbackData(Constants.RACES);
        inlineKeyRow3.add(new InlineKeyboardButton("Классы"));
        inlineKeyRow3.get(1).setCallbackData(Constants.CLASSES);

        InlineKeyboardRow inlineKeyRow4 = new InlineKeyboardRow();

        inlineKeyRow4.add(new InlineKeyboardButton("Черты"));
        inlineKeyRow4.get(0).setCallbackData(Constants.FEATS);
        inlineKeyRow4.add(new InlineKeyboardButton("Предыстории"));
        inlineKeyRow4.get(1).setCallbackData(Constants.BACKGROUNDS);

        ArrayList<InlineKeyboardRow> inlineKeyRows = new ArrayList<>();
        inlineKeyRows.add(inlineKeyRow1);
        inlineKeyRows.add(inlineKeyRow2);
        inlineKeyRows.add(inlineKeyRow3);
        inlineKeyRows.add(inlineKeyRow4);

        return new InlineKeyboardMarkup(inlineKeyRows);
    }

    public static InlineKeyboardMarkup rollVariantsBoard() {
        InlineKeyboardRow inlineKeyRow1 = new InlineKeyboardRow();

        inlineKeyRow1.add(new InlineKeyboardButton("d20"));
        inlineKeyRow1.get(0).setCallbackData(Constants.ROLL_D20);
        inlineKeyRow1.add(new InlineKeyboardButton("2d20"));
        inlineKeyRow1.get(1).setCallbackData(Constants.ROLL_2D20);
        inlineKeyRow1.add(new InlineKeyboardButton("d8"));
        inlineKeyRow1.get(2).setCallbackData(Constants.ROLL_D8);
        inlineKeyRow1.add(new InlineKeyboardButton("d4"));
        inlineKeyRow1.get(3).setCallbackData(Constants.ROLL_D4);

        InlineKeyboardRow inlineKeyRow2 = new InlineKeyboardRow();

        inlineKeyRow2.add(new InlineKeyboardButton("d6"));
        inlineKeyRow2.get(0).setCallbackData(Constants.ROLL_D6);
        inlineKeyRow2.add(new InlineKeyboardButton("Бросок статов (4d6)"));
        inlineKeyRow2.get(1).setCallbackData(Constants.ROLL_4D6);

        InlineKeyboardRow inlineKeyRow3 = new InlineKeyboardRow();
        inlineKeyRow3.add(new InlineKeyboardButton("Кастомные кости"));
        inlineKeyRow3.get(0).setCallbackData(Constants.CUSTOM_DICE);

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow1);
        inlineKeyboardRows.add(inlineKeyRow2);
        inlineKeyboardRows.add(inlineKeyRow3);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup rollAdvantageBoard() {
        InlineKeyboardRow inlineKeyRow = new InlineKeyboardRow();

        inlineKeyRow.add(new InlineKeyboardButton("Да"));
        inlineKeyRow.get(0).setCallbackData(Constants.ADVANTAGE);
        inlineKeyRow.add(new InlineKeyboardButton("Нет"));
        inlineKeyRow.get(1).setCallbackData(Constants.DISADVANTAGE);

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup rollCustomBoard(ChatSession cs) {
        InlineKeyboardRow inlineKeyRow = new InlineKeyboardRow();

        for (String diceVariant : cs.dicePresets) {
            InlineKeyboardButton dice = new InlineKeyboardButton(diceVariant);
            dice.setCallbackData(diceVariant);
            inlineKeyRow.add(dice);
        }

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup YesOrNoBoard() {
        InlineKeyboardRow inlineKeyRow = new InlineKeyboardRow();

        inlineKeyRow.add(new InlineKeyboardButton("Да"));
        inlineKeyRow.get(0).setCallbackData("Да");
        inlineKeyRow.add(new InlineKeyboardButton("Нет"));
        inlineKeyRow.get(1).setCallbackData("Нет");

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup jobSelectionBoard() {
        InlineKeyboardRow inlineKeyRow1 = new InlineKeyboardRow();

        inlineKeyRow1.add(new InlineKeyboardButton("Воин"));
        inlineKeyRow1.get(0).setCallbackData(Constants.CREATION_MENU_FIGHTER);
        inlineKeyRow1.add(new InlineKeyboardButton("Клерик"));
        inlineKeyRow1.get(1).setCallbackData(Constants.CREATION_MENU_CLERIC);

        InlineKeyboardRow inlineKeyRow2 = new InlineKeyboardRow();

        inlineKeyRow2.add(new InlineKeyboardButton("Маг"));
        inlineKeyRow2.get(0).setCallbackData(Constants.CREATION_MENU_MAGE);
        inlineKeyRow2.add(new InlineKeyboardButton("Плут"));
        inlineKeyRow2.get(1).setCallbackData(Constants.CREATION_MENU_ROGUE);
        inlineKeyRow2.add(new InlineKeyboardButton("Следопыт"));
        inlineKeyRow2.get(2).setCallbackData(Constants.CREATION_MENU_RANGER);

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow1);
        inlineKeyboardRows.add(inlineKeyRow2);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup assignStatsBoard(HashSet<String> bannedButtons) {
        InlineKeyboardRow inlineKeyRow = new InlineKeyboardRow();

        if (!bannedButtons.contains(Constants.CREATION_MENU_STRENGTH)) {
            InlineKeyboardButton strength = new InlineKeyboardButton("Сила");
            strength.setCallbackData(Constants.CREATION_MENU_STRENGTH);
            inlineKeyRow.add(strength);
        }

        if (!bannedButtons.contains(Constants.CREATION_MENU_DEXTERITY)) {
            InlineKeyboardButton dexterity = new InlineKeyboardButton("Ловкость");
            dexterity.setCallbackData(Constants.CREATION_MENU_DEXTERITY);
            inlineKeyRow.add(dexterity);
        }

        if (!bannedButtons.contains(Constants.CREATION_MENU_CONSTITUTION)) {
            InlineKeyboardButton constitution = new InlineKeyboardButton("Выносливость");
            constitution.setCallbackData(Constants.CREATION_MENU_CONSTITUTION);
            inlineKeyRow.add(constitution);
        }

        if (!bannedButtons.contains(Constants.CREATION_MENU_INTELLIGENCE)) {
            InlineKeyboardButton intelligence = new InlineKeyboardButton("Интеллект");
            intelligence.setCallbackData(Constants.CREATION_MENU_INTELLIGENCE);
            inlineKeyRow.add(intelligence);
        }

        if (!bannedButtons.contains(Constants.CREATION_MENU_WISDOM)) {
            InlineKeyboardButton wisdom = new InlineKeyboardButton("Мудрость");
            wisdom.setCallbackData(Constants.CREATION_MENU_WISDOM);
            inlineKeyRow.add(wisdom);
        }

        if (!bannedButtons.contains(Constants.CREATION_MENU_CHARISMA)) {
            InlineKeyboardButton charisma = new InlineKeyboardButton("Харизма");
            charisma.setCallbackData(Constants.CREATION_MENU_CHARISMA);
            inlineKeyRow.add(charisma);
        }

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static ReplyKeyboardMarkup setOfCommandsBoard() {
        KeyboardRow keyRow1 = new KeyboardRow();
        keyRow1.add(new KeyboardButton("/search"));
        keyRow1.add(new KeyboardButton("/roll"));

        KeyboardRow keyRow2 = new KeyboardRow();
        keyRow2.add(new KeyboardButton("/mofu"));
        keyRow2.add(new KeyboardButton("/help"));

        KeyboardRow keyRow3 = new KeyboardRow();
        keyRow3.add(new KeyboardButton("/credits"));

        ArrayList<KeyboardRow> keyRowList = new ArrayList<>();
        keyRowList.add(keyRow1);
        keyRowList.add(keyRow2);
        keyRowList.add(keyRow3);

        ReplyKeyboardMarkup keyBoard = new ReplyKeyboardMarkup(keyRowList);
        keyBoard.setResizeKeyboard(true);

        return keyBoard;
    }
}
