package com.uqac.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

public class AspiBot {
    @Getter @Setter
    private int xPosition;
    @Getter @Setter
    private int yPosition;
    @Getter @Setter
    private int batteryLevel;
    @Getter @Setter
    private Board board;
    public enum Direction {UP, DOWN, LEFT, RIGHT}

    /*public AspiBot(int xPosition, int yPosition, Board board) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.board = board;
    }*/

    public AspiBot(Board board) {
        Random r = new Random();
        this.xPosition = r.nextInt(board.getWidth());
        this.yPosition = r.nextInt(board.getHeight());
        this.board = board;
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP:
                board.getTile(xPosition, yPosition).setVacuum(false);
                this.yPosition--;
                board.getTile(xPosition, yPosition).setVacuum(true);
                break;
            case DOWN:
                board.getTile(xPosition, yPosition).setVacuum(false);
                this.yPosition++;
                board.getTile(xPosition, yPosition).setVacuum(true);
                break;
            case LEFT:
                board.getTile(xPosition, yPosition).setVacuum(false);
                this.xPosition--;
                board.getTile(xPosition, yPosition).setVacuum(true);
                break;
            case RIGHT:
                board.getTile(xPosition, yPosition).setVacuum(false);
                this.xPosition++;
                board.getTile(xPosition, yPosition).setVacuum(true);
                break;
        }
    }
}
