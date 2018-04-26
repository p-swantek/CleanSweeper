package edu.se459grp4.project.cleansweep;

import static org.junit.Assert.*;


import org.junit.BeforeClass;
import org.junit.Test;

import edu.se459grp4.project.simulator.Simulator;
import edu.se459grp4.project.simulator.types.Direction;
import edu.se459grp4.project.simulator.types.PathStatus;
import edu.se459grp4.project.simulator.types.SurfaceType;

public class CleanSweepTest {
	private static CleanSweep cleaner;
	@BeforeClass
	public static void setUpBeforeClass(){
		Simulator.getInstance().loadFloorPlan("../simulator/example.flr");
		cleaner = new CleanSweep(1, 100.00, 100, 0, 6);
	}
	
	
	@Test
	public void testCheckMove() {
		PathStatus check = cleaner.checkAbleToMove(Direction.UP);
		assertEquals(PathStatus.STAIR, check);
		check = cleaner.checkAbleToMove(Direction.RIGHT);
		assertEquals(PathStatus.OPEN, check);
		check = cleaner.checkAbleToMove(Direction.LEFT);
		assertEquals(PathStatus.BLOCKED, check);
		check = cleaner.checkAbleToMove(Direction.DOWN);
		assertEquals(PathStatus.OPEN, check);
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
		SurfaceType t=cleaner.senseFloorSurface();
		assertEquals(t,SurfaceType.BARE_FLOOR);
		assertNotEquals(t, SurfaceType.CHARGING_STATION);
		assertNotEquals(t, SurfaceType.HIGH_CARPET);
		assertNotEquals(t, SurfaceType.LOW_CARPET);
		assertNotEquals(t, SurfaceType.STAIRS);
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
	
	@Test
	public void testToString() {
		CleanSweep cleaner = new CleanSweep(1, 100.00, 100, 0, 6);
		assertEquals("CleanSweep[ID: 1]\nPower Status --> 100.00/100.00\nVacuum Status --> 100/100", cleaner.toString());
	}

}
