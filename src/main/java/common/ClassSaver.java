package common;

import game.entities.PlayerCharacter;

import java.io.*;

public class ClassSaver {

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
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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
            System.out.println(pc.job);
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
