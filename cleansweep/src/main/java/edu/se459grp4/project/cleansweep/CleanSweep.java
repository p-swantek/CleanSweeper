package edu.se459grp4.project.cleansweep;

import edu.se459grp4.project.cleansweep.systems.ControlSystem;
import edu.se459grp4.project.cleansweep.types.Direction;

public class CleanSweep {
    //define the location tile coordinate
    private int mx;
    private int my;

    //each sweep has got a powerful control system
    private ControlSystem mControlSystem= new ControlSystem();

    //define the path from one tile to another next tile
    private class PathStatus
    {
        public static final String UNKNOWN = "0";
        public static final String Open = "1";
        public static final String Blocked = "2";
        public static final String Stair = "3";
    };

    //get the x coordinate of this sweep
    public int getX()
    {
        return mx;
    }
    
    //get the y coordinate of this sweep
    public int getY()
    {
        return my;
    }
    
    //start this sweep
    public boolean start()
    {
         mControlSystem.start();
         //set the inital status.
         //suppose all tiles are dirty
         //and it suppose start from a charge station and power is full and the vacuum capacity is empty.
         //so if the vacuum capacity value is not zero then start will fail.
         
         return true;
    }
    
    //stop this sweep
    public boolean stop()
    {
          mControlSystem.stop();
          return true;
    }
    
    //Check if I cam move to the next tile in the specific direction
    private String checkMove(Direction nDirection)
    {
        String lsRet = "";
        if(nDirection == Direction.Left)
            lsRet = mControlSystem.checkMoveLeft(mx, my);
        else if(nDirection == Direction.Right)
            lsRet = mControlSystem.checkMoveRight(mx, my);
        else if(nDirection == Direction.Up)
            lsRet = mControlSystem.checkMoveUp(mx, my);
        else 
            lsRet = mControlSystem.checkMoveDown(mx, my);
        return   lsRet;  
    }
    
    //move this sweep with one step in a spicific directions
    public boolean moveOneStep(Direction nDirection)
    {
        String lsRet = checkMove(nDirection);
        if(lsRet == PathStatus.Open)
        {
           if(nDirection == Direction.Left)
               mx = mx-1;
           else if(nDirection == Direction.Right)
              mx = mx+1;
           else if(nDirection == Direction.Up)
              my = my-1;
           else 
             my = my+1;
           return   true;  
        }
        return false;
    }

}
