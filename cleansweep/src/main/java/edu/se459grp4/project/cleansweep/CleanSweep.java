package edu.se459grp4.project.cleansweep;

import edu.se459grp4.project.simulator.Simulator;
import edu.se459grp4.project.simulator.types.Direction;
import edu.se459grp4.project.simulator.types.PathStatus;
import edu.se459grp4.project.simulator.types.TileStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class CleanSweep extends Observable {

    private   int mnMaxVacuum ;
    private   Double mdbMaxPower ;
    //define the location tile coordinate
    private int mnID;
    private int mx;
    private int my;
    private Double mdbPowerValue;
    private int mnVacuumCapacityValue;

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
    
    
    //public 
    //
    //  each clean sweep has 4 navigation sensor
    private NavigationSensor mLeftSensor = new NavigationSensor(Direction.Left);
    private NavigationSensor mRightSensor = new NavigationSensor(Direction.Right);
    private NavigationSensor mUpSensor = new NavigationSensor(Direction.Up);
    private NavigationSensor mDownSensor = new NavigationSensor(Direction.Down);

    private DirtSensor mDirtSensor = new DirtSensor();
    private SurfaceSensor mSurfaceSensor = new SurfaceSensor();

    //get the x coordinate of this sweep
    public synchronized int GetX() {
        return mx;
    }

    //get the y coordinate of this sweep
    public synchronized int GetY() {
        return my;
    }

    public int GetID() {
        return mnID;
    }

    public List<Direction> GetAllDirectionCanGo() {
        List<Direction> lRetList = new ArrayList<Direction>();
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


    public synchronized boolean MoveTo(int x, int y) {
        
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

    public TileStatus DetectSurfaceType() {
        return mSurfaceSensor.GetSensorData(mx, my);
    }

    public int DetectDirtValue() {
        return mDirtSensor.GetSensorData(mx, my);
    }

    public synchronized int SweepUp(int nVal) {
        int nVacummVal = Simulator.getInstance().SweepUp(mx, my, nVal);
        ExhaustVacuume(nVacummVal);
        return nVacummVal;
    }

    public synchronized Double GetPowerLevel()
    {
        return mdbPowerValue;
    }
    public synchronized Double ExhaustPower(Double ndb)
    {
        mdbPowerValue -= ndb;
        setChanged();
        notifyObservers(this);
        return mdbPowerValue;
    }
    public synchronized int GetVacuumLevel()
    {
        return mnVacuumCapacityValue;
    }
    public synchronized int  ExhaustVacuume(int nnVacuumVal)
    {
        mnVacuumCapacityValue -= nnVacuumVal;
        setChanged();
        notifyObservers(this);
        return mnVacuumCapacityValue;
    }
    public synchronized int CleanVacuum()
    {
        mnVacuumCapacityValue = mnMaxVacuum;
        setChanged();
        notifyObservers(this);
        return mnVacuumCapacityValue;
    }
    
     public synchronized Double Recharge()
    {
        mdbPowerValue = mdbMaxPower;
        setChanged();
        notifyObservers(this);
        return mdbPowerValue;
    }
   
}
