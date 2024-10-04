package tools;

import java.awt.*;

public class Tools {
    public static double[] subtractVectors(double[] vector1, double[] vector2) {
        if (vector1.length != vector2.length) {
            throw new IllegalArgumentException("vector1.length != vector2.length");
        }
        double[] result = new double[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            result[i] = vector1[i] - vector2[i];
        }
        return result;
    }

    public static double vectorLength(double[] vector) {
        return Math.sqrt(dotProduct(vector, vector));
    }

    public static double[] normalize(double[] vector) {
        double length = vectorLength(vector);
        if (length == 0) {
            throw new IllegalArgumentException("Zero vector cannot be normalized");
        }
        double[] result = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            result[i] = vector[i] / length;
        }
        return result;
    }

    public static double dotProduct(double[] vector1, double[] vector2) {
        double dotProduct = 0;
        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
        }
        return dotProduct;
    }

    public static Color addColors(Color color1, Color color2) {
        int red = Math.min(color1.getRed() + color2.getRed(), 255);
        int green = Math.min(color1.getGreen() + color2.getGreen(), 255);
        int blue = Math.min(color1.getBlue() + color2.getBlue(), 255);
        return new Color(red, green, blue);
    }

    public static Color multiplyColors(Color color1, Color color2) {
        return new Color(
                (color1.getRed() * color2.getRed()) / 255,
                (color1.getGreen() * color2.getGreen()) / 255,
                (color1.getBlue() * color2.getBlue()) / 255
        );
    }
}
