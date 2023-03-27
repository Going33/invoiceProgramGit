package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import Client.Client;
import Client.DataOfAll;
import Data.PDFCreator;

public class WindowApp extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Frame Variable static - needed to share exactly the same variable between
	 * parent and child (not a copy of var)
	 */
	//
	private static JFrame windowFrame;
	// private static JPanel windowPanelTax;
	private static JPanel windowPanelSeller;
	private static JPanel windowPanelBuyer;
	private JSplitPane windowSplit;
	private int widthWindowMain = 500;
	private int heightWindowMain = 500;

	private int rowSeller = 2 * (new DataOfAll(1).findSize()) + 3;
	private int collSeller = 1;
	private int rowBuyer = 2 * (new DataOfAll(2).findSize()) + 5;
	private int collBuyer = 1;

	private static JButton confirmButtonSeller;
	private static JButton clearButtonSeller;
	private static JButton confirmButtonBuyer;
	private static JButton clearButtonBuyer;
	private static JButton deleteButton;
	private static JButton closeButton;

	private static JLabel infoLabelSeller;
	private static JLabel infoLabelBuyer;

	private String message = "";

	/**
	 * Auxiliary booleans
	 */
	//
	private static boolean conditionFlag_1 = false;
	private static boolean conditionFlag_2 = false;
	private static boolean conditionFlag_3 = false;
	private static boolean proceedFlag_1 = false;
	private static boolean proceedFlag_2 = false;

	private static boolean changeData = false;
	private static int iteratorChangeData = 0;
	private String VATValue = "";
	private String nettoValue = "";

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
	 * possible to call the "zero" constructor and override the remainder method
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
		 * Inner class - TAX INFO.
		 */

		class TaxChoiceLocal {

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

			private TaxChoiceLocal() {
				initLocal();
			}

			private void initLocal() {

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

							if (pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData)) {
								message = "Confirmed. Successfully wrote to the file.";

								theMiracleOfCreation(pdfObject, windowPanelTax, 4, 75, 750, 350, 30, false, changeData);
								// confirmTax.setText("Close");
								confirmTax.setEnabled(false);
								deleteTax.setEnabled(false);

								// System.out.println(taxValue);
							} else {
								message = "File already exists.";
								confirmTax.setEnabled(false);
								deleteTax.setEnabled(true);
							}

						} catch (Exception e) {
							// TODO Auto-generated catch block
							message = "Error.";
							e.printStackTrace();
						}

						// JLabel infoLabelTax = null;
						ifcheckStatusOfLabel_TEMP(infoLabelTax, windowPanelTax);
						infoLabelTax = createJLabel(infoLabelTax, windowPanelTax, message);

					}
				});

				deleteTax.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {

						try {

							if (!pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData)) {
								message = deleteFlagGUI(new PDFCreator(), false);
								infoLabelTax = deleteTheFile(infoLabelTax, windowPanelTax, clearButtonBuyer,
										clearButtonBuyer, message);
								setButtonsOff();
								// deleteButton.setEnabled(false);
								conditionFlag_3 = false;
							}

						} catch (Exception e) {
							// TODO Auto-generated catch block
							message = "Error.";
							e.printStackTrace();
						}
						confirmTax.setEnabled(true);
						deleteTax.setEnabled(false);
						ifcheckStatusOfLabel_TEMP(infoLabelTax, windowPanelTax);
						infoLabelTax = createJLabel(infoLabelTax, windowPanelTax, message);
					}
				});

				closeTax.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						conditionFlag_3 = true;
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
			 * VATListPanel - 4 from DataOfAll
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

								// ifcheckStatusOfLabel_TEMP(infoLabelTax, windowPanel);
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
		new TaxChoiceLocal();

		// panelDisplay(windowPanelTax, 5);
		// windowPanelTax.setVisible(true);
		// windowFrame.add(windowPanelTax);
		/**
		 * TOP PANEL.
		 */

		windowPanelSeller = new JPanel(new GridLayout(rowSeller, collSeller, 5, 0));
		windowPanelSeller.setBackground(Color.WHITE);
		panelDisplay(windowPanelSeller, 1);

		confirmButtonSeller = new JButton("Confirm Data of Seller");
		clearButtonSeller = new JButton("Clear Data of Seller");

		confirmButtonSeller.addActionListener(this);
		windowPanelSeller.add(confirmButtonSeller);
		clearButtonSeller.addActionListener(this);
		windowPanelSeller.add(clearButtonSeller);
		clearButtonSeller.setEnabled(false);

		/**
		 * BOTTOM PANEL.
		 */

		windowPanelBuyer = new JPanel(new GridLayout(rowBuyer, collBuyer, 0, 0));
		windowPanelBuyer.setBackground(Color.GRAY);

		panelDisplay(windowPanelBuyer, 2);

		confirmButtonBuyer = new JButton("Confirm Data of Buyer");
		clearButtonBuyer = new JButton("Clear Data of Buyer");
		// deleteButton = new JButton("Delete the file");
		closeButton = new JButton("Close");

		confirmButtonBuyer.addActionListener(this);
		windowPanelBuyer.add(confirmButtonBuyer);

		clearButtonBuyer.addActionListener(this);
		windowPanelBuyer.add(clearButtonBuyer);
		clearButtonBuyer.setEnabled(false);

		// deleteButton.addActionListener(this);
		// windowPanelBuyer.add(deleteButton);
		// deleteButton.setEnabled(false);
		closeButton.addActionListener(this);
		closeButton.setEnabled(false);
		windowPanelBuyer.add(closeButton);

		windowPanelBuyer.setVisible(true);
		windowPanelSeller.setVisible(true);

		Dimension maxSize;
		windowSplit = new JSplitPane();
		windowSplit.setPreferredSize(windowFrame.getSize());
		windowSplit.setDividerSize(0);
		windowSplit.setDividerLocation((windowFrame.getSize().width) / 2);
		windowSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
		windowSplit.setTopComponent(windowPanelSeller);
		windowSplit.setBottomComponent(windowPanelBuyer);
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

	public void doTheThings_actionPerformed(ActionEvent e) throws IOException {
		switch (e.getActionCommand()) {

		case "Confirm Data of Seller":

			try {
				// TODO TO OPTIMIZE!!!!!!!!!!!!!
//				System.out.println(
//						"pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData) && !conditionFlag_1 && !conditionFlag_2 "
//								+ pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData) + conditionFlag_1
//								+ conditionFlag_2);
				if (pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData) && !conditionFlag_1 && !conditionFlag_2) {
					theMiracleOfCreation(pdfObject, windowPanelSeller, 1, 5, 700, 530, 30, false, changeData);
					message = "Confirmed. Successfully wrote to the file.";
					conditionFlag_1 = true;
					conditionFlag_2 = false;
					confirmButtonSeller.setEnabled(false);
					clearButtonSeller.setEnabled(false);

				} else {

					if (!pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData)
							&& ((!conditionFlag_1 && conditionFlag_2) || conditionFlag_3)) {
						theMiracleOfCreation(pdfObject, windowPanelSeller, 1, 5, 700, 530, 30, false, changeData);
						message = "Append data to the file.";
						confirmButtonSeller.setEnabled(false);
						clearButtonSeller.setEnabled(false);
						conditionFlag_1 = true;
						conditionFlag_2 = false;
						conditionFlag_3 = false;
					} else {
						if (!pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData)) {
							message = "Old file already exists. Cannot overwrite.";
							setButtonsOff();
						} else {
							message = "Error";
						}
					}
				}

			} catch (Exception e1) {
				e1.printStackTrace();
				message = "Error";

			}

