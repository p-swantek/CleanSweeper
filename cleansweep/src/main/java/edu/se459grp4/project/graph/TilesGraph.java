
package edu.se459grp4.project.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.se459grp4.project.simulator.types.SurfaceType;

/**
 * Graph representation of the floor that is being cleaned by the clean sweep.
 * This graph will allow the clean sweep to keep track of portions of the floor
 * that have been visited as well as determine the shortest path (determined by
 * power requirements) between different sections of the floor
 * 
 * @author Group 4
 * @version 1.8
 */
public class TilesGraph {

    private final Map<String, TileNode> mNodeMap;
    private final Map<String, HashMap<String, Double>> mGraphMap; //the whole graph, string is the incoming node name, and the associated hasmap with this node

    /**
     * Constructs a new Tile graph
     * 
     */
    public TilesGraph() {
        mNodeMap = new HashMap<>();
        mGraphMap = new HashMap<>();
    }

    /**
     * Tells whether a certain section of floor has been previously visited
     * 
     * @param x the x coordinate of the location to check
     * @param y the y coordinate of the location to check
     * @return true if the section has been visited, false otherwise
     */
    public boolean isVisited(int x, int y) {
        TileNode lNode = mNodeMap.get(TileNode.generateKeyString(x, y));
        if (lNode != null && lNode.getNodeStatus() == NodeStatus.NODE_VISITED) {
            return true;
        }
        return false;
    }

    /**
     * Gets the node from the graph that is associated with the given string key
     * 
     * @param nsKey the string designating the node to retrieve
     * @return the TileNode associated with the given key
     */
    public TileNode getTileNode(String nsKey) {
        return mNodeMap.get(nsKey);
    }

    /**
     * Gets a list of nodes from the graph that currently contain charging
     * stations
     * 
     * @return a list of the charging stations from the graph
     */
    public List<TileNode> getChargeStationNode() {
        List<TileNode> lListNode = new ArrayList<>();
        for (Map.Entry<String, TileNode> entry : mNodeMap.entrySet()) {
            if (entry.getValue().getTileStatus() == SurfaceType.CHARGING_STATION) {
                lListNode.add(entry.getValue());
            }
        }
        return lListNode;
    }

    /**
     * Gets a list of nodes from the graph that have not yet been visited
     * 
     * @return a list of the unvisited nodes
     */
    public List<TileNode> getUnvisitedNodes() {
        List<TileNode> lListNode = new ArrayList<>();
        for (Map.Entry<String, TileNode> entry : mNodeMap.entrySet()) {
            if (entry.getValue().getNodeStatus() == NodeStatus.NODE_NOT_VISITED) {
                lListNode.add(entry.getValue());
            }
        }
        return lListNode;
    }

    /**
     * When the clean sweep visits a section of the floor, updates the node in
     * the graph which represents that floor section to signify that it has been
     * visited by the clean sweep
     * 
     * @param x the x coordinate of the visited section
     * @param y the y coordinate of the visited section
     * @param nTileStatus the new status of the visited tile
     * 
     */
    public void visit(int x, int y, SurfaceType nTileStatus) {
        TileNode lNode = mNodeMap.get(TileNode.generateKeyString(x, y));
        if (lNode == null) {
            lNode = new TileNode(x, y, nTileStatus, NodeStatus.NODE_VISITED);
            mNodeMap.put(lNode.toString(), lNode);

            HashMap<String, Double> lSubmap = new HashMap<>();
            mGraphMap.put(lNode.toString(), lSubmap);
        } else {
            lNode.setTileStatus(nTileStatus);
            lNode.setNodeStatus(NodeStatus.NODE_VISITED);
        }
        //Update Weight

        HashMap<String, Double> lSubmap = mGraphMap.get(lNode.toString());

        for (Map.Entry<String, Double> entry : lSubmap.entrySet()) {
            TileNode lDestNode = mNodeMap.get(entry.getKey());
            double ldbWeight = SurfaceType.weight(lNode.getTileStatus()) / 2 + SurfaceType.weight(lDestNode.getTileStatus()) / 2;
            lSubmap.put(entry.getKey(), ldbWeight);

            //Modify the reverse edge
            mGraphMap.get(lDestNode.toString()).put(entry.getKey(), ldbWeight);
        }
    }
    //Get the Shortest Path function
    //return : >0 && < Double.MAX_VALUE means We get a shortest way answer
    //         Double.MAX_VALUE means we can not find a way

