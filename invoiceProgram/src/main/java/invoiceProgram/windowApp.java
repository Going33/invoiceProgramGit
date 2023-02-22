package invoiceProgram;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class windowApp extends JFrame implements ActionListener {

	private static JFrame windowFrame;
	private static JPanel windowPanelTop;
	private JPanel windowPanelBottom;
	private JSplitPane windowSplitPanelMainWindow;
	private final int widthWindowMain = 600;
	private final int heightWindowMain = 600;
	private final int rowTop = 19;
	private final int collTop = 1;
	private final int rowBottom = 11;
	private final int collBottom = 1;
	int hgap = 0;
	int vgap = 0;
	private JButton confirmButtonTop;
	private JButton clearButtonTop;
	private JButton confirmButtonBottom;
	private JButton clearButtonBottom;
	private static JLabel confirmedOrErrorTop;
	private static JLabel confirmedOrErrorBottom;
	private static String message = "";
	private static boolean overwriteFlag_1 = false;
	private static boolean overwriteFlag_2 = false;
	Object newBuyerObjectWindowAppClass;
	Object newSellerObjectWindowAppClass;
	// creating objects of "static" data
	dataOfSeller sellerInfo = new dataOfSeller();
	dataOfBuyer buyerInfo = new dataOfBuyer();

	saveTextFile testZapis = new saveTextFile();

	windowApp(Object newBuyerObject, Object newSellerObject) {

		// Construtors for external objects
		this.newBuyerObjectWindowAppClass = newBuyerObject;
		this.newSellerObjectWindowAppClass = newSellerObject;

		// GUI Window
		windowFrame = new JFrame();

		windowFrame.setTitle("Test");
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

		// addInfosToThePanelTop();
		addInfosToThePanel(windowPanelTop, sellerInfo.size(), true);

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

		// addInfosToThePanelBottom();
		addInfosToThePanel(windowPanelBottom, buyerInfo.size(), false);

		confirmButtonBottom = new JButton("Confirm Data of Buyer");
		clearButtonBottom = new JButton("Clear Data of Buyer");

		confirmButtonBottom.addActionListener(this);
		windowPanelBottom.add(confirmButtonBottom);
		clearButtonBottom.addActionListener(this);
		windowPanelBottom.add(clearButtonBottom);
		clearButtonBottom.setEnabled(false);

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
		repaintFrame();

	}
	///////////////////////////////// Click Execution///////////////

	public void actionPerformed(ActionEvent e) {

		// Object source = e.getSource();
		// to optimize !
		switch (e.getActionCommand()) {

		case "Confirm Data of Seller":

			try {

///////////////////////////////////////////////TO_OPTIMIZE////////////////////////
				//// write to txt file
				saveTextFile testSave = new saveTextFile();

				if (testSave.checkIfAFIleIsAlreadyExisting() && !overwriteFlag_1 && !overwriteFlag_2) {
					System.out.println("Boolean " + testSave.checkIfAFIleIsAlreadyExisting());
					message = "Confirmed. Successfully wrote to the file.";

					// creating seller's object and displaying informations in the console
					// v4.0 true as an argument - seller
					createAnInputClientObject(new Client(), windowPanelTop, true).displayInInfos();
					testSave.makeAFile(createAnInputClientObject(new Client(), windowPanelTop, true).getClientMap());
					overwriteFlag_1 = true;
					overwriteFlag_2 = false;
				} else {

					if (!testSave.checkIfAFIleIsAlreadyExisting() && !overwriteFlag_1 && overwriteFlag_2) {
						createAnInputClientObject(new Client(), windowPanelTop, true).displayInInfos();
						testSave.makeAFile(
								createAnInputClientObject(new Client(), windowPanelTop, true).getClientMap());
						message = "File already exists. Append new data to existing file.";
					} else {
						message = "Old file already exists. Cannot create a new one.";
						confirmButtonTop.setEnabled(true);
						clearButtonTop.setEnabled(false);
					}
				}

				confirmButtonTop.setEnabled(false);
				clearButtonTop.setEnabled(true);

///////////////////////////////////////////////TO_OPTIMIZE////////////////////////
			} catch (Exception e1) {
				// cleanUp();
				e1.printStackTrace();
				message = "Error";

			}

			// checking if JLabel is null,needed to avoid "trash-old" message
			if (!checkIfJLabelConfirmedOrErrorIsNull(confirmedOrErrorTop, windowPanelTop)) {

				// delete existing "delete" JLabel
				windowPanelTop.remove(confirmedOrErrorTop);
			}
			// creating JLabel for the confirm message
			confirmedOrErrorTop = doTheJLabel(confirmedOrErrorTop, windowPanelTop, message);

			System.out.println("Data of Seller Confirmed");
			break;

		case "Confirm Data of Buyer":
			System.out.println(" ");
			try {

/////////////////////////////////////////////// TO_OPTIMIZE////////////////////////
				//// write to txt file
				saveTextFile testSave = new saveTextFile();

				if (testSave.checkIfAFIleIsAlreadyExisting() && !overwriteFlag_1 && !overwriteFlag_2) {
					System.out.println("Boolean " + testSave.checkIfAFIleIsAlreadyExisting());
					message = "Confirmed. Successfully wrote to the file.";

					// creating seller's object and displaying informations in the console
					// v4.0 false as an argument - buyer
					createAnInputClientObject(new Client(), windowPanelBottom, false).displayInInfos();
					testSave.makeAFile(
							createAnInputClientObject(new Client(), windowPanelBottom, false).getClientMap());
					overwriteFlag_1 = false;
					overwriteFlag_2 = true;
				} else {
					System.out.println("Boolean " + testSave.checkIfAFIleIsAlreadyExisting());
					if (!testSave.checkIfAFIleIsAlreadyExisting() && overwriteFlag_1 && !overwriteFlag_2) {
						createAnInputClientObject(new Client(), windowPanelBottom, false).displayInInfos();
						testSave.makeAFile(
								createAnInputClientObject(new Client(), windowPanelBottom, false).getClientMap());

						message = "File already exists. Append new data to existing file.";

					} else {
						message = "Old file already exists. Cannot create a new one.";
						confirmButtonBottom.setEnabled(true);
						clearButtonBottom.setEnabled(false);
					}
				}

				confirmButtonBottom.setEnabled(false);
				clearButtonBottom.setEnabled(true);

				/////////////////////////////////////////////// TO_OPTIMIZE////////////////////////

			} catch (Exception e1) {
				// cleanUp();
				e1.printStackTrace();
				message = "Error";

			}

			// checking if JLabel is null,needed to avoid "trash-old" message
			if (!checkIfJLabelConfirmedOrErrorIsNull(confirmedOrErrorBottom, windowPanelBottom)) {

				// delete existing "delete" JLabel
				windowPanelBottom.remove(confirmedOrErrorBottom);

			}
			// creating JLabel for the confirm message
			confirmedOrErrorBottom = doTheJLabel(confirmedOrErrorBottom, windowPanelBottom, message);

			System.out.println("Data of Buyer Confirmed");
			break;

		case "Clear Data of Seller":

			System.out.println(" ");

			// delete existing "confirm" JLabel
			if (!checkIfJLabelConfirmedOrErrorIsNull(confirmedOrErrorTop, windowPanelTop)) {

				windowPanelTop.remove(confirmedOrErrorTop);
				cleanUp(windowPanelTop, confirmedOrErrorTop, confirmButtonTop, clearButtonTop);
			}

			// creating JLabel for the delete message
			confirmedOrErrorTop = doTheJLabel(confirmedOrErrorTop, windowPanelTop,
					deleteExistingFileGUI(new saveTextFile(), false));

			System.out.println("Clear Data of Seller");
			break;

		case "Clear Data of Buyer":
			// delete existing "confirm" JLabel
			if (!checkIfJLabelConfirmedOrErrorIsNull(confirmedOrErrorBottom, windowPanelBottom)) {

				windowPanelBottom.remove(confirmedOrErrorBottom);
				cleanUp(windowPanelBottom, confirmedOrErrorBottom, confirmButtonBottom, clearButtonBottom);
			}

			// creating JLabel for the delete message
			confirmedOrErrorBottom = doTheJLabel(confirmedOrErrorBottom, windowPanelBottom,
					deleteExistingFileGUI(new saveTextFile(), false));
			System.out.println("Clear Data of Buyer");
			break;

		default:
			System.out.println("default");
		}

		repaintFrame();

	}

	//////////////////////////////// Objects//////////////////////////////////

////////creating specific Object - Client (Seller or Buyer) ////////////////
	public Client createAnInputClientObject(Client clientInInfos, JPanel windowPanel, boolean clientchoice) {
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
					clientInInfos.putInInfos(takeElementsOfClientFromTheInnerList(k, clientchoice).toString(),
							((JTextField) c).getText());
					k = k + 1;
				}

			}
			z++;

		}

		return clientInInfos;
	}

