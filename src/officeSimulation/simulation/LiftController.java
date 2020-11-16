/**
 * 
 */
package officeSimulation.simulation;

import java.util.ArrayList;

import officeSimulation.people.Client;
import officeSimulation.people.Developer;
import officeSimulation.people.Person;

/**
 * Controls actions of lifts in the office
 * @author HP
 *
 */
public class LiftController {

	private Office office;
	
	/**
	 * Loads data of the current office
	 * @param office
	 */
	public void loadData(Office office) {
		this.office = office;
	}
	
	/**
	 * Extracts the updated office
	 * @return
	 */
	public Office extract() {
		return office;
	}
	
	/**
	 * Controls the actions of the lift
	 */
	public void liftsAction() {
		for(int i=0; i<office.lifts.size(); i++) {
			Lift lift = office.lifts.get(i);
			
			
			//Special case if lift stuck due to developers from opposite company refusing to board the lift
			if(lift.stuck)
				if(lift.isDoorsOpen())
					lift.setDoorsOpen(false);
				else
					lift = liftMovement(lift); //clears the stuck flag
			
			//Default case
			else if(lift.isDoorsOpen())
				if(loadable(lift)) lift = loading(lift);
				else lift.setDoorsOpen(false);
			else
				if(loadable(lift)) lift.setDoorsOpen(true);
				else {
					lift = liftMovement(lift); 
					if(loadable(lift)) lift.setDoorsOpen(true);
				}
			office.lifts.set(i, lift);
		}
	}
	
	/**
	 * Decides where the lift should move
	 * @param Lift before movement
	 * @return Lift after movement
	 */
	private Lift liftMovement(Lift lift) {
		lift.stuck = false;
		Request[] requests = getRequests(lift);
		//No requests
		if(!requestAbove(requests, lift) && !requestUnder(requests, lift) && lift.getCurrentFloor() != 0) lift.moveDown();
		//going up and request up
		else if(lift.isGoingUp() && requestAbove(requests, lift)) lift.moveUp();
		//going up but no request up and a request down
		else if(lift.isGoingUp() && requestUnder(requests, lift)) lift.moveDown();
		//going down and request down
		else if(!lift.isGoingUp() && requestUnder(requests, lift)) lift.moveDown();
		//going down but no request down and request up
		else if(!lift.isGoingUp() && requestAbove(requests, lift)) lift.moveUp();
		return lift;
	}

	/**
	 * Loads and unloads people between the current floor and lift
	 * @param lift that will be loading and unloading
	 * @return lift after the loading and unloading
	 */
	Lift loading(Lift lift) {
		int currentFloor = lift.getCurrentFloor();
		Floor floor = office.floors.get(currentFloor);
		//Unloading
		ArrayList<Person> unload = lift.unloadPeople();
		floor.addPeople(unload);
		//Loading
		//Priority for people going up
		if(lift.isGoingUp()) {
			//while lift is not full and queue is not empty load people going up
			while(floor.getQueueUpSize() != 0 && canFitNext(lift, true)) {
				Person p = floor.queueUpPop();
				//Extracting wait time for statistics
				if(p instanceof Client) {
					office.waitTimes.add( ((Client)p).waitTime );
				}
				//Rivalries
				if(p instanceof Developer && !canEnter(lift,((Developer)p))) {
					floor.addPerson(p);
					//Special case when the whole queue consists of people that don't want to get in the lift
					if(floor.getQueueUpSize() == 0) {
						lift.stuck = true;
					}
				}
				else lift.loadPerson(p);
			}
			//while lift is not full and queue is not empty load people going down
			while(floor.getQueueDownSize() != 0 && canFitNext(lift, false)) {
				Person p = floor.queueDownPop();
				//Extracting wait time for statistics
				if(p instanceof Client) {
					office.waitTimes.add( ((Client)p).waitTime );
				}
				//Rivalries
				if(p instanceof Developer && !canEnter(lift,((Developer)p))) {
					floor.addPerson(p);
					//Special case when the whole queue consists of people that don't want to get in the lift
					if(floor.getQueueDownSize() == 0) {
						lift.stuck = true;
					}
				}
				else lift.loadPerson(p);
			}
		}
		//Priority for people going down
		else {
			//while lift is not full and queue is not empty load people going down
			while(floor.getQueueDownSize() != 0 && canFitNext(lift, false)) {
				Person p = floor.queueDownPop();
				//Extracting wait time for statistics
				if(p instanceof Client) {
					office.waitTimes.add( ((Client)p).waitTime );
				}
				//Rivalries
				if(p instanceof Developer && !canEnter(lift,((Developer)p))) {
					floor.addPerson(p);
					//Special case when the whole queue consists of people that don't want to get in the lift
					if(floor.getQueueDownSize() == 0) {
						lift.stuck = true;
					}
				}
				else lift.loadPerson(p);
			}
			//while lift is not full and queue is not empty load people going up
			while(floor.getQueueUpSize() != 0 && canFitNext(lift, true)) {
				Person p = floor.queueUpPop();
				//Extracting wait time for statistics
				if(p instanceof Client) {
					office.waitTimes.add( ((Client)p).waitTime );
				}
				//Rivalries
				if(p instanceof Developer && !canEnter(lift,((Developer)p))) {
					floor.addPerson(p);
					//Special case when the whole queue consists of people that don't want to get in the lift
					if(floor.getQueueUpSize() == 0) {
						lift.stuck = true;
					}
				}
				else lift.loadPerson(p);
			}
		}
		office.floors.set(currentFloor, floor);
		return lift;
	}
	
