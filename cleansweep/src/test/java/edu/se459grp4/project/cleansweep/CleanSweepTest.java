package edu.se459grp4.project.cleansweep;

import static org.junit.Assert.*;


import org.junit.BeforeClass;
import org.junit.Test;

import edu.se459grp4.project.simulator.Simulator;
import edu.se459grp4.project.simulator.types.Direction;
import edu.se459grp4.project.simulator.types.PathStatus;
import edu.se459grp4.project.simulator.types.TileStatus;

public class CleanSweepTest {
	private static CleanSweep cleaner;
	@BeforeClass
	public static void setUpBeforeClass(){
		Simulator.getInstance().loadFloorPlan("../simulator/example.flr");
		cleaner = new CleanSweep(1, 100.00, 100, 0, 6);
	}
	
	
	@Test
	public void testCheckMove() {
		PathStatus check = cleaner.checkAbleToMove(Direction.Up);
		assertEquals(PathStatus.Stair, check);
		check = cleaner.checkAbleToMove(Direction.Right);
		assertEquals(PathStatus.Open, check);
		check = cleaner.checkAbleToMove(Direction.Left);
		assertEquals(PathStatus.Blocked, check);
		check = cleaner.checkAbleToMove(Direction.Down);
		assertEquals(PathStatus.Open, check);
		check =cleaner.checkAbleToMove(null);
		assertEquals(PathStatus.UNKNOWN,check);
	}

	@Test
	public void testMoveTo() {
		assertTrue(cleaner.moveToLoc(0, 7));
		assertTrue(cleaner.moveToLoc(1, 6));
		cleaner.moveToLoc(0, 6);
		assertFalse(cleaner.moveToLoc(0, 5));
		CleanSweep c= new CleanSweep(1,100.00,100,4,4);
		assertFalse(c.moveToLoc(5, 4));
	}

	@Test
	public void testDetectSurfaceType() {
		TileStatus t=cleaner.senseFloorSurface();
		assertEquals(t,TileStatus.BARE_FLOOR);
		assertNotEquals(t, TileStatus.CHARGING_STATION);
		assertNotEquals(t, TileStatus.HIGH_CARPET);
		assertNotEquals(t, TileStatus.LOW_CARPET);
		assertNotEquals(t, TileStatus.STAIRS);
	}

	@Test
	public void testDetectDirtValue() {
		int dirt = cleaner.senseDirtAmount();
		assertEquals(dirt,50);
	}

	@Test
	public void testSweepUp() {
		int dirt = cleaner.senseDirtAmount();
		assertEquals(dirt,50);
		cleaner.cleanDirt(30);
		dirt=cleaner.senseDirtAmount();
		assertEquals(dirt,20);
		
	}

	@Test
	public void testExhaustPower() {
		double currentPower = cleaner.getCurrPower();
		assertEquals(currentPower,100.000,0.001);
		cleaner.usePowerAmount(10.000);
		currentPower = cleaner.getCurrPower();
		assertEquals(currentPower,90.000,0.001);
	}

	@Test
	public void testExhaustVacuume() {
		int capacity = cleaner.getCurrVacuumCapacity();
		assertEquals(capacity,100);
		int newValue =cleaner.fillUpVacuum(20);
		assertEquals(newValue,80);
	}

	@Test
	public void testCleanVacuum() {
		cleaner.emptyVacuum();
		int value =cleaner.getCurrVacuumCapacity();
		assertEquals(value,100);
	}

	@Test
	public void testRecharge() {
		cleaner.rechargePower();
		double power=cleaner.getCurrPower();
		assertEquals(power,100,0.001);
	}

}
