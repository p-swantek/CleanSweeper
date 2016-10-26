package edu.se459grp4.project.simulator.types;

import java.util.HashMap;
import java.util.Map;

public enum Border {
    OPEN(0), WALL(1), OPEN_DOOR(2), CLOSED_DOOR(3);

    private int borderNum;

    private static Map<Integer, Border> borderMap = new HashMap<Integer, Border>();

    static {
        for (Border borderEnum : Border.values()) {
            borderMap.put(borderEnum.borderNum, borderEnum);
        }
    }

    private Border(int border) { borderNum = border; }

    public static Border valueOf(int borderNum) {
        return borderMap.get(borderNum);
    }
}
