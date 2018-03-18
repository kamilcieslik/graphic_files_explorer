package plugin;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Saturation {
    private static String effectName = "Saturation";

    public Saturation() {
    }

    public Image transformImage(Image input) {
        BufferedImage bufferedImageInput = SwingFXUtils.fromFXImage(input, null);

        int[] imagePixels = bufferedImageInput.getRGB(0, 0, bufferedImageInput.getWidth(),
                bufferedImageInput.getHeight(), null, 0, bufferedImageInput.getWidth());

        for (int i = 0; i < bufferedImageInput.getWidth() * bufferedImageInput.getHeight(); i++) {
            imagePixels[i] = calculatePixel(imagePixels[i]);
        }

        BufferedImage sepiaImage = new BufferedImage(bufferedImageInput.getWidth(), bufferedImageInput.getHeight(), BufferedImage.TYPE_INT_ARGB);
        sepiaImage.setRGB(0, 0, bufferedImageInput.getWidth(), bufferedImageInput.getHeight(), imagePixels, 0, bufferedImageInput.getWidth());
        return SwingFXUtils.toFXImage(sepiaImage, null);
    }

    private int calculatePixel(int pixel) {
        int colorRed = (0xff & (pixel >> 16));
        int colorGreen = (0xff & (pixel >> 8));
        int colorBlue = (0xff & pixel);

        float hsb[] = Color.RGBtoHSB(colorRed, colorGreen, colorBlue, null);
        hsb[1]=hsb[1]+(15/100.0f);
        pixel = Color.HSBtoRGB(hsb[0],hsb[1],hsb[2]);
        colorRed = (0xff & (pixel >> 16));
        colorGreen = (0xff & (pixel >> 8));
        colorBlue = (0xff & pixel);
        if (colorRed > 255) {
            colorRed = 255;
        }
        if (colorRed < 0) {
            colorRed = 0;
        }
        if (colorGreen > 255) {
            colorGreen = 255;
        }
        if (colorGreen < 0) {
            colorGreen = 0;
        }
        pixel = (0xff000000 | colorRed << 16 | colorGreen << 8 | colorBlue);
        return pixel;
    }

    public String toString() {
        return effectName;
    }
}
