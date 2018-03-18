package graphic_files_explorer;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.io.File;
import java.lang.ref.SoftReference;

public class ImageFile extends StackPane implements Runnable {
    private File imageSource;
    private ImageView imageView;
    private SoftReference<Image> imageSoftReference;
    private Double imageSize = 50.0;
    private VBox vBoxImageView;
    private VBox vBoxImageViewWithName;
    private Boolean eventHandlerExist = false;

    ImageFile(File imageSource) {
        this.imageSource = imageSource;
        imageSoftReference = new SoftReference<>(null);

        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(imageSize);
        imageView.setFitWidth(imageSize);

        vBoxImageView = new VBox();
        vBoxImageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0, 0, 0);" +
                "-fx-background-color: #ffffff");
        vBoxImageView.getChildren().add(imageView);
        vBoxImageView.alignmentProperty().setValue(Pos.CENTER);

        vBoxImageViewWithName = new VBox();
        vBoxImageViewWithName.setPrefWidth(imageSize);
        vBoxImageViewWithName.getChildren().add(vBoxImageView);
        vBoxImageViewWithName.alignmentProperty().setValue(Pos.BOTTOM_CENTER);

        Label imageName = new Label(imageSource.getName());

        vBoxImageViewWithName.getChildren().add(imageName);
        getChildren().add(vBoxImageViewWithName);
    }

    public Boolean getEventHandlerExist() {
        return eventHandlerExist;
    }

    public void setEventHandlerExist(Boolean eventHandlerExist) {
        this.eventHandlerExist = eventHandlerExist;
    }

    public void rotateLeft(Double rotateValue) {
        vBoxImageView.setRotate(vBoxImageView.getRotate() - rotateValue);

    }

    public void rotateRight(Double rotateValue) {
        vBoxImageView.setRotate(vBoxImageView.getRotate() + rotateValue);
    }

    public void resetRotate() {
        vBoxImageView.setRotate(0.0);
    }

    public void setImageSize(Double imageSize) {
        this.imageSize = imageSize;
        imageView.setFitHeight(imageSize);
        imageView.setFitWidth(imageSize);
        vBoxImageViewWithName.setPrefWidth(imageSize);
    }

    public File getImageSource() {
        return imageSource;
    }

    public void loadImage() {
        SwingUtilities.invokeLater(this);
    }

    @Override
    public void run() {
        if (imageSoftReference.get() == null)
            if (imageSource.exists())
                imageSoftReference = new SoftReference<>(new Image("file:" + imageSource.getPath()));
        imageView.setImage(imageSoftReference.get());
    }
}
