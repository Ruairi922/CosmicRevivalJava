package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class ImageLoader {
    public BufferedImage loadImage(String imagePath) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(imagePath);
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

