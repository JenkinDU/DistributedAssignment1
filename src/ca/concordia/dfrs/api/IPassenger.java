package ca.concordia.dfrs.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ca.concordia.dfrs.utils.Result;

public interface IPassenger  extends Remote{
	public static final String INTERFACE_NAME = "passenger";
	public Result bookFlight(String firstName, String lastName, String address, 
			String phone, String destination, String date, String ticketClass) throws RemoteException;
}
