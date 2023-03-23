package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import Client.Client;
import Client.DataOfAll;
import Data.PDFCreator;

public class WindowApp extends JFrame implements ActionListener {

	/**
	 * Frame Variable static - needed to share exactly the same variable between
	 * parent and child (not a copy of var)
	 */
	//
	private static JFrame windowFrame;
	private static JPanel windowPanelTop;
	private static JPanel windowPanelBottom;
	private JSplitPane windowSplitPanelMainWindow;
	private int widthWindowMain = 600;
	private int heightWindowMain = 600;
	private int rowTop = 2 * (new DataOfAll(1).findSize()) + 3;
	private int collTop = 1;
	private int rowBottom = 2 * (new DataOfAll(2).findSize()) + 4;
	private int collBottom = 1;
	private static JButton confirmButtonTop;
	private static JButton clearButtonTop;
	private static JButton confirmButtonBottom;
	private static JButton clearButtonBottom;
	private static JButton deleteButton;

	private static JLabel infoLabelTop;
	private static JLabel infoLabelBottom;
	private String message = "";

	/**
	 * Auxiliary booleans
	 */
	//
	private static boolean conditionFlag_1 = false;
	private static boolean conditionFlag_2 = false;

	private static boolean proceedFlag_1 = false;
	private static boolean proceedFlag_2 = false;

	private static boolean changeData = false;
	private static int iteratorChangeData = 0;

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
	public WindowApp(int i) {

	}

	/**
	 * Initialize the contents of the frame.
	 */

	protected void init() {

		windowFrame = new JFrame();

		windowFrame.setTitle("Invoice Program [1]");
		windowFrame.setPreferredSize(new Dimension(widthWindowMain, heightWindowMain));
		windowFrame.setLocation(500, 100);
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowFrame.pack();
		windowFrame.setResizable(false);
		windowFrame.setVisible(true);

		/**
		 * TOP PANEL.
		 */

		windowPanelTop = new JPanel(new GridLayout(rowTop, collTop, 0, 0));
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

		windowPanelBottom = new JPanel(new GridLayout(rowBottom, collBottom, 0, 0));
		windowPanelBottom.setBackground(Color.GRAY);

		panelDisplay(windowPanelBottom, 2);

		confirmButtonBottom = new JButton("Confirm Data of Buyer");
		clearButtonBottom = new JButton("Clear Data of Buyer");
		deleteButton = new JButton("Delete the file");

		confirmButtonBottom.addActionListener(this);
		windowPanelBottom.add(confirmButtonBottom);

		clearButtonBottom.addActionListener(this);
		windowPanelBottom.add(clearButtonBottom);
		clearButtonBottom.setEnabled(false);

		deleteButton.addActionListener(this);
		windowPanelBottom.add(deleteButton);

		windowPanelBottom.setVisible(true);
		windowPanelTop.setVisible(true);

		windowSplitPanelMainWindow = new JSplitPane();
		windowSplitPanelMainWindow.setPreferredSize(windowFrame.getSize());
		windowSplitPanelMainWindow.setDividerSize(0);
		windowSplitPanelMainWindow.setDividerLocation((windowFrame.getSize().width) / 2);
		windowSplitPanelMainWindow.setOrientation(JSplitPane.VERTICAL_SPLIT);
		windowSplitPanelMainWindow.setBottomComponent(windowPanelBottom);
		windowSplitPanelMainWindow.setTopComponent(windowPanelTop);
		windowSplitPanelMainWindow.setVisible(true);

		windowFrame.add(windowSplitPanelMainWindow);

		repaintFrame();

	}

	/**
	 * Initialize the actions.
	 */

