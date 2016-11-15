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
import edu.se459grp4.project.simulator.types.TileStatus;
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
        
         assertTrue( mFloorPlan.GetDirtVal(5, 5)  == 50);
    }

    public void testProvideDirectionSensroData() {
        
         assertTrue(Simulator.getInstance().ProvideDirectionSensorData(Direction.Left, 0, 6) == PathStatus.Blocked);
          assertTrue(Simulator.getInstance().ProvideDirectionSensorData(Direction.Up, 0, 6) == PathStatus.Stair);
           assertTrue(Simulator.getInstance().ProvideDirectionSensorData(Direction.Right, 0, 6) == PathStatus.Open);
            assertTrue(Simulator.getInstance().ProvideDirectionSensorData(Direction.Down, 0, 6) == PathStatus.Open);
          
    }

    public void testProvideDirtSensroData() {
        assertTrue( Simulator.getInstance().ProvideDirtSensorData(5, 5)  == 50);
    }

    public void testProvideSurfaceSensorData() {
        assertTrue( Simulator.getInstance().ProvideSurfaceSensorData(5, 5)  == TileStatus.HIGH_CARPET);
    }

    public void testSweepUp() {
        Simulator.getInstance().SweepUp(5, 5,10);
        assertTrue( Simulator.getInstance().ProvideDirtSensorData(5, 5)  == 40);
    }

    public void testOperateDoor() {
        assertTrue(Simulator.getInstance().ProvideDirectionSensorData(Direction.Right, 4, 0) == PathStatus.Open); 
        Simulator.getInstance().OperateDoor(true, 4, 0, 0, false);
        assertTrue(Simulator.getInstance().ProvideDirectionSensorData(Direction.Right, 4, 0) == PathStatus.Blocked); 
    }

    public void testAddChargeStation() {
         Simulator.getInstance().AddChargeStation(9, 9);
         List<Tile> lList =  Simulator.getInstance().GetAllChargeStations();
         assertTrue(lList.size() == 3);
    }

    public void testRemoveChargeStation() {
        Simulator.getInstance().RemoveChargeStation(9, 9);
        List<Tile> lList =  Simulator.getInstance().GetAllChargeStations();
        assertTrue(lList.size() == 2);
    }

    public void testGetAllDoors() {
        List<Door> lList =  Simulator.getInstance().GetAllDoors();
        assertTrue(lList.size() == 2);
    }

   
    
}
