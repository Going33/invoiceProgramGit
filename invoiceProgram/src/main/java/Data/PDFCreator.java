package Data;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File; // Import the File class
import java.io.FileOutputStream;
import java.io.IOException; // Import the IOException class to handle errors
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;

import Client.DataOfAll;

public class PDFCreator {

	final private String absPathPDF = System.getProperty("user.dir") + "\\Invoice_" + LocalDate.now() + ".pdf";

	final private File myObjPDF = new File(absPathPDF);

	private boolean flagDelete;
	private boolean flagCheck;
	private int fontSizeValue = 12;
	private byte[] firstArray;
	private byte[] secondArray;
	private byte[] thirdArray;
	/**
	 * Create pdf file, otherwise overwrite.
	 */
	public void makeAFile(Map<String, String> allDataMap, int x_pos, int y_pos, int x_Offset, int y_Offset,
			boolean addMoney, boolean changeData, int i) {
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
				//System.out.println("PDF file created.");
				document.close();
				System.out.println(" myObjPDF.createNewFile() writeToByte(firstArray,allDataMap)");
				firstArray =writeToByte(firstArray,allDataMap);
				//allDataMap=null;
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
				secondArray=writeToByte(secondArray,allDataMap);
				//allDataMap=null;
			}
			
			//}
		} catch (IOException e) {
			//System.out.println("An error occurred.");
			e.printStackTrace();
		}
		//allDataMap = null;
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
			//System.out.println("\n");
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
				contentStream.newLineAtOffset(0, -100);
				
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
						//System.out.println(set.getKey().toString());
						contentStream.showText(set.getKey().toString());

						contentStream.newLineAtOffset(0, -20);
						//System.out.println(set.getValue().toString());
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
			//System.out.println("An error occurred.");
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

	private byte[] writeToByte(byte[] tab, Map<String,String> map)
	{
	//	tab = new byte[map.size()];
	       // try catch block
        try {
//            FileOutputStream myFileOutStream
//                = new FileOutputStream("C:\\Users\\Bartek\\eclipse-workspace\\GIT\\invoiceProgram\\newHashMap.txt");
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(5000);
            ObjectOutputStream myObjectOutStream
                = new ObjectOutputStream(new BufferedOutputStream(byteStream));
  
            myObjectOutStream.writeObject(map);
  
            // closing FileOutputStream and
            // ObjectOutputStream
            myObjectOutStream.close();
          //  myFileOutStream.close();
           tab = byteStream.toByteArray();
           System.out.println(map.size());
           System.out.println(tab.length);
           // encode, convert byte[] to base64 encoded string
           String s = Base64.getEncoder().encodeToString(tab);

          // System.out.println(s);
           String s1 = new String(tab, StandardCharsets.UTF_8);
         //  System.out.println("Output : " + s1);

           // decode, convert base64 encoded string back to byte[]
           byte[] decode = Base64.getDecoder().decode(s);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return tab;
	}
	
	//////////// usuwanie tekstu/edycja///////////
	// https://stackoverflow.com/questions/63592078/replace-or-remove-text-from-pdf-with-pdfbox-in-java
}
