package common;

import botexecution.ChatSession;
import game.entities.PlayerCharacter;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.*;
import java.util.Date;

import static org.telegram.telegrambots.abilitybots.api.util.AbilityUtils.getChatId;

public class UserDataHandler {

    public static void createChatFile(Update update) {
        File userFile = new File("../token_dir/userData/" + getChatId(update));
        if (!userFile.exists()) {
            userFile.mkdir();

            File userFileMessage = new File("../token_dir/userData/" + getChatId(update) + "/dicePresets.txt");

            try {
                FileWriter message = new FileWriter(userFileMessage);
                message.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void saveDicePresets(Update update) throws IOException {
        FileWriter preset;
        try {
            preset = new FileWriter("../token_dir/userData/" + getChatId(update) + "/dicePresets.txt", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        preset.append(update.getMessage().getText() + "\n");
        preset.close();
    }

    public static void catchMessage(Update update) {
        Date date = new Date();

        StringBuilder fileName = new StringBuilder();
        fileName.append(date.getDay()).append("/")
                .append(date.getMonth()).append("/")
                .append(date.getYear()).append(" ")
                .append(date.getHours()).append(" ").append(date.getMinutes()).append(" ").append(date.getSeconds());

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

     public static void save(PlayerCharacter pc) {
        File pcFile = new File("../token_dir/userData/chatId/pcFile.txt");
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

    public static void save(ChatSession cs) {
        File sessionFile = new File("../token_dir/userData/" + cs.getChatId() + "/pcFile.txt");
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

    public static void read(File file) {
        FileInputStream in;
        ObjectInputStream input;
        try {
            in = new FileInputStream(file);
            input = new ObjectInputStream(in);
            PlayerCharacter pc = (PlayerCharacter) input.readObject();
            System.out.println(pc.job.startHp);
            System.out.println(pc.job.startArmorClass);
            System.out.println(pc.job.startAttackRoll);
            System.out.println(pc.health);
            System.out.println(pc.armorClass);
            System.out.println(pc.attackPower);
            System.out.println(pc.strength);
            System.out.println(pc.dexterity);
            System.out.println(pc.constitution);
            System.out.println(pc.intelligence);
            System.out.println(pc.wisdom);
            System.out.println(pc.charisma);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        File file = new File("../token_dir/userData/chatId/pcFile.txt");
        read(file);
    }
}
