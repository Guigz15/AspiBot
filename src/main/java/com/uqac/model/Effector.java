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

    public int move(AspiBot aspiBot)
    {
        int bonus = 0;
        if (intentions.size()!=0)
        {
            bonus= move(aspiBot, intentions.get(0));
            intentions.remove(0);
        }
        return bonus;
    }

    private int move(AspiBot aspiBot, Action action) {
        int bonus =0;
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
        else if (action == Action.PICK_UP)
        {
            oldTile.setGem(false);
        }
        else if (action == Action.VACUUMIZE)
        {
            if(oldTile.isGem())
            {
                bonus-=5;
                oldTile.setGem(false);

            }
            if (oldTile.isDust())
            {
                bonus +=4;
                oldTile.setDust(false);
            }
        }
        bonus -= 1;

        sensor.getTile().draw();
        return bonus;
    }
}
