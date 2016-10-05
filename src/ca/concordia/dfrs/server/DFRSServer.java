package ca.concordia.dfrs.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import ca.concordia.dfrs.api.IManager;
import ca.concordia.dfrs.api.IPassenger;
import ca.concordia.dfrs.database.FlightData;
import ca.concordia.dfrs.database.TicketData;
import ca.concordia.dfrs.utils.Log;

public class DFRSServer {

	public DFRSServer() {
		super();
	}

	public void exportServer(String name, String server, int port, int udp) throws Exception {
		String s = "["+server+"]-"+"Server is up and running!";
		System.out.println(s);
		
		Log.createLogDir(Log.LOG_DIR+"LOG_"+server+"/");
		FlightData.getInstance().initData(server);
		TicketData.getInstance().initData(server);
		PassengerServant passenger = new PassengerServant(name, server);
		ManagerServant manager = new ManagerServant(server, udp);

		Registry r = LocateRegistry.createRegistry(port);
		r.rebind(IPassenger.INTERFACE_NAME, passenger);
		r.rebind(IManager.INTERFACE_NAME, manager);
		
		Log.i(Log.LOG_DIR+"LOG_"+server+"/"+server+"_LOG.txt", s);
	}
}