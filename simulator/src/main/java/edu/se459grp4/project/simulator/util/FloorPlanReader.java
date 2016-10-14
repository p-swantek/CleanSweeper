package edu.se459grp4.project.simulator.util;

import edu.se459grp4.project.simulator.models.FloorTile;
import edu.se459grp4.project.simulator.types.Border;
import edu.se459grp4.project.simulator.types.Tile;

import java.io.InputStreamReader;
import java.util.ArrayList;

public class FloorPlanReader {
    private InputStreamReader inputStreamReader;

    public FloorPlanReader(InputStreamReader inputStreamReader) {
        this.inputStreamReader = inputStreamReader;
    }

    public FloorTile[][] getTileElements() {
        CSVReader csvReader = new CSVReader(inputStreamReader);
        ArrayList<ArrayList<String>> csvElements = csvReader.readElements();
        String[][] tileTextGrid = listOfListsToGrid(csvElements);
        FloorTile[][] floorTiles = gridToFloorTiles(tileTextGrid);
        return floorTiles;
    }

    private String[][] listOfListsToGrid(ArrayList<ArrayList<String>> listOfLists) {
        int ySize = listOfLists.size();
        int xSize = listOfLists.get(0).size();
        String[][] grid = new String[xSize][ySize];

        int invertY = 0;
        for(int y = ySize - 1; y >= 0; y--) {
            for(int x = 0; x < xSize; x++) {
                grid[invertY][x] = listOfLists.get(y).get(x);
            }
            invertY++;
        }
        return grid;
    }

    private FloorTile[][] gridToFloorTiles(String[][] tileTextGrid) {
        int xLength = tileTextGrid.length;
        int yLength = tileTextGrid[0].length;
        FloorTile[][] floorTiles = new FloorTile[xLength][yLength];

        for(int x = 0; x < xLength; x++) {
            for(int y = 0; y < yLength; y++) {
                floorTiles[y][x] = generateFloorTile(tileTextGrid[x][y]);
            }
        }
        return floorTiles;
    }

    private FloorTile generateFloorTile(String tileText) {
        if(tileText == null) {
            return null;
        }

        Border northBorder = null;
        Border southBorder = null;
        Border westBorder = null;
        Border eastBorder = null;
        Tile tileType = null;
        int dirtAmount = 0;

        // CSV tile element format: N0S1W0E0T1D10
        int northType = Integer.parseInt(Character.toString(tileText.charAt(1)));
        int southType = Integer.parseInt(Character.toString(tileText.charAt(3)));
        int westType = Integer.parseInt(Character.toString(tileText.charAt(5)));
        int eastType = Integer.parseInt(Character.toString(tileText.charAt(7)));
        int tileNumType = Integer.parseInt(Character.toString(tileText.charAt(9)));
        dirtAmount = Integer.parseInt(tileText.substring(11, tileText.length()));

        northBorder = Border.valueOf(northType);
        southBorder = Border.valueOf(southType);
        westBorder = Border.valueOf(westType);
        eastBorder = Border.valueOf(eastType);
        tileType = Tile.valueOf(tileNumType);

        Character.toString(tileText.charAt(0));

        return new FloorTile(northBorder, southBorder, westBorder, eastBorder,
                tileType, dirtAmount);
    }

}