//////////////////////////////// GUI//////////////////////////////////

////////getting and adding names of fields////////////////
	String takeElementsOfClientFromTheInnerList(int i, boolean clientchoice) {

		String elementsOfListClientInfo = " ";
		if (clientchoice) {
			elementsOfListClientInfo = sellerInfo.elements(i);
		} else {
			elementsOfListClientInfo = buyerInfo.elements(i);
		}
		return elementsOfListClientInfo;
	}

	public void addInfosToThePanel(JPanel windowPanel, int size, boolean clientchoice) {
		for (int i = 0; i < size; i++) {
			windowPanel.add(new JLabel(takeElementsOfClientFromTheInnerList(i, clientchoice)));
			windowPanel.add(new JTextField(""));
			// maybe better option is to create a list of each new field
		}
	}

	public JLabel doTheJLabel(JLabel confirmedOrError, JPanel windowPanel, String message) {
		confirmedOrError = new JLabel();
		windowPanel.add(confirmedOrError);
		confirmedOrError.setText(message);
		return confirmedOrError;
	}

	public static void repaintFrame() {
		windowFrame.invalidate();
		windowFrame.validate();
		windowFrame.repaint();
	}

	public static void cleanUp(JPanel panelToClean, JLabel confirmedOrError, JButton BtnConfirm, JButton BtnClear) {

		// confirmedOrError.setText(" ");
		// confirmedOrError =null;
		panelToClean.remove(confirmedOrError);
		for (int i = 0; i <= 10; i++) {
			System.out.println(" ");
		}

		for (Component c : panelToClean.getComponents()) {
			if (c instanceof JTextField) {
				((JTextField) c).setText("");
			}
		}
		overwriteFlag_1 = false;
		overwriteFlag_2 = false;
		repaintFrame();
		BtnConfirm.setEnabled(true);
		BtnClear.setEnabled(false);

	}

