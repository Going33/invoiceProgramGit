package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Client.dataOfAll;
import Main.Main;

public class Service extends windowApp implements ActionListener {
	private JFrame windowFrame;
	private JPanel windowPanel;
	 private static JLabel confirmedOrError;
	 private int widthWindowMain = 600;
	 private int heightWindowMain = 600;
	private int row = 24;
	private	int coll = 1;
	private static final long serialVersionUID = 1L;
	private static JButton confirmButton;
	private static JButton clearButton;
	private static	JButton deleteButton;
	private String message = "";
	
	 private static boolean conditionFlag_1 = false;
	 private static boolean conditionFlag_2 = false;

	 private static boolean proceedFlag_1 = false;
	 private static boolean proceedFlag_2 = false;
	
	
	
	
	
	
	
	
	
	/**
	 *	The trick move, the "parent" object is created but without using inheritance
	 *	 private object windowAppForStealMethos = new windowApp(0);
	 *UPDATE
	 *	solved in a better way using Inheritance, inheritance applied but call constructor 'zero'.
	 */
//	private windowApp objectForStealMethos = new windowApp(0);
	dataOfAll serviceInfo = new dataOfAll(3);

	
	public Service(int i) {
		super(0);
		init();
	}



	@Override
	public void init() {

		windowFrame = new JFrame();

		windowFrame.setTitle("Invoice Program [2]");
		windowFrame.setPreferredSize(new Dimension(widthWindowMain,heightWindowMain));
		windowFrame.setLocation(500, 100);
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowFrame.pack();
		windowFrame.setResizable(false);
		windowFrame.setVisible(true);
		System.out.println(row + " " + coll);
		System.out.println(serviceInfo.findSize());
		windowPanel = new JPanel(new GridLayout(row, coll, 0, 0));
		windowPanel.setBackground(Color.WHITE);
		PanelDisplaying(windowPanel,3);
		windowPanel.setVisible(true);
		windowFrame.add(windowPanel);
		
		confirmButton = new JButton("Confirm Data");
		clearButton = new JButton("Clear Data");
		deleteButton = new JButton("Delete the file");
		
		
		confirmButton.addActionListener(this);
		windowPanel.add(confirmButton);
		
		clearButton.addActionListener(this);
		windowPanel.add(clearButton);
		
		deleteButton.addActionListener(this);
		windowPanel.add(deleteButton);
		
		confirmButton.setEnabled(true);
		clearButton.setEnabled(true);
		deleteButton.setEnabled(true);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		DoTheThings_actionPerformed(e);
	}



	@Override
	public void DoTheThings_actionPerformed(ActionEvent e) {
		Object[] options = { "Open", "Close"};
		int proceed;
		switch (e.getActionCommand())
		{
		case "Confirm Data":
			System.out.println(" ");
			try {

				// TODO TO OPTIMIZE!!!!!!!!!!!!!

				if (testSave.checkIfAFIleIsAlreadyExistingPDF() && !conditionFlag_1) {
					// System.out.println("Boolean " + testSave.checkIfAFIleIsAlreadyExistingPDF());
					//message = "File dosen't exist! Create file again!";

					// creating seller's object and displaying informations in the console
					//TheMiracleOfCreation(testSave, windowPanel, 3);
					conditionFlag_1 = true;
					confirmButton.setEnabled(false);
					clearButton.setEnabled(false);
					deleteButton.setEnabled(true);
					
					/**
					 * Lambda expression
					 */

					deleteButton.addActionListener(new ActionListener() {
					    public void actionPerformed(ActionEvent evt) {
					    	windowFrame.dispose();
					    	System.out.println("LAMBDA TEST");
					    }
					});
					proceed = JOptionPane.showOptionDialog(windowFrame,
							"File dosen't exist! Re-open application or close?", "Warning", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
					
					if(proceed==0)
					{
						windowFrame.dispose();
						  System.exit(0);
						  //TODO - if possible
						new Main();
					}else {
						windowFrame.dispose();
					}
				} else {
					// System.out.println("Boolean " + testSave.checkIfAFIleIsAlreadyExistingPDF());
					if (!testSave.checkIfAFIleIsAlreadyExistingPDF() && !conditionFlag_1) {

						TheMiracleOfCreation(testSave, windowPanel, 3);
						message = "File already exists. Append new data to existing file.";
						confirmButton.setEnabled(false);
						clearButton.setEnabled(true);
						conditionFlag_1=true;
					} else {
						message = "Old file already exists. Cannot overwrite.";
						SetButtonsOff();
					}
				}

			} catch (Exception e1) {
		
				e1.printStackTrace();
				message = "Error";

			}

			if (!CheckStatusOfLabel(confirmedOrError, windowPanel)) {

				// delete existing "delete" JLabel
				windowPanel.remove(confirmedOrError);

			}
			// creating JLabel for the confirm message
			confirmedOrError = CreateJLabel(confirmedOrError, windowPanel, message);

			// System.out.println("Data of Buyer Confirmed");
			conditionFlag_1 = true;
			System.out.println("conditionFlag_1 " + proceedFlag_2);
			break;		
			
			
			
			
			
		case "Clear Data":
			System.out.println(" ");

			// delete existing "confirm" JLabel
			if (!CheckStatusOfLabel(confirmedOrError, windowPanel)) {

				windowPanel.remove(confirmedOrError);
				ClearOnlyPanel(windowPanel, confirmedOrError, confirmButton, clearButton, true);
			}

			// creating JLabel for the delete message
			confirmedOrError = CreateJLabel(confirmedOrError, windowPanel, "Cleared");
			// SetButtonsOn();
			confirmButton.setEnabled(true);
			clearButton.setEnabled(false);
			// System.out.println("Clear Data of Seller");
		case "deleteButton":
			
			break;
		default:
		}
	}


	
}
