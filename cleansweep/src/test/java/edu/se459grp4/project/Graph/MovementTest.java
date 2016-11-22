package edu.se459grp4.project.Graph;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.se459grp4.project.cleansweep.CleanSweep;
import edu.se459grp4.project.cleansweep.Navigator;
import edu.se459grp4.project.simulator.Simulator;
import edu.se459grp4.project.simulator.types.TileStatus;

public class MovementTest {
	
	private static CleanSweep cs;
	private static Navigator nav;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Simulator.getInstance().loadFloorPlan("../simulator/example.flr");
		cs = new CleanSweep(1, 100.0, 10000, 0, 9);
		nav = new Navigator(cs);
		nav.run(); //manually execute the cleaning cycle
	}

	
	/*
	 * after the clean sweep has fully cleaned the floor, check that all the tiles of the floor actually got cleaned,
	 * all tiles besides charging stations and stairs should have a dirt value of 0 after cleaning
	 */
	@Test
	public void testVisit() {
		
		//loop over all (x,y) coordinates from example.flr, check that no tiles have dirt
		for (int x = 0; x < 10; x++){
			for (int y = 0; y < 10; y++){
				if (Simulator.getInstance().getSurfaceData(x, y) != TileStatus.CHARGING_STATION 
						&& Simulator.getInstance().getSurfaceData(x, y) != TileStatus.STAIRS){
					assertEquals(0, Simulator.getInstance().getDirtData(x, y));
				}
			}
			
		}
	
	}

}
