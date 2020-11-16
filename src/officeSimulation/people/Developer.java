/**
 * 
 */
package officeSimulation.people;

/**
 * Models a developer working in an office
 * @author HP
 */
public class Developer extends Person {
	/**
	 * Boolean flag declaring if the dev is a google developer
	 */
	public boolean google;
	public Developer(boolean google) {
		this.size = 1;
		this.currentFloor = 0;
		this.destinationFloor = this.currentFloor;
		this.currentDuration = 0;
		this.google = google;
	}
}
