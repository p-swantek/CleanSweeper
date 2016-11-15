
package edu.se459grp4.project.Graph;

import edu.se459grp4.project.simulator.types.TileStatus;
import java.util.*;


public class TilesGraph {

    private Map<String, TileNode> mNodeMap = new HashMap<>();
    //the whole graph, string is the incoming node name, and the associated hasmap with this node
    private Map<String, HashMap<String, Double>> mGraphMap = new HashMap<>();

    public boolean IsVisited(int x, int y) {
        TileNode lNode = mNodeMap.get(TileNode.GenerateKeyString(x, y));
        if (lNode != null && lNode.NodeStatus() == NodeStatus.eNodeVisited) {
            return true;
        }
        return false;
    }

    public TileNode GetTileNode(String nsKey) {
        return mNodeMap.get(nsKey);
    }

    public List<TileNode> GetChargeStationNode() {
        List<TileNode> lListNode = new ArrayList<TileNode>();
        for (Map.Entry<String, TileNode> entry : mNodeMap.entrySet()) {
            if (entry.getValue().TileStatus() == TileStatus.CHARGING_STATION) {
                lListNode.add(entry.getValue());
            }
        }
        return lListNode;
    }

    public List<TileNode> GetUnvisitedNode() {
        List<TileNode> lListNode = new ArrayList<TileNode>();
        for (Map.Entry<String, TileNode> entry : mNodeMap.entrySet()) {
            if (entry.getValue().NodeStatus() == NodeStatus.eNodeNoVisited) {
                lListNode.add(entry.getValue());
            }
        }
        return lListNode;
    }

    public boolean Visit(int x, int y, TileStatus nTileStatus) {
        TileNode lNode = mNodeMap.get(TileNode.GenerateKeyString(x, y));
        if (lNode == null) {
            lNode = new TileNode(x, y, nTileStatus, NodeStatus.eNodeVisited);
            mNodeMap.put(lNode.toString(), lNode);

            HashMap<String, Double> lSubmap = new HashMap<String, Double>();
            mGraphMap.put(lNode.toString(), lSubmap);
        } else {
            lNode.SetTileStatus(nTileStatus);
            lNode.SetNodeStatus(NodeStatus.eNodeVisited);
        }
        //Update Weight

        HashMap<String, Double> lSubmap = mGraphMap.get(lNode.toString());

        for (Map.Entry<String, Double> entry : lSubmap.entrySet()) {
            TileNode lDestNode = mNodeMap.get(entry.getKey());

            double ldbWeight = TileStatus.Weight(lNode.TileStatus()) / 2
                    + TileStatus.Weight(lDestNode.TileStatus()) / 2;
            lSubmap.put(entry.getKey(), ldbWeight);

            //Modify the reverse edge
            mGraphMap.get(lDestNode.toString()).put(entry.getKey(), ldbWeight);
        }

        return true;
    }
    //Get the Shortest Path function
    //return : >0 && < Double.MAX_VALUE means We get a shortest way answer
    //         Double.MAX_VALUE means we can not find a way

    public double GetShortestPath(int nFromX,
            int nFromY,
            int nDestX,
            int nDestY, //Destination Node
            List<String> nArrayPath
    ) {
        if (nArrayPath == null) {
            return Double.MAX_VALUE;
        }

        //nsInputNode = nsInputNode.toUpperCase();
        // = nsDestinationNode.toUpperCase();
        if (!mGraphMap.containsKey(TileNode.GenerateKeyString(nFromX, nFromY))) {
            return Double.MAX_VALUE;
        }
        if (!mGraphMap.containsKey(TileNode.GenerateKeyString(nDestX, nDestY))) {
            return Double.MAX_VALUE;
        }

        if (nFromX == nDestX && nFromY == nDestY) {
            return 0.0;
        }
        //We are going to use Dijkstra's Algorithm to find the shortest path

        //Construct the line from the GraphMap
        HashMap<String, GraphNode> lRecRow = new HashMap<String, GraphNode>();
        {

            Set set = mGraphMap.entrySet();
            Iterator lIte = set.iterator();
            while (lIte.hasNext()) {
                Map.Entry me = (Map.Entry) lIte.next();
                String lsKey = me.getKey().toString();
                if (lsKey != TileNode.GenerateKeyString(nFromX, nFromY)) {
                    lRecRow.put(lsKey, new GraphNode(TileNode.GenerateKeyString(nFromX, nFromY), Double.MAX_VALUE, NodeStatus.eNodeNoVisited));
                }
            }
        }

        //We use a Queue to store those node waiting for handling
        LinkedList<GraphNode> lQueue = new LinkedList<GraphNode>();
        lQueue.add(new GraphNode(TileNode.GenerateKeyString(nFromX, nFromY), 0.00, NodeStatus.eNodeNoVisited));

        while (!lQueue.isEmpty()) {
            GraphNode lTempNode = lQueue.removeFirst();
            String lsFromNode = lTempNode.NodeName();
            Double ldbShortestDistande = lTempNode.Weight();
            //Iterate the output edge from this node
            //check the minimum weight
            {
                HashMap<String, Double> lRow = mGraphMap.get(lsFromNode);
                Set set = lRow.entrySet();
                Iterator lIte = set.iterator();
                while (lIte.hasNext()) {
                    Map.Entry me = (Map.Entry) lIte.next();
                    String lsToNodeKey = me.getKey().toString();
                    Double lsToWeight = (Double) me.getValue();
                    Double ldbTempWeight = ldbShortestDistande + lsToWeight;

                    //Do not deal the node with the same name of original source node
                    if (lsToNodeKey != TileNode.GenerateKeyString(nFromX, nFromY)) {
                        if (ldbTempWeight < lRecRow.get(lsToNodeKey).Weight()) {
                            lRecRow.get(lsToNodeKey).SetWeight(ldbTempWeight);
                            lRecRow.get(lsToNodeKey).SetNodeName(lsFromNode);
                        }
                    }
                }
            }

            //Find the mininum and Nonvisited Node to Enqueue
            {
                Double ldbMinimunWeight = Double.MAX_VALUE;
                String lsMinWeightNodeName = "";
                Set set = lRecRow.entrySet();
                Iterator lIte = set.iterator();
                while (lIte.hasNext()) {
                    Map.Entry me = (Map.Entry) lIte.next();
                    String lsToNodeKey = me.getKey().toString();
                    GraphNode lTemp = (GraphNode) me.getValue();
                    if (lTemp.NodeStatus() == NodeStatus.eNodeNoVisited && lTemp.Weight() < ldbMinimunWeight) {
                        ldbMinimunWeight = lTemp.Weight();
                        lsMinWeightNodeName = lsToNodeKey;
                    }
                }

                if (!lsMinWeightNodeName.isEmpty()) {
                    lRecRow.get(lsMinWeightNodeName).SetNodeStatus(NodeStatus.eNodeVisited);
                    lQueue.add(new GraphNode(lsMinWeightNodeName, ldbMinimunWeight, NodeStatus.eNodeInVisitingQueue));
                }
            }

        }

        //Generate the path
        nArrayPath.add(TileNode.GenerateKeyString(nDestX, nDestY));
        GraphNode lFinalNode = lRecRow.get(TileNode.GenerateKeyString(nDestX, nDestY));
        Double ldbRetWeight = lFinalNode.Weight();
        String lsTempName = lFinalNode.NodeName();
        do {
            nArrayPath.add(lsTempName);
            lFinalNode = lRecRow.get(lsTempName);
            lsTempName = lFinalNode.NodeName();
        } while (Double.compare(ldbRetWeight, Double.MAX_VALUE) != 0 &&
                lsTempName.compareTo(TileNode.GenerateKeyString(nFromX, nFromY)) != 0 &&
                lsTempName.compareTo(TileNode.GenerateKeyString(nDestX, nDestY)) != 0 );
        
     //  nArrayPath.add(TileNode.GenerateKeyString(nFromX, nFromY));
        //Reverse the array
        Collections.reverse(nArrayPath);

        return ldbRetWeight;
    }

