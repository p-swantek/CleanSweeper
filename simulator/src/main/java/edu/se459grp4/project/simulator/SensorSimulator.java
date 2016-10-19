/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.se459grp4.project.simulator;
/**
 *
 * @author Weihua
 */
public class SensorSimulator {
    //this class will provide the simulation data to the CleanSweep
     //define the direction that this sweep can move
    public static int getPathData(String direction)
    {
        //need the communicate with the floor plan class,
        //the floor plan class need to tell the status of the path from (x,y) to the next tile in a spicific direction.
        // TODO: Retrieve path information from FloorTiles[][]
        switch(direction) {
            case "LEFT":
                return 0;
            case "RIGHT":
                return 0;
            case "UP":
                return 0;
            case "DOWN":
                return 0;
            default:
                return 0;
        }

    }
    
}
