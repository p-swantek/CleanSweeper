package edu.se459grp4.project.cleansweep.types;

import java.util.HashMap;
import java.util.Map;

//define the direction that this sweep can move
public enum Direction {
    UP("UP"), LEFT("LEFT"), DOWN("DOWN"), RIGHT("RIGHT");

    private String value;

    private Direction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
