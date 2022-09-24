package com.uqac.controller;

import com.uqac.model.Board;
import com.uqac.model.Tile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable {
    @FXML @Getter
    private GridPane gridPane;
    @Getter @Setter
    private Board board;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.board = new Board(5, 5);

        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                Tile tile = board.getTile(i, j);
                tile.setHeight(120);
                tile.setWidth(120);
                gridPane.add(board.getTile(i, j), i, j);
            }
        }
    }

    //Different methods to draw the different elements on the board
    public void drawEmpty(int x, int y) {
        this.getBoard().getTile(x, y).setFill(null);
    }

    public void drawDust(int x, int y) {
        this.getBoard().getTile(x, y).setFill(new ImagePattern(new Image("images/dust.png")));
        this.getBoard().getTile(x, y).setDust(true);
    }

    public void drawGem(int x, int y) {
        this.getBoard().getTile(x, y).setFill(new ImagePattern(new Image("images/gem.png")));
        this.getBoard().getTile(x, y).setGem(true);
    }

    public void drawVacuum(int x, int y) {
        this.getBoard().getTile(x, y).setFill(new ImagePattern(new Image("images/vacuum.png")));
        this.getBoard().getTile(x, y).setVacuum(true);
    }

    public void drawDustGem(int x, int y) {
        this.getBoard().getTile(x, y).setFill(new ImagePattern(new Image("images/dust_gem.png")));
        this.getBoard().getTile(x, y).setDust(true);
        this.getBoard().getTile(x, y).setGem(true);
    }

    public void drawVacuumDust(int x, int y) {
        this.getBoard().getTile(x, y).setFill(new ImagePattern(new Image("images/vacuum_dust.png")));
        this.getBoard().getTile(x, y).setVacuum(true);
        this.getBoard().getTile(x, y).setDust(true);
    }

    public void drawVacuumDustGem(int x, int y) {
        this.getBoard().getTile(x, y).setFill(new ImagePattern(new Image("images/vacuum_dust_gem.png")));
        this.getBoard().getTile(x, y).setVacuum(true);
        this.getBoard().getTile(x, y).setDust(true);
        this.getBoard().getTile(x, y).setGem(true);
    }
    public void drawVacuumGem(int x, int y) {
        this.getBoard().getTile(x, y).setFill(new ImagePattern(new Image("images/vacuum_gem.png")));
        this.getBoard().getTile(x, y).setVacuum(true);
        this.getBoard().getTile(x, y).setGem(true);
    }
}
