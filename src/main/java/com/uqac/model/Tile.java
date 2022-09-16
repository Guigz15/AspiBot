package com.uqac.model;

import lombok.Getter;
import lombok.Setter;

public class Tile {
    @Getter @Setter
    private boolean vacuum;
    @Getter @Setter
    private boolean dirt;
    @Getter @Setter
    private boolean gem;

    public Tile() {
        this.vacuum = false;
        this.dirt = false;
        this.gem = false;
    }
}
