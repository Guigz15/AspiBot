package com.uqac;

import com.uqac.controller.MainWindowController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;


public class AspiBotApplication extends Application {
    @Getter
    private MainWindowController mainWindowController;


    public static void main(String[] args) {
        launch();
    }

    /**
     * Start the application
     * @param stage the stage to display the application
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("views/main_window.fxml")));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        mainWindowController = fxmlLoader.getController();
        stage.setTitle("AspiBot");
        stage.setScene(scene);
        stage.getIcons().add(new Image("images/vacuum.png"));
        stage.show();

        stage.setOnCloseRequest(windowEvent -> {
            mainWindowController.stop();

            // Kill GUI Thread
            Platform.exit();
            // Kill the JVM
            System.exit(0);
        });
    }
}
