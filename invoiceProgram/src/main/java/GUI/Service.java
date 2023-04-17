package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Client.DataOfAll;
import Data.PDFCreator;

public class Service extends WindowApp implements ActionListener {

	private JFrame windowFrame;
	private JPanel windowPanel;
	private static JLabel infoLabel;
	private int widthWindowMain = (int) (37.5 * new DataOfAll(3).findSize());// 600
	private int heightWindowMain = (int) (37.5 * new DataOfAll(3).findSize());
	private int row = 2 * (new DataOfAll(3).findSize()) + 5;
	private int coll = 1;
	private static final long serialVersionUID = 1L;
	private static JButton confirmButton;
	private static JButton clearButton;
	private static JButton deleteButton;
	private static JButton closeButton;
	private String message = "";
	private String VATValue;
	final private String absPathPDF = System.getProperty("user.dir") + "\\Invoice_" + LocalDate.now() + ".pdf";

	private static boolean conditionFlag_1 = false;
	private static boolean changeData = false;
	private byte[] pdfNewByteArray = new byte[8192];

	/**
	 * Inheritance applied but call "zero" constructor
	 * 
	 */

	public Service(String VATValue) {
		super(VATValue);
		init();
	}

	/**
	 * Init of the window
	 */
	@Override
	public void init() {

		windowFrame = new JFrame();

		windowFrame.setTitle("Invoice Program [2]");
		windowFrame.setPreferredSize(new Dimension(widthWindowMain, heightWindowMain));
		windowFrame.setLocation(500, 100);
		windowFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		windowFrame.pack();
		windowFrame.setResizable(false);
		windowFrame.setVisible(true);
		windowPanel = new JPanel(new GridLayout(row, coll, 0, 0));
		windowPanel.setBackground(Color.WHITE);
		panelDisplay(windowPanel, 3);
		windowPanel.setVisible(true);
		windowFrame.add(windowPanel);

		confirmButton = new JButton("Confirm Data");
		clearButton = new JButton("Clear Data");
		deleteButton = new JButton("Delete the file");
		closeButton = new JButton("Close");

		confirmButton.addActionListener(this);
		windowPanel.add(confirmButton);

		closeButton.addActionListener(this);
		windowPanel.add(closeButton);

		clearButton.addActionListener(this);
		windowPanel.add(clearButton);

		deleteButton.addActionListener(this);
		windowPanel.add(deleteButton);

		confirmButton.setEnabled(true);
		clearButton.setEnabled(true);
		deleteButton.setEnabled(true);

	}

	/**
	 * @Override actionPerformed
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		doTheThings_actionPerformed(e);
	}

	/**
	 * @Override doTheThings_actionPerformed
	 */
	@Override
	public void doTheThings_actionPerformed(ActionEvent e) {
		Object[] options = { "OK" };
		// int proceed;
		switch (e.getActionCommand()) {
		case "Confirm Data":
			// System.out.println(" ");

			// pdfNewByteArray = readAllBytesFromThePDF(absPathPDF, pdfNewByteArray);

			infoLabel = confirmOrOverwrite(options, changeData, conditionFlag_1, pdfObject, windowPanel, infoLabel,
					confirmButton, clearButton, deleteButton, windowFrame, message);

			conditionFlag_1 = true;
			confirmButton.setEnabled(false);

			break;
		case "Overwrite":

			// restoreSavedByteArrayAsAFile(absPathPDF, pdfNewByteArray);
			infoLabel = confirmOrOverwrite(options, changeData, conditionFlag_1, pdfObject, windowPanel, infoLabel,
					confirmButton, clearButton, deleteButton, windowFrame, message);
			confirmButton.setEnabled(false);
			// System.out.println("conditionFlag_1 " + proceedFlag_2);
			break;
		case "Clear Data":

			confirmButton.setText("Overwrite");
			confirmButton.setEnabled(true);
			changeData = false;

			infoLabel = clearData(infoLabel, windowPanel, confirmButton, clearButton, true);
			break;

		case "Delete the file":
			// System.out.println(" tutaj Delete the file");
			message = deleteFlagGUI(new PDFCreator(), false);
			infoLabel = deleteTheFile(infoLabel, windowPanel, clearButton, clearButton, message);
			confirmButton.setEnabled(false);
			clearButton.setEnabled(false);
			break;
		case "Close":
			windowFrame.dispose();
		default:
		}
	}

	/**
	 * @Override clearData clearData must be overwritten so that the VAT rate
	 *           information is not cleared
	 */
	@Override
	protected JLabel clearData(JLabel infoLabel, JPanel windowPanel, JButton confirmButton, JButton clearButton,
			boolean b) {

		boolean tempBoolean = false;

		if (!checkStatusOfLabel(infoLabel, windowPanel)) {

			windowPanel.remove(infoLabel);

			for (Component c : windowPanel.getComponents()) {

				if (c instanceof JLabel) {
					if (((JLabel) c).getText().toString() == "07.VAT rate") {
						tempBoolean = false;
					} else {
						tempBoolean = true;
					}

				}

				if (c instanceof JTextField && tempBoolean) {
					((JTextField) c).setText("");
				}

			}

		}
		infoLabel = createJLabel(infoLabel, windowPanel, "Cleared");
		confirmButton.setEnabled(true);
		clearButton.setEnabled(false);
		return infoLabel;

	}

	/**
	 * Confirm or overwrite
	 */
	protected JLabel confirmOrOverwrite(Object[] options, boolean changeData, boolean conditionFlag_1,
			PDFCreator pdfObject, JPanel windowPanel, JLabel infoLabel, JButton confirmButton, JButton clearButton,
			JButton deleteButton, JFrame windowFrame, String message) {

		if (!conditionFlag_1) {
			pdfNewByteArray = readAllBytesFromThePDF(absPathPDF, pdfNewByteArray);
		} else {
			restoreSavedByteArrayAsAFile(absPathPDF, pdfNewByteArray);
		}

		try {

			if (pdfObject.checkIfAFIleIsAlreadyExistingPDF()) {

				message = errorEncounteredNoneEmptyField(
						theMiracleOfCreation(pdfObject, windowPanel, 3, 5, 500, 0, 0, true, changeData));
				confirmButton.setEnabled(true);
				clearButton.setEnabled(true);
				conditionFlag_1 = true;

				/**
				 * Lambda expression
				 */

				deleteButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						windowFrame.dispose();
						// System.out.println("LAMBDA TEST");
					}
				});

			} else {

				if (!pdfObject.checkIfAFIleIsAlreadyExistingPDF()) {
					conditionFlag_1 = false;
					confirmButton.setEnabled(false);
					clearButton.setEnabled(false);
					deleteButton.setEnabled(true);

				} else {
					setButtonsOff();

					message = "Error";

				}
			}

		} catch (Exception e1) {

			e1.printStackTrace();
			message = "Error";

		}

		if (!checkStatusOfLabel(infoLabel, windowPanel)) {
			windowPanel.remove(infoLabel);
		}
		return infoLabel = createJLabel(infoLabel, windowPanel, message);
	}

}
