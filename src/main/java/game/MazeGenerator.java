package game;

import common.DataReader;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class MazeGenerator implements Serializable {
    public enum Mode implements Serializable {
        RANDOM_ONLY,
        COMBINED,
        PRESETS_ONLY
    }

    private final static int MAX_ROOM_CREATION_ATTEMPTS = 10000;

    private final int mazeWidth, mazeHeight;
    private final int roomMinSize, roomMaxSize;
    private final int roomCount;

    private final Tiles[][] maze;
    private final List<Room> rooms;
    private final Random random;

    private final Tiles[][][] presets;

    public MazeGenerator(int mazeWidth, int mazeHeight, int roomMinSize, int roomMaxSize, int roomCount) {
        this.mazeWidth = mazeWidth;
        this.mazeHeight = mazeHeight;
        this.roomMinSize = roomMinSize;
        this.roomMaxSize = roomMaxSize;
        this.roomCount = roomCount;
        maze = new Tiles[mazeWidth][mazeHeight];
        rooms = new ArrayList<>();
        random = new Random();

        Tiles[][][] presetsTmp;
        try {
            presetsTmp = DataReader.readRoomPresets();
        } catch (IOException e) {
            presetsTmp = null;
        }
        presets = presetsTmp;
    }

    public void generateMaze(Mode mode) {
        fillWalls();
        switch (mode) {
            case RANDOM_ONLY:
                generateRandomRooms(roomCount);
                break;
            case COMBINED:
                for (int i = 0; i < roomCount; i++) {
                    int coinFlip = random.nextInt(2);
                    if (coinFlip == 0) {
                        generateRandomRooms(1);
                    } else {
                        generatePresetsRooms(1);
                    }
                }
                break;
            case PRESETS_ONLY:
                generatePresetsRooms(roomCount);
                break;
        }
        connectRooms();
        removeIsolatedTiles();
    }

    private void fillWalls() {
        for (int i = 0; i < mazeWidth; i++) {
            Arrays.fill(maze[i], Tiles.WALL);
        }
    }

    private void generateRandomRooms(int roomCount) {
        int attempts = 0;
        int created = 0;
        while (created < roomCount && attempts < MAX_ROOM_CREATION_ATTEMPTS) {
            int roomWidth = random.nextInt(roomMaxSize - roomMinSize + 1) + roomMinSize;
            int roomHeight = random.nextInt(roomMaxSize - roomMinSize + 1) + roomMinSize;
            int xRange = mazeWidth - roomWidth - 2;
            int yRange = mazeHeight - roomHeight - 2;
            int x = (xRange > 0 ? random.nextInt(xRange) : 0) + 1;
            int y = (yRange > 0 ? random.nextInt(yRange) : 0) + 1;

            // FIXME: The heck is this? Can it be optimized?
            Tiles[][] layout = new Tiles[roomHeight][roomWidth];
            for (int i = 0; i < roomHeight; i++) {
                Arrays.fill(layout[i], Tiles.FLOOR);
            }
            Room newRoom = new Room(x, y, roomWidth, roomHeight, layout);

            if (canPlaceRoom(newRoom)) {
                placeRoom(newRoom);
                rooms.add(newRoom);
                created++;
            } else {
                attempts++;
            }
        }

        // DEBUG
        if (attempts >= MAX_ROOM_CREATION_ATTEMPTS) {
            System.out.println("Maximum random room creation attempts reached.");
        }
    }

    private void generatePresetsRooms(int roomCount) {
        int attempts = 0;
        int created = 0;
        while (created < roomCount && attempts < MAX_ROOM_CREATION_ATTEMPTS) {
            int roomType = random.nextInt(presets.length);

            int roomWidth = presets[roomType][0].length;
            int roomHeight = presets[roomType].length;
            int xRange = mazeWidth - roomWidth - 2;
            int yRange = mazeHeight - roomHeight - 2;
            int x = (xRange > 0 ? random.nextInt(xRange) : 0) + 1;
            int y = (yRange > 0 ? random.nextInt(yRange) : 0) + 1;

            Tiles[][] layout = presets[roomType];

            Room room = new Room(x, y, roomWidth, roomHeight, layout);
            if (canPlaceRoom(room)) {
                placeRoom(room);
                rooms.add(room);
                created++;
            } else {
                attempts++;
            }
        }

        // DEBUG
        if (attempts >= MAX_ROOM_CREATION_ATTEMPTS) {
            System.out.println("Maximum presets room creation attempts reached.");
        }
    }

    private boolean canPlaceRoom(Room room) {
        for (Room other : rooms) {
            if (room.intersects(other)) {
                return false;
            }
        }
        return true;
    }

    private void placeRoom(Room room) {
        for (int i = room.y; i < room.y + room.height; i++) {
            for (int j = room.x; j < room.x + room.width; j++) {
                maze[i][j] = room.getTile(j - room.x, i - room.y);
            }
        }
    }

    private void connectRooms() {
        Set<Room> connectedRooms = new HashSet<>();
        connectedRooms.add(rooms.getFirst());

        while (connectedRooms.size() < rooms.size()) {
            Room closestRoomA = null;
            Room closestRoomB = null;
            int minDistance = Integer.MAX_VALUE;

            for (Room roomA : connectedRooms) {
                for (Room roomB : rooms) {
                    if (!connectedRooms.contains(roomB)) {
                        int distance = calculateDistance(roomA, roomB);
                        if (distance < minDistance) {
                            minDistance = distance;
                            closestRoomA = roomA;
                            closestRoomB = roomB;
                        }
                    }
                }
            }

            if (closestRoomA != null && closestRoomB != null) {
                int[] exitA = getRandomEdgePoint(closestRoomA);
                int[] exitB = getRandomEdgePoint(closestRoomB);
                generateCorridor(exitA[0], exitA[1], exitB[0], exitB[1]);
                connectedRooms.add(closestRoomB);
            }
        }
    }

    private void removeIsolatedTiles() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < mazeHeight; j++) {
                if (maze[i][j] == Tiles.WALL) {
                    if (isWallOrNone(i - 1, j) &&
                        isWallOrNone(i + 1, j) &&
                        isWallOrNone(i, j - 1) &&
                        isWallOrNone(i, j + 1)) {
                        maze[i][j] = Tiles.NONE;
                    }
                }
            }
        }
    }

    private boolean hasStrictNeighbour(int x, int y, Tiles tile) {
        return (x - 1 >= 0 && y - 1 >= 0 && maze[y-1][x-1] == tile) ||
               (x + 1 < mazeWidth && y - 1 >= 0 && maze[y-1][x+1] == tile) ||
               (x - 1 >= 0 && y + 1 < mazeHeight && maze[y+1][x-1] == tile) ||
               (x + 1 < mazeWidth && y + 1 < mazeHeight && maze[y+1][x+1] == tile);
    }

    private boolean isWallOrNone(int i, int j) {
        if (i < 0 || i >= mazeWidth || j < 0 || j >= mazeHeight) {
            return true;
        }
        return maze[i][j] == Tiles.WALL || maze[i][j] == Tiles.NONE;
    }

    private int calculateDistance(Room roomA, Room roomB) {
        int xA = roomA.x + roomA.width / 2;
        int yA = roomA.y + roomA.height / 2;
        int xB = roomB.x + roomB.width / 2;
        int yB = roomB.y + roomB.height / 2;
        return Math.abs(xA - xB) + Math.abs(yA - yB);
    }

    private int[] getRandomEdgePoint(Room room) {
        int edge = random.nextInt(4);       // 0 - left, 1 - up, 2 - right, 3 - down

        int[] pos = room.getCenterLocal();
        int x = pos[0];
        int y = pos[1];

        int dx = 0;
        int dy = 0;
        switch (edge) {
            case 0:
                dx = -1;
                break;
            case 1:
                dy = 1;
                break;
            case 2:
                dx = 1;
                break;
            case 3:
                dy = -1;
                break;
        }

        while (x >= 0 && y >= 0 && x < room.width && y < room.height && room.getTile(x, y) == Tiles.FLOOR) {
            x += dx;
            y += dy;
        }

        return new int[]{x - dx + room.x, y - dy + room.y};
    }

    private void generateCorridor(int x1, int y1, int x2, int y2) {
        int middleX = (x1 + x2) / 2;
        int middleY = (y1 + y2) / 2;

        // Randomly chooses one of two paths: right-down-right OR down-right-down
        // (or their inverted variants, depends on coords)
        int coinFlip = random.nextInt(2);
        if (coinFlip == 0) {
            generateLine(x1, y1, middleX, y1);
            generateLine(middleX, y1, middleX, y2);
            generateLine(middleX, y2, x2, y2);
        } else {
            generateLine(x1, y1, x1, middleY);
            generateLine(x1, middleY, x2, middleY);
            generateLine(x2, middleY, x2, y2);
        }
    }

    private void generateLine(int x1, int y1, int x2, int y2) {
        while (x1 != x2) {
            maze[y1][x1] = Tiles.FLOOR;
            if (x1 < x2) {
                x1++;
            } else {
                x1--;
            }
        }
        while (y1 != y2) {
            maze[y1][x1] = Tiles.FLOOR;
            if (y1 < y2) {
                y1++;
            } else {
                y1--;
            }
        }
    }

    public int[] getRandomRoomCenter() {
        Room randomRoom = rooms.get(random.nextInt(rooms.size()));
        int x = randomRoom.x + randomRoom.width / 2;
        int y = randomRoom.y + randomRoom.height / 2;

        return new int[] {x, y};
    }

    public void printMaze() {
        for (int i = 0; i < mazeHeight; i++) {
            for (int j = 0; j < mazeWidth; j++) {
                System.out.print(
                        switch (maze[i][j]) {
                            case FLOOR -> "   ";
                            case WALL, NONE -> "###";
                        });
            }
            System.out.println();
        }
    }

    public static class Room implements Serializable {
        int x, y, width, height;
        Tiles[][] layout;

        Room(int x, int y, int width, int height, Tiles[][] layout) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.layout = layout;
        }

        boolean intersects(Room other) {
            return this.x <= other.x + other.width + 1 &&
                   this.x + this.width + 1 >= other.x &&
                   this.y <= other.y + other.height + 1 &&
                   this.y + this.height + 1 >= other.y;
        }

        public void setTile(int x, int y, Tiles tile) {
            this.layout[y][x] = tile;
        }
        public Tiles getTile(int x, int y) {
            return layout[y][x];
        }
        public int[] getCenter() {
            return new int[] {this.x + this.width / 2, this.y + this.height / 2};
        }

        public int[] getCenterLocal() {
            return new int[] {this.width / 2, this.height / 2};
        }
    }

    public Tiles[][] getMaze() {
        return maze;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Tiles getTile(int x, int y) {
        return maze[y][x];
    }

    public static void main(String[] args) {
        MazeGenerator mazeGenerator = new MazeGenerator(50, 50, 3, 7, 30);
        mazeGenerator.generateMaze(Mode.PRESETS_ONLY);
        mazeGenerator.printMaze();
    }
}