	public void actionPerformed(ActionEvent e) {

		try {
			doTheThings_actionPerformed(e);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void doTheThings_actionPerformed(ActionEvent e) throws IOException {
		switch (e.getActionCommand()) {

		case "Confirm Data of Seller":

			try {
				// TODO TO OPTIMIZE!!!!!!!!!!!!!
				System.out.println("pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData) && !conditionFlag_1 && !conditionFlag_2 "+pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData) + conditionFlag_1 + conditionFlag_2);
				if (pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData) && !conditionFlag_1 && !conditionFlag_2) {

					// System.out.println("1.JESTEM TUTAJ "
					// +pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData) );
;
					theMiracleOfCreation(pdfObject, windowPanelTop, 1, 5, 750, 530, 30, false, changeData, 1);
					message = "Confirmed. Successfully wrote to the file.";
					conditionFlag_1 = true;
					conditionFlag_2 = false;
					confirmButtonTop.setEnabled(false);
					clearButtonTop.setEnabled(true);

				} else {

					if (!pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData) && !conditionFlag_1
							&& conditionFlag_2) {

						// System.out.println("2.JESTEM TUTAJ "
						// +pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData) );
						theMiracleOfCreation(pdfObject, windowPanelTop, 1, 5, 750, 530, 30, false, changeData, 1);
						message = "Append data to the file.";
						confirmButtonTop.setEnabled(false);
						clearButtonTop.setEnabled(true);
						conditionFlag_2 = false;
					} else {
						if(!pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData))
						{
							message = "Old file already exists. Cannot overwrite.";
						}else {
							message = "Error";
						}
					}
				}

			} catch (Exception e1) {
				e1.printStackTrace();
				message = "Error";

			}

			
///////////////////////////////////////////////just for now, gonna be delete in future////
			ifcheckStatusOfLabel_TEMP(infoLabelTop, windowPanelTop);
///////////////////////////////////////////////just for now, gonna be delete in future////

			infoLabelTop = createJLabel(infoLabelTop, windowPanelTop, message);

			proceedFlag_1 = true;
			changeData = false;
			break;

		case "Confirm Data of Buyer":
			try {

				if (pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData) && !conditionFlag_1 && !conditionFlag_2) {
					message = "Confirmed. Successfully wrote to the file.";

					theMiracleOfCreation(pdfObject, windowPanelBottom, 2, 350, 750, 0, 0, false, changeData, 2);

					conditionFlag_1 = false;
					conditionFlag_2 = true;
					confirmButtonBottom.setEnabled(false);
					clearButtonBottom.setEnabled(true);
				} else {
					if (!pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData) && conditionFlag_1
							&& !conditionFlag_2) {

						theMiracleOfCreation(pdfObject, windowPanelBottom, 2, 350, 750, 0, 0, false, changeData, 2);
						message = "Append data to the file.";
						confirmButtonBottom.setEnabled(false);
						clearButtonBottom.setEnabled(true);
						conditionFlag_1 = false;
					} else {
						if(!pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData))
						{
							message = "Old file already exists. Cannot overwrite.";
						}else {
							message = "Error";
						}
						
						setButtonsOff();
					}
				}

			} catch (Exception e1) {

				e1.printStackTrace();
				message = "Error";

			}

///////////////////////////////////////////////just for now, gonna be delete in future////
			ifcheckStatusOfLabel_TEMP(infoLabelBottom, windowPanelBottom);
