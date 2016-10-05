package ca.concordia.dfrs.server;

public class DFRSServerNDL extends DFRSServer {
	public static final String SERVER_NAME = "NDL";
	public static final int PORT_NUM = 2022;
	public static final int UDP_PORT_NUM = 3022;
	
	public static void main(String[] args) {
		try {
			(new DFRSServerNDL()).exportServer("New Delhi", SERVER_NAME, PORT_NUM, UDP_PORT_NUM);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
