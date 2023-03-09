package Data;

import java.io.BufferedWriter;
import java.io.File; // Import the File class
import java.io.FileWriter;
import java.io.IOException; // Import the IOException class to handle errors
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;

public class saveTextFile {

	// tworzenie pliku
	// private File myObj = new File("C:\\Users\\Bartek\\Desktop\\TEST\\test.txt");
	private int i =0;
	final private String absPathPDF = System.getProperty("user.dir") + "\\Invoice_"+LocalDate.now()+".pdf";
	//private String absPathPDF;
	final private File myObjPDF = new File(absPathPDF);

	private boolean flagDelete;
	private boolean flagCheck;
	private int fontSizeValue = 12;

//https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox 
//pdf creator
	public void makeAFile(Map<String, String> allDataMap) {

		try {

			// creating new file
			if (myObjPDF.createNewFile()) {
			//	absPathPDF = System.getProperty("user.dir") + "\\Invoice_"+LocalDate.now()+i+".pdf";
				System.out.println("File created: " + myObjPDF.getName());
				System.out.println(System.getProperty("user.dir"));
				// create a file

				PDDocument document = new PDDocument();
				document.addPage(new PDPage());
				// PDPage page = document.getPage(0);

				createAFilePDF(allDataMap, document, document.getPage(0),
						new PDPageContentStream(document, document.getPage(0)), 5, 750,true);
				System.out.println("PDF file created.");

			} else {

				PDDocument document = PDDocument.load(myObjPDF);
				createAFilePDF(allDataMap, document, document.getPage(0), new PDPageContentStream(document,
						document.getPage(0), PDPageContentStream.AppendMode.APPEND, true), 350, 750,false);

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
			int tX, int tY,boolean setDate) throws IOException {

		try {
			System.out.println("\n");
			contentStream.beginText();

			// Setting the font to the Content stream
			contentStream.setFont(PDType1Font.TIMES_ROMAN, fontSizeValue);
			
				contentStream.newLineAtOffset(tX, tY);
				if(setDate)
				{
					contentStream.newLineAtOffset(tX+530, 30);
					contentStream.showText(LocalDate.now().toString());
					contentStream.newLineAtOffset(tX-530, -30);
				}
						


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

	public boolean deleteAFIle() {

		if (myObjPDF.delete()) {
			System.out.println("File deleted successfully");
			flagDelete = true;
		} else {
			System.out.println("Failed to delete the file");
			flagDelete = false;
		}
		return flagDelete;
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

	
	
	////////////usuwanie tekstu/edycja///////////
	//https://stackoverflow.com/questions/63592078/replace-or-remove-text-from-pdf-with-pdfbox-in-java
}
