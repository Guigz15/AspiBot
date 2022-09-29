package com.uqac.controller;

import com.uqac.model.AspiBot;
import com.uqac.model.Sensor;
import com.uqac.model.Tile;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class Decision {
    @Getter @Setter
    private Sensor sensor;

    public Decision(Sensor sensor) {
        this.sensor = sensor;
    }

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



    public List<Tile> aStar(AspiBot aspiBot) {
        sensor = aspiBot.getSensor();
        Tile goal = sensor.findFarestDust(aspiBot);
        System.out.println("Goal : " + goal.getX() + " " + goal.getY());
        List<Tile> openList = new ArrayList<>();
        List<Tile> closedList = new ArrayList<>();
        List<Tile> path = new ArrayList<>();
        openList.add(sensor.getTile());
        while (!openList.isEmpty()) {
            Tile current = openList.get(0);
            for (Tile tile : openList) {
                if (tile.getF() < current.getF()) {
                    current = tile;
                }
            }
            openList.remove(current);
            closedList.add(current);
            if (current.equals(goal)) {
                Tile temp = current;
                path.add(temp);
                while (temp.getParent() != null) {
                    path.add(temp.getTileParent());
                    temp = temp.getTileParent();
                }
                Collections.reverse(path);
                return path;
            }
            List<Tile> neighbors = sensor.getBoard().getNeighbors((int)current.getX(), (int)current.getY());
            for (Tile neighbor : neighbors) {
                if (!closedList.contains(neighbor)) {
                    int tempG = current.getG() + 1;
                    if (openList.contains(neighbor)) {
                        if (tempG < neighbor.getG()) {
                            neighbor.setG(tempG);
                        }
                    } else {
                        neighbor.setG(tempG);
                        openList.add(neighbor);
                    }
                    neighbor.setH(Math.abs((int)neighbor.getX() - (int)goal.getX()) + Math.abs((int)neighbor.getY() - (int)goal.getY()));
                    neighbor.setF(neighbor.getG() + neighbor.getH());
                    neighbor.setTileParent(current);
                }
            }
        }
        return null;
    }
}
