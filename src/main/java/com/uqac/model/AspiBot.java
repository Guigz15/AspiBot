package com.uqac.model;

import com.uqac.controller.Decision;
import com.uqac.controller.Effector;
import com.uqac.controller.Sensor;
import lombok.Getter;
import lombok.Setter;
import java.util.Random;

/**
 * This class represents the vacuum cleaner
 */
public class AspiBot {
    @Getter @Setter
    private Sensor sensor;
    @Getter @Setter
    private Effector effector;
    @Getter @Setter
    private Decision decision;

    /**
     * AspiBot constructor
     * @param board where the vacuum is evolving
     */
    public AspiBot(Board board) {
        Random r = new Random();
        Tile startTile = board.getTile(r.nextInt(board.getHeight()), r.nextInt(board.getWidth()));
        startTile.setVacuum(true);
        startTile.draw();
        this.sensor = new Sensor(board, startTile);
        this.decision =new Decision(sensor);
        this.effector = new Effector(this.sensor);
    }
}
