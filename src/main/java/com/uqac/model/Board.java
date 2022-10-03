package com.uqac.model;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents the board where the vacuum is evolving
 */
public class Board {
    @Getter @Setter
    private List<List<Tile>> tiles;
    @Getter @Setter
    private int height;
    @Getter @Setter
    private int width;
    @Getter @Setter
    private int nbDust;

    /**
     * Board constructor
     * @param height of the board
     * @param width of the board
     */
    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        nbDust = 0;
        this.tiles = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List<Tile> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                Tile tile = new Tile();
                tile.setFill(null);
                tile.setX(i);
                tile.setY(j);
                row.add(tile);
            }
            tiles.add(row);
        }
    }

    /**
     * Update the number of dust on the board
     * @param amount of dust to add
     */
    public void updateNbDust(int amount) {
        nbDust += amount;
    }

    /**
     * Get a tile from the board
     * @param x of the tile
     * @param y of the tile
     * @return tile at position x, y
     */
    public Tile getTile(int x, int y) {
        return tiles.get(x).get(y);
    }

    /**
     * Get all neighbors of a tile
     * @param tile to get neighbors
     * @return list of neighbors of the tile
     */
    public List<Tile> getNeighbors(Tile tile) {
        int x = (int) tile.getX();
        int y = (int) tile.getY();
        List<Tile> neighbors = new ArrayList<>();
        if (x > 0)
            neighbors.add(getTile(x - 1, y));
        if (x < width - 1)
            neighbors.add(getTile(x + 1, y));
        if (y > 0)
            neighbors.add(getTile(x, y - 1));
        if (y < height - 1)
            neighbors.add(getTile(x, y + 1));
        return neighbors;
    }

    /**
     * This method is used to generate new items (dust and gem) on the board
     */
    public int generateItems() {
        int penalty = 0;
        try {
            Random r = new Random();
            //The time between the items generation is not definitive, I think we have to discuss it. Now the max time is 10 seconds.
            int time =  r.nextInt(11);
            Thread.sleep(time * 1000);
            for(int i = 0; i < 5; i++) {
                for(int j = 0; j < 5; j++) {
                    if(new Random().nextDouble() < 0.1) {
                        if(!this.getTile(i, j).isDust()) {
                            penalty += 4;
                            this.getTile(i, j).setDust(true);
                            nbDust += 1;
                        }
                    }
                    if(new Random().nextDouble() < 0.05) {
                        if(!this.getTile(i,j).isGem())
                            this.getTile(i, j).setGem(true);
                    }
                    this.getTile(i, j).draw();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return -penalty;
    }
}
