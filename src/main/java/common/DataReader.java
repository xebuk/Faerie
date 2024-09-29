package common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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

        for (String articleId: lines) {
            separated = articleId.trim().split("~ ");
            if (separated[1].toLowerCase().contains(name.toLowerCase())) {
                //System.out.println(separated[0]);
                //System.out.println(separated[1]);
                return separated[0];
            }
        }
        return name;
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
