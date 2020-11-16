/**
 * 
 */
package officeSimulation.simulation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import officeSimulation.people.Client;
import officeSimulation.people.Employee;

/**
 * Request testing
 * @author HP
 *
 */
public class PeopleControllerTest {
	
	//Tests if update properly assigns a new destination floor for people
	@Test
	public void PeopleControllerUpdateTest() {
		ArrayList<Floor> floors = new ArrayList<Floor>();
		floors.add(new Floor(0));
		floors.add(new Floor(1));
		floors.get(0).addPerson(new Employee());
		
		PeopleController x = new PeopleController(1234);
		floors = x.update(floors, 1); //100% chance to change destination
		
		assertEquals(1, floors.get(0).idle.get(0).getDestinationFloor());
	}
	
	//Tests if updateQueue properly moves people to respective queues
	@Test
	public void PeopleControllerUpdateQueueTest() {
		ArrayList<Floor> floors = new ArrayList<Floor>();
		floors.add(new Floor(0));
		floors.add(new Floor(1));
		floors.get(0).addPerson(new Employee());
		
		PeopleController x = new PeopleController(1234);
		floors = x.update(floors, 1); //100% chance to change destination
		
		floors = x.updateQueues(floors);
		
		assertEquals(1, floors.get(0).getQueueUpSize());
	}
	
	//Tests if same seed results in the same list of people (reusing one random instance)
	@Test
	public void PeopleControllerSeedTest() {
		ArrayList<Floor> floors = new ArrayList<Floor>();
		for(int i=0; i<100; i++) {
			floors.add(new Floor(i));
		}
		PeopleController x = new PeopleController(12345);
		x.initialSpawn(1, 0, floors);
		PeopleController y = new PeopleController(12345);
		y.initialSpawn(1, 0, floors);
		
		assertEquals(x.extract().get(0).getDestinationFloor(), y.extract().get(0).getDestinationFloor());
	}
	
	//Tests Clients that has finished their visit leave the office
	@Test
	public void PeopleControllerClearTest() {
		ArrayList<Floor> floors = new ArrayList<Floor>();
		for(int i=0; i<10; i++) {
			floors.add(new Floor(i));
		}
		PeopleController x = new PeopleController(12345);
		Client c = new Client(0, 0);
		floors.get(0).addPerson(c);
		Employee e = new Employee();
		floors.get(0).addPerson(e);
		
		floors = x.clear(floors); //Clears clients that have finished their business
		
		assertEquals(1, floors.get(0).idle.size());
	}
	
}
