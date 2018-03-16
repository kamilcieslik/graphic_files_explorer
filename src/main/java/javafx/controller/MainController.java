package javafx.controller;


import graphic_files_explorer.Directory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TreeView;
import javafx.scene.layout.FlowPane;

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
}
