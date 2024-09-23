package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {
    private final BufferedImage image;

    public Texture(String pathToTexture) {
        try {
            image = ImageIO.read(new File(pathToTexture));
        } catch (IOException e) {
            throw new RuntimeException("Texture loading failed", e);
        }
    }

    public Color getColorAt(double u, double v) {
        u = Math.clamp(u, 0, 1);
        v = Math.clamp(v, 0, 1);

        int x = (int) (u * (image.getWidth() - 1));
        int y = (int) (v * (image.getHeight() - 1));

        return new Color(image.getRGB(x, y));
    }
}
