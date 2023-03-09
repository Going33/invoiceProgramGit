package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import Client.Client;
import Client.dataOfAll;
import Data.saveTextFile;

public class windowApp extends JFrame implements ActionListener {

	/**
	 * Frame Variable static - needed to share exactly the same variable between
	 * parent and child (not a copy of var)
	 */
	//
	 private static JFrame windowFrame;
	 private static JPanel windowPanelTop;
	 private static JPanel windowPanelBottom;
	 private  JSplitPane windowSplitPanelMainWindow;
	 private int widthWindowMain = 600;
	 private int heightWindowMain = 600;
	 private int rowTop = 19;
	 private  int collTop = 1;
	 private int rowBottom = 12;
	 private int collBottom = 1;
	 private static JButton confirmButtonTop;
	 private static JButton clearButtonTop;
	 private static JButton confirmButtonBottom;
	 private static JButton clearButtonBottom;
	 private static JButton deleteButton;

	 private static JLabel confirmedOrErrorTop;
	 private static JLabel confirmedOrErrorBottom;
	 private String message = "";

	/**
	 * Auxiliary booleans
	 */
	//
	 private static boolean conditionFlag_1 = false;
	 private static boolean conditionFlag_2 = false;

	 private static boolean proceedFlag_1 = false;
	 private static boolean proceedFlag_2 = false;

	 private boolean doNotListen = false;

	/**
	 * Creating objects.
	 */
	 //	dataOfAll clientInfo = new dataOfAll(0);
	// dataOfBuyer buyerInfo = new dataOfBuyer();
	 saveTextFile testSave = new saveTextFile();

	/**
	 * Create the application.
	 */

	public windowApp() {

		init();

	}
	public windowApp(int i) {

			}
public windowApp(String test)
{
	System.out.println(test);
}
	/**
	 * Initialize the contents of the frame.
	 */

	protected void init() {

		// GUI Window
		windowFrame = new JFrame();

		windowFrame.setTitle("Invoice Program [1]");
		windowFrame.setPreferredSize(new Dimension(widthWindowMain, heightWindowMain));
		windowFrame.setLocation(500, 100);
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowFrame.pack();
		windowFrame.setResizable(false);
		windowFrame.setVisible(true);

		////////////////////////////////////////////////////////////////////////////

		////////////////// TOP PANEL////////////////
		// row, coll

		windowPanelTop = new JPanel(new GridLayout(rowTop, collTop, 0, 0));
		windowPanelTop.setBackground(Color.WHITE);

		PanelDisplaying(windowPanelTop, 1);

		confirmButtonTop = new JButton("Confirm Data of Seller");
		clearButtonTop = new JButton("Clear Data of Seller");

		confirmButtonTop.addActionListener(this);
		windowPanelTop.add(confirmButtonTop);
		clearButtonTop.addActionListener(this);
		windowPanelTop.add(clearButtonTop);
		clearButtonTop.setEnabled(false);

		////////////////// TOP PANEL////////////////

		////////////////////////////////////////////////////////////////////////////

		////////////////// BOTTOM PANEL////////////////
		windowPanelBottom = new JPanel(new GridLayout(rowBottom, collBottom, 0, 0));
		windowPanelBottom.setBackground(Color.GRAY);

		// PanelDisplayingBottom();
		PanelDisplaying(windowPanelBottom, 2);

		confirmButtonBottom = new JButton("Confirm Data of Buyer");
		clearButtonBottom = new JButton("Clear Data of Buyer");
		deleteButton = new JButton("Delete the file");
//		
//		

		confirmButtonBottom.addActionListener(this);
		windowPanelBottom.add(confirmButtonBottom);

		clearButtonBottom.addActionListener(this);
		windowPanelBottom.add(clearButtonBottom);
		clearButtonBottom.setEnabled(false);

		deleteButton.addActionListener(this);
		windowPanelBottom.add(deleteButton);

		////////////////// BOTTOM PANEL////////////////

		////////////////////////////////////////////////////////////////////////////

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

		// to refresh colors
		RepaintFrame();

	}

	/**
	 * Initialize the actions.
	 */

