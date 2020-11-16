/**
 * 
 */
package officeSimulation.simulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import officeSimulation.people.Employee;

/**
 * Lift testing class JUnit4
 * @author HP
 */
public class LiftTest {
	
	//Tests exception when negative input is given to constructor
	@Test(expected=IllegalArgumentException.class)
	public void LiftConstructorNegativeTest() {
		new Lift(-2);
	}
	
	//Lift movement test
	@Test
	public void LiftMoveTest() {
		Lift l = new Lift(4);
		l.moveUp();
		l.moveUp();
		l.moveDown();
		assertEquals(1, l.getCurrentFloor());
	}
	
	//Lift movement direction boolean flag test
	@Test
	public void LiftMoveDiretionTest() {
		Lift l = new Lift(4);
		l.moveUp();
		l.moveUp();
		l.moveDown();
		assertFalse(l.isGoingUp());
	}
	
	//Unload testing
	@Test
	public void LiftUnloadTest() {
		Lift l = new Lift(4);
		
		Employee x = new Employee();
		x.setDestination(0);
		l.loadPerson(x);
		Employee y = new Employee();
		y.setDestination(0);
		l.loadPerson(y);
		Employee z = new Employee();
		z.setDestination(2);
		l.loadPerson(z); 
		
		l.unloadPeople();
		assertEquals(1, l.getCurrentWeight());
	}
	
	//Load testing - prevents loading when lift is full
	@Test
	public void LiftLoadTest() {
		Lift l = new Lift(1);
		Employee x = new Employee();
		l.loadPerson(x);
		
		assertFalse(l.loadPerson(x));
	}
}
