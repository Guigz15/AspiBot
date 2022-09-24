package com.uqac.model;

public class Effector {
    public enum Direction {UP, DOWN, LEFT, RIGHT};
    private Sensor sensor;

    public Effector(Sensor sensor) {
        this.sensor = new Sensor(sensor);
    }

    public void move(AspiBot aspiBot, Direction direction) {
        Sensor sensor = this.sensor;
        if(direction == Direction.UP && sensor.getYPosition() > 0) {
            sensor.getBoard().getTile(sensor.getXPosition(), sensor.getYPosition()).setVacuum(false);
            sensor.setYPosition(sensor.getYPosition() - 1);
            sensor.getBoard().getTile(sensor.getXPosition(), sensor.getYPosition()).setVacuum(true);
        }
        else if(direction == Direction.DOWN && sensor.getYPosition() < sensor.getBoard().getHeight() - 1) {
            sensor.getBoard().getTile(sensor.getXPosition(), sensor.getYPosition()).setVacuum(false);
            sensor.setYPosition(sensor.getYPosition() + 1);
            sensor.getBoard().getTile(sensor.getXPosition(), sensor.getYPosition()).setVacuum(true);
        }
        else if(direction == Direction.LEFT && sensor.getXPosition() > 0) {
            sensor.getBoard().getTile(sensor.getXPosition(), sensor.getYPosition()).setVacuum(false);
            sensor.setXPosition(sensor.getXPosition() - 1);
            sensor.getBoard().getTile(sensor.getXPosition(), sensor.getYPosition()).setVacuum(true);
        }
        else if(direction == Direction.RIGHT && sensor.getXPosition() < sensor.getBoard().getWidth() - 1) {
            sensor.getBoard().getTile(sensor.getXPosition(), sensor.getYPosition()).setVacuum(false);
            sensor.setXPosition(sensor.getXPosition() + 1);
            sensor.getBoard().getTile(sensor.getXPosition(), sensor.getYPosition()).setVacuum(true);
        }
    }

    public void vacuumize(AspiBot aspiBot) {
        Sensor sensor = this.sensor;
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
        Sensor sensor = this.sensor;
        if (sensor.getBoard().getTile(sensor.getXPosition(), sensor.getYPosition()).isGem()) {
            sensor.getBoard().getTile(sensor.getXPosition(), sensor.getYPosition()).setGem(false);
            //aspiBot.setBatteryLevel(aspiBot.getBatteryLevel() + 1);
        }
    }
}
