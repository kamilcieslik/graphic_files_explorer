package plugin;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public class FxImageConverter {
    public Image bufferedImageToFxImage(BufferedImage input) {
        return SwingFXUtils.toFXImage(input, null);
    }

    public BufferedImage fxImageToBufferedImage(Image input) {
        return SwingFXUtils.fromFXImage(input, null);
    }
}
