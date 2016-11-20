package edu.se459grp4.project.cleansweep;

import edu.se459grp4.project.graph.TileNode;
import edu.se459grp4.project.graph.TilesGraph;
import edu.se459grp4.project.simulator.types.Direction;
import edu.se459grp4.project.simulator.types.TileStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * Main navigation logic for the clean sweep, will continue exploring a floor and cleaning any tiles that it visits.
 * If the vacuum fills up or the power of the sweep gets too low, it will attempt to navigate back to a nearest charging
 * stating to either empty the vacuum or recharge its power.  The clean sweep continues cleaning until all the spots
 * of the floor have been explored and/or cleaned
 * 
 * @author Group 4
 * @version 1.8
 *
 */
public class Navigator implements Runnable {
	
    private enum MissionTypeToGoToChargeStation{
        MissionUnkown,
        MissionWork,
        MissionCompleted,
        MissionCharge,
        MissionCleanVacuume,
        MissionReturnToWork,
        MissionReturnToChargeWithUnexpectedHappened
    };
        
        
    private class Coordinate {

        public int currX;
        public int currY;

        public Coordinate(int nx, int ny) {
           setLocation(nx,ny);
        }
        public void setLocation(int nx,int ny) {
            currX = nx;
            currY = ny;
        }
    }

    private CleanSweep cleanSweep;
    private TilesGraph tileGraph = new TilesGraph();
    private Queue<TileNode> mMissionQueue = new LinkedList<>();
    private MissionTypeToGoToChargeStation currentMission = MissionTypeToGoToChargeStation.MissionUnkown;
    private Coordinate returnNodeCoordinate = new Coordinate(0,0);
    private List<String> returnPath = new ArrayList<>();
    private static final int SLEEP = 500;
    
    
    /**
     * Create a navigator for the given clean sweep
     * 
     * @param nCleanSweep the clean sweep with which this navigator is associated
     */
    public Navigator(CleanSweep nCleanSweep){
        cleanSweep = nCleanSweep;
    }

