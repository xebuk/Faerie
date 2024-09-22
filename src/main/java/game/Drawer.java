package game;

import common.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class Drawer {
    private static final int CANVAS_WIDTH = 720;  //1440;
    private static final int CANVAS_HEIGHT = 405;  //810;

    private static final double FOV = Math.toRadians(60);
    private static final double FOCAL_LENGTH = 1.0 / Math.tan(FOV / 2);
    private static final double ASPECT_RATIO = (double) CANVAS_WIDTH / CANVAS_HEIGHT;

    private static final double NEAR_CLIPPING_PLANE = 0.1;
    private static final double FAR_CLIPPING_PLANE = 100.0;

    private double cameraX, cameraY, cameraZ;
    private double yaw, pitch;
    private final double[][] depthBuffer;

    private Graphics2D g2d;
    private BufferedImage image;

    public Drawer(double cameraX, double cameraY, double cameraZ) {
        this.cameraX = cameraX;
        this.cameraY = cameraY;
        this.cameraZ = cameraZ;
        this.depthBuffer = new double[CANVAS_WIDTH][CANVAS_HEIGHT];

        resetDepthBuffer();
    }

    public void startDrawing() {
        image = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();
    }

    public void endDrawing() {
        g2d.dispose();
        try {
            String path = Paths.get(Constants.IMAGE_OUTPUT_PATH, "output.png").toString();
            ImageIO.write(image, "png", new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void resetDepthBuffer() {
        // Fill depth buffer with "very far away" coords
        for (int i = 0; i < CANVAS_WIDTH; i++) {
            for (int j = 0; j < CANVAS_HEIGHT; j++) {
                depthBuffer[i][j] = Double.MAX_VALUE;
            }
        }
    }

    private double[] rotateVertex(double x, double y, double z) {
        double cosYaw = Math.cos(yaw);
        double sinYaw = Math.sin(yaw);
        double cosPitch = Math.cos(pitch);
        double sinPitch = Math.sin(pitch);

        double tempX = cosYaw * x - sinYaw * z;
        double tempZ = sinYaw * x + cosYaw * z;

        double tempY = cosPitch * y - sinPitch * tempZ;
        tempZ = sinPitch * y + cosPitch * tempZ;
        return new double[] {tempX, tempY, tempZ};
    }

    private int[] projectVertex(double x, double y, double z, double[] depth) {
        double localX = x - cameraX;
        double localY = y - cameraY;
        double localZ = z - cameraZ;

        double[] rotatedCoords = rotateVertex(localX, localY, localZ);
        localX = rotatedCoords[0];
        localY = rotatedCoords[1];
        localZ = rotatedCoords[2];

        if (localZ < NEAR_CLIPPING_PLANE || localZ > FAR_CLIPPING_PLANE) {      // Clip vertex
            return null;
        }

        depth[0] = localZ;      // Store z for depth buffer. Passing as double[1] array to return value by reference

        double projX = (localX / localZ) * FOCAL_LENGTH / ASPECT_RATIO;
        double projY = (localY / localZ) * FOCAL_LENGTH;

        int pixelX = (int) ((projX + 1) / 2 * CANVAS_WIDTH);
        int pixelY = (int) ((1 - projY) / 2 * CANVAS_HEIGHT);

        return new int[] {pixelX, pixelY};
    }

    private int[][] projectFace(Face face, double[] depthValues) {
        int[][] projectedVertices = new int[2][4];

        for (int i = 0; i < 4; i++) {
            double[] vertex = face.getVertices()[i];
            double[] depth = new double[1];
            int[] pixelCoords = projectVertex(vertex[0], vertex[1], vertex[2], depth);

            if (pixelCoords == null) {      // If one vertex was clipped, clip the whole face
                return null;
            }

            projectedVertices[0][i] = pixelCoords[0];       // x
            projectedVertices[1][i] = pixelCoords[1];       // y
            depthValues[i] = depth[0];      // Same as in projectVertex() pass-by-reference trick
        }

        return projectedVertices;
    }

    private void updateFaceColor(Face face) {
        double distance = face.distanceToCamera(cameraX, cameraY, cameraZ);
        // TODO: Find optimal values and make them constants
        double intensity = 7.0 / (1.0 + distance);

        int red = (int) (face.getBaseColor().getRed() * intensity);
        int green = (int) (face.getBaseColor().getGreen() * intensity);
        int blue = (int) (face.getBaseColor().getBlue() * intensity);

        face.setCurrentColor(new Color(Math.clamp(red, 0, 255), Math.clamp(green, 0, 255), Math.clamp(blue, 0, 255)));
    }

    private void drawFaceWithDepthBuffer(Face face, int[][] projectedCoords, double[] zValues) {
        Polygon facePolygon = new Polygon(projectedCoords[0], projectedCoords[1], 4);
        Rectangle bounds = facePolygon.getBounds();

        for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
            for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
                boolean isInsideCanvas = (x >= 0 && x < CANVAS_WIDTH && y >= 0 && y < CANVAS_HEIGHT);
                if (isInsideCanvas && facePolygon.contains(x, y)) {
                    double interpolatedZ = interpolateZ(zValues);
                    if (interpolatedZ < depthBuffer[x][y]) {
                        depthBuffer[x][y] = interpolatedZ;      // Update depth buffer
                        g2d.setColor(face.getCurrentColor());
                        g2d.drawLine(x, y, x, y);     // Draw point
                    }
                }
            }
        }
    }

    private double interpolateZ(double[] zValues) {
        return (zValues[0] + zValues[1] + zValues[2] + zValues[3]) / 4.0;       // Simple bilinear interpolation
    }

    public void drawCube(Graphics2D g2d, Cube cube) {
        for (Face face : cube.getFaces()) {
            if (face.isVisible(cameraX, cameraY, cameraZ)) {
                updateFaceColor(face);
                g2d.setColor(face.getCurrentColor());

                double[] depthValues = new double[4];
                int[][] projectedCoords = projectFace(face, depthValues);

                if (projectedCoords != null) {
                    drawFaceWithDepthBuffer(face, projectedCoords, depthValues);
                }
            }
        }
    }

    public void drawScene(List<Cube> cubes) {
        resetDepthBuffer();
        for (Cube cube : cubes) {
            drawCube(g2d, cube);
        }
    }

    public void fillBackground(Color color) {
        int[] xBackground = {0, CANVAS_WIDTH, CANVAS_WIDTH, 0};
        int[] yBackground = {0, 0, CANVAS_HEIGHT, CANVAS_HEIGHT};
        g2d.setColor(color);
        g2d.fillPolygon(xBackground, yBackground, xBackground.length);
    }

    public void rotateCamera(double dYaw, double dPitch) {
        yaw += dYaw;
        pitch += dPitch;

        // Clamp the pitch to avoid gimbal lock
        pitch = Math.max(-Math.PI / 2, Math.min(Math.PI / 2, pitch));
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

        Random random = new Random();
        int cubeCountTest = 10;
        List<Cube> cubes = new ArrayList<>();
        for (int i = 0; i < cubeCountTest; i++) {
            cubes.add(new Cube(random.nextInt(10)-5, random.nextInt(10)-5, random.nextInt(10)-5, 1));
        }

        drawer.startDrawing();

        drawer.fillBackground(Color.BLACK);

        drawer.drawScene(cubes);

        drawer.endDrawing();
    }
}