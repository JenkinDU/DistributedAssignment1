package ca.concordia.dfrs.bean;

import java.io.Serializable;

public class Flight implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -805288756140079584L;
	public static final String FIRST_CLASS = "First";
	public static final String BUSINESS_CLASS = "Business";
	public static final String ECONOMY_CLASS = "Economy";
	public static final String ALL_CLASS = "All";
	
	public static final String DEPARTURE = "DEPARTURE";
	public static final String DATE = "DATE";
	public static final String DESTINATION = "DESTINATION";
	public static final String F_SEATS = "F_SEATS";
	public static final String B_SEATS = "B_SEATS";
	public static final String E_SEATS = "E_SEATS";
	
	private int recordID = 0;
	
	private String flightName = "";

	private String departure = "";
	private String departureDate = "";
	private String destination = "";
	private String achieveDate = "";
	
	private int totalBusinessTickets = 0;
	private int totalFirstTickets = 0;
	private int totalEconomyTickets = 0;
	private int balanceBusinessTickets = 0;
	private int balanceFirstTickets = 0;
	private int balanceEconomyTickets = 0;
	
	public String getFlightName() {
		return flightName;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getAchieveDate() {
		return achieveDate;
	}

	public void setAchieveDate(String achieveDate) {
		this.achieveDate = achieveDate;
	}

	public int getTotalBusinessTickets() {
		return totalBusinessTickets;
	}

	public void setTotalBusinessTickets(int totalBusinessTickets) {
		this.totalBusinessTickets = totalBusinessTickets;
	}

	public int getTotalFirstTickets() {
		return totalFirstTickets;
	}

	public void setTotalFirstTickets(int totalFirstTickets) {
		this.totalFirstTickets = totalFirstTickets;
	}

	public int getTotalEconomyTickets() {
		return totalEconomyTickets;
	}

	public void setTotalEconomyTickets(int totalEconomyTickets) {
		this.totalEconomyTickets = totalEconomyTickets;
	}
	
	public synchronized boolean sellTicket(String type) {
		if (BUSINESS_CLASS.equals(type)) {
			if (balanceBusinessTickets == 0)
				return false;
			else
				balanceBusinessTickets--;
		} else if (FIRST_CLASS.equals(type)) {
			if (balanceFirstTickets == 0)
				return false;
			else
				balanceFirstTickets--;
		} else if (ECONOMY_CLASS.equals(type)) {
			if (balanceEconomyTickets == 0)
				return false;
			else
				balanceEconomyTickets--;
		}

		return true;
	}

	public int getRecordID() {
		return recordID;
	}

	public void setRecordID(int recordID) {
		this.recordID = recordID;
	}

	@Override
	public String toString() {
		return "Flight [recordID=" + recordID + ", flightName=" + flightName + ", departure=" + departure
				+ ", departureDate=" + departureDate + ", destination=" + destination + ", achieveDate=" + achieveDate
				+ ", totalBusinessTickets=" + totalBusinessTickets + ", totalFirstTickets=" + totalFirstTickets
				+ ", totalEconomyTickets=" + totalEconomyTickets + ", balanceBusinessTickets=" + balanceBusinessTickets
				+ ", balanceFirstTickets=" + balanceFirstTickets + ", balanceEconomyTickets=" + balanceEconomyTickets
				+ "]";
	}
	
}
