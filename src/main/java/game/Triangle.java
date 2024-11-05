package game;

import tools.Tools;

import java.io.Serializable;

public class Triangle implements Serializable {
    private final double[][] vertices;      // 3 vertices, each is (x, y, z)
    private final Texture texture;
    private final double[][] textureCoords;

    private double[] cashedNormal;
    private double[] cashedCenter;

    public Triangle(double[][] vertices, Texture texture, double[][] textureCoords) {
        this.vertices = vertices;
        this.texture = texture;
        this.textureCoords = textureCoords;

        this.cashedNormal = null;
        this.cashedCenter = null;
    }

    public boolean isVisible(double cameraX, double cameraY, double cameraZ) {
        double[] normal = calculateNormal();
        double[] triangleCenter = calculateCenter();
        double[] toCamera = vectorToCamera(triangleCenter, cameraX, cameraY, cameraZ);

        // Triangle is visible only if dot product > 0
        double dotProduct = normal[0] * toCamera[0] + normal[1] * toCamera[1] + normal[2] * toCamera[2];

        return dotProduct > 0;
    }

    private double[] calculateNormal() {
        double[] v0 = {
                vertices[1][0] - vertices[0][0],
                vertices[1][1] - vertices[0][1],
                vertices[1][2] - vertices[0][2]
        };
        double[] v1 = {
                vertices[2][0] - vertices[0][0],
                vertices[2][1] - vertices[0][1],
                vertices[2][2] - vertices[0][2]
        };

        double[] normal = new double[] {
                v0[1] * v1[2] - v0[2] * v1[1],
                v0[2] * v1[0] - v0[0] * v1[2],
                v0[0] * v1[1] - v0[1] * v1[0]
        };

        return Tools.normalize(normal);
    }

    private double[] calculateCenter() {
        double[] center = new double[3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                center[i] += vertices[j][i];
            }
            center[i] /= 3;
        }

        return center;
    }

    private double[] vectorToCamera(double[] center, double cameraX, double cameraY, double cameraZ) {
        return new double[] {
                cameraX - center[0],
                cameraY - center[1],
                cameraZ - center[2]
        };
    }

    public double[][] getVertices() {
        return vertices;
    }

    public Texture getTexture() {
        return texture;
    }

    public double[][] getTextureCoords() {
        return textureCoords;
    }

    public double[] getNormal() {
        if (cashedNormal == null) {
            cashedNormal = calculateNormal();
        }
        return cashedNormal;
    }

    public double[] getCenter() {
        if (cashedCenter == null) {
            cashedCenter = calculateCenter();
        }
        return cashedCenter;
    }
}
