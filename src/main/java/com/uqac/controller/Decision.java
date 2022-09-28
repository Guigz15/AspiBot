package com.uqac.controller;

import com.uqac.model.AspiBot;
import com.uqac.model.Sensor;
import com.uqac.model.Tile;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Decision
{
    @Getter @Setter
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
        sensor = aspiBot.getSensor();
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
        return tile;
    }

    /*public List<Tile> aStar(AspiBot aspiBot) {
        sensor = aspiBot.getSensor();
        Tile goal = findFarestDust(aspiBot);
        Tile start = sensor.getTile();
        List<List<Tile>> ways = new ArrayList<>();

        HashMap<Tile, HashMap<Tile, Integer>> everyDistance = new HashMap<>();
        for(int i = 0; i < sensor.getBoard().getHeight(); i++) {
            for(int j = 0; j < sensor.getBoard().getWidth(); j++) {
                List<Tile> neighbors = sensor.getBoard().getNeighbors(i, j);
                HashMap<Tile, Integer> distance = new HashMap<>();
                for(Tile neighbor : neighbors) {
                    if(neighbor.isDust()) {
                        distance.put(neighbor, 0);
                    }
                    else {
                        distance.put(neighbor, 1);
                    }
                    distance.put(neighbor, 1);
                }
                everyDistance.put(tile, distance);
            }
        }

        // This List is equivalent to h
        List<List<Integer>> everyTileToGoal = new ArrayList<>();

        // We are filling h
        for(int i = 0; i < sensor.getBoard().getHeight(); i++) {
            List<Integer> row = new ArrayList<>();
            for(int j = 0; j < sensor.getBoard().getWidth(); j++) {
                row.add(sensor.getBoard().distanceBetweenTiles(sensor.getBoard().getTile(i, j), goal));
            }
            everyTileToGoal.add(row);
        }


        while(sensor.getTile() != goal) {

        }
    }*/
}
