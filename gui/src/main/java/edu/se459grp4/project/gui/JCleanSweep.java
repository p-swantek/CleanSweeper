
package edu.se459grp4.project.gui;

import edu.se459grp4.project.cleansweep.CleanSweep;
import java.awt.Color;
import java.awt.Graphics;


public class JCleanSweep {
    private CleanSweep mCleanSweep;
    
    public JCleanSweep(CleanSweep nCleanSweep)
    {
        mCleanSweep = nCleanSweep;
    }
    public void Draw(Graphics g,int nTileSize)
    {
        if(mCleanSweep == null)
            return;
       
        g.setColor(Color.ORANGE);
        int lnX = mCleanSweep.GetX();
        int lnY = mCleanSweep.GetY();
     
        int x = lnX*nTileSize;
        int y = lnY*nTileSize ;
        g.fillOval(x+nTileSize/4, y+nTileSize/4, nTileSize/2, nTileSize/2);
        //g.fillRect(nTileSize, nTileSize, nTileSize, nTileSize);
        g.drawString(""+mCleanSweep.GetID() +" P"+mCleanSweep.GetPowerLevel() + " V"+mCleanSweep.GetVacuumLevel(),
                x+nTileSize/4 + 5 , 
                y+nTileSize/4 + 5);
        
        return;
    }
}
