package app;

import graphic_files_explorer.ImageFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GraphicFilesExplorer extends Application {
    public static ImageFile selectedImageFile;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("fxml/main.fxml"));
            loader.load();
            Parent parent = loader.getRoot();
            primaryStage.setTitle("Graphic Files Explorer");
            primaryStage.getIcons().add(new Image("/image/app_icon.png"));
            primaryStage.setMinWidth(950);
            primaryStage.setMinHeight(800);
            Scene scene = new Scene(parent, 1600, 900);
            scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case A:
                        if (selectedImageFile != null)
                            selectedImageFile.rotateLeft(10.0);
                        break;
                    case D:
                        if (selectedImageFile != null)
                            selectedImageFile.rotateRight(10.0);
                        break;
                }
            });
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ioEcx) {
            Logger.getLogger(GraphicFilesExplorer.class.getName()).log(Level.SEVERE, null, ioEcx);
        }
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
