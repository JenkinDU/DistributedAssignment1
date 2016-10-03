package ca.concordia.dfrs.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ca.concordia.dfrs.api.IManager;
import ca.concordia.dfrs.bean.Flight;
import ca.concordia.dfrs.bean.Ticket;
import ca.concordia.dfrs.database.FlightData;
import ca.concordia.dfrs.database.TicketData;

public class ManagerServant extends UnicastRemoteObject implements IManager {

	private String serverName;
	private int UDP_PORT;
	
	protected ManagerServant(String name, int udp) throws RemoteException {
		super();
		serverName = name;
		UDP_PORT = udp;
		FlightData.getInstance().initData(name);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				initServer();
			}
		}).start();
	}

	private void initServer() {
		DatagramSocket aSocket = null;
		try {
			aSocket = new DatagramSocket(UDP_PORT);
			// create socket at agreed port
			byte[] buffer = new byte[1000];
			while (true) {
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(request);
				String receive = new String(request.getData(), 0, request.getLength()).trim();
				System.out.print("\nReceive: " + receive);
				int count = 0;
				if (Flight.FIRST_CLASS.equals(receive)) {
					count = getRecordTypeCount(Flight.FIRST_CLASS);
				} else if (Flight.BUSINESS_CLASS.equals(receive)) {
					count = getRecordTypeCount(Flight.BUSINESS_CLASS);
				} else if (Flight.ECONOMY_CLASS.equals(receive)) {
					count = getRecordTypeCount(Flight.ECONOMY_CLASS);
				} else if (Flight.ALL_CLASS.equals(receive)) {
					count = getRecordTypeCount(Flight.ALL_CLASS);
				}
				String re = serverName + " " + count;
				request.setData(re.getBytes());
				DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(),
						request.getPort());
				aSocket.send(reply);
			}
		}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		}catch (IOException e) {System.out.println("IO: " + e.getMessage());
		}finally {if(aSocket != null) aSocket.close();}
	}
	
	private String getCountFromOtherServers(String recordType, String ip, int port) {
		DatagramSocket aSocket = null;
		String receive = "";
		try {
			aSocket = new DatagramSocket();
			byte[] m = recordType.getBytes();
			InetAddress aHost = InetAddress.getByName(ip);
			DatagramPacket request = new DatagramPacket(m, m.length, aHost, port);
			aSocket.send(request);
			byte[] buffer = new byte[1000];
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			aSocket.receive(reply);
			receive = new String(reply.getData(), 0, reply.getLength()).trim();
			System.out.println("\nReply: " + receive);
		}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		}catch (IOException e){System.out.println("IO: " + e.getMessage());
		}finally {if(aSocket != null) aSocket.close();}
		return receive;
	}
	
	@Override
	public String getBookedFlightCount(String recordType) throws RemoteException {
		int count = getRecordTypeCount(recordType);
		String value = "";
		if(DFRSServerMTL.SERVER_NAME.equals(serverName)) {
			value = serverName + " " +count+",";
			value +=getCountFromOtherServers(recordType, "localhost", DFRSServerWST.UDP_PORT_NUM);
			value +=",";
			value +=getCountFromOtherServers(recordType, "localhost", DFRSServerNDL.UDP_PORT_NUM);
		} else if(DFRSServerWST.SERVER_NAME.equals(serverName)) {
			value =getCountFromOtherServers(recordType, "localhost", DFRSServerMTL.UDP_PORT_NUM);
			value += ("," + serverName + " " +count+",");
			value +=getCountFromOtherServers(recordType, "localhost", DFRSServerNDL.UDP_PORT_NUM);
		} else if(DFRSServerNDL.SERVER_NAME.equals(serverName)) {
			value +=getCountFromOtherServers(recordType, "localhost", DFRSServerMTL.UDP_PORT_NUM);
			value +=",";
			value +=getCountFromOtherServers(recordType, "localhost", DFRSServerWST.UDP_PORT_NUM);
			value += ("," + serverName + " " +count);
		}
		System.out.println("ManagerServant getBookedFlightCount: " + value);
		return value;
	}

	private int getRecordTypeCount(String recordType) {
		HashMap<String,List<Ticket>> tickets = TicketData.getInstance().initData(serverName);
		Iterator iter = tickets.entrySet().iterator();
		int count = 0;
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			ArrayList<Ticket> value = (ArrayList<Ticket>)entry.getValue();
			for(Ticket f:value) {
				if(f!=null) {
					if (!recordType.equals(Flight.ALL_CLASS)) {
						if (recordType.equals(f.getTicketClass())) {
							count++;
						}
					} else {
						count++;
					 }
				}
			}
		}
		return count;
	}

	@Override
	public Flight editFlightRecord(int recordID, String fieldName, String newValue) throws RemoteException {
		System.out.println("Edit Flight Record recordID:" + recordID + " fieldName:" + fieldName + "newValue:" + newValue);
		ArrayList<Flight> flight = (ArrayList<Flight>)FlightData.getInstance().initData(serverName);
		Flight result = null;
		for(Flight f:flight) {
			System.out.println("Flight recordID:" + f.getRecordID());
			if(f.getRecordID() == recordID) {
				System.out.println("Find recordID:" + f.getRecordID());
				result = f;
				if(Flight.DEPARTURE.equals(fieldName)) {
					f.setDeparture(newValue);
				} else if(Flight.DATE.equals(fieldName)) {
					f.setDepartureDate(newValue);
				} else if(Flight.DESTINATION.equals(fieldName)) {
					f.setDestination(newValue);
				} else if(Flight.F_SEATS.equals(fieldName)) {
					f.setTotalFirstTickets(Integer.valueOf(newValue));
				} else if(Flight.B_SEATS.equals(fieldName)) {
					f.setTotalBusinessTickets(Integer.valueOf(newValue));
				} else if(Flight.E_SEATS.equals(fieldName)) {
					f.setTotalEconomyTickets(Integer.valueOf(newValue));
				}
			}
		}
		if(result == null) {
			result = new Flight();
			result.setRecordID(recordID);
			if(Flight.DEPARTURE.equals(fieldName)) {
				result.setDeparture(newValue);
			} else if(Flight.DATE.equals(fieldName)) {
				result.setDepartureDate(newValue);
			} else if(Flight.DESTINATION.equals(fieldName)) {
				result.setDestination(newValue);
			} else if(Flight.F_SEATS.equals(fieldName)) {
				result.setTotalFirstTickets(Integer.valueOf(newValue));
			} else if(Flight.B_SEATS.equals(fieldName)) {
				result.setTotalBusinessTickets(Integer.valueOf(newValue));
			} else if(Flight.E_SEATS.equals(fieldName)) {
				result.setTotalEconomyTickets(Integer.valueOf(newValue));
			}
			FlightData.getInstance().addFlight(serverName, result);
		}
		return result;
	}

}
