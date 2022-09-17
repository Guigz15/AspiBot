package com.uqac.controller;

import com.uqac.model.Board;
import com.uqac.model.Tile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable {
    @FXML @Getter
    private GridPane gridPane;
    @Getter @Setter
    private Board board;

    public MainWindowController() {
       this.board = new Board(5, 5);
    }
    public void initialize() {
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                Tile tile = board.getTile(i, j);
                tile.setHeight(100);
                tile.setWidth(100);
                gridPane.add(board.getTile(i, j), i, j);
            }
        }
        //System.out.println(((VBox)gridPane.getChildren().get(3)).getChildren().set(0, (new Rectangle()).setFill(); ImageView("images/dust_gem.png")));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialize();
    }
}
