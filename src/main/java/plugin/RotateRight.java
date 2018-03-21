package plugin;

import javafx.scene.image.Image;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class RotateRight {
    private static String effectName = "RotateRight";

    public RotateRight() {
    }

    public Image transformImage(Image input) {
        FxImageConverter fxImageConverter = new FxImageConverter();
        BufferedImage bufferedInput = fxImageConverter.fxImageToBufferedImage(input);
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.PI / 2, bufferedInput.getWidth() / 2, bufferedInput.getHeight() / 2);
        AffineTransformOp transformOp = new AffineTransformOp(transform,
                AffineTransformOp.TYPE_BILINEAR);
        BufferedImage bufferedOutput = transformOp.filter(bufferedInput, null);
        return fxImageConverter.bufferedImageToFxImage(bufferedOutput);
    }

    @Override
    public String toString() {
        return "RotateRight{}";
    }
}
