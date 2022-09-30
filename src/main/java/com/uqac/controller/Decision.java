package com.uqac.controller;

import com.uqac.model.*;
import lombok.Getter;
import lombok.Setter;
import java.util.*;
import java.util.stream.Collectors;

public class Decision {
    @Getter @Setter
    private Sensor sensor;

    public Decision(Sensor sensor) {
        this.sensor = sensor;
    }

    public List<Action> bidirectionnal_search(AspiBot aspiBot)
    {
        Tile goal = sensor.findFarestDust(aspiBot);
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
            System.out.print("alreadySeenStart :");
            alreadySeenStart.forEach(Tile::display);
            System.out.print("alreadySeenGoal :");
            alreadySeenGoal.forEach(Tile::display);
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
            firstPart.forEach(Tile::display);
            System.out.println();
        }
        List<Tile> secondPart = wayGoal.getWayTo(communTile);
        System.out.print("secondPart :");
        if (secondPart != null)
        {
            secondPart.forEach(Tile::display);
            System.out.println();
        }
        Collections.reverse(secondPart);
        secondPart.remove(0);
        firstPart.addAll(secondPart);
        System.out.println("le chemin :");
        firstPart.forEach(Tile::display);
        List<Action> intentions = new ArrayList<>();
        intentions.addAll(getInstruction(firstPart));
        intentions.forEach(intention->System.out.println(intention.toString()));
        return intentions;
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

    public List<Action> getInstruction(List<Tile> way)
    {
        List<Action> actions = new ArrayList<>();
        Tile currentTile;
        Tile nextTile = null;
        int xMove;
        int yMove;
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

    public List<Action> convertPathToActions(List<Tile> path) {
        List<Action> actionsList = new ArrayList<>();
        for(int i = 0; i < path.size() - 1; i++) {
            if(path.get(i).isGem()) {
                actionsList.add(Action.PICK_UP);
            }
            if(path.get(i).isDust()) {
                actionsList.add(Action.VACUUMIZE);
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
        }
        return actionsList;
    }

    public List<Tile> aStar(AspiBot aspiBot) {
        sensor = aspiBot.getSensor();
        Tile goal = sensor.findFarestDust(aspiBot);
        System.out.println("Start : " + sensor.getTile().getX() + " " + sensor.getTile().getY());
        System.out.println("Goal : " + goal.getX() + " " + goal.getY());
        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();
        List<Tile> path = new ArrayList<>();
        openList.add(new Node(sensor.getTile()));
        while (!openList.isEmpty()) {
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
                return path;
            }
            List<Tile> neighbors = sensor.getBoard().getNeighbors(current.getTile());
            for (Tile tile : neighbors) {
                Node neighbor = new Node(tile);
                if (!closedList.contains(neighbor)) {
                    int tempG = current.getG() + 1;
                    if (openList.contains(neighbor)) {
                        if (current.getTile().isGem())
                            tempG -= 1;
                        if (current.getTile().isDust())
                            tempG -= 2;
                    } else
                        openList.add(neighbor);

                    neighbor.setG(tempG);
                    neighbor.setH(Math.abs((int)neighbor.getTile().getX() - (int)goal.getX()) + Math.abs((int)neighbor.getTile().getY() - (int)goal.getY()));
                    neighbor.setF(neighbor.getG() + neighbor.getH());
                    neighbor.setNodeParent(current);
                }
            }
        }
        return null;
    }

    public class Node {

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