///////////////////////////////////////////////just for now, gonna be delete in future////		

			infoLabelBottom = createJLabel(infoLabelBottom, windowPanelBottom, message);

			proceedFlag_2 = true;
			changeData = false;
			break;

		case "Clear Data of Seller":

			infoLabelTop = clearData(infoLabelTop, windowPanelTop, confirmButtonTop, clearButtonTop, true);
			break;

		case "Clear Data of Buyer":

			infoLabelBottom = clearData(infoLabelBottom, windowPanelBottom, confirmButtonBottom, clearButtonBottom,
					true);
			break;
		case "Delete the file":

			message = deleteFlagGUI(new PDFCreator(), false);
			infoLabelBottom = deleteTheFile(infoLabelBottom, windowPanelBottom, clearButtonBottom, clearButtonBottom,
					message);
			changeData = false;
			iteratorChangeData = 0;
			break;
		default:

		}

		repaintFrame();
		
		nextWindow(proceedFlag_1, proceedFlag_2,changeData,iteratorChangeData,pdfObject,windowFrame);

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
	 * @param windowFrame 
	 * 
	 * @throws IOException
	 */

	void nextWindow(boolean flag, boolean flag1,boolean changeData2, int iteratorChangeData2, PDFCreator pdfObject2, JFrame windowFrame2 ) throws IOException {
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
				new GUI.Service(0);
				break;
			case 1:
				// System.out.println("CHANGE DATA " + proceed);
				// confirmButtonTop.addActionListener(e -> DoTheThings_actionPerformed(e));
				// confirmButtonBottom.addActionListener(e -> DoTheThings_actionPerformed(e));
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
				infoLabelBottom = deleteTheFile(infoLabelBottom, windowPanelBottom, clearButtonBottom,
						clearButtonBottom, message);
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
			//	windowFrame.dispose();
			}
		}
		// return flaghelp;
	}

	/**
	 * Creating specific Object - Client (Seller or Buyer)
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
			if (c instanceof JTextField) {

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
					clientInInfos.fillUpTheMap(clientObjectChoice(k, clientchoice).toString(),
							((JTextField) c).getText());
					k = k + 1;
				}

			}
			z++;

		}

		return clientInInfos;
	}

	/**
	 * Creating specific Object - Client (Seller or Buyer) [2]
	 */

	protected void theMiracleOfCreation(PDFCreator object, JPanel windowPanel, int clientchoice, int x_pos, int y_pos,
			int x_Offset, int y_Offset, boolean addMoney, boolean changeData, int i) {
		// just do the things
		// createClientObject(new Client(), windowPanel, decision).displayInInfos();
		createClientObject(new Client(), windowPanel, clientchoice);
		object.makeAFile(createClientObject(new Client(), windowPanel, clientchoice).getClientMap(), x_pos, y_pos,
				x_Offset, y_Offset, addMoney, changeData, i);

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
			windowPanel.add(new JTextField(""));

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

	static void repaintFrame() {
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

		// infoLabel.setText(" ");
		// infoLabel =null;

		// if the file already exists but the delete button was pressed first then skip
		// instruction
		// System.out.println(infoLabel.toString());
		if (infoLabel != null) {

			panelToClean.remove(infoLabel);
		}

		for (int i = 0; i <= 30; i++) {
			// System.out.println(" ");
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
		repaintFrame();
		BtnConfirm.setEnabled(true);
		BtnClear.setEnabled(false);

	}

	/**
	 * Clear everything
	 */
	static void clearEverything() {

		clearOnlyPanel(windowPanelTop, infoLabelTop, confirmButtonTop, clearButtonTop, false);
		clearOnlyPanel(windowPanelBottom, infoLabelBottom, confirmButtonBottom, clearButtonBottom, false);

		setFlagsFalse();
		setButtonsOn();
		repaintFrame();
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
		confirmButtonTop.setEnabled(true);
		clearButtonTop.setEnabled(true);
		confirmButtonBottom.setEnabled(true);
		clearButtonBottom.setEnabled(true);
	}

	static void setButtonsOff() {
		confirmButtonTop.setEnabled(false);
		clearButtonTop.setEnabled(false);
		confirmButtonBottom.setEnabled(false);
		clearButtonBottom.setEnabled(false);
	}

	/**
	 * Call to delete
	 */
	protected JLabel deleteTheFile(JLabel infoLabel, JPanel windowPanel, JButton clearButton, JButton clearButton2,
			String message) {

		if (infoLabel == null) {
			infoLabel = createJLabel(infoLabel, windowPanel, message);
			// System.out.println("Text " + infoLabel.getText());
			// System.out.println(infoLabel.toString());
		} else {
			// System.out.println("Text " + infoLabel.getText());
			// System.out.println(infoLabel.toString());
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

}
