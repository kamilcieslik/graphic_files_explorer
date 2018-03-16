package javafx.controller;


import graphic_files_explorer.Directory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TreeView<Directory> treeViewDirectories;
    @FXML
    private ScrollPane scrollPaneImages;
    @FXML
    private FlowPane flowPaneImages;
    @FXML
    private Slider sliderFiles, sliderSelectedFile;
    @FXML
    private ComboBox<?> comboBoxFiles, comboBoxSelectedFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        flowPaneImages.prefWidthProperty().bind(scrollPaneImages.widthProperty());
        flowPaneImages.prefHeightProperty().bind(scrollPaneImages.heightProperty());
    }

    @FXML
    void buttonFilesTurnLeft_onAction() {

    }

    @FXML
    void buttonFilesTurnRight_onAction() {

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
        }
    }

    @FXML
    void buttonSelectedFileTurnLeft_onAction() {

    }

    @FXML
    void buttonSelectedFileTurnRight_onAction() {

    }

    @FXML
    void comboBoxFiles_onAction() {

    }

    @FXML
    void comboBoxSelectedFile_onAction() {

    }

    @FXML
    void treeViewDirectories_onMouseReleased() {

    }

    private void buildTreeView(TreeItem<Directory> treeItem) {
        treeItem.getValue().getSubdirectories().forEach(dir -> {
            TreeItem<Directory> directoryTreeItem = new TreeItem<>(new Directory(dir));
            buildTreeView(directoryTreeItem);
            treeItem.getChildren().add(directoryTreeItem);
        });
    }
}
