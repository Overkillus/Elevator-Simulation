/**
 * 
 */
package officeSimulation.simulation;

/**
 * Class representing the lift request state for a specific floor
 * @author HP
 *
 */
public class Request {
	/**
	 * Drop off level for someone in the lift
	 */
	private boolean destination;
	/**
	 * Someone on this floor wants to go up
	 */
	private boolean up;
	/**
	 * Someone on this floor wants to go down
	 */
	private boolean down;
	
	/**
	 * Request constructor
	 */
	public Request() {
		this.setDestination(false);
		this.setUp(false);
		this.setDown(false);
	}
	
	/*
	 * Checks if any of the values is true
	 */
	public boolean isAnyTrue() {
		if(destination || up || down) return true;
		else return false;
	}
	
	/**
	 * @return the destination
	 */
	public boolean isDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(boolean destination) {
		this.destination = destination;
	}

	/**
	 * @return the up
	 */
	public boolean isUp() {
		return up;
	}

	/**
	 * @param up the up to set
	 */
	public void setUp(boolean up) {
		this.up = up;
	}

	/**
	 * @return the down
	 */
	public boolean isDown() {
		return down;
	}

	/**
	 * @param down the down to set
	 */
	public void setDown(boolean down) {
		this.down = down;
	}
}
