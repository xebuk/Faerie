package common;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;

import static org.telegram.telegrambots.abilitybots.api.util.AbilityUtils.getChatId;

public class UserDataHandler {

    public static void createChatFile(Update update) {
        File userFile = new File("../token_dir/userData/" + getChatId(update));
        if (!userFile.exists()) {
            userFile.mkdir();
        }
    }

    public static void catchMessage(Update update) {
        Date date = new Date();

        StringBuilder fileName = new StringBuilder();
        fileName.append(date.getDay()).append(" ").append(date.getHours()).append(" ").append(date.getMinutes()).append(" ").append(date.getSeconds());

        File userFileMessage = new File("../token_dir/userData/" + getChatId(update) + "/" + fileName.toString() + ".txt");

        PrintWriter message;
        try {
            message = new PrintWriter(userFileMessage);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        StringBuilder post = new StringBuilder();
        post.append(update.getMessage().getChat().getUserName()).append(" - ").append(update.getMessage().getText());

        System.out.println(post.toString());
        message.println(post.toString());
        message.close();
    }
}
