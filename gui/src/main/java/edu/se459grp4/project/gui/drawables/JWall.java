
package edu.se459grp4.project.gui.drawables;

import edu.se459grp4.project.simulator.models.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Graphical representation of a wall in the gui
 * 
 * @author Peter Swantek
 * @version 1.8
 */
class JWall implements Drawable{
	
    private Wall mWall;
    private List<Drawable> mJDoors = new ArrayList<>();
    
    /**
     * Construct a graphical representation of a wall
     * 
     * @param nWall the wall object to draw
     */
    public JWall(Wall nWall){
        mWall = nWall;
        //add the door
        List<Door> lListDoor = mWall.getAllDoors();
        for(Door door : lListDoor){
           mJDoors.add(DrawableFactory.makeDoor(door));
        }
    }
    
    @Override
    public void draw(Graphics g,int nTileSize){
        if(mWall == null){
            return;
        }
        
        g.setColor(Color.YELLOW);
        boolean isVertical = mWall.isVertical();
        int lnBase = mWall.getBase()+1;
        int lnFrom = mWall.getFrom();
        int lnTo = mWall.getTo();
        int x = !isVertical ? lnFrom*nTileSize : lnBase*nTileSize - (nTileSize/5)/2;
        int y = !isVertical ? lnBase*nTileSize- (nTileSize/5)/2 : lnFrom*nTileSize;
        int lLen =  !isVertical ? ((Math.abs(lnFrom-lnTo)) + 1)*nTileSize : nTileSize/5;
        int lWid = !isVertical ? nTileSize/5 : (Math.abs(lnFrom-lnTo) + 1)*nTileSize;
        g.fillRect(x, y, lLen, lWid);
        g.setColor(Color.YELLOW);
        
        //draw doors
        for(Drawable item : mJDoors){
        	item.draw(g, nTileSize);
        }
    }
}
