/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.se459grp4.project.simulator.models;
import java.util.*;
/**
 *
 * @author whao
 */
public class Wall implements java.io.Serializable {
    private boolean mbVertical;
    private int mnBase; 
    private int mnFrom;
    private int mnTo;
    private Map<String,Door> mDoors;
      
    private String GenerateDoorKey(int nBase,int x,int y)
    {
        return "Door"+nBase+"_"+x+"_"+y;
    }
    public int GetBase()
    {
        return mnBase;
    }
    public int GetFrom()
    {
        return mnFrom;
    }
    public int GetTo()
    {
        return mnTo;
    }
    public boolean GetVertical()
    {
        return mbVertical;
    }
    public List<Door> GetAllDoors()
    {
        List<Door> lList = new ArrayList<Door>();
        for(Map.Entry<String,Door> entry : mDoors.entrySet()){
           lList.add(entry.getValue());
        }
        return lList;
    }
    public Wall(boolean nVer,int nBase,int nFrom,int nTo)
    {
        mnBase = nBase;
        mbVertical = nVer;
        mnFrom = nFrom;
        mnTo = nTo;
        mDoors = new HashMap<String,Door>();
    }
    public boolean AddDoor(int nBase,int nFrom,int nTo,boolean nbOpen)
    {
        if(nFrom < mnFrom || nTo > mnTo)
            return false;
        if(nTo - nFrom > 1)
            return false;
        mDoors.put(GenerateDoorKey(nBase,nFrom,nTo),
                new Door(mbVertical,nBase,nFrom,nTo,nbOpen) );
        return true;
    }
    
    public boolean CheckCanBuildDoor(boolean nVer,int nBase,int nX,int nY)
    {
        if(mbVertical == nVer &&
           mnBase == nBase&&
           nX >= mnFrom &&
           nY <= mnTo)
            return true;
        return false;
    }
    public boolean CheckCanPass(boolean nVer,int nBase,int nFrom,int nTo)
    {
        if(mbVertical != nVer || mnBase != nBase)
            return true;
        if(nFrom >= mnFrom && nTo <= mnTo )
        {
            //check if there is a open door
           
            for(Map.Entry<String,Door> entry : mDoors.entrySet()){
                if(entry.getValue().CheckPass(nFrom,nTo))
                    return true;
                    
            }
        
            return false;
        }
            
        return true;
    }
   
    public boolean OperateDoor(boolean nbVer,int nBase,int x,int y,boolean nbVal)
    {
        if(nbVer != mbVertical)
            return false;
       //find the door
       Door lDoor = mDoors.get(GenerateDoorKey(nBase,x,y));
       if(lDoor == null)
           return false;
       
       return nbVal == false ? lDoor.Close():lDoor.Open();
    }
}
