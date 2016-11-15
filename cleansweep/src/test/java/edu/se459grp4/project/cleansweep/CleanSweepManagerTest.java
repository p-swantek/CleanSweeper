package edu.se459grp4.project.cleansweep;

import static org.junit.Assert.*;

import org.junit.Test;

public class CleanSweepManagerTest {
	
	private static final double DELTA = 0.001;

	@Test
	public void testCreateCleanSweep() {
		int id = CleanSweepManager.getInstance().CreateCleanSweep(0, 0);
		assertEquals(0, id);
		
	}

	@Test
	public void testGetCleanSweep() {
		int id = CleanSweepManager.getInstance().CreateCleanSweep(0, 0);
		CleanSweep cs = CleanSweepManager.getInstance().GetCleanSweep(id);
		
		assertEquals(1, cs.GetID());
		assertEquals(0, cs.GetX());
		assertEquals(0, cs.GetY());
		assertEquals(100.0, cs.GetPowerLevel(), DELTA);
		assertEquals(1000, cs.GetVacuumLevel());
		
		cs = CleanSweepManager.getInstance().GetCleanSweep(-1);
		assertNull(cs);

	}
	
	
	@Test
	public void testStartCleanCycle(){
		int id = CleanSweepManager.getInstance().CreateCleanSweep(0, 0);
		assertTrue(CleanSweepManager.getInstance().StartCleanCycle(id));
		assertFalse(CleanSweepManager.getInstance().StartCleanCycle(-1));

	}

	
}
