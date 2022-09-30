package com.uqac.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class Effector {
    private Sensor sensor;
    @Getter
    @Setter
    private List<Action> intentions;

    public Effector(Sensor sensor) {
        this.sensor = sensor;
        intentions = new ArrayList<>();
    }

    public void move(AspiBot aspiBot)
    {
        if (intentions.size()!=0)
        {
            move(aspiBot, intentions.get(0));
            intentions.remove(0);
        }
    }

    public void move(AspiBot aspiBot, Action action) {
        this.sensor = aspiBot.getSensor();
        Tile oldTile = sensor.getTile();
        int oldX = oldTile.getXPosition();
        int oldY = oldTile.getYPosition();

        if(action == Action.UP && sensor.getYPosition() > 0) {
            oldTile.setVacuum(false);
            oldTile.draw();
            sensor.setTile(sensor.getBoard().getTile(oldX, oldY - 1));
            sensor.getTile().setVacuum(true);
        }
        else if(action == Action.DOWN && sensor.getYPosition() < sensor.getBoard().getHeight() - 1) {
            oldTile.setVacuum(false);
            oldTile.draw();
            sensor.setTile(sensor.getBoard().getTile(oldX, oldY + 1));
            sensor.getTile().setVacuum(true);
        }
        else if(action == Action.LEFT && sensor.getXPosition() > 0) {
            oldTile.setVacuum(false);
            oldTile.draw();
            sensor.setTile(sensor.getBoard().getTile(oldX - 1, oldY));
            sensor.getTile().setVacuum(true);
        }
        else if(action == Action.RIGHT && sensor.getXPosition() < sensor.getBoard().getWidth() - 1) {
            oldTile.setVacuum(false);
            oldTile.draw();
            sensor.setTile(sensor.getBoard().getTile(oldX + 1, oldY));
            sensor.getTile().setVacuum(true);
        }

        if (action == Action.PICK_UP)
        {
            oldTile.setGem(false);
        }
        else if (action == Action.VACUUMIZE)
        {
            oldTile.setDust(false);
            oldTile.setGem(false);
        }

        sensor.getTile().draw();
    }
}
