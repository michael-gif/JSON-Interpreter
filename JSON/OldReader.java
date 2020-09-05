package JSON;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
@Deprecated
public class OldReader {
	
	public static ArrayList<HashMap<String, String>> read(String filename) {
		return readComponents(splitIntoComponents(filename));
	}
	
	private static String[] splitIntoComponents(String filename) {
		File file = new File(filename);
		String json = "";
		String[] jsonComponents = null;
		try {
			Scanner scanner = new Scanner(file);
			ArrayList<String> lines = new ArrayList<>();
			while (scanner.hasNextLine()) {
				lines.add(scanner.nextLine());
			}
			scanner.close();
			for (String line : lines) {
				String[] parts = line.split("\n");
				String rawline = String.join("", parts);
				json = json.concat(rawline);
			}
			jsonComponents = json.split("\\},\\{");
			jsonComponents[0] = jsonComponents[0].substring(1,jsonComponents[0].length());
			jsonComponents[jsonComponents.length-1] = jsonComponents[jsonComponents.length-1].substring(0,jsonComponents[jsonComponents.length-1].length()-1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return jsonComponents;
	}
	
	private static ArrayList<HashMap<String, String>> readComponents(String[] parts) {
		ArrayList<HashMap<String, String>> jsonOutput = new ArrayList<>();
		for (String part : parts) {
			ArrayList<String> names = new ArrayList<>();
			ArrayList<String> values = new ArrayList<>();
			String[] chars = part.split("");
			int begin = -1;
			int end = -1;
			for (int i = 0; i < chars.length; i ++) {
				if (chars[i].equals("\"")) {
					if (begin < 0) {
						begin = i;
					} else {
						end = i;
						//System.out.println(String.valueOf(begin) + "," + String.valueOf(end));
						String name = String.join("", Arrays.copyOfRange(chars, begin, end + 1));
						names.add(name);
						//System.out.println(name);
						//System.out.println(chars[i + 1]);
						String value = "";
						boolean run = true;
						while (run) {
							if (chars[i].equals(":")) {
								run = false;
								boolean run2 = true;
								int begin2 = -1;
								int end2 = -1;
								while (run2) {
									if (chars[i].equals("\"")) {
										if (begin2 < 0) {
											begin2 = i;
										} else {
											end2 = i;
											run2 = false;
											value = value.concat(String.join("", Arrays.copyOfRange(chars, begin2, end2 + 1)));
											values.add(value);
											begin2 = -1;
											end2 = -1;
										}
									}
									i ++;
								}
							} else {
								i ++;
							}
						}
						begin = -1;
						end = -1;
					}
				}
			}
			
			HashMap<String, String> map = new HashMap<String, String>();
			for (int j = 0; j < names.size(); j ++) {
				map.put(names.get(j), values.get(j));
			}
			jsonOutput.add(map);
		}
		return jsonOutput;
	}
	
}
