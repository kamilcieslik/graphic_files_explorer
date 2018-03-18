package javafx.controller;

import app.GraphicFilesExplorer;
import graphic_files_explorer.ImageFile;
import graphic_files_explorer.PluginsClassLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

public class ImageViewController implements Initializable {
    private PluginsClassLoader pluginsClassLoader;
    private File imageSource;
    private Image image;

    @FXML
    private ImageView imageView;
    @FXML
    private ComboBox<String> comboBoxPlugins;
    @FXML
    private ScrollPane scrollPaneImage;
    @FXML
    private FlowPane flowPaneImage;
    @FXML
    private Slider sliderImage;

    public void setImageFile(ImageFile imageFile, Scene scene) {
        imageSource = imageFile.getImageSource();
        setEffect(null);
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

        try {
            pluginsClassLoader = new PluginsClassLoader("plugin");
            new Thread(this::initPluginsList).start();
        } catch (IOException | ClassNotFoundException e) {
            comboBoxPlugins.setEditable(false);
        }
    }

    @FXML
    void comboBoxPlugins_onAction() {
        String selectedPlugin = comboBoxPlugins.getSelectionModel().getSelectedItem();
        if (selectedPlugin != null) {
            if (selectedPlugin.equals("Reset"))
                setEffect(null);
            else {
                setEffect(selectedPlugin);
            }
        }
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

    private void initPluginsList() {
        comboBoxPlugins.getItems().add("Reset");
        comboBoxPlugins.getItems().addAll(pluginsClassLoader.getPluginClassNames());
    }

    private void setEffect(String plugin) {
        if (plugin == null)
            if (image == null)
                image = new Image("file:" + imageSource.getPath());
            else
                imageView.setImage(image);
        else
            try {
                imageView.setImage(pluginsClassLoader.invokeImageTransformMethod(plugin, "transformImage", image));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                GraphicFilesExplorer.customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                        "Operacja nadania efektu dla obrazu nie powiodła się.",
                        "Powód: " + e.getMessage() + ".")
                        .showAndWait();
            }
    }
}
