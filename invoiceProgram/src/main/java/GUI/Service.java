package GUI;

import java.awt.Color;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Client.DataOfAll;
import Data.PDFCreator;

public class Service extends WindowApp implements ActionListener {

	private JFrame windowFrame;
	private JPanel windowPanel;
	private static JLabel infoLabel;
	private int widthWindowMain = (int) (37.5 * new DataOfAll(3).findSize());// 600
	private int heightWindowMain = (int) (37.5 * new DataOfAll(3).findSize());
	private int row = 2 * (new DataOfAll(3).findSize()) + 4;
	private int coll = 1;
	private static final long serialVersionUID = 1L;
	private static JButton confirmButton;
	private static JButton clearButton;
	private static JButton deleteButton;
	private String message = "";
	final private String absPathPDF = System.getProperty("user.dir") + "\\Invoice_" + LocalDate.now() + ".pdf";
	
	private static boolean conditionFlag_1 = false;
	private static boolean changeData = false;
	private byte[] pdfNewByteArray = new byte[8192];

	/**
	 * The trick move, the "parent" object is created but without using inheritance
	 * private object windowAppForStealMethos = new windowApp(0); UPDATE solved in a
	 * better way using Inheritance, inheritance applied but call constructor
	 * 'zero'.
	 */
//	private windowApp objectForStealMethos = new windowApp(0);
//	DataOfAll serviceInfo = new DataOfAll(3);

	public Service(int i) {
		super(0);
		init();
	}

	@Override
	public void init() {

		windowFrame = new JFrame();

		windowFrame.setTitle("Invoice Program [2]");
		windowFrame.setPreferredSize(new Dimension(widthWindowMain, heightWindowMain));
		windowFrame.setLocation(500, 100);
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowFrame.pack();
		windowFrame.setResizable(false);
		windowFrame.setVisible(true);
		//System.out.println(row + " " + coll);
		//System.out.println(new DataOfAll(3).findSize());
		windowPanel = new JPanel(new GridLayout(row, coll, 0, 0));
		windowPanel.setBackground(Color.WHITE);
		panelDisplay(windowPanel, 3);
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
		doTheThings_actionPerformed(e);
	}

	@Override
	public void doTheThings_actionPerformed(ActionEvent e) {
		Object[] options = { "OK" };
		//int proceed;
		switch (e.getActionCommand()) {
		case "Confirm Data":
			//System.out.println(" ");

			Path pdfPath = Paths.get(absPathPDF);
			
			try {
				 pdfNewByteArray = Files.readAllBytes(pdfPath);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			infoLabel = confirmOrOverwrite(options,changeData,conditionFlag_1,pdfObject,windowPanel,infoLabel,confirmButton,clearButton,deleteButton,windowFrame,message);
			// System.out.println("Data of Buyer Confirmed");
			conditionFlag_1 = true;
			//System.out.println("conditionFlag_1 " + proceedFlag_2);
			break;
		case "Overwrite":
			//to the fix
			//System.out.println(" ");
			
			File pdfOld= new File(absPathPDF); 
			pdfOld.delete();
			OutputStream out;
			try {
				out = new FileOutputStream(absPathPDF);
				out.write(pdfNewByteArray);
				out.close();
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			//conditionFlag_1 = true;
			//System.out.println("Tutaj Overwrite " +changeData);
			infoLabel = confirmOrOverwrite(options,changeData,conditionFlag_1,pdfObject,windowPanel,infoLabel,confirmButton,clearButton,deleteButton,windowFrame,message);

		
		
			//System.out.println("conditionFlag_1 " + proceedFlag_2);
			break;
		case "Clear Data":
			//System.out.println(" ");
			//////////
			//does not work correctly yet
			confirmButton.setText("Overwrite");
			changeData = false;
			/////////
			//System.out.println("Tutaj Clear Data " +changeData);
			infoLabel = clearData(infoLabel, windowPanel, confirmButton, clearButton, true);
			break;

		case "Delete the file":
			//System.out.println(" tutaj Delete the file");
			message = deleteFlagGUI(new PDFCreator(), false);
			infoLabel = deleteTheFile(infoLabel, windowPanel, clearButton, clearButton, message);
			confirmButton.setEnabled(false);
			clearButton.setEnabled(false);
			break;
		default:
		}
	}

	protected JLabel confirmOrOverwrite(Object[] options, boolean changeData, boolean conditionFlag_1, PDFCreator pdfObject, JPanel windowPanel, JLabel infoLabel, JButton confirmButton, JButton clearButton, JButton deleteButton, JFrame windowFrame, String message) {
		int proceed;
		try {

			// TODO TO OPTIMIZE!!!!!!!!!!!!!

			if (pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData)) {
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
						//System.out.println("LAMBDA TEST");
					}
				});

				proceed = JOptionPane.showOptionDialog(windowFrame,
						"File dosen't exist!", "Warning", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

			} else {
				System.out.println(" PLIK "+pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData) + " conditionFlag_1 " + conditionFlag_1);
				
				
				if (!pdfObject.checkIfAFIleIsAlreadyExistingPDF(changeData)) {
					// pos x,pos y pos_x offset, pos_y offset
					theMiracleOfCreation(pdfObject, windowPanel, 3, 5, 550, 0, 0, true, changeData,2);
					message = "Append data to the file.";
					confirmButton.setEnabled(false);
					clearButton.setEnabled(true);
					conditionFlag_1 = true;
				
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

			// delete existing "delete" JLabel
			windowPanel.remove(infoLabel);

		}
		return infoLabel = createJLabel(infoLabel, windowPanel, message);
	}

}
