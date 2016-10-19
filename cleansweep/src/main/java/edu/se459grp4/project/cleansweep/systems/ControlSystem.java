/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.se459grp4.project.cleansweep.systems;

import edu.se459grp4.project.cleansweep.sensors.*;
import edu.se459grp4.project.cleansweep.types.Direction;
import edu.se459grp4.project.cleansweep.types.PathStatus;

/**
 *
 * @author Weihua
 */
public class ControlSystem {
    
    private DirectionalSensor mLeftSensor = new DirectionalSensor(Direction.LEFT);
    private DirectionalSensor mRightSensor = new DirectionalSensor(Direction.RIGHT);
    private DirectionalSensor mUpSensor = new DirectionalSensor(Direction.UP);
    private DirectionalSensor mDownSensor = new DirectionalSensor(Direction.DOWN);
    

    //start the control system
    public boolean start()
    {
        mLeftSensor.start();
        mRightSensor.start();
        mUpSensor.start();
        mDownSensor.start();
        return true;
    }
    
    //start the control system
    public boolean stop()
    {
        mLeftSensor.stop();
        mRightSensor.stop();
        mUpSensor.stop();
        mDownSensor.stop();
        return true;
    }
    
    public PathStatus checkMoveLeft()
    {
        return mLeftSensor.getSensorData();
    }
    public PathStatus checkMoveRight()
    {
        return mRightSensor.getSensorData();
    }
    public PathStatus checkMoveUp()
    {
        return mUpSensor.getSensorData();
    }
    public PathStatus checkMoveDown()
    {
        return mDownSensor.getSensorData();
    }
    
}