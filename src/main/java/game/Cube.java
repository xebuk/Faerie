package game;

import java.awt.Color;

class Cube {
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

    private Color color;

    public Cube(double x, double y, double z, double radius) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
        this.color = new Color(255, 150, 0);
        generateFaces();
    }

    public Cube(double x, double y, double z, double radius, Color color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
        this.color = color;
        generateFaces();
    }

    private void generateFaces() {
        for (int i = 0; i < 6; i++) {
            faces[i] = new Face(
                    generateFaceVertex(offsets[i][0]),      // TOP LEFT
                    generateFaceVertex(offsets[i][1]),      // TOP RIGHT
                    generateFaceVertex(offsets[i][2]),      // DOWN RIGHT
                    generateFaceVertex(offsets[i][3]),      // DOWN LEFT
                    color
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

class Face {
    private final double[][] vertices = new double[4][3];     // 4 vertices, each is (x, y, z)
    private final Color baseColor;
    private Color currentColor;

    public Face(double[] v1, double[] v2, double[] v3, double[] v4, Color baseColor) {
        this.vertices[0] = v1;
        this.vertices[1] = v2;
        this.vertices[2] = v3;
        this.vertices[3] = v4;
        this.baseColor = baseColor;
        this.currentColor = baseColor;
    }

    public double[] calculateCenter() {
        double[] center = new double[3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                center[i] += vertices[j][i];
            }
            center[i] /= 4;
        }

        return center;
    }

    public double[][] getVertices() {
        return vertices;
    }

    public Color getBaseColor() {
        return baseColor;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }
}