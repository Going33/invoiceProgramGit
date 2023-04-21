package GUI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.junit.jupiter.api.Test;

import Client.Client;
import Client.DataOfAll;
import Data.PDFCreator;

public class WindowApp extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JFrame windowFrame;
	private static JPanel windowPanelTop;
	private static JPanel windowPanelBottom;
	private JSplitPane windowSplit;
	private static final int widthWindowMain = 500;
	private static final int heightWindowMain = 500;

	private static final int rowSeller = 2 * (new DataOfAll(1).findSize()) + 3;
	private static final int collSeller = 1;
	private static final int rowBuyer = 2 * (new DataOfAll(2).findSize()) + 5;
	private static final int collBuyer = 1;

	private static JButton confirmButtonTop;
	private static JButton clearButtonTop;
	private static JButton confirmButtonBottom;
	private static JButton clearButtonBottom;
	private static JButton closeButton;
	private static JLabel infoLabelTop;
	private static JLabel infoLabelBottom;

	private String message = "";

	/**
	 * Go to the next step.
	 */
	private static boolean proceedFlag_1 = false;
	/**
	 * Go to the next step.
	 */
	private static boolean proceedFlag_2 = false;

	private String VATValue = "";
	/**
	 * Path of the file.
	 */
	final private String absPathPDF = System.getProperty("user.dir") + "\\Invoice_" + LocalDate.now() + ".pdf";
	/**
	 * Temporary byte array.
	 */
	private byte[] pdfNewByteArray = new byte[8192];

	/**
	 * Creating objects.
	 */
	PDFCreator pdfObject = new PDFCreator();

	/**
	 * Create the application.
	 */

	public WindowApp() {
		init();

	}

	/**
	 * Tricky builder. Allows inheritance between WindowApp and child. This makes it
	 * possible to call the constructor which does nothing and override the methods
	 * without any issues.
	 */
	public WindowApp(String VATValue) {
		this.VATValue = VATValue;
	}

	/**
	 * Initialize the contents of the frame.
	 */

	protected void init() {

		/**
		 * Inner class - TAX INFO. This class was created as an internal one because it
		 * seems to be much easier than creating a new separted class.
		 */

		class TaxChoice {

			private JLabel infoLabelTax;
			private JButton confirmTax;
			private JButton deleteTax;
			private JButton closeTax;
			private int rowTax = 8;
			private int collTax = 1;
			private String taxValue = "";
			JPanel windowPanelTax;
			JFrame windowFrameLocal;
			String list[] = { "oo.", "np.", "zw.", "0%", "5%", "7%", "8%", "23%" };
			JComboBox<Object> taxList = new JComboBox<Object>(list);

			private TaxChoice() {
				initTaxChoice();
			}

			private void initTaxChoice() {

				windowFrameLocal = new JFrame();
				// windowFrameLocal.setAlwaysOnTop(true);
				windowFrameLocal.setTitle("Tax information");
				windowFrameLocal.setPreferredSize(new Dimension(300, 300));
				windowFrameLocal.setLocation(600, 200);
				windowFrameLocal.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				windowFrameLocal.pack();
				windowFrameLocal.setResizable(false);
				windowFrameLocal.setVisible(true);
				windowPanelTax = new JPanel(new GridLayout(rowTax, collTax, 0, 0));
				windowPanelTax.setBackground(Color.GRAY);
				windowPanelTax.setVisible(true);
				windowFrameLocal.add(windowPanelTax);
				confirmTax = new JButton("Confirm");
				deleteTax = new JButton("Delete File");
				closeTax = new JButton("Close");
				taxValue = VATListPanel(windowPanelTax, 4);

				repaintFrame(windowFrameLocal);

				confirmTax.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {

						try {

							if (!pdfObject.checkIfAFIleIsAlreadyExistingPDF()) {

								message = theMiracleOfCreation(pdfObject, windowPanelTax, 4, 75, 750, 350, 30, false);

								// confirmTax.setText("Close");
								confirmTax.setEnabled(false);
								deleteTax.setEnabled(false);

								pdfNewByteArray = readAllBytesFromThePDF(absPathPDF, pdfNewByteArray);

							} else {
								message = "File already exists.";
								confirmTax.setEnabled(false);
								deleteTax.setEnabled(true);
							}

						} catch (Exception e) {
							// TODO Auto-generated catch block
							message = "Error.Cannot create the file";
							e.printStackTrace();
						}

						// JLabel infoLabelTax = null;
						ifcheckStatusOfLabel(infoLabelTax, windowPanelTax);
						infoLabelTax = createJLabel(infoLabelTax, windowPanelTax, message);

					}
				});

				deleteTax.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {

						try {

							if (pdfObject.checkIfAFIleIsAlreadyExistingPDF()) {
								message = deleteFlagGUI(new PDFCreator(), false);
								infoLabelTax = deleteTheFile(infoLabelTax, windowPanelTax, clearButtonBottom,
										clearButtonBottom, message);
								setButtonsOff();
							} else {
								message = "Unable to delete the file!";
							}

						} catch (Exception e) {
							// TODO Auto-generated catch block
							message = "Error. Cannot delete the file";
							e.printStackTrace();
						}
						confirmTax.setEnabled(true);
						deleteTax.setEnabled(false);
						ifcheckStatusOfLabel(infoLabelTax, windowPanelTax);
						infoLabelTax = createJLabel(infoLabelTax, windowPanelTax, message);
					}
				});

				closeTax.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setButtonsOn();

						closeButton.setEnabled(true);
						windowFrameLocal.dispose();

					}
				});

				windowPanelTax.add(confirmTax);
				windowPanelTax.add(deleteTax);
				windowPanelTax.add(closeTax);

			}

			/**
			 * VATListPanel - 4th from DataOfAll
			 */

			private String VATListPanel(JPanel windowPanel, int k) {

				for (int i = 0; i < new DataOfAll(k).findSize(); i++) {
					windowPanel.add(new JLabel(clientObjectChoice(i, 4)));
					if (i == 0) {
						// windowPanel.add(new JTextField(" "));

						windowPanel.add(taxList);
						confirmTax.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								taxValue = taxList.getItemAt(taxList.getSelectedIndex()).toString();
								VATValue = taxValue;

								// ifcheckStatusOfLabel(infoLabelTax, windowPanel);
								// infoLabelTax = createJLabel(infoLabelTax, windowPanel, message);

							}
						});

					} else {
						LocalDate today = LocalDate.now();
						DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						String formattedDate = today.format(dateTimeFormatter); // 17-02-2022
						windowPanel.add(new JTextField(formattedDate + "_"));
					}

				}

				return taxValue;
			}

		}

		windowFrame = new JFrame();

		windowFrame.setTitle("Invoice Program [1]");
		windowFrame.setPreferredSize(new Dimension(widthWindowMain, heightWindowMain));
		windowFrame.setLocation(500, 100);
		windowFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		windowFrame.pack();
		windowFrame.setResizable(false);
		windowFrame.setVisible(true);
		new TaxChoice();
		/**
		 * TOP PANEL.
		 */

		windowPanelTop = new JPanel(new GridLayout(rowSeller, collSeller, 5, 0));
		windowPanelTop.setBackground(Color.WHITE);
		panelDisplay(windowPanelTop, 1);

		confirmButtonTop = new JButton("Confirm Data of Seller");
		clearButtonTop = new JButton("Clear Data of Seller");

		confirmButtonTop.addActionListener(this);
		windowPanelTop.add(confirmButtonTop);
		clearButtonTop.addActionListener(this);
		windowPanelTop.add(clearButtonTop);
		clearButtonTop.setEnabled(false);

		/**
		 * BOTTOM PANEL.
		 */

		windowPanelBottom = new JPanel(new GridLayout(rowBuyer, collBuyer, 0, 0));
		windowPanelBottom.setBackground(Color.GRAY);

		panelDisplay(windowPanelBottom, 2);

		confirmButtonBottom = new JButton("Confirm Data of Buyer");
		clearButtonBottom = new JButton("Clear Data of Buyer");
		closeButton = new JButton("Close");

		confirmButtonBottom.addActionListener(this);
		windowPanelBottom.add(confirmButtonBottom);

		clearButtonBottom.addActionListener(this);
		windowPanelBottom.add(clearButtonBottom);
		clearButtonBottom.setEnabled(false);

		closeButton.addActionListener(this);
		closeButton.setEnabled(false);
		windowPanelBottom.add(closeButton);

		windowPanelBottom.setVisible(true);
		windowPanelTop.setVisible(true);

		windowSplit = new JSplitPane();
		windowSplit.setPreferredSize(windowFrame.getSize());
		windowSplit.setDividerSize(0);
		windowSplit.setDividerLocation((windowFrame.getSize().width) / 2);
		windowSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
		windowSplit.setTopComponent(windowPanelTop);
		windowSplit.setBottomComponent(windowPanelBottom);
		windowSplit.setVisible(true);

		windowFrame.add(windowSplit);
		setButtonsOff();
		repaintFrame(windowFrame);

	}

	/**
	 * Initialize the actions.
	 */

	public void actionPerformed(ActionEvent e) {

		try {
			doTheThings_actionPerformed(e);
		} catch (IOException e1) {

			e1.printStackTrace();
		}

	}

	/**
	 * Initialize the actions [2]
	 */
	public void doTheThings_actionPerformed(ActionEvent e) throws IOException {

		switch (e.getActionCommand()) {

		case "Confirm Data of Seller":

			try {
				message = confrimDataMethod(pdfObject, message, windowPanelTop, 1, 5, 700, 530, 30, false,
						confirmButtonTop, clearButtonTop);
			} catch (Exception e1) {
				e1.printStackTrace();
				message = "Error.Cannot create the file";

			}

			ifcheckStatusOfLabel(infoLabelTop, windowPanelTop);
			infoLabelTop = createJLabel(infoLabelTop, windowPanelTop, message);
			proceedFlag_1 = true;

			break;

		case "Confirm Data of Buyer":
			try {

				message = confrimDataMethod(pdfObject, message, windowPanelBottom, 2, 350, 700, 0, 0, false,
						confirmButtonBottom, clearButtonBottom);

			} catch (Exception e1) {

				e1.printStackTrace();
				message = "Error.Cannot create the file";

			}

			ifcheckStatusOfLabel(infoLabelBottom, windowPanelBottom);
			infoLabelBottom = createJLabel(infoLabelBottom, windowPanelBottom, message);

			proceedFlag_2 = true;
			// changeData = false;
			break;

		case "Clear Data of Seller":

			infoLabelTop = clearData(infoLabelTop, windowPanelTop, confirmButtonTop, clearButtonTop,
					true,windowFrame);
			break;

		case "Clear Data of Buyer":

			infoLabelBottom = clearData(infoLabelBottom, windowPanelBottom, confirmButtonBottom, clearButtonBottom, true,windowFrame);
			break;
		case "Close":
			windowFrame.dispose();
		default:

		}

		repaintFrame(windowFrame);

		nextWindow(proceedFlag_1, proceedFlag_2, pdfObject, windowFrame);

	}

	/**
	 * Confirm method. This method contains statements for the Seller/Buyer.
	 */
	protected String confrimDataMethod(PDFCreator fileObject, String messageInfo, JPanel windowPanel, int clientchoice,
			int xpos, int ypos, int xoffset, int yoffset, boolean addMoney, JButton confirmButton,
			JButton clearButton) {
		if (fileObject.checkIfAFIleIsAlreadyExistingPDF()) {
			messageInfo = theMiracleOfCreation(fileObject, windowPanel, clientchoice, xpos, ypos, xoffset, yoffset,
					addMoney);
			confirmButton.setEnabled(false);
			clearButton.setEnabled(false);
		} else {
			if (!fileObject.checkIfAFIleIsAlreadyExistingPDF()) {
				messageInfo = "There is no existing file!";
				setButtonsOff();
			} else {
				messageInfo = "Error";
			}
			setButtonsOff();
		}
		return messageInfo;
	}

	/**
	 * This method is required due to the specific settings of flags during
	 * overwriting.
	 */

	protected void setSpecificStateOfFlags(boolean state) {
		if (state) {
		} else {
		}
	}

	protected void ifcheckStatusOfLabel(JLabel infoLabel, JPanel windowPanel) {
		if (!checkStatusOfLabel(infoLabel, windowPanel)) {
			windowPanel.remove(infoLabel);
		}
	}

	/**
	 * Next Window Decision.
	 */

	void nextWindow(boolean flag, boolean flag1, PDFCreator fileObject, JFrame windowFrame2) throws IOException {

		int proceed;

		if (flag1 && flag && fileObject.checkIfAFIleIsAlreadyExistingPDF()) {
			Object[] options = { "OK", "CHANGE DATA" };
			proceed = JOptionPane.showOptionDialog(windowFrame2,
					"All data confirmed, pdf file has been created. Proceed?", "Warning", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

			switch (proceed) {
			case 0:
				windowFrame2.dispose();
				new GUI.Service(getVat());
				break;
			case 1:
				restoreSavedByteArrayAsAFile(absPathPDF, pdfNewByteArray);
				clearEverything();
				break;
			default:
			}

		} else {
			Object[] options = { "OK" };
			if (flag1 && flag && !fileObject.checkIfAFIleIsAlreadyExistingPDF()) {
				proceed = JOptionPane.showOptionDialog(windowFrame, "File dosen't exist!", "Warning",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				clearEverything();
				windowFrame2.dispose();
			}
		}
	}

	/**
	 * The byte array created is used to restore the "old" pdf file and allows you
	 * to. overwrite it. PDFBOX allows you to overwrite a file by removing its
	 * previous content. A better solution for this functionality should be found.
	 * 
	 */

	protected void restoreSavedByteArrayAsAFile(String path, byte[] byteArray) {
		new File(path).delete();
		OutputStream out;
		try {

			out = new FileOutputStream(path);
			out.write(byteArray);
			out.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Creating temporary "backup" of file as a byte array. Not so nice way, needs
	 * improvement
	 */

	protected byte[] readAllBytesFromThePDF(String path, byte[] byteArray) {
		byteArray = null;
		try {
			byteArray = Files.readAllBytes(Paths.get(path));

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return byteArray;

	}

	/**
	 * Creating specific Object [1]. Read fields and convert them into the TreeMap
	 */

	Client createClientObject(Client clientInInfos, JPanel windowPanel, int clientchoice) {
		int z = 0;
		int k = 0;
		clientInInfos.clearTreeMap();
		for (Component c : windowPanel.getComponents()) {

//			instanceof is a binary operator we use to test if an object is of a given type. The result of the operation is either true or false.
//			It's also known as a type comparison operator because it compares the instance with the type.
			if (c instanceof JTextField || c instanceof JComboBox) {

				if (z % 2 != 0) {
					if (c instanceof JTextField) {
						clientInInfos.fillUpTheMap(clientObjectChoice(k, clientchoice).toString(),
								((JTextField) c).getText());
					}
					if (c instanceof JComboBox) {
						clientInInfos.fillUpTheMap(clientObjectChoice(k, clientchoice).toString(),
								(((JComboBox<Object>) c).getItemAt(((JComboBox<Object>) c).getSelectedIndex())
										.toString()));
					}
					k = k + 1;
				}

			}
			z++;

		}

		return clientInInfos;
	}

	private void saveAsTheByte(TreeMap<String, String> object) {
		try {
			// Serialize the TreeMap object
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);

			// Convert the ByteArrayOutputStream to a byte array
			byte[] bytes = baos.toByteArray();

			// Print the byte array
			for (byte b : bytes) {
				System.out.print(b + " ");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Creating specific Object [2] Creation of a pdf file. Additional error
	 * checking that all fields are filled in (this return state from the
	 * addPaymentInformation method).
	 * 
	 */

	protected String theMiracleOfCreation(PDFCreator object, JPanel windowPanel, int clientchoice, int x_pos, int y_pos,
			int x_Offset, int y_Offset, boolean addMoney) {
		Boolean checkIfError;

		checkIfError = object.makeAFile(createClientObject(new Client(), windowPanel, clientchoice).getClientMap(),
				x_pos, y_pos, x_Offset, y_Offset, addMoney);
		return errorEncounteredNoneEmptyField(checkIfError);
	}

	/**
	 * Getting and adding names of fields[1] Getting name from the DataOfAll class.
	 */

	String clientObjectChoice(int i, int clientchoice) {

		String elementsOfListClientInfo = " ";

		elementsOfListClientInfo = new DataOfAll(clientchoice).elements(i);

		return elementsOfListClientInfo;
	}

	/**
	 * Getting and adding names of fields [2] Adding names to the window frame and
	 * display them.
	 */
	protected void panelDisplay(JPanel windowPanel, int clientchoice) {

		for (int i = 0; i < new DataOfAll(clientchoice).findSize(); i++) {
			windowPanel.add(new JLabel(clientObjectChoice(i, clientchoice)));
			/**
			 * Special conditions, these fields cannot be editable.
			 */
			if (clientchoice == 3) {

				String a = new JLabel(clientObjectChoice(i, clientchoice)).getText();

				if (a.equals("07.VAT rate")) {
					// //System.out.println("VAT check " + this.VATValue);
					((JTextComponent) windowPanel.add(new JTextField(this.VATValue))).setEditable(false);
				} else if (a.equals("08.Net value") || a.equals("09.VAT amount") || a.equals("10.Gross value")) {
					((JTextComponent) windowPanel.add(new JTextField(""))).setEditable(false);

				} else {
					windowPanel.add(new JTextField(""));
				}

			} else {
				windowPanel.add(new JTextField(""));
			}

		}
	}

	/**
	 * Getting and adding names of fields [3] Method for the Label creating.
	 */

	JLabel createJLabel(JLabel infoLabel, JPanel windowPanel, String message) {
		if (infoLabel != null) {

			windowPanel.remove(infoLabel);
		}
		infoLabel = new JLabel();
		windowPanel.add(infoLabel);
		infoLabel.setText(message);
		return infoLabel;
	}

	/**
	 * Repainting the Frame
	 */

	protected static void repaintFrame(JFrame windowFrame) {
		windowFrame.invalidate();
		windowFrame.validate();
		windowFrame.repaint();
	}

	/**
	 * Call to clear
	 * 
	 * 
	 */
	protected JLabel clearData(JLabel infoLabel, JPanel windowPanel, JButton confirmButton, JButton clearButton,
			boolean b, JFrame windowFrame) {
		if (!checkStatusOfLabel(infoLabel, windowPanel)) {

			windowPanel.remove(infoLabel);
			clearOnlyPanel(windowPanel, infoLabel, confirmButton, clearButton, b,windowFrame);
		} else {
			clearOnlyPanel(windowPanel, infoLabel, confirmButton, clearButton, b,windowFrame);
		}
		infoLabel = createJLabel(infoLabel, windowPanel, "Cleared");
		confirmButton.setEnabled(true);
		clearButton.setEnabled(false);
		return infoLabel;
	}

	/**
	 * Clear only panel
	 */
	static void clearOnlyPanel(JPanel panelToClean, JLabel infoLabel, JButton BtnConfirm, JButton BtnClear,
			boolean choice, JFrame windowFrame) {

		if (infoLabel != null) {

			panelToClean.remove(infoLabel);
		}

		if (choice) {
			for (Component c : panelToClean.getComponents()) {
				if (c instanceof JTextField) {
					((JTextField) c).setText("");
				}
			}
		}
		// clearEverything();
		// setFlagsFalse();
		repaintFrame(windowFrame);
		BtnConfirm.setEnabled(true);
		BtnClear.setEnabled(false);

	}

	/**
	 * Clear everything
	 */
	static void clearEverything() {

		clearOnlyPanel(windowPanelTop, infoLabelTop, confirmButtonTop, clearButtonTop, false,windowFrame);
		clearOnlyPanel(windowPanelBottom, infoLabelBottom, confirmButtonBottom, clearButtonBottom, false,windowFrame);

		setFlagsFalse();
		setButtonsOn();
		repaintFrame(windowFrame);
	}

	/**
	 * Setting Flags
	 */
	static void setFlagsFalse() {
		proceedFlag_1 = false;
		proceedFlag_2 = false;
	}

	/**
	 * Setting setButtonsOn
	 */
	static void setButtonsOn() {
		confirmButtonTop.setEnabled(true);
		clearButtonTop.setEnabled(true);
		confirmButtonBottom.setEnabled(true);
		clearButtonBottom.setEnabled(true);
	}

	/**
	 * Setting setButtonsOff
	 */
	static void setButtonsOff() {
		confirmButtonTop.setEnabled(false);
		clearButtonTop.setEnabled(false);
		confirmButtonBottom.setEnabled(false);
		clearButtonBottom.setEnabled(false);
	}

	/**
	 * Call to Delete the file method
	 */
	protected JLabel deleteTheFile(JLabel infoLabel, JPanel windowPanel, JButton clearButton, JButton clearButton2,
			String message) {

		if (infoLabel == null) {
			infoLabel = createJLabel(infoLabel, windowPanel, message);

		} else {

			windowPanel.remove(infoLabel);
			infoLabel = createJLabel(infoLabel, windowPanel, message);
		}
		clearButton.setEnabled(true);
		clearButton2.setEnabled(true);
		return infoLabel;

	}

	/**
	 * Internal test method Check if Label exists of not
	 */
	boolean checkStatusOfLabel(JLabel infoLabel, JPanel windowPanel) {
		boolean flaghelp;
		if (infoLabel != null) {
			// //System.out.println("Confirmed NOT NULL");
			flaghelp = false;
		} else {
			// //System.out.println("Confirmed IS NULL");
			flaghelp = true;

		}
		return flaghelp;
	}

	/**
	 * Getting Vat value
	 */
	protected String getVat() {
		// //System.out.println("getVAt" + VATValue);
		return this.VATValue;
	}

	/**
	 * Set a specific message depending on whether the file has been deleted or not
	 *
	 */
	String deleteFlagGUI(PDFCreator deleteFile, boolean flagAppWindow) {
		clearEverything();
		flagAppWindow = deleteFile.deleteAFIle();
		if (flagAppWindow) {
			message = "File deleted successfully";
		} else {
			message = "Failed to delete the file";
		}

		return message;
	}

	/**
	 * Get the result of the function check from PDFCreator if the field is empty.
	 * Set a specific message.
	 */
	protected String errorEncounteredNoneEmptyField(Boolean booleanCheck) {
		if (booleanCheck) {
			message = "Confirmed. Successfully wrote/append to the file.";
		} else {
			message = "Error encountered.";
			setFlagsFalse();
			setButtonsOff();
		}
		return message;
	}

	

	
}
