package invoiceProgram;

import java.io.BufferedWriter;
import java.io.File; // Import the File class
import java.io.FileWriter;
import java.io.IOException; // Import the IOException class to handle errors
import java.io.PrintWriter;
import java.util.Map;

public class saveTextFile {

	// tworzenie pliku
	private File myObj = new File("C:\\Users\\Bartek\\Desktop\\TEST\\test.txt");
	private BufferedWriter bf;
	private boolean flagDelete;
	private boolean flagCheck;

	public void makeAFile(Map<String, String> allDataMap) {

		try {
			// creating new file
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());

				// create a file
				createAFile(allDataMap);
				System.out.println("tworzenie pliku");
			} else {
				
				
				// Append new text to existing file

				// try-with-resource statement
				// https://www.java67.com/2015/07/how-to-append-text-to-existing-file-in-java-example.html
				try (FileWriter f = new FileWriter(myObj, true);
						BufferedWriter b = new BufferedWriter(f);
						PrintWriter p = new PrintWriter(b);) {

					for (Map.Entry<String, String> set : ((Map<String, String>) allDataMap).entrySet()) {
						// put key and value separated by a colon
						p.println(set.getKey() + " " + set.getValue());
					}

				} catch (IOException i) {
					i.printStackTrace();
				}

				System.out.println("File already exists. Append new text to existing file.");
				// }

			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}

	/////////////////////////////// to optmize//////////////////////////////
	public void createAFile(Object object) {
		// write inputs to file if file dosent exist
		try {
			System.out.println("\n");
			bf = new BufferedWriter(new FileWriter(myObj));

			for (Map.Entry<String, String> set : ((Map<String, String>) object).entrySet()) {
				bf.write(set.getKey() + " " + set.getValue());
				bf.newLine();
			}

			bf.flush();
			bf.close();

		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
/////////////////////////////////////////////////////////////////////
	public boolean deleteAFIle() {

		if (myObj.delete()) {
			System.out.println("File deleted successfully");
			flagDelete = true;
		} else {
			System.out.println("Failed to delete the file");
			flagDelete = false;
		}
		return flagDelete;
	}

	////// TEST_METHOD///////
	public boolean checkIfAFIleIsAlreadyExisting() {
		if (myObj.exists()) {
			System.out.println("File already exists. Cannot create a new one.");
			flagCheck = false;
		} else {
			System.out.println("Confirmed. Successfully wrote to the file.");
			flagCheck = true;
		}
		return flagCheck;
	}

}
