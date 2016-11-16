package edu.se459grp4.project.cleansweep;

import edu.se459grp4.project.simulator.Simulator;
import edu.se459grp4.project.simulator.types.Direction;
import edu.se459grp4.project.simulator.types.PathStatus;
import edu.se459grp4.project.simulator.types.TileStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


/**
 * Represents a CleanSweep vacuum robot.  The robot contains a vacuum to suck up dirt off the floor, 
 * as well as a variety of sensors to sense various aspects of the floor which it is cleaning.  The clean 
 * sweep can use sensor data to gather information on directions it can travel as well as the floor type
 * of the floor being traveled as well as the dirtiness of a spot on the floor. Clean sweep maintains
 * an internal power supply and needs to be recharged to keep working.  
 * 
 * @author Group 4
 * @version 1.8
 *
 */
public class CleanSweep extends Observable {

    private int mnMaxVacuum ;
    private Double mdbMaxPower ;
    //define the location tile coordinate
    private int mnID;
    private int mx;
    private int my;
    private Double mdbPowerValue;
    private int mnVacuumCapacityValue;
    
    //  each clean sweep has 4 navigation sensor
    private NavigationSensor mLeftSensor = new NavigationSensor(Direction.Left);
    private NavigationSensor mRightSensor = new NavigationSensor(Direction.Right);
    private NavigationSensor mUpSensor = new NavigationSensor(Direction.Up);
    private NavigationSensor mDownSensor = new NavigationSensor(Direction.Down);
    
    private DirtSensor mDirtSensor = new DirtSensor();
    private SurfaceSensor mSurfaceSensor = new SurfaceSensor();

    /**
     * Constructs a new CleanSweep. CleanSweep has a unique id number, a starting power level, vacuum capacity,
     * and a starting (x,y) coordinate
     * 
     * @param nID the id of this CleanSweep
     * @param ndbPowerValue the starting power level of this CleanSweep
     * @param nVacuumCapacityValue the max capacity of this CleanSweep's vacuum
     * @param nx starting coordinate x value
     * @param ny starting coordinate y value
     */
    public CleanSweep(int nID, 
            Double ndbPowerValue,
            int nVacuumCapacityValue,
            int nx,
            int ny) {
        mdbPowerValue = ndbPowerValue;
        mdbMaxPower = ndbPowerValue;
        mnVacuumCapacityValue = nVacuumCapacityValue;
        mnMaxVacuum = nVacuumCapacityValue;
        mnID = nID;
        mx = nx;
        my = ny;
    }
    
    
    /**
     * Get the current x coordinate of this sweep
     * 
     * @return the x coordinate
     */
    public synchronized int GetX() {
        return mx;
    }

    /**
     * Get the current y coordinate of this sweep
     * 
     * @return the y coordinate
     */
    public synchronized int GetY() {
        return my;
    }
    
    /**
     * Get the id number of this sweep
     * 
     * @return the int id number of the sweep
     */
    public int GetID() {
        return mnID;
    }

    /**
     * Generates a list of directions that can be traveled from the current location. 
     * A direction can be traveled if the path status of that direction is open
     * 
     * @return the list of possible directions
     */
    public List<Direction> GetAllDirectionCanGo() {
        List<Direction> lRetList = new ArrayList<>();
        if (PathStatus.Open == CheckMove(Direction.Left)) {
            lRetList.add(Direction.Left);
        }
        if (PathStatus.Open == CheckMove(Direction.Up)) {
            lRetList.add(Direction.Up);
        }
        if (PathStatus.Open == CheckMove(Direction.Right)) {
            lRetList.add(Direction.Right);
        }

        if (PathStatus.Open == CheckMove(Direction.Down)) {
            lRetList.add(Direction.Down);
        }
        return lRetList;

    }
    
    /**
     * Generates a list of the directions that can not be traveled based on the 
     * current location. A direction can't be traveled if the path status in that
     * direction is not open
     * 
     * @return the list of directions in which the sweep can't travel
     */
    public List<Direction> GetAllDirectionCannotGo() {
        List<Direction> lRetList = new ArrayList<Direction>();
        if (PathStatus.Open != CheckMove(Direction.Left)) {
            lRetList.add(Direction.Left);
        }
        if (PathStatus.Open != CheckMove(Direction.Up)) {
            lRetList.add(Direction.Up);
        }
        if (PathStatus.Open != CheckMove(Direction.Right)) {
            lRetList.add(Direction.Right);
        }

        if (PathStatus.Open != CheckMove(Direction.Down)) {
            lRetList.add(Direction.Down);
        }
        return lRetList;

    }
    
