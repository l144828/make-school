package assignment11;

import java.util.HashMap;

/**
 * Airport Class contains the vertices of the NetworkGraph class.
 * It contains a list of Flights to connect to other airports, and
 * a weight used to determine the best path.
 * @author Peter Forsling & Jabrail Ahmed
 *
 */
public class Airport {
	/** name of the airport */
	private String name;
	/** The collection of flights within the airport */
	public HashMap<String, Flight> flights;
	/** If the airport has been visited during findBestPath */
	public boolean visited;
	/** The previous airport used for creating a path */
	private Airport cameFrom;
	/** The weight of an airport used for creating a path */
	private double weight;

	/**
	 * Constructs an instance of Airport Class
	 * @param name - The name of the airport
	 */
	public Airport(String name) {
		this.name = name;
		flights = new HashMap<String, Flight>();
		visited = false;
		cameFrom = null;
		weight = Double.MAX_VALUE;
	}

	/**
	 * Getter method for name of the airport
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Adds a flight to the airport's collection of flights
	 * @param toBeAdded - the flight to be added
	 */
	public void addFlight(Flight toBeAdded) {
		flights.put(toBeAdded.getDestination().getName(), toBeAdded);
	}

	/**
	 * Determines if the airport has the given flight
	 * @param toBeSearched - the flight to be searched
	 * @return true if the airport has the flight, false if not
	 */
	public boolean hasFlight(Flight toBeSearched) {
		return flights.containsKey(toBeSearched.getDestination().getName());
	}

	/**
	 * returns a flight that is being searched for
	 * @param destination - the name of the destination airport
	 * @return - the flight that is being searched for
	 */
	public Flight getFlight(Airport destination) {
		return flights.get(destination.getName());
	}

	/**
	 * Replaces a duplicate old flight with a new flight
	 * @param toBeUpdated - the flight to replace the old flight
	 */
	public void updateFlight(Flight toBeUpdated) {
		flights.replace(toBeUpdated.getDestination().getName(), toBeUpdated);
	}
	
	/**
	 * Getter method for the weight of the airport
	 * @return weight
	 */
	public double getWeight() {
		return weight;
	}
	
	/**
	 * Sets the weight of the airport to a specified value
	 * @param setWeight - the value to set the weight of the airport
	 */
	public void setStartWeight(double setWeight) {
		weight = setWeight;
	}
	
	/**
	 * Sets the cameFrom to the given airport
	 * @param cameFrom the airport to set the cameFrom
	 */
	public void setCameFrom(Airport cameFrom) {
		this.cameFrom = cameFrom;
	}
	
	/**
	 * Getter method for cameFrom
	 * @return cameFrom
	 */
	public Airport getCameFrom() {
		return cameFrom;
	}
	
	/**
	 * combines the values of two different airport weights
	 * together
	 * @param pathWeight - weight of the entire path
	 * @param flightWeight - weight of the flight to the next airport
	 */
	public void adjustWeight(double pathWeight, double flightWeight) {
		weight = pathWeight + flightWeight;
	}
}
