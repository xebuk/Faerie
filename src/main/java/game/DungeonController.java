package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DungeonController {
    private final MazeGenerator mazeGenerator;
    private final Drawer drawer;
    private List<Cube> sceneObjects;
    private List<LightSource> lights;

    // Called when user do smth like /startgame and creates unique controller for each user and each game session
    public DungeonController(int mazeWidth, int mazeHeight, int roomMinSize, int roomMaxSize, int roomCount) {
        sceneObjects = new ArrayList<>();
        lights = new ArrayList<>();

        mazeGenerator = new MazeGenerator(mazeWidth, mazeHeight, roomMinSize, roomMaxSize, roomCount);
        mazeGenerator.generateMaze();
        mazeGenerator.printMaze();
        generateTilesFromMaze(mazeGenerator.getMaze());

        int[] cameraXZ = mazeGenerator.getRandomRoomCenter();
        drawer = new Drawer(cameraXZ[0], 0.5, cameraXZ[1]);

//        lights.add(new LightSource(cameraXZ[0], 0.5, cameraXZ[1], 3));
        for (MazeGenerator.Room room : mazeGenerator.getRooms()) {
            int[] center = room.getCenter();
            lights.add(new LightSource(center[0], 0.5, center[1], 5));
        }
//        drawer = new Drawer(2, 1.5, -10);
        System.out.println("Camera pos: " + cameraXZ[0] + ", " + cameraXZ[1]);

        drawer.startDrawing(Color.BLACK, sceneObjects, lights);
        drawer.drawScene();
        drawer.endDrawing();
    }

    private void generateTilesFromMaze(MazeGenerator.Tiles[][] maze) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                switch (maze[i][j]) {
                    case WALL:
                        sceneObjects.add(new Cube(j, 0.5, i, 0.5, new Texture("wall_revamped.png")));
                        break;
                    case FLOOR:
                        sceneObjects.add(new Cube(j, -0.5, i, 0.5, new Texture("floor.png")));
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
        DungeonController dungeonController = new DungeonController(20, 20, 3, 7, 30);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            int[] direction = {0, 0};
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
                    case 'w' -> new int[]{0, 1};
                    case 's' -> new int[]{0, -1};
                    case 'a' -> new int[]{-1, 0};
                    case 'd' -> new int[]{1, 0};
                    default -> new int[]{0, 0, 0};
                };
            }
            int value = Integer.parseInt(command.substring(1));

            int dYaw = rotation[0] * value;
            int dPitch = rotation[1] * value;
            dungeonController.drawer.rotateCamera(Math.toRadians(dYaw), Math.toRadians(dPitch));

            int dx = direction[0] * value;
            int dz = direction[1] * value;
            dungeonController.drawer.moveCamera(dx, dz);

            dungeonController.drawer.startDrawing(Color.BLACK, dungeonController.sceneObjects, dungeonController.lights);
            dungeonController.drawer.drawScene();
            dungeonController.drawer.endDrawing();
        }
    }
}