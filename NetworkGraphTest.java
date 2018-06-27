package assignment11;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;

import org.junit.Test;

public class NetworkGraphTest {
	InputStream q3is = NetworkGraphTest.class.getResourceAsStream("flights-2017-q3.csv");
	NetworkGraph q3Flights = new NetworkGraph(q3is);
	
	@Test
	public void testShortestDistancePath() {
		BestPath shortestDistancePath = q3Flights.getBestPath("MOB", "ACV", FlightCriteria.DISTANCE);
		ArrayList<String> expectedPath = new ArrayList<String>();
		expectedPath.add("MOB");
		expectedPath.add("DFW");
		expectedPath.add("SFO");
		expectedPath.add("ACV");
		double expectedDistance = 2253;
		assertTrue(expectedDistance == shortestDistancePath.getPathCost());
		for (int i = 0; i < expectedPath.size(); i++) {
			assertTrue(expectedPath.get(i).equals(shortestDistancePath.getPath().get(i)));
		}
	}
	
	@Test
	public void testShortestDistancePathCarrier() {
		BestPath shortestDistancePath = q3Flights.getBestPath("SFO", "DFW", FlightCriteria.DISTANCE, "DL");
		ArrayList<String> expectedPath = new ArrayList<String>();
		expectedPath.add("SFO");
		expectedPath.add("SLC");
		expectedPath.add("DFW");
		double expectedDistance = 1588;
		assertTrue(expectedDistance == shortestDistancePath.getPathCost());
		for (int i = 0; i < expectedPath.size(); i++) {
			assertTrue(expectedPath.get(i).equals(shortestDistancePath.getPath().get(i)));
		}
	}
	
	@Test
	public void testShortestTimePath() {
	    BestPath shortestTimePath = q3Flights.getBestPath("MOB", "SLC", FlightCriteria.TIME);
		ArrayList<String> expectedPath = new ArrayList<String>();
		expectedPath.add("MOB");
		expectedPath.add("DFW");
		expectedPath.add("SLC");
		double expectedTime = 269.25;
		assertTrue(Math.abs(shortestTimePath.getPathCost() - expectedTime) <= 0.01);
		for (int i = 0; i < expectedPath.size(); i++) {
			assertTrue(expectedPath.get(i).equals(shortestTimePath.getPath().get(i)));
		}
	}
	
	@Test
	public void testCheapestPath() {
		BestPath cheapestPath = q3Flights.getBestPath("LAS", "LAX", FlightCriteria.PRICE);
		ArrayList<String> expectedPath = new ArrayList<String>();
		expectedPath.add("LAS");
		expectedPath.add("LAX");
		double expectedPrice = 138.39;
		assertTrue(Math.abs(cheapestPath.getPathCost() - expectedPrice) <= 0.0001);
		for (int i = 0; i < expectedPath.size(); i++) {
			assertTrue(expectedPath.get(i).equals(cheapestPath.getPath().get(i)));

		}
	}
	
	@Test
	public void testNoOriginPath() {
		BestPath emptyPath = q3Flights.getBestPath("123", "SLC", FlightCriteria.PRICE);
		ArrayList<String> expectedPath = new ArrayList<String>();
		double expectedWeight = 0.0;
		assertTrue(expectedWeight == emptyPath.getPathCost());
		assertTrue(expectedPath.size() == emptyPath.getPath().size());
	}
	
	@Test
	public void testNoDestinationPath() {
		BestPath emptyPath = q3Flights.getBestPath("SLC", "123", FlightCriteria.DELAY);
		ArrayList<String> expectedPath = new ArrayList<String>();
		double expectedWeight = 0.0;
		assertTrue(expectedWeight == emptyPath.getPathCost());
		assertTrue(expectedPath.size() == emptyPath.getPath().size());
	}
	
	@Test
	public void testNoAirportParameters() {
		BestPath emptyPath = q3Flights.getBestPath("456", "123", FlightCriteria.DISTANCE);
		ArrayList<String> expectedPath = new ArrayList<String>();
		double expectedWeight = 0.0;
		assertTrue(expectedWeight == emptyPath.getPathCost());
		assertTrue(expectedPath.size() == emptyPath.getPath().size());
	}
	
	@Test
	public void testNoAirportparametersCarrier() {
		BestPath emptyPath = q3Flights.getBestPath("456", "123", FlightCriteria.DISTANCE, "AA");
		ArrayList<String> expectedPath = new ArrayList<String>();
		double expectedWeight = 0.0;
		assertTrue(expectedWeight == emptyPath.getPathCost());
		assertTrue(expectedPath.size() == emptyPath.getPath().size());
	}
	
	@Test
	public void testNoDestinationPathCarrier() {
		BestPath emptyPath = q3Flights.getBestPath("SLC", "123", FlightCriteria.DELAY, "DL");
		ArrayList<String> expectedPath = new ArrayList<String>();
		double expectedWeight = 0.0;
		assertTrue(expectedWeight == emptyPath.getPathCost());
		assertTrue(expectedPath.size() == emptyPath.getPath().size());
	}
	
	@Test
	public void testNoOriginPathCarrier() {
		BestPath emptyPath = q3Flights.getBestPath("123", "SLC", FlightCriteria.PRICE, "UA");
		ArrayList<String> expectedPath = new ArrayList<String>();
		double expectedWeight = 0.0;
		assertTrue(expectedWeight == emptyPath.getPathCost());
		assertTrue(expectedPath.size() == emptyPath.getPath().size());
	}
	
	@Test
	public void testNullCarrier() {
		BestPath shortestDistancePath = q3Flights.getBestPath("MOB", "ACV", FlightCriteria.DISTANCE, null);
		ArrayList<String> expectedPath = new ArrayList<String>();
		expectedPath.add("MOB");
		expectedPath.add("DFW");
		expectedPath.add("SFO");
		expectedPath.add("ACV");
		double expectedDistance = 2253;
		assertTrue(expectedDistance == shortestDistancePath.getPathCost());
		for (int i = 0; i < expectedPath.size(); i++) {
			assertTrue(expectedPath.get(i).equals(shortestDistancePath.getPath().get(i)));
		}
	}

}