    public Boolean DeleteEdge(int nFromX, int nFromY, int nDestX, int nDestY) {

        if (nFromX == nDestX && nFromY == nDestY) {
            return false;
        }

        if (mGraphMap.containsKey(TileNode.GenerateKeyString(nFromX, nFromY))) {
            //If existed then get the submap
            HashMap<String, Double> lSubmap = mGraphMap.get(TileNode.GenerateKeyString(nFromX, nFromY));
            lSubmap.remove(TileNode.GenerateKeyString(nDestX, nDestY));

        }
        if (mGraphMap.containsKey(TileNode.GenerateKeyString(nDestX, nDestY))) {
            //If existed then get the submap
            HashMap<String, Double> lSubmap = mGraphMap.get(TileNode.GenerateKeyString(nDestX, nDestY));
            lSubmap.remove(TileNode.GenerateKeyString(nFromX, nFromY));

        }
        return true;
    }

    //This is the main way to construce a graph
    //We need to add edges one by one
    //Note: this is an undirect graph, so we need to add a converse edge simultaneously
    public Boolean AddEdge(int nFromX, int nFromY,
            int nDestX, int nDestY, TileStatus nTileStatus) {

        if (nFromX == nDestX && nFromY == nDestY) {
            return false;
        }
        TileNode lSourceNode = mNodeMap.get(TileNode.GenerateKeyString(nFromX, nFromY));
        if (lSourceNode == null) {
            lSourceNode = new TileNode(nFromX, nFromY, nTileStatus, NodeStatus.eNodeNoVisited);
            mNodeMap.put(lSourceNode.toString(), lSourceNode);
        }

        Boolean lbRet = true;

        TileNode lDestNode = mNodeMap.get(TileNode.GenerateKeyString(nDestX, nDestY));
        if (lDestNode == null) {
            lDestNode = new TileNode(nDestX, nDestY, nTileStatus, NodeStatus.eNodeNoVisited);
            mNodeMap.put(lDestNode.toString(), lDestNode);
        } 

        double ldbWeight = TileStatus.Weight(lSourceNode.TileStatus()) / 2
                + TileStatus.Weight(lDestNode.TileStatus()) / 2;

        //Check the node of input if it exists in the GraphMap  
        if (mGraphMap.containsKey(lSourceNode.toString())) {
            //If existed then get the submap
            HashMap<String, Double> lSubmap = mGraphMap.get(lSourceNode.toString());
            lSubmap.put(lDestNode.toString(), ldbWeight);

        } else {
            //this is a new node,we should create all
            HashMap<String, Double> lSubmap = new HashMap<String, Double>();
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
            HashMap<String, Double> lSubmap = new HashMap<String, Double>();
            lSubmap.put(lSourceNode.toString(), ldbWeight);
            mGraphMap.put(lDestNode.toString(), lSubmap);
        }
        return lbRet;
    }
    
    public Double GetWeight(int nFromX,int nFromY,int nDestX,int nDestY)
    {
        Double ldbWeight = 0.0;
         if (mGraphMap.containsKey(TileNode.GenerateKeyString(nFromX, nFromY))) {
            //If existed then get the submap
            HashMap<String, Double> lSubmap = mGraphMap.get(TileNode.GenerateKeyString(nFromX, nFromY));
            ldbWeight = lSubmap.get(TileNode.GenerateKeyString(nDestX, nDestY));

        } 
        return ldbWeight;
    }

}
