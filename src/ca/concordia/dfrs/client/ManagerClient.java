package ca.concordia.dfrs.client;

import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.concordia.dfrs.api.IManager;
import ca.concordia.dfrs.bean.Flight;
import ca.concordia.dfrs.server.DFRSServerMTL;
import ca.concordia.dfrs.server.DFRSServerNDL;
import ca.concordia.dfrs.server.DFRSServerWST;

public class ManagerClient {
	private static IManager manager;
	
	private void showMenu() {
		System.out.println("\n****Welcome to DFRS Manage System****\n");
		System.out.println("Please enter your manager ID:");
	}
	
	private int validInputOption(Scanner keyboard, int max) {
		int userChoice = 0;
		boolean valid = false;

		// Enforces a valid integer input.
		while (!valid) {
			try {
				userChoice = keyboard.nextInt();
				if(userChoice >=1 && userChoice <=max)
					valid = true;
				else {
					throw new Exception();
				}
			} catch (Exception e) {
				System.out.println("Invalid Input, please enter an Integer (1 - "+max+")\n");
				valid = false;
				keyboard.nextLine();
			}
		}
		return userChoice;
	}
	
	private void showBookedMenu() {
		System.out.println("Please select the record type (1-4)");
		System.out.println("1. First Class");
		System.out.println("2. Bussiness Class");
		System.out.println("3. Economy Class");
		System.out.println("4. ALL");
		
		Scanner keyboard = new Scanner(System.in);
		int userChoice = validInputOption(keyboard, 4);
		String type = "";
		switch (userChoice) {
		case 1:
			type = Flight.FIRST_CLASS;
			break;
		case 2:
			type = Flight.BUSINESS_CLASS;
			break;
		case 3:
			type = Flight.ECONOMY_CLASS;
			break;
		case 4:
			type = Flight.ALL_CLASS;
			break;
		default:
			System.out.println("Invalid Input, please try again.");
		}
		try {
			String value = manager.getBookedFlightCount(type);
			System.out.println("get Booked Flight Count:" + value);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private int showCityMenu(Scanner keyboard) {
		System.out.println("Please choose the city (1-3)");
		System.out.println("1. Montreal");
		System.out.println("2. Washington");
		System.out.println("3. New Delhi");
		
		return validInputOption(keyboard, 3);
	}
	
	private void showEditFlghtOptionMenu(int recordId) {
		System.out.println("Please select the field name (1-6)");
		System.out.println("1. Departure place");//\n  11 Montreal\n  12 Washington\n  13 New Delhi");
		System.out.println("2. Departure date");
		System.out.println("3. Destination place");//\n  31 Montreal\n  32 Washington\n  33 New Delhi");
		System.out.println("4. First Class seats");
		System.out.println("5. Business Class seats");
		System.out.println("6. Economy Class seats");
		
		Scanner keyboard = new Scanner(System.in);
		int userChoice = validInputOption(keyboard, 6);
		int seats = -1;
		String fieldName = "";
		String value = "";
		if(userChoice == 1 || userChoice == 3) {
			int city = showCityMenu(keyboard);
			if(city == 1) {
				value = "Montreal";
			} else if(city == 2) {
				value = "Washington";
			} else if(city == 3) {
				value = "New Delhi";
			}
		} else {
			System.out.println("Please enter new field value");
			if(userChoice > 3) {
				while (seats < 0) {
					try {
						seats = keyboard.nextInt();
					} catch (Exception e) {
						System.out.println("Invalid Input, please enter the number");
						seats = -1;
						keyboard.nextLine();
					}
				}
				value = seats + "";
			} else {
				value = keyboard.next();
			}
		}
		try {
			switch (userChoice) {
			case 1:
				fieldName = Flight.DEPARTURE;
				break;
			case 2:
				fieldName = Flight.DATE;
				break;
			case 3:
				fieldName = Flight.DESTINATION;
				break;
			case 4:
				fieldName = Flight.F_SEATS;
				break;
			case 5:
				fieldName = Flight.B_SEATS;
				break;
			case 6:
				fieldName = Flight.E_SEATS;
				break;
			default:
				System.out.println("Invalid Input, please try again.");
			}
			Flight result = manager.editFlightRecord(recordId, fieldName, value);
			System.out.println("Edit Flight Record Success:\n" + result.toString());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void showEditMenu() {
		System.out.println("Please enter the flight record ID:");
		Scanner keyboard = new Scanner(System.in);
		boolean valid = false;
		int userInput = 0;
		
		while (!valid) {
			try {
				userInput = keyboard.nextInt();
				valid=true;
			} catch (Exception e) {
				System.out.println("Invalid Input, please enter the flight record ID:");
				valid = false;
				keyboard.nextLine();
			}
		}
		showEditFlghtOptionMenu(userInput);
	}

	private void showOptionMenu(Scanner keyboard) {
		System.out.println("Please select your option (1-3)");
		System.out.println("1. Get Booked Flight Count");
		System.out.println("2. Edit Flight Record");
		System.out.println("3. Exit");
		
		int userChoice = validInputOption(keyboard, 3);
		switch (userChoice) {
		case 1:
			showBookedMenu();
			break;
		case 2:
			showEditMenu();
			break;
		case 3:
			System.out.println("Have a nice day!");
			keyboard.close();
			System.exit(0);
		default:
			System.out.println("Invalid Input, please try again.");
		}
		initPage();
	}
	
	private boolean initConnection(int port) {
		try {
			String registryURL = "rmi://localhost:"+port+"/" + IManager.INTERFACE_NAME;
			manager = (IManager) Naming.lookup(registryURL);
			System.out.println("Lookup completed ");
			return true;
		} catch (ConnectException e) {
			System.out.println("Failed to find Server, please try again.\n");//because:" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean validManagerId(String input) {
		String pat = "(MTL|WST|NDL)\\d{4}" ;
        Pattern p = Pattern.compile(pat) ;
        Matcher m = p.matcher(input) ;
        return m.matches();
	}
	
	private int getServerPort(String input) {
		String pat = "(MTL)\\d{4}" ;
        if(Pattern.compile(pat).matcher(input).matches()) {
        	return DFRSServerMTL.PORT_NUM;
        }
        pat = "(WST)\\d{4}" ;
        if(Pattern.compile(pat).matcher(input).matches()) {
        	return DFRSServerWST.PORT_NUM;
        }
		pat = "(NDL)\\d{4}" ;
		if(Pattern.compile(pat).matcher(input).matches()) {
        	return DFRSServerNDL.PORT_NUM;
        }
		return 0;
	}
	
	private void initPage() {
		String userInput = "";
		Scanner keyboard = new Scanner(System.in);
		boolean valid = false;
		
		showMenu();

		// Enforces a valid integer input.
		while (!valid) {
			try {
				userInput = keyboard.next();
				valid = validManagerId(userInput);
				if (!valid) {
					System.out.println("Invalid Input, please enter your ManagerID");
				}
			} catch (Exception e) {
				System.out.println("Invalid Input, please enter your ManagerID");
				valid = false;
				keyboard.nextLine();
			}
		}
		if (initConnection(getServerPort(userInput))) {
			showOptionMenu(keyboard);
		} else {
			initPage();
		}
	}
	
	public static void main(String[] args) {
		new ManagerClient().initPage();
	}
}
