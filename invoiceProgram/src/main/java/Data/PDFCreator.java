package Data;

import java.io.File; // Import the File class
import java.io.IOException; // Import the IOException class to handle errors
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import Client.DataOfAll;

public class PDFCreator {

	final private String absPathPDF = System.getProperty("user.dir") + "\\Invoice_" + LocalDate.now() + ".pdf";

	final private File myObjPDF = new File(absPathPDF);

	private boolean flagDelete;
	private boolean flagCheck;
	private int fontSizeValue = 12;
	/**
	 * Create pdf file, otherwise overwrite.
	 */
	public void makeAFile(Map<String, String> allDataMap, int x_pos, int y_pos, int x_Offset, int y_Offset,
			boolean addMoney, boolean changeData) {
		//System.out.println("makeAFile funkcja " + changeData);
		try {

			if (myObjPDF.createNewFile()) {
				//System.out.println("File created: " + myObjPDF.getName());
				//System.out.println(System.getProperty("user.dir"));
				// create a file

				PDDocument document = new PDDocument();

				document.addPage(new PDPage());
				
				addPaymentInformation(allDataMap, document, document.getPage(0),
						new PDPageContentStream(document, document.getPage(0)), x_pos, y_pos, x_Offset, y_Offset, true,
						addMoney,changeData);
			
				document.close();
				
				
				
				Path pdfPath = Paths.get(absPathPDF);
				
				try {
					 Files.readAllBytes(pdfPath);
				} catch (IOException e1) {
				
					e1.printStackTrace();
				}

			} else {
				PDDocument document = PDDocument.load(myObjPDF);
				
				
				if(changeData)
				{
					addPaymentInformation(allDataMap, document, document.getPage(0), new PDPageContentStream(document,
							document.getPage(0), PDPageContentStream.AppendMode.OVERWRITE, true),
					x_pos, y_pos, x_Offset, y_Offset, false, addMoney,changeData);
				}else {
				addPaymentInformation(
						allDataMap, document, document.getPage(0), new PDPageContentStream(document,
								document.getPage(0), PDPageContentStream.AppendMode.APPEND, true),
						x_pos, y_pos, x_Offset, y_Offset, false, addMoney,changeData);
				
				}		

				document.close();
				System.out.println(" else myObjPDF.createNewFile() writeToByte(firstArray,allDataMap)");

			}
			
			//}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 3 scenarios are possible. If there is no client - add infos abt him, if
	 * seller/buyer is already added, add the next one, if both clients are added
	 * then add gross.
	 */
	public void addPaymentInformation(Object object, PDDocument document, PDPage page,
			PDPageContentStream contentStream, int tX, int tY, int x_Offset, int y_Offset, boolean firstWrite,
			boolean addMoney,boolean changeData) throws IOException {
		int i = 0;
		float j=0;

		try {

			contentStream.beginText();

			contentStream.setFont(PDType1Font.TIMES_ROMAN, fontSizeValue);
			contentStream.newLineAtOffset(tX, tY);
			/**
			 * First write.
			 */

			if (firstWrite || changeData) {
				contentStream.newLineAtOffset(tX + x_Offset, y_Offset);
				contentStream.showText(LocalDate.now().toString());
				contentStream.newLineAtOffset(tX - x_Offset, -y_Offset);
				
			}

			/**
			 * If clients data are already added then go addMoney=true. TODO Optimize
			 */
			if (addMoney) {
				contentStream.newLineAtOffset(0,0);
				
				//TODO -> optimize
				if(new DataOfAll(3).findSize()%2==0)
				{
					j=new DataOfAll(3).findSize();
				}else {
					j=new DataOfAll(3).findSize()+1;
					
				}
				contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
				
				

				for (Map.Entry<String, String> set : ((Map<String, String>) object).entrySet()) {

					if (i < j/2) {
						contentStream.showText(set.getKey().toString());

						contentStream.newLineAtOffset(0, -20);
						contentStream.showText(set.getValue().toString());
						contentStream.newLineAtOffset(0, 20);
						contentStream.newLineAtOffset(130, 0);
						i = i + 1;
						if (i == j/2) {
							/**
							 * Go to next line.
							 */
							contentStream.newLineAtOffset(-650, -50);
						}
					}

					else {
						contentStream.showText(set.getKey().toString());
						contentStream.newLineAtOffset(0, -20);
						contentStream.showText(set.getValue().toString());
						contentStream.newLineAtOffset(0, 20);
						contentStream.newLineAtOffset(130, 0);
						i = i + 1;
					}

				}

			} else {
				/**
				 * Second write.
				 */
				for (Map.Entry<String, String> set : ((Map<String, String>) object).entrySet()) {
					contentStream.showText(set.getKey().toString() + " " + set.getValue().toString());
					contentStream.newLineAtOffset(0, -20);
				}
			}
			contentStream.endText();
			contentStream.close();
			document.save(new File(absPathPDF));
			document.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set the flag to true if the file is deleted.
	 */
	public boolean deleteAFIle() {

		if (myObjPDF.delete()) {
			flagDelete = true;
		} else {

			flagDelete = false;
		}
		return flagDelete;
	}

	/**
	 * Set the flag to true if the file exists.
	 */
	public boolean checkIfAFIleIsAlreadyExistingPDF(boolean changeData) {
		if (myObjPDF.exists() && changeData == false) {
			flagCheck = false;
		} else {
			flagCheck = true;
		}
		return flagCheck;
	}

}
