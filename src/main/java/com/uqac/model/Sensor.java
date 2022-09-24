package com.uqac.model;

import lombok.Getter;
import lombok.Setter;

public class Sensor {
    @Getter @Setter
    private int xPosition;
    @Getter @Setter
    private int yPosition;
    @Getter @Setter
    private Board board;

    public Sensor(Board board, int xPosition, int yPosition) {
        this.board = board;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public void updateBoard(Board board) {
        this.board = new Board(board);
    }
}
