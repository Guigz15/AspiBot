package com.uqac.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static java.lang.Thread.sleep;

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
        if (sonTrees == null || sonTrees.size()==0)
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
        return leafs;
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
    public List<Tile> getAllNodes() {
        List<Tile> nodes = new ArrayList<>();
        return getAllNodes(nodes);
    }
    private List<Tile> getAllNodes(List<Tile> nodes)  {
        if (isLeaf())
        {
            nodes.add(node);
            return nodes;
        }
        else
        {
            nodes.add(node);
            for (SearchTree son : sonTrees)
            {
                son.getAllNodes(nodes);
            }
            return nodes;
        }
    }
    public Tile hasCommunNode(SearchTree treeToCompare)  {
        List<Tile> nodes = getAllNodes();
        List<Tile> nodesToCompare = treeToCompare.getAllNodes();
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
    public List<Tile>getWayTo(Tile element)   {
        System.out.print("element : ");
        element.display();
        System.out.println();
        List<Tile> way = new ArrayList<>();
        List<Boolean> wayIsFind = new ArrayList<>();
        wayIsFind.add(false);
        return getWayTo(element, way, wayIsFind);
    }

    private List<Tile> getWayTo(Tile element, List<Tile> way, List<Boolean> wayIsFind)  {
        if(node.equals(element))
        {
            System.out.print("ici");
            node.display();
            way.add(node);
            wayIsFind.set(0, true);
            return way;
        }
        if (contains(element))
        {
            System.out.print("là");
            node.display();
            for (SearchTree sonTree : sonTrees)
            {
                sonTree.node.display();
            }
            way.add(node);
            for (SearchTree sonTree : sonTrees)
            {
                if (!wayIsFind.get(0) && sonTree.contains(element))
                {
                    System.out.println(wayIsFind.get(0));
                    sonTree.getWayTo(element, way, wayIsFind);
                }
            }
        }
        return way;
        /*sleep(500);
        System.out.print("way :");
        if(wayIsFind.get(0) || node.equals(element))
        {
            wayIsFind.remove(0);
            wayIsFind.add(true);
            way.add(node);
            way.stream().forEach(tile-> tile.display());
            System.out.println();
            return way;
        }
        else if (!isLeaf())
        {
            way.stream().forEach(tile-> tile.display());
            System.out.println();
            for (SearchTree sonTree : sonTrees)
            {
                LinkedHashSet<Tile> truc = new LinkedHashSet<>();
                truc.addAll(sonTree.getWayTo(element,way,wayIsFind));
                if (wayIsFind.get(0))
                {
                    way.add(node);

                };
                return getWayTo(element, way, wayIsFind);
            }
        }
        return way;

         */
    }
    public void display() throws InterruptedException
    {
        List<Tile>tiles = getAllNodes();
        tiles.stream().forEach(tile-> tile.display());
        System.out.println();
    }
}
