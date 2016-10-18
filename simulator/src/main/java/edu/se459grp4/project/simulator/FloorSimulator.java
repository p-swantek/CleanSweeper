package edu.se459grp4.project.simulator;

import edu.se459grp4.project.simulator.gui.SimulatorGUI;
import edu.se459grp4.project.simulator.models.FloorTile;
import edu.se459grp4.project.simulator.util.FloorPlanReader;

import java.awt.*;
import java.io.*;

public class FloorSimulator {
    static final String DEFAULT_FLOOR_PLAN = "/sample-floor-plan.csv";
    FloorTile[][] floorTiles;
    Point cleanSweepPosition;

    public FloorSimulator(int cleanSweepX, int cleanSweepY, String floorPlanLocation) {
        cleanSweepPosition = new Point(cleanSweepX, cleanSweepY);
        loadFloorPlan(floorPlanLocation);
    }

    public void loadFloorPlan(String fileLocation) {
        InputStream inputStream = null;
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;

        // Use default file in "resources" directory if no file location provided
        if(fileLocation == null) {
            inputStream = getClass().getResourceAsStream(FloorSimulator.DEFAULT_FLOOR_PLAN);
            inputStreamReader = new InputStreamReader(inputStream);
        }
        else {
            try {
                fileInputStream = new FileInputStream(fileLocation);
            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
            }
            inputStreamReader = new InputStreamReader(fileInputStream) ;
        }

        FloorPlanReader floorPlanReader = new FloorPlanReader(inputStreamReader);
        this.floorTiles = floorPlanReader.getTileElements();
    }

    public FloorTile[][] getFloorTiles() {
        return floorTiles;
    }

    public FloorTile getFloorTile(int x, int y) {
        return floorTiles[x][y];
    }

    public FloorTile getCleanSweepFloorTile() {
        int cleanSweepX = cleanSweepPosition.getLocation().x;
        int cleanSweepY = cleanSweepPosition.getLocation().y;
        return floorTiles[cleanSweepX][cleanSweepY];
    }

    // To use custom floor plan pass file path as program argument
    public static void main(String[] args) {
        String fileLocation = null;
        if(args.length > 0) {
            fileLocation = args[0];
        }
        FloorSimulator floorSimulator = new FloorSimulator(0, 1, fileLocation);

        SimulatorGUI gui = new SimulatorGUI(floorSimulator.getFloorTiles());
        gui.start();

    }

}
