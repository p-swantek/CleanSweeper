package edu.se459grp4.project.cleansweep;

import static org.junit.Assert.*;

import org.junit.Test;

public class CleanSweepManagerTest {
	
	private static final double DELTA = 0.001;

	@Test
	public void testCreateCleanSweep() {
		int id = CleanSweepManager.getInstance().createCleanSweep(0, 0);
		assertEquals(0, id);
		
	}

	@Test
	public void testGetCleanSweep() {
		int id = CleanSweepManager.getInstance().createCleanSweep(0, 0);
		CleanSweep cs = CleanSweepManager.getInstance().getCleanSweep(id);
		
		assertEquals(1, cs.getID());
		assertEquals(0, cs.getX());
		assertEquals(0, cs.getY());
		assertEquals(100.0, cs.getCurrPower(), DELTA);
		assertEquals(1000, cs.getCurrVacuumCapacity());
		
		cs = CleanSweepManager.getInstance().getCleanSweep(-1);
		assertNull(cs);

	}
}
