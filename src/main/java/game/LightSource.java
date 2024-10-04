package game;

import java.awt.*;

public class LightSource {
    private final double x, y, z;
    private final double intensity;
    private Color color;

    private final double constant, linear, quadratic;

    public LightSource(double x, double y, double z, double intensity) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.intensity = intensity;
        this.color = Color.WHITE;
        this.constant = 1.0;
        this.linear = 0.7;
        this.quadratic = 1.8;
    }

    public LightSource(double x, double y, double z, double intensity, Color color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.intensity = intensity;
        this.color = color;
        this.constant = 1.0;
        this.linear = 0.7;
        this.quadratic = 1.8;
    }

    public double[] getPosition() {
        return new double[] {this.x, this.y, this.z};
    }

    public double getIntensity() {
        return this.intensity;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public double getConstant() {
        return this.constant;
    }

    public double getLinear() {
        return this.linear;
    }

    public double getQuadratic() {
        return this.quadratic;
    }
}
