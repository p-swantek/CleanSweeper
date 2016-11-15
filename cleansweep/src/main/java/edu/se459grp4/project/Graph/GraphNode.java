
package edu.se459grp4.project.Graph;

import edu.se459grp4.project.simulator.types.TileStatus;

public class GraphNode {
    
        private String msNodeName;
        private Double mdbWeigth;
        private NodeStatus meStatus;  //the visiting status of this node 
        
        public GraphNode(String nsName,double ndbWeigth,NodeStatus neValue)
        {
            msNodeName = nsName;
            mdbWeigth = ndbWeigth;
            meStatus = neValue;
        }
        public NodeStatus NodeStatus() {return meStatus;}
        public void SetNodeStatus(NodeStatus neVal) { meStatus = neVal;}  
       
        public Double Weight() {return mdbWeigth;}
        public void SetWeight(Double ndbVal) { mdbWeigth = ndbVal;}  
        
        public String NodeName() {return msNodeName;}
        public void SetNodeName(String nName) { msNodeName = nName;}  
        
}
