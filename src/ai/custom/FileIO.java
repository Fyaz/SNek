package ai.custom;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileIO {

	/** Save the String, output, into the file, file_name. 
	 * Preconditions: Know that the text in the file
	 * will be replaced with output.
	 * @param file_name - the name of the file to output to. 
	 * If the file_name doesn't end with .txt, then the 
	 * method will change it to a .txt file.  
	 * @param output - the text to output to the file. */
	public static void save(String file_name, String output) throws IOException {
		String output_file = file_name;
		
		if(!output_file.contains(".txt"))
			output_file += ".txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(output_file));
		writer.write(output);
		writer.close();
	}
	
	/** Loads a file and outputs the whole file into a string. 
	 * @param file_name - the name of the file to get input from. 
	 * If the file_name doesn't end with .txt, then the 
	 * method will change it to a .txt file.  
	 * @return the contents of the file. */
	public static String load(String file_name) throws IOException {
		String output = "";
		String input_file = file_name;
		if(!input_file.contains(".txt"))
			input_file += ".txt";
		Scanner readFile = new Scanner(new File(input_file));
		while(readFile.hasNextLine())
			output += readFile.nextLine();
		readFile.close();
		return output;
	}
	
}
