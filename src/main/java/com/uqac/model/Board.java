package com.uqac.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

public class Board {
    @Getter @Setter
    private List<List<Tile>> tiles;
    @Getter @Setter
    private int height;
    @Getter @Setter
    private int width;

    //Create an empty board
    public Board(int height, int width) {
        this.height = height;
        this.width = width;
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

    //Create a new board from a given board
    public Board(Board board) {
        this.height = board.getHeight();
        this.width = board.getWidth();
        this.tiles = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List<Tile> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                Tile tile = new Tile(board.getTile(i, j));
                tile.setX(i);
                tile.setY(j);
                row.add(tile);
            }
            tiles.add(row);
        }
    }


    public Tile getTile(int x, int y) {
        return tiles.get(x).get(y);
    }
    public LinkedHashSet<Tile> getNeighbors(List<Tile> tiles)
    {
        LinkedHashSet<Tile> neighbors = new LinkedHashSet<>();
        if (tiles != null)
        {
            tiles.stream().forEach(tile ->
            {
                neighbors.addAll(getNeighbors(tile));
            });
        }
        return neighbors;
    }

    public List<Tile> getNeighbors(Tile tile) {
        int x = (int) tile.getX();
        int y = (int) tile.getY();
        List<Tile> neighbors = new ArrayList<>();
        if (x > 0) {
            neighbors.add(getTile(x - 1, y));
        }
        if (x < width - 1) {
            neighbors.add(getTile(x + 1, y));
        }
        if (y > 0) {
            neighbors.add(getTile(x, y - 1));
        }
        if (y < height - 1) {
            neighbors.add(getTile(x, y + 1));
        }
        return neighbors;
    }

    public List<Tile> getAllTiles() {
        List<Tile> allTiles = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                allTiles.add(getTile(i, j));
            }
        }
        return allTiles;
    }

    public void generateItems() {
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if(new Random().nextDouble() < 0.1) {
                    this.getTile(i, j).setDust(true);
                }
                if(new Random().nextDouble() < 0.05){
                    this.getTile(i, j).setGem(true);
                }
                this.getTile(i, j).draw();
            }
        }
    }
}
