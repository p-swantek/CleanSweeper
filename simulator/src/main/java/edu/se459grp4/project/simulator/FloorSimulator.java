package edu.se459grp4.project.simulator;

import edu.se459grp4.project.cleansweep.CleanSweep;
import edu.se459grp4.project.simulator.models.FloorTile;
import edu.se459grp4.project.simulator.util.FloorPlanReader;

public class FloorSimulator {
    FloorTile[][] floorTiles;

    public static void main(String[] args) {
        System.out.println("HELLO!");
        CleanSweep cleanSweep = new CleanSweep();
    }

    public void loadFloorPlan(String fileLocation) {
        FloorPlanReader floorPlanReader = new FloorPlanReader(fileLocation);
        floorTiles = floorPlanReader.getTileElements();
    }

    public FloorTile[][] getFloorTiles() {
        return null;
    }

    public FloorTile getFloorTile(int x, int y) {
        return null;
    }


}
