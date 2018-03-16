package graphic_files_explorer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.lang.ref.SoftReference;

public class ImageFile extends StackPane {
    private File imageSource;
    private ImageView imageView;
    private SoftReference<Image> imageSoftReference;

    public ImageFile(File imageSource) {
        this.imageSource = imageSource;
        imageSoftReference = new SoftReference<>(null);
        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        getChildren().add(imageView);
        setStyle("-fx-border-color: #000000;");
        setSizes(80, 80, 80, 80, 80, 80);
    }

    public File getImageSource() {
        return imageSource;
    }

    public void setImageSource(File imageSource) {
        this.imageSource = imageSource;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public SoftReference<Image> getImageSoftReference() {
        return imageSoftReference;
    }

    public void setImageSoftReference(SoftReference<Image> imageSoftReference) {
        this.imageSoftReference = imageSoftReference;
    }

    public void setSizes(double minWidth, double minHeight, double prefWidth, double prefHeight, double maxWidth,
                         double maxHeight) {
        setMinWidth(minWidth);
        setMinHeight(minHeight);
        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);
        setMaxWidth(maxWidth);
        setMaxHeight(maxHeight);
    }

    public void loadOrReloadWeakReference() {
        if (imageSoftReference.get() == null)
            if (imageSource.exists())
                imageSoftReference = new SoftReference<>(new Image("file:" + imageSource.getPath()));
        imageView.setImage(imageSoftReference.get());
    }
}
