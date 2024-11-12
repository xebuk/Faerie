package game.environment;

import game.objects.Cube;
import game.objects.LightSource;
import game.objects.Tiles;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DungeonController implements Serializable {
    private final MazeGenerator mazeGenerator;
    private final List<Cube> sceneObjects;
    private final List<LightSource> lights;

    private final LightSource visionLight;

    private double cameraX;
    private double cameraY;
    private double cameraZ;

    private double yaw = 0;
    private double pitch = 0;

    // Called when user do smth like /startgame and creates unique controller for each user and each game session
    public DungeonController(int mazeWidth, int mazeHeight, int roomMinSize, int roomMaxSize, int roomCount) {
        sceneObjects = new ArrayList<>();
        lights = new ArrayList<>();

        mazeGenerator = new MazeGenerator(mazeWidth, mazeHeight, roomMinSize, roomMaxSize, roomCount);
        mazeGenerator.generateMaze(MazeGenerator.Mode.COMBINED);
        mazeGenerator.printMaze();
        generateTilesFromMaze(mazeGenerator.getMaze());

        int[] cameraXZ = mazeGenerator.getRandomRoomCenter();
        cameraX = cameraXZ[0];
        cameraY = 0.5;
        cameraZ = cameraXZ[1];

        visionLight = new LightSource(cameraX, cameraY, cameraZ, 3, Color.PINK);

//        lights.add(new LightSource(cameraXZ[0], 0.5, cameraXZ[1], 3));
        for (MazeGenerator.Room room : mazeGenerator.getRooms()) {
            int[] center = room.getCenter();
            lights.add(new LightSource(center[0], 0.5, center[1], 5));
        }
//        drawer = new Drawer(2, 1.5, -10);
        System.out.println("Camera pos: " + cameraXZ[0] + ", " + cameraXZ[1]);
    }

    private void generateTilesFromMaze(Tiles[][] maze) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                switch (maze[i][j]) {
                    case WALL:
                        sceneObjects.add(new Cube(j, 0.5, i, 0.5, Tiles.WALL));
                        break;
                    case FLOOR:
                        sceneObjects.add(new Cube(j, -0.5, i, 0.5, Tiles.FLOOR));
                        break;
                }
            }
        }
    }

    public boolean movementOpportunity(double dx, double dz) {
        double moveX =  dx * Math.cos(yaw) + dz * Math.sin(yaw);
        double moveZ = -dx * Math.sin(yaw) + dz * Math.cos(yaw);

        if (mazeGenerator.getTile((int) (cameraX + moveX), (int) (cameraZ + moveZ)) == Tiles.FLOOR) {
            cameraX = cameraX + moveX;
            cameraZ = cameraZ + moveZ;
            visionLight.moveVisionLight(moveX, moveZ);
            return true;
        }
        return false;
    }

    public void rotateOpportunity(double dYaw, double dPitch) {
        yaw += dYaw;
        pitch += dPitch;

        // Clamp the pitch to avoid gimbal lock
        pitch = Math.max(-Math.PI / 2, Math.min(Math.PI / 2, pitch));
    }

    public void stepForward() {
        movementOpportunity(0, 1);
    }

    public void stepBackward() {
        movementOpportunity(0, -1);
    }

    public void stepLeft() {
        movementOpportunity(-1, 0);
    }

    public void stepRight() {
        movementOpportunity(1, 0);
    }

    public void turnLeft() {
        rotateOpportunity(-Math.PI / 2, 0);
    }

    public void turnRight() {
        rotateOpportunity(Math.PI / 2, 0);
    }

    public MazeGenerator getMazeGenerator() {
        return mazeGenerator;
    }

    public double getCameraX() {
        return cameraX;
    }

    public double getCameraY() {
        return cameraY;
    }

    public double getCameraZ() {
        return cameraZ;
    }

    public double getYaw() {
        return yaw;
    }

    public double getPitch() {
        return pitch;
    }

    public List<Cube> getSceneObjects() {
        return sceneObjects;
    }

    public List<LightSource> getLights() {
        return lights;
    }

    public LightSource getVisionLight() {
        return visionLight;
    }

    public static void main(String[] args) {
        DungeonController dc = new DungeonController(50, 50, 3, 7, 30);
        Drawer monitor = new Drawer(dc);

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

            monitor.setMaze(dc);
            monitor.startDrawing(Color.BLACK, dc.sceneObjects, dc.lights, dc.visionLight);
            monitor.drawScene();
            monitor.endDrawing();
        }
    }
}