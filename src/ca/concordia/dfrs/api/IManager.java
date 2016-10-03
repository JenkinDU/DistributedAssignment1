package ca.concordia.dfrs.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ca.concordia.dfrs.bean.Flight;

public interface IManager extends Remote {
	public static final String INTERFACE_NAME = "manager";
	public String getBookedFlightCount(String recordType) throws RemoteException;
	public Flight editFlightRecord(int recordID, String fieldName, String newValue) throws RemoteException;
}
