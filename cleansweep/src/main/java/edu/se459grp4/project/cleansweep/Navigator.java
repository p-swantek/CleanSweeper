
package edu.se459grp4.project.cleansweep;

import edu.se459grp4.project.Graph.TileNode;
import edu.se459grp4.project.Graph.TilesGraph;
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
    private enum MissionTypeToGoToChargeStation
    {
        MissionUnkown,
        MissionWork,
                MissionCompleted,
                MissionCharge,
                MissionCleanVacuume,
                MissionReturnToWork,
                MissionReturnToChargeWithUnexpectedHappened,
    };
    private class Coordinate {

        public int mx;
        public int my;

        public Coordinate(int nx, int ny) {
           Set(nx,ny);
        }
        public void Set(int nx,int ny) {
            mx = nx;
            my = ny;
        }
    }

    private CleanSweep mCleanSweep;
    private TilesGraph mTileGraph = new TilesGraph();
    private Queue<TileNode> mMissionQueue = new LinkedList<TileNode>();
    private MissionTypeToGoToChargeStation meMissionType = MissionTypeToGoToChargeStation.MissionUnkown;
    private Coordinate mReturningNodeCoordinate = new Coordinate(0,0);
    private ArrayList<String> mReturingPath = new ArrayList<String>();
    private static final int SLEEP = 100;
    
    
    /**
     * Create a navigator for the given clean sweep
     * 
     * @param nCleanSweep the clean sweep with which this navigator is associated
     */
    public Navigator(CleanSweep nCleanSweep) {
        mCleanSweep = nCleanSweep;
    }

    /*
     * Execute the main navigation logic
     * 
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
        //smart control run
        try {
            meMissionType = MissionTypeToGoToChargeStation.MissionWork;
            while (mCleanSweep != null) {
                
                //Detect Around
                List<Direction> lListCanGo = DectectAround();
                if (lListCanGo.isEmpty()) {
                    break;
                }  
                //detect tilestatus and sweep ,Set Visit
                TileStatus lTileStatus = mCleanSweep.senseFloorSurface();
                
                //Visit this node 
                mTileGraph.Visit(mCleanSweep.getX(), mCleanSweep.getY(), lTileStatus);

                //Check if there is a next mission, it 
                if (!mMissionQueue.isEmpty()) {
                    TileNode lMovetoNode = mMissionQueue.poll();
                    Double ldbNeedPower = mTileGraph.GetWeight(mCleanSweep.getX(),
                                mCleanSweep.getY(),
                                lMovetoNode.GetX(),
                                lMovetoNode.GetY());
                    //if mission is working,then need to check the power is enough to return to station
                    if(MissionTypeToGoToChargeStation.MissionWork == meMissionType)
                    {
                        //Check power is enough
                        if(CheckIfNeededGoToChargeStaionOutOfPower(mCleanSweep.getX(),
                                mCleanSweep.getY(),
                                lMovetoNode.GetX(),
                                lMovetoNode.GetY(),
                                ldbNeedPower))
                        {
                            GoToNearestChargeStation(mCleanSweep.getX(),mCleanSweep.getY(),
                                    MissionTypeToGoToChargeStation.MissionCharge) ;
                            continue;
                        }
                    }
                   
                    //if no power then suspend, let the host to move the sweep to the chargesation
                    if(mCleanSweep.getCurrPower() <= 0.0)
                        break;
                    
                    //if power is engough or it is a mission going to station then Move 
                    if(false == mCleanSweep.moveToLoc(lMovetoNode.GetX(), lMovetoNode.GetY()))
                    {
                        //move fail, becausing the door is charge
                        //cancel curruent mission ,go back to charge staion
                        mMissionQueue.clear();
                        GoToNearestChargeStation(mCleanSweep.getX(),mCleanSweep.getY(),
                                MissionTypeToGoToChargeStation.MissionReturnToChargeWithUnexpectedHappened) ;
                        continue;
                        
                    }
                    
                    //move success
                    mCleanSweep.usePowerAmount(ldbNeedPower);
                    
                    
                    
                    //check if arrived the charge station
                    if(meMissionType== MissionTypeToGoToChargeStation.MissionCompleted && 
                            lMovetoNode.TileStatus() == TileStatus.CHARGING_STATION)
                    {
                        //end up all cycle and thread
                        break;
                    }
                    if(meMissionType == MissionTypeToGoToChargeStation.MissionReturnToChargeWithUnexpectedHappened && 
                            lMovetoNode.TileStatus() == TileStatus.CHARGING_STATION)
                    {
                        //end up all cycle and thread
                        mCleanSweep.emptyVacuum();
                        mCleanSweep.rechargePower();
                        meMissionType = MissionTypeToGoToChargeStation.MissionWork;
                        break;
                    }
                   
                    else if(meMissionType== MissionTypeToGoToChargeStation.MissionCleanVacuume && 
                            lMovetoNode.TileStatus() == TileStatus.CHARGING_STATION)
                    {
                        //Clean vacuum and go back to last tile
                        mCleanSweep.emptyVacuum();
                        mCleanSweep.rechargePower();
                        meMissionType = MissionTypeToGoToChargeStation.MissionReturnToWork;
                        //reverse the return path
                        Collections.reverse(mReturingPath);
                        AddStringPathToQueue(mReturingPath);
                        //continue;
                    }
                    else if(meMissionType== MissionTypeToGoToChargeStation.MissionCharge && 
                            lMovetoNode.TileStatus() == TileStatus.CHARGING_STATION)
                    {
                        //Recharge sweep and go back to last tile
                        mCleanSweep.emptyVacuum();
                        mCleanSweep.rechargePower();
                        meMissionType = MissionTypeToGoToChargeStation.MissionReturnToWork;
                        Collections.reverse(mReturingPath);
                        AddStringPathToQueue(mReturingPath);
                        //continue;
                    }
                    else if(meMissionType== MissionTypeToGoToChargeStation.MissionReturnToWork && 
                            mCleanSweep.getX() == mReturningNodeCoordinate.mx &&
                            mCleanSweep.getY() == mReturningNodeCoordinate.my)
                    {
                        //go back to work
                        meMissionType = MissionTypeToGoToChargeStation.MissionWork;
                        mReturingPath.clear();
                        //continue;
                    }
                    
                    Thread.sleep(SLEEP);
                    continue;
                }

                
                int nDirtVal = mCleanSweep.senseDirtAmount();
                while ((lTileStatus == TileStatus.BARE_FLOOR
                        || lTileStatus == TileStatus.LOW_CARPET
                        || lTileStatus == TileStatus.HIGH_CARPET) && nDirtVal > 0) {
                    int lnRet = mCleanSweep.cleanDirt(10);
                    Thread.sleep(20);
                    if (lnRet == 0) {
                        break;
                    }
                }
                
                //if the no enough vacumme space
                if(mCleanSweep.getCurrVacuumCapacity() == 0)
                {
                    GoToNearestChargeStation(mCleanSweep.getX(),
                            mCleanSweep.getY(),
                            MissionTypeToGoToChargeStation.MissionCleanVacuume);
                    continue;
                }
                
                //Get all the path it can go
                List<Coordinate> lListAfterMatched = new ArrayList<Coordinate>();
                for (Direction item : lListCanGo) {
                    int x = mCleanSweep.getX();
                    int y = mCleanSweep.getY();
                    //Add Node and path to the graph
                    if (item == Direction.Left)   x--;
                    if (item == Direction.Right)   x++;
                    if (item == Direction.Up)  y--;
                    if (item == Direction.Down)  y++;
                    if (mTileGraph.IsVisited(x, y) == false) {
                        lListAfterMatched.add(new Coordinate(x, y));
                    }
                }
                //decide which one should go
                if (lListAfterMatched.isEmpty()) {
                    //check if has unvisited node
                    List<TileNode> lListUnvisitedTileNode = mTileGraph.GetUnvisitedNode();
                    if (!lListUnvisitedTileNode.isEmpty()) {
                        GoToNeartestUnvisitedNode(mCleanSweep.getX(), mCleanSweep.getY(),lListUnvisitedTileNode);
                    } else {
                        //Go back chargestation to end this mission
                       GoToNearestChargeStation(mCleanSweep.getX(), mCleanSweep.getY(),MissionTypeToGoToChargeStation.MissionCompleted);
                    }
                } else {
                   // mCleanSweep.MoveOneStep(lListAfterMatched.get(0));
                   Coordinate lMovingNode = lListAfterMatched.get(0);
                   mMissionQueue.add(mTileGraph.GetTileNode(TileNode.GenerateKeyString(lMovingNode.mx, lMovingNode.my)));
                }

            }
        } catch (Exception e) {
            return;
        }
    }

    private double ChooseShortestPath(int x, int y, List<TileNode> nListNode, List<String> nretPath) {
        List<Double> ldbList = new ArrayList<Double>();
        Double ldb = Double.MAX_VALUE;
        for (TileNode item : nListNode) {
            List<String> nArrayPath = new ArrayList<String>();
            Double lTemp = mTileGraph.GetShortestPath(x, y, item.GetX(), item.GetY(), nArrayPath);
            if (lTemp < ldb) {
                ldb = lTemp;
                nretPath.clear();
                nretPath.addAll(nArrayPath);
                // nretPath = nArrayPath;
            }
        }

        return ldb;
    }
    
    private double ChooseANearestChargeStation(int x,int y,List<String> nRetPath)
    {
        List<TileNode> llistChargeStation = mTileGraph.GetChargeStationNode();
        return ChooseShortestPath(mCleanSweep.getX(), mCleanSweep.getY(), llistChargeStation, nRetPath);
    }
    
    private void GoToNearestChargeStation(int x, int y, MissionTypeToGoToChargeStation neMissionType) {
        //go to charge station
       
        List<String> lRetPath = new ArrayList<String>();
        Double ldb = ChooseANearestChargeStation(mCleanSweep.getX(), mCleanSweep.getY(), lRetPath);
        if (0 != Double.compare(ldb, Double.MAX_VALUE)) {
            //check the power and add the mission
            AddStringPathToQueue(lRetPath);
        } 
        //set the returning node
        mReturingPath.clear();
        mReturingPath.add(TileNode.GenerateKeyString(x, y));
        mReturingPath.addAll(lRetPath);
        mReturningNodeCoordinate.Set(x, y);
        meMissionType = neMissionType;
    }
    
    private double GoToNeartestUnvisitedNode(int x, int y, List<TileNode> nListUnvisitedNode) {
        //pich a shortest one
        List<String> lRetPath = new ArrayList<String>();
        Double ldb = ChooseShortestPath(mCleanSweep.getX(), mCleanSweep.getY(), nListUnvisitedNode, lRetPath);
        if (0 != Double.compare(ldb, Double.MAX_VALUE)) {
            //check the power and add the mission
            
            AddStringPathToQueue(lRetPath);
        }
        return ldb;
    }
    
    private boolean CheckIfNeededGoToChargeStaionOutOfPower(int nFromX,int nFromY,int nDestX,int nDestY,Double ndbNeedPower)
    {
         Double lnPowerValue = mCleanSweep.getCurrPower();
         //double ldbNeedPower = mTileGraph.GetWeight(nFromX,nFromY,nDestX,nDestY);
         //cacluate the fewest power to the nearest chargestaion 
         List<String> lRetPath = new ArrayList<String>();
         
         Double ldb = ChooseANearestChargeStation(mCleanSweep.getX(), mCleanSweep.getY(), lRetPath);
         if(ldb >= (lnPowerValue - ndbNeedPower)*0.9)
         {
             return true;
         }
         return false;
    }
    
    private boolean AddStringPathToQueue(List<String> nlistPath)
    {
        for (String ls : nlistPath) {
                mMissionQueue.add(mTileGraph.GetTileNode(ls));
            }
        return true;
    }
    
    private List<Direction> DectectAround() {
        //Get all the path it can go
        List<Direction> lListCanGo = mCleanSweep.getValidDirections();

       
        for (Direction item : lListCanGo) {
            int x = mCleanSweep.getX();
            int y = mCleanSweep.getY();
            //Add Node and path to the graph
            if (item == Direction.Left) {
                x--;
            }
            if (item == Direction.Right) {
                x++;
            }
            if (item == Direction.Up) {
                y--;
            }
            if (item == Direction.Down) {
                y++;
            }
            mTileGraph.AddEdge(mCleanSweep.getX(), mCleanSweep.getY(),x, y, TileStatus.HIGH_CARPET);

           
        }
        //If find some way I can not go because of closing door, then we need to update the graph
        List<Direction> lListCannotGo = mCleanSweep.getInvalidDirections();
        for (Direction item : lListCannotGo) {
            int x = mCleanSweep.getX();
            int y = mCleanSweep.getY();
            //Add Node and path to the graph
            if (item == Direction.Left) {
                x--;
            }
            if (item == Direction.Right) {
                x++;
            }
            if (item == Direction.Up) {
                y--;
            }
            if (item == Direction.Down) {
                y++;
            }
            mTileGraph.DeleteEdge(x, y, mCleanSweep.getX(), mCleanSweep.getY());
        }
        return lListCanGo;
    }
}
