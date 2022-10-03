package com.uqac.controller;

import com.uqac.model.AspiBot;
import com.uqac.model.Board;
import com.uqac.model.Tile;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents the sensor of the vacuum
 */
public class Sensor {
    @Getter @Setter
    private Tile tile;
    @Getter @Setter
    private boolean clean;
    @Getter @Setter
    private Board board;

    /**
     * Sensor constructor
     * @param board where the vacuum is evolving
     * @param tile where the vacuum is
     */
    public Sensor(Board board, Tile tile) {
        this.board = board;
        this.tile = tile;
        this.clean = true;
    }

    /**
     * Get x position of the tile
     * @return tile x position
     */
    public int getXPosition() {
        return tile.getXPosition();
    }

    /**
     * Get y position of the tile
     * @return tile y position
     */
    public int getYPosition() {
        return tile.getYPosition();
    }

    /**
     * Update state of the board (clean or not)
     */
    public void updateState() {
        clean = board.getNbDust() == 0;
    }

    /**
     * Get the furthest tile with dust from the vacuum
     * @param aspiBot vacuum
     * @return furthest tile with dust
     */
    public Tile findFurthestDust(AspiBot aspiBot) {
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