///////////////////////////////////////////////just for now, gonna be deleted in the future////
			ifcheckStatusOfLabel_TEMP(infoLabelSeller, windowPanelSeller);
///////////////////////////////////////////////just for now, gonna be deleted in the future////

			infoLabelSeller = createJLabel(infoLabelSeller, windowPanelSeller, message);

			proceedFlag_1 = true;
			changeData = false;
			break;

		case "Confirm Data of Buyer":
			try {

				if (pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData) && !conditionFlag_1 && !conditionFlag_2) {
					message = "Confirmed. Successfully wrote to the file.";

					theMiracleOfCreation(pdfObject, windowPanelBuyer, 2, 350, 700, 0, 0, false, changeData);

					conditionFlag_1 = false;
					conditionFlag_2 = true;
					confirmButtonBuyer.setEnabled(false);
					clearButtonBuyer.setEnabled(false);
				} else {
					if (!pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData)
							&& ((conditionFlag_1 && !conditionFlag_2) || conditionFlag_3)) {

						theMiracleOfCreation(pdfObject, windowPanelBuyer, 2, 350, 700, 0, 0, false, changeData);
						message = "Append data to the file.";
						confirmButtonBuyer.setEnabled(false);
						clearButtonBuyer.setEnabled(false);
						conditionFlag_1 = false;
						conditionFlag_2 = true;
						conditionFlag_3 = false;
					} else {
						if (!pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData)) {
							message = "Old file already exists. Cannot overwrite.";
							setButtonsOff();
						} else {
							message = "Error";
						}

						setButtonsOff();
					}
				}

			} catch (Exception e1) {

				e1.printStackTrace();
				message = "Error";

			}

