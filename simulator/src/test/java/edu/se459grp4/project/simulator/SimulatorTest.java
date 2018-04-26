/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.se459grp4.project.simulator;

import edu.se459grp4.project.simulator.models.Door;
import edu.se459grp4.project.simulator.models.FloorPlan;
import edu.se459grp4.project.simulator.models.Tile;
import edu.se459grp4.project.simulator.types.Direction;
import edu.se459grp4.project.simulator.types.PathStatus;
import edu.se459grp4.project.simulator.types.SurfaceType;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author whao
 */
public class SimulatorTest extends TestCase {
    FloorPlan mFloorPlan;
    public SimulatorTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        mFloorPlan =   Simulator.getInstance().loadFloorPlan("./example.flr");
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

 
    public void testLoadFloorPlan() {
        
         assertTrue( mFloorPlan.getDirtAmount(5, 5)  == 50);
    }

    public void testProvideDirectionSensroData() {
        
         assertTrue(Simulator.getInstance().getDirectionalData(Direction.LEFT, 0, 6) == PathStatus.BLOCKED);
          assertTrue(Simulator.getInstance().getDirectionalData(Direction.UP, 0, 6) == PathStatus.STAIR);
           assertTrue(Simulator.getInstance().getDirectionalData(Direction.RIGHT, 0, 6) == PathStatus.OPEN);
            assertTrue(Simulator.getInstance().getDirectionalData(Direction.DOWN, 0, 6) == PathStatus.OPEN);
          
    }

    public void testProvideDirtSensroData() {
        assertTrue( Simulator.getInstance().getDirtData(5, 5)  == 50);
    }

    public void testProvideSurfaceSensorData() {
        assertTrue( Simulator.getInstance().getSurfaceData(5, 5)  == SurfaceType.HIGH_CARPET);
    }

    public void testSweepUp() {
        Simulator.getInstance().removeDirt(5, 5,10);
        assertTrue( Simulator.getInstance().getDirtData(5, 5)  == 40);
    }

    public void testOperateDoor() {
        assertTrue(Simulator.getInstance().getDirectionalData(Direction.RIGHT, 4, 0) == PathStatus.OPEN); 
        Simulator.getInstance().operateDoor(true, 4, 0, 0, false);
        assertTrue(Simulator.getInstance().getDirectionalData(Direction.RIGHT, 4, 0) == PathStatus.BLOCKED); 
    }

    public void testAddChargeStation() {
         Simulator.getInstance().addChargeStation(9, 9);
         List<Tile> lList =  Simulator.getInstance().getAllChargeStations();
         assertTrue(lList.size() == 3);
    }

    public void testRemoveChargeStation() {
        Simulator.getInstance().removeChargeStation(9, 9);
        List<Tile> lList =  Simulator.getInstance().getAllChargeStations();
        assertTrue(lList.size() == 2);
    }

    public void testGetAllDoors() {
        List<Door> lList =  Simulator.getInstance().getAllDoors();
        assertTrue(lList.size() == 2);
    }

   
    
}
