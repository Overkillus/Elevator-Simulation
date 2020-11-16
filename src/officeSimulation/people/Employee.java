/**
 * 
 */
package officeSimulation.people;

/**
 * Models an employee of an office
 * @author HP
 */
public class Employee extends Person {
	
	/**
	 * Constructor updating starting values
	 */
	public Employee() {
		this.size = 1;
		this.currentFloor = 0;
		this.destinationFloor = this.currentFloor;
		this.currentDuration = 0;
	}
}
