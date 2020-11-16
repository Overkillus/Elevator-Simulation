/**
 * 
 */
package officeSimulation.simulation;

import java.util.ArrayList;
import officeSimulation.people.Person;

/**
 * @author HP
 *
 */
public class Office{
	
	/**
	 * List of all the floors in the office
	 */
	ArrayList<Floor> floors;
	/**
	 * List of all the lifts in the office
	 */
	ArrayList<Lift> lifts;
	/**
	 * Number of complaints
	 */
	int complaints;
	/**
	 * Archive of wait time throughout the simulation
	 */
	ArrayList<Integer> waitTimes;
	
	/**
	 * Office constructor
	 * @param floorNumber Number of floors in the office
	 * @throws IllegalArgumentAception if input <= 0
	 */
	public Office(int floorNumber, int liftNumber, int liftCapacity) {
		//Detects Illegal Arguments
		if(floorNumber <= 0) throw new IllegalArgumentException();
		//Initialisation
		this.floors = new ArrayList<Floor>();
		this.lifts = new ArrayList<Lift>();
		this.waitTimes = new ArrayList<Integer>();
		//Populates floors
		for(int i=0; i<floorNumber; i++) {
			this.floors.add(new Floor(i));
		}
		//Populates lifts
		for(int i=0; i<liftNumber; i++) {
			this.lifts.add(new Lift(liftCapacity));
		}
		complaints = 0;
	}
	
	/**
	 * Adds new people to floor 0
	 * @param people
	 */
	public void addPeople(ArrayList<Person> people) {
		floors.get(0).addPeople(people);
	}
	
	/**
	 * @return all floors in the office
	 */
	public ArrayList<Floor> getFloors(){
		return floors;
	}
	
	/**
	 * 
	 * @param floors
	 */
	public void setFloors(ArrayList<Floor> floors) {
		this.floors = floors;
	}
	
	/**
	 * @return all lifts in the office
	 */
	public ArrayList<Lift> getLifts(){
		return lifts;
	}
	
	/**
	 * @param lifts
	 */
	public void setLifts(ArrayList<Lift> lifts) {
		this.lifts = lifts;
	}
	
	/**
	 * Calculates the average wait time
	 */
	public int getAverageWaitTime() {
		if(waitTimes.size() == 0) return 0;
		int sum = 0;
		for(int i=0; i<waitTimes.size(); i++) {
			sum += waitTimes.get(i);
		}
		sum = sum / waitTimes.size();
		return sum;
	}
}
