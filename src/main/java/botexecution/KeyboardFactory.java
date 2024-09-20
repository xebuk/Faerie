package botexecution;

import common.Constants;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;

public class KeyboardFactory {
    public static ReplyKeyboard searchEngine() {
        InlineKeyboardRow inlineKeyboardRow = new InlineKeyboardRow();

        inlineKeyboardRow.add(new InlineKeyboardButton("Spells"));
        inlineKeyboardRow.get(0).setCallbackData(Constants.SPELLS);
        inlineKeyboardRow.add(new InlineKeyboardButton("Items"));
        inlineKeyboardRow.get(1).setCallbackData(Constants.ITEMS);
        inlineKeyboardRow.add(new InlineKeyboardButton("Bestiary"));
        inlineKeyboardRow.get(2).setCallbackData(Constants.BESTIARY);

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyboardRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static ReplyKeyboard rollVariants() {
        InlineKeyboardRow inlineKeyboardRow1 = new InlineKeyboardRow();

        inlineKeyboardRow1.add(new InlineKeyboardButton("d20"));
        inlineKeyboardRow1.get(0).setCallbackData(Constants.ROLL_D20);
        inlineKeyboardRow1.add(new InlineKeyboardButton("2d20"));
        inlineKeyboardRow1.get(1).setCallbackData(Constants.ROLL_2D20);
        inlineKeyboardRow1.add(new InlineKeyboardButton("d8"));
        inlineKeyboardRow1.get(2).setCallbackData(Constants.ROLL_D8);
        inlineKeyboardRow1.add(new InlineKeyboardButton("d4"));
        inlineKeyboardRow1.get(3).setCallbackData(Constants.ROLL_D4);

        InlineKeyboardRow inlineKeyboardRow2 = new InlineKeyboardRow();

        inlineKeyboardRow2.add(new InlineKeyboardButton("d6"));
        inlineKeyboardRow2.get(0).setCallbackData(Constants.ROLL_D6);
        inlineKeyboardRow2.add(new InlineKeyboardButton("Stats Roll (4d6)"));
        inlineKeyboardRow2.get(1).setCallbackData(Constants.ROLL_4D6);

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyboardRow1);
        inlineKeyboardRows.add(inlineKeyboardRow2);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static ReplyKeyboard rollAdvantage() {
        InlineKeyboardRow inlineKeyboardRow = new InlineKeyboardRow();

        inlineKeyboardRow.add(new InlineKeyboardButton("Yes"));
        inlineKeyboardRow.get(0).setCallbackData(Constants.ADVANTAGE);
        inlineKeyboardRow.add(new InlineKeyboardButton("No"));
        inlineKeyboardRow.get(1).setCallbackData(Constants.DISADVANTAGE);

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyboardRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup setOfCommands() {
        InlineKeyboardRow inlineKeyboardRow = new InlineKeyboardRow();

        inlineKeyboardRow.add(new InlineKeyboardButton("Search"));
        inlineKeyboardRow.get(0).setCallbackData(Constants.SEARCH_COMMAND);
        inlineKeyboardRow.add(new InlineKeyboardButton("Dice"));
        inlineKeyboardRow.get(1).setCallbackData(Constants.DICE_COMMAND);

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyboardRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }
}
