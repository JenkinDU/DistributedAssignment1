package ca.concordia.dfrs.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.concordia.dfrs.api.IPassenger;
import ca.concordia.dfrs.bean.Ticket;
import ca.concordia.dfrs.database.TicketData;

public class PassengerServant extends UnicastRemoteObject implements IPassenger {

	private String serverName;
//	private HashMap<String,List<Ticket>> tickets;
	
	protected PassengerServant(String name) throws RemoteException {
		super();
		serverName = name;
		TicketData.getInstance().initData(name);
	}

	@Override
	public boolean bookFlight(String firstName, String lastName, String address, String phone, String destination,
			String date, String ticketClass) throws RemoteException {
		System.out.println("Request book flight order from " + serverName + "\nPassenger info is\nFirstName:"+firstName+"\n"
				+"lastName:"+lastName +"\n"
				+"address:"+address +"\n"
				+"phone:"+phone +"\n"
				+"destination:"+destination +"\n"
				+"date:"+date +"\n"
				+"ticketClass:"+ticketClass +"\n");
		Ticket t = new Ticket(firstName, lastName, address, phone, ticketClass, serverName, date, destination);
		String index = Character.toUpperCase(lastName.charAt(0)) + "" ;
		TicketData.getInstance().addTicket(serverName, t, index);
		return true;
	}

}
