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
		PathStatus check = cleaner.CheckMove(Direction.Up);
		assertEquals(PathStatus.Stair, check);
		check = cleaner.CheckMove(Direction.Right);
		assertEquals(PathStatus.Open, check);
		check = cleaner.CheckMove(Direction.Left);
		assertEquals(PathStatus.Blocked, check);
		check = cleaner.CheckMove(Direction.Down);
		assertEquals(PathStatus.Open, check);
		check =cleaner.CheckMove(null);
		assertEquals(PathStatus.UNKNOWN,check);
	}

	@Test
	public void testMoveTo() {
		assertFalse(cleaner.MoveTo(0, 5));
		assertTrue(cleaner.MoveTo(0, 7));
	}

	@Test
	public void testDetectSurfaceType() {
		TileStatus t=cleaner.DetectSurfaceType();
		assertEquals(t,TileStatus.BARE_FLOOR);
		assertNotEquals(t, TileStatus.CHARGING_STATION);
		assertNotEquals(t, TileStatus.HIGH_CARPET);
		assertNotEquals(t, TileStatus.LOW_CARPET);
		assertNotEquals(t, TileStatus.STAIRS);
	}

	@Test
	public void testDetectDirtValue() {
		int dirt = cleaner.DetectDirtValue();
		assertEquals(dirt,50);
	}

	@Test
	public void testSweepUp() {
		int dirt = cleaner.DetectDirtValue();
		assertEquals(dirt,50);
		cleaner.SweepUp(30);
		dirt=cleaner.DetectDirtValue();
		assertEquals(dirt,20);
		
	}

	@Test
	public void testExhaustPower() {
		double currentPower = cleaner.GetPowerLevel();
		assertEquals(currentPower,100.000,0.001);
		cleaner.ExhaustPower(10.000);
		currentPower = cleaner.GetPowerLevel();
		assertEquals(currentPower,90.000,0.001);
	}

	@Test
	public void testExhaustVacuume() {
		int capacity = cleaner.GetVacuumLevel();
		assertEquals(capacity,100);
		int newValue =cleaner.ExhaustVacuume(20);
		assertEquals(newValue,80);
	}

	@Test
	public void testCleanVacuum() {
		cleaner.CleanVacuum();
		int value =cleaner.GetVacuumLevel();
		assertEquals(value,100);
	}

	@Test
	public void testRecharge() {
		cleaner.Recharge();
		double power=cleaner.GetPowerLevel();
		assertEquals(power,100,0.001);
	}

}
