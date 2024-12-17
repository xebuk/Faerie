package botexecution.handlers.corehandlers;

import botexecution.mainobjects.ChatSession;
import common.SearchCategories;
import logger.BotLogger;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;

import static common.Constants.URL;

public class DataHandler {
    private final HashMap<String, ChatSession> listOfSessions;
    private final HashMap<String, String> listOfUsernames;
    private final HashMap<String, Integer> listOfArticleIds;

    private int[] articleUpdateContext;
    private final File articleContext;
    private final Timer dataRefreshTimer;

    private final LevenshteinDistance env;

    public DataHandler(boolean noTimer) {
        this.listOfSessions = new HashMap<>();
        this.listOfUsernames = readUsernames();
        this.listOfArticleIds = readArticleIds();

        this.articleContext  = new File("../token_dir/articleContext.txt");

        readSessions();
        try (FileInputStream articleIn = new FileInputStream(articleContext);
             ObjectInputStream articleInput = new ObjectInputStream(articleIn)) {
            this.articleUpdateContext = (int[]) articleInput.readObject();
        } catch (ClassNotFoundException | IOException e) {
            this.articleUpdateContext = new int[]{0, -200, 1};
        }

        if (!noTimer) {
            this.dataRefreshTimer = new Timer("DataHandler(SaveDataTimer)", true);
            dataRefreshTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    BotLogger.info("Users' tags being saved");
                    saveUsernames();
                    BotLogger.info("Users' sessions being saved");
                    if (listOfSessions.isEmpty()) {
                        BotLogger.info("There's no users' sessions");
                        return;
                    }
                    saveSessions();
                }
            }, 60000, 60000);
            dataRefreshTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    BotLogger.info("Updating list of article IDs");
                    updateArticleIds();
                    BotLogger.info("Saving list of article IDs");
                    saveArticleIds();
                    BotLogger.info("List of article IDs saved successfully");
                }
            }, 0, 1800000);
        } else {
            this.dataRefreshTimer = null;
        }

        this.env = new LevenshteinDistance();
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
                    try {
                        distance = env.apply(articleName.substring(categoryEnd + 1, categoryEnd + 1 + name.length()), name);
                    } catch (StringIndexOutOfBoundsException ex) {
                        continue;
                    }
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

            if (clearName.toLowerCase().contains(name.toLowerCase())
                    || env.apply(clearName.toLowerCase(), name.toLowerCase()) <= 3) {
                results.add(String.valueOf(this.listOfArticleIds.get(articleName)));
                results.add(clearName);
            }
        }

        return results;
    }

    private void updateArticleIds() {
        Connection.Response response;
        Document page;

        String result;
        int retries;
        Elements name = null;
        boolean pageNotFound;

        SearchCategories currentUpdatedSection = SearchCategories.getSearchCategory(articleUpdateContext[0]);
        if (articleUpdateContext[2] == 8000) {
            articleUpdateContext[0] = (articleUpdateContext[0] + 1) % 6;
            articleUpdateContext[1] = 1;
            articleUpdateContext[2] = 200;
        }
        else {
            articleUpdateContext[1] = articleUpdateContext[2];
            articleUpdateContext[2] += 200;
        }

        try (FileOutputStream articleContextOut = new FileOutputStream(articleContext);
             ObjectOutputStream articleContextOutput = new ObjectOutputStream(articleContextOut)) {
            articleContextOutput.writeObject(articleUpdateContext);
        } catch (IOException ignored) {}

        for (int i = articleUpdateContext[1]; i <= articleUpdateContext[2]; i++) {
            pageNotFound = false;
            retries = 0;

            do {
                try {
                    response = Jsoup.connect(URL + currentUpdatedSection + "/" + i).execute();
                    if (response.url().toString().contains("homebrew")
                            || Objects.equals(response.url().toString(), URL)
                            || response.url().toString().contains("404")) {
                        BotLogger.info("Page made a redirection to either homebrew, home or 404 page - "
                                + URL + currentUpdatedSection + "/" + i);
                        pageNotFound = true;
                        break;
                    }
                    page = response.parse();

                } catch (IOException e) {
                    BotLogger.info("Page is not found (Error 404) - " + URL + currentUpdatedSection + "/" + i);
                    pageNotFound = true;
                    break;
                }

                name = page.select("h2.card-title[itemprop=name]");

                if (name.text().length() > 150) {
                    BotLogger.info("Name of a page is bigger than 150 characters - " + URL + currentUpdatedSection + "/" + i);
                    pageNotFound = true;
                    break;
                }

                retries++;
                if (retries > 10) {
                    BotLogger.info("Retry counter is more than 10 - " + URL + currentUpdatedSection + "/" + i);
                    pageNotFound = true;
                    break;
                }
            } while (name.text().isEmpty());

            if (pageNotFound) {
                continue;
            }

            result = String.format("[%s]%s", currentUpdatedSection.toString(), name.text());
            this.listOfArticleIds.put(result, i);
            BotLogger.info("Current updated entry: " + result + " - " + i);
            BotLogger.log(Level.FINE, "Current updated entry as URL: " + URL + currentUpdatedSection + "/" + i);
        }
    }

    //сохранение и чтение всего
    private void saveSessions() {
        StringBuilder chatPath = new StringBuilder();
        File chatFile;

        for (String chatId: listOfSessions.keySet()) {
            chatPath.append("../token_dir/userData/").append(chatId).append("/session.txt");
            chatFile = new File(chatPath.toString());

            try (FileOutputStream chatOut = new FileOutputStream(chatFile);
                 ObjectOutputStream chatOutput = new ObjectOutputStream(chatOut)) {
                chatOutput.writeObject(listOfSessions.get(chatId));

                BotLogger.info("Saved session ID: " + chatId);
            } catch (IOException e) {
                BotLogger.severe(e.getMessage());
            }
            finally {
                chatPath.setLength(0);
            }
        }

        listOfSessions.clear();
    }

    private void saveUsernames() {
        File usernamesFile = new File("../token_dir/userData/usernameToChatID.txt");

        HashMap<String, String> listOfUsernamesInFile = readUsernames();
        listOfUsernamesInFile.putAll(this.listOfUsernames);

        try (FileOutputStream usernamesOut = new FileOutputStream(usernamesFile);
             ObjectOutputStream usernamesOutput = new ObjectOutputStream(usernamesOut)) {
            usernamesOutput.writeObject(listOfUsernamesInFile);
        } catch (IOException e) {
            BotLogger.severe(e.getMessage());
        }
    }

    private void saveArticleIds() {
        File articleIdsFile = new File("../token_dir/articleIDs.txt");

        HashMap<String, Integer> listOfArticleIdsInFile = readArticleIds();
        listOfArticleIdsInFile.putAll(this.listOfArticleIds);

        try (FileOutputStream articleIdsOut = new FileOutputStream(articleIdsFile);
             ObjectOutputStream articleIdsOutput = new ObjectOutputStream(articleIdsOut)) {
            articleIdsOutput.writeObject(listOfArticleIdsInFile);
        } catch (IOException e) {
            BotLogger.severe(e.getMessage());
        }
    }

    private void readSessions() {
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

            } catch (IOException | ClassNotFoundException e) {
                BotLogger.severe(e.getMessage());
            }
        }
    }

    private HashMap<String, String> readUsernames() {
        File usernamesFile = new File("../token_dir/userData/usernameToChatID.txt");

        HashMap<String, String> listOfUsernamesFile = new HashMap<>();

        try (FileInputStream usernamesIn = new FileInputStream(usernamesFile);
             ObjectInputStream usernamesInput = new ObjectInputStream(usernamesIn)) {
            listOfUsernamesFile = (HashMap<String, String>) usernamesInput.readObject();
        } catch (IOException | ClassNotFoundException e) {
            BotLogger.severe(e.getMessage());
        }

        return listOfUsernamesFile;
    }

    private HashMap<String, Integer> readArticleIds() {
        File articleIdsFile = new File("../token_dir/articleIDs.txt");

        HashMap<String, Integer> articleIds = new HashMap<>();

        try (FileInputStream articleIdsIn = new FileInputStream(articleIdsFile);
             ObjectInputStream articleIdsInput = new ObjectInputStream(articleIdsIn)) {
            articleIds = (HashMap<String, Integer>) articleIdsInput.readObject();
        } catch (IOException | ClassNotFoundException e) {
            BotLogger.severe(e.getMessage());
        }

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
                BotLogger.severe(e.getMessage());
            }
        }
    }

    private void readArticleIdsBackUp() {
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

                for (String id : ids) {
                    String[] info = id.split("~ ");

                    String modifiedId = "[" + i.substring(0, i.length() - 4) + "]" + info[1];
                    System.out.println(modifiedId);
                    this.listOfArticleIds.put(modifiedId, Integer.parseInt(info[0]));
                }
            } catch (IOException e) {
                BotLogger.severe(e.getMessage());
            }
            finally {
                searchFileName.setLength(0);
            }
        }
    }
}
