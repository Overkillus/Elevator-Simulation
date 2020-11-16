/**
 * 
 */
package officeSimulation.simulation;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author HP
 *
 */
public class OfficeTest {

	//Test if the office sets up correctly
	@Test
	public void officeConstructorTest() {
		Office o = new Office(7, 1 ,4); //floors lifts capacity
		boolean f = o.getFloors().size() == 7 ? true : false;
		boolean l = o.getLifts().size() == 1 ? true : false;
		assertTrue(f && l);
	}
	
}
