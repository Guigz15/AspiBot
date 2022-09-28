package com.uqac.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class SearchTree
{
    @Getter
    @Setter
    private Tile node;
    @Getter
    @Setter
    private List<SearchTree> sonTrees;

    public SearchTree(Tile tileArg)
    {
        node = tileArg;
        sonTrees = new ArrayList<>();
    }

    public void addSon(Tile sonTile)
    {
        sonTrees.add(new SearchTree(sonTile));
    }
    public void addSons(List<Tile> sonTiles)
    {
        sonTiles.forEach(sonTile -> sonTrees.add(new SearchTree(sonTile)));
    }
    public boolean isLeaf()
    {
        if (sonTrees.size()==0)
        {
            return true;
        }
        return false;
    }
    public List<SearchTree> getLeaf()
    {
        List<SearchTree> leafs = new ArrayList<>();
        return getLeaf(leafs);
    }

    private List<SearchTree> getLeaf(List<SearchTree> leafs)
    {
        if (isLeaf())
        {
            leafs.add(this);
            return leafs;
        }
        else
        {
            for (SearchTree son : sonTrees)
            {
             son.getLeaf(leafs);
            }
        }
        return null;
    }
    public boolean contains(Tile element)
    {
        return contains(element, false);
    }
    private boolean contains(Tile element, boolean isHere)
    {
        if (isLeaf() && !node.equals(element))
        {
            return false;
        }
        else if (node.equals(element))
        {
            return true;
        }
        else if (!getSonTrees().isEmpty())
        {
            for(SearchTree son : sonTrees)
            {
                isHere = isHere||son.contains(element, isHere);
            }
            return isHere;
        }
        return false;
    }
    public List<Tile> getAllNodes()
    {
        List<Tile> nodes = new ArrayList<>();
        return getAllNodes(nodes);
    }
    private List<Tile> getAllNodes(List<Tile> nodes)
    {
        if (nodes == null)
        {
            nodes = new ArrayList<>();
        }
        if (isLeaf())
        {
            nodes.add(node);
            return nodes;
        }
        else
        {
            nodes.add(node);
            getAllNodes(nodes);
            return nodes;
        }
    }
    public Tile hasCommunNode(SearchTree treeToCompare)
    {
        List<Tile> nodes = getAllNodes(null);
        List<Tile> nodesToCompare = treeToCompare.getAllNodes(null);
        for (Tile node : nodes)
        {
            for (Tile nodeToCompare : nodesToCompare)
            {
                if (node.equals(nodeToCompare))
                {
                    return node;
                }
            }
        }
        return null;
    }
    public List<Tile>getWayTo(Tile element)
    {
        List<Tile> way = new ArrayList<>();
        return getWayTo(element, way, false);
    }

    private List<Tile>getWayTo(Tile element, List<Tile> way, boolean wayIsFind)
    {
        if (way == null)
        {
            way = new ArrayList<>();
        }
        if(wayIsFind || node.equals(element))
        {
            wayIsFind = true;
            way.add(node);
            return way;
        }
        else if (!isLeaf())
        {
            return getWayTo(element, way, wayIsFind );
        }
        else
        {
            return way;
        }
    }
}
