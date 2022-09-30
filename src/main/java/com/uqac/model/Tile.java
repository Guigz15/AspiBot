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

    //Create an empty tile
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


    //Create a new tile from a given tile
    public Tile(Tile tile) {
        this.vacuum = tile.isVacuum();
        this.dust = tile.isDust();
        this.gem = tile.isGem();
        this.setFill(tile.getFill());
        this.setX(tile.getX());
        this.setY(tile.getY());
    }
    public int getXPosition()
    {
        return (int)getX();
    }

    public int getYPosition()
    {
        return (int)getY();
    }


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
    public void display()
    {
        System.out.print("(" +getXPosition() + ";"+getYPosition()+"); ");
    }
}
