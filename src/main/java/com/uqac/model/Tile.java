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

    public Tile() {
        this.vacuum = false;
        this.dust = false;
        this.gem = false;
    }
}
