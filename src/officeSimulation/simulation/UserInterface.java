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

/**
 * Controls the display of UI
 * @author HP
 *
 */
public class UserInterface {

	private Office office;
	
	/**
	 * Loads data of the current office
	 * @param office
	 */
	public void loadData(Office office) {
		this.office = office;
	}
	
	public void display() {
		System.out.println("==================================================================");
		System.out.println("Lift view:");

		for(int i=office.floors.size()-1; i>=0; i--) {
			Floor f = office.floors.get(i);
			//Print the number of people in idle and queue for every floor
			String output = f.getLevel() + " - X:"+f.idle.size() + ", /\\:" + f.getQueueUpSize() + ", \\/:"+f.getQueueDownSize();
			//Print the lift as [] and : for doors being open
			for(int j=0; j<office.lifts.size(); j++) {
				Lift l = office.lifts.get(j);
				if(l.getCurrentFloor() == f.getLevel()) {
					if(!l.isDoorsOpen()) output += "   [";
					else if(l.stuck) output += "   !";
					else output += "   :";
					for(int k=0; k<l.getPopulation().size(); k++) {
						output += "("+ personSymbol(l.getPopulation().get(k)) +")";
					}
					output += "]";
				}
			}
			System.out.println(output);
		}
	}
	
	public void displayEach() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("Population view:                     (Average wait time: "+ office.getAverageWaitTime() +") (Complaints: " + office.complaints+")");
		for(int i=office.floors.size()-1; i>=0; i--) {
			Floor f = office.floors.get(i);
			String output = f.getLevel()+" - ";
			//Idle enumerate
			for(int j=0; j<f.idle.size(); j++) {
				Person p = f.idle.get(j);
				output += personSymbol(p)+", ";
			}
			//Up enumerate
			ArrayList<Person> tempUp = new ArrayList<Person>(f.queueUp);
			for(int j=0; j<tempUp.size(); j++) {
				Person p = tempUp.get(j);
				output += "/\\"+personSymbol(p)+", ";
			}
			//Up enumerate
			ArrayList<Person> tempUpClient = new ArrayList<Person>(f.queueUpClient);
			for(int j=0; j<tempUpClient.size(); j++) {
				Person p = tempUpClient.get(j);
				output += "/\\"+personSymbol(p)+", ";
			}

			//Down enumerate
			ArrayList<Person> tempDown = new ArrayList<Person>(f.queueDown);
			for(int j=0; j<tempDown.size(); j++) {
				Person p = tempDown.get(j);
				output += "\\/"+personSymbol(p)+", ";
			}
			//Down enumerate
			ArrayList<Person> tempDownClient = new ArrayList<Person>(f.queueDownClient);
			for(int j=0; j<tempDownClient.size(); j++) {
				Person p = tempDownClient.get(j);
				output += "\\/"+personSymbol(p)+", ";
			}
			
			System.out.println(output);
		}
	}
	
	/**
	 * Transforms Person into a string describing its state
	 * @param Person to stringify
	 * @return String describing the state of a person
	 */
	private String personSymbol(Person p) {
		//Maintenance M + how long they are going to stay
		if(p instanceof Maintenance) return new String("M" +"(w"+(((Maintenance)p).visitDuration - p.getDuration())+")");
		//Client C + how long they are going to stay + how long they waited in a queue
		else if(p instanceof Client) return new String("C" + "(w"+(((Client)p).visitDuration - p.getDuration()) +")(q"+((Client)p).waitTime+")");
		//Developer D + company they are working for, either g or m
		else if(p instanceof Developer) return new String("D" + "("+(((Developer)p).google ? "g" : "m")+")");
		//Employee E
		else if(p instanceof Employee) return new String("E");
		return "";
	}
	
}
