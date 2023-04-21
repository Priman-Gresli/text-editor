package lk.ijse.dep10.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep10.app.controller.EditorController;

import java.io.IOException;
import java.net.URL;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL fxmlFile = this.getClass().getResource("/view/EditorScene.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlFile);
        AnchorPane root = fxmlLoader.load();
        EditorController control = fxmlLoader.getController();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Text Editor");
        primaryStage.show();
        primaryStage.centerOnScreen();

    }
}
