package edu.se459grp4.project.simulator.util;

import edu.se459grp4.project.simulator.models.FloorTile;

import java.util.ArrayList;
import java.util.List;

public class FloorPlanReader {

    public List<List<String>> tileElements;

    public FloorPlanReader(String fileLocation) {
        CSVReader csvReader = new CSVReader(fileLocation);
        tileElements = csvReader.readElements();
    }

    public FloorTile[][] getTileElements() {
        return null;
    }
}
