/**
 * 
 */
package officeSimulation.simulation;

import java.util.ArrayList;

import officeSimulation.people.Client;
import officeSimulation.people.Developer;
import officeSimulation.people.Employee;
import officeSimulation.people.Maintenance;
import officeSimulation.people.Person;
import java.util.Random;

/**
 * Spawns and controls changes occurring in people
 * @author HP
 */
public class PeopleController {
	/**
	 * Newly generated people
	 */
	private ArrayList<Person> people;
	/**
	 * Random body used for all random elements in the simulation
	 */
	private Random random;
	
	/**
	 * Creates a new people generator
	 */
	public PeopleController() {
		people = new ArrayList<Person>();
		random = new Random();
	}
	
	/**
	 * Creates a new people generator with a specific seed
	 * @overload
	 * @param seed for random body
	 */
	public PeopleController(int seed) {
		people = new ArrayList<Person>();
		random = new Random(seed);
	}
	
	/**
	 * Attempts to spawn new clients and maintenance crews
	 * @param maintenance probability of new maintenance crew arriving
	 * @param q probability of new client arriving
	 * @param floorNumber specifies the number of floors
	 */
	public void spawn(double maintenance, double q, int floorNumber) {
		//Probability check
		if(random.nextDouble() <= maintenance) 
			//Creates a new maintenance with a random visit duration
			people.add(new Maintenance(floorNumber,random.nextInt(120)+120));
		//Probability check
		if(random.nextDouble() <= q)
			//Creates a new Client with a random destination floor and visit duration
			people.add(new Client(random.nextInt(floorNumber/2),random.nextInt(120)+60));
	}
	
	/**
	 * Spawns the starting population
	 * @param employeeNumber amount of employees to be spawned
	 * @param developerNumber amount of developers to be spawned
	 */
	public void initialSpawn(int employeeNumber, int developerNumber, ArrayList<Floor> floors) {
		//Initial population
		for(int i=0; i<employeeNumber; i++) {
			Employee p = new Employee();
			int d = p.getCurrentFloor();
			while(d == p.getCurrentFloor()){
				d = random.nextInt(floors.size());
			}
			p.setDestination(d);
			people.add(p);
		}
		for(int i=0; i<developerNumber; i++) {
			boolean google = (i>=developerNumber/2) ? true : false;
			Developer p = new Developer(google);
			int d = p.getCurrentFloor();
			while(d == p.getCurrentFloor()){
				d = random.nextInt((floors.size()+1)/2) + floors.size()/2 ;
			}
			p.setDestination(d);
			people.add(p);
		}
	}
	
	/**
	 * Extracts all newly generated people
	 * @return
	 */
	public ArrayList<Person> extract(){
		//Temporary storage for people
		ArrayList<Person> extract = people;
		//Empties people
		people = new ArrayList<Person>();
		//Output
		return extract;
	}
	
	/**
	 * Updates destinations, visit time and clears people that are already finished
	 * @return
	 */
	public ArrayList<Floor> update(ArrayList<Floor> floors, double p) {
		floors.forEach( (f) -> {
			f.getIdle().forEach( (person) -> {
				//Updates duration
				person.setDuration(person.getDuration()+1);
				//Updates current location
				person.setCurrentFloor(f.getLevel());
				//Updates destination
				//If employee change floors with probability of p
				if(person instanceof Employee) {
					if(random.nextDouble() <= p) {
						int d = person.getCurrentFloor();
						while(d == person.getCurrentFloor()){
							d = random.nextInt(floors.size());
						}
						person.setDestination(d);
					}
				}
				//If developer change floors with probability of p
				else if(person instanceof Developer) {
					if(random.nextDouble() <= p) {
						int d = person.getCurrentFloor();
						while(d == person.getCurrentFloor()){
							//Randomises a floor in the upper half of the building
							d = random.nextInt((floors.size()+1)/2) + floors.size()/2 ;
						}
						person.setDestination(d);
					}
				}
				//If client and finished send him to floor 0 (complaints registered when leaving building)
				else if(person instanceof Client){
					Client client = (Client) person;
					if(client.getDestinationFloor() != 0 && client.finished()) client.setDestination(0);
				}
				//If client and work finished send him to floor 0
				else if(person instanceof Maintenance){
					Maintenance maintenance = (Maintenance) person;
					if(maintenance.getDestinationFloor() != 0 && maintenance.finished()) maintenance.setDestination(0);
				}
			});
		});
		//Update wait time in queue before returning
		ArrayList<Floor> floorsTemp = updateClientWaitTime(floors);
		return floorsTemp;
	}
	
