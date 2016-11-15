
package edu.se459grp4.project.gui;
import edu.se459grp4.project.simulator.models.*;
import edu.se459grp4.project.simulator.types.TileStatus;
import java.awt.Color;
import java.awt.Graphics;

public class JDoor {
    private Door mDoor;
    
    public JDoor(Door nDoor)
    {
        mDoor = nDoor;
    }
    public void Draw(Graphics g,int nTileSize)
    {
        if(mDoor == null)
            return;
       
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
