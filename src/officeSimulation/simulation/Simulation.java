/**
 * 
 */
package officeSimulation.simulation;

import java.util.Scanner;

/**
 * Class representing the office simulation
 * @author HP
 */
public class Simulation implements ITickable {

	/**
	 * Number of floors in the office
	 */
	private int floorNumber = 7;
	/**
	 * Number of lifts in the office
	 */
	private int liftNumber = 2;
	/**
	 * Capacity of a lift
	 */
	private int liftCapacity = 4;
	/**
	 * Probability of a maintenance crew arriving
	 */
	private double maintenanceProbability = 0.005;
	/**
	 * Probability of employees and developers changing floors
	 */
	private double p = 0.005;
	/**
	 * Probability of new clients arriving
	 */
	private double q = 0.005;
	/*
	 * Number of employees to spawn in the initial tick
	 */
	private int employeeNumber = 10;
	/**
	 * Number of employees to spawn in the initial tick
	 */
	private int developerNumber = 10;
	/**
	 * Number of ticks to simulate
	 */
	private int tickNumber = 2880; //2880=8hours
	/**
	 * Seed for the hole simulation
	 */
	private int seed = 123259;
	/**
	 * Decide if UI should be displayed
	 */
	private boolean statisticalUi = false;

	/**
	 * Office that is being simulated
	 */
	private Office office;
	/**
	 * Class spawning and maintaining people in the office
	 */
	private PeopleController peopleController;
	/**
	 * Class managing lifts 
	 */
	private LiftController liftController;
	/**
	 * Class displaying the UI based on the current state of office
	 */
	private UserInterface userInterface;

	/**
	 * Constructs a new simulation
	 */
	public Simulation(){
		//Initialising variables
		variableSetup();
		
		//Initialising the peopleController with a set seed
		peopleController = new PeopleController(seed);
		//Initialising the office (floors and lifts with it)
		office = new Office(floorNumber, liftNumber, liftCapacity);
		//Initialising liftController
		liftController = new LiftController();
		//Initialising UI
		userInterface = new UserInterface();
		
		//Making the initial employee and developer spawn
		peopleController.initialSpawn(employeeNumber, developerNumber, office.floors);
		//Extracting newly spawned people from peopleController to office
		office.addPeople(peopleController.extract());
		
		//Starting the simulation for TICK_NUMBER of ticks
		run(tickNumber);
	}
	
	/**
	 * Alternative constructor used for testing
	 * @param p
	 * @param q
	 * @param seed
	 */
	public Simulation(double p, double q, int seed, boolean ui) {
		this.p = p;
		this.q = q;
		this.seed = seed;
		this.statisticalUi = ui;
		peopleController = new PeopleController(seed);
		office = new Office(floorNumber, liftNumber, liftCapacity);
		liftController = new LiftController();
		userInterface = new UserInterface();
		peopleController.initialSpawn(employeeNumber, developerNumber, office.floors);
		office.addPeople(peopleController.extract());
		run(tickNumber);
	}
	
	/**
	 * Performs all tick-based actions
	 */
	public void tick() {
		//Client and Maintenance spawn
		peopleController.spawn(maintenanceProbability, q, floorNumber);
		//Adding new Clients and Maintenance
		office.addPeople(peopleController.extract());
		//Update destinations / visit time / clear clients and maintenance that has finished work
		office.floors = peopleController.update(office.floors, p);
		//Adds new complaints
		office.complaints += peopleController.gatherComplaints(office.floors);
		//Clears people that want to leave the office
		office.floors = peopleController.clear(office.floors);
		//Update queues
		office.floors = peopleController.updateQueues(office.floors);
		
		//Loads current data to liftController
		liftController.loadData(office);
		//Performs a tick-based action for lift (movement / loading / opening / waiting)
		liftController.liftsAction();
		//Updates the office
		office = liftController.extract();
		
		//Loads current data to UI
		userInterface.loadData(office);
		//Displays UI based on currently loaded data
		if(!statisticalUi) {
			userInterface.display();
			userInterface.displayEach();
		}

		
	}
	
