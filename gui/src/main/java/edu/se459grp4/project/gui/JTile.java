
package edu.se459grp4.project.gui;
import edu.se459grp4.project.simulator.models.*; 
import edu.se459grp4.project.simulator.types.TileStatus;
import java.awt.Color;
import java.awt.Graphics;

public class JTile {
    private Tile mTile;
    
    public JTile(Tile nTile)
    {
        mTile = nTile;
    }
    public void Draw(Graphics g,int nTileSize)
    { 
        g.setColor(Color.GRAY);
        g.drawRect(mTile.GetX()*nTileSize, mTile.GetY()*nTileSize, nTileSize, nTileSize);
        if(mTile.GetStatus() == TileStatus.BARE_FLOOR)
            g.setColor(Color.BLUE);
        else if(mTile.GetStatus() == TileStatus.LOW_CARPET)
            g.setColor(Color.LIGHT_GRAY);
        else if(mTile.GetStatus() == TileStatus.HIGH_CARPET)
            g.setColor(Color.GRAY);
        else if(mTile.GetStatus() == TileStatus.CHARGING_STATION)
            g.setColor(Color.YELLOW);
        else if(mTile.GetStatus() == TileStatus.STAIRS)
            g.setColor(Color.darkGray);
      
        g.fillRect(mTile.GetX()*nTileSize-1, mTile.GetY()*nTileSize-1, nTileSize-2, nTileSize-2);
        
        if(mTile.GetStatus() != TileStatus.CHARGING_STATION &&
            mTile.GetStatus() != TileStatus.STAIRS    )
        {
            g.setColor(Color.RED);
            g.drawString(""+mTile.GetDirtVal(), mTile.GetX()*nTileSize + nTileSize/2 -5, mTile.GetY()*nTileSize + nTileSize/2 -5);
        }
        return;
    }
}