	/**
	 * Checks if there is a request on the current floor
	 * @param lift to check
	 * @return boolean determining if there is a request on the current floor
	 */
	private boolean loadable(Lift lift) {
		Request[] requests = getRequests(lift);
		Request request = requests[lift.getCurrentFloor()];
		if(request.isDestination()) return true;
		else if(request.isUp() && canFitNext(lift, true)) return true;
		else if(request.isDown() && canFitNext(lift, false)) return true;
		else return false;
	}
	
	/**
	 * Creates an easier to process request table from all the people
	 * @param lift to get requests for
	 * @return Array of Request
	 */
	Request[] getRequests(Lift lift){
		Request[] requests = new Request[office.floors.size()];
		//Populates requests
		for(int i=0; i<office.floors.size(); i++) {
			requests[i] = new Request();
		}
		//Updates requests based on floor populations
		if(!lift.full()) {
			office.floors.forEach((f) -> {
				if(f.getQueueUpSize() != 0) requests[f.getLevel()].setUp(true);
				if(f.getQueueDownSize() != 0) requests[f.getLevel()].setDown(true);
			});
		}
		//Updates requests based on lift population
		lift.getPopulation().forEach((p) -> {
			requests[p.getDestinationFloor()].setDestination(true);
		});

		//Output
		return requests;
	}
	
	/**
	 * Checks if there is a request above the lift
	 * @return
	 */
	private boolean requestAbove(Request[] requests, Lift lift) {
		for(int i=lift.getCurrentFloor()+1; i<office.floors.size(); i++) {
			if(requests[i].isAnyTrue()) return true;
		}
		return false;
	}
	
	/**
	 * Checks if there is a request under the lift
	 * @return
	 */
	private boolean requestUnder(Request[] requests, Lift lift) {
		for(int i=lift.getCurrentFloor()-1; i>=0; i--) {
			if(requests[i].isAnyTrue()) return true;
		}
		return false;
	}
	
	/**
	 * Checks if next person from the specified queue can fit into the given lift
	 * @param lift lift to check for
	 * @param up if true checks queueUp for next person
	 * @return
	 */
	private boolean canFitNext(Lift lift, boolean up) {
		Floor floor = office.floors.get(lift.getCurrentFloor());
		int weight = 0;
		if(up) {
			//Current weight of lift + next person <= capacity
			if(floor.queueUpClient.size() != 0) weight = floor.queueUpClient.peek().getSize();
			else weight = floor.queueUp.peek().getSize();
			if (lift.getCurrentWeight() + weight <= lift.getCapacity())
				return true;
			else return false;
		}
		else {
			//Current weight of lift + next person <= capacity
			if(floor.queueDownClient.size() != 0) weight = floor.queueDownClient.peek().getSize();
			else weight = floor.queueDown.peek().getSize();
			if (lift.getCurrentWeight() + weight <= lift.getCapacity())
				return true;
			else return false;
		}
	}
	
	/**
	 * Determines if a person can enter the lift due to company conflict
	 * @return
	 */
	public boolean canEnter(Lift lift, Person n) {
		if(!(n instanceof Developer)) return true;
		Developer d = (Developer)n;
		for(int i=0; i<lift.getPopulation().size(); i++) {
			Person p = lift.getPopulation().get(i);
			if(p instanceof Developer) 
				if(((Developer)p).google != d.google) return false;
		}
		return true;
	}
}
