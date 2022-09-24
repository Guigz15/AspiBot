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
        //Need to be removed later
        this.setOnMouseClicked(mouseEvent -> {
            System.out.println(this);
            System.out.println("vacuum: " + this.isVacuum() + " dust: " + this.isDust() + " gem: " + this.isGem());
        });
    }

    //Create a new tile from a given tile
    public Tile(Tile tile) {
        this.vacuum = tile.isVacuum();
        this.dust = tile.isDust();
        this.gem = tile.isGem();
        this.setFill(tile.getFill());
        this.setOnMouseClicked(mouseEvent -> {
            System.out.println("vacuum: " + vacuum + " dust: " + dust + " gem: " + gem);
        });
    }

    public void draw() {
        if (isDust() && isGem() && isVacuum()) {
            setFill(null);
            setFill(new ImagePattern(new Image("images/vacuum_dust_gem.png")));
        } else if (isDust() && isVacuum() && !isGem()) {
            setFill(null);
            setFill(new ImagePattern(new Image("images/vacuum_dust.png")));
        } else if (isGem() && isVacuum() && !isDust()) {
            setFill(null);
            setFill(new ImagePattern(new Image("images/vacuum_gem.png")));
        } else if (isDust() && isGem() && !isVacuum()) {
            setFill(null);
            setFill(new ImagePattern(new Image("images/dust_gem.png")));
        } else if (isDust() && !isGem() && !isVacuum()) {
            setFill(null);
            setFill(new ImagePattern(new Image("images/dust.png")));
        } else if (isGem() && !isDust() && !isVacuum()) {
            setFill(null);
            setFill(new ImagePattern(new Image("images/gem.png")));
        } else if (isVacuum() && !isGem() && !isDust()) {
            setFill(null);
            setFill(new ImagePattern(new Image("images/vacuum.png")));
        } else if (!isVacuum() && !isGem() && !isDust()) {
            setFill(null);
        }
    }
}
