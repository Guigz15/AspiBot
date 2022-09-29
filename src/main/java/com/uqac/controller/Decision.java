package com.uqac.controller;

import com.uqac.model.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;
import java.util.*;

public class Decision {
    @Getter @Setter
    private Sensor sensor;

    public Decision(Sensor sensor) {
        this.sensor = sensor;
    }

    public List<Action> bidirectionnal_search()
    {
        Tile goal = findFarestDust();
        if (goal == null)
        {
            return new ArrayList<>();
        }
        Tile communTile = null;
        LinkedHashSet<Tile> alreadySeenStart = new LinkedHashSet<>();
        LinkedHashSet<Tile> alreadySeenGoal = new LinkedHashSet<>();
        LinkedHashSet<SearchTree> leafStart = new LinkedHashSet<>();
        LinkedHashSet<SearchTree> leafGoal = new LinkedHashSet<>();
        SearchTree wayStart = new SearchTree(sensor.getTile());
        System.out.println("Tile depart :");
        sensor.getTile().display();
        SearchTree wayGoal = new SearchTree(goal);
        System.out.println("Tile arrivée :");
        goal.display();
        alreadySeenStart.add(sensor.getTile());
        alreadySeenGoal.add(goal);
        boolean wayFinded = false;
        while (!wayFinded)
        {
            sleep(500);
            //propagation des arbres de recherches goal et start
            propagate(wayStart, alreadySeenStart);
            System.out.print( "wayStart :");
            wayStart.display();
            propagate(wayGoal, alreadySeenGoal);
            System.out.print( "wayGoal :");
            wayGoal.display();
            //MAJ de la liste forbiddenStart et de l'arbre wayStart
            leafStart.clear();
            leafStart.addAll(wayStart.getLeaf());
            leafGoal.clear();
            leafGoal.addAll(wayGoal.getLeaf());
            for (SearchTree leaf : leafStart)
            {
                alreadySeenStart.add(leaf.getNode());
            }
            //MAJ de la liste forbiddenGoal et de l'arbre wayGoal
            for (SearchTree leaf : leafGoal)
            {
                alreadySeenGoal.add(leaf.getNode());
            }
            System.out.print("alreadySeenStart :");
            alreadySeenStart.stream().forEach(tile->tile.display());
            System.out.print("alreadySeenGoal :");
            alreadySeenGoal.stream().forEach(tile->tile.display());
            communTile = wayStart.hasCommunNode(wayGoal);
            if (communTile != null)
            {
                wayFinded = true;
            }
        }
        System.out.print("communTile :");
        communTile.display();
        //trouver le noeud commun aux arbre et relier les chemins
        List<Tile> firstPart = wayStart.getWayTo(communTile);
        System.out.print("firstPart :");
        if (firstPart != null)
        {
            firstPart.stream().forEach(tile-> tile.display());
            System.out.println();
        }
        List<Tile> secondPart = wayGoal.getWayTo(communTile);
        System.out.print("secondPart :");
        if (secondPart != null)
        {
            secondPart.stream().forEach(tile-> tile.display());
            System.out.println();
        }
        Collections.reverse(secondPart);
        secondPart.remove(0);
        firstPart.addAll(secondPart);
        System.out.println("le chemin :");
        firstPart.stream().forEach(tile->tile.display());
        List<Action> intentions = new ArrayList<>();
        intentions.addAll(getInstruction(firstPart));
        intentions.stream().forEach(intention->System.out.println(intention.toString()));
        return intentions;
    }

    public void propagate(SearchTree tree, LinkedHashSet<Tile> forbiddenTiles)
    {
        List<SearchTree> leafs = tree.getLeaf();
        List<Tile> tileToPropagate = new ArrayList<>();
        leafs.stream().forEach(leaf->
        {
            List<Tile> neighbours = sensor.getBoard().getNeighbors(leaf.getNode()).stream()
                    .filter((neighbour -> !forbiddenTiles.contains(neighbour))).collect(Collectors.toList());
            leaf.addSons(neighbours);
        });
        /*
        LinkedHashSet<Tile> neighbours = sensor.getBoard().getNeighbors(tree.getLeaf());
        List<Tile> tileToPropagate =
                neighbours.stream()
                .filter(neighbour -> !forbiddenTiles.contains(neighbour))
                        .collect(Collectors.toList());
        if(!tileToPropagate.isEmpty())
        {
            tree.addSons(tileToPropagate);
        }

        */
    }
    public List<Action> getInstruction(List<Tile> way)
    {
        List<Action> actions = new ArrayList<>();
        Tile currentTile = null;
        Tile nextTile = null;
        int xMove = 0;
        int yMove = 0;
        if(way.size() == 0)
        {
            return actions;
        }
        for (int i =0; i< way.size()-1; ++i)
        {
            currentTile = way.get(i);
            nextTile = way.get(i+1);
            xMove = nextTile.getXPosition() - currentTile.getXPosition();
            yMove = nextTile.getYPosition() - currentTile.getYPosition();
            if (currentTile.isDust() && currentTile.isGem())
            {
                actions.add(Action.PICK_UP);
                actions.add(Action.VACUUMIZE);
            }
            else if (currentTile.isDust())
            {
                actions.add(Action.VACUUMIZE);
            }
            else if(currentTile.isGem())
            {
                actions.add(Action.PICK_UP);
            }
            if (xMove == 1 && yMove == 0)
            {
                actions.add(Action.RIGHT);
            }
            else if (xMove == -1 && yMove == 0)
            {
                actions.add(Action.LEFT);
            }
            else if (xMove == 0 && yMove == 1)
            {
                actions.add(Action.DOWN);
            }
            else if (xMove == 0 && yMove == -1)
            {
                actions.add(Action.UP);
            }
        }
        if (nextTile.isGem())
        {
            actions.add(Action.PICK_UP);
        }
        actions.add(Action.VACUUMIZE);
        System.out.print("Actions : ");
        actions.forEach(action -> System.out.print(action.toString() + " "));
        return actions;
    }



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
                while (temp.getTileParent() != null) {
                    path.add(temp.getTileParent());
                    temp = temp.getTileParent();
                }
                Collections.reverse(path);
                return path;
            }
            List<Tile> neighbors = sensor.getBoard().getNeighbors(current);
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
