/**
 * 
 */
package assignment11;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * <p>
 * This class represents a graph of flights and airports along with specific
 * data about those flights. It is recommended to create an airport class and a
 * flight class to represent nodes and edges respectively. There are other ways
 * to accomplish this and you are free to explore those.
 * </p>
 * 
 * <p>
 * Testing will be done with different criteria for the "best" path so be sure
 * to keep all information from the given file. Also, before implementing this
 * class (or any other) draw a diagram of how each class will work in relation
 * to the others. Creating such a diagram will help avoid headache and confusion
 * later.
 * </p>
 * 
 * <p>
 * Be aware also that you are free to add any member variables or methods needed
 * to completed the task at hand
 * </p>
 * 
 * @author CS2420 Teaching Staff - Spring 2018, Peter Forsling & Jabrail Ahmed
 */
public class NetworkGraph {

	/**
	 * Collection of airports in the graph, the key is a string of the airport's
	 * code
	 */
	HashMap<String, Airport> airports;

	/**
	 * <p>
	 * Constructs a NetworkGraph object and populates it with the information
	 * contained in the given file. See the sample files or a randomly generated one
	 * for the proper file format.
	 * </p>
	 * 
	 * <p>
	 * You will notice that in the given files there are duplicate flights with some
	 * differing information. That information should be averaged and stored
	 * properly. For example some times flights are canceled and that is represented
	 * with a 1 if it is and a 0 if it is not. When several of the same flights are
	 * reported totals should be added up and then reported as an average or a
	 * probability (value between 0-1 inclusive).
	 * </p>
	 * 
	 * @param flightInfo
	 *            - The inputstream to the flight data. The format is a *.csv(comma
	 *            separated value) file
	 * 
	 */
	public NetworkGraph(InputStream flightInfo) {
		airports = new HashMap<String, Airport>();
		Scanner scan = new Scanner(flightInfo);
		scan.nextLine(); // Skips first line, which contains column headers
		while (scan.hasNextLine()) { //scan.hasNext()
			String[] flightData = scan.nextLine().split(","); //The line in the file as an Array
			Airport flightOrigin = new Airport(flightData[0]);
			if (!(airports.containsKey(flightOrigin.getName()))) {
				airports.put(flightOrigin.getName(), flightOrigin);
			} else // If the airport exists in the graph, pull it out
				flightOrigin = airports.get(flightOrigin.getName());
			
				Airport flightDestination = new Airport(flightData[1]);

			if (!(airports.containsKey(flightDestination.getName()))) {
				airports.put(flightDestination.getName(), flightDestination);
			} else // If the airport exists in the graph, pull it out
				flightDestination = airports.get(flightDestination.getName());

			// The entire line in the file as a flight
			Flight scanFlight = new Flight(flightOrigin, flightDestination, flightData[2], Double.parseDouble(flightData[3]),
					Double.parseDouble(flightData[4]), Double.parseDouble(flightData[5]), Double.parseDouble(flightData[6]), 
					Double.parseDouble(flightData[7]));

			if (flightOrigin.hasFlight(scanFlight)) {
				Flight sameFlight = flightOrigin.getFlight(scanFlight.getDestination());
				sameFlight = sameFlight.combineFlightData(scanFlight);
				flightOrigin.updateFlight(sameFlight);
			} else // If the origin airport does not have the flight
				flightOrigin.addFlight(scanFlight);

			// Update the graph with the updated airport data
			airports.replace(flightOrigin.getName(), flightOrigin);
			airports.replace(flightDestination.getName(), flightDestination);

		}
		scan.close();
	}

	/**
	 * This method returns a BestPath object containing information about the best
	 * way to fly to the destination from the origin. "Best" is defined by the
	 * FlightCriteria parameter <code>enum</code>. This method should throw no
	 * exceptions and simply return a BestPath object with information dictating the
	 * result. If the destination or origin is not contained in this instance of
	 * NetworkGraph, simply return a BestPath object with an empty path (not a
	 * <code>null</code> path) and a path cost of 0. If origin or destination are
	 * <code>null</code>, also return a BestPath object with an empty path and a
	 * path cost of 0 . If there is no path in this NetworkGraph from origin to
	 * destination, also return a BestPath with an empty path and a path cost of 0.
	 * 
	 * @param origin
	 *            - The starting location to find a path from. This should be a 3
	 *            character long string denoting an airport.
	 * 
	 * @param destination
	 *            - The destination location from the starting airport. Again, this
	 *            should be a 3 character long string denoting an airport.
	 * 
	 * @param criteria
	 *            - This enum dictates the definition of "best". Based on this value
	 *            a path should be generated and return.
	 * 
	 * @return - An object containing path information including origin,
	 *         destination, and everything in between.
	 */
	public BestPath getBestPath(String origin, String destination, FlightCriteria criteria) {
		Airport start = airports.get(origin);
		if (start == null) //If the origin does not exist
			return new BestPath(new ArrayList<String>(), 0.0);
		
		Airport goal = airports.get(destination);
		if (goal == null) //If the destination does not exist
			return new BestPath(new ArrayList<String>(), 0.0);
		
		start.setCameFrom(null);
		start.setStartWeight(0.0);
		start.visited = false;
		Airport current = start;
		PriorityQueue<Airport> pq = new PriorityQueue<Airport>((lhs, rhs) -> (int) (lhs.getWeight() - rhs.getWeight()));

		// Reset all airport weights except start to infinity and all cameFroms to null
		for (String airportKey : airports.keySet()) {
			if (airports.get(airportKey) != start) {
				airports.get(airportKey).setStartWeight(Double.MAX_VALUE);
				airports.get(airportKey).setCameFrom(null);
				airports.get(airportKey).visited = false;
			}
		}

		pq.add(current);

		while (!(pq.isEmpty())) {
			current = pq.remove();
			String currentName = current.getName();

			if (currentName.equals(destination))
				return drawPath(goal, criteria);

			// For each neighbor of current
			for (String flightKey : current.flights.keySet()) {
				Flight flight = current.flights.get(flightKey);
				Airport flightDestination = flight.getDestination();
				if (flight.flightCost(criteria) > 0) { //if not visited
					Airport flightOrigin = flight.getOrigin();
					if (flightDestination.getWeight() > (flightOrigin.getWeight() + flight.flightCost(criteria))) {
						flightDestination.setCameFrom(current);
						flightDestination.adjustWeight(flightOrigin.getWeight(), flight.flightCost(criteria));
						current.visited = true;
						pq.add(flightDestination);
					}
				}
			}

		}
		// This return statement will be reached when there
		// is no path, or if any of the airports do not exist
		return new BestPath(new ArrayList<String>(), 0.0);
	}

