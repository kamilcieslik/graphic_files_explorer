package plugin;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public class Sepia {
    private static String effectName = "Sepia";

    public Sepia() {
    }

    public Image transformImage(Image input) {
        BufferedImage bufferedImageInput = SwingFXUtils.fromFXImage(input, null);

        int sepiaDepth = 20;
        int[] imagePixels = bufferedImageInput.getRGB(0, 0, bufferedImageInput.getWidth(),
                bufferedImageInput.getHeight(), null, 0, bufferedImageInput.getWidth());

        for (int i = 0; i < imagePixels.length; i++) {
            int color = imagePixels[i];

            int colorRed = (color >> 16) & 0xff;
            int colorGreen = (color >> 8) & 0xff;
            int colorBlue = (color) & 0xff;
            int gry = (colorRed + colorGreen + colorBlue) / 3;

            colorRed = colorGreen = colorBlue = gry;
            colorRed = colorRed + (sepiaDepth * 2);
            colorGreen = colorGreen + sepiaDepth;

            if (colorRed > 255)
                colorRed = 255;
            if (colorGreen > 255)
                colorGreen = 255;
            if (colorBlue > 255)
                colorBlue = 255;

            colorBlue -= 10;

            if (colorBlue < 0)
                colorBlue = 0;
            if (colorBlue > 255)
                colorBlue = 255;

            imagePixels[i] = (color & 0xff000000) + (colorRed << 16) + (colorGreen << 8) + colorBlue;
        }

        BufferedImage sepiaImage = new BufferedImage(bufferedImageInput.getWidth(), bufferedImageInput.getHeight(), BufferedImage.TYPE_INT_ARGB);
        sepiaImage.setRGB(0, 0, bufferedImageInput.getWidth(), bufferedImageInput.getHeight(), imagePixels, 0, bufferedImageInput.getWidth());
        return SwingFXUtils.toFXImage(sepiaImage, null);
    }

    public String toString() {
        return effectName;
    }
}
