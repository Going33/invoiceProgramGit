package invoiceProgram;

import java.io.BufferedWriter;
import java.io.File; // Import the File class
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException; // Import the IOException class to handle errors
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class saveTextFile {

	// tworzenie pliku
	private File myObj = new File("C:\\Users\\Bartek\\Desktop\\TEST\\test.txt");
	private BufferedWriter bf;
	private boolean flagDelete;
	private boolean flagCheck;

	public void makeAFile(Map<String, String> allDataMap) {

		try {
			// File myObj = new File("C:\\Users\\Bartek\\Desktop\\TEST\\test.txt");
			// creating new file
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());

				
				// create a file
				createAFile(allDataMap);

				
				
				
				// read a file
				try {
					// File myObj1 = new File("C:\\Users\\Bartek\\Desktop\\TEST\\test.txt");
					Scanner myReader = new Scanner(myObj);
					while (myReader.hasNextLine()) {
						String data = myReader.nextLine();
						// System.out.println(data);
					}
					myReader.close();
				} catch (FileNotFoundException e) {
					System.out.println("An error occurred.");
					e.printStackTrace();
				}

			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}

	public void createAFile(Object object) {
		// write inputs to file
		try {
			System.out.println("\n");
			bf = new BufferedWriter(new FileWriter(myObj));

			for (Map.Entry<String, String> set : ((Map<String, String>) object).entrySet()) {
				// put key and value separated by a colon
				bf.write(set.getKey() + " " + set.getValue());
				bf.newLine();
				// System.out.println("Successfully wrote to the file.");
			}

			bf.flush();
			bf.close();

		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

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

	
	//////TEST_METHOD///////
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