    /**
     * Determines the the shortest path between two nodes on the graph
     * 
     * @param nFromX the starting x coordinate
     * @param nFromY the starting y coordinate
     * @param nDestX the desired destination x coordinate
     * @param nDestY the desired destination y coordinate
     * @param nArrayPath the current path that is being traveled, shortest path
     *            is added to this list
     * @return the power cost of the shortest path between the 2 points
     */
    public double getShortestPath(int nFromX, int nFromY, int nDestX, int nDestY, List<String> nArrayPath) {
        if (nArrayPath == null) {
            return Double.MAX_VALUE;
        }

        if (!mGraphMap.containsKey(TileNode.generateKeyString(nFromX, nFromY))) {
            return Double.MAX_VALUE;
        }

        if (!mGraphMap.containsKey(TileNode.generateKeyString(nDestX, nDestY))) {
            return Double.MAX_VALUE;
        }

        if (nFromX == nDestX && nFromY == nDestY) {
            return 0.0;
        }
        //We are going to use Dijkstra's Algorithm to find the shortest path

        //Construct the line from the GraphMap
        Map<String, GraphNode> lRecRow = new HashMap<>();
        setUpMap(mGraphMap, lRecRow, nFromX, nFromY);

        //We use a Queue to store those node waiting for handling
        LinkedList<GraphNode> lQueue = new LinkedList<>();
        lQueue.add(new GraphNode(TileNode.generateKeyString(nFromX, nFromY), 0.00, NodeStatus.NODE_NOT_VISITED));

        while (!lQueue.isEmpty()) {
            GraphNode lTempNode = lQueue.removeFirst();
            String lsFromNode = lTempNode.getNodeName();
            double ldbShortestDistance = lTempNode.getWeight();
            //Iterate the output edge from this node
            //check the minimum weight

            updateWeights(mGraphMap, lRecRow, lsFromNode, nFromX, nFromY, ldbShortestDistance);

            //Find the mininum and Nonvisited Node to Enqueue
            determineMinimumWeights(lRecRow, lQueue);
        }

        //Generate the path
        nArrayPath.add(TileNode.generateKeyString(nDestX, nDestY));
        GraphNode lFinalNode = lRecRow.get(TileNode.generateKeyString(nDestX, nDestY));
        double ldbRetWeight = lFinalNode.getWeight();
        String lsTempName = lFinalNode.getNodeName();
        do {
            nArrayPath.add(lsTempName);
            lFinalNode = lRecRow.get(lsTempName);
            lsTempName = lFinalNode.getNodeName();
        } while (ldbRetWeight != Double.MAX_VALUE && lsTempName.compareTo(TileNode.generateKeyString(nFromX, nFromY)) != 0 && lsTempName.compareTo(TileNode.generateKeyString(nDestX, nDestY)) != 0);

        Collections.reverse(nArrayPath);

        return ldbRetWeight;
    }

    private void setUpMap(Map<String, HashMap<String, Double>> graphMap, Map<String, GraphNode> mapToBuild, int nFromX, int nFromY) {
        Set<Entry<String, HashMap<String, Double>>> set = graphMap.entrySet();
        for (Entry<String, HashMap<String, Double>> entry : set) {
            String lsKey = entry.getKey();
            if (lsKey != TileNode.generateKeyString(nFromX, nFromY)) {
                mapToBuild.put(lsKey, new GraphNode(TileNode.generateKeyString(nFromX, nFromY), Double.MAX_VALUE, NodeStatus.NODE_NOT_VISITED));
            }
        }

    }

    private void updateWeights(Map<String, HashMap<String, Double>> graphMap, Map<String, GraphNode> mapToBuild, String fromNode, int fromX, int fromY, double ldbShortestDistance) {
        Set<Entry<String, Double>> lRow = graphMap.get(fromNode).entrySet();
        for (Entry<String, Double> entry : lRow) {
            String lsToNodeKey = entry.getKey();
            double lsToWeight = entry.getValue();
            double ldbTempWeight = ldbShortestDistance + lsToWeight;

            //Do not deal the node with the same name of original source node
            if ((lsToNodeKey != TileNode.generateKeyString(fromX, fromY)) && (ldbTempWeight < mapToBuild.get(lsToNodeKey).getWeight())) {
                mapToBuild.get(lsToNodeKey).setWeight(ldbTempWeight);
                mapToBuild.get(lsToNodeKey).setNodeName(fromNode);
            }
        }
    }

    private void determineMinimumWeights(Map<String, GraphNode> mapBeingBuilt, LinkedList<GraphNode> queue) {
        double ldbMinimunWeight = Double.MAX_VALUE;
        String lsMinWeightNodeName = "";
        Set<Entry<String, GraphNode>> set = mapBeingBuilt.entrySet();
        for (Entry<String, GraphNode> entry : set) {
            String lsToNodeKey = entry.getKey();
            GraphNode lTemp = entry.getValue();
            if (lTemp.nodeStatus() == NodeStatus.NODE_NOT_VISITED && lTemp.getWeight() < ldbMinimunWeight) {
                ldbMinimunWeight = lTemp.getWeight();
                lsMinWeightNodeName = lsToNodeKey;
            }
        }

        if (!lsMinWeightNodeName.isEmpty()) {
            mapBeingBuilt.get(lsMinWeightNodeName).setNodeStatus(NodeStatus.NODE_VISITED);
            queue.add(new GraphNode(lsMinWeightNodeName, ldbMinimunWeight, NodeStatus.NODE_IN_VISITING_QUEUE));
        }
    }

