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

		addInfosToThePanelTop();

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

		addInfosToThePanelBottom();

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
			System.out.println(" ");
		
			try {
			
				if(true)
				{
					System.out.println("FYI [2]");
				}
				message = "Confirmed. Successfully wrote to the file.";

				// creating seller's object and displaying informations in the console

				// v1
				// createAnInputObjectOfSeller();
				// SellerClass newSellerObject = createAnInputObjectOfSeller(new SellerClass());
				// v2
				// newSellerObjectWindowAppClass = createAnInputObjectOfSeller(new
				// SellerClass());
				// ((SellerClass) newSellerObjectWindowAppClass).displayInInfos();

				// v3
				createAnInputObjectOfSeller(new SellerClass()).displayInInfos();

////write to txt file
				saveTextFile testSave = new saveTextFile();
				testSave.makeAFile(createAnInputObjectOfSeller(new SellerClass()).getMapSeller());
////

				confirmButtonTop.setEnabled(false);
				clearButtonTop.setEnabled(true);
			} catch (Exception e1) {
				// cleanUp();
				e1.printStackTrace();
				message = "Error";

			}

//checking if JLabel is null 
// needed to avoid "trash-old" message 
			// checkIfJLabelConfirmedOrErrorIsNull(confirmedOrErrorTop, windowPanelTop);
			//windowPanelTop.remove(confirmedOrErrorTop);
	
			if (checkIfJLabelConfirmedOrErrorIsNull(confirmedOrErrorTop, windowPanelTop)) {
	
				confirmedOrErrorTop = new JLabel(message);
				windowPanelTop.add(confirmedOrErrorTop);
				System.out.println("Data of Seller Confirmed");

			} else {

				windowPanelTop.remove(confirmedOrErrorTop);
			}
