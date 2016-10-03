package ca.concordia.dfrs.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.concordia.dfrs.bean.Flight;
import ca.concordia.dfrs.server.DFRSServerMTL;
import ca.concordia.dfrs.server.DFRSServerNDL;
import ca.concordia.dfrs.server.DFRSServerWST;

public class FlightData {
	private static FlightData instance;
	private HashMap<String, List<Flight>> data;
	private int recordID = 0;
	// private int totalTickets = 0;
	// private int totalBalance = 0;
//	private int totalBusinessTickets = 0;
//	private int totalFirstTickets = 0;
//	private int totalEconomyTickets = 0;
//	private int balanceBusinessTickets = 0;
//	private int balanceFirstTickets = 0;
//	private int balanceEconomyTickets = 0;

	private FlightData() {
		data = new HashMap<String, List<Flight>>();
	}

	public static synchronized FlightData getInstance() {
		if (instance == null) {
			instance = new FlightData();
		}
		return instance;
	}

	public synchronized List<Flight> initData(String name) {
		List<Flight> o = data.get(name);
		if (o == null) {
			data.put(name, addInitFlight(name));
		}
		return data.get(name);
	}
	
	public synchronized void addFlight(String name, Flight f) {
		List<Flight> list = data.get(name);
		if(list == null)
			list = new ArrayList<Flight>();
		if(f.getRecordID() <= 0)
			f.setRecordID(++recordID);
		list.add(f);
	}
	
	private ArrayList<Flight> addInitFlight(String name) {
		ArrayList<Flight> flight = new ArrayList<Flight>();
		
		if(DFRSServerMTL.SERVER_NAME.equals(name)) {
			
			Flight f = new Flight();
			f.setFlightName("CZ 101");
			f.setDeparture("Montreal");
			f.setDestination("Washington");
			f.setDepartureDate("8:00am");
			f.setAchieveDate("10:00am");
			f.setTotalBusinessTickets(50);
			f.setTotalFirstTickets(20);
			f.setTotalEconomyTickets(300);
			f.setRecordID(++recordID);
			flight.add(f);
			
			f = new Flight();
			f.setFlightName("CZ 201");
			f.setDeparture("Montreal");
			f.setDestination("New Delhi");
			f.setDepartureDate("2:00am");
			f.setAchieveDate("11:00pm");
			f.setTotalBusinessTickets(50);
			f.setTotalFirstTickets(20);
			f.setTotalEconomyTickets(300);
			f.setRecordID(++recordID);
			flight.add(f);
			
//			f = new Flight();
//			f.setFlightName("CZ 301");
//			f.setDeparture("Montreal");
//			f.setDestination("Washington");
//			f.setDepartureDate("9:00am");
//			f.setAchieveDate("12:00am");
//			f.setTotalBusinessTickets(50);
//			f.setTotalFirstTickets(20);
//			f.setTotalEconomyTickets(300);
//			flight.add(f);
//			
//			f = new Flight();
//			f.setFlightName("CZ 401");
//			f.setDeparture("Montreal");
//			f.setDestination("New Delhi");
//			f.setDepartureDate("6:00am");
//			f.setAchieveDate("11:00pm");
//			f.setTotalBusinessTickets(50);
//			f.setTotalFirstTickets(20);
//			f.setTotalEconomyTickets(300);
//			flight.add(f);
//			
//			f = new Flight();
//			f.setFlightName("CZ 501");
//			f.setDeparture("Montreal");
//			f.setDestination("Washington");
//			f.setDepartureDate("7:00am");
//			f.setAchieveDate("10:00am");
////			f.setDepartureDate("Oct. 9 2016 8:00am");
////			f.setAchieveDate("Oct. 9 2016 10:00am");
//			f.setTotalBusinessTickets(50);
//			f.setTotalFirstTickets(20);
//			f.setTotalEconomyTickets(300);
//			flight.add(f);
		} else if(DFRSServerWST.SERVER_NAME.equals(name)) {
			Flight f = new Flight();
			f.setFlightName("CW 101");
			f.setDeparture("Washington");
			f.setDestination("Montreal");
			f.setDepartureDate("8:00am");
			f.setAchieveDate("10:00am");
			f.setTotalBusinessTickets(50);
			f.setTotalFirstTickets(20);
			f.setTotalEconomyTickets(300);
			f.setRecordID(++recordID);
			flight.add(f);
			
			f = new Flight();
			f.setFlightName("CW 201");
			f.setDeparture("Washington");
			f.setDestination("New Delhi");
			f.setDepartureDate("2:00am");
			f.setAchieveDate("11:00pm");
			f.setTotalBusinessTickets(50);
			f.setTotalFirstTickets(20);
			f.setTotalEconomyTickets(300);
			f.setRecordID(++recordID);
			flight.add(f);
		} else if(DFRSServerNDL.SERVER_NAME.equals(name)) {
			Flight f = new Flight();
			f.setFlightName("CN 101");
			f.setDeparture("New Delhi");
			f.setDestination("Washington");
			f.setDepartureDate("1:00am");
			f.setAchieveDate("10:00pm");
			f.setTotalBusinessTickets(50);
			f.setTotalFirstTickets(20);
			f.setTotalEconomyTickets(300);
			f.setRecordID(++recordID);
			flight.add(f);
			
			f = new Flight();
			f.setFlightName("CN 201");
			f.setDeparture("New Delhi");
			f.setDestination("Montreal");
			f.setDepartureDate("2:00am");
			f.setAchieveDate("11:00pm");
			f.setTotalBusinessTickets(50);
			f.setTotalFirstTickets(20);
			f.setTotalEconomyTickets(300);
			f.setRecordID(++recordID);
			flight.add(f);
		}
		
		return flight;
	}
}
