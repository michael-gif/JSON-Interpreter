import java.util.ArrayList;
import java.util.HashMap;

import JSON.HashMapValue;
import JSON.Reader;
import JSON.Writer;

public class Main {

	public static void main(String[] args) {
		ArrayList<HashMap<String, HashMapValue>> thing = Reader.read("json.txt");
		System.out.println(thing);
		ArrayList<HashMap<String, HashMapValue>> list = new ArrayList<>();
		HashMap<String, HashMapValue> map = new HashMap<>();
		map.put("index", new HashMapValue("0"));
		map.put("name", new HashMapValue("jeff"));
		map.put("value", new HashMapValue("69"));
		ArrayList<HashMap<String, HashMapValue>> list2 = new ArrayList<>();
		HashMap<String, HashMapValue> map3 = new HashMap<>();
		map3.put("index", new HashMapValue("0"));
		map3.put("name", new HashMapValue("boe mama"));
		map3.put("value", new HashMapValue("1738"));
		list2.add(map3);
		HashMap<String, HashMapValue> map4 = new HashMap<>();
		map4.put("index", new HashMapValue("1"));
		map4.put("name", new HashMapValue("mama"));
		map4.put("value", new HashMapValue("56"));
		list2.add(map4);
		HashMap<String, HashMapValue> map2 = new HashMap<>();
		map2.put("index", new HashMapValue("1"));
		map2.put("name", new HashMapValue("joe mama"));
		map2.put("value", new HashMapValue(list2));
		list.add(map);
		list.add(map2);
		Writer.writeHashMaps(list, "json.txt", true);
	}
}
