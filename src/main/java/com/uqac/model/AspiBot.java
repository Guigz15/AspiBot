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
        Tile StartTile = new Tile(board.getTile(r.nextInt(board.getHeight()), r.nextInt(board.getWidth())));
        this.sensor = new Sensor(board,StartTile);
        this.effector = new Effector(this.sensor);
        this.batteryLevel = 0;
    }
    
    /*
    public Tile findFarestDust() {
        int x = (int)sensor.getTile().getX();
        int y = (int)sensor.getTile().getY();
        Tile tile = sensor.getBoard().getTile(x, y);
        int distance = 0;
        for (int i = 0; i < sensor.getBoard().getHeight(); i++) {
            for (int j = 0; j < sensor.getBoard().getWidth(); j++) {
                if (sensor.getBoard().getTile(i, j).isDust()) {
                    int newDistance = Math.abs(x - i) + Math.abs(y - j);
                    if (distance < newDistance) {
                        distance = newDistance;
                        tile = sensor.getBoard().getTiles().get(i).get(j);

                    }
                }
            }
        }
        if (tile.isDust()) {
            return tile;
        } else {
            return tile;
        }
    }
    }*/

}
