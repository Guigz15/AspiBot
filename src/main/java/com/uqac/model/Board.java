package com.uqac.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
                row.add(tile);
            }
            tiles.add(row);
        }
    }

    public Tile getTile(int x, int y) {
        return tiles.get(x).get(y);
    }
}
