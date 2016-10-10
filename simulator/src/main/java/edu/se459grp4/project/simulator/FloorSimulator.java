package edu.se459grp4.project.simulator;

import edu.se459grp4.project.cleansweep.CleanSweep;
import edu.se459grp4.project.simulator.gui.SimulatorGUI;
import edu.se459grp4.project.simulator.models.FloorTile;
import edu.se459grp4.project.simulator.types.Border;
import edu.se459grp4.project.simulator.types.Tile;
import edu.se459grp4.project.simulator.util.FloorPlanReader;

import java.awt.*;

public class FloorSimulator {
    FloorTile[][] floorTiles;
    Point cleanSweepPosition;

    public FloorSimulator(int cleanSweepX, int cleanSweepY, String floorPlanLocation) {
        cleanSweepPosition = new Point(cleanSweepX, cleanSweepY);
        loadFloorPlan(floorPlanLocation);
    }

    public void loadFloorPlan(String fileLocation) {
        FloorPlanReader floorPlanReader = new FloorPlanReader(fileLocation);
        floorTiles = floorPlanReader.getTileElements();
    }

    public FloorTile[][] getFloorTiles() {
        FloorTile testX00 = new FloorTile(null, Border.WALL, Border.WALL, null, Tile.BARE_FLOOR, 5);
        FloorTile testX10 = new FloorTile(null, Border.WALL, null, null, Tile.HIGH_CARPET, 5);
        FloorTile testX20 = new FloorTile(null, Border.WALL, null, Border.WALL, Tile.LOW_CARPET, 5);

        FloorTile testX01 = new FloorTile(Border.WALL, null, Border.WALL, null, Tile.CHARGING_STATION, 5);
        FloorTile testX11 = new FloorTile(null, null, null, null, Tile.STAIRS, 5);
        FloorTile testX21 = new FloorTile(Border.WALL, null, null, Border.WALL, Tile.BARE_FLOOR, 5);

        FloorTile[] col0 = {testX00, testX01};
        FloorTile[] col1 = {testX10, testX11};
        FloorTile[] col2 = {testX20, testX21};
        FloorTile[][] tiles = {col0, col1, col2};
        return tiles;
    }

    public FloorTile getFloorTile(int x, int y) {
        return null;
    }

    public FloorTile getCleanSweepFloorTile() {
        return null;
    }

    public static void main(String[] args) {
        CleanSweep cleanSweep = new CleanSweep();
        FloorSimulator floorSimulator = new FloorSimulator(0,0, null);
        SimulatorGUI gui = new SimulatorGUI(floorSimulator.getFloorTiles());
        gui.start();
    }

}
