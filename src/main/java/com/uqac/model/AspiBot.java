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

}
