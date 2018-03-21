package javafx.controller;

import app.GraphicFilesExplorer;
import graphic_files_explorer.Directory;
import graphic_files_explorer.ImageFile;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static app.GraphicFilesExplorer.selectedImageFile;

public class MainController implements Initializable {
    private Directory selectedDirectory;
    private static double sliderStartValue = 90.0;

    @FXML
    private Label labelFilename, labelFileSize;
    @FXML
    private TreeView<Directory> treeViewDirectories;
    @FXML
    private ScrollPane scrollPaneImages;
    @FXML
    private FlowPane flowPaneImages;
    @FXML
    private Slider sliderFiles;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        flowPaneImages.prefWidthProperty().bind(scrollPaneImages.widthProperty());
        flowPaneImages.prefHeightProperty().bind(scrollPaneImages.heightProperty());

        setSelectedImageFileComponents(null, null);

        sliderFiles.setValue(sliderStartValue);
        sliderFiles.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedDirectory != null && selectedDirectory.getImageFiles() != null)
                selectedDirectory.getImageFiles().forEach(image ->
                        image.setImageSize((Double) newValue));
        });
    }

    @FXML
    void menuItemPluginsDirectoryPath_onAction() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Wybór lokalizacji pluginów");
        File directory = chooser.showDialog(sliderFiles.getScene().getWindow());
        if (directory != null) {
            GraphicFilesExplorer.pref.put("graphic_file_explorer_plugins_directory", directory.getAbsolutePath());
        }
    }

    @FXML
    void buttonTest() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Wybór lokalizacji zapisu szablonu życzeń");
        File directory = chooser.showDialog(sliderFiles.getScene().getWindow());
        if (directory != null) {
            System.out.println(directory.getAbsolutePath());
        }
    }

    @FXML
    void buttonEdit_onAction() {
        if (selectedImageFile != null) {
            FXMLLoader loader = new FXMLLoader();
            try {
                loader.setLocation(getClass().getClassLoader().getResource("fxml/image_view.fxml"));
                loader.load();
                Parent parent = loader.getRoot();
                Scene scene = new Scene(parent, 1600, 900);
                ImageViewController loaderController = loader.getController();
                loaderController.setImageFile(selectedImageFile, scene);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle(selectedImageFile.getImageSource().getName());
                stage.getIcons().add(new Image("/image/app_icon.png"));
                stage.setMinWidth(970);
                stage.setMinHeight(900);
                stage.setScene(scene);
                stage.showAndWait();
            } catch (IOException ioEcx) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ioEcx);
            }
        } else
            GraphicFilesExplorer.customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                    "Operacja otworzenia okna edycji obrazu nie powiedzie się.",
                    "Powód: nie zaznaczono obrazu.")
                    .showAndWait();
    }

    @FXML
    void buttonSelectRoot() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Wybór korzenia drzewa katalogów.");
        File rootDirectory = chooser.showDialog(scrollPaneImages.getScene().getWindow());
        if (rootDirectory != null) {
            TreeItem<Directory> rootDirectoryTreeItem = new TreeItem<>(new Directory(rootDirectory));
            buildTreeView(rootDirectoryTreeItem);
            treeViewDirectories.setRoot(rootDirectoryTreeItem);
            setSelectedImageFileComponents(null, null);
        }
    }

    @FXML
    void treeViewDirectories_onMouseClicked() {
        if (treeViewDirectories.getSelectionModel().getSelectedItem() != null) {
            selectedDirectory = treeViewDirectories.getSelectionModel().getSelectedItem().getValue();
            flowPaneImages.getChildren().clear();
            setSelectedImageFileComponents(null, null);
            if (selectedDirectory.getImageFiles() != null) {
                sliderFiles.setValue(sliderStartValue);
                selectedDirectory.getImageFiles().forEach(image -> {
                            SwingUtilities.invokeLater(image);
                            if (!image.getEventHandlerExist())
                                image.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                        event -> setSelectedImageFileComponents(image, event));
                            image.setImageSize(sliderStartValue);
                            image.resetRotate();
                            flowPaneImages.getChildren().add(image);
                        }
                );
            }
        }
    }

    private void setSelectedImageFileComponents(ImageFile imageFile, Event event) {
        if (imageFile == null) {
            selectedImageFile = null;
            labelFilename.setText("------");
            labelFileSize.setText("------");
        } else {
            selectedImageFile = imageFile;
            labelFilename.setText(imageFile.getImageSource().getName());
            labelFileSize.setText(String.valueOf(imageFile.getImageSource().length() / 1024) + " KB");
            imageFile.setEventHandlerExist(true);
            event.consume();
        }
    }

    private void buildTreeView(TreeItem<Directory> treeItem) {
        treeItem.getValue().getSubdirectories().forEach(dir -> {
            TreeItem<Directory> directoryTreeItem = new TreeItem<>(new Directory(dir));
            buildTreeView(directoryTreeItem);
            treeItem.getChildren().add(directoryTreeItem);
        });
    }
}
