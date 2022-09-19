package com.uqac;

import com.uqac.controller.MainWindowController;
import com.uqac.model.AspiBot;
import com.uqac.model.Board;
import com.uqac.model.MyThread;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
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
        Board board = new Board(mainWindowController.getBoard());
        AspiBot aspiBot = new AspiBot(board);
        drawVacuumDustGem(aspiBot.getXPosition(), aspiBot.getYPosition());
        stage.setTitle("AspiBot");
        stage.setScene(scene);
        stage.show();
        //Create a new thread to update the visual board
        MyThread boardUpdateThread = new MyThread("boardUpdate") {
            @Override
            public void run() {
                super.runUpdateBoard(AspiBotApplication.this, aspiBot);
            }
        };
        //Create a new thread to update the vacuum board
        MyThread vacuumBoardUpdateThread = new MyThread("vacuumBoardUpdate") {
            @Override
            public void run() {
                super.runVacuumBoardUpdate(aspiBot, board);
            }
        };
        //Create a new thread to update the vacuum position and decide the next move
        MyThread vacuumThread = new MyThread("aspiBot") {
            @Override
            public void run() {
                super.runAspibot(aspiBot, board);
            }
        };
        //Create a new thread to generate dust and gems on the board
        MyThread itemsThread = new MyThread("items") {
            @Override
            public void run() {
                super.runItemsGeneration(board);
            }
        };

        stage.setOnCloseRequest(windowEvent -> {vacuumThread.stop();itemsThread.stop();boardUpdateThread.stop();vacuumBoardUpdateThread.stop();});
    }

    //Different methods to draw the different elements on the board
    public void drawEmpty(int x, int y) {
        mainWindowController.getBoard().getTile(x, y).setFill(null);
    }

    public void drawDust(int x, int y) {
        mainWindowController.getBoard().getTiles().get(x).get(y).setFill(new ImagePattern(new Image("images/dust.png")));
        mainWindowController.getBoard().getTile(x, y).setDust(true);
    }

    public void drawGem(int x, int y) {
        mainWindowController.getBoard().getTiles().get(x).get(y).setFill(new ImagePattern(new Image("images/gem.png")));
        mainWindowController.getBoard().getTile(x, y).setGem(true);
    }

    public void drawVacuum(int x, int y) {
        mainWindowController.getBoard().getTiles().get(x).get(y).setFill(new ImagePattern(new Image("images/vaccum.png")));
        mainWindowController.getBoard().getTile(x, y).setVacuum(true);
    }

    public void drawDustGem(int x, int y) {
        mainWindowController.getBoard().getTiles().get(x).get(y).setFill(new ImagePattern(new Image("images/dust_gem.png")));
        mainWindowController.getBoard().getTile(x, y).setDust(true);
        mainWindowController.getBoard().getTile(x, y).setGem(true);
    }

    public void drawVacuumDust(int x, int y) {
        mainWindowController.getBoard().getTiles().get(x).get(y).setFill(new ImagePattern(new Image("images/vacuum_dust.png")));
        mainWindowController.getBoard().getTile(x, y).setVacuum(true);
        mainWindowController.getBoard().getTile(x, y).setDust(true);
    }

    public void drawVacuumDustGem(int x, int y) {
        mainWindowController.getBoard().getTiles().get(x).get(y).setFill(new ImagePattern(new Image("images/vacuum_dust_gem.png")));
        mainWindowController.getBoard().getTile(x, y).setVacuum(true);
        mainWindowController.getBoard().getTile(x, y).setDust(true);
        mainWindowController.getBoard().getTile(x, y).setGem(true);
    }
    public void drawVacuumGem(int x, int y) {
        mainWindowController.getBoard().getTiles().get(x).get(y).setFill(new ImagePattern(new Image("images/vacuum_gem.png")));
        mainWindowController.getBoard().getTile(x, y).setVacuum(true);
        mainWindowController.getBoard().getTile(x, y).setGem(true);
    }
}
