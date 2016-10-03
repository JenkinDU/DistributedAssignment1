package ca.concordia.dfrs.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import ca.concordia.dfrs.api.IManager;
import ca.concordia.dfrs.api.IPassenger;

public class DFRSServer {

	public DFRSServer() {
		super();
	}

	public void exportServer(String name, int port, int udp) throws Exception {
			PassengerServant passenger = new PassengerServant(name);
			ManagerServant manager = new ManagerServant(name, udp);
			
			Registry r = LocateRegistry.createRegistry(port);
			r.rebind(IPassenger.INTERFACE_NAME, passenger);
			r.rebind(IManager.INTERFACE_NAME, manager);
			
			System.out.println("Server is up and running!");
		}

}