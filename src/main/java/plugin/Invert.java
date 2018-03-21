package plugin;

import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public class Invert {
    private static String effectName = "Invert";

    public Invert() {
    }

    public Image transformImage(Image input) {
        FxImageConverter fxImageConverter = new FxImageConverter();
        BufferedImage bufferedImageInput = fxImageConverter.fxImageToBufferedImage(input);

        int[] imagePixels = bufferedImageInput.getRGB(0, 0, bufferedImageInput.getWidth(),
                bufferedImageInput.getHeight(), null, 0, bufferedImageInput.getWidth());

        for (int i = 0; i < bufferedImageInput.getHeight() * bufferedImageInput.getWidth(); i++) {
            imagePixels[i] = calculatePixel(imagePixels[i]);
        }

        BufferedImage invertImage = new BufferedImage(bufferedImageInput.getWidth(), bufferedImageInput.getHeight(), BufferedImage.TYPE_INT_ARGB);
        invertImage.setRGB(0, 0, bufferedImageInput.getWidth(), bufferedImageInput.getHeight(), imagePixels, 0, bufferedImageInput.getWidth());
        return fxImageConverter.bufferedImageToFxImage(invertImage);
    }

    private int calculatePixel(int pixel) {
        int colorRed =(0xff & (pixel>>16));
        int colorGreen = (0xff & (pixel>>8));
        int colorBlue = (0xff & pixel);
        colorRed  = 256 - colorRed;
        colorGreen= 256 - colorGreen;
        colorBlue = 256 - colorBlue;
        if (colorRed > 255) colorRed =255;if(colorRed<0) colorRed=0;
        if (colorGreen > 255) colorGreen=255;if(colorGreen<0) colorGreen=0;
        if (colorBlue >255) colorBlue =255;if(colorBlue<0) colorBlue=0;
        pixel = (0xff000000 | colorRed << 16 | colorGreen <<8 | colorBlue);
        return pixel;
    }

    public String toString() {
        return effectName;
    }
}
