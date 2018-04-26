
package edu.se459grp4.project.graph;


/**
 * Enumeration type that designates the status of a node in the graph representing the floor
 * 
 * @author Group 4
 * @version 1.8
 *
 */
public enum NodeStatus {
    NODE_VISITED, //Indicate this node has been visited
    NODE_NOT_VISITED, //Reveal this node has not been visited
    NODE_IN_VISITING_QUEUE //show this node is waiting for being visited.

};
