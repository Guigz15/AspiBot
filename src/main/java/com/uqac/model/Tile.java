package com.uqac.model;

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
}
