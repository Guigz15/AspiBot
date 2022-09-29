package com.uqac.controller;

import com.uqac.model.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class Decision
{
    @Getter
    @Setter
    private Sensor sensor;
    public Decision(Sensor sensorArg)
    {
        sensor = new Sensor(sensorArg);
    }
    public List<Action> bidirectionnal_search() throws InterruptedException {
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


    public Tile findFarestDust() {
        int x = sensor.getXPosition();
        int y = sensor.getYPosition();
        //Tile tile = sensor.getBoard().getTile(x, y);
        Tile tile = null;
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


