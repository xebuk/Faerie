package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Drawer {
    private static final int CANVAS_WIDTH = 720;  //1440;
    private static final int CANVAS_HEIGHT = 405;  //810;

    private static final double FOV = Math.toRadians(60);
    private static final double FOCAL_LENGTH = 1.0 / Math.tan(FOV / 2);
    private static final double ASPECT_RATIO = (double) CANVAS_WIDTH / CANVAS_HEIGHT;

    private double cameraX, cameraY, cameraZ;

    public Drawer(double cameraX, double cameraY, double cameraZ) {
        this.cameraX = cameraX;
        this.cameraY = cameraY;
        this.cameraZ = cameraZ;
    }

    private int[] projectVertex(double x, double y, double z) {
        double localX = x - cameraX;
        double localY = y - cameraY;
        double localZ = z - cameraZ;

        double projX = (localX / localZ) * FOCAL_LENGTH / ASPECT_RATIO;
        double projY = (localY / localZ) * FOCAL_LENGTH;

        int pixelX = (int) ((projX + 1) / 2 * CANVAS_WIDTH);
        int pixelY = (int) ((1 - projY) / 2 * CANVAS_HEIGHT);

        return new int[] {pixelX, pixelY};
    }

    private int[][] projectFace(Face face) {
        int[][] projectedVertices = new int[2][4];

        for (int i = 0; i < 4; i++) {
            double[] vertex = face.getVertices()[i];
            var tmp = projectVertex(vertex[0], vertex[1], vertex[2]);
            projectedVertices[0][i] = tmp[0];
            projectedVertices[1][i] = tmp[1];
        }

        return projectedVertices;
    }

    public void drawCube(Graphics2D g2d, Cube cube) {
        for (Face face : cube.getFaces()) {
            updateFaceColor(face);
            int[][] projectedCoords = projectFace(face);
            g2d.setColor(face.getCurrentColor());
            g2d.fillPolygon(projectedCoords[0], projectedCoords[1], 4);
        }
    }

    private void updateFaceColor(Face face) {
        double distance = faceDistanceToCamera(face);
        double intensity = 10.0 / (1.0 + distance);

        int red = (int) (face.getBaseColor().getRed() * intensity);
        int green = (int) (face.getBaseColor().getGreen() * intensity);
        int blue = (int) (face.getBaseColor().getBlue() * intensity);

        face.setCurrentColor(new Color(Math.clamp(red, 0, 255), Math.clamp(green, 0, 255), Math.clamp(blue, 0, 255)));
    }

    private double faceDistanceToCamera(Face face) {
        double[] center = face.calculateCenter();
        return Math.sqrt(Math.pow(center[0] - cameraX, 2) + Math.pow(center[1] - cameraY, 2) + Math.pow(center[2] - cameraZ, 2));
    }

    public void moveCamera(double dx, double dy, double dz) {
        cameraX += dx;
        cameraY += dy;
        cameraZ += dz;
    }

    public void setCameraPosition(double x, double y, double z) {
        cameraX = x;
        cameraY = y;
        cameraZ = z;
    }

    public double getCameraX() {
        return cameraX;
    }

    public void setCameraX(double cameraX) {
        this.cameraX = cameraX;
    }

    public double getCameraY() {
        return cameraY;
    }

    public void setCameraY(double cameraY) {
        this.cameraY = cameraY;
    }

    public double getCameraZ() {
        return cameraZ;
    }

    public void setCameraZ(double cameraZ) {
        this.cameraZ = cameraZ;
    }

    public static void main(String[] args) {
        // This main is only for testing purposes and Drawer class still have WIP status.
        // Any useful functionality will be added in the next updates.
        Drawer drawer = new Drawer(0, 0,-10);

        // * TESTING
        Random random = new Random();
        int cubeCountTest = 10;
        Cube[] cubes = new Cube[cubeCountTest];
        for (int i = 0; i < cubeCountTest; i++) {
            cubes[i] = new Cube(random.nextInt(10)-5, random.nextInt(10)-5, random.nextInt(10)-5, random.nextDouble(2));
        }

        try {
            BufferedImage image = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();

            // BG
            int[] xBackground = {0, CANVAS_WIDTH, CANVAS_WIDTH, 0};
            int[] yBackground = {0, 0, CANVAS_HEIGHT, CANVAS_HEIGHT};
            g2d.setColor(Color.WHITE);
            g2d.fillPolygon(xBackground, yBackground, xBackground.length);

            // * TESTING
            for (int i = 0; i < cubeCountTest; i++) {
                drawer.drawCube(g2d, cubes[i]);
            }

            g2d.dispose();

            ImageIO.write(image, "png", new File("output.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
