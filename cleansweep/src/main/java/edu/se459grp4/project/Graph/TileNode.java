package edu.se459grp4.project.Graph;

import edu.se459grp4.project.simulator.types.TileStatus;

/**
 * Representation of a node that is contained in the graph of all the floor tiles. Node
 * will represent a floor tile from the floor plan
 * 
 * @author Group 4
 * @version 1.8
 */
public class TileNode{

    private int mx;
    private int my;
    private TileStatus meTileStatus;

    private NodeStatus meStatus;  //the visiting status of this node 

    
    /**
     * Constructs a new node with the given location, type, and status
     * 
     * @param x the x coordinate of the floor space represented by this node
     * @param y the y coordinate of the floor space represented by this node
     * @param nStatus the type of floor that this node is representing
     * @param neValue the status of the floor represented by this node
     */
    public TileNode(int x, int y, TileStatus nStatus, NodeStatus neValue) {
        mx = x;
        my = y;
        meTileStatus = nStatus;
        meStatus = neValue;
    }

    /**
     * Gets the x coordinate of the floor tile this node represents
     * 
     * @return the x coordinate
     */
    public int GetX() {
        return mx;
    }

    /**
     * Gets the y coordinate of the floor tile this node represents
     * 
     * @return the y coordinate
     */
    public int GetY() {
        return my;
    }

    /**
     * Gets the status of the floor represented by this node
     * 
     * @return the NodeStatus of this node
     * @see NodeStatus
     */
    public NodeStatus NodeStatus() {
        return meStatus;
    }

    /**
     * Sets the status of this node
     * 
     * @param neVal the status that this node should have
     */
    public void SetNodeStatus(NodeStatus neVal) {
        meStatus = neVal;
    }

    /**
     * Gets the type of floor that this node represents
     * 
     * @return the TileStatus
     * @see TileStatus
     */
    public TileStatus TileStatus() {
        return meTileStatus;
    }

    /**
     * Sets the type of floor that this node represents
     * 
     * @param neVal the TileStatus this node should represent
     */
    public void SetTileStatus(TileStatus neVal) {
        meTileStatus = neVal;
    }

    @Override
    public String toString() {
        return GenerateKeyString(mx, my);
    }

    /**
     * Generates a string for this node that can be used as a key in a map containing
     * this node
     * 
     * @param x the x coordinate of this node
     * @param y the y coordinate of this node
     * @return the string representation of this node consisting of the x,y coordinates
     */
    public static String GenerateKeyString(int x, int y) {
        return "X" + x + "Y" + y;
    }
}
