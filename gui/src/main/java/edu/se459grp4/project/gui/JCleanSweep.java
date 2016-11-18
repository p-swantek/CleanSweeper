
package edu.se459grp4.project.gui;

import edu.se459grp4.project.cleansweep.CleanSweep;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Graphical representation of the clean sweep on the gui
 * 
 * @author Group 4
 * @version 1.8
 *
 */
public class JCleanSweep {
    private CleanSweep mCleanSweep;
    
    /**
     * Constructs a graphical representation of a clean sweep
     * 
     * @param nCleanSweep the clean sweep object to draw
     */
    public JCleanSweep(CleanSweep nCleanSweep)
    {
        mCleanSweep = nCleanSweep;
    }
    
    /**
     * Draws the clean sweep on the gui
     * 
     * @param g the graphics on which to draw the clean sweep
     * @param nTileSize the total size of a tile on the gui
     */
    public void Draw(Graphics g,int nTileSize)
    {
        if(mCleanSweep == null)
            return;
       
        g.setColor(Color.ORANGE);
        int lnX = mCleanSweep.getX();
        int lnY = mCleanSweep.getY();
     
        int x = lnX*nTileSize;
        int y = lnY*nTileSize ;
        g.fillOval(x+nTileSize/4, y+nTileSize/4, nTileSize/2, nTileSize/2);
        //g.fillRect(nTileSize, nTileSize, nTileSize, nTileSize);
        g.drawString(""+mCleanSweep.getID() +" P"+mCleanSweep.getCurrPower() + " V"+mCleanSweep.getCurrVacuumCapacity(),
                x+nTileSize/4 + 5 , 
                y+nTileSize/4 + 5);
        
        return;
    }
}
