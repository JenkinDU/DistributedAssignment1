package ca.concordia.dfrs.server;

public class DFRSServerWST extends DFRSServer {
	public static final String SERVER_NAME = "WST";
	public static final int PORT_NUM = 2021;
	public static final int UDP_PORT_NUM = 3021;
	
	public static void main(String[] args) {
		try {
			(new DFRSServerWST()).exportServer(SERVER_NAME, PORT_NUM, UDP_PORT_NUM);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