	/**
	 * <p>
	 * This overloaded method should do the same as the one above only when looking
	 * for paths skip the ones that don't match the given airliner.
	 * </p>
	 * 
	 * @param origin
	 *            - The starting location to find a path from. This should be a 3
	 *            character long string denoting an airport.
	 * 
	 * @param destination
	 *            - The destination location from the starting airport. Again, this
	 *            should be a 3 character long string denoting an airport.
	 * 
	 * @param criteria
	 *            - This enum dictates the definition of "best". Based on this value
	 *            a path should be generated and return.
	 * 
	 * @param airliner
	 *            - a string dictating the airliner the user wants to use
	 *            exclusively. Meaning no flights from other airliners will be
	 *            considered.
	 *            
	 * 
	 * @return - An object containing path information including origin,
	 *         destination, and everything in between.
	 */
	public BestPath getBestPath(String origin, String destination, FlightCriteria criteria, String airliner) {
		if (airliner == null)
			return getBestPath(origin, destination, criteria);
		
		Airport start = airports.get(origin);
		if (start == null) //If the origin does not exist
			return new BestPath(new ArrayList<String>(), 0.0);
		
		Airport goal = airports.get(destination);
		if (goal == null) //If the destination does not exist
			return new BestPath(new ArrayList<String>(), 0.0);
		
		start.setCameFrom(null);
		start.setStartWeight(0.0);
		start.visited = true;
		Airport current = start;
		PriorityQueue<Airport> pq = new PriorityQueue<Airport>((lhs, rhs) -> (int) (lhs.getWeight() - rhs.getWeight()));

		// Reset all airport weights except start to infinity and all cameFroms to null
		for (String airportKey : airports.keySet()) {
			if (airports.get(airportKey) != start) {
				airports.get(airportKey).setStartWeight(Double.MAX_VALUE);
				airports.get(airportKey).setCameFrom(null);
				airports.get(airportKey).visited = false;
			}
		}

		pq.add(current);

		while (!pq.isEmpty()) {
			current = pq.remove();
			String currentName = current.getName();

			if (currentName.equals(destination)) {
				return drawPath(goal, criteria);
			}

			// For each neighbor of current
			for (String flightKey : current.flights.keySet()) {
				Flight flight = current.flights.get(flightKey);

				// Only work with flights that have the given carrier as an option
				if (flight.hasCarrier(airliner)) {
					Airport flightDestination = flight.getDestination();
					if (flight.flightCost(criteria) > 0) {
						Airport flightOrigin = flight.getOrigin();
						if (flightDestination.getWeight() > (flightOrigin.getWeight() + flight.flightCost(criteria))) {
							flightDestination.setCameFrom(current);
							flightDestination.adjustWeight(flightOrigin.getWeight(), flight.flightCost(criteria));
							current.visited = true;
							pq.add(flightDestination);
						}
					}
				}
			}
		}
		// This return statement will be reached when there
		// is no path, or if any of the airports do not exist
		return new BestPath(new ArrayList<String>(), 0.0);
	}

	/**
	 * Draws the path as a result of getBestPath
	 * 
	 * @param goal
	 *            - the start of the path
	 * @param criteria
	 *            - provides the way to measure total weight
	 * @return The best path from GetBestPath
	 */
	private BestPath drawPath(Airport goal, FlightCriteria criteria) {
		Airport current = goal;
		ArrayList<String> path = new ArrayList<String>();
		while (current != null) {
			path.add(current.getName());
			current = current.getCameFrom();
		}
		Collections.reverse(path);

		//Take the average of the canceled criteria
		if (criteria == FlightCriteria.CANCELED)
			return new BestPath(path, (goal.getWeight() / (path.size() - 1)));

		return new BestPath(path, goal.getWeight());
	}

}
