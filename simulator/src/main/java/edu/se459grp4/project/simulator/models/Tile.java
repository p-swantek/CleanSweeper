/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.se459grp4.project.simulator.models;
import edu.se459grp4.project.simulator.types.TileStatus;
/**
 *
 * @author whao
 */

public class Tile extends Object implements java.io.Serializable{
    private int mx;
    private int my;
    private TileStatus mTileStatus;
    private int mnDirtVal;
    
    public Tile(int x,int y,TileStatus nStatus,int nVal)
    {
        mx = x;
        my = y;
        mTileStatus = nStatus;
        mnDirtVal = nVal;
    }
    public int GetX()
    {
        return mx;
    }
    public int GetY()
    {
        return my;
    }
    public synchronized TileStatus GetStatus()
    {
        return mTileStatus;
    }
     public synchronized boolean SetStatus(TileStatus nStatus)
    {
        mTileStatus = nStatus;
        return true;
    }
    public synchronized  int GetDirtVal()
    {
        return mnDirtVal;
    }
    public synchronized  boolean SetDirtVal(int nVal)
    {
        mnDirtVal = nVal;
        return true;
        
    }
    public synchronized  int Sweep(int nVal)
    {
        if(mnDirtVal == 0)
            return 0;
        
        if(mnDirtVal > nVal)
        {
            mnDirtVal -= nVal;
            return nVal;
        }
        else
        {
            int lTemp = mnDirtVal;
            mnDirtVal = 0;
            return lTemp;
        }
    }
    
    @Override
    public String toString()
    {
        return  (mTileStatus == TileStatus.CHARGING_STATION?"ChargeStation":"Tile") + " X "+mx+" Y "+my;
    }
}
