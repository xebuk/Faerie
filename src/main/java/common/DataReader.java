package common;

import game.MazeGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    public static MazeGenerator.Tiles[][][] readRoomPresets() throws IOException {
        MazeGenerator.Tiles[][][] roomPresets;

        File[] filesList = new File(Constants.ROOM_PRESETS_PATH).listFiles();
        int filesCount = filesList.length;
        roomPresets = new MazeGenerator.Tiles[filesCount][][];

        for (int i = 0; i < filesCount; i++) {
            File presetFile = filesList[i];
            int linesCount = (int) Files.lines(presetFile.toPath()).count();
            roomPresets[i] = new MazeGenerator.Tiles[linesCount][];

            BufferedReader br = new BufferedReader(new FileReader(presetFile));
            String line;
            for (int j = 0; j < linesCount; j++) {
                line = br.readLine();
                int lineLength = line.length();
                roomPresets[i][j] = new MazeGenerator.Tiles[lineLength];

                for (int k = 0; k < lineLength; k++) {
                    roomPresets[i][j][k] = switch (line.charAt(k)) {
                        case '#' -> MazeGenerator.Tiles.WALL;
                        case '.' -> MazeGenerator.Tiles.FLOOR;
                        default -> MazeGenerator.Tiles.NONE;
                    };
                }
            }
        }

        return roomPresets;
    }
}
