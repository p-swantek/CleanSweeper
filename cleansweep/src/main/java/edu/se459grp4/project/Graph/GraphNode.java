
package edu.se459grp4.project.Graph;


/**
 * Data type that represents a node in the representation of the floor which is being cleaned by 
 * the clean sweep. Nodes in the graph have a weight associated with them to represent the power
 * cost the sweep must expend to move around the floor
 * 
 * @author Group 4
 * @version 1.8
 *
 */
public class GraphNode {
    
        private String msNodeName;
        private Double mdbWeigth;
        private NodeStatus meStatus;  //the visiting status of this node 
        
        /**
         * Construct a node in the graph with a given name, weight, and node status
         * 
         * @param nsName the name of this node
         * @param ndbWeigth the weight associated with this node
         * @param neValue the status of this node
         * @see NodeStatus
         */
        public GraphNode(String nsName,double ndbWeigth,NodeStatus neValue)
        {
            msNodeName = nsName;
            mdbWeigth = ndbWeigth;
            meStatus = neValue;
        }
        
        /**
         * Gets the status of this node
         * 
         * @return the NodeStatus associated with this node
         */
        public NodeStatus NodeStatus() {return meStatus;}
        
        /**
         * Sets the status of this node
         * 
         * @param neVal the status that this node should have
         */
        public void SetNodeStatus(NodeStatus neVal) { meStatus = neVal;}  
       
        /**
         * Gets the weight associated with this node
         * 
         * @return the node's weight
         */
        public Double Weight() {return mdbWeigth;}
        
        /**
         * Sets the weight for this node
         * 
         * @param ndbVal the weight that this node should have
         */
        public void SetWeight(Double ndbVal) { mdbWeigth = ndbVal;}  
        
        /**
         * Gets the name of this node
         * 
         * @return the name of the node
         */
        public String NodeName() {return msNodeName;}
        
        /**
         * Sets the name of this node
         * 
         * @param nName the name the node should have
         */
        public void SetNodeName(String nName) { msNodeName = nName;}  
        
}
