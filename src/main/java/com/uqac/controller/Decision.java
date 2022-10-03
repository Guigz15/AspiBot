package com.uqac.controller;

import com.uqac.model.*;
import lombok.Getter;
import lombok.Setter;
import java.util.*;
import java.util.stream.Collectors;

public class Decision {
    @Getter @Setter
    private Sensor sensor;
    @Getter @Setter
    private int evaluation;
    public Decision(Sensor sensor) {
        this.sensor = sensor;
        this.evaluation=100;
    }

    public void updateEvaluation(int amount)
    {

        evaluation =evaluation + amount;
        if (evaluation > 100)
        {
            evaluation = 100;
        }
        if(evaluation < 0)
        {
            evaluation = 0;
        }
    }
    public List<Action> chooseAlgorithm (AspiBot aspiBot)
    {
        Tile goal = sensor.findFarestDust(aspiBot);
        if (goal == null)
        {
            sensor.setClean(true);
            return new ArrayList<>();
        }
        HashMap<Integer,List<Action>> actions = new HashMap<>();
        HashMap<Integer,List<Action>> temp = bidirectionnalSearch(aspiBot, goal);
        int bidirectionalKey = temp.keySet().iterator().next();
        actions.put(bidirectionalKey, temp.get(bidirectionalKey));
        temp = aStar(aspiBot,goal);

        int aStarKey = temp.keySet().iterator().next();
        actions.put(aStarKey, temp.get(aStarKey));

        if(aStarKey >= bidirectionalKey)
        {
            return actions.get(aStarKey);
        }
        return actions.get(bidirectionalKey);

    }
    public HashMap<Integer,List<Action>> bidirectionnalSearch(AspiBot aspiBot, Tile goal)
    {

        Tile communTile = null;
        LinkedHashSet<Tile> alreadySeenStart = new LinkedHashSet<>();
        LinkedHashSet<Tile> alreadySeenGoal = new LinkedHashSet<>();
        LinkedHashSet<SearchTree> leafStart = new LinkedHashSet<>();
        LinkedHashSet<SearchTree> leafGoal = new LinkedHashSet<>();
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
            communTile = wayStart.hasCommunNode(wayGoal);
            if (communTile != null)
            {
                wayFinded = true;
            }
        }
        //trouver le noeud commun aux arbre et relier les chemins
        List<Tile> firstPart = wayStart.getWayTo(communTile);
        List<Tile> secondPart = wayGoal.getWayTo(communTile);
        Collections.reverse(secondPart);
        secondPart.remove(0);
        firstPart.addAll(secondPart);
        return convertPathToActions(firstPart);
    }

    public void propagate(SearchTree tree, LinkedHashSet<Tile> forbiddenTiles)
    {
        List<SearchTree> leafs = tree.getLeaf();
        leafs.forEach(leaf->
        {
            List<Tile> neighbours = sensor.getBoard().getNeighbors(leaf.getNode()).stream()
                    .filter((neighbour -> !forbiddenTiles.contains(neighbour))).collect(Collectors.toList());
            leaf.addSons(neighbours);
        });
    }

    public HashMap<Integer,List<Action>> convertPathToActions(List<Tile> path) {
        List<Action> actionsList = new ArrayList<>();
        int bonus =0;
        for(int i = 0; i < path.size() - 1; i++) {
            if(path.get(i).isGem()) {
                actionsList.add(Action.PICK_UP);
            }
            if(path.get(i).isDust()) {
                actionsList.add(Action.VACUUMIZE);
                bonus += 4;
            }
            if(path.get(i).getX() < path.get(i + 1).getX()) {
                actionsList.add(Action.RIGHT);

            }
            else if(path.get(i).getX() > path.get(i + 1).getX()) {
                actionsList.add(Action.LEFT);
            }
            else if(path.get(i).getY() < path.get(i + 1).getY()) {
                actionsList.add(Action.DOWN);
            }
            else if(path.get(i).getY() > path.get(i + 1).getY()) {
                actionsList.add(Action.UP);
            }
            bonus -= 1;
        }
        if(path.get(path.size()-1).isGem())
        {
            actionsList.add(Action.PICK_UP);
        }
        actionsList.add(Action.VACUUMIZE);
        HashMap<Integer,List<Action>> mapAction = new HashMap<>();
        mapAction.put(bonus, actionsList);
        return mapAction;
    }

    public HashMap<Integer,List<Action>> aStar(AspiBot aspiBot,Tile goal) {
        sensor = aspiBot.getSensor();
        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();
        List<Tile> path = new ArrayList<>();
        openList.add(new Node(sensor.getTile()));
        while (!openList.isEmpty()) {
            openList.sort(Comparator.comparing(Node::getF));
            Node current = openList.get(0);
            for (Node node : openList) {
                if (node.getF() < current.getF()) {
                    current = node;
                }
            }
            openList.remove(current);
            closedList.add(current);
            if (current.getTile().equals(goal)) {
                Node temp = current;
                path.add(temp.getTile());
                while (temp.getNodeParent() != null) {
                    path.add(temp.getNodeParent().getTile());
                    temp = temp.getNodeParent();
                }
                Collections.reverse(path);
                return convertPathToActions(path);
            }
            List<Tile> neighbors = sensor.getBoard().getNeighbors(current.getTile());
            for (Tile tile : neighbors) {
                Node neighbor = new Node(tile);
                if (!closedList.contains(neighbor)) {
                    int tempG = current.getG() + 1;
                    if (current.getTile().isGem())
                        tempG -= 1;
                    if (current.getTile().isDust())
                        tempG -= 2;
                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }

                    neighbor.setG(tempG);
                    neighbor.setH(Math.abs((int)neighbor.getTile().getX() - (int)goal.getX()) + Math.abs((int)neighbor.getTile().getY() - (int)goal.getY()));
                    neighbor.setF(neighbor.getG() + neighbor.getH());
                    neighbor.setNodeParent(current);
                }
            }
        }
        return null;
    }

    private class Node {

        @Getter @Setter
        private int f;
        @Getter @Setter
        private int g;
        @Getter @Setter
        private int h;
        @Getter @Setter
        private Node nodeParent;
        @Getter @Setter
        private Tile tile;

        public Node(Tile tile) {
            this.f = 0;
            this.g = 0;
            this.h = 0;
            this.nodeParent = null;
            this.tile = tile;
        }
    }
}
