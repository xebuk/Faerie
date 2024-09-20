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

        inlineKeyboardRow.add(new InlineKeyboardButton("spells"));
        inlineKeyboardRow.get(0).setCallbackData(Constants.SPELLS);
        inlineKeyboardRow.add(new InlineKeyboardButton("items"));
        inlineKeyboardRow.get(1).setCallbackData(Constants.ITEMS);
        inlineKeyboardRow.add(new InlineKeyboardButton("bestiary"));
        inlineKeyboardRow.get(2).setCallbackData(Constants.BESTIARY);

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyboardRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static ReplyKeyboard rollVariants() {
        InlineKeyboardRow inlineKeyboardRow = new InlineKeyboardRow();

        inlineKeyboardRow.add(new InlineKeyboardButton("D20"));
        inlineKeyboardRow.get(0).setCallbackData(Constants.ROLL_D20);
        inlineKeyboardRow.add(new InlineKeyboardButton("D8"));
        inlineKeyboardRow.get(1).setCallbackData(Constants.ROLL_D8);
        inlineKeyboardRow.add(new InlineKeyboardButton("D4"));
        inlineKeyboardRow.get(2).setCallbackData(Constants.ROLL_D4);

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