///////////////////////////////////////////////just for now, gonna be deleted in the future////
			ifcheckStatusOfLabel_TEMP(infoLabelBuyer, windowPanelBuyer);
///////////////////////////////////////////////just for now, gonna be deleted in the future////		

			infoLabelBuyer = createJLabel(infoLabelBuyer, windowPanelBuyer, message);

			proceedFlag_2 = true;
			changeData = false;
			break;

		case "Clear Data of Seller":

			infoLabelSeller = clearData(infoLabelSeller, windowPanelSeller, confirmButtonSeller, clearButtonSeller,
					true);
			break;

		case "Clear Data of Buyer":

			infoLabelBuyer = clearData(infoLabelBuyer, windowPanelBuyer, confirmButtonBuyer, clearButtonBuyer, true);
			break;
		case "Delete the file":

			message = deleteFlagGUI(new PDFCreator(), false);
			infoLabelBuyer = deleteTheFile(infoLabelBuyer, windowPanelBuyer, clearButtonSeller, clearButtonBuyer,
					message);
			changeData = false;
			iteratorChangeData = 0;
			break;
		case "Close":
			windowFrame.dispose();
		default:

		}

		repaintFrame(windowFrame);

		nextWindow(proceedFlag_1, proceedFlag_2, changeData, iteratorChangeData, pdfObject, windowFrame);

	}

////////////very temp method - gonna be changed///////////////////
	protected void ifcheckStatusOfLabel_TEMP(JLabel infoLabel, JPanel windowPanel) {
		if (!checkStatusOfLabel(infoLabel, windowPanel)) {
			// System.out.println("CHECK INFO LABEL");
			// delete existing "delete" JLabel
			windowPanel.remove(infoLabel);

		}
	}
