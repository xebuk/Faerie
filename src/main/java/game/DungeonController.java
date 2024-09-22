package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DungeonController {
    private final MazeGenerator mazeGenerator;
    private final Drawer drawer;
    private List<Cube> scene;

    // Called when user do smth like /startgame and creates unique controller for each user and each game session
    public DungeonController(int mazeWidth, int mazeHeight, int roomMinSize, int roomMaxSize, int roomCount) {
        scene = new ArrayList<>();

        mazeGenerator = new MazeGenerator(mazeWidth, mazeHeight, roomMinSize, roomMaxSize, roomCount);
        mazeGenerator.generateMaze();
        mazeGenerator.printMaze();
        generateTilesFromMaze(mazeGenerator.getMaze());

        int[] cameraXZ = mazeGenerator.getRandomRoomCenter();
        drawer = new Drawer(cameraXZ[0], 0.5, cameraXZ[1]);
//        drawer = new Drawer(2, 1.5, -10);
        System.out.println("Camera pos: " + cameraXZ[0] + ", " + cameraXZ[1]);

        drawer.startDrawing();
        drawer.fillBackground(Color.BLACK);
        drawer.drawScene(scene);
        drawer.endDrawing();
    }

    private void generateTilesFromMaze(MazeGenerator.Tiles[][] maze) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                switch (maze[i][j]) {
                    case WALL:
                        scene.add(new Cube(i, 0.5, j, 0.5));
                        break;
                    case ROOM:
                    case EMPTY:
                        scene.add(new Cube(i, -0.5, j, 0.5, Color.BLUE));
                        break;
                }
            }
        }
    }

    public Drawer getDrawer() {
        return drawer;
    }

    public MazeGenerator getMazeGenerator() {
        return mazeGenerator;
    }

    public static void main(String[] args) {
        DungeonController dungeonController = new DungeonController(50, 50, 3, 7, 30);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            int[] direction = {0, 0, 0};
            int[] rotation = {0, 0};

            String command = scanner.next();
            if ("zxrf".contains(Character.toString(command.charAt(0)))) {
                rotation = switch (command.charAt(0)) {
                    case 'z' -> new int[]{-1, 0};
                    case 'x' -> new int[]{1, 0};
                    case 'r' -> new int[]{0, 1};
                    case 'f' -> new int[]{0, -1};
                    default -> new int[]{0, 0};
                };
            } else {
                direction = switch (command.charAt(0)) {
                    case 'w' -> new int[]{0, 0, 1};
                    case 's' -> new int[]{0, 0, -1};
                    case 'a' -> new int[]{-1, 0, 0};
                    case 'd' -> new int[]{1, 0, 0};
                    case 'q' -> new int[]{0, 1, 0};
                    case 'e' -> new int[]{0, -1, 0};
                    default -> new int[]{0, 0, 0};
                };
            }
            int value = Integer.parseInt(command.substring(1));

            int dYaw = rotation[0] * value;
            int dPitch = rotation[1] * value;
            dungeonController.drawer.rotateCamera(Math.toRadians(dYaw), Math.toRadians(dPitch));

            int dx = direction[0] * value;
            int dy = direction[1] * value;
            int dz = direction[2] * value;
            dungeonController.drawer.moveCamera(dx, dy, dz);

            dungeonController.drawer.startDrawing();
            dungeonController.drawer.fillBackground(Color.BLACK);
            dungeonController.drawer.drawScene(dungeonController.scene);
            dungeonController.drawer.endDrawing();
        }
    }
}