	public void actionPerformed(ActionEvent e) {
		System.out.println("Slucha ? " + doNotListen);

		if (doNotListen) {
			// System.out.println("Slucha ? " + doNotListen);
		} else {
			System.out.println("Slucha ? " + doNotListen);
			DoTheThings_actionPerformed(e);

		}

	}

public	 void DoTheThings_actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {

		case "Confirm Data of Seller":

			try {
				// TODO TO OPTIMIZE!!!!!!!!!!!!!
				
				
				if (testSave.checkIfAFIleIsAlreadyExistingPDF() && !conditionFlag_1 && !conditionFlag_2) {
					// System.out.println("Boolean " + testSave.checkIfAFIleIsAlreadyExistingPDF());

					// creating seller's object and displaying informations in the console
					TheMiracleOfCreation(testSave, windowPanelTop, 1);

					message = "Confirmed. Successfully wrote to the file.";
					conditionFlag_1 = true;
					conditionFlag_2 = false;
					confirmButtonTop.setEnabled(false);
					clearButtonTop.setEnabled(true);

				} else {

					if (!testSave.checkIfAFIleIsAlreadyExistingPDF() && !conditionFlag_1 && conditionFlag_2) {

						TheMiracleOfCreation(testSave, windowPanelTop, 1);
						message = "File already exists. Append new data to existing file.";
						confirmButtonTop.setEnabled(false);
						clearButtonTop.setEnabled(true);
						conditionFlag_2=false;
					} else {
						message = "Old file already exists. Cannot overwrite.";
						SetButtonsOff();
					}
				}

///////////////////////////////////////////////
			} catch (Exception e1) {
				// ClearOnlyPanel();
				e1.printStackTrace();
				message = "Error";

			}
			if (!CheckStatusOfLabel(confirmedOrErrorTop, windowPanelTop)) {

				// delete existing "delete" JLabel
				windowPanelTop.remove(confirmedOrErrorTop);

			}
			// creating JLabel for the confirm message
			confirmedOrErrorTop = CreateJLabel(confirmedOrErrorTop, windowPanelTop, message);

			proceedFlag_1 = true;
			System.out.println("proceedFlag_1 " + proceedFlag_1);
			// proceedFlag_1 =JustForNiceLook(confirmedOrErrorTop, windowPanelTop, message);
			break;

		case "Confirm Data of Buyer":
			System.out.println(" ");
			try {

				// TODO TO OPTIMIZE!!!!!!!!!!!!!

				if (testSave.checkIfAFIleIsAlreadyExistingPDF() && !conditionFlag_1 && !conditionFlag_2) {
					// System.out.println("Boolean " + testSave.checkIfAFIleIsAlreadyExistingPDF());
					message = "Confirmed. Successfully wrote to the file.";

					// creating seller's object and displaying informations in the console
					TheMiracleOfCreation(testSave, windowPanelBottom, 2);
					conditionFlag_1 = false;
					conditionFlag_2 = true;
					confirmButtonBottom.setEnabled(false);
					clearButtonBottom.setEnabled(true);
				} else {
					// System.out.println("Boolean " + testSave.checkIfAFIleIsAlreadyExistingPDF());
					if (!testSave.checkIfAFIleIsAlreadyExistingPDF() && conditionFlag_1 && !conditionFlag_2) {

						TheMiracleOfCreation(testSave, windowPanelBottom, 2);
						message = "File already exists. Append new data to existing file.";
						confirmButtonBottom.setEnabled(false);
						clearButtonBottom.setEnabled(true);
						conditionFlag_1=false;
					} else {
						message = "Old file already exists. Cannot overwrite.";
						SetButtonsOff();
					}
				}

			} catch (Exception e1) {
		
				e1.printStackTrace();
				message = "Error";

			}

			if (!CheckStatusOfLabel(confirmedOrErrorBottom, windowPanelBottom)) {

				// delete existing "delete" JLabel
				windowPanelBottom.remove(confirmedOrErrorBottom);

			}
			// creating JLabel for the confirm message
			confirmedOrErrorBottom = CreateJLabel(confirmedOrErrorBottom, windowPanelBottom, message);

			// System.out.println("Data of Buyer Confirmed");
			proceedFlag_2 = true;
			System.out.println("proceedFlag_2 " + proceedFlag_2);
			break;

		case "Clear Data of Seller":

			System.out.println(" ");

			// delete existing "confirm" JLabel
			if (!CheckStatusOfLabel(confirmedOrErrorTop, windowPanelTop)) {

				windowPanelTop.remove(confirmedOrErrorTop);
				ClearOnlyPanel(windowPanelTop, confirmedOrErrorTop, confirmButtonTop, clearButtonTop, true);
			}

			// creating JLabel for the delete message
			confirmedOrErrorTop = CreateJLabel(confirmedOrErrorTop, windowPanelTop, "Cleared");
			// SetButtonsOn();
			confirmButtonTop.setEnabled(true);
			clearButtonTop.setEnabled(false);
			// System.out.println("Clear Data of Seller");
			break;

		case "Clear Data of Buyer":
			// delete existing "confirm" JLabel
			if (!CheckStatusOfLabel(confirmedOrErrorBottom, windowPanelBottom)) {

				windowPanelBottom.remove(confirmedOrErrorBottom);
				ClearOnlyPanel(windowPanelBottom, confirmedOrErrorBottom, confirmButtonBottom, clearButtonBottom, true);
			}

			// creating JLabel for the delete message
			confirmedOrErrorBottom = CreateJLabel(confirmedOrErrorBottom, windowPanelBottom, "Cleared");
			confirmButtonBottom.setEnabled(true);
			clearButtonBottom.setEnabled(false);
			// System.out.println("Clear Data of Buyer");
			break;
		case "Delete the file":

			message = DeleteFlagGUI(new saveTextFile(), false);
			confirmedOrErrorBottom = CreateJLabel(confirmedOrErrorBottom, windowPanelBottom, message);
			clearButtonBottom.setEnabled(true);
			clearButtonTop.setEnabled(true);
			break;
		default:
			// System.out.println("default");
		}