	/**
	 * Asks if the user wants to provide starting parameters
	 */
	private void variableSetup(){
		System.out.println("--------------------------------------------------------");
		System.out.println("            Welcome to Office Simulation");
		System.out.println("--------------------------------------------------------");
		String a;
		Scanner S = new Scanner(System.in);
		do {
			System.out.println("      Would you like to use default parameters? [y/n]");
			a = S.next();
		} while (!a.equals("y") && !a.equals("n"));
		
		//Using custom parameter
		if(a.equals("n")) {
			//Temporary variables analogous to office fields
			int floorNumberTemp = floorNumber;
			int liftNumberTemp = liftNumber;
			int liftCapacityTemp = liftCapacity;
			double maintenanceProbabilityTemp = maintenanceProbability;
			double pTemp = p;
			double qTemp = q;
			int employeeNumberTemp = employeeNumber;
			int developerNumberTemp = developerNumber;
			int tickNumberTemp = tickNumber;
			int seedTemp = seed;
			//Tries to get all the necessary inputs from the user
			try {
				do {
					System.out.println("How many floors? (minimum 2): ");
					floorNumberTemp = S.nextInt();
				} while (floorNumberTemp < 2);
				do {
					System.out.println("How many lifts? (minimum 1): ");
					liftNumberTemp = S.nextInt();
				} while (liftNumberTemp < 1);
				do {
					System.out.println("Lift capacity? (minimum 4): ");
					liftCapacityTemp = S.nextInt();
				} while (liftCapacityTemp < 4);
				do {
					System.out.println("Probability of a maintenance? (between 0 and 1): ");
					maintenanceProbabilityTemp = S.nextDouble();
				} while (maintenanceProbabilityTemp > 1 || maintenanceProbabilityTemp < 0 );
				do {
					System.out.println("Probability of a developer and employee changing floors? (between 0 and 1): ");
					pTemp = S.nextDouble();
				} while (pTemp > 1 || pTemp < 0);
				do {
					System.out.println("Probability of a client arriving? (between 0 and 1): ");
					qTemp = S.nextDouble();
				} while (qTemp > 1 || qTemp < 0);
				do {
					System.out.println("Number of employees? (minimum 0): ");
					employeeNumberTemp = S.nextInt();
				} while (employeeNumberTemp < 0);
				do {
					System.out.println("Number of developers? (minimum 0): ");
					developerNumberTemp = S.nextInt();
				} while (developerNumberTemp < 0);
				do {
					System.out.println("Number of ticks to simulate? (minimum 1): ");
					tickNumberTemp = S.nextInt();
				} while (tickNumberTemp < 1);
				do {
					System.out.println("What is the seed? (minimum 0): ");
					seedTemp = S.nextInt();
				} while (seedTemp < 0);
				
				//Updating
				floorNumber = floorNumberTemp;
				liftNumber = liftNumberTemp;
				liftCapacity = liftCapacityTemp;
				maintenanceProbability = maintenanceProbabilityTemp;
				p = pTemp;
				q = qTemp;
				employeeNumber = employeeNumberTemp;
				developerNumber = developerNumberTemp;
				tickNumber = tickNumberTemp;
				seed = seedTemp;
				
				S.close();
				
			}
			catch(Exception e){
				//retries
				System.out.println("Input error. Restarting.");
				variableSetup();
			}
		}
	}
	
	/**
	 * Runs the simulation
	 * @param ticks amount of ticks to be run
	 */
	private void run(int tickNumber) {
		for(int i=0; i<tickNumber; i++) {tick();}
		if(statisticalUi) {
			System.out.println(p+" "+q+" "+seed+" "+office.getAverageWaitTime() +" "+ office.complaints);
		}
	}
}
