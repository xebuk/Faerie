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
        inlineKeyboardRow.get(0).setCallbackData(Constants.spells);
        inlineKeyboardRow.add(new InlineKeyboardButton("items"));
        inlineKeyboardRow.get(1).setCallbackData(Constants.items);
        inlineKeyboardRow.add(new InlineKeyboardButton("bestiary"));
        inlineKeyboardRow.get(2).setCallbackData(Constants.bestiary);

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyboardRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }


}
