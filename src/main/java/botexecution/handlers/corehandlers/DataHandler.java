package botexecution.handlers.corehandlers;

import botexecution.mainobjects.ChatSession;

import java.io.*;
import java.util.*;

public class DataHandler {
    private final HashMap<String, ChatSession> listOfSessions;
    private HashMap<String, String> listOfUsernames;
    private final Timer saver;

    public DataHandler(boolean noTimer) {
        this.listOfSessions = new HashMap<>();
        this.listOfUsernames = readUsernames();

        readSessions();

        if (!noTimer) {
            this.saver = new Timer("Храни_Меня_Господь",true);
            saver.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Сохраняю ники");
                    saveUsernames();
                    System.out.println("Сохраняю сессии");
                    if (listOfSessions.isEmpty()) {
                        System.out.println("Сессий для сохранения нет");
                        return;
                    }
                    saveSessions();
                }
            }, 60000, 60000);
        }
        else {
            this.saver = null;
        }
    }

    public void renewListChat(ChatSession cs) {
        listOfSessions.put(cs.getChatId().toString(), cs);
    }

    public void renewListUsername(ChatSession cs) {
        if (cs.isPM()) {
            listOfUsernames.put(cs.username, cs.getChatId().toString());
        }
    }

    public ChatSession getSession(String chatId) {
        ChatSession cs = listOfSessions.get(chatId);
        if (cs == null) {
            readSessionToList(chatId);
            cs = listOfSessions.get(chatId);
        }
        return cs;
    }

    public String findChatId(String username) {
        return listOfUsernames.get(username);
    }

    public void readSessionToList(String chatId) {
        StringBuilder route = new StringBuilder("../token_dir/userData/");
        route.append(chatId).append("/session.txt");
        File chatFile = new File(route.toString());

        try (FileInputStream chatIn = new FileInputStream(chatFile);
             ObjectInputStream chatInput = new ObjectInputStream(chatIn)) {
            listOfSessions.put(chatId, (ChatSession) chatInput.readObject());
        } catch (IOException | ClassNotFoundException ignored) {}
    }

    //сохранение и чтение всех сессий
    public void saveSessions() {
        StringBuilder chatPath = new StringBuilder();
        File chatFile;

        for (String chatId: listOfSessions.keySet()) {
            chatPath.append("../token_dir/userData/").append(chatId).append("/session.txt");
            chatFile = new File(chatPath.toString());

            System.out.println(chatId);

            try (FileOutputStream chatOut = new FileOutputStream(chatFile);
                 ObjectOutputStream chatOutput = new ObjectOutputStream(chatOut)) {
                chatOutput.writeObject(listOfSessions.get(chatId));
            } catch (IOException ignored) {}

            chatPath.setLength(0);
        }

        listOfSessions.clear();
    }

    public void saveUsernames() {
        File usernamesFile = new File("../token_dir/userData/usernameToChatID.txt");

        HashMap<String, String> listOfUsernamesInFile = readUsernames();
        listOfUsernamesInFile.putAll(this.listOfUsernames);

        try (FileOutputStream usernamesOut = new FileOutputStream(usernamesFile);
             ObjectOutputStream usernamesOutput = new ObjectOutputStream(usernamesOut)) {
            usernamesOutput.writeObject(listOfUsernamesInFile);
        } catch (IOException ignored) {}
    }

    public void readSessions() {
        String mainRoute = "../token_dir/userData/";

        File chatIds = new File(mainRoute);
        String[] files = chatIds.list();

        StringBuilder chatId = new StringBuilder();
        File chatFile;

        for (String i: files) {
            try {
                int number = Integer.parseInt(i);
            } catch (NumberFormatException e) {
                continue;
            }

            chatId.append(mainRoute).append(i).append("/session.txt");
            chatFile = new File(chatId.toString());

            try (FileInputStream chatIn = new FileInputStream(chatFile);
                 ObjectInputStream chatInput = new ObjectInputStream(chatIn)) {
                listOfSessions.put(i, (ChatSession) chatInput.readObject());

            } catch (IOException | ClassNotFoundException ignored) {}
        }
    }

    public HashMap<String, String> readUsernames() {
        File usernamesFile = new File("../token_dir/userData/usernameToChatID.txt");

        HashMap<String, String> listOfUsernamesFile = new HashMap<>();

        try (FileInputStream usernamesIn = new FileInputStream(usernamesFile);
             ObjectInputStream usernamesInput = new ObjectInputStream(usernamesIn)) {
            listOfUsernamesFile = (HashMap<String, String>) usernamesInput.readObject();
        } catch (IOException | ClassNotFoundException ignored) {}

        return listOfUsernamesFile;
    }

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
    }
}
