package ca.concordia.dfrs.server;

public class DFRSServerMTL extends DFRSServer {
	public static final String SERVER_NAME = "MTL";
	public static final int PORT_NUM = 2020;
	public static final int UDP_PORT_NUM = 3020;
	
	public static void main(String[] args) {
		try {
			(new DFRSServerMTL()).exportServer("Montreal", SERVER_NAME, PORT_NUM, UDP_PORT_NUM);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