////////////////////////////////ADDITIONAL FILE OPERATIONS//////////////////////////////////

	public void saveData() {
		//// write to txt file
		saveTextFile testSave = new saveTextFile();

		if (testSave.checkIfAFIleIsAlreadyExisting() && !overwriteFlag_1 && !overwriteFlag_2) {
			System.out.println("Boolean " + testSave.checkIfAFIleIsAlreadyExisting());
			message = "Confirmed. Successfully wrote to the file.";

			// creating seller's object and displaying informations in the console

			// v4.0 false as an argument - buyer
			createAnInputClientObject(new Client(), windowPanelBottom, false).displayInInfos();
			testSave.makeAFile(createAnInputClientObject(new Client(), windowPanelBottom, false).getClientMap());
			overwriteFlag_1 = false;
			overwriteFlag_2 = true;
		} else {
			System.out.println("Boolean " + testSave.checkIfAFIleIsAlreadyExisting());
			if (!testSave.checkIfAFIleIsAlreadyExisting() && overwriteFlag_1 && !overwriteFlag_2) {
				createAnInputClientObject(new Client(), windowPanelBottom, false).displayInInfos();
				testSave.makeAFile(createAnInputClientObject(new Client(), windowPanelBottom, false).getClientMap());

				message = "File already exists. Append new data to existing file.";

			} else {
				message = "Old file already exists. Cannot create a new one.";
				confirmButtonTop.setEnabled(true);
				clearButtonTop.setEnabled(false);
			}
		}
	}

	public String deleteExistingFileGUI(saveTextFile deleteFile, boolean flagAppWindow) {

		flagAppWindow = deleteFile.deleteAFIle();
		if (flagAppWindow) {
			message = "File deleted successfully";
		} else {
			message = "Failed to delete the file";
		}
		return message;
	}

////////////////////////////////TEST METHODS//////////////////////////////////
	public boolean checkIfJLabelConfirmedOrErrorIsNull(JLabel confirmedOrError, JPanel windowPanel) {
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
