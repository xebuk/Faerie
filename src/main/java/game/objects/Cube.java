package game.objects;

import java.awt.Color;
import java.io.Serializable;

public class Cube implements Serializable {
    // Signs of radius (+/-) in game.Face generation
    private static final int[][][] offsets = {
            {{-1,  1, -1}, { 1,  1, -1}, { 1, -1, -1}, {-1, -1, -1}},       // FRONT
            {{ 1,  1,  1}, {-1,  1,  1}, {-1, -1,  1}, { 1, -1,  1}},       // BACK
            {{-1,  1,  1}, {-1,  1, -1}, {-1, -1, -1}, {-1, -1,  1}},       // LEFT
            {{ 1,  1, -1}, { 1,  1,  1}, { 1, -1,  1}, { 1, -1, -1}},       // RIGHT
            {{-1,  1,  1}, { 1,  1,  1}, { 1,  1, -1}, {-1,  1, -1}},       // UP
            {{-1, -1, -1}, { 1, -1, -1}, { 1, -1,  1}, {-1, -1,  1}}        // DOWN
    };

    private final Triangle[] triangles = new Triangle[12];

    private final double x, y, z;
    private final double radius;

    private final Color color;
    private final Tiles tileID;

    public Cube(double x, double y, double z, double radius) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
        this.color = new Color(255, 150, 0);
        this.tileID = null;
        generateTriangles();
    }

    public Cube(double x, double y, double z, double radius, Color color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
        this.color = color;
        this.tileID = null;
        generateTriangles();
    }

    public Cube(double x, double y, double z, double radius, Tiles tileID) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
        this.color = new Color(255, 255, 255);
        this.tileID = tileID;
        generateTriangles();
    }

    private void generateTriangles() {
        for (int i = 0; i < 6; i++) {
            double[][] vertices1 = {
                    generateVertex(offsets[i][0]),
                    generateVertex(offsets[i][1]),
                    generateVertex(offsets[i][2])
            };

            triangles[i] = new Triangle(
                    vertices1,
                    tileID,
                    new double[][] {
                            {0, 1, 1},
                            {1, 1, 0}
                    }
            );

            double[][] vertices2 = {
                    generateVertex(offsets[i][0]),
                    generateVertex(offsets[i][2]),
                    generateVertex(offsets[i][3])
            };

            triangles[i + 6] = new Triangle(
                    vertices2,
                    tileID,
                    new double[][] {
                            {0, 1, 0},
                            {1, 0, 0}
                    }
            );
        }
    }

    private double[] generateVertex(int[] offset) {
        return new double[] {
                x + offset[0] * radius,
                y + offset[1] * radius,
                z + offset[2] * radius
        };
    }

    public Triangle[] getTriangles() {
        return triangles;
    }
}
