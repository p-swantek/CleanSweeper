package edu.se459grp4.project.gui;

import edu.se459grp4.project.cleansweep.CleanSweep;
import edu.se459grp4.project.gui.drawables.Drawable;
import edu.se459grp4.project.gui.drawables.JCleanSweep;
import edu.se459grp4.project.gui.drawables.JTile;
import edu.se459grp4.project.gui.drawables.JWall;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import edu.se459grp4.project.simulator.models.*;
import java.util.*;

/**
 * Main graphical panel that holds the drawing of the floor plan that was loaded into the simulator.  The floor plan
 * graphic consists of floor tiles, walls, doors, staircase sections, and charging stations that the sweeper visits to
 * recharge and empty the vacuum.
 * 
 * @author Group 4
 * @version 1.8
 */
class FloorPlanPanel extends JPanel implements Observer{

    private FloorPlan mFloorPlan;
    private int mnSqureTileSize;
    private int mnSqureTilesNum;
//    private List<JTile> mJTiles = new ArrayList<>();
//    private List<JWall> mJWalls = new ArrayList<>();
//    private List<JCleanSweep> mJCleanSweeps = new ArrayList<>();
    
    private List<Drawable> guiElements = new ArrayList<>();

    
    /**
     * Sets the floorplan that the gui will will display
     * 
     * @param nFloorplan the floorplan to draw on the gui
     * @return true if the floorplan was successfully set, false otherwise
     * @see FloorPlan
     */
    public boolean setFloorPlan(FloorPlan nFloorplan){
        if(nFloorplan == null){
            return false;
        }
        
        mFloorPlan = nFloorplan;
        mFloorPlan.addObserver(this);
        guiElements.clear();
        

        //add the tile
        List<Tile> lListTile = mFloorPlan.GetAllTiles();
        for(Tile tile : lListTile){
        	guiElements.add(new JTile(tile));
        }
        
        //add the wall
        List<Wall> lListWall = mFloorPlan.GetAllWalls();
        for(Wall wall : lListWall){
        	guiElements.add(new JWall(wall));
        }
        
        this.repaint();
        return true;
    }
    
    /**
     * Adds a cleansweep in the simulation to the drawing of the
     * floorplan
     * 
     * @param nCleanSweep the CleanSweep robot to draw on the floorplan
     * @return true if the clean sweep was successfully drawn, false otherwise
     */
    public boolean addCleanSweep(CleanSweep nCleanSweep){
        if(nCleanSweep == null){
            return false;
        }
        
        guiElements.add(new JCleanSweep(nCleanSweep));
        nCleanSweep.addObserver(this);
        this.updateUI();
        return true;
        
    }
    
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.blue);
        if(mFloorPlan == null){
            return;
        }
        
       //draw floor plan background
       Dimension lDim = this.getSize();
       int nSqureSize = Math.min(lDim.height, lDim.width);
       mnSqureTilesNum = mFloorPlan.GetTilesSquareNum();
       mnSqureTileSize = nSqureSize/mnSqureTilesNum;
      
       //draw tiles
//       for (JTile item : mJTiles){
//           item.draw(g, mnSqureTileSize);
//       }
//       
//       //draw walls
//        for (JWall item : mJWalls){
//           item.draw(g, mnSqureTileSize);
//        }
//        
//       //draw sweepcleans
//        for (JCleanSweep item : mJCleanSweeps){
//           item.draw(g, mnSqureTileSize);
//        }
       
       for (Drawable item : guiElements){
    	   item.draw(g, mnSqureTileSize);
       }
      
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }
    
    @Override
    public void update(Observable o, Object arg){
        //need to set the clip rect to improve the perfomance
        //get the invalidate rect, then just update this rect to improve the performance of drawing
        this.repaint();
    }
}