    /**
     * Deletes an edge from the tile graph, can happen when a door was closed
     * and a path can no longer be traveled by the sweeper
     * 
     * @param nFromX the x coordinate of the first node
     * @param nFromY the y coordinate of the first node
     * @param nDestX the x coordinate of the second node
     * @param nDestY the y coordinate of the second node
     * @return true if the edge was successfully deleted, false otherwise
     */
    public boolean deleteEdge(int nFromX, int nFromY, int nDestX, int nDestY) {

        if (nFromX == nDestX && nFromY == nDestY) {
            return false;
        }

        if (mGraphMap.containsKey(TileNode.generateKeyString(nFromX, nFromY))) {
            //If existed then get the submap
            HashMap<String, Double> lSubmap = mGraphMap.get(TileNode.generateKeyString(nFromX, nFromY));
            lSubmap.remove(TileNode.generateKeyString(nDestX, nDestY));
        }

        if (mGraphMap.containsKey(TileNode.generateKeyString(nDestX, nDestY))) {
            //If existed then get the submap
            HashMap<String, Double> lSubmap = mGraphMap.get(TileNode.generateKeyString(nDestX, nDestY));
            lSubmap.remove(TileNode.generateKeyString(nFromX, nFromY));
        }
        return true;
    }

    //This is the main way to construce a graph
    //We need to add edges one by one
    //Note: this is an undirect graph, so we need to add a converse edge simultaneously

    /**
     * Creates an edge between 2 nodes in the tile graph.
     * 
     * @param nFromX the x coordinate of the first node
     * @param nFromY the y coordinate of the first node
     * @param nDestX the x coordinate of the second node
     * @param nDestY the y coordinate of the second node
     * @param nTileStatus the type of tile this is
     * @return true if the edge was successfully added, false otherwise
     */
    public boolean addEdge(int nFromX, int nFromY, int nDestX, int nDestY, SurfaceType nTileStatus) {

        if (nFromX == nDestX && nFromY == nDestY) {
            return false;
        }
        TileNode lSourceNode = mNodeMap.get(TileNode.generateKeyString(nFromX, nFromY));
        if (lSourceNode == null) {
            lSourceNode = new TileNode(nFromX, nFromY, nTileStatus, NodeStatus.NODE_NOT_VISITED);
            mNodeMap.put(lSourceNode.toString(), lSourceNode);
        }

        boolean lbRet = true;

        TileNode lDestNode = mNodeMap.get(TileNode.generateKeyString(nDestX, nDestY));
        if (lDestNode == null) {
            lDestNode = new TileNode(nDestX, nDestY, nTileStatus, NodeStatus.NODE_NOT_VISITED);
            mNodeMap.put(lDestNode.toString(), lDestNode);
        }

        double ldbWeight = SurfaceType.weight(lSourceNode.getTileStatus()) / 2 + SurfaceType.weight(lDestNode.getTileStatus()) / 2;

        //Check the node of input if it exists in the GraphMap  
        if (mGraphMap.containsKey(lSourceNode.toString())) {
            //If existed then get the submap
            HashMap<String, Double> lSubmap = mGraphMap.get(lSourceNode.toString());
            lSubmap.put(lDestNode.toString(), ldbWeight);

        } else {
            //this is a new node,we should create all
            HashMap<String, Double> lSubmap = new HashMap<>();
            lSubmap.put(lDestNode.toString(), ldbWeight);
            mGraphMap.put(lSourceNode.toString(), lSubmap);
        }

        //Check the dest node of input if it exists in the GraphMap  
        if (mGraphMap.containsKey(lDestNode.toString())) {
            //If existed then get the submap
            HashMap<String, Double> lSubmap = mGraphMap.get(lDestNode.toString());
            lSubmap.put(lSourceNode.toString(), ldbWeight);

        } else {
            //this is a new node,we should create all
            HashMap<String, Double> lSubmap = new HashMap<>();
            lSubmap.put(lSourceNode.toString(), ldbWeight);
            mGraphMap.put(lDestNode.toString(), lSubmap);
        }
        return lbRet;
    }

    /**
     * Gets the weight of the edge between 2 nodes in the graph
     * 
     * @param nFromX the x coordinate of one node
     * @param nFromY the y coordinate of one node
     * @param nDestX the x coordinate of the other node
     * @param nDestY the y coordinate of the other node
     * @return the weight value between these two nodes
     */
    public double getWeight(int nFromX, int nFromY, int nDestX, int nDestY) {
        double ldbWeight = 0.0;
        if (mGraphMap.containsKey(TileNode.generateKeyString(nFromX, nFromY))) {
            //If existed then get the submap
            HashMap<String, Double> lSubmap = mGraphMap.get(TileNode.generateKeyString(nFromX, nFromY));

            Double d = lSubmap.get(TileNode.generateKeyString(nDestX, nDestY));
            if (d != null) {
                ldbWeight = d.doubleValue();
            }

        }

        return ldbWeight;
    }

}
