package game;

public class LightSource {
    private final int x, y, z;
    private final double intensity;

    public LightSource(int x, int y, int z, double intensity) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.intensity = intensity;
    }

    public double calculateIntensityInPoint(int x, int y, int z, double attenuationFactor) {
        double distance = Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2) + Math.pow(z - this.z, 2));
        return intensity / (1 + attenuationFactor * (distance * distance));
    }
}
