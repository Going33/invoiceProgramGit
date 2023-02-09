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
	private	File myObj = new File("C:\\Users\\Bartek\\Desktop\\TEST\\test.txt");
	private BufferedWriter bf;
	private boolean flag;
	
	public void makeAFile(Map<String, String> allDataMap) {
		
		
		try {
			//File myObj = new File("C:\\Users\\Bartek\\Desktop\\TEST\\test.txt");
			// creating new file
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());

				// write inputs to file 
				try {
					System.out.println("\n");
					bf = new BufferedWriter(new FileWriter(myObj));
				
					for (Map.Entry<String, String> set : allDataMap.entrySet()) {
						// put key and value separated by a colon
						bf.write(set.getKey() + " " + set.getValue());
						bf.newLine();
						//System.out.println("Successfully wrote to the file.");
					}

					bf.flush();
					bf.close();

				} catch (IOException e ) {
					System.out.println("An error occurred.");
					e.printStackTrace();
				}

				// odczyt pliku
				try {
					// File myObj1 = new File("C:\\Users\\Bartek\\Desktop\\TEST\\test.txt");
					Scanner myReader = new Scanner(myObj);
					while (myReader.hasNextLine()) {
						String data = myReader.nextLine();
						//System.out.println(data);
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

	public boolean deleteAFIle() {
		
		if (myObj.delete()) {
            System.out.println("File deleted successfully");
            flag = true;
        }
        else {
            System.out.println("Failed to delete the file");
            flag = false;
        }
		return flag;
	}
	
	//TO_DO
	public void checkIfAFIleIsAlreadyExisting(File file) throws IOException
	{
		if(myObj.exists())
		{
		System.out.println("PLIK ISTNIEJE");
		}
	}
	}
	
