package botexecution.handlers.corehandlers;

import botexecution.mainobjects.ChatSession;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DataHandler {
    private final HashMap<String, ChatSession> listOfSessions;
    private final HashMap<String, String> listOfUsernames;
    private final HashMap<String, Integer> listOfArticleIds;
    private final Timer saver;

    public DataHandler(boolean noTimer) {
        this.listOfSessions = new HashMap<>();
        this.listOfUsernames = readUsernames();
        this.listOfArticleIds = readArticleIds();

        readSessions();

        if (!noTimer) {
            this.saver = new Timer("Храни_Меня_Господь", true);
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
        } else {
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

    public String searchArticleId(String section, String name) {
        try {
            int index = Integer.parseInt(name);
            return name;
        } catch (NumberFormatException ignored) {}

        Set<String> articleNames = this.listOfArticleIds.keySet();

        LevenshteinDistance env = new LevenshteinDistance();
        int minSimilarityDistance = Integer.MAX_VALUE;
        int resArticleId = 1;

        int distance;
        int englishNameStart;
        for (String articleName: articleNames) {
            int categoryEnd = articleName.indexOf("]");

            if (!articleName.substring(1, categoryEnd).equals(section)) {
                continue;
            }
            englishNameStart = articleName.lastIndexOf("[") + 1;


            if (name.charAt(0) <= 'z' && name.charAt(0) >= 'a') {
                distance = env.apply(articleName.substring(englishNameStart, articleName.length() - 1), name);
            }
            else {
                try {
                    distance = env.apply(articleName.substring(categoryEnd + 1, englishNameStart - 1), name);
                } catch (StringIndexOutOfBoundsException e) {
                    distance = env.apply(articleName.substring(categoryEnd + 1, categoryEnd + 1 + name.length()), name);
                }
            }

            if (distance < minSimilarityDistance) {
                minSimilarityDistance = distance;
                resArticleId = this.listOfArticleIds.get(articleName);
            }
        }

        return String.valueOf(resArticleId);
    }

    public ArrayList<String> searchArticleIds(String section, String name) {
        ArrayList<String> results = new ArrayList<>();

        Set<String> articleNames = this.listOfArticleIds.keySet();

        String clearName;
        int sectionEnd;
        for (String articleName: articleNames) {
            sectionEnd = articleName.indexOf("]");
            if (!articleName.substring(1, sectionEnd).equals(section)) {
                continue;
            }
            clearName = articleName.substring(sectionEnd + 1);

            if (clearName.toLowerCase().contains(name.toLowerCase())) {
                results.add(String.valueOf(this.listOfArticleIds.get(articleName)));
                results.add(clearName);
            }
        }

        return results;
    }

    //сохранение и чтение всего
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

    public void saveArticleIds() {
        File articleIdsFile = new File("../token_dir/articleIDs.txt");

        HashMap<String, Integer> listOfArticleIdsInFile = readArticleIds();
        listOfArticleIdsInFile.putAll(this.listOfArticleIds);

        try (FileOutputStream articleIdsOut = new FileOutputStream(articleIdsFile);
             ObjectOutputStream articleIdsOutput = new ObjectOutputStream(articleIdsOut)) {
            articleIdsOutput.writeObject(listOfArticleIdsInFile);
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

    public HashMap<String, Integer> readArticleIds() {
        File articleIdsFile = new File("../token_dir/articleIDs.txt");

        HashMap<String, Integer> articleIds = new HashMap<>();

        try (FileInputStream articleIdsIn = new FileInputStream(articleIdsFile);
             ObjectInputStream articleIdsInput = new ObjectInputStream(articleIdsIn)) {
            articleIds = (HashMap<String, Integer>) articleIdsInput.readObject();
        } catch (IOException | ClassNotFoundException ignored) {}

        return articleIds;
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

    public void readArticleIdsBackUp() {
        String searchIDRoute = "../token_dir/searchID/";

        File articleIdsFile = new File(searchIDRoute);
        String[] files = articleIdsFile.list();

        StringBuilder searchFileName = new StringBuilder();
        Path searchFile;

        for (String i : files) {
            searchFileName.append(searchIDRoute).append(i);
            searchFile = Path.of(searchFileName.toString());
            List<String> ids;
            try {
                ids = Files.readAllLines(searchFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (String id : ids) {
                String[] info = id.split("~ ");

                String modifiedId = "[" + i.substring(0, i.length() - 4) + "]" + info[1];
                System.out.println(modifiedId);
                this.listOfArticleIds.put(modifiedId, Integer.parseInt(info[0]));
            }

            searchFileName.setLength(0);
        }
    }
}
