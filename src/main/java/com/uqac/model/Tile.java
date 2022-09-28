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
        this.setOnMouseClicked(mouseEvent -> {
            System.out.println(this);
        });
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
        this.setOnMouseClicked(mouseEvent -> {
            System.out.println("vacuum: " + vacuum + " dust: " + dust + " gem: " + gem);
        });
    }
    public int getXPosition()
    {
        return (int)getX();
    }

    public int getYPosition()
    {
        return (int)getY();
    }

}
