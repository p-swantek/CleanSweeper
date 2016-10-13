package edu.se459grp4.project.simulator.types;

import java.util.HashMap;
import java.util.Map;

public enum Tile {
    BARE_FLOOR(1), LOW_CARPET(2), HIGH_CARPET(3), STAIRS(4), CHARGING_STATION(5);

    private int tileNum;

    private static Map<Integer, Tile> tileMap = new HashMap<Integer, Tile>();

    static {
        for (Tile tileEnum : Tile.values()) {
            tileMap.put(tileEnum.tileNum, tileEnum);
        }
    }

    private Tile(int tile) { tileNum = tile; }

    public static Tile valueOf(int tileNum) {
        return tileMap.get(tileNum);
    }
}
