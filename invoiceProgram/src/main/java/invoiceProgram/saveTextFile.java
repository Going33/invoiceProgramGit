package invoiceProgram;

import java.io.BufferedWriter;
import java.io.File; // Import the File class
import java.io.FileWriter;
import java.io.IOException; // Import the IOException class to handle errors
import java.io.PrintWriter;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;

public class saveTextFile {

	// tworzenie pliku
	private File myObj = new File("C:\\Users\\Bartek\\Desktop\\TEST\\test.txt");
	private File myObjPDF = new File("C:\\Users\\Bartek\\Desktop\\TEST\\testPDF.pdf");

	private BufferedWriter bf;
	private boolean flagDelete;
	private boolean flagCheck;
	private String text = "";
	private int i = 5;

//https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox 
//pdf creator
	public void makeAFile(Map<String, String> allDataMap) {

		try {

			// creating new file
			if (myObj.createNewFile()) {

				System.out.println("File created: " + myObj.getName());

				// create a file
				createAFile(allDataMap);
				createAFilePDF(allDataMap);
				System.out.println("tworzenie pliku");

			} else {

				// Append new text to existing file

				PDDocument documentOpen = PDDocument.load(myObjPDF);
				PDPage page = documentOpen.getPage(0);
				//	https://stackoverflow.com/questions/14657602/cannot-figure-out-how-to-use-pdfbox
				PDPageContentStream contentStream = new PDPageContentStream(documentOpen, page, PDPageContentStream.AppendMode.APPEND, true);
				try {
					contentStream.beginText();
						contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
					contentStream.newLineAtOffset(5, 620);
					for (Map.Entry<String, String> set : ((Map<String, String>) allDataMap).entrySet()) {

						contentStream.showText(set.getKey().toString() + " " + set.getValue().toString());
						contentStream.newLineAtOffset(0, -20);
						// contentStream.newLine();
					}
					contentStream.endText();
					contentStream.close();
					// Saving the document
					documentOpen.save(new File("C:\\Users\\Bartek\\Desktop\\TEST\\testPDF.pdf"));
					// Closing the document
					documentOpen.close();

				} catch (IOException e) {
					System.out.println("An error occurred.");
					e.printStackTrace();
				}

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
	public void createAFilePDF(Object object) throws IOException {
		// write inputs to file if file dosent exist

		// Loading an existing document
		PDDocument document = new PDDocument();
		// PDDocument document = PDDocument.load(file);
		document.addPage(new PDPage());
		// Retrieving the pages of the document
		PDPage page = document.getPage(0);
		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		try {
			System.out.println("\n");
			contentStream.beginText();
			// Setting the font to the Content stream
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			contentStream.newLineAtOffset(5, 780);
			for (Map.Entry<String, String> set : ((Map<String, String>) object).entrySet()) {
				// contentStream.setLeading(15);
				contentStream.showText(set.getKey().toString() + " " + set.getValue().toString());
				contentStream.newLineAtOffset(0, -20);

				i = i + 50;
				// contentStream.newLine();
			}
			contentStream.endText();
			contentStream.close();
			// Saving the document
			document.save(new File("C:\\Users\\Bartek\\Desktop\\TEST\\testPDF.pdf"));
			// Closing the document
			document.close();

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
