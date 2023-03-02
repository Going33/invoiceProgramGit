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
	// private File myObj = new File("C:\\Users\\Bartek\\Desktop\\TEST\\test.txt");
	final private String absPathTxt = System.getProperty("user.dir") + "\\test.txt";
	final private String absPathPDF = System.getProperty("user.dir") + "\\testPDF.pdf";
	final private File myObj = new File(absPathTxt);
	final private File myObjPDF = new File(absPathPDF);

	private BufferedWriter bf;
	private boolean flagDelete;
	private boolean flagCheck;
	private int fontSizeValue = 12;

//https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox 
//pdf creator
	public void makeAFile(Map<String, String> allDataMap) {

		try {

			// creating new file
			if (myObjPDF.createNewFile()) {

				System.out.println("File created: " + myObj.getName());
				System.out.println(System.getProperty("user.dir"));
				// create a file

				PDDocument document = new PDDocument();
				document.addPage(new PDPage());
				// PDPage page = document.getPage(0);

				createAFilePDF(allDataMap, document, document.getPage(0),
						new PDPageContentStream(document, document.getPage(0)), 5, 780);
				System.out.println("PDF file created.");

			} else {

				PDDocument document = PDDocument.load(myObjPDF);
				createAFilePDF(allDataMap, document, document.getPage(0), new PDPageContentStream(document,
						document.getPage(0), PDPageContentStream.AppendMode.APPEND, true), 350, 780);

				System.out.println("File already exists. Append new text to existing file.");
				// }

			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}

	/////////////////////////////// to optmize//////////////////////////////
	public void createAFilePDF(Object object, PDDocument document, PDPage page, PDPageContentStream contentStream,
			int tX, int tY) throws IOException {

		try {
			System.out.println("\n");
			contentStream.beginText();
			// Setting the font to the Content stream
			contentStream.setFont(PDType1Font.TIMES_ROMAN, fontSizeValue);
			contentStream.newLineAtOffset(tX, tY);
			for (Map.Entry<String, String> set : ((Map<String, String>) object).entrySet()) {
				contentStream.showText(set.getKey().toString() + " " + set.getValue().toString());
				contentStream.newLineAtOffset(0, -20);
			}
			contentStream.endText();
			contentStream.close();
			// Saving the document
			document.save(new File(absPathPDF));
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

	public boolean checkIfAFIleIsAlreadyExistingPDF() {
		if (myObjPDF.exists()) {
			System.out.println("File already exists. Cannot create a new one.");
			flagCheck = false;
		} else {
			System.out.println("Confirmed. Successfully wrote to the file.");
			flagCheck = true;
		}
		return flagCheck;
	}

}
