package javafx.controller;

import graphic_files_explorer.ImageFile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ImageViewController implements Initializable {
    private File imageSource;
    private Image image;

    @FXML
    private ImageView imageView;
    @FXML
    private MenuButton menuButtonPlugins;
    @FXML
    private ScrollPane scrollPaneImage;
    @FXML
    private FlowPane flowPaneImage;
    @FXML
    private Slider sliderImage;

    public void setImageFile(ImageFile imageFile, Scene scene) {
        imageSource = imageFile.getImageSource();
        image = new Image("file:" + imageSource.getPath());
        imageView.setImage(image);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A:
                    imageView.setRotate(imageView.getRotate() - 10.0);
                    break;
                case D:
                    imageView.setRotate(imageView.getRotate() + 10.0);
                    break;
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageView.setPreserveRatio(true);
        setImageSize(400.0);

        flowPaneImage.prefWidthProperty().bind(scrollPaneImage.widthProperty());
        flowPaneImage.prefHeightProperty().bind(scrollPaneImage.heightProperty());

        sliderImage.setValue(400);
        sliderImage.valueProperty().addListener((observable, oldValue, newValue) -> setImageSize((Double) newValue));
    }

    @FXML
    void buttonResetRotate_onAction() {
        imageView.setRotate(0.0);
    }

    @FXML
    void buttonTurnLeft_onAction() {
        imageView.setRotate(imageView.getRotate() - 90.0);
    }

    @FXML
    void buttonTurnRight_onAction() {
        imageView.setRotate(imageView.getRotate() + 90.0);
    }

    private void setImageSize(Double imageSize) {
        imageView.setFitHeight(imageSize);
        imageView.setFitWidth(imageSize);
    }
}
