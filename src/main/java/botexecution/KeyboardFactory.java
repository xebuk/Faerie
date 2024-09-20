package botexecution;

import common.Constants;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class KeyboardFactory {
    public static ReplyKeyboard searchEngine() {
        InlineKeyboardRow inlineKeyRow1 = new InlineKeyboardRow();

        inlineKeyRow1.add(new InlineKeyboardButton("Spells"));
        inlineKeyRow1.get(0).setCallbackData(Constants.SPELLS);
        inlineKeyRow1.add(new InlineKeyboardButton("Items"));
        inlineKeyRow1.get(1).setCallbackData(Constants.ITEMS);
        inlineKeyRow1.add(new InlineKeyboardButton("Bestiary"));
        inlineKeyRow1.get(2).setCallbackData(Constants.BESTIARY);

        InlineKeyboardRow inlineKeyRow2 = new InlineKeyboardRow();

        inlineKeyRow2.add(new InlineKeyboardButton("Races"));
        inlineKeyRow2.get(0).setCallbackData(Constants.RACES);
        inlineKeyRow2.add(new InlineKeyboardButton("Classes"));
        inlineKeyRow2.get(1).setCallbackData(Constants.CLASSES);

        InlineKeyboardRow inlineKeyRow3 = new InlineKeyboardRow();

        inlineKeyRow3.add(new InlineKeyboardButton("Feats"));
        inlineKeyRow3.get(0).setCallbackData(Constants.FEATS);
        inlineKeyRow3.add(new InlineKeyboardButton("Backgrounds"));
        inlineKeyRow3.get(1).setCallbackData(Constants.BACKGROUNDS);

        ArrayList<InlineKeyboardRow> inlineKeyRows = new ArrayList<>();
        inlineKeyRows.add(inlineKeyRow1);
        inlineKeyRows.add(inlineKeyRow2);
        inlineKeyRows.add(inlineKeyRow3);

        return new InlineKeyboardMarkup(inlineKeyRows);
    }

    public static ReplyKeyboard rollVariants() {
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
        inlineKeyRow2.add(new InlineKeyboardButton("Stats Roll (4d6)"));
        inlineKeyRow2.get(1).setCallbackData(Constants.ROLL_4D6);

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow1);
        inlineKeyboardRows.add(inlineKeyRow2);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static ReplyKeyboard rollAdvantage() {
        InlineKeyboardRow inlineKeyRow = new InlineKeyboardRow();

        inlineKeyRow.add(new InlineKeyboardButton("Yes"));
        inlineKeyRow.get(0).setCallbackData(Constants.ADVANTAGE);
        inlineKeyRow.add(new InlineKeyboardButton("No"));
        inlineKeyRow.get(1).setCallbackData(Constants.DISADVANTAGE);

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static ReplyKeyboardMarkup setOfCommands() {
        KeyboardRow keyRow1 = new KeyboardRow();
        keyRow1.add(new KeyboardButton("/search"));
        keyRow1.add(new KeyboardButton("/roll"));

        KeyboardRow keyRow2 = new KeyboardRow();
        keyRow2.add(new KeyboardButton("/hello"));
        keyRow2.add(new KeyboardButton("/help"));

        ArrayList<KeyboardRow> keyRowList = new ArrayList<>();
        keyRowList.add(keyRow1);
        keyRowList.add(keyRow2);

        return new ReplyKeyboardMarkup(keyRowList);
    }
}