    /*
     * Execute the main navigation logic
     * 
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run(){
        //smart control run
        try {
            currentMission = MissionTypeToGoToChargeStation.MissionWork;
            while (!Thread.currentThread().isInterrupted() && cleanSweep != null){
                
                //Detect Around
                List<Direction> lListCanGo = detectPossibleDirectionsFromHere();
                if (lListCanGo.isEmpty()) {
                    break;
                }  
                //detect tilestatus and sweep ,Set Visit
                TileStatus lTileStatus = cleanSweep.senseFloorSurface();
                
                //Visit this node 
                tileGraph.visit(cleanSweep.getX(), cleanSweep.getY(), lTileStatus);

                //Check if there is a next mission, it 
                if (!mMissionQueue.isEmpty()){
                    TileNode lMovetoNode = mMissionQueue.poll();
                    double ldbNeedPower = tileGraph.GetWeight(cleanSweep.getX(), cleanSweep.getY(), lMovetoNode.getX(), lMovetoNode.getY());
                    
                    //if mission is working,then need to check the power is enough to return to station
                    if(MissionTypeToGoToChargeStation.MissionWork == currentMission){
                        //Check power is enough
                        if(shouldReturnToChargingStation(cleanSweep.getX(), cleanSweep.getY(), lMovetoNode.getX(), lMovetoNode.getY(), ldbNeedPower)){
                            goToNearestChargeStation(cleanSweep.getX(), cleanSweep.getY(), MissionTypeToGoToChargeStation.MissionCharge);
                            continue;
                        }
                    }
                   
                    //if no power then suspend, let the host to move the sweep to the chargesation
                    if(cleanSweep.getCurrPower() <= 0.0){
                        break;
                    }
                    
                    //if power is engough or it is a mission going to station then Move 
                    if(!cleanSweep.moveToLoc(lMovetoNode.getX(), lMovetoNode.getY())){
                        //move fail, becausing the door is charge
                        //cancel curruent mission ,go back to charge staion
                        mMissionQueue.clear();
                        goToNearestChargeStation(cleanSweep.getX(),cleanSweep.getY(), MissionTypeToGoToChargeStation.MissionReturnToChargeWithUnexpectedHappened);
                        continue;
                        
                    }
                    
                    //move success
                    cleanSweep.usePowerAmount(ldbNeedPower);
                    
                    
                    
                    //check if arrived the charge station
                    if(currentMission == MissionTypeToGoToChargeStation.MissionCompleted && lMovetoNode.getTileStatus() == TileStatus.CHARGING_STATION){
                        //end up all cycle and thread
                        break;
                    }
                    
                    if(currentMission == MissionTypeToGoToChargeStation.MissionReturnToChargeWithUnexpectedHappened && lMovetoNode.getTileStatus() == TileStatus.CHARGING_STATION){
                        //end up all cycle and thread
                        cleanSweep.emptyVacuum();
                        cleanSweep.rechargePower();
                        currentMission = MissionTypeToGoToChargeStation.MissionWork;
                        break;
                    }
                   
                    else if(currentMission== MissionTypeToGoToChargeStation.MissionCleanVacuume && lMovetoNode.getTileStatus() == TileStatus.CHARGING_STATION){
                        //Clean vacuum and go back to last tile
                        cleanSweep.emptyVacuum();
                        cleanSweep.rechargePower();
                        currentMission = MissionTypeToGoToChargeStation.MissionReturnToWork;
                        //reverse the return path
                        Collections.reverse(returnPath);
                        addPathToQueue(returnPath);
                        //continue;
                    }
                    
                    else if(currentMission== MissionTypeToGoToChargeStation.MissionCharge && lMovetoNode.getTileStatus() == TileStatus.CHARGING_STATION){
                        //Recharge sweep and go back to last tile
                        cleanSweep.emptyVacuum();
                        cleanSweep.rechargePower();
                        currentMission = MissionTypeToGoToChargeStation.MissionReturnToWork;
                        Collections.reverse(returnPath);
                        addPathToQueue(returnPath);
                        //continue;
                    }
                    
                    else if(currentMission == MissionTypeToGoToChargeStation.MissionReturnToWork && 
                    		cleanSweep.getX() == returnNodeCoordinate.currX && cleanSweep.getY() == returnNodeCoordinate.currY){
                        //go back to work
                        currentMission = MissionTypeToGoToChargeStation.MissionWork;
                        returnPath.clear();
                        //continue;
                    }
                    
                    Thread.sleep(SLEEP);
                    continue;
                }

                
                int nDirtVal = cleanSweep.senseDirtAmount();
                while ((lTileStatus == TileStatus.BARE_FLOOR || lTileStatus == TileStatus.LOW_CARPET || lTileStatus == TileStatus.HIGH_CARPET) && nDirtVal > 0){
                    int lnRet = cleanSweep.cleanDirt(10);
                    Thread.sleep(20);
                    if (lnRet == 0){
                        break;
                    }
                }
                
                //if the no enough vacumme space
                if(cleanSweep.getCurrVacuumCapacity() == 0){
                    goToNearestChargeStation(cleanSweep.getX(), cleanSweep.getY(), MissionTypeToGoToChargeStation.MissionCleanVacuume);
                    continue;
                }
                
                //Get all the path it can go
                List<Coordinate> lListAfterMatched = new ArrayList<>();
                for (Direction direction : lListCanGo) {
                    int x = cleanSweep.getX();
                    int y = cleanSweep.getY();
                    //Add Node and path to the graph
                    if (direction == Direction.Left){
                    	x--;
                    }
                    
                    if (direction == Direction.Right){
                    	x++;
                    }
                    
                    if (direction == Direction.Up){
                    	y--;
                    }
                    
                    if (direction == Direction.Down){
                    	y++;
                    }
                    
                    if (!tileGraph.isVisited(x, y)) {
                        lListAfterMatched.add(new Coordinate(x, y));
                    }
                }
                //decide which one should go
                if (lListAfterMatched.isEmpty()) {
                    //check if has unvisited node
                    List<TileNode> lListUnvisitedTileNode = tileGraph.getUnvisitedNodes();
                    if (!lListUnvisitedTileNode.isEmpty()) {
                        goToNeartestUnvisitedNode(cleanSweep.getX(), cleanSweep.getY(),lListUnvisitedTileNode);
                    } else {
                        //Go back chargestation to end this mission
                       goToNearestChargeStation(cleanSweep.getX(), cleanSweep.getY(),MissionTypeToGoToChargeStation.MissionCompleted);
                    }
                } else {
                   // mCleanSweep.MoveOneStep(lListAfterMatched.get(0));
                   Coordinate lMovingNode = lListAfterMatched.get(0);
                   mMissionQueue.add(tileGraph.getTileNode(TileNode.generateKeyString(lMovingNode.currX, lMovingNode.currY)));
                }

            }
        } catch (Exception e) {
        	e.printStackTrace();
            return;
        }
    }

    private double findShortestPathWeight(int x, int y, List<TileNode> listOfNodes, List<String> returnPath){
        double currMinimum = Double.MAX_VALUE;
        for (TileNode tileNode : listOfNodes) {
            List<String> shortestPath = new ArrayList<>();
            double minimum = tileGraph.getShortestPath(x, y, tileNode.getX(), tileNode.getY(), shortestPath);
            if (minimum < currMinimum) {
                currMinimum = minimum;
                returnPath.clear();
                returnPath.addAll(shortestPath);
                // nretPath = nArrayPath;
            }
        }

        return currMinimum;
    }
    
    private double chooseNearestChargeStation(int x,int y,List<String> nRetPath){
        List<TileNode> chargeStations = tileGraph.getChargeStationNode();
        return findShortestPathWeight(cleanSweep.getX(), cleanSweep.getY(), chargeStations, nRetPath);
    }
    
    private void goToNearestChargeStation(int x, int y, MissionTypeToGoToChargeStation neMissionType){
        //go to charge station
       
        List<String> lRetPath = new ArrayList<>();
        double weight = chooseNearestChargeStation(cleanSweep.getX(), cleanSweep.getY(), lRetPath);
        if (weight != Double.MAX_VALUE){
            //check the power and add the mission
            addPathToQueue(lRetPath);
        } 
        //set the returning node
        returnPath.clear();
        returnPath.add(TileNode.generateKeyString(x, y));
        returnPath.addAll(lRetPath);
        returnNodeCoordinate.setLocation(x, y);
        currentMission = neMissionType;
    }
    
    private double goToNeartestUnvisitedNode(int x, int y, List<TileNode> unvisitedNodes){
        //pich a shortest one
        List<String> lRetPath = new ArrayList<>();
        double weight = findShortestPathWeight(cleanSweep.getX(), cleanSweep.getY(), unvisitedNodes, lRetPath);
        if (weight != Double.MAX_VALUE) {
            //check the power and add the mission
            addPathToQueue(lRetPath);
        }
        return weight;
    }
    
    private boolean shouldReturnToChargingStation(int nFromX, int nFromY, int nDestX, int nDestY, double ndbNeedPower){
         double csCurrPower = cleanSweep.getCurrPower();
         //double ldbNeedPower = mTileGraph.GetWeight(nFromX,nFromY,nDestX,nDestY);
         //cacluate the fewest power to the nearest chargestaion 
         List<String> lRetPath = new ArrayList<>();
         
         double weight = chooseNearestChargeStation(cleanSweep.getX(), cleanSweep.getY(), lRetPath);
         if(weight >= (csCurrPower - ndbNeedPower)*0.9){
             return true;
         }
         return false;
    }
    
    private void addPathToQueue(List<String> path){
        for (String ls : path){
        	mMissionQueue.add(tileGraph.getTileNode(ls));
        }
    }
    
    private List<Direction> detectPossibleDirectionsFromHere() {
        //Get all the path it can go
        List<Direction> validDirections = cleanSweep.getValidDirections();

       
        for (Direction direction : validDirections) {
            int x = cleanSweep.getX();
            int y = cleanSweep.getY();
            //Add Node and path to the graph
            if (direction == Direction.Left) {
                x--;
            }
            if (direction == Direction.Right) {
                x++;
            }
            if (direction == Direction.Up) {
                y--;
            }
            if (direction == Direction.Down) {
                y++;
            }
            tileGraph.addEdge(cleanSweep.getX(), cleanSweep.getY(),x, y, TileStatus.HIGH_CARPET);

           
        }
        //If find some way I can not go because of closing door, then we need to update the graph
        List<Direction> invalidDirections = cleanSweep.getInvalidDirections();
        for (Direction direction : invalidDirections) {
            int x = cleanSweep.getX();
            int y = cleanSweep.getY();
            //Add Node and path to the graph
            if (direction == Direction.Left) {
                x--;
            }
            if (direction == Direction.Right) {
                x++;
            }
            if (direction == Direction.Up) {
                y--;
            }
            if (direction == Direction.Down) {
                y++;
            }
            tileGraph.deleteEdge(x, y, cleanSweep.getX(), cleanSweep.getY());
        }
        return validDirections;
    }
}
