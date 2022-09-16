package com.uqac.controller;

import com.uqac.model.AspiBot;
import com.uqac.model.Tile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable {
    @FXML
    private GridPane gridPane;
    @Getter @Setter
    private List<List<Tile>> tiles;

    public void initialize() {
        for(int i = 0; i < 5; i++) {
            List<Tile> row = new ArrayList<>();
            for(int j = 0; j < 5; j++) {
                Tile tile = new Tile();
                row.add(tile);
            }
            tiles.add(row);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
