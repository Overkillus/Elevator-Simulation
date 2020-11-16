/**
 * 
 */
package officeSimulation.people;

/**
 * Default person
 * @author HP
 */
public class Person {
	/**
	 * Size defines the amount of space taken in a lift
	 */
	protected int size;
	/**
	 * Current floor level the person is at
	 */
	protected int currentFloor;
	/**
	 * The floor where the person wants to go to
	 */
	protected int destinationFloor;
	/**
	 * 
	 */
	protected int currentDuration;
	
	public int getCurrentFloor() {return currentFloor; }
	public void setCurrentFloor(int floor) { currentFloor = floor; }
	public int getDestinationFloor() {return destinationFloor; }
	public int getSize() { return size; }
	public void setDestination(int d) { destinationFloor = d; }
	public void setDuration(int d) { currentDuration = d;}
	public int getDuration() { return currentDuration; }
}
