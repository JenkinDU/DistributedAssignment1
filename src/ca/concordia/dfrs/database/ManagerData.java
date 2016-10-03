package ca.concordia.dfrs.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.concordia.dfrs.bean.Manager;

public class ManagerData {
	private static ManagerData instance;

	private HashMap<String, List<Manager>> data;
	
	private ManagerData() {
		data = new HashMap<String, List<Manager>>();
	}

	public static synchronized ManagerData getInstance() {
		if (instance == null) {
			instance = new ManagerData();
		}
		return instance;
	}
	
	public void initData(String name) {
		Object o = data.get(name);
		if(o == null) {
			data.put(name, new ArrayList<Manager>());
		}
	}
}
