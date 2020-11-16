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
public class Lift {
	/**
	 * All the people on a specific floor
	 */
	private ArrayList<Person> population;
	/**
	 * Defines how many people can fit into the lift
	 */
	private int capacity;
	/**
	 * Current capacity of the lift
	 */
	private int currentWeight;
	/**
	 * Current Floor of the lift
	 */
	private int currentFloor;
	/**
	 * States if the lift moved up in the last move
	 */
	private boolean goingUp;
	/**
	 * States if the doors are open
	 */
	private boolean doorsOpen;
	/**
	 * Describes a special case for rivalries when the whole queue on a floor consists of "enemy" developer that don't want to board the lift
	 */
	boolean stuck;
	
	/**
	 * Creates a new lift
	 * @param capacity of the lift
	 * @throws IllegalArgumentException
	 */
	public Lift(int capacity) {
		if(capacity < 0) throw new IllegalArgumentException();
		this.population = new ArrayList<Person>();
		this.capacity = capacity;
		this.currentWeight = 0;
		this.currentFloor = 0;
		this.goingUp = false;
		this.setDoorsOpen(false);
		stuck = false;
	}

	/**
	 * Unloads the people from the lift
	 * @return ArrayList of all the people getting off at the current floor
	 */
	public ArrayList<Person> unloadPeople() {
		ArrayList<Person> unload = new ArrayList<Person>();
		ArrayList<Person> tempPopulation = new ArrayList<Person>();
		population.forEach( (p) -> {
			if( p.getDestinationFloor() == currentFloor) {
				p.setCurrentFloor(currentFloor);
				unload.add(p);
			}
			else tempPopulation.add(p);
		});
		population = tempPopulation;
		updateCurrentWeight();
		return unload;
	}
	
	/*
	 * Adds person to the population
	 */
	public boolean loadPerson(Person person) {
		if(person == null || currentWeight + person.getSize() > capacity) return false;
		population.add(person);
		updateCurrentWeight();
		return true;
	}
	
	/**
	 * Updates current weight
	 * @return
	 */
	private void updateCurrentWeight() {
		int currentWeight = 0;
		for(int i=0; i<population.size(); i++) {
			currentWeight+= population.get(i).getSize();
		}
		this.currentWeight = currentWeight;
	}
	
	//Getters and Setters --------------

	/**
	 * @return the population
	 */
	public ArrayList<Person> getPopulation()
	{
		return population;
	}
	
	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @return the currentCapacity
	 */
	public int getCurrentWeight() {
		return currentWeight;
	}
	
	/**
	 * Checks if the lift is full
	 * @return true if the lift is full
	 */
	public boolean full() {
		if (capacity == currentWeight) return true;
		else return false;
	}

	/**
	 * @return the goingUp
	 */
	public boolean isGoingUp() {
		return goingUp;
	}

	/**
	 * @return the doorsOpen
	 */
	public boolean isDoorsOpen() {
		return doorsOpen;
	}

	/**
	 * @param doorsOpen the doorsOpen to set
	 */
	public void setDoorsOpen(boolean doorsOpen) {
		this.doorsOpen = doorsOpen;
	}
	
	/**
	 * Moves one floor up
	 */
	public void moveUp(){
		this.currentFloor++;
		goingUp = true;
	}
	
	/**
	 * Moves one floor down
	 */
	public void moveDown(){
		this.currentFloor--;
		goingUp = false;
	}
	
	/**
	 * @return the currentFloor
	 */
	public int getCurrentFloor() {
		return currentFloor;
	}
}
