
package edu.se459grp4.project.gui.drawables;
import edu.se459grp4.project.simulator.models.*; 
import edu.se459grp4.project.simulator.types.TileStatus;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Graphical representation of a floor tile on the gui. Floor tiles can be bare floor, low pile carpet,
 * and high pile carpet. Tiles can also represent either a staircase or a clean sweep charging station
 * 
 * @author Group 4
 *
 */
class JTile implements Drawable{
	
    private Tile mTile;
    
    /**
     * Construct a graphical representation of a floor tile
     * 
     * @param nTile the floor tile to draw
     */
    public JTile(Tile nTile){
        mTile = nTile;
    }
    
    @Override
    public void draw(Graphics g,int nTileSize){ 
        g.setColor(Color.GRAY);
        g.drawRect(mTile.getX()*nTileSize, mTile.getY()*nTileSize, nTileSize, nTileSize);
        if(mTile.getSurfaceType() == TileStatus.BARE_FLOOR)
            g.setColor(Color.BLUE);
        else if(mTile.getSurfaceType() == TileStatus.LOW_CARPET)
            g.setColor(Color.LIGHT_GRAY);
        else if(mTile.getSurfaceType() == TileStatus.HIGH_CARPET)
            g.setColor(Color.GRAY);
        else if(mTile.getSurfaceType() == TileStatus.CHARGING_STATION)
            g.setColor(Color.YELLOW);
        else if(mTile.getSurfaceType() == TileStatus.STAIRS)
            g.setColor(Color.darkGray);
      
        g.fillRect(mTile.getX()*nTileSize-1, mTile.getY()*nTileSize-1, nTileSize-2, nTileSize-2);
        
        if(mTile.getSurfaceType() != TileStatus.CHARGING_STATION &&
            mTile.getSurfaceType() != TileStatus.STAIRS    )
        {
            g.setColor(Color.RED);
            g.drawString(""+mTile.getDirtAmount(), mTile.getX()*nTileSize + nTileSize/2 -5, mTile.getY()*nTileSize + nTileSize/2 -5);
        }
        return;
    }
}
