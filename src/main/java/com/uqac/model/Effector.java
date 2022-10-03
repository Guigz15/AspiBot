package com.uqac.model;

import com.uqac.controller.Sensor;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the vacuum effector
 */
public class Effector {
    private Sensor sensor;
    @Getter @Setter
    private List<Action> intentions;

    /**
     * Effector constructor
     * @param sensor of the vacuum
     */
    public Effector(Sensor sensor) {
        this.sensor = sensor;
        intentions = new ArrayList<>();
    }

    /**
     * Made the vacuum move
     * @param aspiBot to move
     */
    public void move(AspiBot aspiBot) {
        if (intentions.size()!=0) {
            move(aspiBot, intentions.get(0));
            intentions.remove(0);
        }
    }

    /**
     * Made the vacuum move
     * @param aspiBot to move
     * @param action to do
     */
    private void move(AspiBot aspiBot, Action action) {
        this.sensor = aspiBot.getSensor();
        Tile oldTile = sensor.getTile();
        int oldX = oldTile.getXPosition();
        int oldY = oldTile.getYPosition();
        int bonus= 0;
        if (action == Action.UP && sensor.getYPosition() > 0) {
            oldTile.setVacuum(false);
            oldTile.draw();
            sensor.setTile(sensor.getBoard().getTile(oldX, oldY - 1));
            sensor.getTile().setVacuum(true);
        }
        else if (action == Action.DOWN && sensor.getYPosition() < sensor.getBoard().getHeight() - 1) {
            oldTile.setVacuum(false);
            oldTile.draw();
            sensor.setTile(sensor.getBoard().getTile(oldX, oldY + 1));
            sensor.getTile().setVacuum(true);
        }
        else if (action == Action.LEFT && sensor.getXPosition() > 0) {
            oldTile.setVacuum(false);
            oldTile.draw();
            sensor.setTile(sensor.getBoard().getTile(oldX - 1, oldY));
            sensor.getTile().setVacuum(true);
        }
        else if (action == Action.RIGHT && sensor.getXPosition() < sensor.getBoard().getWidth() - 1) {
            oldTile.setVacuum(false);
            oldTile.draw();
            sensor.setTile(sensor.getBoard().getTile(oldX + 1, oldY));
            sensor.getTile().setVacuum(true);
        }
        else if (action == Action.PICK_UP) {
            oldTile.setGem(false);
            bonus += 1;
        }
        else if (action == Action.VACUUMIZE) {
            if(oldTile.isGem()) {
                bonus-=5;
                oldTile.setGem(false);
            }
            if (oldTile.isDust()) {
                bonus +=4;
                sensor.getBoard().updateNbDust(-1);
                oldTile.setDust(false);
            }
        }
        bonus -= 1;
        aspiBot.getDecision().updateEvaluation(bonus);
        sensor.getTile().draw();
    }
}
