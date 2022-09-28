package com.uqac.controller;

import com.uqac.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Decision
{
    private Sensor sensor;

    public List<Direction> bidirectionnal_search(Board board)
    {
        Tile goal = findFarestDust();
        Tile communTile = null;
        List<Tile> alreadySeenStart = new ArrayList<>();
        List<Tile> alreadySeenGoal = new ArrayList<>();
        SearchTree wayStart = new SearchTree(sensor.getTile());
        SearchTree wayGoal = new SearchTree(goal);
        alreadySeenStart.add(sensor.getTile());
        alreadySeenGoal.add(goal);
        boolean wayFinded = false;
        while (!wayFinded)
        {
            //propagation des arbres de recherches goal et start
            propagate(wayStart, alreadySeenStart);
            propagate(wayGoal, alreadySeenGoal);
            //MAJ de la liste forbiddenStart et de l'arbre wayStart
            wayStart.getLeaf().stream()
                    .distinct()
                    .forEach(leaf -> alreadySeenStart.add(leaf.getNode()));
            alreadySeenStart.stream().distinct();
            //MAJ de la liste forbiddenGoal et de l'arbre wayGoal
            wayGoal.getLeaf().stream()
                    .distinct()
                    .forEach(leaf -> alreadySeenGoal.add(leaf.getNode()));
            alreadySeenGoal.stream().distinct();
            communTile = wayStart.hasCommunNode(wayGoal);
            if (communTile != null)
            {
                wayFinded = true;
            }
        }
        //trouver le noeud commun aux arbre et relier les chemins
        List<Tile> firstPart = wayStart.getWayTo(communTile);
        List<Tile> secondPart = wayGoal.getWayTo(communTile);
        secondPart.remove(0);
        Collections.reverse(secondPart);
        firstPart.addAll(secondPart);
        return getInstruction(firstPart);
    }

    public void propagate(SearchTree tree, List<Tile> forbidenTiles)
    {
        List<Tile> neighbours = sensor.getBoard().getNeighbors(tree.getNode());
        List<Tile> tileToPropagate =
                neighbours.stream()
                .filter(neighbour -> !forbidenTiles.contains(neighbour))
                        .collect(Collectors.toList());
        if(!tileToPropagate.isEmpty())
        {
            tree.addSons(tileToPropagate);
        }
    }
    public List<Direction> getInstruction(List<Tile> way)
    {
        List<Direction> directions = new ArrayList<>();
        Tile currentTile = null;
        Tile nextTile = null;
        int xMove = 0;
        int yMove = 0;
        if(way.size() == 0)
        {
            return directions;
        }
        for (int i =0; i< way.size()-2; ++i)
        {
            currentTile = way.get(i);
            nextTile = way.get(i+1);
            xMove = nextTile.getXPosition() - currentTile.getXPosition();
            yMove = nextTile.getYPosition() - currentTile.getYPosition();
            if (xMove == 1 && yMove == 0)
            {
                directions.add(Direction.UP);
            }
            else if (xMove == -1 && yMove == 0)
            {
                directions.add(Direction.DOWN);
            }
            else if (xMove == 0 && yMove == 1)
            {
                directions.add(Direction.RIGHT);
            }
            else
            {
              directions.add(Direction.LEFT);
            }

        }
        return directions;
    }


    public Tile findFarestDust() {
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
}


