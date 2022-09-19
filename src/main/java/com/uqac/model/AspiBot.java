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
        this.batteryLevel = 0;
    }

    public void updateBoard(Board board) {
        this.board = new Board(board);
    }

    public void vacuumize() {
        if (board.getTile(xPosition, yPosition).isDust()) {
            board.getTile(xPosition, yPosition).setDust(false);
            //batteryLevel += 1;
        }
        if (board.getTile(xPosition, yPosition).isGem()) {
            board.getTile(xPosition, yPosition).setGem(false);
            //batteryLevel += 1;
        }
    }

    public void pickUpGem() {
        if (board.getTile(xPosition, yPosition).isGem()) {
            board.getTile(xPosition, yPosition).setGem(false);
            //batteryLevel += 1;
        }
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP:
                if (yPosition > 0) {
                    board.getTile(xPosition, yPosition).setVacuum(false);
                    this.yPosition--;
                    board.getTile(xPosition, yPosition).setVacuum(true);
                }
                break;
            case DOWN:
                if (yPosition < board.getHeight() - 1) {
                    board.getTile(xPosition, yPosition).setVacuum(false);
                    this.yPosition++;
                    board.getTile(xPosition, yPosition).setVacuum(true);
                }
                break;
            case LEFT:
                if (xPosition > 0) {
                    board.getTile(xPosition, yPosition).setVacuum(false);
                    this.xPosition--;
                    board.getTile(xPosition, yPosition).setVacuum(true);
                }
                break;
            case RIGHT:
                if (xPosition < board.getWidth() - 1) {
                    board.getTile(xPosition, yPosition).setVacuum(false);
                    this.xPosition++;
                    board.getTile(xPosition, yPosition).setVacuum(true);
                }
                break;
        }
    }

}
