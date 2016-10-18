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
public abstract class ISensor {
  
 
    private Boolean mbStarted = false;
    
    //Start this Sensor
    public boolean Start()
    {
        mbStarted = true;
        return true;
    }
    
    //Stop this Sensor
    public boolean Stop()
    {
        mbStarted = false;
        return true;
    }
    
    //Check if the sensor is running
    public boolean IsRunning()
    {
        return mbStarted;
    }
    
    //Get the sensor data according the coordinate of x and y
    public abstract String GetSensorData(int x,int y);
    
    
}
