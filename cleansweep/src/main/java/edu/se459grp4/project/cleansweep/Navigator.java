package edu.se459grp4.project.cleansweep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.se459grp4.project.graph.TileNode;
import edu.se459grp4.project.graph.TilesGraph;
import edu.se459grp4.project.simulator.types.Direction;
import edu.se459grp4.project.simulator.types.SurfaceType;

/**
 * Main navigation logic for the clean sweep, will continue exploring a floor
 * and cleaning any tiles that it visits. If the vacuum fills up or the power of
 * the sweep gets too low, it will attempt to navigate back to a nearest
 * charging stating to either empty the vacuum or recharge its power. The clean
 * sweep continues cleaning until all the spots of the floor have been explored
 * and/or cleaned
 * 
 * @author Group 4
 * @version 1.8
 *
 */
public class Navigator implements Runnable {

    private enum MissionType {
        MISSION_UNKNOWN, MISSION_WORK, MISSION_COMPLETED, MISSION_CHARGE, MISSION_EMPTY_VACUUM, MISSION_RETURN_TO_WORK, MISSION_RETURN_TO_CHARGE_STATION_ERROR_OCCURRED
    };

    private class Coordinate {

        public int currX;
        public int currY;

        public Coordinate(int nx, int ny) {
            setLocation(nx, ny);
        }

        public void setLocation(int nx, int ny) {
            currX = nx;
            currY = ny;
        }
    }

    private final CleanSweep cleanSweep;
    private final TilesGraph tileGraph;
    private final Queue<TileNode> mMissionQueue;
    private MissionType currentMission;
    private Coordinate returnNodeCoordinate;
    private final List<String> returnPath;
    private static final int SLEEP_TIME_MS = 100;
    private static final Double ZERO = Double.valueOf(0d);

    /**
     * Create a navigator for the given clean sweep
     * 
     * @param nCleanSweep the clean sweep with which this navigator is
     *            associated
     */
    public Navigator(CleanSweep nCleanSweep) {
        cleanSweep = nCleanSweep;
        tileGraph = new TilesGraph();
        mMissionQueue = new LinkedList<>();
        currentMission = MissionType.MISSION_UNKNOWN;
        returnNodeCoordinate = new Coordinate(0, 0);
        returnPath = new ArrayList<>();
    }

