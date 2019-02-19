package ai;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Saver {

	public static void writeToFile(String file_name, String output) throws IOException {
		String output_file = file_name;
		
		if(!output_file.contains(".txt"))
			output_file += ".txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(output_file));
		/* TODO: write the current state of the file. 
		 * Use append to add to the file. Use write to override the file. */
	}
	
}
