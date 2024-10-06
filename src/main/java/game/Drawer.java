package game;

import common.Constants;
import tools.Tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class Drawer {
    private static final int CANVAS_WIDTH = 512;  /*465;*/  /*310;*/  /*720;*/  /*1440;*/
    private static final int CANVAS_HEIGHT = 288;  /*304;*/  /*202;*/  /*405;*/  /*810;*/

    private static final double FOV = Math.toRadians(60);
    private static final double FOCAL_LENGTH = 1.0 / Math.tan(FOV / 2);
    private static final double ASPECT_RATIO = (double) CANVAS_WIDTH / CANVAS_HEIGHT;

    private static final double NEAR_CLIPPING_PLANE = 0.1;
    private static final double FAR_CLIPPING_PLANE = 25.0;

    private static final double LIGHT_ATTENUATION_FACTOR = 1.0;

    private double cameraX, cameraY, cameraZ;
    private double yaw, pitch;
    private final double[][] depthBuffer;

    // TODO: Create base class/interface GameObject with getVertices() and etc. methods,
    //       replace -> List<GameObject>
    private List<Cube> sceneObjects;
    private List<LightSource> lights;

    private Graphics2D g2d;
    private BufferedImage image;

    private static final boolean DEBUG_SAVE = true;

    public Drawer(double cameraX, double cameraY, double cameraZ) {
        this.cameraX = cameraX;
        this.cameraY = cameraY;
        this.cameraZ = cameraZ;
        this.depthBuffer = new double[CANVAS_WIDTH][CANVAS_HEIGHT];

        resetDepthBuffer();
    }

    public void startDrawing(Color backgroundColor, List<Cube> sceneObjects, List<LightSource> lights) {
        image = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();

        fillBackground(backgroundColor);
        this.sceneObjects = sceneObjects;
        this.lights = lights;
    }

    public void endDrawing() {
        g2d.dispose();
        try {
            String path = Paths.get(Constants.IMAGE_OUTPUT_PATH, "output.png").toString();
            ImageIO.write(image, "png", new File(path));

            if (DEBUG_SAVE) {
                String pathDebug = Paths.get(".", "output.png").toString();
                ImageIO.write(image, "png", new File(pathDebug));
            }
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

        // Straight order: yaw -> pitch
        double tempX = cosYaw * x - sinYaw * z;
        double tempZ = sinYaw * x + cosYaw * z;

        double tempY = cosPitch * y - sinPitch * tempZ;
        tempZ = sinPitch * y + cosPitch * tempZ;

        return new double[] {tempX, tempY, tempZ};
    }

    private double[] unrotateVertex(double x, double y, double z) {
        double cosPitch = Math.cos(pitch);
        double sinPitch = Math.sin(pitch);
        double cosYaw = Math.cos(yaw);
        double sinYaw = Math.sin(yaw);

        // Inverse order: pitch -> yaw
        double tempY = cosPitch * y + sinPitch * z;
        double tempZ = -sinPitch * y + cosPitch * z;

        double tempX = cosYaw * x + sinYaw * tempZ;
        tempZ = -sinYaw * x + cosYaw * tempZ;

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

    private double[] unprojectPixel(int pixelX, int pixelY, double depth) {
        // Back to NDC
        double projX = 2.0 * pixelX / CANVAS_WIDTH - 1.0;
        double projY = 1.0 - 2.0 * pixelY / CANVAS_HEIGHT;

        double localX = projX * depth * ASPECT_RATIO / FOCAL_LENGTH;
        double localY = projY * depth / FOCAL_LENGTH;
        double localZ = depth;

        double[] unrotatedCoords = unrotateVertex(localX, localY, localZ);

        return new double[] {
                unrotatedCoords[0] + cameraX,
                unrotatedCoords[1] + cameraY,
                unrotatedCoords[2] + cameraZ
        };
    }

    private int[][] projectTriangle(Triangle triangle, double[] depthValues) {
        int[][] projectedVertices = new int[2][3];

        for (int i = 0; i < 3; i++) {
            double[] vertex = triangle.getVertices()[i];
            double[] depth = new double[1];
            int[] pixelCoords = projectVertex(vertex[0], vertex[1], vertex[2], depth);

            if (pixelCoords == null) {      // If one vertex was clipped, clip the whole triangle
                return null;
            }

            projectedVertices[0][i] = pixelCoords[0];       // x
            projectedVertices[1][i] = pixelCoords[1];       // y
            depthValues[i] = depth[0];      // Same as in projectVertex() pass-by-reference trick
        }

        return projectedVertices;
    }

    private void drawTriangleWithDepthBuffer(Triangle triangle, int[][] projectedCoords, double[] zValues) {
        Polygon trianglePolygon = new Polygon(projectedCoords[0], projectedCoords[1], 3);
        Rectangle bounds = trianglePolygon.getBounds();

        for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
            for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
                boolean isInsideCanvas = (x >= 0 && x < CANVAS_WIDTH && y >= 0 && y < CANVAS_HEIGHT);
                if (isInsideCanvas && trianglePolygon.contains(x, y)) {
                    double interpolatedZ = interpolateZ(x, y, projectedCoords, zValues);
                    if (interpolatedZ < depthBuffer[x][y]) {
                        depthBuffer[x][y] = interpolatedZ;      // Update depth buffer

                        Texture triangleTexture = triangle.getTexture();
                        if (triangleTexture == null) {
                            throw new RuntimeException("triangleTexture == null");
//                            g2d.setColor(triangle.getCurrentColor());
                        } else {
                            double[] interpolatedUV = interpolateUV(x, y, projectedCoords, zValues, triangle.getTextureCoords());
                            Color textureColor = triangleTexture.getColorAt(interpolatedUV[0], interpolatedUV[1]);

                            Color light = calculateLightning(triangle, x, y, interpolatedZ);
                            Color finalColor = Tools.multiplyColors(textureColor, light);
                            g2d.setColor(finalColor);
                        }

                        g2d.drawLine(x, y, x, y);     // Draw point
                    }
                }
            }
        }
    }

    private double interpolateZ(int x, int y, int[][] projectedCoords, double[] zValues) {
        // Perspective-correct interpolation
        double[] barycentricCoords = calculateBarycentricCoords(x, y, projectedCoords);
        double u = barycentricCoords[0];
        double v = barycentricCoords[1];
        double w = barycentricCoords[2];

        double zInverseInterpolated = u / zValues[0] + v / zValues[1] + w / zValues[2];
        return 1.0 / zInverseInterpolated;
    }

    private double[] interpolateUV(int x, int y, int[][] projectedCoords, double[] zValues, double[][] textureCoords) {
        double[] barycentricCoords = calculateBarycentricCoords(x, y, projectedCoords);
        double u = barycentricCoords[0];
        double v = barycentricCoords[1];
        double w = barycentricCoords[2];

        // Interpolate 1/z and texture coords
        double zInverseInterpolated = u / zValues[0] + v / zValues[1] + w / zValues[2];
        double uInterpolated = (u * textureCoords[0][0] / zValues[0] + v * textureCoords[0][1] / zValues[1] + w * textureCoords[0][2] / zValues[2]) / zInverseInterpolated;
        double vInterpolated = (u * textureCoords[1][0] / zValues[0] + v * textureCoords[1][1] / zValues[1] + w * textureCoords[1][2] / zValues[2]) / zInverseInterpolated;

        return new double[] {uInterpolated, vInterpolated};
    }

    private double[] calculateBarycentricCoords(int x, int y, int[][] projectedCoords) {
        double[] v0 = {projectedCoords[0][1] - projectedCoords[0][0], projectedCoords[1][1] - projectedCoords[1][0]};
        double[] v1 = {projectedCoords[0][2] - projectedCoords[0][0], projectedCoords[1][2] - projectedCoords[1][0]};
        double[] v2 = {x - projectedCoords[0][0], y - projectedCoords[1][0]};

        double v0v0 = v0[0] * v0[0] + v0[1] * v0[1];
        double v0v1 = v0[0] * v1[0] + v0[1] * v1[1];
        double v0v2 = v0[0] * v2[0] + v0[1] * v2[1];
        double v1v1 = v1[0] * v1[0] + v1[1] * v1[1];
        double v1v2 = v1[0] * v2[0] + v1[1] * v2[1];

        double denominator = v0v0 * v1v1 - v0v1 * v0v1;
        double v = (v1v1 * v0v2 - v0v1 * v1v2) / denominator;
        double w = (v0v0 * v1v2 - v0v1 * v0v2) / denominator;
        double u = 1.0 - v - w;

        return new double[] {u, v, w};
    }

    private Color calculateLightning(Triangle triangle, int x, int y, double depth) {
        Color pixelColor = new Color(0, 0, 0);

        double[] unprojectedPixelCoords = unprojectPixel(x, y, depth);
        double[] normal = triangle.getNormal();

        for (LightSource source : lights) {
            Color lightContribution = calculateLightContribution(unprojectedPixelCoords, normal, source);
            pixelColor = Tools.addColors(pixelColor, lightContribution);
        }

        return pixelColor;
    }

    private Color calculateLightContribution(double[] unprojectedPixelCoords, double[] normal, LightSource source) {
        double[] toLight = Tools.subtractVectors(source.getPosition(), unprojectedPixelCoords);

        double distance = Tools.vectorLength(toLight);
        toLight[0] /= distance;
        toLight[1] /= distance;
        toLight[2] /= distance;

        double attenuation = 1.0 / (source.getConstant() +
                                    source.getLinear() * distance +
                                    source.getQuadratic() * distance * distance);

        double angle = Math.max(0.0, Tools.dotProduct(normal, toLight));

        double red = source.getColor().getRed() * source.getIntensity() * angle * attenuation;
        double green = source.getColor().getGreen() * source.getIntensity() * angle * attenuation;
        double blue = source.getColor().getBlue() * source.getIntensity() * angle * attenuation;

        red = Math.clamp(red, 0, 255);
        green = Math.clamp(green, 0, 255);
        blue = Math.clamp(blue, 0, 255);

        return new Color((int) red, (int) green, (int) blue);
    }

    public void drawCube(Cube cube) {
        for (Triangle triangle : cube.getTriangles()) {
            if (triangle.isVisible(cameraX, cameraY, cameraZ)) {
                double[] depthValues = new double[4];
                int[][] projectedCoords = projectTriangle(triangle, depthValues);

                if (projectedCoords != null) {
                    drawTriangleWithDepthBuffer(triangle, projectedCoords, depthValues);
                }
            }
        }
    }

    public void drawScene() {
        resetDepthBuffer();
        for (Cube cube : sceneObjects) {
            drawCube(cube);
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

    public void moveCamera(double dx, double dz) {
        cameraX += -dx * Math.cos(yaw) + dz * Math.sin(yaw);
        cameraZ +=  dx * Math.sin(yaw) + dz * Math.cos(yaw);
    }
}