    /*
     * Execute the main navigation logic
     * 
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        //smart control run
        try {
            currentMission = MissionType.MISSION_WORK;
            while (!Thread.currentThread().isInterrupted() && cleanSweep != null) {

                //Detect Around
                List<Direction> lListCanGo = detectPossibleDirectionsFromHere();
                if (lListCanGo.isEmpty()) {
                    break;
                }
                //detect tilestatus and sweep ,Set Visit
                SurfaceType lTileStatus = cleanSweep.senseFloorSurface();

                //Visit this node 
                tileGraph.visit(cleanSweep.getX(), cleanSweep.getY(), lTileStatus);

                //Check if there is a next mission, it 
                if (!mMissionQueue.isEmpty()) {
                    TileNode lMovetoNode = mMissionQueue.poll();
                    double ldbNeedPower = tileGraph.getWeight(cleanSweep.getX(), cleanSweep.getY(), lMovetoNode.getX(), lMovetoNode.getY());

                    if (Double.valueOf(ldbNeedPower).compareTo(ZERO) == 0) {
                        getPathsAndDecide(detectPossibleDirectionsFromHere());
                        TileNode newDest = mMissionQueue.poll();
                        goToNeartestUnvisitedNode(cleanSweep.getX(), cleanSweep.getY(), Arrays.asList(newDest));
                        continue;

                    }

                    //if mission is working,then need to check the power is enough to return to station
                    if (MissionType.MISSION_WORK == currentMission) {
                        //Check power is enough
                        if (shouldReturnToChargingStation(cleanSweep.getX(), cleanSweep.getY(), lMovetoNode.getX(), lMovetoNode.getY(), ldbNeedPower)) {
                            goToNearestChargeStation(cleanSweep.getX(), cleanSweep.getY(), MissionType.MISSION_CHARGE);
                            continue;
                        }
                    }

                    //if no power then suspend, let the host to move the sweep to the chargesation
                    if (cleanSweep.getCurrPower() <= 0.0) {
                        break;
                    }

                    //if power is engough or it is a mission going to station then Move 
                    if (!cleanSweep.moveToLoc(lMovetoNode.getX(), lMovetoNode.getY())) {
                        //move fail, becausing the door is charge
                        //cancel curruent mission ,go back to charge staion
                        mMissionQueue.clear();
                        goToNearestChargeStation(cleanSweep.getX(), cleanSweep.getY(), MissionType.MISSION_RETURN_TO_CHARGE_STATION_ERROR_OCCURRED);
                        continue;

                    }

                    //move success
                    cleanSweep.usePowerAmount(ldbNeedPower);

                    //check if arrived the charge station
                    if (currentMission == MissionType.MISSION_COMPLETED && lMovetoNode.getTileStatus() == SurfaceType.CHARGING_STATION) {
                        //end up all cycle and thread
                        break;
                    }

                    if (currentMission == MissionType.MISSION_RETURN_TO_CHARGE_STATION_ERROR_OCCURRED && lMovetoNode.getTileStatus() == SurfaceType.CHARGING_STATION) {
                        //end up all cycle and thread
                        cleanSweep.emptyVacuum();
                        cleanSweep.rechargePower();
                        currentMission = MissionType.MISSION_WORK;
                        break;
                    }

                    else if (currentMission == MissionType.MISSION_EMPTY_VACUUM && lMovetoNode.getTileStatus() == SurfaceType.CHARGING_STATION) {
                        //Clean vacuum and go back to last tile
                        cleanSweep.emptyVacuum();
                        cleanSweep.rechargePower();
                        currentMission = MissionType.MISSION_RETURN_TO_WORK;
                        //reverse the return path
                        Collections.reverse(returnPath);
                        addPathToQueue(returnPath);
                    }

                    else if (currentMission == MissionType.MISSION_CHARGE && lMovetoNode.getTileStatus() == SurfaceType.CHARGING_STATION) {
                        //Recharge sweep and go back to last tile
                        cleanSweep.emptyVacuum();
                        cleanSweep.rechargePower();
                        currentMission = MissionType.MISSION_RETURN_TO_WORK;
                        Collections.reverse(returnPath);
                        addPathToQueue(returnPath);
                    }

                    else if (currentMission == MissionType.MISSION_RETURN_TO_WORK && cleanSweep.getX() == returnNodeCoordinate.currX && cleanSweep.getY() == returnNodeCoordinate.currY) {
                        //go back to work
                        currentMission = MissionType.MISSION_WORK;
                        returnPath.clear();
                    }

                    Thread.sleep(SLEEP_TIME_MS);
                    continue;
                }

                int nDirtVal = cleanSweep.senseDirtAmount();
                while ((lTileStatus == SurfaceType.BARE_FLOOR || lTileStatus == SurfaceType.LOW_CARPET || lTileStatus == SurfaceType.HIGH_CARPET) && nDirtVal > 0) {
                    int lnRet = cleanSweep.cleanDirt(10);
                    Thread.sleep(20);
                    if (lnRet == 0) {
                        break;
                    }
                }

                //if the no enough vacuum space
                if (cleanSweep.getCurrVacuumCapacity() == 0) {
                    goToNearestChargeStation(cleanSweep.getX(), cleanSweep.getY(), MissionType.MISSION_EMPTY_VACUUM);
                    continue;
                }

                getPathsAndDecide(lListCanGo);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private void getPathsAndDecide(List<Direction> directions) {
        //Get all the path it can go
        List<Coordinate> lListAfterMatched = new ArrayList<>();
        for (Direction direction : directions) {
            int[] newCoords = getModifiedCoordinates(direction);
            if (!tileGraph.isVisited(newCoords[0], newCoords[1])) {
                lListAfterMatched.add(new Coordinate(newCoords[0], newCoords[1]));
            }
        }

        //decide which one should go
        if (lListAfterMatched.isEmpty()) {
            //check if has unvisited node
            List<TileNode> lListUnvisitedTileNode = tileGraph.getUnvisitedNodes();
            if (!lListUnvisitedTileNode.isEmpty()) {
                goToNeartestUnvisitedNode(cleanSweep.getX(), cleanSweep.getY(), lListUnvisitedTileNode);
            } else {
                //Go back chargestation to end this mission
                goToNearestChargeStation(cleanSweep.getX(), cleanSweep.getY(), MissionType.MISSION_COMPLETED);
            }
        } else {
            Coordinate lMovingNode = lListAfterMatched.get(0);
            mMissionQueue.add(tileGraph.getTileNode(TileNode.generateKeyString(lMovingNode.currX, lMovingNode.currY)));
        }
    }

    private double findShortestPathWeight(int x, int y, List<TileNode> listOfNodes, List<String> returnPath) {
        double currMinimum = Double.MAX_VALUE;
        for (TileNode tileNode : listOfNodes) {
            List<String> shortestPath = new ArrayList<>();
            double minimum = tileGraph.getShortestPath(x, y, tileNode.getX(), tileNode.getY(), shortestPath);
            if (minimum < currMinimum) {
                currMinimum = minimum;
                returnPath.clear();
                returnPath.addAll(shortestPath);
            }
        }

        return currMinimum;
    }

    private double chooseNearestChargeStation(int x, int y, List<String> nRetPath) {
        List<TileNode> chargeStations = tileGraph.getChargeStationNode();
        return findShortestPathWeight(cleanSweep.getX(), cleanSweep.getY(), chargeStations, nRetPath);
    }

    private void goToNearestChargeStation(int x, int y, MissionType neMissionType) {
        //go to charge station

        List<String> lRetPath = new ArrayList<>();
        double weight = chooseNearestChargeStation(cleanSweep.getX(), cleanSweep.getY(), lRetPath);
        if (weight != Double.MAX_VALUE) {
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

    private double goToNeartestUnvisitedNode(int x, int y, List<TileNode> unvisitedNodes) {
        //pich a shortest one
        List<String> lRetPath = new ArrayList<>();
        double weight = findShortestPathWeight(cleanSweep.getX(), cleanSweep.getY(), unvisitedNodes, lRetPath);
        if (weight != Double.MAX_VALUE) {
            //check the power and add the mission
            addPathToQueue(lRetPath);
        }
        return weight;
    }

    private boolean shouldReturnToChargingStation(int nFromX, int nFromY, int nDestX, int nDestY, double ndbNeedPower) {
        double csCurrPower = cleanSweep.getCurrPower();
        //cacluate the fewest power to the nearest chargestaion 
        List<String> lRetPath = new ArrayList<>();

        double weight = chooseNearestChargeStation(cleanSweep.getX(), cleanSweep.getY(), lRetPath);
        if (weight >= (csCurrPower - ndbNeedPower) * 0.9) {
            return true;
        }
        return false;
    }

    private void addPathToQueue(List<String> path) {
        for (String ls : path) {
            mMissionQueue.add(tileGraph.getTileNode(ls));
        }
    }

    private List<Direction> detectPossibleDirectionsFromHere() {
        //Get all the path it can go
        List<Direction> validDirections = cleanSweep.getValidDirections();
        for (Direction direction : validDirections) {
            int[] newCoords = getModifiedCoordinates(direction);
            tileGraph.addEdge(cleanSweep.getX(), cleanSweep.getY(), newCoords[0], newCoords[1], SurfaceType.HIGH_CARPET);
        }

        //If find some way I can not go because of closing door, then we need to update the graph
        List<Direction> invalidDirections = cleanSweep.getInvalidDirections();
        for (Direction direction : invalidDirections) {
            int[] newCoords = getModifiedCoordinates(direction);
            tileGraph.deleteEdge(newCoords[0], newCoords[1], cleanSweep.getX(), cleanSweep.getY());
        }
        return validDirections;
    }

    /*
     * generates a modified x and y coordinate based on possible directions,
     * starting values of x and y are the clean sweep's current x an y
     * coordinates. Is passed in a direction, will add or subtract 1 from the x
     * and y coordinates based on which direction it is. final result for x and
     * y will be the new coordinates that should be used for new location. First
     * item of the array is the x coordinate, 2nd is the y coordinate
     */
    private int[] getModifiedCoordinates(Direction direction) {
        int x = cleanSweep.getX();
        int y = cleanSweep.getY();

        if (direction == Direction.LEFT) {
            x--;
        }
        if (direction == Direction.RIGHT) {
            x++;
        }
        if (direction == Direction.UP) {
            y--;
        }
        if (direction == Direction.DOWN) {
            y++;
        }

        return new int[] { x, y };
    }
}
