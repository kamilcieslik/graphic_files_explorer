package plugin;

import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Grayscale {
    private static String effectName = "Grayscale";

    public Grayscale() {
    }

    public Image transformImage(Image input) {
        FxImageConverter fxImageConverter = new FxImageConverter();
        BufferedImage bufferedImageInput = fxImageConverter.fxImageToBufferedImage(input);

        BufferedImage grayscaleImage = new BufferedImage(bufferedImageInput.getWidth(), bufferedImageInput.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < bufferedImageInput.getWidth(); i++) {
            for (int j = 0; j < bufferedImageInput.getHeight(); j++) {
                Color color = new Color(bufferedImageInput.getRGB(i, j));
                int colorRed = color.getRed();
                int colorGreen = color.getGreen();
                int colorBlue = color.getBlue();
                int colorAlpha = color.getAlpha();

                int grayscaling = (colorRed + colorGreen + colorBlue) / 3;

                Color colorGray = new Color(grayscaling, grayscaling, grayscaling, colorAlpha);
                grayscaleImage.setRGB(i,j, colorGray.getRGB());
            }
        }

        return fxImageConverter.bufferedImageToFxImage(grayscaleImage);
    }

    public String toString() {
        return effectName;
    }
}
