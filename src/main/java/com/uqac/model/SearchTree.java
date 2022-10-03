package com.uqac.model;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

public class SearchTree {
    @Getter @Setter
    private Tile node;
    @Getter @Setter
    private List<SearchTree> sonTrees;

    public SearchTree(Tile tileArg) {
        node = tileArg;
        sonTrees = new ArrayList<>();
    }

    public void addSons(List<Tile> sonTiles)
    {
        sonTiles.forEach(sonTile -> sonTrees.add(new SearchTree(sonTile)));
    }

    public boolean isLeaf() {
        return sonTrees == null || sonTrees.size() == 0;
    }

    public List<SearchTree> getLeaf() {
        List<SearchTree> leafs = new ArrayList<>();
        return getLeaf(leafs);
    }

    private List<SearchTree> getLeaf(List<SearchTree> leafs) {
        if (isLeaf()) {
            leafs.add(this);
            return leafs;
        } else {
            for (SearchTree son : sonTrees)
                son.getLeaf(leafs);
        }
        return leafs;
    }

    public boolean contains(Tile element)
    {
        return contains(element, false);
    }

    private boolean contains(Tile element, boolean isHere) {
        if (isLeaf() && !node.equals(element))
            return false;
        else if (node.equals(element))
            return true;
        else if (!getSonTrees().isEmpty()) {
            for(SearchTree son : sonTrees)
                isHere = isHere||son.contains(element, isHere);
            return isHere;
        }
        return false;
    }

    public List<Tile> getAllNodes() {
        List<Tile> nodes = new ArrayList<>();
        return getAllNodes(nodes);
    }

    private List<Tile> getAllNodes(List<Tile> nodes)  {
        if (isLeaf()) {
            nodes.add(node);
            return nodes;
        } else {
            nodes.add(node);
            for (SearchTree son : sonTrees)
                son.getAllNodes(nodes);
            return nodes;
        }
    }

    public Tile hasCommunNode(SearchTree treeToCompare)  {
        List<Tile> nodes = getAllNodes();
        List<Tile> nodesToCompare = treeToCompare.getAllNodes();
        for (Tile node : nodes) {
            for (Tile nodeToCompare : nodesToCompare) {
                if (node.equals(nodeToCompare))
                    return node;
            }
        }
        return null;
    }

    public List<Tile>getWayTo(Tile element)   {
        List<Tile> way = new ArrayList<>();
        List<Boolean> wayIsFind = new ArrayList<>();
        wayIsFind.add(false);
        return getWayTo(element, way, wayIsFind);
    }

    private List<Tile> getWayTo(Tile element, List<Tile> way, List<Boolean> wayIsFind)  {
        if(node.equals(element)) {
            way.add(node);
            wayIsFind.set(0, true);
            return way;
        }

        if (contains(element)) {
            way.add(node);
            for (SearchTree sonTree : sonTrees) {
                if (!wayIsFind.get(0) && sonTree.contains(element))
                    sonTree.getWayTo(element, way, wayIsFind);
            }
        }
        return way;
    }
}
