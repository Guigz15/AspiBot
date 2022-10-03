package com.uqac.model;

import lombok.Getter;
import lombok.Setter;

public class Sensor extends Tile {
    @Getter @Setter
    private Tile tile;
    @Getter @Setter
    private boolean clean;
    @Getter @Setter
    private Board board;

    /**
     * Sensor constructor
     * @param board board where the vacuum is evolving
     * @param tile tile where the vacuum is
     */
    public Sensor(Board board, Tile tile) {
        this.board = board;
        this.tile = tile;
        this.clean = true;
    }

    /**
     * Update state of the board (clean or not)
     */
    public void updateState()
    {
        if(board.getNbDust() == 0)
        {
            clean = true;
        }
        else
        {
            clean = false;
        }
    }

    /**
     * Get x position of the tile
     * @return tile x position
     */
    public int getXPosition()
    {
        return tile.getXPosition();
    }

    /**
     * Get y position of the tile
     * @return tile y position
     */
    public int getYPosition()
    {
        return tile.getYPosition();
    }

    /**
     * Get the tile farest from the vacuum
     * @param aspiBot vacuum
     * @return tile
     */
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
