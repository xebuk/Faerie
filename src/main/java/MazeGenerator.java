import java.util.*;

public class MazeGenerator {
    private final static int EMPTY = 0;
    private final static int WALL = 1;
    private final static int ROOM = 2;

    private final static int MAX_ROOM_CREATION_ATTEMPTS = 10000;

    private final int width, height;
    private final int roomMinSize, roomMaxSize;
    private final int[][] maze;
    private final List<Room> rooms = new ArrayList<Room>();
    private final Random random = new Random();

    public MazeGenerator(int width, int height, int roomMinSize, int roomMaxSize) {
        this.width = width;
        this.height = height;
        this.roomMinSize = roomMinSize;
        this.roomMaxSize = roomMaxSize;
        maze = new int[width][height];
        generateMaze();
    }

    private void generateMaze() {
        fillWalls();
        // generateRooms();
        // generateCorridors();
    }

    private void fillWalls() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                maze[i][j] = WALL;
            }
        }
    }

    public void generateRooms(int roomCount) {
        int attempts = 0;
        while (rooms.size() < roomCount && attempts < MAX_ROOM_CREATION_ATTEMPTS) {
            int roomWidth = random.nextInt(roomMaxSize - roomMinSize + 1) + roomMinSize;
            int roomHeight = random.nextInt(roomMaxSize - roomMinSize + 1) + roomMinSize;
            int x = random.nextInt(width - roomWidth - 2) + 1;
            int y = random.nextInt(height - roomHeight - 2) + 1;

            Room newRoom = new Room(x, y, roomWidth, roomHeight);

            if (canPlaceRoom(newRoom)) {
                placeRoom(newRoom);
                rooms.add(newRoom);
            }
            attempts++;
        }

        // DEBUG
        if (attempts >= MAX_ROOM_CREATION_ATTEMPTS) {
            System.out.println("Maximum room creation attempts reached.");
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
                maze[i][j] = ROOM;
            }
        }
    }

    public void connectRooms() {
        Set<Room> connectedRooms = new HashSet<Room>();
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

        /*for (int i = 0; i < rooms.size() - 1; i++) {
            Room roomA = rooms.get(i);
            Room roomB = rooms.get(i + 1);

            int[] exitA = getRandomEdgePoint(roomA);
            int[] exitB = getRandomEdgePoint(roomB);

            generateCorridor(exitA[0], exitA[1], exitB[0], exitB[1]);
        }*/
    }

    private int calculateDistance(Room roomA, Room roomB) {
        int xA = roomA.x + roomA.width / 2;
        int yA = roomA.y + roomA.height / 2;
        int xB = roomB.x + roomB.width / 2;
        int yB = roomB.y + roomB.height / 2;
        return Math.abs(xA - xB) + Math.abs(yA - yB);
    }

    private int[] getRandomEdgePoint(Room room) {
        int edge = random.nextInt(4);
        int x, y;

        switch (edge) {
            case 0:     // UP border
                x = random.nextInt(room.width) + room.x;
                y = room.y + room.height - 1;
                break;
            case 1:     // RIGHT border
                x = room.x + room.width - 1;
                y = random.nextInt(room.height) + room.y;
                break;
            case 2:     // DOWN border
                x = random.nextInt(room.width) + room.x;
                y = room.y;
                break;
            default:    // LEFT border
                x = room.x;
                y = random.nextInt(room.height) + room.y;
                break;
        }

        return new int[]{x, y};
    }

    private void generateCorridor(int x1, int y1, int x2, int y2) {
        int middleX = (x1 + x2) / 2;
        int middleY = (y1 + y2) / 2;

        // Randomly chooses one of two paths: right-down-right OR down-right-down
        // (or their inverted variants, depends on coords)
        int coinFlip = random.nextInt(2);
        if (coinFlip == 0) {
            digLine(x1, y1, middleX, y1);
            digLine(middleX, y1, middleX, y2);
            digLine(middleX, y2, x2, y2);
        } else {
            digLine(x1, y1, x1, middleY);
            digLine(x1, middleY, x2, middleY);
            digLine(x2, middleY, x2, y2);
        }
    }

    private void digLine(int x1, int y1, int x2, int y2) {
        while (x1 != x2) {
            maze[y1][x1] = EMPTY;
            if (x1 < x2) {
                x1++;
            } else {
                x1--;
            }
        }
        while (y1 != y2) {
            maze[y1][x1] = EMPTY;
            if (y1 < y2) {
                y1++;
            } else {
                y1--;
            }
        }
    }

    public void printMaze() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(maze[i][j] == WALL ? "###" : maze[i][j] == ROOM ? "..." : "   ");
            }
            System.out.println();
        }
    }

    private static class Room {
        int x, y, width, height;

        Room(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        boolean intersects(Room other) {
            return this.x <= other.x + other.width + 1 &&
                   this.x + this.width + 1 >= other.x &&
                   this.y <= other.y + other.height + 1 &&
                   this.y + this.height + 1 >= other.y;
        }
    }

    public static void main(String[] args) {
        MazeGenerator mazeGenerator = new MazeGenerator(50, 50, 3, 7);
        mazeGenerator.generateRooms(30);
        mazeGenerator.connectRooms();
        mazeGenerator.printMaze();
    }
}
