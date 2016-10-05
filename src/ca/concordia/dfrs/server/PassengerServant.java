package ca.concordia.dfrs.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import ca.concordia.dfrs.api.IPassenger;
import ca.concordia.dfrs.bean.Flight;
import ca.concordia.dfrs.bean.Ticket;
import ca.concordia.dfrs.database.FlightData;
import ca.concordia.dfrs.database.TicketData;
import ca.concordia.dfrs.utils.Log;
import ca.concordia.dfrs.utils.Result;

public class PassengerServant extends UnicastRemoteObject implements IPassenger {

	private String LOG_PATH = Log.LOG_DIR+"LOG_";
	private String name;
	private String server;
	protected PassengerServant(String name,String server) throws RemoteException {
		super();
		this.name = name;
		this.server = server;
		LOG_PATH=LOG_PATH+server+"/"+server+"_LOG.txt";
		printFlight(server);
	}

	@Override
	public Result bookFlight(String firstName, String lastName, String address, String phone, String destination,
			String date, String ticketClass) throws RemoteException {
		String s = "["+server+"]-"+"Request Book Flight Order Passenger Info Is\n     -FirstName:"+firstName+"\n"
				+"     -lastName:"+lastName +"\n"
				+"     -address:"+address +"\n"
				+"     -phone:"+phone +"\n"
				+"     -destination:"+destination +"\n"
				+"     -date:"+date +"\n"
				+"     -ticketClass:"+ticketClass;
		System.out.println(s);
		Log.i(LOG_PATH, s);
		ArrayList<Flight> flight = (ArrayList<Flight>)FlightData.getInstance().initData(server);
		Result result = new Result();
		boolean r = false;
		String info = "Book Success, Thank you!";
		Flight book = null;
		for(Flight f:flight) {
			if(f.getDeparture().equals(this.name)&&f.getDestination().equals(destination)&&f.getDepartureDate().equals(date)) {
				book = f;
				r = true;
				s = "     -Find Flight From "+this.name+" To "+destination+" On "+date;
				System.out.println(s);
				Log.i(LOG_PATH, s);
				break;
			}
		}
		if(r) {
			if(book!=null&book.sellTicket(ticketClass)) {
				Ticket t = new Ticket(firstName, lastName, address, phone, destination, date, ticketClass, this.name);
				String index = Character.toUpperCase(lastName.charAt(0)) + "" ;
				TicketData.getInstance().addTicket(server, t, index);
			} else {
				r = false;
				info = "Book Failed, We Didn't Have Enough "+ticketClass+" ticket.";
			}
		} else {
			info = "Book Failed, We Didn't Have This Ticket.";
		}
		s = "     -"+info;
		System.out.println(s);
		Log.i(LOG_PATH, s);
		result.setSuccess(r);
		result.setContent(info);
		return result;
	}

	public static void printFlight(String server) {
		List<Flight> flight = FlightData.getInstance().initData(server);
		System.out.println("ID\tDEP\t\tDES\t\tDATE");
		for(Flight f:flight) {
			System.out.println(f.getRecordID()+"\t"+f.getDeparture()+"\t"+f.getDestination()+"\t"+f.getDepartureDate());
		}
	}
}
