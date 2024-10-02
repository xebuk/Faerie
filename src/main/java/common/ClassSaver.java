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
            System.out.println(((PlayerCharacter) input.readObject()).name);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        PlayerCharacter pc = new PlayerCharacter("a", "b", "c");
        save(pc);
        File file = new File("../token_dir/userData/chatId/pcFile.txt");
        read(file);
    }
}
