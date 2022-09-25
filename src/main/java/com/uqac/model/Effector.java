package com.uqac.model;

import javafx.scene.paint.Color;

public class Effector {
    public enum Direction {UP, DOWN, LEFT, RIGHT};
    private Sensor sensor;

    public Effector(Sensor sensor) {
        this.sensor = new Sensor(sensor);
    }

    public void move(Board board, AspiBot aspiBot, Direction direction) {
        Sensor sensor = aspiBot.getSensor();
        Tile oldTile = sensor.getTile();
        int oldX = (int)oldTile.getX();
        int oldY = (int)oldTile.getY();

        if(direction == Direction.UP && sensor.getYPosition() > 0) {
            oldTile.setVacuum(false);
            sensor.getTile().setFill(null);
            //board.getTile(oldX, oldY).setVacuum(false);
            //board.getTile(oldX, oldY).setFill(null);

            sensor.setTile(sensor.getBoard().getTile(oldX, oldY - 1));
            sensor.getTile().setVacuum(true);
            //board.getTile(sensor.getXPosition(), sensor.getYPosition()).setVacuum(true);
        }
        else if(direction == Direction.DOWN && sensor.getYPosition() < sensor.getBoard().getHeight() - 1) {
            oldTile.setVacuum(false);
            sensor.getTile().setFill(null);
            //board.getTile(oldX, oldY).setVacuum(false);
            //board.getTile(oldX, oldY).setFill(null);

            sensor.setTile(sensor.getBoard().getTile(oldX, oldY + 1));
            sensor.getTile().setVacuum(true);
            //board.getTile(sensor.getXPosition(), sensor.getYPosition()).setVacuum(true);
        }
        else if(direction == Direction.LEFT && sensor.getXPosition() > 0) {
            oldTile.setVacuum(false);
            sensor.getTile().setFill(null);
            //board.getTile(oldX, oldY).setVacuum(false);
            //board.getTile(oldX, oldY).setFill(null);

            sensor.setTile(sensor.getBoard().getTile(oldX - 1, oldY));
            sensor.getTile().setVacuum(true);
            //board.getTile(sensor.getXPosition(), sensor.getYPosition()).setVacuum(true);
        }
        else if(direction == Direction.RIGHT && sensor.getXPosition() < sensor.getBoard().getWidth() - 1) {
            oldTile.setVacuum(false);
            sensor.getTile().setFill(null);
            //board.getTile(oldX, oldY).setVacuum(false);
            //board.getTile(oldX, oldY).setFill(null);

            sensor.setTile(sensor.getBoard().getTile(oldX + 1, oldY));
            sensor.getTile().setVacuum(true);
            //board.getTile(sensor.getXPosition(), sensor.getYPosition()).setVacuum(true);
        }
        sensor.getTile().draw();
        //System.out.println("AspiBot moved to " + sensor.getXPosition() + " " + sensor.getYPosition());
    }

    public void vacuumize(AspiBot aspiBot) {
        Sensor sensor = aspiBot.getSensor();
        if (sensor.getBoard().getTile(sensor.getXPosition(), sensor.getYPosition()).isDust()) {
            sensor.getBoard().getTile(sensor.getXPosition(), sensor.getYPosition()).setDust(false);
            //aspiBot.setBatteryLevel(aspiBot.getBatteryLevel() + 1);
        }
        if (sensor.getBoard().getTile(sensor.getXPosition(), sensor.getYPosition()).isGem()) {
            sensor.getBoard().getTile(sensor.getXPosition(), sensor.getYPosition()).setGem(false);
            //aspiBot.setBatteryLevel(aspiBot.getBatteryLevel() + 1);
        }
    }

    public void pickUpGem(AspiBot aspiBot) {
        Sensor sensor = aspiBot.getSensor();
        if (sensor.getBoard().getTile(sensor.getXPosition(), sensor.getYPosition()).isGem()) {
            sensor.getBoard().getTile(sensor.getXPosition(), sensor.getYPosition()).setGem(false);
            //aspiBot.setBatteryLevel(aspiBot.getBatteryLevel() + 1);
        }
    }

    public void updateBoard(Board board) {
        this.sensor.updateBoard(board);
    }
}
