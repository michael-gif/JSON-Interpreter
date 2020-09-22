package JSON;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Interpreter {
	
	
	// Reader
	
	public static ArrayList<HashMap<String, HashMapValue>> read(String filename) {
		ArrayList<HashMap<String, HashMapValue>> thing = new ArrayList<>();
		File file = new File(filename);
		String json = "";
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) { json = json.concat(scanner.nextLine()); }
			scanner.close();
			thing = readJSON(json);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return thing;
	}
	
	private static ArrayList<HashMap<String, HashMapValue>> readJSON(String json) {
		ArrayList<HashMap<String, HashMapValue>> processedJSON = splitJSONObjects(identifyJSONObjects(json));
		for (HashMap<String, HashMapValue> processedPart : processedJSON) {
			Set<String> one = processedPart.keySet();
			Collection<HashMapValue> two = processedPart.values();
			String[] keys = new String[one.size()];
			one.toArray(keys);
			HashMapValue[] hashMaps = new HashMapValue[two.size()];
			two.toArray(hashMaps);
			String[] values = new String[two.size()];
			for (int k = 0; k < hashMaps.length; k ++) {
				values[k] = hashMaps[k].getString();
			}
			for (int i = 0; i < keys.length; i ++) {
				ArrayList<HashMap<String, HashMapValue>> thing = readJSON(values[i]);
				if (!thing.isEmpty()) {
					processedPart.put(keys[i], new HashMapValue(thing));
				}
			}
		}
		return processedJSON;
	}
	
	private static ArrayList<String> identifyJSONObjects(String json) {
		ArrayList<String> components = new ArrayList<>();
		String[] chars = json.split("");
		int i = 0;
		int[] indexes = {0,0};
		boolean detected = false;
		for (int j = 0; j < chars.length; j ++) {
			if (chars[j].equals("{")) {
				if (!detected) { indexes[0] = j; }
				i ++;
				detected = true;
			} else if (chars[j].equals("}")) {
				i --;
			}
			if (detected && i == 0) {
				indexes[1] = j + 1;
				components.add(json.substring(indexes[0],indexes[1]));
				detected = false;
			}
		}
		return components;
	}
	
	private static ArrayList<HashMap<String, HashMapValue>> splitJSONObjects(ArrayList<String> objects) {
		ArrayList<HashMap<String, HashMapValue>> splitObjects = new ArrayList<>();
		for (String object : objects) {
			HashMap<String, HashMapValue> map = new HashMap<>();
			String[] chars = object.split("");
			String name = null;
			String value;
			int i = 0;
			int[] indexes = {0,0};
			boolean detected = false;
			String stage = "gettingName";
			boolean squareBracket = false;
			for (int j = 0; j < chars.length; j ++) {
				if (chars[j].equals("\"")) {
					if (!squareBracket) {
						if (!detected) { indexes[0] = j; }
						i = (i == 0) ? 1 : 0;
						detected = true;
					}
				} else if (chars[j].equals("[")) {
					if (!detected) { indexes[0] = j; }
					i ++;
					detected = true;
					squareBracket = true;
				} else if (chars[j].equals("]")) {
					i --;;
					squareBracket = false;
				}
				if (detected && i == 0) {
					indexes[1] = j + 1;
					if (stage.equals("gettingName")) {
						name = object.substring(indexes[0],indexes[1]);
						stage = "gettingValue";
					} else {
						value = object.substring(indexes[0],indexes[1]);
						if (value.indexOf("\"") == 0) {
							map.put(name.substring(1,name.length()-1), new HashMapValue(value.substring(1,value.length()-1)));
						} else if (value.indexOf("[") == 0) {
							map.put(name.substring(1,name.length()-1), new HashMapValue(value));
						}
						stage = "gettingName";
					}
					detected = false;
				}
			}
			splitObjects.add(map);
		}
		return splitObjects;
	}
	
	
	
	// Writer
	
	public static void writeString(ArrayList<HashMap<String, String>> data, String filename) {
		for (HashMap<String, String> map : data) {
			ArrayList<String> jsonObject = new ArrayList<>();
			String[] keys = map.keySet().toArray(new String[map.keySet().size()]);
			String[] values = map.values().toArray(new String[map.values().size()]);
			for (int i = 0; i < keys.length; i ++) {
				jsonObject.add("\"" + keys[i] + "\":\"" + values[i] + "\"");
			}
			jsonObject.set(0, "{".concat(jsonObject.get(0)));
			jsonObject.set(jsonObject.size()-1, jsonObject.get(jsonObject.size()-1).concat("},\n"));
			String json = String.join(",", jsonObject);
			try {
				if (new File(filename).exists()) {
					FileWriter filewriter = new FileWriter(filename, true);
					filewriter.write(json);
					filewriter.close();
				} else {
					System.out.println("Error: " + filename + " does not exist");
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void writeCompound(ArrayList<HashMap<String, HashMapValue>> data, String filename, boolean append) {
		String json = convertJSONHashMaps(data) + ",\n";
		try {
			if (new File(filename).exists()) {
				FileWriter filewriter = new FileWriter(filename, append);
				filewriter.write(json);
				filewriter.close();
			} else {
				System.out.println("Error: " + filename + " does not exist");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String convertJSONHashMaps(ArrayList<HashMap<String, HashMapValue>> data) {
		ArrayList<String> json = new ArrayList<>();
		for (HashMap<String, HashMapValue> map : data) {
			ArrayList<String> jsonObject = new ArrayList<>();
			String[] keys = map.keySet().toArray(new String[map.keySet().size()]);
			HashMapValue[] values = map.values().toArray(new HashMapValue[map.values().size()]);
			boolean isValueString = false;
			for (int i = 0; i < keys.length; i ++) {
				String key = keys[i];
				String value;
				if (!(values[i].getString() == null)) {
					value = values[i].getString();
					isValueString = true;
				} else {
					value = convertJSONHashMaps(values[i].getList());
					String[] newValues = value.split("\n");
					value = String.join("", newValues);
					isValueString = false;
				}
				if (isValueString) {
					jsonObject.add("\"" + key + "\":\"" + value + "\"");
				} else {
					jsonObject.add("\"" + key + "\":[" + value + "]");
				}
			}
			jsonObject.set(0, "{".concat(jsonObject.get(0)));
			jsonObject.set(jsonObject.size()-1, jsonObject.get(jsonObject.size()-1).concat("}"));
			json.add(String.join(",", jsonObject));
		}
		return String.join(",\n", json);
	}
	
}