//			confirmedOrErrorTop = new JLabel(message);
//			windowPanelTop.add(confirmedOrErrorTop);
//			System.out.println("Data of Seller Confirmed");
			break;

		case "Confirm Data of Buyer":
			System.out.println(" ");
			try {

				message = "Confirmed";

				createAnInputObjectOfBuyer(new BuyerClass()).displayInInfos();
				//// write to txt file
				saveTextFile testSave = new saveTextFile();
				testSave.makeAFile(createAnInputObjectOfBuyer(new BuyerClass()).getMapBuyer());
				////

				confirmButtonBottom.setEnabled(false);
				clearButtonBottom.setEnabled(true);
			} catch (Exception e1) {
				// cleanUp();
				e1.printStackTrace();
				message = "Error";

			}
			checkIfJLabelConfirmedOrErrorIsNull(confirmedOrErrorBottom, windowPanelBottom);
			confirmedOrErrorBottom = new JLabel(message);
			windowPanelBottom.add(confirmedOrErrorBottom);
			System.out.println("Data of Buyer Confirmed");
			break;

		case "Clear Data of Seller":
			/// boolean flagSeller = false;

			System.out.println(" ");
			cleanUp(windowPanelTop, confirmedOrErrorTop, confirmButtonTop, clearButtonTop);
			// message =deleteExistingFile (new saveTextFile(),false);

			// confirmedOrErrorTop = new JLabel(message);
			// confirmedOrErrorTop = new JLabel(deleteExistingFile (new saveTextFile(),false));
			windowPanelTop.add(new JLabel(deleteExistingFile(new saveTextFile(), false)));

			System.out.println("Clear Data of Seller");
			break;

		case "Clear Data of Buyer":
			cleanUp(windowPanelBottom, confirmedOrErrorBottom, confirmButtonBottom, clearButtonBottom);
			windowPanelBottom.add(new JLabel(deleteExistingFile(new saveTextFile(), false)));
			System.out.println("Clear Data of Buyer");
			break;

		default:
			System.out.println("default");
		}

		repaintFrame();

	}

	//////////////////////////////// Objects//////////////////////////////////

	//////// creating specific Object - Seller////////////////
	public SellerClass createAnInputObjectOfSeller(SellerClass sellerInInfos) {
		// SellerClass sellerInInfos = new SellerClass();

		///////////////// iterate through Jpanel
		/// https://stackoverflow.com/questions/1037139/loop-through-jpanel
		int z = 0;
		int k = 0;
		sellerInInfos.clearTreeMap();
		for (Component c : windowPanelTop.getComponents()) {

			if (c instanceof JTextField) {
				((JTextField) c).getText();

				if (z % 2 != 0) {
					// System.out.println(z);
					// add to HASHMAP Object
					sellerInInfos.putInInfos(takeElementsOfSellerFromTheInnerList(k).toString(),
							((JTextField) c).getText());
					k = k + 1;
				}

			}
			z++;

		}
		// sellerInInfos.displayInInfos();

		return sellerInInfos;
	}

	//////// creating specific Object - Buyer////////////////
	public BuyerClass createAnInputObjectOfBuyer(BuyerClass buyerInInfos) {
		// BuyerClass buyerInInfos = new BuyerClass();

		///////////////// iterate through Jpanel
		/// https://stackoverflow.com/questions/1037139/loop-through-jpanel
		int z = 0;
		int k = 0;
		buyerInInfos.clearTreeMap();
		for (Component c : windowPanelBottom.getComponents()) {

			if (c instanceof JTextField) {
				((JTextField) c).getText();

				if (z % 2 != 0) {
					// System.out.println(z);
					// add to HASHMAP Object
					buyerInInfos.putInInfos(takeElementsOfSellerFromTheInnerList(k).toString(),
							((JTextField) c).getText());
					k = k + 1;

				}
			}
			z++;

		}
		// buyerInInfos.displayInInfos();

		return buyerInInfos;
	}

	//////////////////////////////// GUI//////////////////////////////////

	//////// getting and adding names of fields////////////////
	String takeElementsOfSellerFromTheInnerList(int i) {

		String elementsOfListSellerInfo = " ";
		return elementsOfListSellerInfo = sellerInfo.elements(i);
	}

	String takeElementsOfBuyerFromTheInnerList(int i) {

		String elementsOfListBuyerInfo = " ";
		return elementsOfListBuyerInfo = buyerInfo.elements(i);
	}

	public void addInfosToThePanelTop() {
		for (int i = 0; i < sellerInfo.size(); i++) {
			windowPanelTop.add(new JLabel(takeElementsOfSellerFromTheInnerList(i)));
			windowPanelTop.add(new JTextField(""));
			// maybe better option is to create a list of each new field
		}
	}

	public void addInfosToThePanelBottom() {
		for (int i = 0; i < buyerInfo.size(); i++) {
			windowPanelBottom.add(new JLabel(takeElementsOfBuyerFromTheInnerList(i)));
			windowPanelBottom.add(new JTextField(""));
			// maybe better option is to create a list of each new field
		}
	}

	public static void repaintFrame() {
		windowFrame.invalidate();
		windowFrame.validate();
		windowFrame.repaint();
	}

	public static void cleanUp(JPanel panelToClean, JLabel confirmedOrError, JButton BtnConfirm, JButton BtnClear) {

		panelToClean.remove(confirmedOrError);
		for (int i = 0; i <= 50; i++) {
			System.out.println(" ");
		}

		for (Component c : panelToClean.getComponents()) {
			if (c instanceof JTextField) {
				((JTextField) c).setText("");
			}
		}

		repaintFrame();

		BtnConfirm.setEnabled(true);
		BtnClear.setEnabled(false);
	}

	public boolean checkIfJLabelConfirmedOrErrorIsNull(JLabel confirmedOrError, JPanel windowPanel) {
		boolean flaghelp;
		if (confirmedOrError != null) {
			System.out.println("Confirmed NOT NULL");
			// windowPanel.remove(confirmedOrError);
			flaghelp = false;
		} else {

			System.out.println("Confirmed IS NULL");
			flaghelp = true;

		}
		return flaghelp;
	}

	public String deleteExistingFile(saveTextFile deleteFile, boolean flagAppWindow) {
		// saveTextFile deleteSeller = new saveTextFile();
		// boolean flagAppWindow = false;
		flagAppWindow = deleteFile.deleteAFIle();
		if (flagAppWindow) {
			message = "File deleted successfully";
		} else {
			message = "Failed to delete the file";
		}
		return message;
	}

}
