package JSON;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Writer {
	
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
	
	public static void writeHashMaps(ArrayList<HashMap<String, HashMapValue>> data, String filename, boolean append) {
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
