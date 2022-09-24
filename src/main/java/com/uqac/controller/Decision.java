package com.uqac.controller;

import com.uqac.model.AspiBot;
import com.uqac.model.Sensor;
import com.uqac.model.Tile;

public class Decision
{
    private Sensor sensor;

    /*public List<Tile> bidirectionnal_search(Board board)
    {
        Tile goal = findFarestDust();
        List<Tile> alreadySeenStart = new ArrayList<>();
        List<Tile> alreadySeenGoal = new ArrayList<>();
        List<List<Tile>> wayStart = new ArrayList<>();
        List<List<Tile>> wayGoal = new ArrayList<>();
        int iteration = 0;
        alreadySeenStart.add(sensor.getTile());

    }

     */

    public Tile findFarestDust(AspiBot aspiBot) {
        Sensor sensor = aspiBot.getSensor();
        int x = (int)sensor.getTile().getX();
        int y = (int)sensor.getTile().getY();
        Tile tile = sensor.getBoard().getTile(x, y);
        int distance = 0;
        for (int i = 0; i < sensor.getBoard().getHeight(); i++) {
            for (int j = 0; j < sensor.getBoard().getWidth(); j++) {
                if (sensor.getBoard().getTile(i, j).isDust()) {
                    int newDistance = Math.abs(x - i) + Math.abs(y - j);
                    if (distance < newDistance) {
                        distance = newDistance;
                        tile = sensor.getBoard().getTiles().get(i).get(j);

                    }
                }
            }
        }
        if (tile.isDust()) {
            return tile;
        } else {
            return tile;
        }
    }
}
