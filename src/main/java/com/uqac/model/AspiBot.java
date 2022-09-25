package com.uqac.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

public class AspiBot {
    @Getter @Setter
    private Sensor sensor;
    @Getter @Setter
    private Effector effector;
    @Getter @Setter
    private int batteryLevel;

    public AspiBot(Board board) {
        Random r = new Random();
        Tile startTile = new Tile(board.getTile(r.nextInt(board.getHeight()), r.nextInt(board.getWidth())));
        startTile.setVacuum(true);
        startTile.setFill(new ImagePattern(new Image("images/vacuum.png")));
        this.sensor = new Sensor(board, startTile);
        this.effector = new Effector(this.sensor);
        this.batteryLevel = 0;
    }

}
