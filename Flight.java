package assignment11;

import java.util.ArrayList;

/**
 * Flight Class contains the edges of the NetworkGraph class.
 * Flights have an origin airport, a destination airport, and
 * a series of different weight values.
 * @author Peter Forsling & Jabrail Ahmed
 * @version April 20 2018
 *
 */
public class Flight {

	/** The airport where the flight comes from */
	private Airport origin;
	/** The airport where the flight goes to */
	private Airport destination;
	/** The list of flight carriers available for the flight */
	private ArrayList<String> carriers;
	/** Amount of minutes the flight is delayed */
	private double delay;
	/** 0 if not canceled, 1 if canceled */
	private double canceled;
	/** Elapsed time of flight in minutes */
	private double time;
	/** Measured distance of flight in miles(?) */
	private double distance;
	/** How much the flight costs */
	private double cost;
	/** The amount of duplicate flights used for averaging purposes */
	private int flightCount;

	/**
	 * Constructs an instance of the Flight class with the given parameters
	 * @param origin - Airport where the flight comes from
	 * @param destination - Airport where the flight goes to
	 * @param carriers - The list of carriers available to fly with
	 * @param delay - The delay from this flight in minutes
	 * @param canceled - 0 if not canceled, 1 if canceled
	 * @param time - elapsed time of flight in minutes
	 * @param distance - measured distance of flight in miles(?)
	 * @param cost - how much the flight costs
	 * @param flightCount - Used if adding a duplicate flight
	 */
	public Flight(Airport origin, Airport destination, ArrayList<String> carriers, double delay, double canceled, double time,
			      double distance, double cost, int flightCount) {
		this.origin = origin;
		this.destination = destination;
		this.carriers = carriers;
		this.delay = delay;
		this.canceled = canceled;
		this.time = time;
		this.distance = distance;
		this.cost = cost;
		this.flightCount = flightCount;
	}

	/**
	 * Consructs an instance of the Flight class with the given parameters
	 * @param origin - Airport where the flight comes from
	 * @param destination - Airport where the flight goes to
	 * @param carrier - The flight carrier for this flight
	 * @param delay - The delay from this flight in minutes
	 * @param canceled - 0 if not canceled, 1 if canceled
	 * @param time - elapsed time of flight in minutes
	 * @param distance - measured distance of flight in miles(?)
	 * @param cost - how much the flight costs
	 */
	public Flight(Airport origin, Airport destination, String carrier, double delay, double canceled, double time,
			double distance, double cost) {
		this.origin = origin;
		this.destination = destination;
		carriers = new ArrayList<String>();
		carriers.add(carrier);
		this.delay = delay;
		this.canceled = canceled;
		this.time = time;
		this.distance = distance;
		this.cost = cost;
		flightCount = 1;
	}
	
	/**
	 * Constructs an instance of the Flight class with the given parameters
	 * @param origin - Airport where the flight comes from
	 * @param destination - Airport where the flight goes to
	 * @param carriers - The list of carriers available to fly with
	 * @param delay - The delay from this flight in minutes
	 * @param canceled - 0 if not canceled, 1 if canceled
	 * @param time - elapsed time of flight in minutes
	 * @param distance - measured distance of flight in miles(?)
	 * @param cost - how much the flight costs
	 */
	public Flight(Airport origin, Airport destination, ArrayList<String> carriers, double delay, double canceled, double time,
			double distance, double cost) {
		this.origin = origin;
		this.destination = destination;
		this.carriers = carriers;
		this.delay = delay;
		this.canceled = canceled;
		this.time = time;
		this.distance = distance;
		this.cost = cost;
		flightCount = 1;
	}

	/**
	 * Getter method for Origin
	 * @return origin
	 */
	public Airport getOrigin() {
		return origin;
	}

	/**
	 * Getter method for Destination
	 * @return destination
	 */
	public Airport getDestination() {
		return destination;
	}

	/**
	 * Getter method for delay
	 * @return delay
	 */
	public double getDelay() {
		return delay;
	}

	/**
	 * Getter method for canceled
	 * @return canceled
	 */
	public double getCanceled() {
		return canceled;
	}

	/**
	 * Getter method for time
	 * @return time
	 */
	public double getTime() {
		return time;
	}

	/**
	 * Getter method for distance
	 * @return distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Getter method for cost
	 * @return cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Determines which weight to use and returns that weight
	 * of the flight
	 * @param criteria - used to determine which weight to return
	 * @return weight - the weight of the flight based off the criteria
	 */
	public double flightCost(FlightCriteria criteria) {
		double result = 0.0;
		switch (criteria) {
		case PRICE:
			result = cost / flightCount;
			break;
		case DELAY:
			result = delay / flightCount;
			break;
		case DISTANCE:
			result = distance / flightCount;
			break;
		case CANCELED:
			result = canceled / flightCount;
			break;
		case TIME:
			result = time / flightCount;
			break;
		}
		return result;
	}
	
	/**
	 * adds together the two different datasets
	 * of two duplicate flights
	 * @param otherFlight - the duplicate flight with different data
	 * @return the flight with combined data
	 */
	public Flight combineFlightData(Flight otherFlight) {
		for (String carrier : otherFlight.carriers) {
			if (!(carriers.contains(carrier)))
				carriers.add(carrier);
		}	
		double combineDelay = delay + otherFlight.getDelay();
		double combineCanceled = canceled + otherFlight.getCanceled();
		double combineTime = time + otherFlight.getTime();
		double combineDistance = distance + otherFlight.getDistance();
		double combineCost = cost + otherFlight.getCost();
		flightCount++;
		return new Flight(origin, destination, carriers, combineDelay, combineCanceled, combineTime, combineDistance, combineCost, flightCount);
	}
	
	/**
	 * Determines if the given carrier is contained
	 * in the flight's list of carriers
	 * @param carrier - the carrier to be searched
	 * @return true if contained, false if not contained
	 */
	public boolean hasCarrier(String carrier) {
		return carriers.contains(carrier);
	}
}
