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
    @Getter @Setter
    private int f;
    @Getter @Setter
    private int g;
    @Getter @Setter
    private int h;
    @Getter @Setter
    private Tile tileParent;

    //Create an empty tile
    public Tile() {
        this.vacuum = false;
        this.dust = false;
        this.gem = false;
        this.setFill(null);
        this.f = 0;
        this.g = 0;
        this.h = 0;
        this.tileParent = null;
    }

    //Create a new tile from a given tile
    public Tile(Tile tile) {
        this.vacuum = tile.isVacuum();
        this.dust = tile.isDust();
        this.gem = tile.isGem();
        this.setFill(tile.getFill());
        this.setX(tile.getX());
        this.setY(tile.getY());
        this.f = tile.getF();
        this.g = tile.getG();
        this.h = tile.getH();
        this.tileParent = tile.getTileParent();
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
}
