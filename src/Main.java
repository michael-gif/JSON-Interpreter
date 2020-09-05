import java.util.ArrayList;
import java.util.HashMap;

import JSON.HashMapValue;
import JSON.Reader;

public class Main {

	public static void main(String[] args) {
		ArrayList<HashMap<String, HashMapValue>> thing = Reader.read("json.txt");
		System.out.println(thing);
	}
}
