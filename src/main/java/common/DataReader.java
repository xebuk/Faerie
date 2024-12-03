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
    public static String readToken() {
        Path tokenFilePath = Path.of("../token_dir/token.txt");
        try {
            return Files.readString(tokenFilePath).trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static long readCreatorId() {
        Path creatorID_FilePath = Path.of("../token_dir/creatorID.txt");
        try {
            return parseInt(Files.readString(creatorID_FilePath).trim());
        } catch (IOException e) {
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
