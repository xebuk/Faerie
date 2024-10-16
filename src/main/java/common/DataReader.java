package common;

import org.apache.commons.text.similarity.LevenshteinDistance;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Integer.parseInt;

public class DataReader {
    public static String readToken() throws IOException {
        Path tokenFilePath = Path.of("../token_dir/token.txt");
        return Files.readString(tokenFilePath).trim();
    }

    public static int readCreatorId() throws IOException {
        Path creatorID_FilePath = Path.of("../token_dir/creatorID.txt");
        return parseInt(Files.readString(creatorID_FilePath).trim());
    }

    public static String searchArticleId(String section, String name) throws IOException {
        Path articleIdFilePath = Path.of("../token_dir/searchID/" + section + ".txt");
        List<String> lines = Files.readAllLines(articleIdFilePath);;
        String[] separated;

        LevenshteinDistance env = new LevenshteinDistance();
        int minSimilarityDistance = 1999999999;
        String resArticleId = "1";

        for (String articleId: lines) {
            separated = articleId.trim().split("~ ");
            int distance;
            if ((int) name.charAt(0) < 123 && (int) name.charAt(0) > 96) {
                distance = env.apply(separated[1].substring(separated[1].indexOf("[") + 1, separated[1].indexOf("]")), name);
            }
            else {
                distance = env.apply(separated[1].substring(0, separated[1].indexOf("[")), name);
            }

            if (distance < minSimilarityDistance) {
                minSimilarityDistance = distance;
                resArticleId = separated[0];
            }
        }

        return resArticleId;
    }

    public static ArrayList<String> searchArticleIds(String section, String name) throws IOException {
        ArrayList<String> results = new ArrayList<>();

        Path articleIdFilePath = Path.of("../token_dir/searchID/" + section + ".txt");
        List<String> lines = Files.readAllLines(articleIdFilePath);
        String[] separated;

        for (String articleId: lines) {
            separated = articleId.trim().split("~ ");
            if (separated[1].toLowerCase().contains(name.toLowerCase())) {
                results.add(separated[0]);
                results.add(separated[1]);
            }
        }

        return results;
    }

    public static void saveArticleIdsAsHashMap(String section) throws IOException {
        HashMap<Integer, String> articleIds = new HashMap<>();

        Path articleIdsFilePath = Path.of("../token_dir/searchID/" + section + ".txt");
        List<String> lines = Files.readAllLines(articleIdsFilePath);
        String[] separated;

        File articleIdsHash = new File("../token_dir/searchIDasHashMaps/" + section + ".txt");
        articleIdsHash.createNewFile();

        for (String articleId: lines) {
            separated = articleId.trim().split("~ ");
            articleIds.put(Integer.parseInt(separated[0]), separated[1]);
        }

        FileOutputStream out = new FileOutputStream(articleIdsHash);
        ObjectOutputStream output = new ObjectOutputStream(out);
        output.writeObject(articleIds);

        output.close();
        out.close();
    }

    public static HashMap<Integer, String> readArticleIds(String section) {
        File articleIdsHash = new File("../token_dir/searchIDasHashMaps/" + section + ".txt");

        FileInputStream in;
        ObjectInputStream input;

        try {
            in = new FileInputStream(articleIdsHash);
            input = new ObjectInputStream(in);
            HashMap<Integer, String> articleIds = (HashMap<Integer, String>) input.readObject();
            input.close();
            in.close();
            return articleIds;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static File frame = new File(Constants.IMAGE_OUTPUT_PATH + "output.png");

    public static File getFrame() {
        return frame;
    }

    public static boolean updatePicture() {
        File frameCheck = new File(Constants.IMAGE_OUTPUT_PATH + "output.png");
        if (frame.lastModified() != frameCheck.lastModified()) {
            frame = frameCheck;
            return true;
        }
        return false;
    }
}
