package com.uqac.model;

import java.util.ArrayList;
import java.util.List;

public class Effector {
    public enum Direction {UP, DOWN, LEFT, RIGHT}
    private Sensor sensor;

    public Effector(Sensor sensor) {
        this.sensor = sensor;
    }

    public void move(AspiBot aspiBot, Direction direction) {
        Sensor sensor = aspiBot.getSensor();
        Tile oldTile = sensor.getTile();
        int oldX = (int)oldTile.getX();
        int oldY = (int)oldTile.getY();

        if(direction == Direction.UP && sensor.getYPosition() > 0) {
            oldTile.setVacuum(false);
            oldTile.draw();
            sensor.setTile(sensor.getBoard().getTile(oldX, oldY - 1));
            sensor.getTile().setVacuum(true);
        }
        else if(direction == Direction.DOWN && sensor.getYPosition() < sensor.getBoard().getHeight() - 1) {
            oldTile.setVacuum(false);
            oldTile.draw();
            sensor.setTile(sensor.getBoard().getTile(oldX, oldY + 1));
            sensor.getTile().setVacuum(true);
        }
        else if(direction == Direction.LEFT && sensor.getXPosition() > 0) {
            oldTile.setVacuum(false);
            oldTile.draw();
            sensor.setTile(sensor.getBoard().getTile(oldX - 1, oldY));
            sensor.getTile().setVacuum(true);
        }
        else if(direction == Direction.RIGHT && sensor.getXPosition() < sensor.getBoard().getWidth() - 1) {
            oldTile.setVacuum(false);
            oldTile.draw();
            sensor.setTile(sensor.getBoard().getTile(oldX + 1, oldY));
            sensor.getTile().setVacuum(true);
        }
        sensor.getTile().draw();
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

    public List<Direction> convertPathToDirections(List<Tile> path) {
        List<Direction> directionsList = new ArrayList<>();
        for(Tile tile : path) {
            if(tile.getX() > sensor.getTile().getX()) {
                directionsList.add(Direction.RIGHT);
            }
            else if(tile.getX() < sensor.getTile().getX()) {
                directionsList.add(Direction.LEFT);
            }
            else if(tile.getY() > sensor.getTile().getY()) {
                directionsList.add(Direction.DOWN);
            }
            else if(tile.getY() < sensor.getTile().getY()) {
                directionsList.add(Direction.UP);
            }
        }
        return directionsList;
    }
}
