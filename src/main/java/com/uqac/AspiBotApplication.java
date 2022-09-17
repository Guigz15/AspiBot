package com.uqac;

import com.uqac.controller.MainWindowController;
import com.uqac.model.AspiBot;
import com.uqac.model.Tile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class AspiBotApplication extends Application {

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
        AspiBot aspiBot = new AspiBot(mainWindowController.getBoard());
        drawVacuumDustGem(aspiBot.getXPosition(), aspiBot.getYPosition());
        stage.setTitle("AspiBot");
        stage.setScene(scene);
        stage.show();

        aspiBot.move(AspiBot.Direction.RIGHT);
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                Tile tile = mainWindowController.getBoard().getTile(i, j);
                if(tile.isDust() && tile.isGem() && tile.isVacuum()) {
                    drawVacuumDustGem(i, j);
                }else if(tile.isDust() && tile.isVacuum() && !tile.isGem()) {
                    drawVacuumDust(i, j);
                }else if(tile.isGem() && tile.isVacuum() && !tile.isDust()) {
                    drawVacuumGem(i, j);
                }else if(tile.isDust() && tile.isGem() && !tile.isVacuum()) {
                    drawDustGem(i, j);
                }else if(tile.isDust() && !tile.isGem() && !tile.isVacuum()) {
                    drawDust(i, j);
                }else if(tile.isGem() && !tile.isDust() && !tile.isVacuum()) {
                    drawGem(i, j);
                }else if(tile.isVacuum() && !tile.isGem() && !tile.isDust()) {
                    drawVacuum(i, j);
                }
            }
        }
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
