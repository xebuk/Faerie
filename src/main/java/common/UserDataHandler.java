package common;

import botexecution.ChatSession;
import game.entities.PlayerCharacter;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.*;
import java.util.HashSet;

import static org.telegram.telegrambots.abilitybots.api.util.AbilityUtils.getChatId;

public class UserDataHandler {

    public static void createChatFile(String chatId) {
        File newUserDirectoryFile = new File("../token_dir/userData/" + chatId);
        if (!newUserDirectoryFile.exists()) {
            newUserDirectoryFile.mkdir();
        }

        File newUserSessionFile = new File("../token_dir/userData/" + chatId + "/session.txt");
        if (!newUserSessionFile.exists()) {
            try {
                newUserSessionFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        File newUserDiceFile = new File("../token_dir/userData/" + chatId + "/dicePresets.txt");
        if (!newUserDiceFile.exists()) {
            try {
                newUserDiceFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void saveDicePresets(HashSet<String> dicePresets, Update update) {
        File diceFile = new File("../token_dir/userData/" + getChatId(update) + "/dicePresets.txt");
        FileOutputStream out;
        ObjectOutputStream output;
        try {
            out = new FileOutputStream(diceFile);
            output = new ObjectOutputStream(out);
            output.writeObject(dicePresets);
            output.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashSet<String> readDicePresets(Update update) {
        File diceFile = new File("../token_dir/userData/" + getChatId(update) + "/dicePresets.txt");
        FileInputStream in;
        ObjectInputStream input;
        try {
            in = new FileInputStream(diceFile);
            input = new ObjectInputStream(in);
            HashSet<String> dicePreset = (HashSet<String>) input.readObject();
            input.close();
            in.close();
            return dicePreset;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

     public static void savePlayerCharacter(PlayerCharacter pc, Update update) {
        File pcFile = new File("../token_dir/userData/" + getChatId(update) + "/pcFile.txt");
        FileOutputStream out;
        ObjectOutputStream output;
        try {
            pcFile.createNewFile();
            out = new FileOutputStream(pcFile);
            output = new ObjectOutputStream(out);
            output.writeObject(pc);
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PlayerCharacter readPlayerCharacter(Update update) {
        File pcFile = new File("../token_dir/userData/" + getChatId(update) + "/pcFile.txt");
        FileInputStream in;
        ObjectInputStream input;
        try {
            in = new FileInputStream(pcFile);
            input = new ObjectInputStream(in);
            PlayerCharacter pc = (PlayerCharacter) input.readObject();
            input.close();
            in.close();
            return pc;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveSession(ChatSession cs, Update update) {
        File sessionFile = new File("../token_dir/userData/" + getChatId(update) + "/session.txt");
        FileOutputStream out;
        ObjectOutputStream output;
        try {
            sessionFile.createNewFile();
            out = new FileOutputStream(sessionFile);
            output = new ObjectOutputStream(out);
            output.writeObject(cs);
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ChatSession readSession(Update update) {
        FileInputStream in;
        ObjectInputStream input;
        try {
            in = new FileInputStream("../token_dir/userData/" + getChatId(update) + "/session.txt");
            input = new ObjectInputStream(in);
            ChatSession cs = (ChatSession) input.readObject();
            input.close();
            in.close();
            return cs;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
