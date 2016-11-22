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
public class JDoor implements Drawable{
	
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
    public void draw(Graphics g,int nTileSize){
        if(mDoor == null){
            return;
        }
       
        boolean lbVer = mDoor.GetVertical();
        int lnBase = mDoor.GetBase() +1;
        int lnFrom = mDoor.GetFrom() ;
        int lnTo = mDoor.GetTo() ;
        boolean lbOpen = mDoor.GetIsOpened(); 
        if(lbOpen == false)
            g.setColor(Color.RED);
        else g.setColor(Color.GREEN);
        int lOffset = lnFrom == lnTo ? 0 : nTileSize/2;
        int x = lbVer == false ? lnFrom*nTileSize+lOffset : lnBase*nTileSize- (nTileSize/5)/2;
        int y = lbVer == false ? lnBase*nTileSize- (nTileSize/5)/2 : lnFrom*nTileSize+lOffset ;
        int lLen =  lbVer == false ? nTileSize : nTileSize/5;
        int lWid = lbVer == false? nTileSize/5: nTileSize;
        g.fillRect(x, y, lLen, lWid);
        //g.fillRect(nTileSize, nTileSize, nTileSize, nTileSize);
       // g.drawString(""+mTile.GetDirtVal(), mTile.GetX()*nTileSize + nTileSize/2 -5, mTile.GetY()*nTileSize + nTileSize/2 -5);
        return;
    }
}
