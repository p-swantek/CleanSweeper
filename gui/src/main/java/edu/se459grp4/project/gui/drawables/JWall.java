
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
        List<Door> lListDoor = mWall.GetAllDoors();
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
        boolean lbVer = mWall.GetVertical();
        int lnBase = mWall.GetBase() +1;
        int lnFrom = mWall.GetFrom() ;
        int lnTo = mWall.GetTo();
        int x = lbVer == false ? lnFrom*nTileSize : lnBase*nTileSize - (nTileSize/5)/2;
        int y = lbVer == false ? lnBase*nTileSize- (nTileSize/5)/2 : lnFrom*nTileSize;
        int lLen =  lbVer == false ? ((Math.abs(lnFrom-lnTo)) + 1)*nTileSize : nTileSize/5;
        int lWid = lbVer == false? nTileSize/5: (Math.abs(lnFrom-lnTo) + 1)*nTileSize;
        g.fillRect(x, y, lLen, lWid);
        g.setColor(Color.YELLOW);
        
        //draw doors
        for(Drawable item : mJDoors){
        	item.draw(g, nTileSize);
        }
        //g.fillRect(nTileSize, nTileSize, nTileSize, nTileSize);
       // g.drawString(""+mTile.GetDirtVal(), mTile.GetX()*nTileSize + nTileSize/2 -5, mTile.GetY()*nTileSize + nTileSize/2 -5);
    }
}
