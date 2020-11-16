/**
 * 
 */
package officeSimulation.simulation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import officeSimulation.people.Client;
import officeSimulation.people.Person;

/**
 * @author HP
 *
 */
public class Floor {
	
	/**
	 * Idle people on a specific floor
	 */
	ArrayList<Person> idle;
	/**
	 * People that are waiting for a lift to go up on a specific floor
	 */
	Queue<Person> queueUp;
	/**
	 * People that are waiting for a lift to go down on a specific floor
	 */
	Queue<Person> queueDown;
	/**
	 * Clients that are waiting for a lift to go up on a specific floor
	 */
	Queue<Client> queueUpClient;
	/**
	 * Clients that are waiting for a lift to go down on a specific floor
	 */
	Queue<Client> queueDownClient;
	/**
	 * Level of the specific floor
	 */
	private final int level;
	
	/**
	 * Floor Constructor
	 * @throws IllegalArgumentAception when level < 0
	 */
	public Floor(int level) {
		if(level < 0) throw new IllegalArgumentException();
		idle = new ArrayList<Person>();
		queueUp = new LinkedList<Person>();
		queueDown = new LinkedList<Person>();
		queueUpClient = new LinkedList<Client>();
		queueDownClient = new LinkedList<Client>();
		this.level = level;
	}

	/**
	 * Adds the person to the list
	 * @param person
	 * @throws IllegalArgumentAception if person null
	 */
	public void addPerson(Person person) {
		if(person == null) throw new IllegalArgumentException();
		idle.add(person);
	}
	
	/**
	 * Adds people to the idle list
	 * @param people
	 * 	 * @throws IllegalArgumentAception if people null
	 */
	public void addPeople(ArrayList<Person> people) {
		if(people == null) throw new IllegalArgumentException();
		people.forEach((n) -> {
			idle.add(n);
		});
	}
	
	//Getters and Setters --------------
	
	public int getLevel() {
		return level;
	}
	
	public int getQueueUpSize() {
		return queueUp.size()+queueUpClient.size();	
	}
	
	public int getQueueDownSize() {
		return queueDown.size()+queueDownClient.size();	
	}
	
	/**
	 * Standard queue pop but for priority queue (clients > rest of people)
	 * @return next available person
	 */
	public Person queueUpPop() {
		if (queueUpClient.size() != 0) return queueUpClient.poll();
		else return queueUp.poll();
	}
	
	/**
	 * Standard queue pop but for priority queue (clients > rest of people)
	 * @return next available person
	 */
	public Person queueDownPop() {
		if (queueDownClient.size() != 0) return queueDownClient.poll();
		else return queueDown.poll();
	}
	
	public ArrayList<Person> getIdle(){
		return idle;
	}
}
