/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.se459grp4.project.cleansweep.sensors;

import edu.se459grp4.project.simulator.SensorSimulator;

/**
 *
 * @author Weihua
 */
public class UpDirectionalSensor extends ISensor {
     //Get the sensor data according the coordinate of x and y
    public  String GetSensorData(int x,int y)
    {
        //check the simulator to get the x,y-1
        //check if I can traverse to the (x,y-1) for (x,y)
        //0 is unknow
        //1 is open
        //2 is obstacle
        //3 is stair
        return SensorSimulator.GetDirectionSensorData(SensorSimulator.Direction.Up, x, y);
    }
}
