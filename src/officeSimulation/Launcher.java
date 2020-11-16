/**
 * 
 */
package officeSimulation;

import officeSimulation.simulation.Simulation;

/**
 * Launcher for office simulation
 * @author HP
 */
public class Launcher {

	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		Simulation sim = new Simulation();
		//study();
		//studyHigh();
	}
	
	/**
	 * Study parameters for easier setup
	 */
	private static void study() {
		for(int i=0; i<10; i++) {
			new Simulation(0.001, 0.002, (i+5)*10, true);
			new Simulation(0.001, 0.004, (i+5)*10, true);
			new Simulation(0.001, 0.006, (i+5)*10, true);
			new Simulation(0.001, 0.008, (i+5)*10, true);
			new Simulation(0.001, 0.01, (i+5)*10, true);
			
			new Simulation(0.002, 0.002, (i+5)*10, true);
			new Simulation(0.002, 0.004, (i+5)*10, true);
			new Simulation(0.002, 0.006, (i+5)*10, true);
			new Simulation(0.002, 0.008, (i+5)*10, true);
			new Simulation(0.002, 0.01, (i+5)*10, true);
			
			new Simulation(0.003, 0.002, (i+5)*10, true);
			new Simulation(0.003, 0.004, (i+5)*10, true);
			new Simulation(0.003, 0.006, (i+5)*10, true);
			new Simulation(0.003, 0.008, (i+5)*10, true);
			new Simulation(0.003, 0.001, (i+5)*10, true);
			
			new Simulation(0.004, 0.002, (i+5)*10, true);
			new Simulation(0.004, 0.004, (i+5)*10, true);
			new Simulation(0.004, 0.006, (i+5)*10, true);
			new Simulation(0.004, 0.008, (i+5)*10, true);
			new Simulation(0.004, 0.01, (i+5)*10, true);
			
			new Simulation(0.005, 0.002, (i+5)*10, true);
			new Simulation(0.005, 0.004, (i+5)*10, true);
			new Simulation(0.005, 0.006, (i+5)*10, true);
			new Simulation(0.005, 0.008, (i+5)*10, true);
			new Simulation(0.005, 0.01, (i+5)*10, true);
		}
	}
	
	/**
	 * Study parameters for easier setup
	 */
	private static void studyHigh() {
		for(int i=0; i<10; i++) {
			new Simulation(0.01, 0.02, (i+5)*10, true);
			new Simulation(0.01, 0.04, (i+5)*10, true);
			new Simulation(0.01, 0.06, (i+5)*10, true);
			new Simulation(0.01, 0.08, (i+5)*10, true);
			new Simulation(0.01, 0.1, (i+5)*10, true);
			
			new Simulation(0.02, 0.02, (i+5)*10, true);
			new Simulation(0.02, 0.04, (i+5)*10, true);
			new Simulation(0.02, 0.06, (i+5)*10, true);
			new Simulation(0.02, 0.08, (i+5)*10, true);
			new Simulation(0.02, 0.1, (i+5)*10, true);
			
			new Simulation(0.03, 0.02, (i+5)*10, true);
			new Simulation(0.03, 0.04, (i+5)*10, true);
			new Simulation(0.03, 0.06, (i+5)*10, true);
			new Simulation(0.03, 0.08, (i+5)*10, true);
			new Simulation(0.03, 0.1, (i+5)*10, true);
			
			new Simulation(0.04, 0.02, (i+5)*10, true);
			new Simulation(0.04, 0.04, (i+5)*10, true);
			new Simulation(0.04, 0.06, (i+5)*10, true);
			new Simulation(0.04, 0.08, (i+5)*10, true);
			new Simulation(0.04, 0.1, (i+5)*10, true);
			
			new Simulation(0.05, 0.02, (i+5)*10, true);
			new Simulation(0.05, 0.04, (i+5)*10, true);
			new Simulation(0.05, 0.06, (i+5)*10, true);
			new Simulation(0.05, 0.08, (i+5)*10, true);
			new Simulation(0.05, 0.1, (i+5)*10, true);
		}
	}

}
