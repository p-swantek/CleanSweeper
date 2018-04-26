package edu.se459grp4.project.simulator.types;

import java.util.HashMap;
import java.util.Map;


/**
 * Data type representing the type of floor a certain floor tile is.  It may either be bare floor, 
 * low pile carpet, high pile carpet, a staircase, or a charging station for the clean sweep.
 * 
 * @author Group 4
 * @version 1.8
 *
 */
public enum SurfaceType {
    BARE_FLOOR(1), LOW_CARPET(2), HIGH_CARPET(3), STAIRS(4), CHARGING_STATION(5);

    private int tileNum;

    private static final Map<Integer, SurfaceType> tileMap = new HashMap<>();

    static {
        for (SurfaceType tileEnum : SurfaceType.values()) {
            tileMap.put(tileEnum.tileNum, tileEnum);
        }
    }

    private SurfaceType(int tile){ 
    	tileNum = tile; 
    }

    public static SurfaceType valueOf(int tileNum) {
        return tileMap.get(tileNum);
    }
    
    public static double weight(SurfaceType type){
        switch(type){
            case BARE_FLOOR: 
            	return 1f;
            	
            case LOW_CARPET: 
            	return 1.5f;
            	
            case HIGH_CARPET:
            	return 2f;
            	
            case CHARGING_STATION: 
            	return 1f;
            	
            default: 
            	return 0.0f;
        }
    
    }
}
