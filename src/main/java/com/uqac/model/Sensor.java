package com.uqac.model;

import lombok.Getter;
import lombok.Setter;

public class Sensor {
    @Getter @Setter
    private Tile tile;
    @Getter @Setter
    private boolean clean;
    @Getter @Setter
    private Board board;

    public Sensor(Board board, Tile tile) {
        this.board = board;
        this.tile = tile;
        this.clean = true;
    }

    public int getXPosition()
    {
        return tile.getXPosition();
    }

    public int getYPosition()
    {
        return tile.getYPosition();
    }

    public Tile findFarestDust(AspiBot aspiBot) {
        Sensor sensor = aspiBot.getSensor();
        int x = sensor.getTile().getXPosition();
        int y = sensor.getTile().getYPosition();
        Tile tile = null;
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
        return tile;
    }
}
