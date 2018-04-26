package edu.se459grp4.project.gui.drawables;

import edu.se459grp4.project.simulator.models.*;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Graphical representation of a door on the gui
 * 
 * @author Group 4
 * @version 1.8
 */
class JDoor implements Drawable{
	
    private Door mDoor;
    
    /**
     * Construct a graphical representation of a door
     * 
     * @param nDoor the door object to draw
     */
    public JDoor(Door nDoor){
        mDoor = nDoor;
    }
    
    @Override
    public void draw(Graphics g, int nTileSize){
        if(mDoor == null){
            return;
        }
       
        boolean isVertical = mDoor.isVertical();
        int lnBase = mDoor.getBase() +1;
        int lnFrom = mDoor.getFrom() ;
        int lnTo = mDoor.getTo() ;
        boolean isOpen = mDoor.isOpen(); 
        
        if(!isOpen){
            g.setColor(Color.RED);
        }
        else{
        	g.setColor(Color.GREEN);
        }
        
        int lOffset = lnFrom == lnTo ? 0 : nTileSize/2;
        int x = !isVertical ? lnFrom*nTileSize+lOffset : lnBase*nTileSize-(nTileSize/5)/2;
        int y = !isVertical ? lnBase*nTileSize-(nTileSize/5)/2 : lnFrom*nTileSize+lOffset;
        int lLen =  !isVertical ? nTileSize : nTileSize/5;
        int lWid = !isVertical ? nTileSize/5 : nTileSize;
        g.fillRect(x, y, lLen, lWid);
    }
}
