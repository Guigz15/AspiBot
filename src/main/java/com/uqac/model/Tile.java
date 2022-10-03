package com.uqac.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

public class Tile extends Rectangle {
    @Getter @Setter
    private boolean vacuum;
    @Getter @Setter
    private boolean dust;
    @Getter @Setter
    private boolean gem;

    /**
     * Tile constructor
     */
    public Tile() {
        this.vacuum = false;
        this.dust = false;
        this.gem = false;
        this.setFill(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;

        if (getX() != tile.getX()) return false;
        return getY() == tile.getY();
    }


    /**
     * Tile constructor with another tile
     * @param tile tile to copy
     */
    public Tile(Tile tile) {
        this.vacuum = tile.isVacuum();
        this.dust = tile.isDust();
        this.gem = tile.isGem();
        this.setFill(tile.getFill());
        this.setX(tile.getX());
        this.setY(tile.getY());
    }

    /**
     * Get x position of the tile
     * @return x position
     */
    public int getXPosition()
    {
        return (int)getX();
    }

    /**
     * Get y position of the tile
     * @return y position
     */
    public int getYPosition()
    {
        return (int)getY();
    }

    /**
     * Draw objects in tile if an object is present
     */
    public void draw() {
        if (isDust() && isGem() && isVacuum()) {
            setFill(new ImagePattern(new Image("images/vacuum_dust_gem.png")));
        } else if (isDust() && isVacuum() && !isGem()) {
            setFill(new ImagePattern(new Image("images/vacuum_dust.png")));
        } else if (isGem() && isVacuum() && !isDust()) {
            setFill(new ImagePattern(new Image("images/vacuum_gem.png")));
        } else if (isDust() && isGem() && !isVacuum()) {
            setFill(new ImagePattern(new Image("images/dust_gem.png")));
        } else if (isDust() && !isGem() && !isVacuum()) {
            setFill(new ImagePattern(new Image("images/dust.png")));
        } else if (isGem() && !isDust() && !isVacuum()) {
            setFill(new ImagePattern(new Image("images/gem.png")));
        } else if (isVacuum() && !isGem() && !isDust()) {
            setFill(new ImagePattern(new Image("images/vacuum.png")));
        } else if (!isVacuum() && !isGem() && !isDust()) {
            setFill(null);
        }
    }
}
