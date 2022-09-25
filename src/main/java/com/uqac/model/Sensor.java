package com.uqac.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import lombok.Getter;
import lombok.Setter;

public class Sensor {
    @Getter @Setter
    private Tile tile;
    @Getter @Setter
    private Board board;

    public Sensor(Board board, Tile tile) {
        this.board = board;
        this.tile = tile;
    }

    public Sensor(Sensor sensor) {
        this.board = sensor.getBoard();
        this.tile = sensor.getTile();
    }
    public int getXPosition()
    {
        return (int)tile.getX();
    }

    public int getYPosition()
    {
        return (int)tile.getY();
    }

    public void updateBoard(Board board) {
        this.board = new Board(board);
    }
}
