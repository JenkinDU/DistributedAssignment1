package ca.concordia.dfrs.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ca.concordia.dfrs.bean.Flight;
import ca.concordia.dfrs.server.DFRSServerMTL;
import ca.concordia.dfrs.server.DFRSServerNDL;
import ca.concordia.dfrs.server.DFRSServerWST;

public class FlightData {
	private static FlightData instance;
	private HashMap<String, List<Flight>> data;
	private int recordID = 0;

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
	
	public synchronized void addNewFlight(String name, Flight f) {
		List<Flight> list = data.get(name);
		if(list == null)
			list = new ArrayList<Flight>();
		if(f.getRecordID() <= 0 || isRecordIdExist(f.getRecordID()))
			f.setRecordID(++recordID);
		list.add(f);
	}
	
	private boolean isRecordIdExist(int id) {
		Iterator iter = this.data.entrySet().iterator();
		int count = 0;
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			ArrayList<Flight> value = (ArrayList<Flight>) entry.getValue();
			for (Flight f : value) {
				if (f != null) {
					if (id == f.getRecordID()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private ArrayList<Flight> addInitFlight(String name) {
		ArrayList<Flight> flight = new ArrayList<Flight>();
		
		if(DFRSServerMTL.SERVER_NAME.equals(name)) {
			
			Flight f = new Flight();
			f.setFlightName("CZ 101");
			f.setDeparture("Montreal");
			f.setDestination("Washington");
			f.setDepartureDate("20161010");
			f.setAchieveDate("20161011");
			f.setTotalBusinessTickets(50);
			f.setTotalFirstTickets(20);
			f.setTotalEconomyTickets(300);
			f.setRecordID(++recordID);
			flight.add(f);
			
			f = new Flight();
			f.setFlightName("CZ 201");
			f.setDeparture("Montreal");
			f.setDestination("New Delhi");
			f.setDepartureDate("20161010");
			f.setAchieveDate("20161012");
			f.setTotalBusinessTickets(50);
			f.setTotalFirstTickets(20);
			f.setTotalEconomyTickets(300);
			f.setRecordID(++recordID);
			flight.add(f);
		} else if(DFRSServerWST.SERVER_NAME.equals(name)) {
			Flight f = new Flight();
			f.setFlightName("CW 101");
			f.setDeparture("Washington");
			f.setDestination("Montreal");
			f.setDepartureDate("20161011");
			f.setAchieveDate("20161012");
			f.setTotalBusinessTickets(50);
			f.setTotalFirstTickets(20);
			f.setTotalEconomyTickets(300);
			f.setRecordID(++recordID);
			flight.add(f);
			
			f = new Flight();
			f.setFlightName("CW 201");
			f.setDeparture("Washington");
			f.setDestination("New Delhi");
			f.setDepartureDate("20161011");
			f.setAchieveDate("20161012");
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
			f.setDepartureDate("20161012");
			f.setAchieveDate("20161013");
			f.setTotalBusinessTickets(50);
			f.setTotalFirstTickets(20);
			f.setTotalEconomyTickets(300);
			f.setRecordID(++recordID);
			flight.add(f);
			
			f = new Flight();
			f.setFlightName("CN 201");
			f.setDeparture("New Delhi");
			f.setDestination("Montreal");
			f.setDepartureDate("20161012");
			f.setAchieveDate("20161013");
			f.setTotalBusinessTickets(50);
			f.setTotalFirstTickets(20);
			f.setTotalEconomyTickets(300);
			f.setRecordID(++recordID);
			flight.add(f);
		}
		
		return flight;
	}
}
