package com.uqac.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        this.sensor = new Sensor(board, r.nextInt(board.getHeight()), r.nextInt(board.getWidth()));
        this.effector = new Effector(this.sensor);
        this.batteryLevel = 0;
    }
    
    /*
    public Tile findFarestDust() {
        int x = xPosition;
        int y = yPosition;
        int xDust = 0;
        int yDust = 0;
        Tile tile = board.getTile(x, y);
        int distance = 0;
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (board.getTile(i, j).isDust()) {
                    int newDistance = Math.abs(x - i) + Math.abs(y - j);
                    if (distance < newDistance) {
                        distance = newDistance;
                        xDust = i;
                        yDust = j;
                        tile = board.getTiles().get(i).get(j);

                    }
                }
            }
        }
        if(tile.isDust()) {
            return tile;
        } else {
            return tile;
        }
    }*/

}
