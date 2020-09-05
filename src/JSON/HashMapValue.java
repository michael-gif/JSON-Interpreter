package JSON;

import java.util.ArrayList;
import java.util.HashMap;

public class HashMapValue {
	
	private String string = null;
	private ArrayList<HashMap<String, HashMapValue>> list = null;
	
	public HashMapValue(String string) {
		this.string = string;
	}
	
	public HashMapValue(ArrayList<HashMap<String, HashMapValue>> list) {
		this.list = list;
	}
	
	public String getString() {
		return this.string;
	}
	
	public ArrayList<HashMap<String, HashMapValue>> getList() {
		return this.list;
	}
	
	public int getInt() {
		try {
			return Integer.parseInt(this.string);
		} catch (NumberFormatException e) {
			System.out.println("Error: element value is not an integer");
			return -1;
		}
	}
	
}
