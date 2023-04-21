package Data;

import java.io.File; // Import the File class
import java.io.IOException; // Import the IOException class to handle errors
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
	private String grossValue = "";
	private String VATRate = "";
	private String Qty = "";
	private String unitPrice = "";
	String VATAmount = "";
	Float netto;
	String nettostring = "";
	final private int fontSizeValue = 12;
	Boolean emptyFieldState;
	Boolean resultOfaddPaymentInformation;

	/**
	 * Create pdf file, otherwise overwrite.
	 * 
	 * @return
	 */
	public Boolean makeAFile(Map<String, String> allDataMap, int x_pos, int y_pos, int x_Offset, int y_Offset,
			boolean addMoney) {
		// System.out.println("makeAFile funkcja " + changeData);
		try {

			if (myObjPDF.createNewFile()) {
				// System.out.println("File created: " + myObjPDF.getName());
				// System.out.println(System.getProperty("user.dir"));
				// create a file

				PDDocument document = new PDDocument();

				document.addPage(new PDPage());

				resultOfaddPaymentInformation = addPaymentInformation(allDataMap, document, document.getPage(0),
						new PDPageContentStream(document, document.getPage(0)), x_pos, y_pos, x_Offset, y_Offset, true,
						addMoney);

				document.close();

				Path pdfPath = Paths.get(absPathPDF);

				try {
					Files.readAllBytes(pdfPath);
				} catch (IOException e1) {

					e1.printStackTrace();
				}

			} else {
				PDDocument document = PDDocument.load(myObjPDF);
				resultOfaddPaymentInformation = addPaymentInformation(allDataMap, document, document.getPage(0),
						new PDPageContentStream(document, document.getPage(0),
								PDPageContentStream.AppendMode.APPEND, true),
						x_pos, y_pos, x_Offset, y_Offset, false, addMoney);
			

				document.close();
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
		return resultOfaddPaymentInformation;
	}

	/**
	 * 3 scenarios are possible. If there is no client - add infos abt him, if
	 * seller/buyer is already added, add the next one, if both clients are added
	 * then add gross.
	 * 
	 * @return
	 */
	
	public Boolean addPaymentInformation(Object object, PDDocument document, PDPage page,
			PDPageContentStream contentStream, int tX, int tY, int x_Offset, int y_Offset, boolean firstWrite,
			boolean addMoney) throws IOException {
		int i = 0;
		float j = 0;

		try {

			contentStream.beginText();

			contentStream.setFont(PDType1Font.TIMES_ROMAN, fontSizeValue);
			contentStream.newLineAtOffset(tX, tY);
			/**
			 * First write.
			 */

			if (firstWrite) {
				contentStream.newLineAtOffset(tX + x_Offset, y_Offset);
				contentStream.showText(LocalDate.now().toString());
				contentStream.newLineAtOffset(tX - x_Offset, -y_Offset);

			}

			/**
			 * If clients data are already added then go addMoney=true. TODO Optimize
			 */
			if (addMoney) {
				contentStream.newLineAtOffset(0, 0);

				// TODO -> optimize
				if (new DataOfAll(3).findSize() % 2 == 0) {
					j = new DataOfAll(3).findSize();
				} else {
					j = new DataOfAll(3).findSize() + 1;

				}
				contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);

				for (Map.Entry<String, String> set : ((Map<String, String>) object).entrySet()) {

					if (set.getKey() == "08.Net value" || set.getKey() == "09.VAT amount"
							|| set.getKey() == "10.Gross value") {
						emptyFieldState = true;
					} else {

						if (!isEmpty(set.getValue().toString(), set.getKey().toString())) {
							emptyFieldState = false;
							break;
						} else {

							emptyFieldState = true;

						}
					}

					if (i < j / 2) {
						contentStream.showText(set.getKey().toString());
						contentStream.newLineAtOffset(0, -20);
						contentStream.showText(set.getValue().toString());
						contentStream.newLineAtOffset(0, 20);
						contentStream.newLineAtOffset(130, 0);
						i = i + 1;

						if (set.getKey() == ("04.Qty")) {

							if (!isNumeric(set.getValue())) {
								// Qty = set.getValue();
								break;

							} else {
								Qty = set.getValue();
							}

						}

						if (i == j / 2) {
							/**
							 * Go to next line.
							 */
							contentStream.newLineAtOffset(-650, -50);
						}
					}

					else {

						if (set.getKey() == ("06.Unit price")) {

							if (isNumeric(set.getValue())) {
								unitPrice = set.getValue();
								contentStream.showText(set.getKey().toString());
								contentStream.newLineAtOffset(0, -20);
								contentStream.showText(set.getValue().toString());
								contentStream.newLineAtOffset(0, 20);
								contentStream.newLineAtOffset(130, 0);
							} else {
								break;
							}

						} else if (set.getKey() == ("07.VAT rate")) {
							VATRate = set.getValue();
							contentStream.showText(set.getKey().toString());
							contentStream.newLineAtOffset(0, -20);
							contentStream.showText(set.getValue().toString());
							contentStream.newLineAtOffset(0, 20);
							contentStream.newLineAtOffset(130, 0);
						}

						else if (set.getKey() == ("08.Net value")) {
							System.out.println("unitPrice " + unitPrice + " Qty " + Qty);
							netto = Float.parseFloat(unitPrice) * Float.parseFloat(Qty);
							nettostring = Double.toString(netto);
							System.out.println("Netto :" + nettostring);
							grossValue = countBrutto(nettostring, VATRate);
							contentStream.showText(set.getKey().toString());
							contentStream.newLineAtOffset(0, -20);
							contentStream.showText(nettostring);
							contentStream.newLineAtOffset(0, 20);
							contentStream.newLineAtOffset(130, 0);
							//
						}

						else if (set.getKey().equals("09.VAT amount")) {
							Float temp;
							try {
								temp = Float.parseFloat(grossValue) - netto;
								VATAmount = temp.toString();
							} catch (NumberFormatException e) {
								VATAmount = grossValue.toString();
								grossValue = netto.toString();
							}

							System.out.println("VATAmount " + VATAmount);
							contentStream.showText(set.getKey().toString());
							contentStream.newLineAtOffset(0, -20);
							contentStream.showText(VATAmount);
							contentStream.newLineAtOffset(0, 20);
							contentStream.newLineAtOffset(130, 0);

						}

						else if (set.getKey().equals("10.Gross value")) {
							contentStream.showText(set.getKey().toString());
							contentStream.newLineAtOffset(0, -20);
							contentStream.showText(grossValue);
							contentStream.newLineAtOffset(0, 20);
							contentStream.newLineAtOffset(130, 0);

						} else {
							contentStream.showText(set.getKey().toString());
							contentStream.newLineAtOffset(0, -20);
							contentStream.showText(set.getValue().toString());
							contentStream.newLineAtOffset(0, 20);
							contentStream.newLineAtOffset(130, 0);
						}
						i = i + 1;
					}

				}

			} else {
				/**
				 * Second write.
				 */
				for (Map.Entry<String, String> set : ((Map<String, String>) object).entrySet()) {

					if (!isEmpty(set.getValue().toString(), set.getKey().toString())) {
						emptyFieldState = false;
						break;
					} else {

						emptyFieldState = true;

					}

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
		return emptyFieldState;
	}

	/**
	 * Check if the field is a numeric value.
	 */
	private Boolean isNumeric(String userInput) {
		Boolean result;

		try {

			Float.parseFloat(userInput);
			// localVariable = check.toString();
			result = true;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Error! Number Format Exception! " + userInput, "Error",
					JOptionPane.ERROR_MESSAGE);
			result = false;
		}
		return result;
	}

	/**
	 * Check if the field is empty.
	 */
	private Boolean isEmpty(String userInput, String fieldName) {
		Boolean result;

		if (userInput == null || userInput.length() == 0)

		{
			result = false;
			JOptionPane.showMessageDialog(new JFrame(), "Error! Empty field! " + fieldName, "Error",
					JOptionPane.ERROR_MESSAGE);

		} else {
			result = true;
		}
		return result;

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
	public boolean checkIfAFIleIsAlreadyExistingPDF() {
		if (myObjPDF.exists()) {
			flagCheck = true;
		} else {
			flagCheck = false;
		}
		return flagCheck;
	}

	/**
	 * Gross-Net
	 */
	public String countBrutto(String value, String VAxTValue2) {

		String result = "";
		double resultFloat = 0;

		switch (VAxTValue2) {
		case "oo.":
			result = "Reverse charge";

			break;
		case "np.":
			result = "Not subject to VAT";

			break;
		case "zw.":
			result = "VAT exemption";

			break;
		case "0%":
			resultFloat = Float.parseFloat(value) * 1;
			result = Double.toString(resultFloat);
			break;
		case "5%":
			resultFloat = (Float.parseFloat(value) * 1.05);
			result = Double.toString(resultFloat);
			break;
		case "7%":
			resultFloat = Float.parseFloat(value) * 1.07;
			result = Double.toString(resultFloat);
			break;
		case "8%":
			resultFloat = Float.parseFloat(value) * 1.08;
			result = Double.toString(resultFloat);
			break;
		case "23%":
			resultFloat = Float.parseFloat(value) * 1.23;
			result = Double.toString(resultFloat);
			break;
		default:
		}

		return result;
	}

}