	/**
	 * Adds +1 waitTime for every client in queue
	 * @param floors
	 */
	private ArrayList<Floor> updateClientWaitTime(ArrayList<Floor> floors) {
		floors.forEach((f) -> {
			f.queueUpClient.forEach(c -> {
				c.waitTime++;
			});
			f.queueDownClient.forEach(c -> {
				c.waitTime++;
			});
		});
		return floors;
	}
	
	/**
	 * Clear clients and maintenance crew that has finished working
	 * @param floors
	 * @return cleared floors
	 */
	public ArrayList<Floor> clear(ArrayList<Floor> floors){
		Floor floor = floors.get(0);
		Floor tempFloor = floor;
		for(int i=0; i<floor.getIdle().size(); i++) {
			Person p = floor.getIdle().get(i);
			if(p instanceof Maintenance && ((Maintenance) p).finished()) {
				tempFloor.getIdle().remove(p);
			}
			else if(p instanceof Client && ((Client) p).finished()) {
				tempFloor.getIdle().remove(p);
			}
		}
		floor = tempFloor;
		floors.set(0, floor);
		return floors;
	}
	
	/**
	 * Counts all the complaints
	 * @param floors
	 * @return
	 */
	public int gatherComplaints(ArrayList<Floor> floors) {
		Floor floor = floors.get(0);
		ArrayList<Person> idle = floor.idle;
		int output = 0;
		//Iterate through all the people on floor 0
		for(int i=0; i<idle.size(); i++) {
			Person p = idle.get(i);
			//If they are clients check if they registered a complaint
			if(p instanceof Client) {
				Client c = (Client)p;
				if(c.complaint()) output++;
			}
		}
		//Return the sum of new complaints
		return output;
	}
	
	/**
	 * Checks the population and moves people requesting the lift to the queue
	 * @param floors to update
	 * @return updated ArrayList of floors
	 */
	public ArrayList<Floor> updateQueues(ArrayList<Floor> floors) {
		floors.forEach((f) -> {
			//Temporary ArrayList to keep track of people leaving idle ArrayList
			ArrayList<Person> tempIdle = new ArrayList<Person>();
			f.idle.forEach( (p) -> {
				//If person wants to change floors
				if(p.getCurrentFloor() != p.getDestinationFloor()) {
					//If person wants to go up
					if(p.getDestinationFloor() > p.getCurrentFloor()) 
						if(p instanceof Client) {
							Client c = (Client)p;
							//Reset wait time when joining queue
							c.waitTime = 0;
							f.queueUpClient.add(c);
						}
							
						else f.queueUp.add(p);
					//If person wants go down
					else 
						if(p instanceof Client) {
							Client c = (Client)p;
							c.waitTime = 0;
							f.queueDownClient.add(c);
						}
						else f.queueDown.add(p);
				}
				else tempIdle.add(p);
			});
			//Refresh idle
			f.idle = tempIdle;
		});
		
		//Special case - client on floor 0 waits for too long and leaves the queue
		Floor floor = floors.get(0);
		while(floor.queueUpClient.size() != 0 && floor.queueUpClient.peek().complaint()) {
			Client c = floor.queueUpClient.poll();
			c.setDestination(0);
			floor.addPerson(c);
		}
		
		return floors;
	}
}