    /**
     * Checks a direction to see if you can move in that direction. Queries the
     * sensors to get the status of the path to the given direction.
     * 
     * @param nDirection the movement direction that should be checked
     * @return the PathStatus for the given direction
     * @see PathStatus
     */
    public synchronized PathStatus CheckMove(Direction nDirection) {
        if (nDirection == Direction.Left) {
            return mLeftSensor.GetSensorData(mx, my);
        }
        if (nDirection == Direction.Right) {
            return mRightSensor.GetSensorData(mx, my);
        }
        if (nDirection == Direction.Up) {
            return mUpSensor.GetSensorData(mx, my);
        }
        if (nDirection == Direction.Down) {
            return mDownSensor.GetSensorData(mx, my);
        }

        return PathStatus.UNKNOWN;
    }


    /**
     * Tells whether the sweep can move to the given x and y coordinates. sweep will update its position
     * if the move is successful
     * 
     * @param x the new x coordinate
     * @param y the new y coordinate
     * @return true if the move succeeded, false otherwise
     */
    public synchronized boolean MoveTo(int x, int y) {
    	
    	if (Math.abs(GetX()-x) > 1 || Math.abs(GetY()-y) > 1){
    		throw new IllegalArgumentException("MoveTo() got passed an invalid x or y coordinate.");
    	}
        
        if(x == mx)
        {
            if( PathStatus.Open != CheckMove(y < my ? Direction.Up : Direction.Down))
                  return false;
        }
        else {
            if( PathStatus.Open != CheckMove(x < mx ? Direction.Left : Direction.Right))
                  return false;
        
        }
        mx = x;
        my = y;
        setChanged();
        notifyObservers(this);
        return true;
    }

    /**
     * Use the surface sensor to get the floor tile type of its current location
     * 
     * @return the TileStatus of the current floor tile on which the sweep is located
     * @see TileStatus
     */
    public TileStatus DetectSurfaceType() {
        return mSurfaceSensor.GetSensorData(mx, my);
    }

    /**
     * Use the dirt sensor to get the dirt amount on its current location
     * 
     * @return an integer representing the amount of dirt on the floor tile
     */
    public int DetectDirtValue() {
        return mDirtSensor.GetSensorData(mx, my);
    }

    /**
     * Have the sweep clean up an amount of dirt from its location
     * 
     * @param nVal the amount of dirt the sweep up
     * @return the amount of dirt that was succesfully removed from the sweeps position
     * @see Simulator
     */
    public synchronized int SweepUp(int nVal) {
        int nVacummVal = Simulator.getInstance().SweepUp(mx, my, nVal);
        exhaustVacuum(nVacummVal);
        return nVacummVal;
    }

    /**
     * Gets the current power remaining in this sweep
     * 
     * @return a double representing the current power level
     */
    public synchronized Double GetPowerLevel()
    {
        return mdbPowerValue;
    }
    
    /**
     * Have this clean sweep expend some of its power supply
     * 
     * @param ndb the amount of power to decrease from the current power supply
     * @return the new amount of power remaining after exhausing resources
     */
    public synchronized Double ExhaustPower(Double ndb)
    {
        mdbPowerValue -= ndb;
        setChanged();
        notifyObservers(this);
        Logger.writeToBatteryLog(mdbPowerValue);
        return mdbPowerValue;
    }
    
    /**
     * Get the vacuum capacity for this sweep
     * 
     * @return the the sweep's vacuum's capacity
     */
    public synchronized int GetVacuumLevel()
    {
        return mnVacuumCapacityValue;
    }
    
    /**
     * Decrease the amount of space inside the sweep's vacuum 
     *
     * @param nnVacuumVal the amount of space to decrease the vacuum's capacity by
     * @return the new capacity of the vacuum
     */
    public synchronized int exhaustVacuum(int nnVacuumVal)
    {
        mnVacuumCapacityValue -= nnVacuumVal;
        setChanged();
        notifyObservers(this);
		Logger.writeToDirtSensorLog(nnVacuumVal, mx, my);
		Logger.writeToDirtCapacityLog(mnVacuumCapacityValue, mnMaxVacuum, mx, my);
        return mnVacuumCapacityValue;
    }
    
   
    /**
     * Empty the vacuum, reset its capacity to the default max capacity
     * 
     * @return the capacity of the vacuum after it has been emptied
     */
    public synchronized int CleanVacuum()
    {
        mnVacuumCapacityValue = mnMaxVacuum;
        setChanged();
        notifyObservers(this);
		Logger.writeToDirtCapacityLog(0, mnMaxVacuum, mx, my);
        return mnVacuumCapacityValue;
    }
    
    /**
     * Recharge the power on this sweep to the default max power level
     * 
     * @return the amount of power in the sweep after it has been recharged
     */
     public synchronized Double Recharge()
    {
        mdbPowerValue = mdbMaxPower;
        setChanged();
        notifyObservers(this);
		Logger.writeToBatteryLog(mdbMaxPower);
        return mdbPowerValue;
    }
   
}
