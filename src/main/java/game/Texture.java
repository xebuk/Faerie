package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class Texture implements Serializable {
    private final BufferedImage image;

    public Texture(String pathToTexture) {
        try (InputStream istream = getClass().getClassLoader().getResourceAsStream(pathToTexture)) {
            if (istream == null) {
                throw new RuntimeException("Texture not found: " + pathToTexture);
            }
            image = ImageIO.read(istream);
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
