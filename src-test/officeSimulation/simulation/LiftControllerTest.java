/**
 * 
 */
package officeSimulation.simulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import officeSimulation.people.Employee;

/**
 * @author HP
 *
 */
public class LiftControllerTest {
	
	//Tests if loading and unloading data is possible
	@Test
	public void LiftControllerDataTest(){
		Office o = new Office(7, 1, 4);
		LiftController c = new LiftController();
		c.loadData(o);
		assertEquals(o, c.extract());
	}
	
	//Test if requests are created accordingly
	@Test
	public void LiftControllerGetRequestsUpTest() {
		Office o = new Office(7, 1, 4);
		LiftController c = new LiftController();
		
		Employee e = new Employee();
		e.setDestination(5);
		o.floors.get(0).queueUp.add(e);
		
		c.loadData(o);
		Request[] requests = c.getRequests(o.getLifts().get(0));
		assertTrue(requests[0].isUp());
	}
	
	//Test if requests are created accordingly
	@Test
	public void LiftControllerGetRequestsDownTest() {
		Office o = new Office(7, 1, 4);
		LiftController c = new LiftController();
		
		Employee e = new Employee();
		e.setDestination(0);
		o.floors.get(5).queueDown.add(e);
		
		c.loadData(o);
		Request[] requests = c.getRequests(o.getLifts().get(0));
		assertTrue(requests[5].isDown());
	}
	
	//Test if requests are created accordingly
	@Test
	public void LiftControllerGetRequestsDestinationTest() {
		Office o = new Office(7, 1, 4);
		LiftController c = new LiftController();
		
		Employee e = new Employee();
		e.setDestination(0);
		o.lifts.get(0).loadPerson(e);
		
		c.loadData(o);
		Request[] requests = c.getRequests(o.getLifts().get(0));
		assertTrue(requests[0].isDestination());
	}
	
	//Test if requests are created accordingly
	@Test
	public void LiftControllerLoadingTest() {
		Office o = new Office(7, 1, 4);
		LiftController c = new LiftController();
		
		for(int i=0; i<6; i++) {
			Employee e = new Employee();
			e.setDestination(5);
			o.floors.get(0).queueUp.add(e);
		}
		
		c.loadData(o);
		
		Lift l = o.getLifts().get(0);
		l = c.loading(l);
		
		assertEquals(4, l.getCurrentWeight());
	}
}
