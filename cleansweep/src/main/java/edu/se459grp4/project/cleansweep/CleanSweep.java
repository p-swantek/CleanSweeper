package edu.se459grp4.project.cleansweep;

public class CleanSweep {
    //define the direction that this sweep can move
    enum Direction
    {
        Up,
        Left,
        Down,
        Right
    };
    
    //define the path from one tile to another next tile
    private class PathStatus
    {
        public static final String UNKNOWN = "0";
        public static final String Open = "1";
        public static final String Blocked = "2";
        public static final String Stair = "3";
    };
    
    //define the location tile coordinate
    private int mx;
    private int my;
  
    //each sweep has got a powerful control system
    private ControlSystem mControlSystem= new ControlSystem();
    
    
    //get the x coordinate of this sweep
    public int GetX()
    {
        return mx;
    }
    
    //get the y coordinate of this sweep
    public int GetY()
    {
        return my;
    }
    
    //start this sweep
    public boolean Start()
    {
         mControlSystem.Start();
         //set the inital status.
         //suppose all tiles are dirty
         //and it suppose start from a charge station and power is full and the vacuum capacity is empty.
         //so if the vacuum capacity value is not zero then start will fail.
         
         return true;
    }
    
    //stop this sweep
    public boolean Stop()
    {
          mControlSystem.Stop();
          return true;
    }
    
    //Check if I cam move to the next tile in the specific direction
    private String CheckMove(Direction nDirection)
    {
        String lsRet = "";
        if(nDirection == Direction.Left)
            lsRet = mControlSystem.CheckMoveLeft(mx, my);
        else if(nDirection == Direction.Right)
            lsRet = mControlSystem.CheckMoveRight(mx, my);
        else if(nDirection == Direction.Up)
            lsRet = mControlSystem.CheckMoveUp(mx, my);
        else 
            lsRet = mControlSystem.CheckMoveDown(mx, my);
        return   lsRet;  
    }
    
    //move this sweep with one step in a spicific directions
    public boolean MoveOneStep(Direction nDirection)
    {
        String lsRet = CheckMove(nDirection);
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
