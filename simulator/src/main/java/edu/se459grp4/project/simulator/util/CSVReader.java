package edu.se459grp4.project.simulator.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVReader {
    private InputStreamReader inputStreamReader;

    public CSVReader(InputStreamReader inputStreamReader) {
        this.inputStreamReader = inputStreamReader;
    }

    public ArrayList<ArrayList<String>> readElements() {
        ArrayList<ArrayList<String>> elements = new ArrayList<ArrayList<String>>();
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                elements.add(readLineElements(line));
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return elements;
    }

    private ArrayList<String> readLineElements(String csvLine) {
        ArrayList<String> lineElements = new ArrayList<>();
        lineElements.addAll(Arrays.asList(csvLine.split("\\s*(,\\s*)+")));
        return lineElements;
    }

}
