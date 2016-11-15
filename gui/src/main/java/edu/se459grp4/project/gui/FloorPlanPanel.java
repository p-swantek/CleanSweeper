package edu.se459grp4.project.gui;

import edu.se459grp4.project.cleansweep.CleanSweep;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import edu.se459grp4.project.simulator.models.*;
import java.util.*;
class FloorPlanPanel extends JPanel implements Observer{

    private FloorPlan mFloorPlan;
    private int mnSqureTileSize;
    private int mnSqureTilesNum;
    private List<JTile> mJTiles = new ArrayList<JTile>();
    private List<JWall> mJWalls = new ArrayList<JWall>();
    private List<JCleanSweep> mJCleanSweeps = new ArrayList<JCleanSweep>();
    
    public boolean SetFloorPlan(FloorPlan nFloorplan)
    {
        if(nFloorplan == null)
            return false;
        mFloorPlan = nFloorplan;
        mFloorPlan.addObserver(this);
        mJTiles.clear();
        //add the tile
        List<Tile> lListTile = mFloorPlan.GetAllTiles();
        for(Tile item : lListTile)
           mJTiles.add(new JTile(item));
        //add the wall
        List<Wall> lListWall = mFloorPlan.GetAllWalls();
        for(Wall item : lListWall)
           mJWalls.add(new JWall(item));
        
        this.repaint();
        return true;
    }
    public boolean AddCleanSweep(CleanSweep nCleanSweep)
    {
        if(nCleanSweep == null)
            return false;
        mJCleanSweeps.add(new JCleanSweep(nCleanSweep));
        nCleanSweep.addObserver(this);
        this.updateUI();
        return true;
        
    }
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.blue);
        if(mFloorPlan == null)
            return;
        
       //draw floor plan background
       Dimension lDim = this.getSize();
       int nSqureSize = Math.min(lDim.height, lDim.width);
       mnSqureTilesNum = mFloorPlan.GetTilesSquareNum();
       mnSqureTileSize = nSqureSize/mnSqureTilesNum;
      
       //draw tiles
       for(JTile item:mJTiles)
           item.Draw(g,mnSqureTileSize);
       //draw walls
        for(JWall item:mJWalls)
           item.Draw(g,mnSqureTileSize);
       //draw sweepcleans
        for(JCleanSweep item:mJCleanSweeps)
           item.Draw(g,mnSqureTileSize);
      
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
    
    public void update(Observable o, Object arg) {
   
        //need to set the clip rect to improve the perfomance
        //get the invalidate rect, then just update this rect to improve the performance of drawing
        this.repaint();
    }
}
