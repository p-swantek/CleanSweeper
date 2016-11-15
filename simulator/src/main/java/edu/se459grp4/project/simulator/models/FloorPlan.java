/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.se459grp4.project.simulator.models;
import edu.se459grp4.project.simulator.types.*;
import java.util.*;
import java.util.Observable;
import java.util.Observer;
/**
 *
 * @author whao
 */
public class FloorPlan  extends Observable implements java.io.Serializable {
    private int mnTileSquareNum;
    private int mnTileSize;
    private int mnWallWidth;
    private int mnDoorLength;
    private Map<String,Tile> mMapTiles;
    private Map<String,Wall> mMapWalls;
    
    private String GenerateKey(int x,int y)
    {
        return "Tile"+x+" " + y;
    }
     private String GenerateWallKey(int nBase,int x,int y,boolean nVer)
    {
        return "Wall"+x+"_"+y+"_"+nBase+"_"+nVer;
    }
    public FloorPlan(int nSquareNum,int nTileSize,int nWallWidth,int nDoorLength)
    {
        mnTileSquareNum = nSquareNum;
        mnTileSize = nTileSize;
        mnWallWidth = nWallWidth;
        mnDoorLength = nDoorLength;
        mMapTiles = new HashMap<String,Tile>();
        mMapWalls = new HashMap<String,Wall>();
    }
    public int GetTilesSquareNum()
    {
        return mnTileSquareNum;
    }
    public List<Tile> GetAllTiles()
    {
        List<Tile> lList = new ArrayList<Tile>();
        for(Map.Entry<String,Tile> entry : mMapTiles.entrySet()){
           lList.add(entry.getValue());
        }
        return lList;
    }
    
    public List<Wall> GetAllWalls()
    {
        List<Wall> lList = new ArrayList<Wall>();
        for(Map.Entry<String,Wall> entry : mMapWalls.entrySet()){
           lList.add(entry.getValue());
        }
        return lList;
    }
    public List<Door> GetAllDoors()
    {
        List<Door> lList = new ArrayList<Door>();
        for(Map.Entry<String,Wall> entry : mMapWalls.entrySet()){
           lList.addAll(entry.getValue().GetAllDoors());
        }
        return lList;
    }
    public List<Tile> GetAllChargeStations()
    {
        List<Tile> lList = new ArrayList<Tile>();
        for(Map.Entry<String,Tile> entry : mMapTiles.entrySet()){
           if(entry.getValue().GetStatus() == TileStatus.CHARGING_STATION)
             lList.add(entry.getValue());
        }
        return lList;
    }
    public boolean AddTile(int x,int y,TileStatus nStatus,int nDirVal)
    {
        if(x < 0 || x >= mnTileSquareNum)
            return false;
        if(y < 0 || y >= mnTileSquareNum)
            return false;
        //should check if x,y existed
        mMapTiles.put(GenerateKey(x,y) ,new Tile(x,y,nStatus,nDirVal));
        return true;
    }
    
    public boolean AddWall(boolean nVer,int nBase,int x,int y)
    {
        if(x < 0 || x >= mnTileSquareNum)
            return false;
        if(y < 0 || y >= mnTileSquareNum)
            return false;
        //should check if x,y existed
        mMapWalls.put(GenerateWallKey(nBase,x,y,nVer), new Wall(nVer,nBase,x,y));
        return true;
    }
    
    public boolean AddDoor(boolean nVer,int nBase,int x,int y,boolean nbOepn)
    {
        if(x < 0 || x >= mnTileSquareNum)
            return false;
        if(y < 0 || y >= mnTileSquareNum)
            return false;
        //Find the wall include x,y
        Wall lWall = null;
        for(Map.Entry<String,Wall> entry : mMapWalls.entrySet()){
            if(entry.getValue().CheckCanBuildDoor(nVer,nBase,x,y) != false)
            {
                lWall = entry.getValue();
                break;
            }
        }
        //Add the door to that wall
        if(lWall != null)
         return lWall.AddDoor(nBase,x,y,nbOepn);
        return false;
    }
    
    public PathStatus CheckPath(int x,int y,int nDestX,int nDestY)
    {
        //check x,y existing
        Tile lTile = mMapTiles.get(GenerateKey(nDestX,nDestY));
        if(lTile == null)
            return PathStatus.Blocked;
        
        //Check Stair
        if(lTile.GetStatus() == TileStatus.STAIRS)
            return PathStatus.Stair;
        
        //check if there is a wall 
        boolean lbVertical = (x == nDestX ? false : true);
        int nBase = (lbVertical == false ? Math.min(y, nDestY) : Math.min(x, nDestX));
        int nFrom = (lbVertical == false ? Math.min(x, nDestX) : Math.min(y, nDestY));
        int nTo = nFrom;

        //Find the wall include lbVertical,x,y
        boolean lbRet = true;
        for(Map.Entry<String,Wall> entry : mMapWalls.entrySet()){
            if(entry.getValue().CheckCanPass(lbVertical,nBase,nFrom,nTo) == false)
            {
                lbRet = false;
                break;
            }
        }
        
        return lbRet!=false ? PathStatus.Open:PathStatus.Blocked;
    }
    
    
    public int GetDirtVal(int x,int y)
    {
        Tile lTile = mMapTiles.get(GenerateKey(x,y));
        if(lTile != null)
            return lTile.GetDirtVal();
        return 0;
    }
      
    public boolean SetDirtVal(int x,int y,int nVal)
    {
        Tile lTile = mMapTiles.get(GenerateKey(x,y));
        if(lTile != null)
            return lTile.SetDirtVal(nVal);
        return false ;
    }
    
    public TileStatus GetTileSatus(int x,int y)
    {
        Tile lTile = mMapTiles.get(GenerateKey(x,y));
        if(lTile != null)
            return lTile.GetStatus();
        return TileStatus.BARE_FLOOR;
    }
    public boolean SetTileSatus(int x,int y,TileStatus nStatus)
    {
        Tile lTile = mMapTiles.get(GenerateKey(x,y));
        if(lTile != null)
        {
            lTile.SetStatus(nStatus);
            setChanged();
            notifyObservers(lTile);
            return true;
        }
        return false;
    }
    public int SweepUp(int x,int y,int nVal)
    {
        Tile lTile = mMapTiles.get(GenerateKey(x,y));
        if(lTile != null )
        {
            int lnVal = lTile.Sweep(nVal);
            setChanged();
            notifyObservers(lTile);
            return lnVal;
        } 
        return 0;
    }
    public boolean OperateDoor(boolean nbVer,int nBase,int x,int y,boolean nbVal)
    {
        //find the door from the wall
        boolean lbRet = false;
        
        for(Map.Entry<String,Wall> entry : mMapWalls.entrySet()){
            if(entry.getValue().OperateDoor(nbVer,nBase,x,y,nbVal) != false)
            {
                lbRet = true;
                setChanged();
                notifyObservers(entry.getValue());
                break;
            }
        }
        return lbRet;
    }
}
