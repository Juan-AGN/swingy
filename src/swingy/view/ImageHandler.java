package swingy.view;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageHandler {
    public static BufferedImage getImage(String imageRoute) {
        try {
            BufferedImage picture = ImageIO.read(new File(imageRoute));
            return (picture);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (null);
    }
}
