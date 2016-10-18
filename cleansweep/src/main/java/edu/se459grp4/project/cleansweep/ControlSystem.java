/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.se459grp4.project.cleansweep;

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
    public boolean Start()
    {
        mLeftSensor.Start();
        mRightSensor.Start();
        mUpSensor.Start();
        mDownSensor.Start();
        return true;
    }
    
    //start the control system
    public boolean Stop()
    {
        mLeftSensor.Stop();
        mRightSensor.Stop();
        mUpSensor.Stop();
        mDownSensor.Stop();
        return true;
    }
    
    public String CheckMoveLeft(int x, int y)
    {
        return mLeftSensor.GetSensorData(x, y);
    }
    public String CheckMoveRight(int x, int y)
    {
        return mRightSensor.GetSensorData(x, y);
    }
    public String CheckMoveUp(int x, int y)
    {
        return mUpSensor.GetSensorData(x, y);
    }
    public String CheckMoveDown(int x, int y)
    {
        return mDownSensor.GetSensorData(x, y);
    }
    
}
