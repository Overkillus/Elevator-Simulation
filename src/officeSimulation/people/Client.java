/**
 * 
 */
package officeSimulation.people;

/**
 * Models a client visiting an office
 * @author HP
 */
public class Client extends Person {
	/*
	 * Determines how long a client will spend on his desired floor
	 */
	public int visitDuration;
	/**
	 * Max wait time of a client
	 */
	private final static int MAX_WAIT_TIME = 60;
	/**
	 * Current queue wait time (refreshed when client gets into a queue)
	 */
	public int waitTime;
	public Client(int destination, int visitDuration) {
		this.size = 1;
		this.currentFloor = 0;
		this.destinationFloor = destination;
		this.visitDuration = visitDuration;
		this.currentDuration = 0;
		waitTime = 0;
	}
	
	/**
	 * Determines if a client has finished his visit on his desired floor
	 * @return true if client is finished with his visit
	 */
	public boolean finished() {
		if(currentDuration >= visitDuration) return true;
		else return complaint();
	}
	
	/**
	 * Checks if the client filled in a complaint
	 * @return
	 */
	public boolean complaint() {
		if(waitTime > MAX_WAIT_TIME) return true;
		else return false;
	}
}
