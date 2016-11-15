package edu.se459grp4.project.simulator;


import edu.se459grp4.project.simulator.types.TileStatus;
import edu.se459grp4.project.simulator.models.*;
import edu.se459grp4.project.simulator.types.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Simulator {
    private static Simulator instance ;
    public static synchronized Simulator getInstance(){
        if(instance == null){
            instance = new Simulator();
        }
        return instance;
    }
    
    private FloorPlan mFloorPlan;
    
    private Simulator()
    {
       mFloorPlan = null; 
    }
    
    
    public FloorPlan loadFloorPlan(String fileLocation) {
     // FloorPlan lLoadedFloorPlan = null;
      mFloorPlan = null;
      try {
         FileInputStream fileIn = new FileInputStream(fileLocation);
         ObjectInputStream in = new ObjectInputStream(fileIn);
         mFloorPlan = (FloorPlan) in.readObject();
         in.close();
         fileIn.close();
         return mFloorPlan;
      }catch(IOException i) {
        // i.printStackTrace();
         return null;
      }catch(ClassNotFoundException c) {
         //System.out.println("./example.flr class not found");
         //c.printStackTrace();
         return null;
      }
    }

    public PathStatus ProvideDirectionSensroData(Direction nDirection,int x,int y)
    {
        //
        int nDestX,nDestY;
        nDestX = x;
        nDestY = y;
        if(nDirection == Direction.Up)
            nDestY--;
        if(nDirection == Direction.Down)
            nDestY++;
        if(nDirection == Direction.Left)
            nDestX--;
        if(nDirection == Direction.Right)
           nDestX++;
        
        //Check if there is path from (x,y) to (nDestX,nDestY)
        if(mFloorPlan != null)
            return mFloorPlan.CheckPath(x, y, nDestX, nDestY);
        return PathStatus.Blocked;
    }
    public int ProvideDirtSensroData(int x,int y)
    {
        if(mFloorPlan != null)
            return mFloorPlan.GetDirtVal(x, y);
          
        return 0;
    }
    public TileStatus ProvideSurfaceSensorData(int x,int y)
    {
        if(mFloorPlan != null)
            return mFloorPlan.GetTileSatus(x, y);
         
        return TileStatus.BARE_FLOOR;
    }
    
    public int SweepUp(int x,int y,int nVal)
    {
         if(mFloorPlan != null)
            return mFloorPlan.SweepUp(x, y,nVal);
        return 0;
    }
    public boolean OperateDoor(boolean nVer,int nBase,int nFrom,int nTo,boolean bOpen)
    {
          if(mFloorPlan != null)
            return mFloorPlan.OperateDoor(nVer, nBase, nFrom, nTo, bOpen);
        return false;
    }
   
    
    public boolean AddChargeStation(int nX,int nY)
    {
         if(mFloorPlan == null)
             return false;
         return mFloorPlan.SetTileSatus(nX, nY, TileStatus.CHARGING_STATION);
        
    }
    public boolean RemoveChargeStation(int nX,int nY)
    {
        if(mFloorPlan == null)
             return false;
        return mFloorPlan.SetTileSatus(nX, nY, TileStatus.BARE_FLOOR);
    }
    public List<Door> GetAllDoors()
    {
         if(mFloorPlan == null)
             return null;
        return mFloorPlan.GetAllDoors();
    }
    public List<Tile> GetAllChargeStations()
    {
       if(mFloorPlan == null)
             return null;
        return mFloorPlan.GetAllChargeStations();
    }
   
}
