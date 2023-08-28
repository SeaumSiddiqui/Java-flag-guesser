import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageService {

    public static void updateImage(JLabel imageContainer, String resourcePath, boolean isResized, int targetWidth, int targetHeight)
    {
        BufferedImage image;
        try{
            InputStream inputStream = ImageService.class.getResourceAsStream(resourcePath);
            assert inputStream != null;
            image = ImageIO.read(inputStream);

            if (isResized)
                image = resizedImage(image, targetWidth, targetHeight);
            imageContainer.setIcon(new ImageIcon(image));

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static JLabel loadImage(String resourcePath, boolean isResized, int targetWidth, int targetHeight)
    {
        BufferedImage image;
        JLabel imageContainer;
        try {
            InputStream inputStream = ImageService.class.getResourceAsStream(resourcePath);
            assert inputStream != null;
            image = ImageIO.read(inputStream);

            if (isResized)
                image = resizedImage(image, targetWidth, targetHeight);
            imageContainer = new JLabel(new ImageIcon(image));

            return imageContainer;

        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    private static BufferedImage resizedImage(BufferedImage image, int targetWidth, int targetHeight) {
        BufferedImage newImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics2D = newImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();

        return newImage;
    }
}
