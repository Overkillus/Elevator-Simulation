/**
 * 
 */
package officeSimulation.people;

/**
 * Models a maintenance crew working visiting the office
 * @author HP
 */
public class Maintenance extends Person {
	/**
	 * Declares how long the maintenance will spend on its desired floor ("(w<length>)") in UI
	 */
	public int visitDuration;
	public Maintenance(int floorNumber, int visitDuration) {
		this.size = 4;
		this.currentFloor = 0;
		this.destinationFloor = floorNumber-1;
		this.visitDuration = visitDuration;
		this.currentDuration = 0;
	}
	
	/**
	 * Determines if maintenance crew has finished theirs visit on the desired floor
	 * @return true if finished with the visit
	 */
	public boolean finished() {
		if(currentDuration >= visitDuration) return true;
		else return false;
	}
}