////////////////////////////////////////

	/**
	 * Next Window Decision.
	 * 
	 * @param windowFrame
	 * 
	 * @throws IOException
	 */

	void nextWindow(boolean flag, boolean flag1, boolean changeData2, int iteratorChangeData2, PDFCreator pdfObject2,
			JFrame windowFrame2) throws IOException {
		// boolean flaghelp = false;
		// 0 - yes
		// 1 - CHANGE DATA
		// 2 - clear all
		int proceed;

		// System.out.println("Flagi " + flag + " " + flag1);

		if (flag1 && flag && !pdfObject2.checkIfAFIleIsAlreadyExistingPDF(changeData2)) {
			// System.out.println(flag + " " + flag1);
			if (!changeData2 && iteratorChangeData2 < 1) {
				Object[] options = { "OK", "CHANGE DATA", "DELETE THE FILE" };

				// clearEverything();
				proceed = JOptionPane.showOptionDialog(windowFrame,
						"All data confirmed, pdf file has been created. Proceed?", "Warning",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				iteratorChangeData = iteratorChangeData + 1;
			} else {
				Object[] options2 = { "OK", "DELETE THE FILE" };

				// clearEverything();
				proceed = JOptionPane.showOptionDialog(windowFrame,
						"All data confirmed, pdf file has been updated. Proceed?", "Warning",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options2, options2[0]);
				setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

				if (proceed == 1) {
					proceed = 2;
				}
			}

			// System.out.println(proceed);

			switch (proceed) {
			case 0:
				// System.out.println("OK - zamkniecie " + proceed);
				windowFrame2.dispose();
				new GUI.Service(getVat());
				break;
			case 1:
				// System.out.println("CHANGE DATA " + proceed);
				// confirmButtonSeller.addActionListener(e -> DoTheThings_actionPerformed(e));
				// confirmButtonBuyer.addActionListener(e -> DoTheThings_actionPerformed(e));
				// deleteButton.addActionListener(e ->deleteFlagGUI(new saveTextFile(), false));
				// windowFrame.dispose();
				// init();
				// code block
				clearEverything();
				changeData = true;
				// new PDFCreator().changeData();
				break;
			case 2:
				// System.out.println("CLEAR ALL " + proceed);

				clearEverything();
				changeData = false;
				iteratorChangeData = 0;
				message = deleteFlagGUI(new PDFCreator(), false);
				infoLabelBuyer = deleteTheFile(infoLabelBuyer, windowPanelBuyer, clearButtonBuyer, clearButtonBuyer,
						message);
				// TODO
				// changeData = false;
				break;
			default:
				// code block
			}

			// flaghelp = true;
		} else {
			Object[] options = { "OK" };
			if (flag1 && flag && pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData)) {
				proceed = JOptionPane.showOptionDialog(windowFrame, "File dosen't exist!", "Warning",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				clearEverything();
				// windowFrame.dispose();
			}
		}
		// return flaghelp;
	}

	/**
	 * Creating specific Object [1]
	 */

	Client createClientObject(Client clientInInfos, JPanel windowPanel, int clientchoice) {
		// SellerClass sellerInInfos = new SellerClass();

		///////////////// iterate through Jpanel
		/// https://stackoverflow.com/questions/1037139/loop-through-jpanel
		int z = 0;
		int k = 0;
		clientInInfos.clearTreeMap();
		for (Component c : windowPanel.getComponents()) {

//			instanceof is a binary operator we use to test if an object is of a given type. The result of the operation is either true or false.
//			It's also known as a type comparison operator because it compares the instance with the type.
			if (c instanceof JTextField || c instanceof JComboBox) {

//				((JTextField) c).getText();
//				if (((JTextField) c).getText().equals("")) {
//					JOptionPane.showMessageDialog(null, "FILL UP ALL TEXT FIELDS " + z, "ALERT MESSAGE",
//							JOptionPane.WARNING_MESSAGE);
//					// clientInInfos=null;
//					break;
//				}
//				
				if (z % 2 != 0) {

					// add to HASHMAP Object
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

	/**
	 * Creating specific Object [2]
	 */

	protected void theMiracleOfCreation(PDFCreator object, JPanel windowPanel, int clientchoice, int x_pos, int y_pos,
			int x_Offset, int y_Offset, boolean addMoney, boolean changeData) {
		// just do the things
		// createClientObject(new Client(), windowPanel, decision).displayInInfos();
		createClientObject(new Client(), windowPanel, clientchoice);
		object.makeAFile(createClientObject(new Client(), windowPanel, clientchoice).getClientMap(), x_pos, y_pos,
				x_Offset, y_Offset, addMoney, changeData);

	}

	/**
	 * Getting and adding names of fields
	 */

	String clientObjectChoice(int i, int clientchoice) {

		String elementsOfListClientInfo = " ";

		elementsOfListClientInfo = new DataOfAll(clientchoice).elements(i);

		return elementsOfListClientInfo;
	}

	/**
	 * Getting and adding names of fields [2]
	 */
	protected void panelDisplay(JPanel windowPanel, int clientchoice) {

		for (int i = 0; i < new DataOfAll(clientchoice).findSize(); i++) {
			windowPanel.add(new JLabel(clientObjectChoice(i, clientchoice)));
			// System.out.println("VAT "+VATValue);
			if (clientchoice == 3) {
				
				String a = new JLabel(clientObjectChoice(i, clientchoice)).getText();	
				

				
				
				if (a == "07.VAT rate") {
					//System.out.println("VAT check " + this.VATValue);
					((JTextComponent) windowPanel.add(new JTextField(this.VATValue))).setEditable(false);
				}else if (a == "08.Net value"|| a ==  "09.VAT amount"|| a ==  "10.Gross value" ) {
				
					((JTextComponent) windowPanel.add(new JTextField(""))).setEditable(false);
				 
			} else	{
				windowPanel.add(new JTextField(""));
			}
			
			} 
			else {
				windowPanel.add(new JTextField(""));
			}

		}
	}

	/**
	 * Getting and adding names of fields [3]
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
			boolean b) {
		// System.out.println(" ");

		// delete existing "confirm" JLabel
		if (!checkStatusOfLabel(infoLabel, windowPanel)) {

			windowPanel.remove(infoLabel);
			clearOnlyPanel(windowPanel, infoLabel, confirmButton, clearButton, b);
		}

		// creating JLabel for the delete message
		infoLabel = createJLabel(infoLabel, windowPanel, "Cleared");
		confirmButton.setEnabled(true);
		clearButton.setEnabled(false);
		return infoLabel;

		// System.out.println("Clear Data of Buyer");
	}

	/**
	 * Clear only panel
	 */
	static void clearOnlyPanel(JPanel panelToClean, JLabel infoLabel, JButton BtnConfirm, JButton BtnClear,
			boolean choice) {

		// if the file already exists but the delete button was pressed first then skip
		// instruction
		// System.out.println(infoLabel.toString());
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

		clearOnlyPanel(windowPanelSeller, infoLabelSeller, confirmButtonSeller, clearButtonSeller, false);
		clearOnlyPanel(windowPanelBuyer, infoLabelBuyer, confirmButtonBuyer, clearButtonBuyer, false);

		setFlagsFalse();
		setButtonsOn();
		repaintFrame(windowFrame);
	}

	/**
	 * Setting buttons
	 */
	static void setFlagsFalse() {
		conditionFlag_1 = false;
		conditionFlag_2 = false;
		proceedFlag_1 = false;
		proceedFlag_2 = false;
	}

	static void setButtonsOn() {
		confirmButtonSeller.setEnabled(true);
		clearButtonSeller.setEnabled(true);
		confirmButtonBuyer.setEnabled(true);
		clearButtonBuyer.setEnabled(true);
	}

	static void setButtonsOff() {
		confirmButtonSeller.setEnabled(false);
		clearButtonSeller.setEnabled(false);
		confirmButtonBuyer.setEnabled(false);
		clearButtonBuyer.setEnabled(false);
	}

	/**
	 * Call to delete
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
	 * Additional file operations
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
	 * Internal test method
	 */
	boolean checkStatusOfLabel(JLabel infoLabel, JPanel windowPanel) {
		boolean flaghelp;
		if (infoLabel != null) {
			// System.out.println("Confirmed NOT NULL");
			flaghelp = false;
		} else {
			// System.out.println("Confirmed IS NULL");
			flaghelp = true;

		}
		return flaghelp;
	}

	/**
	 * Getting Vat value
	 */
	protected String getVat() {
		// System.out.println("getVAt" + VATValue);
		return this.VATValue;
	}

}
