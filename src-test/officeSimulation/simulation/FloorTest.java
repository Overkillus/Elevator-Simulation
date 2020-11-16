package officeSimulation.simulation;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import officeSimulation.people.Client;
import officeSimulation.people.Person;

public class FloorTest {
	@Test(expected=IllegalArgumentException.class)
	public void FloorConstructorNegativeTest() {
		new Floor(-1);
	}
	
	@Test
	public void FloorConstructorZeroTest() {
		Floor floor = new Floor(0);
		assertEquals(0,floor.getLevel());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void FloorAddPersonNullTest() {
		Floor floor = new Floor(0);
		floor.addPerson(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void FloorAddPeopleNullTest() {
		Floor floor = new Floor(0);
		floor.addPeople(null);
	}
	
	//Queue tests
	
	//Tests if the queue pop properly priorities clients
	@Test
	public void FloorPriorityQueueUpPopTest() {
			Floor floor = new Floor(0);
			Person p = new Person();
			floor.queueUp.add(p);
			Client c = new Client(10, 10);
			floor.queueUpClient.add(c);
			assertEquals(c,floor.queueUpPop());
	}
	
	//Tests if the queue pop properly priorities clients
	@Test
	public void FloorPriorityQueueDownPopTest() {
			Floor floor = new Floor(1);
			
			Person p = new Person();
			floor.queueDown.add(p);
			Client c = new Client(10, 10);
			floor.queueDownClient.add(c);
			assertEquals(c,floor.queueDownPop());
	}
	
	//Tests if people are properly remove from the queue when popped
	@Test
	public void FloorQueueUpPopRemoveTest() {
			Floor floor = new Floor(1);
			
			Person p = new Person();
			floor.queueUp.add(p);
			Client c = new Client(10, 10);
			floor.queueUpClient.add(c);
			floor.queueUpPop();
			assertEquals(1,floor.getQueueUpSize());
	}
	
	//Tests if people are properly remove from the queue when popped
	@Test
	public void FloorQueueDownPopRemoveTest() {
			Floor floor = new Floor(1);
			
			Person p = new Person();
			floor.queueDown.add(p);
			Client c = new Client(10, 10);
			floor.queueDownClient.add(c);
			floor.queueDownPop();
			assertEquals(1, floor.getQueueDownSize());
	}

	//Tests if priority queue size is working as intended
	@Test
	public void FloorQueueUpSizeTest() {
			Floor floor = new Floor(1);
			
			Person p = new Person();
			floor.queueUp.add(p);
			Client c = new Client(10, 10);
			floor.queueUpClient.add(c);
			assertEquals(floor.queueUp.size() + floor.queueUpClient.size(),floor.getQueueUpSize());
	}
	
	//Tests if priority queue size is working as intended
	@Test
	public void FloorQueueDownSizeTest() {
			Floor floor = new Floor(1);
			
			Person p = new Person();
			floor.queueDown.add(p);
			Client c = new Client(10, 10);
			floor.queueDownClient.add(c);
			assertEquals(floor.queueDown.size() + floor.queueDownClient.size(),floor.getQueueDownSize());
	}
	
}
