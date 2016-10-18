/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.se459grp4.project.cleansweep.systems;

import edu.se459grp4.project.cleansweep.sensors.DownDirectionalSensor;
import edu.se459grp4.project.cleansweep.sensors.LeftDirectionalSensor;
import edu.se459grp4.project.cleansweep.sensors.RightDirectionalSensor;
import edu.se459grp4.project.cleansweep.sensors.UpDirectionalSensor;

/**
 *
 * @author Weihua
 */
public class ControlSystem {
    
    private LeftDirectionalSensor mLeftSensor = new LeftDirectionalSensor();
    private RightDirectionalSensor mRightSensor = new RightDirectionalSensor();
    private UpDirectionalSensor mUpSensor = new UpDirectionalSensor();
    private DownDirectionalSensor mDownSensor = new DownDirectionalSensor();
    

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
    
    public String checkMoveLeft(int x, int y)
    {
        return mLeftSensor.getSensorData(x, y);
    }
    public String checkMoveRight(int x, int y)
    {
        return mRightSensor.getSensorData(x, y);
    }
    public String checkMoveUp(int x, int y)
    {
        return mUpSensor.getSensorData(x, y);
    }
    public String checkMoveDown(int x, int y)
    {
        return mDownSensor.getSensorData(x, y);
    }
    
}
