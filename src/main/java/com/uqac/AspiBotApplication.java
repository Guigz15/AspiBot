package com.uqac;

import com.uqac.controller.MainWindowController;
import com.uqac.model.AspiBot;
import com.uqac.model.Board;
import com.uqac.model.MyThread;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("views/main_window.fxml")));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        mainWindowController = fxmlLoader.getController();
        stage.setTitle("AspiBot");
        stage.setScene(scene);
        stage.show();



        //Create a new thread to update the vacuum position and decide the next move
        /*MyThread vacuumThread = new MyThread("aspiBot") {
            @Override
            public void run() {
                super.runAspibot(aspiBot, mainWindowController);
            }
        };

        //Create a new thread to update the visual board
        MyThread boardUpdateThread = new MyThread("boardUpdate") {
            @Override
            public void run() {
                super.runUpdateBoard(mainWindowController, aspiBot);
            }
        };*/


        //aspiBot.aStar(aspiBot.getBoard(), aspiBot.getXPosition(), aspiBot.getYPosition());

        //}
        /*
        //Create a new thread to generate dust and gems on the board
        MyThread itemsThread = new MyThread("items") {
            @Override
            public void run() {
                super.runItemsGeneration(board);
            }
        };

        //Create a new thread to update the vacuum board
        MyThread vacuumBoardUpdateThread = new MyThread("vacuumBoardUpdate") {
            @Override
            public void run() {
                super.runVacuumBoardUpdate(aspiBot, board);
            }
        };*/



        stage.setOnCloseRequest(windowEvent -> mainWindowController.stop());
    }
}
