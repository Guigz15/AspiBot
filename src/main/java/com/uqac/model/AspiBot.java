package com.uqac.model;

import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

public class AspiBot {
    @Getter
    private int xPosition;
    @Getter @Setter
    private int yPosition;
    @Getter @Setter
    private int batteryLevel;
    private enum Direction {UP, DOWN, LEFT, RIGHT};

    public AspiBot(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public AspiBot() {
        Random r = new Random();
        int low = 0;
        int high = 5;
        this.xPosition = r.nextInt(high-low) + low;
        this.yPosition = r.nextInt(high-low) + low;
    }

    public void move(Direction direction, GridPane board) {
        switch (direction) {
            case UP:
                this.yPosition++;
                break;
            case DOWN:
                this.yPosition--;
                break;
            case LEFT:
                this.xPosition--;
                break;
            case RIGHT:
                this.xPosition++;
                break;
        }
    }
}
