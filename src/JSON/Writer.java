package JSON;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Writer {
	
	public static void write(HashMap<String,String> data, String filename) {
		data.forEach((key, value) -> {
			try {
				if (new File(filename).exists()) {
					FileWriter filewriter = new FileWriter(filename, true);
					String json = "{\"" + key + "\":\"" + value + "\"},\n";
					filewriter.write(json);
					filewriter.close();
				} else {
					System.out.println("Error: " + filename + " does not exist");
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
}
