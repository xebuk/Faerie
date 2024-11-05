package common;

import botexecution.ChatSession;
import game.entities.PlayerCharacter;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.*;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

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

    public static void saveDicePresets(ArrayDeque<String> dicePresets, Update update) {
        File diceFile = new File("../token_dir/userData/" + getChatId(update) + "/dicePresets.txt");
        if (!diceFile.exists()) {
            try {
                diceFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

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

    public static ArrayDeque<String> readDicePresets(Update update) {
        File diceFile = new File("../token_dir/userData/" + getChatId(update) + "/dicePresets.txt");
        if (!diceFile.exists()) {
            try {
                diceFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileInputStream in;
        ObjectInputStream input;
        try {
            in = new FileInputStream(diceFile);
            input = new ObjectInputStream(in);
            ArrayDeque<String> dicePreset = (ArrayDeque<String>) input.readObject();
            input.close();
            in.close();
            return dicePreset;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

     public static void savePlayerCharacter(PlayerCharacter pc, Update update) {
        File pcFile = new File("../token_dir/userData/" + getChatId(update) + "/pcFile.txt");
        if (!pcFile.exists()) {
            try {
                pcFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

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
        if (!pcFile.exists()) {
            try {
                pcFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

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

    public static void saveSession(ChatSession cs) {
        File sessionFile = new File("../token_dir/userData/" + cs.getChatId() + "/session.txt");
        if (!sessionFile.exists()) {
            try {
                sessionFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileOutputStream out;
        ObjectOutputStream output;
        try {
            out = new FileOutputStream(sessionFile);
            output = new ObjectOutputStream(out);
            output.writeObject(cs);
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ChatSession readSession(Update update) {
        File sessionFile = new File("../token_dir/userData/" + getChatId(update) + "/session.txt");
        if (!sessionFile.exists()) {
            try {
                sessionFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileInputStream in;
        ObjectInputStream input;
        try {
            in = new FileInputStream(sessionFile);
            input = new ObjectInputStream(in);
            ChatSession cs = (ChatSession) input.readObject();
            input.close();
            in.close();
            return cs;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ChatSession readSession(long chatId) {
        File sessionFile = new File("../token_dir/userData/" + chatId + "/session.txt");
        if (!sessionFile.exists()) {
            try {
                sessionFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileInputStream in;
        ObjectInputStream input;
        try {
            in = new FileInputStream(sessionFile);
            input = new ObjectInputStream(in);
            ChatSession cs = (ChatSession) input.readObject();
            input.close();
            in.close();
            return cs;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createUsernameToChatIdHashMapFile() {
        HashMap<String, Long> chatIdToUsername = new HashMap<>();

        File hashMapFile = new File("../token_dir/userData/usernameToChatID.txt");
        if (!hashMapFile.exists()) {
            try {
                hashMapFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileOutputStream out;
        ObjectOutputStream output;
        try {
            out = new FileOutputStream(hashMapFile);
            output = new ObjectOutputStream(out);
            output.writeObject(chatIdToUsername);
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addUsernameToChatIdEntry(String username, long chatId) {
        File hashMapFile = new File("../token_dir/userData/usernameToChatID.txt");

        FileInputStream in;
        ObjectInputStream input;
        FileOutputStream out;
        ObjectOutputStream output;
        try {
            in = new FileInputStream(hashMapFile);
            input = new ObjectInputStream(in);
            HashMap<String, Long> usernameToChatId = (HashMap<String, Long>) input.readObject();
            input.close();
            in.close();

            usernameToChatId.put(username, chatId);

            out = new FileOutputStream(hashMapFile);
            output = new ObjectOutputStream(out);
            output.writeObject(usernameToChatId);
            output.close();
            out.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Long findChatId(String username) {
        File hashMapFile = new File("../token_dir/userData/usernameToChatID.txt");

        FileInputStream in;
        ObjectInputStream input;
        try {
            in = new FileInputStream(hashMapFile);
            input = new ObjectInputStream(in);
            HashMap<String, Long> usernameToChatId = (HashMap<String, Long>) input.readObject();
            input.close();
            in.close();

            return usernameToChatId.get(username);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String findUsername(long chatId) {
        File hashMapFile = new File("../token_dir/userData/usernameToChatID.txt");

        FileInputStream in;
        ObjectInputStream input;
        try {
            in = new FileInputStream(hashMapFile);
            input = new ObjectInputStream(in);
            HashMap<String, Long> usernameToChatId = (HashMap<String, Long>) input.readObject();
            input.close();
            in.close();

            for (Map.Entry<String, Long> entry : usernameToChatId.entrySet()) {
                if (entry.getValue() == chatId) {
                    return entry.getKey();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "";
    }
}
