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
        mazeGenerator.generateMaze(MazeGenerator.Mode.COMBINED);
        mazeGenerator.printMaze();
        generateTilesFromMaze(mazeGenerator.getMaze());

        int[] cameraXZ = mazeGenerator.getRandomRoomCenter();
        drawer = new Drawer(cameraXZ[0], 0.5, cameraXZ[1], mazeGenerator);

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

    public void stepForward() {
        drawer.moveCamera(0, 1);
    }

    public void stepBackward() {
        drawer.moveCamera(0, -1);
    }

    public void stepLeft() {
        drawer.moveCamera(-1, 0);
    }

    public void stepRight() {
        drawer.moveCamera(1, 0);
    }

    public void turnLeft() {
        drawer.rotateCamera(-Math.PI / 2, 0);
    }

    public void turnRight() {
        drawer.rotateCamera(Math.PI / 2, 0);
    }

    public Drawer getDrawer() {
        return drawer;
    }

    public MazeGenerator getMazeGenerator() {
        return mazeGenerator;
    }

    public static void main(String[] args) {
        DungeonController dc = new DungeonController(50, 50, 3, 7, 30);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.next();
            switch (command) {
                case "w":
                    dc.stepForward();
                    break;
                case "s":
                    dc.stepBackward();
                    break;
                case "a":
                    dc.stepLeft();
                    break;
                case "d":
                    dc.stepRight();
                    break;
                case "z":
                    dc.turnLeft();
                    break;
                case "x":
                    dc.turnRight();
                    break;
                default:
                    break;
            }

            dc.drawer.startDrawing(Color.BLACK, dc.sceneObjects, dc.lights);
            dc.drawer.drawScene();
            dc.drawer.endDrawing();
        }
    }
}