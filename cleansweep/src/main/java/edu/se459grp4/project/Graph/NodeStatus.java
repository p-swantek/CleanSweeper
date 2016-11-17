
package edu.se459grp4.project.Graph;


/**
 * Enumeration type that designates the status of a node in the graph representing the floor
 * 
 * @author Group 4
 * @version 1.8
 *
 */
public enum NodeStatus {
    eNodeVisited, //Indicate this node has been visited
    eNodeNoVisited, //Reveal this node has not been visited
    eNodeInVisitingQueue //show this node is waiting for being visited.

};
