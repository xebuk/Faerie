package game;

import java.awt.Color;

public class Cube {
    // Signs of radius (+/-) in game.Face generation
    private static final int[][][] offsets = {
            {{-1,  1, -1}, { 1,  1, -1}, { 1, -1, -1}, {-1, -1, -1}},       // FRONT
            {{ 1,  1,  1}, {-1,  1,  1}, {-1, -1,  1}, { 1, -1,  1}},       // BACK
            {{-1,  1,  1}, {-1,  1, -1}, {-1, -1, -1}, {-1, -1,  1}},       // LEFT
            {{ 1,  1, -1}, { 1,  1,  1}, { 1, -1,  1}, { 1, -1, -1}},       // RIGHT
            {{-1,  1,  1}, { 1,  1,  1}, { 1,  1, -1}, {-1,  1, -1}},       // UP
            {{-1, -1, -1}, { 1, -1, -1}, { 1, -1,  1}, {-1, -1,  1}}        // DOWN
    };

    private final Face[] faces = new Face[6];

    private final double x, y, z;
    private final double radius;

    private final Color color;
    private final Texture texture;

    public Cube(double x, double y, double z, double radius) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
        this.color = new Color(255, 150, 0);
        this.texture = null;
        generateFaces();
    }

    public Cube(double x, double y, double z, double radius, Color color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
        this.color = color;
        this.texture = null;
        generateFaces();
    }

    public Cube(double x, double y, double z, double radius, Texture texture) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
        this.color = new Color(255, 255, 255);
        this.texture = texture;
        generateFaces();
    }

    private void generateFaces() {
        for (int i = 0; i < 6; i++) {
            double[][] vertices = {
                    generateFaceVertex(offsets[i][0]),      // TOP LEFT
                    generateFaceVertex(offsets[i][1]),      // TOP RIGHT
                    generateFaceVertex(offsets[i][2]),      // DOWN RIGHT
                    generateFaceVertex(offsets[i][3]),      // DOWN LEFT
            };

            faces[i] = new Face(
                    vertices,
                    color,
                    texture,
                    new double[][] {
                            {0, 1, 1, 0},
                            {0, 0, 1, 1}
                    }
            );
        }
    }

    private double[] generateFaceVertex(int[] offset) {
        return new double[] {
                x + offset[0] * radius,
                y + offset[1] * radius,
                z + offset[2] * radius
        };
    }

    public Face[] getFaces() {
        return faces;
    }
}