		RepaintFrame();
		System.out.println("Flagi " + proceedFlag_1 + " " + proceedFlag_2);
		NextWindow(proceedFlag_1, proceedFlag_2);

	}

	/**
	 * Next Window Decision.
	 */

	 boolean NextWindow(boolean flag, boolean flag1) {
		boolean flaghelp = false;
		// 0 - yes
		// 1 - CHANGE DATA
		// 2 - clear all
		int proceed;
		System.out.println("Flagi " + flag + " " + flag1);

		Object[] options = { "OK", "CHANGE DATA", "CLEAR ALL AND DELETE THE FILE" };

		if (flag1 && flag) {
			System.out.println(flag + " " + flag1);
			ClearEverything();
			proceed = JOptionPane.showOptionDialog(windowFrame,
					"All data confirmed, pdf file has been created. Proceed?", "Warning", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

			System.out.println(proceed);

			switch (proceed) {
			case 0:
				System.out.println("OK -  zamkniecie " + proceed);
				//ClearEverything();
				windowFrame.dispose();
			
				//	new GUI.Service(widthWindowMain, heightWindowMain, rowTop, collTop, rowBottom, collBottom, hgap, vgap);
			new GUI.Service(0);
				break;
			case 1:
				System.out.println("CHANGE DATA " + proceed);
			//	ClearEverything();
				doNotListen = true;
				// confirmButtonTop.addActionListener(e -> DoTheThings_actionPerformed(e));
				// confirmButtonBottom.addActionListener(e -> DoTheThings_actionPerformed(e));
				// deleteButton.addActionListener(e ->DeleteFlagGUI(new saveTextFile(), false));
				// windowFrame.dispose();
				// init();
				// code block
				break;
			case 2:
				System.out.println("CLEAR ALL " + proceed);

				//ClearEverything();
				DeleteFlagGUI(new saveTextFile(), false);
				break;
			default:
				// code block
			}

			flaghelp = true;
		} else {
			System.out.println(flag + " " + flag1);
			flaghelp = false;
		}
		return flaghelp;
	}

	/**
	 * Creating specific Object - Client (Seller or Buyer)
	 */

	 Client CreateClientObject(Client clientInInfos, JPanel windowPanel, int clientchoice) {
		// SellerClass sellerInInfos = new SellerClass();

		///////////////// iterate through Jpanel
		/// https://stackoverflow.com/questions/1037139/loop-through-jpanel
		int z = 0;
		int k = 0;
		clientInInfos.clearTreeMap();
		for (Component c : windowPanel.getComponents()) {

			if (c instanceof JTextField) {
				((JTextField) c).getText();

				if (z % 2 != 0) {
					// add to HASHMAP Object
					clientInInfos.putInInfos(ClientObjectChoice(k, clientchoice).toString(),
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

	 void TheMiracleOfCreation(saveTextFile object, JPanel windowPanel, int clientchoice) {
		// just do the things
		// CreateClientObject(new Client(), windowPanel, decision).displayInInfos();
		CreateClientObject(new Client(), windowPanel, clientchoice);
		object.makeAFile(CreateClientObject(new Client(), windowPanel, clientchoice).getClientMap());

	}

	/**
	 * Getting and adding names of fields
	 */

	 String ClientObjectChoice(int i, int clientchoice) {

		String elementsOfListClientInfo = " ";
	
			elementsOfListClientInfo =  new dataOfAll(clientchoice).elements(i);

		return elementsOfListClientInfo;
	}

	/**
	 * Getting and adding names of fields [2]
	 */
	protected void PanelDisplaying(JPanel windowPanel,int clientchoice) {
		
		for (int i = 0; i < new dataOfAll(clientchoice).findSize(); i++) {
			windowPanel.add(new JLabel(ClientObjectChoice(i, clientchoice)));
			windowPanel.add(new JTextField(""));
			
		}
	}

	/**
	 * Getting and adding names of fields [3]
	 */

	 JLabel CreateJLabel(JLabel confirmedOrError, JPanel windowPanel, String message) {
		confirmedOrError = new JLabel();
		windowPanel.add(confirmedOrError);
		confirmedOrError.setText(message);
		return confirmedOrError;
	}

	/**
	 * Repaiting the Frame
	 */

	 static void RepaintFrame() {
		windowFrame.invalidate();
		windowFrame.validate();
		windowFrame.repaint();
	}

	/**
	 * Clear only panel
	 */
	 static void ClearOnlyPanel(JPanel panelToClean, JLabel confirmedOrError, JButton BtnConfirm,
			JButton BtnClear, boolean choice) {

		// confirmedOrError.setText(" ");
		// confirmedOrError =null;

		// if the file already exists but the delete button was pressed first then skip
		// instruction
		if (confirmedOrError != null) {

			panelToClean.remove(confirmedOrError);
		}

		for (int i = 0; i <= 30; i++) {
			System.out.println(" ");
		}

		if (choice) {
			for (Component c : panelToClean.getComponents()) {
				if (c instanceof JTextField) {
					((JTextField) c).setText("");
				}
			}
		}
		// ClearEverything();
		//SetFlagsFalse();
		RepaintFrame();
		BtnConfirm.setEnabled(true);
		BtnClear.setEnabled(false);

	}

	/**
	 * Clear everything
	 */
	// temporary _ to change
	 static void ClearEverything() {
		ClearOnlyPanel(windowPanelTop, confirmedOrErrorTop, confirmButtonTop, clearButtonTop, false);
		ClearOnlyPanel(windowPanelBottom, confirmedOrErrorBottom, confirmButtonBottom, clearButtonBottom, false);

		SetFlagsFalse();
		SetButtonsOn();
		RepaintFrame();
	}

	/**
	 * Setting buttons
	 */
	static void SetFlagsFalse() {
		conditionFlag_1 = false;
		conditionFlag_2 = false;
		proceedFlag_1 = false;
		proceedFlag_2 = false;
	}

	static void SetButtonsOn() {
		confirmButtonTop.setEnabled(true);
		clearButtonTop.setEnabled(false);
		confirmButtonBottom.setEnabled(true);
		clearButtonBottom.setEnabled(false);
	}

	static void SetButtonsOff() {
		confirmButtonTop.setEnabled(false);
		clearButtonTop.setEnabled(false);
		confirmButtonBottom.setEnabled(false);
		clearButtonBottom.setEnabled(false);
	}

	/**
	 * Additional file operations
	 */
	 String DeleteFlagGUI(saveTextFile deleteFile, boolean flagAppWindow) {
		ClearEverything();
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
	 boolean CheckStatusOfLabel(JLabel confirmedOrError, JPanel windowPanel) {
		boolean flaghelp;
		if (confirmedOrError != null) {
			// System.out.println("Confirmed NOT NULL");
			flaghelp = false;
		} else {

			// System.out.println("Confirmed IS NULL");
			flaghelp = true;

		}
		return flaghelp;
	}

}
