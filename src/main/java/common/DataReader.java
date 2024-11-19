package common;

import game.objects.Tiles;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.io.*;

import java.io.File;
import java.io.IOException;
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

    public static Tiles[][][] readRoomPresets() throws IOException {
        Tiles[][][] roomPresets;

        File[] filesList = new File(Constants.ROOM_PRESETS_PATH).listFiles();
        int filesCount = filesList.length;
        roomPresets = new Tiles[filesCount][][];

        for (int i = 0; i < filesCount; i++) {
            File presetFile = filesList[i];
            List<String> lines = Files.lines(presetFile.toPath()).toList();
            int linesCount = lines.size();
            roomPresets[i] = new Tiles[linesCount][];

            int lineSize = -1;
            int j = 0;
            for (String line : lines) {
                int lineLength = line.length();
                if (lineSize == -1) {
                    lineSize = lineLength;
                } else if (lineSize != lineLength) {
                    throw new RuntimeException("Lines are not the same length.");
                }
                roomPresets[i][j] = new Tiles[lineLength];

                for (int k = 0; k < lineLength; k++) {
                    roomPresets[i][j][k] = switch (line.charAt(k)) {
                        case '#' -> Tiles.WALL;
                        case '.' -> Tiles.FLOOR;
                        default -> Tiles.NONE;
                    };
                }
                j++;
            }
        }

        return roomPresets;
    }
}
